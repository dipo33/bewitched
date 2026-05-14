package com.dipo33.bewitched.client.sound;

import com.dipo33.bewitched.init.BewitchedItems;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientSoundHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ClientSoundHandler());
    }

    private boolean isWhiteListedSound(String name) {
        return name.startsWith("gui.");
    }

    private boolean isBlackListedSound(String name) {
        return name.startsWith("records.") || name.startsWith("music_disc.");
    }

    private ISound applyMuffling(PlaySoundEvent17 event) {
        ISound original = event.result;

        return new PositionedSoundRecord(
            original.getPositionedSoundLocation(),
            original.getVolume() * 0.01F,
            original.getPitch(),
            original.getXPosF(),
            original.getYPosF(),
            original.getZPosF()
        );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlaySound(PlaySoundEvent17 event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return;
        }

        ItemStack helmet = mc.thePlayer.inventory.armorInventory[3];
        if (helmet != null && helmet.getItem() == BewitchedItems.EARMUFFS.get()) {
            String name = event.name;

            if (name == null) {
                return;
            }

            if (isWhiteListedSound(name)) {
                return;
            }

            if (isBlackListedSound(name)) {
                event.result = null;
                return;
            }

            event.result = applyMuffling(event);
        }
    }
}
