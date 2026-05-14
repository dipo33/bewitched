package com.dipo33.bewitched;

import com.dipo33.bewitched.client.effect.EffectRegistry;
import com.dipo33.bewitched.client.effect.entity.render.RenderMandrake;
import com.dipo33.bewitched.client.model.ArmorModelRegistry;
import com.dipo33.bewitched.client.sound.ClientSoundHandler;
import com.dipo33.bewitched.entity.EntityMandrake;
import com.dipo33.bewitched.entity.model.ModelMandrake;
import com.dipo33.bewitched.network.BewitchedNetwork;
import com.dipo33.bewitched.network.handler.EffectPlayMsgHandler;
import com.dipo33.bewitched.network.handler.UpdateFlowerPotMsgHandler;
import com.dipo33.bewitched.network.message.EffectPlayMsg;
import com.dipo33.bewitched.network.message.UpdateFlowerPotMsg;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);
        EffectRegistry.registerEffects();
        ArmorModelRegistry.init();
        ClientSoundHandler.init();
    }

    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        this.registerRenderers();
    }

    /**
     * Registers client-targeted network messages with their real client-side handlers.
     *
     * <p>Overrides the common implementation to bind handlers that may reference
     * client-only code (rendering, Minecraft client classes, etc.). These handlers
     * must never be loaded on the server.</p>
     */
    @Override
    protected void registerClientMessages() {
        BewitchedNetwork.registerClientMessage(EffectPlayMsgHandler.class, EffectPlayMsg.class);
        BewitchedNetwork.registerClientMessage(UpdateFlowerPotMsgHandler.class, UpdateFlowerPotMsg.class);
    }

    private void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(
            EntityMandrake.class,
            new RenderMandrake(new ModelMandrake(), 0.5F)
        );
    }
}
