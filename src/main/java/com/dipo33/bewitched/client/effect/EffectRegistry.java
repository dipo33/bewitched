package com.dipo33.bewitched.client.effect;

import com.dipo33.bewitched.network.message.EffectPlayMsg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EffectRegistry {
    private static final Map<String, IEffect> EFFECTS = new HashMap<>();

    private EffectRegistry() {
    }

    public static void register(String id, IEffect effect) {
        EFFECTS.put(id, effect);
    }

    public static void play(EffectPlayMsg msg) {
        World w = Minecraft.getMinecraft().theWorld;
        if (w == null) {
            return;
        }
        if (w.provider.dimensionId != msg.dimensionId) {
            return;
        }

        IEffect fx = EFFECTS.get(msg.effectId);
        if (fx != null) {
            fx.play(w, msg.x, msg.y, msg.z);
        }
    }

    public static void registerEffects() {
        register(Effects.MUTANDIS_FX, new MutandisFX());
    }
}
