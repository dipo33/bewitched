package com.dipo33.bewitched.init;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.block.BlockBewitchedCrops;
import com.dipo33.bewitched.block.BlockBewitchedDoor;
import com.dipo33.bewitched.block.BlockBewitchedLeaves;
import com.dipo33.bewitched.block.BlockBewitchedLog;
import com.dipo33.bewitched.block.BlockBewitchedPlanks;
import com.dipo33.bewitched.block.BlockBewitchedSapling;
import com.dipo33.bewitched.block.BlockBewitchedSlab;
import com.dipo33.bewitched.block.BlockBewitchedStairs;
import com.dipo33.bewitched.block.BlockMandrakeCrop;
import com.dipo33.bewitched.block.BlockSmolderingPlant;
import com.dipo33.bewitched.block.BlockSpanishMoss;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.items.ItemBewitchedLeaves;
import com.dipo33.bewitched.items.ItemBewitchedMultiTexture;
import com.dipo33.bewitched.items.ItemBewitchedSlab;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.EnumPlantType;

public class BewitchedBlocks {
    // Crops
    public static final ObjectHolder<Block> BELLADONNA_CROP = new ObjectHolder<>(() ->
        new BlockBewitchedCrops(BewitchedItems.BELLADONNA_SEEDS, BewitchedItems.BELLADONNA_FLOWER)
            .setStages(5)
    );
    public static final ObjectHolder<Block> WOLFSBANE_CROP = new ObjectHolder<>(() ->
        new BlockBewitchedCrops(BewitchedItems.WOLFSBANE_SEEDS, BewitchedItems.WOLFSBANE_FLOWER)
            .setStages(8)
    );
    public static final ObjectHolder<Block> WATER_ARTICHOKE_CROP = new ObjectHolder<>(() ->
        new BlockBewitchedCrops(BewitchedItems.WATER_ARTICHOKE_SEEDS, BewitchedItems.WATER_ARTICHOKE_GLOBE)
            .setStages(5)
            .setPlantType(EnumPlantType.Water)
    );
    public static final ObjectHolder<Block> MANDRAKE_CROP = new ObjectHolder<>(BlockMandrakeCrop::new);
    public static final ObjectHolder<Block> SNOW_WISP_CROP = new ObjectHolder<>(() ->
        new BlockBewitchedCrops(BewitchedItems.SNOW_WISP_SEEDS, new ObjectHolder<>(() -> Items.snowball))
            .setStages(5)
            .addAdditionalDrops(BewitchedItems.ICY_NEEDLE, 0.1D)
    );
    public static final ObjectHolder<Block> GARLIC_CROP = new ObjectHolder<>(() ->
        new BlockBewitchedCrops(BewitchedItems.GARLIC, BewitchedItems.GARLIC)
            .setStages(6)
    );

    // Planks
    public static final ObjectHolder<Block> PLANKS = new ObjectHolder<>(() ->
        new BlockBewitchedPlanks()
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setHardness(2.0F)
            .setStepSound(Block.soundTypeWood)
    );

    // Leaves
    public static final ObjectHolder<Block> LEAVES = new ObjectHolder<>(BlockBewitchedLeaves::new);

    // Stairs
    public static final ObjectHolder<Block> STAIRS_ROWAN = new ObjectHolder<>(() ->
        new BlockBewitchedStairs(PLANKS.get(), 0)
    );
    public static final ObjectHolder<Block> STAIRS_ALDER = new ObjectHolder<>(() ->
        new BlockBewitchedStairs(PLANKS.get(), 1)
    );
    public static final ObjectHolder<Block> STAIRS_HAWTHORN = new ObjectHolder<>(() ->
        new BlockBewitchedStairs(PLANKS.get(), 2)
    );

    // Slabs
    public static final ObjectHolder<Block> SLAB = new ObjectHolder<>(() ->
        new BlockBewitchedSlab(false)
            .setHardness(2.0F)
            .setStepSound(Block.soundTypeWood)
    );
    public static final ObjectHolder<Block> SLAB_DOUBLE = new ObjectHolder<>(() ->
        new BlockBewitchedSlab(true)
            .setSingleSlab((BlockBewitchedSlab) SLAB.get())
            .setHardness(2.0F)
            .setStepSound(Block.soundTypeWood)
    );

    // Logs
    public static final ObjectHolder<Block> LOG = new ObjectHolder<>(() ->
        new BlockBewitchedLog()
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setHardness(2.0F)
            .setStepSound(Block.soundTypeWood)
    );

    // Doors
    public static final ObjectHolder<Block> ALDER_DOOR_BLOCK = new ObjectHolder<>(() ->
        new BlockBewitchedDoor(BewitchedItems.ALDER_DOOR, true)
    );
    public static final ObjectHolder<Block> CONCEALING_DOOR_BLOCK = new ObjectHolder<>(() ->
        new BlockBewitchedDoor(BewitchedItems.CONCEALING_DOOR, true)
    );
    public static final ObjectHolder<Block> HAWTHORN_DOOR_BLOCK = new ObjectHolder<>(() ->
        new BlockBewitchedDoor(BewitchedItems.HAWTHORN_DOOR)
    );

