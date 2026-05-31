package com.dipo33.bewitched.worldgen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.dipo33.bewitched.init.BewitchedBlocks;

public class WorldGenHawthornTree extends WorldGenAbstractTree {

    private static final int HAWTHORN = 2;

    private static final int   MIN_TRUNK_HEIGHT  = 5;
    private static final int   MAX_TRUNK_HEIGHT  = 9;
    // Crown base (widest layer) is this many blocks above ground
    private static final int   CROWN_BASE_Y      = 3;
    // w1 (x-axis diameter) = 2h + W1_MIN_BONUS + rand[0, W1_RAND_RANGE)
    private static final int   W1_MIN_BONUS      = 1;
    private static final int   W1_RAND_RANGE     = 3;
    // w2 (z-axis diameter) = 2h + W2_MIN_BONUS + rand[0, W2_RAND_RANGE)
    private static final int   W2_MIN_BONUS      = -2;
    private static final int   W2_RAND_RANGE     = 6;
    // Each successive crown layer shrinks by TAPER_MIN..(TAPER_MIN+TAPER_RANGE-1) in radius
    private static final int   TAPER_MIN         = 1;
    private static final int   TAPER_RANGE       = 2;
    // Chance for extra aesthetic branches at non-primary heights
    private static final float BRANCH_CHANCE_1ST = 0.65f;
    private static final float BRANCH_CHANCE_2ND = 0.35f;
    // Leaves decay if no log is reachable within this many blocks (Chebyshev distance)
    private static final int   MAX_LOG_DISTANCE  = 4;

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

    public WorldGenHawthornTree() {
        super(true);
    }

    @Override
    public boolean generate(World world, Random rng, int x, int y, int z) {
        int h  = MIN_TRUNK_HEIGHT + rng.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
        int hc = (int) Math.round(h / 5.0);
        int w1 = 2 * h + W1_MIN_BONUS + rng.nextInt(W1_RAND_RANGE);
        int w2 = 2 * h + W2_MIN_BONUS + rng.nextInt(W2_RAND_RANGE);

        Block soil = world.getBlock(x, y - 1, z);
        if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
            return false;
        }
        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        Block logBlock    = BewitchedBlocks.LOG.get();
        Block leavesBlock = BewitchedBlocks.LEAVES.get();

        int crownBase   = y + CROWN_BASE_Y - 1;
        int crownTip    = y + h - 1 + hc;
        int totalLayers = h + hc - CROWN_BASE_Y + 1;

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

        for (int li = 0; li < totalLayers; li++) {
            placeLeafEllipse(world, x, crownBase + li, z, rxArr[li], rzArr[li], leavesBlock);
        }

        for (int i = 0; i < h; i++) {
            int by = y + i;
            Block b = world.getBlock(x, by, z);
            if (b.isAir(world, x, by, z) || b.isLeaves(world, x, by, z)) {
                setBlockAndNotifyAdequately(world, x, by, z, logBlock, HAWTHORN);
            }
        }

        // ── Primary branches ─────────────────────────────────────────────────────
        // Cardinals and diagonals are placed on adjacent Y levels so that no two
        // arms are ever face-adjacent at step 1, eliminating bark-to-bark contacts.
        // Both sets together provide full 8-direction coverage.
        int mainBranchY     = y + CROWN_BASE_Y;       // widest layer; always inside trunk (h >= 5)
        int mainDiagY       = mainBranchY + 1;         // one above; also inside trunk (h >= 5)
        placeDirectionalBranches(world, rng, x, mainBranchY, z, rxArr, rzArr, crownBase, totalLayers, logBlock, CARDINAL_DIRS);
        placeDirectionalBranches(world, rng, x, mainDiagY,   z, rxArr, rzArr, crownBase, totalLayers, logBlock, DIAGONAL_DIRS);

        int secondBranchY    = Math.min(y + h - 1, crownTip - MAX_LOG_DISTANCE);
        int secondDiagY      = -1; // sentinel: no second level
        if (secondBranchY > mainDiagY) {
            secondDiagY = secondBranchY + 1 <= y + h - 1 ? secondBranchY + 1 : secondBranchY - 1;
            placeDirectionalBranches(world, rng, x, secondBranchY, z, rxArr, rzArr, crownBase, totalLayers, logBlock, CARDINAL_DIRS);
            placeDirectionalBranches(world, rng, x, secondDiagY,   z, rxArr, rzArr, crownBase, totalLayers, logBlock, DIAGONAL_DIRS);
        }

        // ── Aesthetic branches ────────────────────────────────────────────────────
        for (int i = 0; i < h; i++) {
            int by = y + i;
            if (by == mainBranchY || by == mainDiagY
                    || by == secondBranchY || by == secondDiagY) continue;
            int li = by - crownBase;
            if (li < 0 || li >= totalLayers) continue;

            if (rng.nextFloat() < BRANCH_CHANCE_1ST) {
                placeShortBranch(world, rng, x, by, z, rxArr[li], rzArr[li], logBlock);
            }
            if (rng.nextFloat() < BRANCH_CHANCE_2ND) {
                placeShortBranch(world, rng, x, by, z, rxArr[li], rzArr[li], logBlock);
            }
        }

