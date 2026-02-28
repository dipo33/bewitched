package com.dipo33.bewitched;

import com.dipo33.bewitched.client.effect.EffectRegistry;
import com.dipo33.bewitched.network.message.EffectPlayMsg;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);
        EffectRegistry.registerEffects();
    }

    @Override
    public void playFX(final EffectPlayMsg message) {
        Minecraft.getMinecraft().func_152344_a(() -> {
            EffectRegistry.play(message);
        });
    }
}
