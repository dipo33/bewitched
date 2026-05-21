package com.dipo33.bewitched.init;

import com.dipo33.bewitched.items.ItemRowanDoorKey;
import com.dipo33.bewitched.items.ItemRowanKeyring;

import java.util.Arrays;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeRowanKeyringAdd extends ShapelessRecipes {

    public RecipeRowanKeyringAdd() {
        super(
            new ItemStack(BewitchedItems.ROWAN_KEYRING.get()),
            Arrays.asList(
                new ItemStack(BewitchedItems.ROWAN_KEYRING.get()),
                new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get())
            )
        );
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        ItemStack keyring = null;
        ItemStack key = null;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanKeyring) {
                if (keyring != null) {
                    return false;
                }
                keyring = slot;
            } else if (slot.getItem() instanceof ItemRowanDoorKey && slot.getTagCompound() != null) {
                if (key != null) {
                    return false;
                }
                key = slot;
            } else {
                return false;
            }
        }
        if (keyring == null || key == null) {
            return false;
        }
        NBTTagCompound keyNbt = key.getTagCompound();
        return !ItemRowanKeyring.matches(
            keyring,
            keyNbt.getInteger("x"), keyNbt.getInteger("y"), keyNbt.getInteger("z"),
            keyNbt.getInteger("dim")
        );
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack keyring = null;
        ItemStack key = null;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanKeyring) {
                keyring = slot;
            } else if (slot.getItem() instanceof ItemRowanDoorKey) {
                key = slot;
            }
        }
        return ItemRowanKeyring.addKey(keyring, key);
    }
}
