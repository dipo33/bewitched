package com.dipo33.bewitched.items;

import com.dipo33.bewitched.init.BewitchedItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemRowanKeyring extends Item {

    private static final int TAG_COMPOUND = 10;

    public ItemRowanKeyring() {
        this.maxStackSize = 1;
    }

    public static ItemStack fromTwoKeys(ItemStack key1, ItemStack key2) {
        ItemStack ring = new ItemStack(BewitchedItems.ROWAN_KEYRING.get());
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        list.appendTag(key1.getTagCompound().copy());
        list.appendTag(key2.getTagCompound().copy());
        nbt.setTag("keys", list);
        ring.setTagCompound(nbt);
        return ring;
    }

    public static ItemStack addKey(ItemStack keyring, ItemStack key) {
        ItemStack result = keyring.copy();
        NBTTagCompound nbt = result.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }
        NBTTagList list = nbt.getTagList("keys", TAG_COMPOUND);
        if (key.getTagCompound() != null) {
            list.appendTag(key.getTagCompound().copy());
        }
        nbt.setTag("keys", list);
        result.setTagCompound(nbt);
        return result;
    }

    public static boolean matches(ItemStack ring, int x, int y, int z, int dimensionId) {
        NBTTagCompound nbt = ring.getTagCompound();
        if (nbt == null) {
            return false;
        }
        NBTTagList list = nbt.getTagList("keys", TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);
            if (entry.getInteger("x") == x
                && entry.getInteger("y") == y
                && entry.getInteger("z") == z
                && entry.getInteger("dim") == dimensionId) {
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            return;
        }
        NBTTagList keys = nbt.getTagList("keys", TAG_COMPOUND);
        for (int i = 0; i < keys.tagCount(); i++) {
            list.add(ItemRowanDoorKey.formatCoords(keys.getCompoundTagAt(i)));
        }
    }
}
