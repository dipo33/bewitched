package com.dipo33.bewitched.client.model;

import com.dipo33.bewitched.client.model.model.BewitchedModelEarmuffs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;

@SideOnly(Side.CLIENT)
public class ArmorModelRegistry {

    public static ModelBiped helmetModel;

    public static void init() {
        helmetModel = new BewitchedModelEarmuffs();
    }
}
