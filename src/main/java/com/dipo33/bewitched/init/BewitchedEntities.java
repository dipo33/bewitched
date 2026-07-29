package com.dipo33.bewitched.init;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.entity.EntityEnt;
import com.dipo33.bewitched.entity.EntityMandrake;

import cpw.mods.fml.common.registry.EntityRegistry;

public class BewitchedEntities {

    public static final String MANDRAKE_ID = "mandragora";
    public static final String ENT_ID = "ent";

    /**
     * Registers every entity declared in this class with the game's entity registry.
     */
    public static void registerEntities() {
        registerMandrakeEntity();
        registerEntEntity();
    }

    private static void registerMandrakeEntity() {
        int globalId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityMandrake.class, MANDRAKE_ID, globalId, 0x5E7C3A, 0xD8C46A);
        EntityRegistry.registerModEntity(EntityMandrake.class, MANDRAKE_ID, 1, Bewitched.instance, 64, 1, true);
    }

    private static void registerEntEntity() {
        int globalId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityEnt.class, ENT_ID, globalId, 0x5C4033, 0x3E6B2A);
        EntityRegistry.registerModEntity(EntityEnt.class, ENT_ID, 2, Bewitched.instance, 64, 1, true);
    }
}
