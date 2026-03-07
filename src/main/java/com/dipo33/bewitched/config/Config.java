package com.dipo33.bewitched.config;

import com.dipo33.bewitched.items.mutandis.MutandisMutation;

import java.io.File;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean rightClickMatureCropHarvest = true;

    public static Collection<MutandisMutation> mutandisAdditionalMutations;
    public static Collection<MutandisMutation> mutandisAdditionalFlowerPotMutations;
    public static Collection<MutandisMutation> mutandisExtremisAdditionalMutations;
    public static Collection<MutandisMutation> mutandisExtremisAdditionalGrassMutations;

    /**
     * Loads the mod configuration from the provided file
     *
     * @param configFile
     *     the file to load and (if modified) save the configuration to
     */
    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        rightClickMatureCropHarvest = configuration.getBoolean(
            "rightClickMatureCropHarvest", Configuration.CATEGORY_GENERAL, true,
            "Should right click on mature crop harvest the crop?"
        );

        mutandisAdditionalMutations = Arrays.stream(configuration.getStringList(
            "mutandisAdditionalMutations", Configuration.CATEGORY_GENERAL, new String[]{
                "bewitched:glint_weed@0 -> bewitched:glint_weed@0",
                "minecraft:tallgrass@2 -> minecraft:tallgrass@2"
            }, "Additional mutations in the Mutandis mutation pool"
        )).map(Parsers::parseMutation).collect(Collectors.toList());
        mutandisAdditionalFlowerPotMutations = Arrays.stream(configuration.getStringList(
            "mutandisAdditionalFlowerPotMutations", Configuration.CATEGORY_GENERAL, new String[]{},
            "Additional mutations in the Mutandis flower pot mutation pool"
        )).map(Parsers::parseMutation).collect(Collectors.toList());
        mutandisExtremisAdditionalMutations = Arrays.stream(configuration.getStringList(
            "mutandisExtremisAdditionalMutations", Configuration.CATEGORY_GENERAL, new String[]{},
            "Additional mutations in the Mutandis Extremis mutation pool"
        )).map(Parsers::parseMutation).collect(Collectors.toList());
        mutandisExtremisAdditionalGrassMutations = Arrays.stream(configuration.getStringList(
            "mutandisExtremisAdditionalGrassMutations", Configuration.CATEGORY_GENERAL, new String[]{},
            "Additional mutations in the Mutandis Extremis special grass mutation pool"
        )).map(Parsers::parseMutation).collect(Collectors.toList());

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
