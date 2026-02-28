package com.dipo33.bewitched.network.message;

import com.dipo33.bewitched.Bewitched;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class EffectPlayMsg implements IMessage {
    public String effectId;
    public int dimensionId;
    public double x, y, z;

    public EffectPlayMsg() {
    }

    public EffectPlayMsg(World world, String effectId, double x, double y, double z) {
        this.dimensionId = world.provider.dimensionId;
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        this.effectId = ByteBufUtils.readUTF8String(buf);
        this.dimensionId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.effectId);
        buf.writeInt(this.dimensionId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public static class Handler implements IMessageHandler<EffectPlayMsg, IMessage> {
        @Override
        public IMessage onMessage(final EffectPlayMsg message, final MessageContext ctx) {
            Bewitched.proxy.playFX(message);
            return null;
        }
    }
}
