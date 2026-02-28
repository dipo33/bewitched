package com.dipo33.bewitched.network;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.network.message.MutandisFXMsg;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class BwNetwork {
    public static SimpleNetworkWrapper NET;

    private static int MESSAGE_ID = 0;

    public static void register() {
        NET = NetworkRegistry.INSTANCE.newSimpleChannel(Bewitched.MODID);

        registerMessages();
    }

    private static void registerMessages() {
        NET.registerMessage(MutandisFXMsg.Handler.class, MutandisFXMsg.class, MESSAGE_ID++, Side.CLIENT);
    }
}
