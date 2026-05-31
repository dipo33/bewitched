package com.dipo33.bewitched.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.dipo33.bewitched.init.BewitchedItems;
import com.dipo33.bewitched.items.ItemRowanDoorKey;
import com.dipo33.bewitched.items.ItemRowanKeyring;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class RowanKeyringNEIHandler extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return I18n.format("bewitched.nei.rowan_keyring");
    }

    @Override
    public String getGuiTexture() {
        return "textures/gui/container/crafting_table.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return "bewitched.rowan_keyring";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiCrafting.class;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier()) && getClass() == RowanKeyringNEIHandler.class) {
            addAllRecipes();
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() instanceof ItemRowanKeyring) {
            addAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient.getItem() instanceof ItemRowanDoorKey
            || ingredient.getItem() instanceof ItemRowanKeyring) {
            addAllRecipes();
        }
    }

    private void addAllRecipes() {
        arecipes.add(new CachedKeyringRecipe(false));
        arecipes.add(new CachedKeyringRecipe(true));
    }

    public class CachedKeyringRecipe extends CachedRecipe {

        private final List<PositionedStack> ingredients;
        private final PositionedStack result;

        public CachedKeyringRecipe(boolean hasKeyring) {
            ingredients = new ArrayList<>();
            ItemStack firstInput = hasKeyring
                ? new ItemStack(BewitchedItems.ROWAN_KEYRING.get())
                : new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get());
            ingredients.add(new PositionedStack(firstInput, 25, 6));
            ingredients.add(new PositionedStack(new ItemStack(BewitchedItems.ROWAN_DOOR_KEY.get()), 43, 6));
            result = new PositionedStack(new ItemStack(BewitchedItems.ROWAN_KEYRING.get()), 119, 24);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return ingredients;
        }
    }
}
