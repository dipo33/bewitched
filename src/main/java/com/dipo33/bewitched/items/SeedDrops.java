package com.dipo33.bewitched.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class SeedDrops {
    public static void dropSeedsFromGrass() {
        MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.BELLADONNA_SEED.get()), 3);
    }
}
