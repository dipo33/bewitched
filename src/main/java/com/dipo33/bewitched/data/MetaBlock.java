package com.dipo33.bewitched.data;

import com.github.bsideup.jabel.Desugar;

import net.minecraft.block.Block;

@Desugar
public record MetaBlock(Block block, Integer meta) {}
