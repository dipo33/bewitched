package com.dipo33.bewitched;

import com.dipo33.bewitched.block.BlockRegistry;
import com.dipo33.bewitched.items.ItemRegistry;
import com.dipo33.bewitched.items.SeedDrops;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();


        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        Bewitched.LOG.info(Config.greeting);
        Bewitched.LOG.info("I am MyMod at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        SeedDrops.dropSeedsFromGrass();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
