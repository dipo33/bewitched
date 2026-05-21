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
        int keyringCount = 0;
        int keyCount = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanKeyring) {
                keyringCount++;
            } else if (slot.getItem() instanceof ItemRowanDoorKey && slot.getTagCompound() != null) {
                keyCount++;
            } else {
                return false;
            }
        }
        return (keyringCount == 1 && keyCount == 1) || (keyringCount == 0 && keyCount == 2);
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
