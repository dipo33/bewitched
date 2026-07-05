package com.dipo33.bewitched.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.dipo33.bewitched.init.BewitchedBlocks;

/**
 * Hawthorn: a short trunk that forks low into several spreading branches, capped by
 * overlapping elliptical leaf lobes that merge into one wide, squashed dome — unlike
 * alder/rowan's single straight trunk and no-branch crowns. Vanilla-style generation:
 * simple loops writing straight to the world, no post-processing.
 */
public class WorldGenHawthornTree extends WorldGenAbstractTree {

    private static final int HAWTHORN = 2;

    private static final int FORK_OFFSET = 3; // bare trunk logs below the fork
    private static final int MIN_LEADER_LEN = 2;
    private static final int MAX_LEADER_LEN = 3;
    private static final int MIN_BRANCHES = 4;
    private static final int MAX_BRANCHES = 6;
    private static final int MIN_BRANCH_LEN = 2;
    private static final int MAX_BRANCH_LEN = 3;

    private static final int LOBE_RADIUS_H_MIN = 2;
    private static final int LOBE_RADIUS_H_MAX = 3;
    private static final int LOBE_RADIUS_V = 2;
    // Leaves further than this (in 6-neighbour steps through leaves) from a log decay.
    // No leaf is ever placed past it relative to its own lobe's center log, so the
    // crown is decay-proof by construction.
    private static final int MAX_LOG_DISTANCE = 4;
    private static final double LOBE_FUDGE = 1.3;

    private static final int CLEARANCE_RADIUS_CROWN = 6;

    private static final int[][] BRANCH_DIRECTIONS = {
        {0, -1}, {0, 1}, {1, 0}, {-1, 0}, {1, -1}, {-1, -1}, {1, 1}, {-1, 1}
    };

    public WorldGenHawthornTree() {
        super(true);
    }

    @Override
    public boolean generate(World world, Random rng, int x, int y, int z) {
        int forkY = y + FORK_OFFSET;
        int leaderLen = MIN_LEADER_LEN + rng.nextInt(MAX_LEADER_LEN - MIN_LEADER_LEN + 1);
        int trunkTop = forkY + leaderLen;

        int[] branchDirs = pickBranchDirections(rng);
        int[] branchLens = new int[branchDirs.length];
        int[][] branchTips = new int[branchDirs.length][3];
        for (int i = 0; i < branchDirs.length; i++) {
            int len = MIN_BRANCH_LEN + rng.nextInt(MAX_BRANCH_LEN - MIN_BRANCH_LEN + 1);
            branchLens[i] = len;
            int[] dir = BRANCH_DIRECTIONS[branchDirs[i]];
            branchTips[i] = new int[]{x + dir[0] * len, forkY + len, z + dir[1] * len};
        }

        int top = trunkTop + LOBE_RADIUS_V;
        if (y < 1 || top + 1 > 256) {
            return false;
        }
        if (!hasSpaceToGrow(world, x, y, z, forkY, top)) {
            return false;
        }

        Block soil = world.getBlock(x, y - 1, z);
        if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
            return false;
        }
        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        Block leaves = BewitchedBlocks.LEAVES.get();

        // Lobes first: the low fork skirt, every branch tip, then the top cap
        addLobe(world, rng, x, forkY, z, leaves);
        for (int[] tip : branchTips) {
            addLobe(world, rng, tip[0], tip[1], tip[2], leaves);
        }
        addLobe(world, rng, x, trunkTop, z, leaves);

        // Trunk logs (bare trunk + central leader) last, overwriting lobe centers
        Block logBlock = BewitchedBlocks.LOG.get();
        for (int by = y; by <= trunkTop; by++) {
            if (canReplace(world, x, by, z)) {
                setBlockAndNotifyAdequately(world, x, by, z, logBlock, HAWTHORN);
            }
        }

        // Branch logs
        for (int i = 0; i < branchDirs.length; i++) {
            int[] dir = BRANCH_DIRECTIONS[branchDirs[i]];
            for (int step = 1; step <= branchLens[i]; step++) {
                int bx = x + dir[0] * step, by = forkY + step, bz = z + dir[1] * step;
                if (canReplace(world, bx, by, bz)) {
                    setBlockAndNotifyAdequately(world, bx, by, bz, logBlock, HAWTHORN);
                }
            }
        }

        return true;
    }

    /**
     * Picks a random subset (4-6) of the 8 horizontal/diagonal directions, each used
     * at most once, via a Fisher-Yates shuffle of the direction indices.
     */
    private int[] pickBranchDirections(Random rng) {
        int[] indices = {0, 1, 2, 3, 4, 5, 6, 7};
        for (int i = indices.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = indices[i];
            indices[i] = indices[j];
            indices[j] = tmp;
        }
        int count = MIN_BRANCHES + rng.nextInt(MAX_BRANCHES - MIN_BRANCHES + 1);
        int[] chosen = new int[count];
        System.arraycopy(indices, 0, chosen, 0, count);
        return chosen;
    }

    /**
     * Stamps one elliptical leaf lobe centered on a log (fork, branch tip, or leader
     * top). Horizontal radii are rolled independently per lobe so the footprint reads
     * as an oval rather than a perfect circle; the vertical radius is fixed and
     * shorter, squashing the whole canopy wide rather than round.
     */
    private void addLobe(World world, Random rng, int cx, int cy, int cz, Block leaves) {
        int rx = LOBE_RADIUS_H_MIN + rng.nextInt(LOBE_RADIUS_H_MAX - LOBE_RADIUS_H_MIN + 1);
        int rz = LOBE_RADIUS_H_MIN + rng.nextInt(LOBE_RADIUS_H_MAX - LOBE_RADIUS_H_MIN + 1);
        int ry = LOBE_RADIUS_V;

        for (int dx = -rx; dx <= rx; dx++) {
            for (int dy = -ry; dy <= ry; dy++) {
                for (int dz = -rz; dz <= rz; dz++) {
                    if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > MAX_LOG_DISTANCE) {
                        continue; // would decay; also trims the ellipsoid's far corners
                    }
                    double norm = (double) (dx * dx) / (rx * rx) + (double) (dz * dz) / (rz * rz)
                        + (double) (dy * dy) / (ry * ry);
                    if (norm > LOBE_FUDGE) {
                        continue;
                    }
                    boolean rim = norm > 1.0;
                    if (rim && rng.nextInt(2) == 0) {
                        continue; // ragged edge dither
                    }
                    int bx = cx + dx, by = cy + dy, bz = cz + dz;
                    if (canReplace(world, bx, by, bz)) {
                        setBlockAndNotifyAdequately(world, bx, by, bz, leaves, HAWTHORN);
                    }
                }
            }
        }
    }

    /**
     * Vanilla-style clearance probe: a narrow column around the trunk must contain
     * only replaceable blocks — radius 0 at the sapling, 1 along the bare trunk,
     * wide enough above the fork to cover the longest branch plus its lobe's reach.
     */
    private boolean hasSpaceToGrow(World world, int x, int y, int z, int forkY, int top) {
        for (int by = y; by <= top; by++) {
            int radius = by >= forkY ? CLEARANCE_RADIUS_CROWN : by == y ? 0 : 1;
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
}
