package com.dipo33.bewitched.client.model.model;

import com.dipo33.bewitched.client.model.BewitchedModel;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class BewitchedModelEarmuffs extends BewitchedModel {

    public BewitchedModelEarmuffs() {
        super(32, 32);

        hideHeadwear = false;

        final ModelRenderer earmuffs = new ModelRenderer(this);
        earmuffs.setRotationPoint(0.0F, 0.0F, 0.0F);
        earmuffs.cubeList.add(new ModelBox(earmuffs, 0, 0, -4.0F, -10.0F, -1.0F, 8, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 12, 3, 6.0F, -6.0F, -1.0F, 1, 3, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 12, 8, -7.0F, -6.0F, -1.0F, 1, 3, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 12, 13, 5.0F, -7.0F, -1.0F, 2, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 12, 16, -5.0F, -9.0F, -1.0F, 2, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 18, 3, 3.0F, -9.0F, -1.0F, 2, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 18, 6, -7.0F, -7.0F, -1.0F, 2, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 0, 3, 4.0F, -5.0F, -2.0F, 2, 4, 4, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 0, 11, -6.0F, -5.0F, -2.0F, 2, 4, 4, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 18, 9, -6.0F, -8.0F, -1.0F, 2, 1, 2, 0.0F));
        earmuffs.cubeList.add(new ModelBox(earmuffs, 0, 19, 4.0F, -8.0F, -1.0F, 2, 1, 2, 0.0F));

        attachToHead(earmuffs);

        setArmorPartVisibility();
    }
}
