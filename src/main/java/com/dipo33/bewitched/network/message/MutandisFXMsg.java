package com.dipo33.bewitched.network.message;

import com.dipo33.bewitched.entity.particle.EntityMutandisFX;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class MutandisFXMsg implements IMessage {
    private int dimensionId;
    private double x, y, z;

    public MutandisFXMsg() {
    }

    public MutandisFXMsg(World world, double x, double y, double z) {
        this.dimensionId = world.provider.dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        this.dimensionId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.dimensionId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public static class Handler implements IMessageHandler<MutandisFXMsg, IMessage> {

        @Override
        public IMessage onMessage(final MutandisFXMsg message, final MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() -> {
                World world = Minecraft.getMinecraft().theWorld;
                if (world == null)
                    return;
                if (world.provider.dimensionId != message.dimensionId)
                    return;
                EntityMutandisFX.spawnMutandisFX(world, message.x, message.y, message.z);
            });

            return null;
        }
    }
}
