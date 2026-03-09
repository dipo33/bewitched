package com.dipo33.bewitched.config;

import com.dipo33.bewitched.data.MetaBlock;
import com.dipo33.bewitched.api.mutation.Mutation;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Parsers {

    public static Mutation parseMutation(String line) {
        String[] arrow = line.split("->", 2);
        if (arrow.length != 2) {
            throw new IllegalArgumentException("Missing '->'");
        }

        String sourcesPart = arrow[0].trim();
        String outputPart = arrow[1].trim();

        List<Mutation.Source> sources = new ArrayList<>();
        for (String token : sourcesPart.split("\\+")) {
            sources.add(parseSource(token.trim()));
        }

        MetaBlock output = parseMetaBlock(outputPart, true);

        return new Mutation(
            new Mutation.Output(output.block(), Mutation.basicStrategy(output.meta())),
            sources
        );
    }

    private static Mutation.Source parseSource(String token) {
        MetaBlock parsed = parseMetaBlock(token, false);

        if (parsed.meta() == null) {
            return Mutation.Source.anyMeta(parsed.block());
        } else {
            return Mutation.Source.exact(parsed.block(), parsed.meta());
        }
    }

    private static MetaBlock parseMetaBlock(String token, boolean requireMeta) {
        int at = token.indexOf('@');

        String blockName;
        Integer meta = null;

        if (at >= 0) {
            blockName = token.substring(0, at).trim();
            String metaText = token.substring(at + 1).trim();

            if (!metaText.equals("*")) {
                meta = Integer.parseInt(metaText);
            }
        } else {
            blockName = token.trim();
        }

        if (requireMeta && meta == null) {
            throw new IllegalArgumentException("Output must specify metadata: " + token);
        }

        Block block = (Block) Block.blockRegistry.getObject(blockName);
        if (block == null || block == Blocks.air) {
            throw new IllegalArgumentException("Unknown block: " + blockName);
        }

        if (meta != null && (meta < 0 || meta > 15)) {
            throw new IllegalArgumentException("Metadata out of range 0-15: " + token);
        }

        return new MetaBlock(block, meta);
    }
}
