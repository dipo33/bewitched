package com.dipo33.bewitched.worldgen;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.dipo33.bewitched.init.BewitchedBlocks;

/**
 * Hawthorn: a short trunk carrying a wide, flat-ish crown.
 * <p>
 * The tree is first built into an in-memory {@link TreeShape} in phases, then written
 * to the world in one go:
 * <ol>
 *   <li>skeleton — trunk and branch arms</li>
 *   <li>crown — stacked leaf ellipses with jittered centers and ragged edges</li>
 *   <li>prune — leaves the edge noise left further than {@link #MAX_LOG_DISTANCE}
 *       from any log (measured through leaves, like vanilla decay) are removed</li>
 *   <li>wrap — any log face still exposed to air gets a leaf, so no branch wood is
 *       ever visible from outside; only the trunk column below the crown stays bare</li>
 * </ol>
 * Before anything is built, a vanilla-style clearance column around the trunk is
 * checked; the write phase then replaces only air and leaves, exactly like vanilla
 * tree generation.
 */
public class WorldGenHawthornTree extends WorldGenAbstractTree {

    private static final int HAWTHORN = 2;

    private static final int MIN_TRUNK_HEIGHT = 5;
    private static final int MAX_TRUNK_HEIGHT = 9;
    // Crown base (widest layer) is this many blocks above ground
    private static final int CROWN_BASE_Y = 3;
    // w1 (x-axis diameter) = 2h + W1_MIN_BONUS + rand[0, W1_RAND_RANGE)
    private static final int W1_MIN_BONUS = 1;
    private static final int W1_RAND_RANGE = 3;
    // w2 (z-axis diameter) = 2h + W2_MIN_BONUS + rand[0, W2_RAND_RANGE)
    private static final int W2_MIN_BONUS = -2;
    private static final int W2_RAND_RANGE = 6;
    // Each successive crown layer shrinks by TAPER_MIN..(TAPER_MIN+TAPER_RANGE-1) in radius
    private static final int TAPER_MIN = 1;
    private static final int TAPER_RANGE = 2;
    // Chance for extra aesthetic branches at non-primary heights
    private static final float BRANCH_CHANCE_1ST = 0.65f;
    private static final float BRANCH_CHANCE_2ND = 0.35f;
    // Leaves decay if no log is reachable within this many blocks (through leaves)
    private static final int MAX_LOG_DISTANCE = 4;

    // Crown edge noise: leaves are guaranteed inside EDGE_NOISE_START of the radius,
    // increasingly likely to be dropped towards the rim (up to EDGE_DROP_CHANCE),
    // and occasionally bump out just past it.
    private static final double EDGE_NOISE_START = 0.7;
    private static final float EDGE_DROP_CHANCE = 0.5f;
    private static final double BUMP_MAX_DIST = 1.15;
    private static final float BUMP_CHANCE = 0.25f;
    // Layers narrower than this keep their center on the trunk so it stays covered
    private static final double MIN_JITTER_RADIUS = 2.5;

    // Space check before growing (vanilla-style): square radius around the trunk
    // column that must be free of non-replaceable blocks
    private static final int TRUNK_CLEARANCE_RADIUS = 0;
    private static final int CROWN_CLEARANCE_RADIUS = 2;

