package com.dipo33.bewitched.api.mutation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;

public final class MutationRegistry {

    private static final Map<MutationPoolType, MutationPool> POOLS = new EnumMap<>(MutationPoolType.class);

    private MutationRegistry() {}

    public static MutationPool createPool(MutationPoolType type, String unlocalizedName) {
        MutationPool pool = new MutationPool(type, unlocalizedName);
        POOLS.put(type, pool);
        return pool;
    }

    public static MutationPool getPool(MutationPoolType type) {
        return POOLS.get(type);
    }

    public static List<ItemStack> getOutputs(MutationPoolType type, ItemStack source) {
        MutationPool pool = getPool(type);
        List<ItemStack> outputs = new ArrayList<>();

        if (pool == null || source == null) {
            return outputs;
        }

        for (Mutation member : pool.getMembers()) {
            ItemStack output = member.output().asStack();
            if (!areStacksSame(source, output)) {
                outputs.add(output);
            }
        }

        return outputs;
    }

    public static boolean isMember(MutationPoolType type, ItemStack stack) {
        MutationPool pool = getPool(type);
        if (pool == null || stack == null) {
            return false;
        }

        for (Mutation member : pool.getMembers()) {
            if (areStacksSame(member.output().asStack(), stack)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCatalyst(MutationPoolType type, ItemStack stack) {
        MutationPool pool = getPool(type);
        if (pool == null || stack == null) {
            return false;
        }

        for (ItemStack catalyst : pool.getCatalysts()) {
            if (areStacksSame(catalyst, stack)) {
                return true;
            }
        }

        return false;
    }

    public static List<ItemStack> getCatalysts(MutationPoolType type) {
        MutationPool pool = getPool(type);
        return pool == null ? new ArrayList<ItemStack>() : new ArrayList<ItemStack>(pool.getCatalysts());
    }

    public static List<Mutation> getMembers(MutationPoolType type) {
        MutationPool pool = getPool(type);
        return pool == null ? new ArrayList<>() : new ArrayList<>(pool.getMembers());
    }

    private static boolean areStacksSame(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;
        if (a.getItem() != b.getItem()) return false;

        int ma = a.getItemDamage();
        int mb = b.getItemDamage();

        return ma == mb;
    }
}
