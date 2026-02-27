package com.dipo33.bewitched;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Bewitched.MODID, version = Tags.VERSION, name = Bewitched.MOD_NAME, acceptedMinecraftVersions = "[1.7.10]")
public class Bewitched {

    public static final String MODID = "bewitched";
    public static final String MOD_NAME = "Bewitched";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.dipo33.bewitched.ClientProxy", serverSide = "com.dipo33.bewitched.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Handle the mod's pre-initialization lifecycle event.
     * <p>
     * Delegates pre-initialization work (configuration loading and early registration) to the sided proxy.
     *
     * @param event
     *     the Forge pre-initialization event containing mod configuration and environment data
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    /**
     * Handle the mod's initialization lifecycle event.
     * <p>
     * Delegates initialization work (recipe registration and data structure building) to the sided proxy.
     *
     * @param event
     *     the Forge initialization event containing mod configuration and environment data
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * Handle the mod's post-initialization lifecycle event.
     * <p>
     * Delegates post-initialization work (interaction with other mods) to the sided proxy.
     *
     * @param event
     *     the Forge post-initialization event containing mod configuration and environment data
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    /**
     * Handle the mod's server-starting lifecycle event.
     * <p>
     * Delegates server-side setup (such as command registration) to the sided proxy.
     *
     * @param event
     *     the Forge server starting event providing access to command registration and server context
     */
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
