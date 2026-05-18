package com.dipo33.bewitched.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.Tags;
import com.dipo33.bewitched.init.BewitchedBlocks;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "codechicken.nei.api.IConfigureNEI", modid = "NotEnoughItems")
public class NEIBewitchedConfig implements IConfigureNEI {

    /**
     * Hides Bewitched crop blocks from the NotEnoughItems item list so they are not shown in NEI.
     */
    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(BewitchedBlocks.BELLADONNA_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.WOLFSBANE_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.WATER_ARTICHOKE_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.MANDRAKE_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.SNOW_WISP_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.GARLIC_CROP.get()));
        API.hideItem(new ItemStack(BewitchedBlocks.SLAB_DOUBLE.get(), 1, OreDictionary.WILDCARD_VALUE));

        API.registerRecipeHandler(new MutationNEIHandler());
        API.registerUsageHandler(new MutationNEIHandler());
    }

    /**
     * Provides the mod's display name.
     *
     * @return the mod's display name
     */
    @Override
    public String getName() {
        return Bewitched.MOD_NAME;
    }

    /**
     * Get the mod's version identifier.
     *
     * @return the mod version string
     */
    @Override
    public String getVersion() {
        return Tags.VERSION;
    }
}