    // Plants
    public static final ObjectHolder<Block> SPANISH_MOSS = new ObjectHolder<>(() ->
        new BlockSpanishMoss().setCreativeTab(Bewitched.CREATIVE_TAB)
            .setHardness(0.2F)
            .setStepSound(Block.soundTypeGrass)
    );
    public static final ObjectHolder<Block> GLINT_WEED = new ObjectHolder<>(() ->
        new BlockSmolderingPlant(true, new BlockSmolderingPlant.SmolderConfig(20F / 32F, 10.5F / 16F, 4F / 16F))
            .withBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F) // TODO: Based on texture
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setHardness(0F)
            .setStepSound(Block.soundTypeGrass)
            .setLightLevel(0.9375F)
    );
    public static final ObjectHolder<Block> EMBER_MOSS = new ObjectHolder<>(() ->
        new BlockSmolderingPlant(false, new BlockSmolderingPlant.SmolderConfig(1F / 32F, 2F / 16F, 10F / 16F))
            .withBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F) // TODO: Based on texture
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setHardness(0F)
            .setStepSound(Block.soundTypeGrass)
            .setLightLevel(0.4375F)
    );
    public static final ObjectHolder<Block> SAPLING = new ObjectHolder<>(() ->
        new BlockBewitchedSapling()
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setStepSound(Block.soundTypeGrass)
            .setHardness(0.0F)
    );

    /**
     * Register the mod's blocks with the game registry.
     */
    public static void registerBlocks() {
        registerBlock(BELLADONNA_CROP.get(), "belladonna");
        registerBlock(WOLFSBANE_CROP.get(), "wolfsbane");
        registerBlock(WATER_ARTICHOKE_CROP.get(), "water_artichoke");
        registerBlock(MANDRAKE_CROP.get(), "mandrake");
        registerBlock(SNOW_WISP_CROP.get(), "snow_wisp");
        registerBlock(GARLIC_CROP.get(), "garlic");

        registerBlock(SPANISH_MOSS.get(), "spanish_moss");
        registerBlock(GLINT_WEED.get(), "glint_weed");
        registerBlock(EMBER_MOSS.get(), "ember_moss");
        registerBlock(SAPLING.get(), ItemBewitchedMultiTexture.class, "sapling");

        registerBlock(PLANKS.get(), ItemBewitchedMultiTexture.class, "planks");
        registerBlock(LOG.get(), ItemBewitchedMultiTexture.class, "log");
        registerBlock(LEAVES.get(), ItemBewitchedLeaves.class, "leaves");

        registerBlock(STAIRS_ROWAN.get(), "stairs_rowan");
        registerBlock(STAIRS_ALDER.get(), "stairs_alder");
        registerBlock(STAIRS_HAWTHORN.get(), "stairs_hawthorn");

        BlockBewitchedSlab singleSlab = (BlockBewitchedSlab) SLAB.get();
        BlockBewitchedSlab doubleSlab = (BlockBewitchedSlab) SLAB_DOUBLE.get();
        registerBlock(singleSlab, ItemBewitchedSlab.class, "slab", singleSlab, doubleSlab);
        registerBlock(doubleSlab, ItemBewitchedSlab.class, "slab_double", "slab", singleSlab, doubleSlab);

        registerBlock(ALDER_DOOR_BLOCK.get(), (Class<? extends ItemBlock>) null, "alder_door");
        registerBlock(CONCEALING_DOOR_BLOCK.get(), null, "concealing_door", "doorWood");
        CONCEALING_DOOR_BLOCK.get().setBlockTextureName("minecraft:door_wood");
        registerBlock(HAWTHORN_DOOR_BLOCK.get(), (Class<? extends ItemBlock>) null, "hawthorn_door");
    }

    /**
     * Configure a block's texture and unlocalized name, then register it with the GameRegistry.
     *
     * @param block
     *     the block to configure and register
     * @param name
     *     the registry name used for the block's texture, unlocalized name, and registration
     */
    private static void registerBlock(Block block, String name) {
        registerBlock(block, name, name);
    }

    /**
     * Configure a block's texture and unlocalized name, then register it with the GameRegistry.
     *
     * @param block
     *     the block to configure and register
     * @param registryName
     *     the name used for GameRegistry registration and the block's texture
     * @param blockName
     *     the translation key set on the block (used for localization lookups)
     */
    private static void registerBlock(Block block, String registryName, String blockName) {
        block.setBlockTextureName(Bewitched.MODID + ":" + registryName);
        block.setBlockName(blockName);
        GameRegistry.registerBlock(block, registryName);
    }

    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object... variants) {
        registerBlock(block, itemclass, name, name, variants);
    }

    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String registryName, String blockName, Object... variants) {
        block.setBlockTextureName(Bewitched.MODID + ":" + registryName);
        block.setBlockName(blockName);
        GameRegistry.registerBlock(block, itemclass, registryName, variants);
    }
}
