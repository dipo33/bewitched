package com.dipo33.bewitched;

import com.dipo33.bewitched.block.BlockRegistry;
import com.dipo33.bewitched.config.Config;
import com.dipo33.bewitched.items.ItemRegistry;
import com.dipo33.bewitched.items.SeedDrops;
import com.dipo33.bewitched.items.mutandis.MutandisMutationRegistry;
import com.dipo33.bewitched.network.BwNetwork;
import com.dipo33.bewitched.network.message.EffectPlayMsg;
import com.dipo33.bewitched.network.message.UpdateFlowerPotMsg;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    /**
     * Handle the mod's pre-initialization lifecycle event.
     * <p>
     * Delegates pre-initialization work (configuration loading and early registration) to the sided proxy.
     *
     * @param event
     *     the Forge pre-initialization event containing mod configuration and environment data
     */
    public void preInit(FMLPreInitializationEvent event) {
        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
        BwNetwork.register();
        this.registerClientMessages();

        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
    }

    protected void registerClientMessages() {
        BwNetwork.registerClientMessage(EffectPlayMsg.SafeHandler.class, EffectPlayMsg.class);
        BwNetwork.registerClientMessage(UpdateFlowerPotMsg.SafeHandler.class, UpdateFlowerPotMsg.class);
    }

    /**
     * Handle the mod's initialization lifecycle event.
     * <p>
     * Delegates initialization work (recipe registration and data structure building) to the sided proxy.
     *
     * @param event
     *     the Forge initialization event containing mod configuration and environment data
     */
    public void init(FMLInitializationEvent event) {
        SeedDrops.dropSeedsFromGrass();
        MutandisMutationRegistry.registerMutations();
    }

    /**
     * Handle the mod's post-initialization lifecycle event.
     * <p>
     * Delegates post-initialization work (interaction with other mods) to the sided proxy.
     *
     * @param event
     *     the Forge post-initialization event containing mod configuration and environment data
     */
    public void postInit(FMLPostInitializationEvent event) {
    }

    /**
     * Handle the mod's server-starting lifecycle event.
     * <p>
     * Delegates server-side setup (such as command registration) to the sided proxy.
     *
     * @param event
     *     the Forge server starting event providing access to command registration and server context
     */
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
