package com.dipo33.bewitched.block;

import com.dipo33.bewitched.data.Position;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpreadablePlant extends BlockBush {
    private final boolean canHang;

    protected BlockSpreadablePlant(boolean canHang) {
        super(Material.plants);
        this.canHang = canHang;
    }

    public BlockSpreadablePlant withBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return this;
    }

    @Override
    public boolean canBlockStay(final World worldIn, final int x, final int y, final int z) {
        return worldIn.getBlock(x, y - 1, z).isBlockSolid(worldIn, x, y - 1, z, ForgeDirection.UP.flag)
            || (worldIn.getBlock(x, y + 1, z).isBlockSolid(worldIn, x, y + 1, z, ForgeDirection.UP.flag) && canHang);
    }

    @Override
    public void updateTick(final World world, int x, int y, int z, final Random rng) {
        if (!shouldAttemptSpread(rng)) {
            return;
        }

        if (isTooDenseNearby(world, x, y, z)) {
            return;
        }

        Position pos = new Position(x, y, z);

        // initial candidate near the original block
        Position candidate = randomNearby(pos, rng);

        // up to 4 "walk" attempts; update pos when candidate is valid
        for (int i = 0; i < 4; i++) {
            if (canPlaceAt(world, candidate)) {
                pos = candidate;
            }
            candidate = randomNearby(pos, rng);
        }

        // final placement attempt
        if (canPlaceAt(world, candidate)) {
            world.setBlock(candidate.x(), candidate.y(), candidate.z(), this, 0, 2);
        }
    }

    private boolean shouldAttemptSpread(Random rng) {
        return rng.nextInt(15) == 0;
    }

    private boolean isTooDenseNearby(World world, int x, int y, int z) {
        final int radius = 4;
        int remaining = 5;

        for (int xi = x - radius; xi <= x + radius; xi++) {
            for (int zi = z - radius; zi <= z + radius; zi++) {
                for (int yi = y - 1; yi <= y + 1; yi++) {
                    if (world.getBlock(xi, yi, zi) == this) {
                        remaining--;
                        if (remaining <= 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Position randomNearby(Position origin, Random rng) {
        // [-1, 1]
        int nx = origin.x() + rng.nextInt(3) - 1;
        // -1, 0, 0, 1 (more likely to not move)
        int ny = origin.y() + rng.nextInt(2) - rng.nextInt(2);
        // [-1, 1]
        int nz = origin.z() + rng.nextInt(3) - 1;
        return new Position(nx, ny, nz);
    }

    private boolean canPlaceAt(World world, Position pos) {
        Block block = world.getBlock(pos.x(), pos.y() - 1, pos.z());
        return world.isAirBlock(pos.x(), pos.y(), pos.z()) && (
            block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || block == Blocks.sand
        );
    }
}
