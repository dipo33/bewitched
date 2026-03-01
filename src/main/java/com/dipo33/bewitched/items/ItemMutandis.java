package com.dipo33.bewitched.items;

import com.dipo33.bewitched.client.effect.EffectPlayer;
import com.dipo33.bewitched.client.effect.Effects;
import com.dipo33.bewitched.data.Pair;
import com.dipo33.bewitched.network.BwNetwork;
import com.dipo33.bewitched.network.message.UpdateFlowerPotMsg;
import com.dipo33.bewitched.sound.Sounds;

import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
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

    private static final List<Pair<Block, Integer>> ADDITIONAL_SOURCE_BLOCKS = new ArrayList<>(
        Arrays.asList(
            new Pair<>(Blocks.sapling, 8),
            new Pair<>(Blocks.sapling, 9),
            new Pair<>(Blocks.sapling, 10),
            new Pair<>(Blocks.sapling, 11),
            new Pair<>(Blocks.sapling, 12),
            new Pair<>(Blocks.sapling, 13)
        ));

    private static final List<Pair<Block, Integer>> FLOWER_POT_BLOCKS = new ArrayList<>(
        Arrays.asList(
            new Pair<>(Blocks.yellow_flower, 0),
            new Pair<>(Blocks.red_flower, 0),
            new Pair<>(Blocks.cactus, 0),
            new Pair<>(Blocks.brown_mushroom, 0),
            new Pair<>(Blocks.red_mushroom, 0),
            new Pair<>(Blocks.sapling, 0),
            new Pair<>(Blocks.sapling, 1),
            new Pair<>(Blocks.sapling, 2),
            new Pair<>(Blocks.sapling, 3),
            new Pair<>(Blocks.sapling, 4),
            new Pair<>(Blocks.sapling, 5),
            new Pair<>(Blocks.deadbush, 0),
            new Pair<>(Blocks.tallgrass, 2)
        ));

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }

        if (applyMutandis(stack, world, x, y, z)) {
            if (!world.isRemote) {
                EffectPlayer.playFX(Effects.MUTANDIS_FX, world, x, y, z, 32);
                world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Sounds.MUTANDIS, 1.0F, 0.60F);
            }

            return true;
        }

        return false;
    }

    private static Pair<Block, Integer> getMutationResult(
        Random random, Pair<Block, Integer> currentBlock, List<Pair<Block, Integer>> mutationPool, List<Pair<Block, Integer>> additionalSources
    ) {
        int currentIndex = mutationPool.indexOf(currentBlock);

        if (currentIndex < 0) {
            boolean isAdditional = additionalSources != null && additionalSources.contains(currentBlock);
            if (isAdditional) {
                return mutationPool.get(random.nextInt(mutationPool.size()));
            }

            return null;
        }

        // Pick a random index in [0, n-2], then shift if it crosses currentIndex.
        int randomIndex = random.nextInt(mutationPool.size() - 1);
        if (randomIndex >= currentIndex) {
            randomIndex++;
        }

        return mutationPool.get(randomIndex);
    }

    private static boolean applyMutationToWorldBlock(
        ItemStack stack, World world, int x, int y, int z, Pair<Block, Integer> mutationResult
    ) {
        if (mutationResult == null) {
            return false;
        }

        if (!world.isRemote) {
            world.setBlock(x, y, z, mutationResult.first(), mutationResult.second(), 2);
            stack.stackSize--;
        }
        return true;
    }

    private static boolean applyMutationToFlowerPot(
        ItemStack stack, World world, TileEntityFlowerPot pot, Pair<Block, Integer> result
    ) {
        if (result == null) {
            return false;
        }

        if (!world.isRemote) {
            pot.func_145964_a(Item.getItemFromBlock(result.first()), result.second());
            pot.markDirty();

            if (!world.setBlockMetadataWithNotify(pot.xCoord, pot.yCoord, pot.zCoord, result.second(), 2)) {
                world.markBlockForUpdate(pot.xCoord, pot.yCoord, pot.zCoord);
            }

            BwNetwork.NET.sendToAllAround(
                new UpdateFlowerPotMsg(pot.xCoord, pot.yCoord, pot.zCoord, pot.getFlowerPotItem(), pot.getFlowerPotData()),
                new NetworkRegistry.TargetPoint(world.provider.dimensionId, pot.xCoord + 0.5, pot.yCoord + 0.5, pot.zCoord + 0.5, 128)
            );

            stack.stackSize--;
        }
        return true;
    }

    private static boolean applyMutandis(ItemStack stack, World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.flower_pot) {
            return mutateFlowerPot(stack, world, x, y, z);
        }

        int metadata = world.getBlockMetadata(x, y, z);
        var mutationResult = getMutationResult(world.rand, new Pair<>(block, metadata), BLOCKS, ADDITIONAL_SOURCE_BLOCKS);
        return applyMutationToWorldBlock(stack, world, x, y, z, mutationResult);
    }

    private static boolean mutateFlowerPot(ItemStack stack, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntityFlowerPot pot)) {
            return false;
        }

        Item potItem = pot.getFlowerPotItem();
        if (!(potItem instanceof ItemBlock itemBlock)) {
            return false;
        }

        Block contained = Block.getBlockFromItem(itemBlock);
        int meta = pot.getFlowerPotData();
        Pair<Block, Integer> mutationResult = getMutationResult(world.rand, new Pair<>(contained, meta), FLOWER_POT_BLOCKS, null);

        return applyMutationToFlowerPot(stack, world, pot, mutationResult);
    }
}
