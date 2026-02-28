package com.dipo33.bewitched.client.effect;

import com.dipo33.bewitched.network.BwNetwork;
import com.dipo33.bewitched.network.message.EffectPlayMsg;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.world.World;

public class EffectPlayer {

    public static void playFX(String effectId, World world, double x, double y, double z, double range) {
        NetworkRegistry.TargetPoint tp = new NetworkRegistry.TargetPoint(
            world.provider.dimensionId, x, y, z, range
        );
        BwNetwork.NET.sendToAllAround(new EffectPlayMsg(world, effectId, x, y, z), tp);
    }
}
