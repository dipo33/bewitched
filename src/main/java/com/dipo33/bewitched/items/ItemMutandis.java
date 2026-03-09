package com.dipo33.bewitched.items;

import com.dipo33.bewitched.api.mutation.MutationPoolType;
import com.dipo33.bewitched.api.mutation.MutationRegistry;
import com.dipo33.bewitched.client.effect.EffectPlayer;
import com.dipo33.bewitched.client.effect.Effects;
import com.dipo33.bewitched.api.mutation.Mutation;
import com.dipo33.bewitched.network.BwNetwork;
import com.dipo33.bewitched.network.message.UpdateFlowerPotMsg;
import com.dipo33.bewitched.sound.Sounds;

import cpw.mods.fml.common.network.NetworkRegistry;
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

    private static final MutationPoolType[] WORLD_POOLS = {
        MutationPoolType.MUTANDIS,
        MutationPoolType.MUTANDIS_EXTREMIS_GRASS,
        MutationPoolType.MUTANDIS_EXTREMIS
    };

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }

        if (this.applyMutandis(stack, world, x, y, z)) {
            if (!world.isRemote) {
                if (this == ItemRegistry.MUTANDIS_EXTREMIS.get()) {
                    EffectPlayer.playFX(Effects.MUTANDIS_EXTREMIS_FX, world, x, y, z, 32);
                } else {
                    EffectPlayer.playFX(Effects.MUTANDIS_FX, world, x, y, z, 32);
                }

                world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Sounds.MUTANDIS, 1.0F, 0.60F);
            }

            return true;
        }

        return false;
    }

    private static boolean applyMutationToWorldBlock(
        ItemStack catalyst, World world, int x, int y, int z, Mutation.Output mutationOutput
    ) {
        if (!world.isRemote) {
            int meta = mutationOutput.placement().placementMeta(world, x, y, z);
            world.setBlock(x, y, z, mutationOutput.block(), meta, 2);
            catalyst.stackSize--;
        }
        return true;
    }

    private static boolean applyMutationToFlowerPot(
        ItemStack catalyst, World world, TileEntityFlowerPot pot, Mutation.Output mutationOutput
    ) {
        if (!world.isRemote) {
            int meta = mutationOutput.placement().defaultPlacementMeta();
            pot.func_145964_a(Item.getItemFromBlock(mutationOutput.block()), meta);
            pot.markDirty();

            if (!world.setBlockMetadataWithNotify(pot.xCoord, pot.yCoord, pot.zCoord, meta, 2)) {
                world.markBlockForUpdate(pot.xCoord, pot.yCoord, pot.zCoord);
            }

            BwNetwork.NET.sendToAllAround(
                new UpdateFlowerPotMsg(pot.xCoord, pot.yCoord, pot.zCoord, pot.getFlowerPotItem(), pot.getFlowerPotData()),
                new NetworkRegistry.TargetPoint(world.provider.dimensionId, pot.xCoord + 0.5, pot.yCoord + 0.5, pot.zCoord + 0.5, 128)
            );

            catalyst.stackSize--;
        }
        return true;
    }

    private boolean applyMutandis(ItemStack catalyst, World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block == Blocks.flower_pot) {
            if (MutationRegistry.isCatalyst(MutationPoolType.FLOWER_POT, catalyst)) {
                return applyMutandisOnFlowerPot(catalyst, world, x, y, z);
            }
            return false;
        }

        var mutation = pickMutation(catalyst, block, meta, world.rand);
        return mutation != null && applyMutationToWorldBlock(catalyst, world, x, y, z, mutation.output());
    }

    private static boolean applyMutandisOnFlowerPot(ItemStack catalyst, World world, int x, int y, int z) {
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
        var mutation = Mutation.mutate(MutationPoolType.FLOWER_POT, contained, meta, world.rand);
        return mutation != null && applyMutationToFlowerPot(catalyst, world, pot, mutation.output());
    }

    private Mutation pickMutation(ItemStack catalyst, Block block, int meta, Random rng) {
        for (var pool : WORLD_POOLS) {
            if (MutationRegistry.isCatalyst(pool, catalyst)) {
                Mutation mutation = Mutation.mutate(pool, block, meta, rng);
                if (mutation != null) {
                    return mutation;
                }
            }
        }
        return null;
    }
}
