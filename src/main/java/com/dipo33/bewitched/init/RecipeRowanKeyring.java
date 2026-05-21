package com.dipo33.bewitched.init;

import com.dipo33.bewitched.items.ItemRowanDoorKey;
import com.dipo33.bewitched.items.ItemRowanKeyring;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeRowanKeyring implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        ItemStack keyring = null;
        List<ItemStack> keys = new ArrayList<>();
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
                keys.add(slot);
            } else {
                return false;
            }
        }
        if (keyring == null && keys.size() == 2) {
            return !ItemRowanDoorKey.sameTarget(keys.get(0).getTagCompound(), keys.get(1).getTagCompound());
        }
        if (keyring != null && keys.size() == 1) {
            var keyNbt = keys.get(0).getTagCompound();
            return !ItemRowanKeyring.matches(keyring, keyNbt.getInteger("x"), keyNbt.getInteger("y"), keyNbt.getInteger("z"), keyNbt.getInteger("dim"));
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        ItemStack keyring = null;
        List<ItemStack> keys = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanKeyring) {
                keyring = slot;
            } else if (slot.getItem() instanceof ItemRowanDoorKey) {
                keys.add(slot);
            }
        }
        if (keyring != null) {
            return ItemRowanKeyring.addKey(keyring, keys.get(0));
        }
        return ItemRowanKeyring.fromTwoKeys(keys.get(0), keys.get(1));
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(BewitchedItems.ROWAN_KEYRING.get());
    }
}
