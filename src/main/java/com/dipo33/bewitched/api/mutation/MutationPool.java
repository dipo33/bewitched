package com.dipo33.bewitched.api.mutation;

import com.github.bsideup.jabel.Desugar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MutationPool {

    private final MutationPoolType type;
    private final String unlocalizedName;
    private final List<ItemStack> catalysts = new ArrayList<>();
    private final List<Mutation> members = new ArrayList<>();

    public MutationPool(MutationPoolType type, String unlocalizedName) {
        this.type = type;
        this.unlocalizedName = unlocalizedName;
    }

    public MutationPoolType getType() {
        return type;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void addCatalyst(ItemStack stack) {
        catalysts.add(stack.copy());
    }

    public void addMember(Mutation mutation) {
        members.add(mutation);
    }

    public List<ItemStack> getCatalysts() {
        return Collections.unmodifiableList(catalysts);
    }

    public List<Mutation> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public MutationMatch getMutationFor(Block block, int meta) {
        for (int i = 0; i < this.members.size(); i++) {
            Mutation mutation = this.members.get(i);
            if (mutation.matchesSource(block, meta)) {
                return new MutationMatch(mutation, i);
            }
        }

        return null;
    }

    @Desugar
    public record MutationMatch(Mutation mutation, int index) {
    }
}