    // Cardinal and diagonal directions kept separate so primary arms can be staggered
    // across two Y levels, preventing bark-to-bark contacts between adjacent arms.
    private static final int[][] CARDINAL_DIRS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    private static final int[][] DIAGONAL_DIRS = {
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    private static final int[][] ALL_8_DIRS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    private static final int[][] SIX_NEIGHBOURS = {
        {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}
    };

    /**
     * The tree under construction: log/leaf positions before anything touches the world.
     */
    private static final class TreeShape {

        final Map<Long, int[]> logs = new LinkedHashMap<>(); // key -> {x, y, z, meta}
        final Map<Long, int[]> leaves = new LinkedHashMap<>(); // key -> {x, y, z}

        static long key(int x, int y, int z) {
            return ((long) x & 0x3FFFFFFL) << 38 | ((long) z & 0x3FFFFFFL) << 12 | (y & 0xFFFL);
        }

        void addLog(int x, int y, int z, int meta) {
            logs.put(key(x, y, z), new int[]{x, y, z, meta});
        }

        void addLeaf(int x, int y, int z) {
            leaves.putIfAbsent(key(x, y, z), new int[]{x, y, z});
        }

        boolean isLog(int x, int y, int z) {
            return logs.containsKey(key(x, y, z));
        }

        boolean isLeaf(int x, int y, int z) {
            return leaves.containsKey(key(x, y, z));
        }

        /**
         * @return log meta at the position, or -1 if there is no log there
         */
        int logMeta(int x, int y, int z) {
            int[] p = logs.get(key(x, y, z));
            return p == null ? -1 : p[3];
        }
    }

    public WorldGenHawthornTree() {
        super(true);
    }

    @Override
    public boolean generate(World world, Random rng, int x, int y, int z) {
        int h = MIN_TRUNK_HEIGHT + rng.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
        int hc = (int) Math.round(h / 5.0);
        int w1 = 2 * h + W1_MIN_BONUS + rng.nextInt(W1_RAND_RANGE);
        int w2 = 2 * h + W2_MIN_BONUS + rng.nextInt(W2_RAND_RANGE);

        int crownBase = y + CROWN_BASE_Y - 1;
        int crownTip = y + h - 1 + hc;
        int totalLayers = h + hc - CROWN_BASE_Y + 1;

        if (y < 1 || crownTip + 1 > 256) {
            return false;
        }

        Block soil = world.getBlock(x, y - 1, z);
        if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
            return false;
        }
        if (!hasSpaceToGrow(world, x, y, z, crownBase, crownTip)) {
            return false;
        }
        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        double[] rxArr = new double[totalLayers];
        double[] rzArr = new double[totalLayers];
        rxArr[0] = (w1 - 2) / 2.0; // sub-base: 1 block narrower on each side than base
        rzArr[0] = (w2 - 2) / 2.0;
        rxArr[1] = w1 / 2.0;        // base (widest)
        rzArr[1] = w2 / 2.0;
        for (int i = 2; i < totalLayers; i++) {
            rxArr[i] = Math.max(0.5, rxArr[i - 1] - (TAPER_MIN + rng.nextInt(TAPER_RANGE)));
            rzArr[i] = Math.max(0.5, rzArr[i - 1] - (TAPER_MIN + rng.nextInt(TAPER_RANGE)));
        }

        TreeShape shape = new TreeShape();

        for (int i = 0; i < h; i++) {
            shape.addLog(x, y + i, z, HAWTHORN);
        }

        // ── Primary branches ─────────────────────────────────────────────────────
        // Cardinals and diagonals are placed on adjacent Y levels so that no two
        // arms are ever face-adjacent at step 1, eliminating bark-to-bark contacts.
        // Both sets together provide full 8-direction coverage.
        int mainBranchY = y + CROWN_BASE_Y;       // widest layer; always inside trunk (h >= 5)
        int mainDiagY = mainBranchY + 1;         // one above; also inside trunk (h >= 5)
        addDirectionalBranches(shape, rng, x, mainBranchY, z, rxArr, rzArr, crownBase, totalLayers, CARDINAL_DIRS);
        addDirectionalBranches(shape, rng, x, mainDiagY, z, rxArr, rzArr, crownBase, totalLayers, DIAGONAL_DIRS);

        int secondBranchY = Math.min(y + h - 1, crownTip - MAX_LOG_DISTANCE);
        int secondDiagY = -1; // sentinel: no second level
        if (secondBranchY > mainDiagY) {
            secondDiagY = secondBranchY + 1 <= y + h - 1 ? secondBranchY + 1 : secondBranchY - 1;
            addDirectionalBranches(shape, rng, x, secondBranchY, z, rxArr, rzArr, crownBase, totalLayers, CARDINAL_DIRS);
            addDirectionalBranches(shape, rng, x, secondDiagY, z, rxArr, rzArr, crownBase, totalLayers, DIAGONAL_DIRS);
        }

        // ── Aesthetic branches ────────────────────────────────────────────────────
        for (int i = 0; i < h; i++) {
            int by = y + i;
            if (by == mainBranchY || by == mainDiagY
                || by == secondBranchY || by == secondDiagY) {
                continue;
            }
            int li = by - crownBase;
            if (li < 0 || li >= totalLayers) {
                continue;
            }

            if (rng.nextFloat() < BRANCH_CHANCE_1ST) {
                addShortBranch(shape, world, rng, x, by, z, rxArr[li], rzArr[li]);
            }
            if (rng.nextFloat() < BRANCH_CHANCE_2ND) {
                addShortBranch(shape, world, rng, x, by, z, rxArr[li], rzArr[li]);
            }
        }

        // ── Crown ─────────────────────────────────────────────────────────────────
        for (int li = 0; li < totalLayers; li++) {
            addNoisyLeafLayer(shape, rng, x, crownBase + li, z, rxArr[li], rzArr[li]);
        }

        pruneUnsupportedLeaves(shape);
        wrapExposedLogs(shape, x, z, crownBase);

        // ── Write to world ────────────────────────────────────────────────────────
        Block logBlock = BewitchedBlocks.LOG.get();
        Block leavesBlock = BewitchedBlocks.LEAVES.get();

        shape.leaves.keySet().removeAll(shape.logs.keySet());
        for (int[] p : shape.logs.values()) {
            if (canReplace(world, p[0], p[1], p[2])) {
                setBlockAndNotifyAdequately(world, p[0], p[1], p[2], logBlock, p[3]);
            }
        }
        for (int[] p : shape.leaves.values()) {
            if (canReplace(world, p[0], p[1], p[2])) {
                setBlockAndNotifyAdequately(world, p[0], p[1], p[2], leavesBlock, HAWTHORN);
            }
        }

        return true;
    }

