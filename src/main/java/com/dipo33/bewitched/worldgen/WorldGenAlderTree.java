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
 * Alder: a tall, slender tree — a single straight trunk carrying a conical-to-ovoid
 * crown that is widest just below its middle. Vanilla-style generation: simple loops
 * writing straight to the world, no branches, no post-processing.
 */
public class WorldGenAlderTree extends WorldGenAbstractTree {

    private static final int ALDER = 1;

    private static final int MIN_HEIGHT = 8;
    private static final int MAX_HEIGHT = 12;
    // Leaves further than this (in 6-neighbour steps through leaves) from a log decay.
    // No leaf is ever placed past it horizontally, so the crown is decay-proof by
    // construction despite having no branch logs.
    private static final int MAX_LOG_DISTANCE = 4;

    public WorldGenAlderTree() {
        super(true);
    }

    @Override
    public boolean generate(World world, Random rng, int x, int y, int z) {
        int height = MIN_HEIGHT + rng.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);
        int crownBase = y + height / 4;
        int top = y + height - 1;

        if (y < 1 || top + 1 > 256) {
            return false;
        }
        if (!hasSpaceToGrow(world, x, y, z, crownBase, top)) {
            return false;
        }

        Block soil = world.getBlock(x, y - 1, z);
        if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
            return false;
        }
        soil.onPlantGrow(world, x, y - 1, z, x, y, z);

        int[] radii = layerRadii(top - crownBase + 1);
        for (int li = 0; li < radii.length; li++) {
            boolean endLayer = li == 0 || li == radii.length - 1;
            addLeafLayer(world, rng, x, crownBase + li, z, radii[li], endLayer);
        }

        // Trunk last, overwriting layer centers — the topmost crown layer stays log-free
        Block logBlock = BewitchedBlocks.LOG.get();
        for (int i = 0; i < height - 1; i++) {
            if (canReplace(world, x, y + i, z)) {
                setBlockAndNotifyAdequately(world, x, y + i, z, logBlock, ALDER);
            }
        }
        return true;
    }

    /**
     * Radius of each crown layer, bottom to top: an ovoid widest just below the crown
     * middle. From the top down: 1s (two on tall crowns, one otherwise), two 2s, then
     * 3s the rest of the way; the bottommost layer narrows back to 2.
     */
    private int[] layerRadii(int layers) {
        int[] radii = new int[layers];
        int ones = layers >= 8 ? 2 : 1;
        for (int i = 0; i < layers; i++) {
            int fromTop = layers - 1 - i;
            if (fromTop < ones) {
                radii[i] = 1;
            } else if (fromTop < ones + 2) {
                radii[i] = 2;
            } else {
                radii[i] = 3;
            }
        }
        radii[0] = 2;
        return radii;
    }

    private void addLeafLayer(World world, Random rng, int cx, int ly, int cz, int r, boolean endLayer) {
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                int taxicab = Math.abs(dx) + Math.abs(dz);
                if (taxicab > MAX_LOG_DISTANCE) {
                    continue; // would decay; also rounds the wide layers into octagons
                }
                boolean corner = Math.abs(dx) == r && Math.abs(dz) == r;
                if (corner && (endLayer || rng.nextInt(2) == 0)) {
                    continue;
                }
                if (r == 3 && taxicab == MAX_LOG_DISTANCE && rng.nextInt(2) == 0) {
                    continue; // ragged rim on the widest layers
                }
                if (canReplace(world, cx + dx, ly, cz + dz)) {
                    setBlockAndNotifyAdequately(world, cx + dx, ly, cz + dz, BewitchedBlocks.LEAVES.get(), ALDER);
                }
            }
        }
    }

    /**
     * Vanilla-style clearance probe: a narrow column around the trunk must contain
     * only replaceable blocks — radius 0 at the sapling, 1 along the bare trunk,
     * 2 in the crown region.
     */
    private boolean hasSpaceToGrow(World world, int x, int y, int z, int crownBase, int top) {
        for (int by = y; by <= top; by++) {
            int radius = by >= crownBase ? 2 : by == y ? 0 : 1;
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
