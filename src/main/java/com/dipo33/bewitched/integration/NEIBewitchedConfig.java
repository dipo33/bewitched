package com.dipo33.bewitched.integration;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.Tags;
import com.dipo33.bewitched.block.BlockRegistry;

import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "codechicken.nei.api.IConfigureNEI", modid = "NotEnoughItems")
public class NEIBewitchedConfig implements IConfigureNEI {

    /**
     * Hides Bewitched crop blocks from the NotEnoughItems item list so they are not shown in NEI.
     */
    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(BlockRegistry.BELLADONNA_CROP.get()));
        API.hideItem(new ItemStack(BlockRegistry.WOLFSBANE_CROP.get()));
        API.hideItem(new ItemStack(BlockRegistry.WATER_ARTICHOKE_CROP.get()));
        API.hideItem(new ItemStack(BlockRegistry.MANDRAKE_CROP.get()));
        API.hideItem(new ItemStack(BlockRegistry.SNOW_WISP_CROP.get()));
        API.hideItem(new ItemStack(BlockRegistry.GARLIC_CROP.get()));
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
