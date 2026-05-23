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

    // Hawthorn species index = 2 (matches BlockBewitchedLog.VARIANTS and BlockBewitchedLeaves.VARIANTS)
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
    // Chance for extra aesthetic branches at non-primary heights
    private static final float BRANCH_CHANCE_1ST = 0.65f;
    private static final float BRANCH_CHANCE_2ND = 0.35f;
    // Leaves decay if no log is reachable within this many blocks (Chebyshev distance)
    private static final int   MAX_LOG_DISTANCE  = 4;

    // All 8 compass directions used for radial branch sweeps
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
        int hc = (int) Math.round(h / 5.0); // crown extends hc blocks above trunk top
        int w1 = 2 * h + W1_MIN_BONUS + rng.nextInt(W1_RAND_RANGE);
        int w2 = 2 * h + W2_MIN_BONUS + rng.nextInt(W2_RAND_RANGE);

        Block soil = world.getBlock(x, y - 1, z);
        if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
            return false;
        }
        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        Block logBlock    = BewitchedBlocks.LOG.get();
        Block leavesBlock = BewitchedBlocks.LEAVES.get();

        // Crown spans:
        //   crownBase (y + CROWN_BASE_Y - 1) → sub-base
        //   crownBase + 1                     → base (widest)
        //   ...
        //   y + h - 1 + hc                    → tip
        int crownBase   = y + CROWN_BASE_Y - 1;
        int crownTip    = y + h - 1 + hc;
        int totalLayers = h + hc - CROWN_BASE_Y + 1;

        double[] rxArr = new double[totalLayers];
        double[] rzArr = new double[totalLayers];
        rxArr[0] = (w1 - 2) / 2.0; // sub-base: 1 narrower on each side than base
        rzArr[0] = (w2 - 2) / 2.0;
        rxArr[1] = w1 / 2.0;        // base (widest)
        rzArr[1] = w2 / 2.0;
        for (int i = 2; i < totalLayers; i++) {
            rxArr[i] = Math.max(0.5, rxArr[i - 1] - (1 + rng.nextInt(2)) / 2.0);
            rzArr[i] = Math.max(0.5, rzArr[i - 1] - (1 + rng.nextInt(2)) / 2.0);
        }

        // Place crown leaves
        for (int li = 0; li < totalLayers; li++) {
            placeLeafEllipse(world, x, crownBase + li, z, rxArr[li], rzArr[li], leavesBlock);
        }

        // Place trunk (overwrites any leaves at the center column)
        for (int i = 0; i < h; i++) {
            int by = y + i;
            Block b = world.getBlock(x, by, z);
            if (b.isAir(world, x, by, z) || b.isLeaves(world, x, by, z)) {
                setBlockAndNotifyAdequately(world, x, by, z, logBlock, HAWTHORN);
            }
        }

        // ── Primary branches: guaranteed full-coverage radial arms ──────────────
        //
        // A branch tip placed at distance (ellipseEdge - MAX_LOG_DISTANCE) from centre
        // is exactly MAX_LOG_DISTANCE blocks from the outermost leaf in its direction.
        // Together the 8 compass spokes cover every leaf at that height, and their
        // vertical ±MAX_LOG_DISTANCE reach covers the rest of the crown.
        //
        // One primary level (at the widest layer, y+CROWN_BASE_Y) is enough for most
        // tree heights.  A second is added if the crown tip would be more than
        // MAX_LOG_DISTANCE above the first level's reach.
        int mainBranchY = y + CROWN_BASE_Y; // = crownBase+1, widest layer, always inside trunk
        placeFullCoverageBranches(world, rng, x, mainBranchY, z,
                                   rxArr, rzArr, crownBase, totalLayers, logBlock);

        int secondBranchY = Math.min(y + h - 1, crownTip - MAX_LOG_DISTANCE);
        if (secondBranchY > mainBranchY + 1) {
            placeFullCoverageBranches(world, rng, x, secondBranchY, z,
                                       rxArr, rzArr, crownBase, totalLayers, logBlock);
        }

        // ── Aesthetic branches: shorter arms at other trunk heights ───────────
        // These add visual variety but are not required for decay prevention.
        for (int i = 0; i < h; i++) {
            int by = y + i;
            if (by == mainBranchY || by == secondBranchY) continue;
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

    /**
     * Places radial branches in all 8 compass directions at the given height.
     * Each branch grows from the trunk outward to exactly (ellipseEdge - MAX_LOG_DISTANCE)
     * steps, guaranteeing the outermost leaf in every direction is within MAX_LOG_DISTANCE
     * of the branch tip.  A ±1 random extension is added for visual variety.
     */
    private void placeFullCoverageBranches(World world, Random rng, int cx, int by, int cz,
                                            double[] rxArr, double[] rzArr,
                                            int crownBase, int totalLayers, Block logBlock) {
        int li = by - crownBase;
        if (li < 0 || li >= totalLayers) return;
        double rx = rxArr[li];
        double rz = rzArr[li];

        for (int[] dir : ALL_8_DIRS) {
            int ddx = dir[0], ddz = dir[1];
            int ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
            int minLen = ellipseEdge - MAX_LOG_DISTANCE;
            if (minLen <= 0) continue; // trunk already covers this direction

            // Small random extension (0 or 1 extra block), capped before the ellipse boundary
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

    /**
     * Places a single short branch from the trunk in a random direction.
     * Length is capped at MAX_LOG_DISTANCE+1 so these never reach the outer fringe
     * — their purpose is purely visual, not structural.
     */
    private void placeShortBranch(World world, Random rng, int cx, int by, int cz,
                                   double rx, double rz, Block logBlock) {
        int[] dir       = ALL_8_DIRS[rng.nextInt(ALL_8_DIRS.length)];
        int   ddx       = dir[0], ddz = dir[1];
        int   logMeta   = logMetaForDirection(ddx, ddz);
        int   ellipseEdge = findEllipseEdge(ddx, ddz, rx, rz);
        int   maxLen    = Math.min(ellipseEdge - 1, MAX_LOG_DISTANCE + 1);
        if (maxLen <= 0) return;

        int branchLen = 1 + rng.nextInt(maxLen);
        for (int step = 1; step <= branchLen; step++) {
            int bx = cx + ddx * step;
            int bz = cz + ddz * step;
            double nx = (bx - cx) / rx;
            double nz = (bz - cz) / rz;
            if (nx * nx + nz * nz > 1.0) break;
            Block b = world.getBlock(bx, by, bz);
            if (b.isAir(world, bx, by, bz) || b.isLeaves(world, bx, by, bz)) {
                setBlockAndNotifyAdequately(world, bx, by, bz, logBlock, logMeta);
            }
        }
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

    /**
     * Returns how many integer steps fit inside the ellipse in direction (ddx, ddz).
     * The step at which nx*nx + nz*nz first exceeds 1.0 is the first step outside;
     * we return one less than that.
     */
    private int findEllipseEdge(int ddx, int ddz, double rx, double rz) {
        for (int step = 1; step <= 30; step++) {
            double nx = (ddx * step) / rx;
            double nz = (ddz * step) / rz;
            if (nx * nx + nz * nz > 1.0) return step - 1;
        }
        return 30;
    }

    private int logMetaForDirection(int dx, int dz) {
        // BlockLog meta: bits 0-1 = species, bits 2-3 = axis (0=Y, 1=X, 2=Z)
        if (dx != 0 && dz == 0) return HAWTHORN | (1 << 2); // X-axis log
        if (dz != 0 && dx == 0) return HAWTHORN | (2 << 2); // Z-axis log
        return HAWTHORN; // diagonal: use Y-axis
    }
}
