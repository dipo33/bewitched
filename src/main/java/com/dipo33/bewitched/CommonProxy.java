package com.dipo33.bewitched;

import com.dipo33.bewitched.config.Config;
import com.dipo33.bewitched.init.BewitchedBlocks;
import com.dipo33.bewitched.init.BewitchedEntities;
import com.dipo33.bewitched.init.BewitchedItemArmorMaterials;
import com.dipo33.bewitched.init.BewitchedItems;
import com.dipo33.bewitched.init.BewitchedMutations;
import com.dipo33.bewitched.init.BewitchedRecipes;
import com.dipo33.bewitched.items.SeedDrops;
import com.dipo33.bewitched.network.BewitchedNetwork;
import com.dipo33.bewitched.network.message.EffectPlayMsg;
import com.dipo33.bewitched.network.message.UpdateFlowerPotMsg;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
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
        BewitchedBlocks.registerBlocks();
        BewitchedItemArmorMaterials.addArmorMaterials();
        BewitchedItems.registerItems();
        BewitchedEntities.registerEntities();
        BewitchedNetwork.register();
        this.registerClientMessages();

        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
    }

    /**
     * Registers client-targeted network messages using side-safe placeholder handlers.
     *
     * <p>Called on the server to avoid loading client-only handler classes. The registered
     * handlers must be safe to load on server and are never expected to execute on the server.</p>
     */
    protected void registerClientMessages() {
        BewitchedNetwork.registerClientMessage(EffectPlayMsg.SafeHandler.class, EffectPlayMsg.class);
        BewitchedNetwork.registerClientMessage(UpdateFlowerPotMsg.SafeHandler.class, UpdateFlowerPotMsg.class);
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
        BewitchedRecipes.init();
        BewitchedMutations.init();
        SeedDrops.dropSeedsFromGrass();

        FMLInterModComms.sendMessage("Waila", "register", "com.dipo33.bewitched.integration.waila.HUDHandlerBewitched.register");
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
