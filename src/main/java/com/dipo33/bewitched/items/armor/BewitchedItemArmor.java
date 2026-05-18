package com.dipo33.bewitched.items.armor;

import com.dipo33.bewitched.Bewitched;

public final class BewitchedItemArmor {

    private BewitchedItemArmor() {}

    public static String getArmorTexture(String name) {
        return Bewitched.MODID + ":textures/models/armor/" + name + ".png";
    }
}
