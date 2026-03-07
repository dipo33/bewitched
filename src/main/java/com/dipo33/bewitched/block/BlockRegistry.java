package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.items.ItemRegistry;
import com.dipo33.bewitched.items.ItemSaplingMultiTexture;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.EnumPlantType;

public class BlockRegistry {
    // Crops
    public static final ObjectHolder<Block> BELLADONNA_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.BELLADONNA_SEED, ItemRegistry.BELLADONNA_FLOWER)
            .setStages(5)
    );
    public static final ObjectHolder<Block> WOLFSBANE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.WOLFSBANE_SEED, ItemRegistry.WOLFSBANE_FLOWER)
            .setStages(8)
    );
    public static final ObjectHolder<Block> WATER_ARTICHOKE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.WATER_ARTICHOKE_SEED, ItemRegistry.WATER_ARTICHOKE_GLOBE)
            .setStages(5)
            .setPlantType(EnumPlantType.Water)
    );
    public static final ObjectHolder<Block> MANDRAKE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.MANDRAKE_SEED, ItemRegistry.MANDRAKE_ROOT)
            .setStages(5)
    );
    public static final ObjectHolder<Block> SNOW_WISP_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.SNOW_WISP_SEED, new ObjectHolder<>(() -> Items.snowball))
            .setStages(5)
            .addAdditionalDrops(ItemRegistry.ICY_NEEDLE, 0.1D)
    );
    public static final ObjectHolder<Block> GARLIC_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.GARLIC, ItemRegistry.GARLIC)
            .setStages(6)
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
        new BlockBwSapling()
            .setCreativeTab(Bewitched.CREATIVE_TAB)
            .setStepSound(Block.soundTypeGrass)
            .setHardness(0.0F)
    );

    /**
     * Register the mod's crop blocks with the game registry.
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
        registerBlock(SAPLING.get(), ItemSaplingMultiTexture.class, "sapling");
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
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
    }

    private static void registerBlockWithoutItem(Block block, String name) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, null, name);
    }

    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object... variants) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, itemclass, name, variants);
    }
}
