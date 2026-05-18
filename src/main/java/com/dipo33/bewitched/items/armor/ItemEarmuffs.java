package com.dipo33.bewitched.items.armor;

import com.dipo33.bewitched.client.model.ArmorModelRegistry;
import com.dipo33.bewitched.init.BewitchedItemArmorMaterials;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemEarmuffs extends ItemArmor {

    public ItemEarmuffs() {
        // material, render passes, armor slot (0 for helmet)
        super(BewitchedItemArmorMaterials.BEWITCHED_LEATHER, 0, 0);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltips, boolean advancedTooltips) {
        tooltips.add(StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".desc"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        return ArmorModelRegistry.helmetModel;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return BewitchedItemArmor.getArmorTexture("earmuffs");
    }
}
