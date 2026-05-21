package com.dipo33.bewitched.items;

import com.dipo33.bewitched.init.BewitchedItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemRowanDoorKey extends Item {

    public ItemRowanDoorKey() {
        this.maxStackSize = 1;
    }

    public static ItemStack create(int x, int y, int z, int dimensionId, String dimensionName) {
        ItemStack stack = new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get());
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
        nbt.setInteger("dim", dimensionId);
        nbt.setString("dimName", dimensionName);
        stack.setTagCompound(nbt);
        return stack;
    }

    public static boolean sameTarget(NBTTagCompound a, NBTTagCompound b) {
        return a.getInteger("x") == b.getInteger("x")
            && a.getInteger("y") == b.getInteger("y")
            && a.getInteger("z") == b.getInteger("z")
            && a.getInteger("dim") == b.getInteger("dim");
    }

    public static boolean matches(ItemStack stack, int x, int y, int z, int dimensionId) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            return false;
        }
        return nbt.getInteger("x") == x
            && nbt.getInteger("y") == y
            && nbt.getInteger("z") == z
            && nbt.getInteger("dim") == dimensionId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            return;
        }
        list.add(formatCoords(nbt));
    }

    public static String formatCoords(NBTTagCompound nbt) {
        int x = nbt.getInteger("x");
        int y = nbt.getInteger("y");
        int z = nbt.getInteger("z");
        String dimName = nbt.getString("dimName");
        return (dimName != null && !dimName.isEmpty()) ? dimName + ": " + x + ", " + y + ", " + z : x + ", " + y + ", " + z;
    }
}
