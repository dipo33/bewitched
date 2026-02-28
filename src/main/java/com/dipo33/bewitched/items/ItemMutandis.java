package com.dipo33.bewitched.items;

import com.dipo33.bewitched.data.Pair;
import com.dipo33.bewitched.network.BwNetwork;
import com.dipo33.bewitched.network.message.MutandisFXMsg;

import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMutandis extends Item {

    private static final List<Pair<Block, Integer>> BLOCKS = new ArrayList<>(
        Arrays.asList(
            new Pair<>(Blocks.tallgrass, 1),
            new Pair<>(Blocks.tallgrass, 2),
            new Pair<>(Blocks.brown_mushroom, 0),
            new Pair<>(Blocks.red_mushroom, 0),
            new Pair<>(Blocks.red_flower, 0),
            new Pair<>(Blocks.yellow_flower, 0),
            new Pair<>(Blocks.sapling, 0),
            new Pair<>(Blocks.sapling, 1),
            new Pair<>(Blocks.sapling, 2),
            new Pair<>(Blocks.sapling, 3),
            new Pair<>(Blocks.sapling, 4),
            new Pair<>(Blocks.sapling, 5),
            new Pair<>(Blocks.waterlily, 0)
        ));

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }

        if (applyMutandis(stack, world, x, y, z)) {
            if (!world.isRemote) {
                ItemMutandis.playMutandisFX(world, x, y, z);
            }

            return true;
        }

        return false;
    }

    public static boolean applyMutandis(ItemStack stack, World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        var current = new Pair<>(block, metadata);

        final int currentIndex = BLOCKS.indexOf(current);
        if (currentIndex < 0) {
            return false;
        }

        // Pick a random index in [0, n-2], then shift if it crosses currentIndex.
        int randomIndex = world.rand.nextInt(BLOCKS.size() - 1);
        if (randomIndex >= currentIndex) {
            randomIndex++;
        }

        final Pair<Block, Integer> result = BLOCKS.get(randomIndex);
        if (!world.isRemote) {
            world.setBlock(x, y, z, result.first(), result.second(), 2);
            stack.stackSize--;
        }

        return true;
    }

    public static void playMutandisFX(World world, double x, double y, double z) {
        NetworkRegistry.TargetPoint tp = new NetworkRegistry.TargetPoint(
            world.provider.dimensionId,
            x + 0.5, y + 0.5, z + 0.5,
            32
        );
        BwNetwork.NET.sendToAllAround(new MutandisFXMsg(world, x, y, z), tp);
    }
}