        return true;
    }

    private void placeDirectionalBranches(World world, Random rng, int cx, int by, int cz,
                                           double[] rxArr, double[] rzArr,
                                           int crownBase, int totalLayers, Block logBlock,
                                           int[][] dirs) {
        int li = by - crownBase;
        if (li < 0 || li >= totalLayers) return;
        double rx = rxArr[li];
        double rz = rzArr[li];

        for (int[] dir : dirs) {
            int ddx = dir[0], ddz = dir[1];
            int ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
            int minLen = ellipseEdge - MAX_LOG_DISTANCE;
            if (minLen <= 0) continue;

            int branchLen = Math.min(minLen + rng.nextInt(2), ellipseEdge - 1);
            int logMeta   = logMetaForDirection(ddx, ddz);

            for (int step = 1; step <= branchLen; step++) {
                int bx = cx + ddx * step;
                int bz = cz + ddz * step;
                Block b = world.getBlock(bx, by, bz);
                if (b.isAir(world, bx, by, bz) || b.isLeaves(world, bx, by, bz)) {
                    setBlockAndNotifyAdequately(world, bx, by, bz, logBlock, logMeta);
                }
            }
        }
    }

    private void placeShortBranch(World world, Random rng, int cx, int by, int cz,
                                   double rx, double rz, Block logBlock) {
        int[] dir     = ALL_8_DIRS[rng.nextInt(ALL_8_DIRS.length)];
        int   ddx     = dir[0], ddz = dir[1];
        int   logMeta = logMetaForDirection(ddx, ddz);
        int   logAxis = (logMeta >> 2) & 3;
        int   ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
        int   maxLen  = Math.min(ellipseEdge - 1, MAX_LOG_DISTANCE + 1);
        if (maxLen <= 0) return;

        int branchLen = 1 + rng.nextInt(maxLen);
        for (int step = 1; step <= branchLen; step++) {
            int bx = cx + ddx * step;
            int bz = cz + ddz * step;
            double nx = (bx - cx) / rx;
            double nz = (bz - cz) / rz;
            if (nx * nx + nz * nz > 1.0) break;
            if (hasBarkContact(world, bx, by, bz, logAxis)) break;
            Block b = world.getBlock(bx, by, bz);
            if (b.isAir(world, bx, by, bz) || b.isLeaves(world, bx, by, bz)) {
                setBlockAndNotifyAdequately(world, bx, by, bz, logBlock, logMeta);
            }
        }
    }

    private boolean hasBarkContact(World world, int x, int y, int z, int logAxis) {
        return isBarkToBark(world, x, y, z, logAxis,  1,  0,  0)
            || isBarkToBark(world, x, y, z, logAxis, -1,  0,  0)
            || isBarkToBark(world, x, y, z, logAxis,  0,  0,  1)
            || isBarkToBark(world, x, y, z, logAxis,  0,  0, -1)
            || isBarkToBark(world, x, y, z, logAxis,  0,  1,  0)
            || isBarkToBark(world, x, y, z, logAxis,  0, -1,  0);
    }

    private boolean isBarkToBark(World world, int x, int y, int z, int logAxis, int dx, int dy, int dz) {
        Block nb = world.getBlock(x + dx, y + dy, z + dz);
        if (!nb.isWood(world, x + dx, y + dy, z + dz)) return false;
        int neighbourAxis = (world.getBlockMetadata(x + dx, y + dy, z + dz) >> 2) & 3;
        return isBarkFace(logAxis, dx, dy, dz) && isBarkFace(neighbourAxis, -dx, -dy, -dz);
    }

    private boolean isBarkFace(int axis, int dx, int dy, int dz) {
        return switch (axis) {
            case 0  -> dy == 0; // Y-axis: top/bottom end-grain, sides bark
            case 1  -> dx == 0; // X-axis: east/west end-grain, rest bark
            case 2  -> dz == 0; // Z-axis: north/south end-grain, rest bark
            default -> true;
        };
    }

    private void placeLeafEllipse(World world, int cx, int y, int cz, double rx, double rz, Block leavesBlock) {
        int minX = (int) Math.floor(cx - rx);
        int maxX = (int) Math.ceil(cx + rx);
        int minZ = (int) Math.floor(cz - rz);
        int maxZ = (int) Math.ceil(cz + rz);

        for (int bx = minX; bx <= maxX; bx++) {
            for (int bz = minZ; bz <= maxZ; bz++) {
                double dx = (bx - cx) / rx;
                double dz = (bz - cz) / rz;
                if (dx * dx + dz * dz <= 1.0) {
                    Block b = world.getBlock(bx, y, bz);
                    if (b.isAir(world, bx, y, bz) || b.isLeaves(world, bx, y, bz)) {
                        setBlockAndNotifyAdequately(world, bx, y, bz, leavesBlock, HAWTHORN);
                    }
                }
            }
        }
    }

    private int findEllipseEdge(int ddx, int ddz, double rx, double rz) {
        for (int step = 1; step <= 30; step++) {
            double nx = (ddx * step) / rx;
            double nz = (ddz * step) / rz;
            if (nx * nx + nz * nz > 1.0) return step - 1;
        }
        return 30;
    }

    private int logMetaForDirection(int dx, int dz) {
        if (Math.abs(dx) >= Math.abs(dz)) return HAWTHORN | (1 << 2); // X-axis
        return HAWTHORN | (2 << 2);                                     // Z-axis
    }
}
