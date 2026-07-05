package com.dipo33.bewitched.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.dipo33.bewitched.init.BewitchedBlocks;

/**
 * Rowan: a slender bare trunk carrying a dense, rounded ovoid crown. Vanilla-style
 * generation: simple loops writing straight to the world, no branches, no
 * post-processing — except a final berry pass that re-rolls crown-surface leaves
 * into berry leaves (see {@link #scatterBerryLeaves}).
 */
public class WorldGenRowanTree extends WorldGenAbstractTree {

    private static final int ROWAN = 0;

    private static final int MIN_HEIGHT = 9;
    private static final int MAX_HEIGHT = 12;
    // Leaves further than this (in 6-neighbour steps through leaves) from a log decay.
    // No leaf is ever placed past it horizontally, so the crown is decay-proof by
    // construction despite having no branch logs.
    private static final int MAX_LOG_DISTANCE = 4;
    // Chance for a crown-surface leaf to become a berry leaf
    private static final float BERRY_LEAF_CHANCE = 0.15f;

    private static final int[][] SIX_NEIGHBOURS = {
        {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}
    };

    public WorldGenRowanTree() {
        super(true);
    }

    @Override
    public boolean generate(World world, Random rng, int x, int y, int z) {
        int height = MIN_HEIGHT + rng.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);
        int crownBase = y + (height >= 11 ? 5 : 4); // bare trunk below the crown
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

        List<int[]> leafPositions = new ArrayList<>();
        int[] radii = layerRadii(top - crownBase + 1);
        for (int li = 0; li < radii.length; li++) {
            boolean endLayer = li == 0 || li == radii.length - 1;
            addLeafLayer(world, rng, x, crownBase + li, z, radii[li], endLayer, leafPositions);
        }

        // Trunk second, overwriting layer centers — the topmost crown layer stays log-free
        Block logBlock = BewitchedBlocks.LOG.get();
        for (int i = 0; i < height - 1; i++) {
            if (canReplace(world, x, y + i, z)) {
                setBlockAndNotifyAdequately(world, x, y + i, z, logBlock, ROWAN);
            }
        }

        scatterBerryLeaves(world, rng, leafPositions);
        return true;
    }

    /**
     * The two leaf variants the crown is made of. Both currently resolve to plain
     * rowan leaves; once a dedicated berry-leaf block is registered, point the berry
     * pair at it and {@link #scatterBerryLeaves} starts placing it — no other change
     * needed.
     */
    private Block plainLeaves() {
        return BewitchedBlocks.LEAVES.get();
    }

    private int plainLeavesMeta() {
        return ROWAN;
    }

    private Block berryLeaves() {
        return BewitchedBlocks.LEAVES.get();
    }

    private int berryLeavesMeta() {
        return ROWAN;
    }

    /**
     * Radius of each crown layer, bottom to top: a rounded ovoid — a single 1 on top,
     * a 2 below it, 3s the rest of the way, with the bottommost layer narrowing back
     * to 2.
     */
    private int[] layerRadii(int layers) {
        int[] radii = new int[layers];
        for (int i = 0; i < layers; i++) {
            int fromTop = layers - 1 - i;
            radii[i] = fromTop == 0 ? 1 : fromTop == 1 ? 2 : 3;
        }
        radii[0] = 2;
        return radii;
    }

    private void addLeafLayer(World world, Random rng, int cx, int ly, int cz, int r, boolean endLayer,
                              List<int[]> placed) {
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq > r * r + 1) {
                    continue; // circular layer; the +1 fudge fattens the diagonals
                }
                if (Math.abs(dx) + Math.abs(dz) > MAX_LOG_DISTANCE) {
                    continue; // would decay
                }
                // The fudge ring just past the true circle: dropped entirely on end
                // layers so they stay cleanly rounded, 50% elsewhere for a ragged rim
                boolean rim = distSq > r * r;
                if (rim && (endLayer || rng.nextInt(2) == 0)) {
                    continue;
                }
                int bx = cx + dx, bz = cz + dz;
                if (canReplace(world, bx, ly, bz)) {
                    setBlockAndNotifyAdequately(world, bx, ly, bz, plainLeaves(), plainLeavesMeta());
                    placed.add(new int[]{bx, ly, bz});
                }
            }
        }
    }

    /**
     * Re-rolls crown-surface leaves — those with at least one air neighbour — into
     * berry leaves with {@link #BERRY_LEAF_CHANCE}, mimicking how real rowan berry
     * corymbs dot the outside of the crown. While the berry pair still equals the
     * plain pair this is a visual no-op (nothing is rewritten), but the rolls and
     * surface detection are already in place.
     */
    private void scatterBerryLeaves(World world, Random rng, List<int[]> leafPositions) {
        Block plain = plainLeaves();
        Block berry = berryLeaves();
        int berryMeta = berryLeavesMeta();
        boolean berryDiffers = berry != plain || berryMeta != plainLeavesMeta();

        for (int[] p : leafPositions) {
            if (world.getBlock(p[0], p[1], p[2]) != plain) {
                continue; // overwritten since placement, e.g. by the trunk
            }
            if (!isSurfaceLeaf(world, p[0], p[1], p[2])) {
                continue;
            }
            if (rng.nextFloat() < BERRY_LEAF_CHANCE && berryDiffers) {
                setBlockAndNotifyAdequately(world, p[0], p[1], p[2], berry, berryMeta);
            }
        }
    }

    private boolean isSurfaceLeaf(World world, int x, int y, int z) {
        for (int[] d : SIX_NEIGHBOURS) {
            int nx = x + d[0], ny = y + d[1], nz = z + d[2];
            if (world.getBlock(nx, ny, nz).isAir(world, nx, ny, nz)) {
                return true;
            }
        }
        return false;
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
