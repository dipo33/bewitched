package com.dipo33.bewitched.init;

import com.dipo33.bewitched.items.ItemRowanDoorKey;
import com.dipo33.bewitched.items.ItemRowanKeyring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class RecipeRowanKeyring extends ShapelessRecipes {

    public RecipeRowanKeyring() {
        super(
            new ItemStack(BewitchedItems.ROWAN_KEYRING.get()),
            Arrays.asList(
                new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get()),
                new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get())
            )
        );
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        List<ItemStack> keys = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanDoorKey && slot.getTagCompound() != null) {
                keys.add(slot);
            } else {
                return false;
            }
        }
        return keys.size() == 2
            && !ItemRowanDoorKey.sameTarget(keys.get(0).getTagCompound(), keys.get(1).getTagCompound());
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        List<ItemStack> keys = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanDoorKey) {
                keys.add(slot);
            }
        }
        return ItemRowanKeyring.fromTwoKeys(keys.get(0), keys.get(1));
    }
}