    /**
     * Vanilla-style space check: a narrow column around the trunk (radius
     * {@link #TRUNK_CLEARANCE_RADIUS} below the crown, {@link #CROWN_CLEARANCE_RADIUS}
     * within it) must contain only replaceable blocks — air, leaves, wood, plants,
     * dirt/grass (see {@link WorldGenAbstractTree#isReplaceable}). Deliberately much
     * smaller than the final crown, matching how vanilla trees only probe near the trunk.
     */
    private boolean hasSpaceToGrow(World world, int x, int y, int z, int crownBase, int crownTip) {
        for (int by = y; by <= crownTip; by++) {
            int radius = by < crownBase ? TRUNK_CLEARANCE_RADIUS : CROWN_CLEARANCE_RADIUS;
            for (int bx = x - radius; bx <= x + radius; bx++) {
                for (int bz = z - radius; bz <= z + radius; bz++) {
                    if (!isReplaceable(world, bx, by, bz)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Same rule vanilla WorldGenTrees uses when placing logs and leaves
    private boolean canReplace(World world, int x, int y, int z) {
        Block b = world.getBlock(x, y, z);
        return b.isAir(world, x, y, z) || b.isLeaves(world, x, y, z);
    }

    private void addDirectionalBranches(TreeShape shape, Random rng, int cx, int by, int cz,
                                        double[] rxArr, double[] rzArr,
                                        int crownBase, int totalLayers, int[][] dirs) {
        int li = by - crownBase;
        if (li < 0 || li >= totalLayers) {
            return;
        }
        double rx = rxArr[li];
        double rz = rzArr[li];

        for (int[] dir : dirs) {
            int ddx = dir[0], ddz = dir[1];
            int ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
            int minLen = ellipseEdge - MAX_LOG_DISTANCE;
            if (minLen <= 0) {
                continue;
            }

            int branchLen = Math.min(minLen + rng.nextInt(3), ellipseEdge - 1);
            int logMeta = logMetaForDirection(ddx, ddz);

            for (int step = 1; step <= branchLen; step++) {
                int bx = cx + ddx * step;
                int bz = cz + ddz * step;
                if (!shape.isLog(bx, by, bz)) {
                    shape.addLog(bx, by, bz, logMeta);
                }
            }
        }
    }

    private void addShortBranch(TreeShape shape, World world, Random rng, int cx, int by, int cz,
                                double rx, double rz) {
        int[] dir = ALL_8_DIRS[rng.nextInt(ALL_8_DIRS.length)];
        int ddx = dir[0], ddz = dir[1];
        int logMeta = logMetaForDirection(ddx, ddz);
        int logAxis = (logMeta >> 2) & 3;
        int ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
        int maxLen = Math.min(ellipseEdge - 1, MAX_LOG_DISTANCE + 1);
        if (maxLen <= 0) {
            return;
        }

        int branchLen = 1 + rng.nextInt(maxLen);
        for (int step = 1; step <= branchLen; step++) {
            int bx = cx + ddx * step;
            int bz = cz + ddz * step;
            double nx = (bx - cx) / rx;
            double nz = (bz - cz) / rz;
            if (nx * nx + nz * nz > 1.0) {
                break;
            }
            if (hasBarkContact(shape, world, bx, by, bz, logAxis)) {
                break;
            }
            if (!shape.isLog(bx, by, bz)) {
                shape.addLog(bx, by, bz, logMeta);
            }
        }
    }

    /**
     * Fills one horizontal crown layer. The ellipse center is jittered by up to one
     * block (wide layers only, so the trunk always stays inside), the rim is ragged:
     * blocks near the edge have an increasing chance to be skipped, and a sparse ring
     * of "bump" leaves may appear just outside the mathematical boundary.
     */
    private void addNoisyLeafLayer(TreeShape shape, Random rng, int cx, int ly, int cz, double rx, double rz) {
        int jx = 0, jz = 0;
        if (Math.min(rx, rz) >= MIN_JITTER_RADIUS) {
            jx = rng.nextInt(3) - 1;
            jz = rng.nextInt(3) - 1;
        }

        int minX = (int) Math.floor(cx + jx - rx * BUMP_MAX_DIST);
        int maxX = (int) Math.ceil(cx + jx + rx * BUMP_MAX_DIST);
        int minZ = (int) Math.floor(cz + jz - rz * BUMP_MAX_DIST);
        int maxZ = (int) Math.ceil(cz + jz + rz * BUMP_MAX_DIST);

        for (int bx = minX; bx <= maxX; bx++) {
            for (int bz = minZ; bz <= maxZ; bz++) {
                double nx = (bx - cx - jx) / rx;
                double nz = (bz - cz - jz) / rz;
                double d = Math.sqrt(nx * nx + nz * nz);

                if (d <= EDGE_NOISE_START) {
                    shape.addLeaf(bx, ly, bz);
                } else if (d <= 1.0) {
                    float dropChance = (float) ((d - EDGE_NOISE_START) / (1.0 - EDGE_NOISE_START)) * EDGE_DROP_CHANCE;
                    if (rng.nextFloat() >= dropChance) {
                        shape.addLeaf(bx, ly, bz);
                    }
                } else if (d <= BUMP_MAX_DIST) {
                    if (rng.nextFloat() < BUMP_CHANCE) {
                        shape.addLeaf(bx, ly, bz);
                    }
                }
            }
        }
    }

    /**
     * Removes leaves the edge noise left without a log reachable within
     * {@link #MAX_LOG_DISTANCE} blocks through other leaves — the same reachability
     * vanilla leaf decay uses — so nothing placed here decays afterwards.
     */
    private void pruneUnsupportedLeaves(TreeShape shape) {
        Set<Long> reached = new HashSet<>();
        ArrayDeque<int[]> queue = new ArrayDeque<>(); // {x, y, z, distance}
        for (int[] p : shape.logs.values()) {
            queue.add(new int[]{p[0], p[1], p[2], 0});
        }
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[3] == MAX_LOG_DISTANCE) {
                continue;
            }
            for (int[] d : SIX_NEIGHBOURS) {
                int nx = cur[0] + d[0], ny = cur[1] + d[1], nz = cur[2] + d[2];
                if (shape.isLeaf(nx, ny, nz) && reached.add(TreeShape.key(nx, ny, nz))) {
                    queue.add(new int[]{nx, ny, nz, cur[3] + 1});
                }
            }
        }
        shape.leaves.keySet().retainAll(reached);
    }

    /**
     * Guarantees no branch wood is visible: every log neighbour that is neither log
     * nor leaf gets a leaf. Only the trunk column below the crown is left uncovered.
     * Runs after pruning; the added leaves touch a log directly, so they cannot decay.
     */
    private void wrapExposedLogs(TreeShape shape, int trunkX, int trunkZ, int crownBase) {
        for (int[] p : shape.logs.values()) {
            boolean visibleTrunk = p[0] == trunkX && p[2] == trunkZ && p[1] < crownBase;
            if (visibleTrunk) {
                continue;
            }
            for (int[] d : SIX_NEIGHBOURS) {
                int nx = p[0] + d[0], ny = p[1] + d[1], nz = p[2] + d[2];
                if (!shape.isLog(nx, ny, nz) && !shape.isLeaf(nx, ny, nz)) {
                    shape.addLeaf(nx, ny, nz);
                }
            }
        }
    }

    private boolean hasBarkContact(TreeShape shape, World world, int x, int y, int z, int logAxis) {
        for (int[] d : SIX_NEIGHBOURS) {
            if (isBarkToBark(shape, world, x, y, z, logAxis, d[0], d[1], d[2])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBarkToBark(TreeShape shape, World world, int x, int y, int z, int logAxis,
                                 int dx, int dy, int dz) {
        int nx = x + dx, ny = y + dy, nz = z + dz;
        int neighbourMeta = shape.logMeta(nx, ny, nz);
        if (neighbourMeta < 0) {
            Block nb = world.getBlock(nx, ny, nz);
            if (!nb.isWood(world, nx, ny, nz)) {
                return false;
            }
            neighbourMeta = world.getBlockMetadata(nx, ny, nz);
        }
        int neighbourAxis = (neighbourMeta >> 2) & 3;
        return isBarkFace(logAxis, dx, dy, dz) && isBarkFace(neighbourAxis, -dx, -dy, -dz);
    }

    private boolean isBarkFace(int axis, int dx, int dy, int dz) {
        return switch (axis) {
            case 0 -> dy == 0; // Y-axis: top/bottom end-grain, sides bark
            case 1 -> dx == 0; // X-axis: east/west end-grain, rest bark
            case 2 -> dz == 0; // Z-axis: north/south end-grain, rest bark
            default -> true;
        };
    }

    private int findEllipseEdge(int ddx, int ddz, double rx, double rz) {
        for (int step = 1; step <= 30; step++) {
            double nx = (ddx * step) / rx;
            double nz = (ddz * step) / rz;
            if (nx * nx + nz * nz > 1.0) {
                return step - 1;
            }
        }
        return 30;
    }

    private int logMetaForDirection(int dx, int dz) {
        if (Math.abs(dx) >= Math.abs(dz)) {
            return HAWTHORN | (1 << 2); // X-axis
        }
        return HAWTHORN | (2 << 2);                                     // Z-axis
    }
}
