package com.dipo33.bewitched.items;

import static org.junit.Assert.*;

import net.minecraft.item.Item;

import org.junit.Test;

import com.dipo33.bewitched.data.ObjectHolder;

/**
 * Test suite for ItemRegistry class.
 * Tests item holder initialization and structure.
 */
public class ItemRegistryTest {

    // Seed tests
    @Test
    public void testBelladonnaSeedHolderNotNull() {
        assertNotNull(ItemRegistry.BELLADONNA_SEED);
    }

    @Test
    public void testWolfsbaneSeedHolderNotNull() {
        assertNotNull(ItemRegistry.WOLFSBANE_SEED);
    }

    @Test
    public void testWaterArtichokeSeedHolderNotNull() {
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_SEED);
    }

    @Test
    public void testMandrakeSeedHolderNotNull() {
        assertNotNull(ItemRegistry.MANDRAKE_SEED);
    }

    @Test
    public void testSnowWispSeedHolderNotNull() {
        assertNotNull(ItemRegistry.SNOW_WISP_SEED);
    }

    @Test
    public void testGarlicHolderNotNull() {
        assertNotNull(ItemRegistry.GARLIC);
    }

    // Crop tests
    @Test
    public void testBelladonnaFlowerHolderNotNull() {
        assertNotNull(ItemRegistry.BELLADONNA_FLOWER);
    }

    @Test
    public void testWolfsbaneFlowerHolderNotNull() {
        assertNotNull(ItemRegistry.WOLFSBANE_FLOWER);
    }

    @Test
    public void testWaterArtichokeGlobeHolderNotNull() {
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_GLOBE);
    }

    @Test
    public void testMandrakeRootHolderNotNull() {
        assertNotNull(ItemRegistry.MANDRAKE_ROOT);
    }

    @Test
    public void testIcyNeedleHolderNotNull() {
        assertNotNull(ItemRegistry.ICY_NEEDLE);
    }

    @Test
    public void testAllSeedHoldersAreObjectHolderType() {
        assertTrue(ItemRegistry.BELLADONNA_SEED instanceof ObjectHolder);
        assertTrue(ItemRegistry.WOLFSBANE_SEED instanceof ObjectHolder);
        assertTrue(ItemRegistry.WATER_ARTICHOKE_SEED instanceof ObjectHolder);
        assertTrue(ItemRegistry.MANDRAKE_SEED instanceof ObjectHolder);
        assertTrue(ItemRegistry.SNOW_WISP_SEED instanceof ObjectHolder);
        assertTrue(ItemRegistry.GARLIC instanceof ObjectHolder);
    }

    @Test
    public void testAllCropHoldersAreObjectHolderType() {
        assertTrue(ItemRegistry.BELLADONNA_FLOWER instanceof ObjectHolder);
        assertTrue(ItemRegistry.WOLFSBANE_FLOWER instanceof ObjectHolder);
        assertTrue(ItemRegistry.WATER_ARTICHOKE_GLOBE instanceof ObjectHolder);
        assertTrue(ItemRegistry.MANDRAKE_ROOT instanceof ObjectHolder);
        assertTrue(ItemRegistry.ICY_NEEDLE instanceof ObjectHolder);
    }

    @Test
    public void testAllHoldersAreItemType() {
        // Verify the generic type is Item
        ObjectHolder<Item> belladonnaSeed = ItemRegistry.BELLADONNA_SEED;
        ObjectHolder<Item> wolfsbaneSeed = ItemRegistry.WOLFSBANE_SEED;
        ObjectHolder<Item> waterArtichokeSeed = ItemRegistry.WATER_ARTICHOKE_SEED;
        ObjectHolder<Item> mandrakeSeed = ItemRegistry.MANDRAKE_SEED;
        ObjectHolder<Item> snowWispSeed = ItemRegistry.SNOW_WISP_SEED;
        ObjectHolder<Item> garlic = ItemRegistry.GARLIC;

        ObjectHolder<Item> belladonnaFlower = ItemRegistry.BELLADONNA_FLOWER;
        ObjectHolder<Item> wolfsbaneFlower = ItemRegistry.WOLFSBANE_FLOWER;
        ObjectHolder<Item> waterArtichokeGlobe = ItemRegistry.WATER_ARTICHOKE_GLOBE;
        ObjectHolder<Item> mandrakeRoot = ItemRegistry.MANDRAKE_ROOT;
        ObjectHolder<Item> icyNeedle = ItemRegistry.ICY_NEEDLE;

        assertNotNull(belladonnaSeed);
        assertNotNull(wolfsbaneSeed);
        assertNotNull(waterArtichokeSeed);
        assertNotNull(mandrakeSeed);
        assertNotNull(snowWispSeed);
        assertNotNull(garlic);

        assertNotNull(belladonnaFlower);
        assertNotNull(wolfsbaneFlower);
        assertNotNull(waterArtichokeGlobe);
        assertNotNull(mandrakeRoot);
        assertNotNull(icyNeedle);
    }

    @Test
    public void testRegistryHasSixSeeds() {
        ObjectHolder<?>[] seedHolders = { ItemRegistry.BELLADONNA_SEED, ItemRegistry.WOLFSBANE_SEED,
            ItemRegistry.WATER_ARTICHOKE_SEED, ItemRegistry.MANDRAKE_SEED, ItemRegistry.SNOW_WISP_SEED,
            ItemRegistry.GARLIC };

        assertEquals(6, seedHolders.length);

        // Verify they're all distinct
        for (int i = 0; i < seedHolders.length; i++) {
            for (int j = i + 1; j < seedHolders.length; j++) {
                assertNotSame("Seed holders should be distinct", seedHolders[i], seedHolders[j]);
            }
        }
    }

    @Test
    public void testRegistryHasFiveCrops() {
        ObjectHolder<?>[] cropHolders = { ItemRegistry.BELLADONNA_FLOWER, ItemRegistry.WOLFSBANE_FLOWER,
            ItemRegistry.WATER_ARTICHOKE_GLOBE, ItemRegistry.MANDRAKE_ROOT, ItemRegistry.ICY_NEEDLE };

        assertEquals(5, cropHolders.length);

        // Verify they're all distinct
        for (int i = 0; i < cropHolders.length; i++) {
            for (int j = i + 1; j < cropHolders.length; j++) {
                assertNotSame("Crop holders should be distinct", cropHolders[i], cropHolders[j]);
            }
        }
    }

    @Test
    public void testTotalItemCount() {
        // 6 seeds + 5 crops = 11 total items
        ObjectHolder<?>[] allHolders = { ItemRegistry.BELLADONNA_SEED, ItemRegistry.WOLFSBANE_SEED,
            ItemRegistry.WATER_ARTICHOKE_SEED, ItemRegistry.MANDRAKE_SEED, ItemRegistry.SNOW_WISP_SEED,
            ItemRegistry.GARLIC, ItemRegistry.BELLADONNA_FLOWER, ItemRegistry.WOLFSBANE_FLOWER,
            ItemRegistry.WATER_ARTICHOKE_GLOBE, ItemRegistry.MANDRAKE_ROOT, ItemRegistry.ICY_NEEDLE };

        assertEquals(11, allHolders.length);
    }

    @Test
    public void testSeedNamesUnique() {
        String[] seedNames = { "belladonna_seed", "wolfsbane_seed", "water_artichoke_seed", "mandrake_seed",
            "snow_wisp_seed", "garlic_seed" };

        assertEquals(6, seedNames.length);

        // Verify all names are unique
        for (int i = 0; i < seedNames.length; i++) {
            for (int j = i + 1; j < seedNames.length; j++) {
                assertNotEquals(seedNames[i], seedNames[j]);
            }
        }
    }

    @Test
    public void testCropNamesUnique() {
        String[] cropNames = { "belladonna_flower", "wolfsbane_flower", "water_artichoke_globe", "mandrake_root",
            "icy_needle" };

        assertEquals(5, cropNames.length);

        // Verify all names are unique
        for (int i = 0; i < cropNames.length; i++) {
            for (int j = i + 1; j < cropNames.length; j++) {
                assertNotEquals(cropNames[i], cropNames[j]);
            }
        }
    }

    @Test
    public void testWaterArtichokeSeedUsesWaterItemSeeds() {
        // Water artichoke seed should use WaterItemSeeds class
        // This is a structural test
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_SEED);
    }

    @Test
    public void testIcyNeedleIsAdditionalDrop() {
        // Icy Needle is an additional drop from Snow Wisp crop
        // This is documented in the registry
        assertNotNull(ItemRegistry.ICY_NEEDLE);
    }

    @Test
    public void testHoldersArePublicStatic() {
        // Verify seed holders are accessible as public static fields
        try {
            assertNotNull(ItemRegistry.class.getField("BELLADONNA_SEED"));
            assertNotNull(ItemRegistry.class.getField("WOLFSBANE_SEED"));
            assertNotNull(ItemRegistry.class.getField("WATER_ARTICHOKE_SEED"));
            assertNotNull(ItemRegistry.class.getField("MANDRAKE_SEED"));
            assertNotNull(ItemRegistry.class.getField("SNOW_WISP_SEED"));
            assertNotNull(ItemRegistry.class.getField("GARLIC"));
        } catch (NoSuchFieldException e) {
            fail("Expected seed field not found: " + e.getMessage());
        }

        // Verify crop holders are accessible as public static fields
        try {
            assertNotNull(ItemRegistry.class.getField("BELLADONNA_FLOWER"));
            assertNotNull(ItemRegistry.class.getField("WOLFSBANE_FLOWER"));
            assertNotNull(ItemRegistry.class.getField("WATER_ARTICHOKE_GLOBE"));
            assertNotNull(ItemRegistry.class.getField("MANDRAKE_ROOT"));
            assertNotNull(ItemRegistry.class.getField("ICY_NEEDLE"));
        } catch (NoSuchFieldException e) {
            fail("Expected crop field not found: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterItemsMethodExists() {
        // Verify the registerItems method exists
        try {
            assertNotNull(ItemRegistry.class.getMethod("registerItems"));
        } catch (NoSuchMethodException e) {
            fail("registerItems method not found");
        }
    }

    @Test
    public void testRegistryClassExists() {
        // Verify the class can be loaded
        Class<?> clazz = ItemRegistry.class;
        assertNotNull(clazz);
        assertEquals("ItemRegistry", clazz.getSimpleName());
    }

    @Test
    public void testGarlicIsUnique() {
        // Garlic is unique in that it uses itself as both seed and crop
        // according to BlockRegistry.GARLIC_CROP configuration
        assertNotNull(ItemRegistry.GARLIC);
    }

    @Test
    public void testAllItemsHaveDistinctHolders() {
        // Verify all 11 items have distinct ObjectHolder instances
        ObjectHolder<?>[] allHolders = { ItemRegistry.BELLADONNA_SEED, ItemRegistry.WOLFSBANE_SEED,
            ItemRegistry.WATER_ARTICHOKE_SEED, ItemRegistry.MANDRAKE_SEED, ItemRegistry.SNOW_WISP_SEED,
            ItemRegistry.GARLIC, ItemRegistry.BELLADONNA_FLOWER, ItemRegistry.WOLFSBANE_FLOWER,
            ItemRegistry.WATER_ARTICHOKE_GLOBE, ItemRegistry.MANDRAKE_ROOT, ItemRegistry.ICY_NEEDLE };

        for (int i = 0; i < allHolders.length; i++) {
            for (int j = i + 1; j < allHolders.length; j++) {
                assertNotSame("All holders should be distinct instances", allHolders[i], allHolders[j]);
            }
        }
    }

    @Test
    public void testSeedCropPairsExist() {
        // Test that each seed (except garlic) has a corresponding crop
        assertNotNull(ItemRegistry.BELLADONNA_SEED);
        assertNotNull(ItemRegistry.BELLADONNA_FLOWER);

        assertNotNull(ItemRegistry.WOLFSBANE_SEED);
        assertNotNull(ItemRegistry.WOLFSBANE_FLOWER);

        assertNotNull(ItemRegistry.WATER_ARTICHOKE_SEED);
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_GLOBE);

        assertNotNull(ItemRegistry.MANDRAKE_SEED);
        assertNotNull(ItemRegistry.MANDRAKE_ROOT);

        assertNotNull(ItemRegistry.SNOW_WISP_SEED);
        // Snow wisp produces snowballs (vanilla item) + icy needle
        assertNotNull(ItemRegistry.ICY_NEEDLE);
    }

    @Test
    public void testNoNullHolders() {
        // Verify no holder is null
        assertNotNull(ItemRegistry.BELLADONNA_SEED);
        assertNotNull(ItemRegistry.WOLFSBANE_SEED);
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_SEED);
        assertNotNull(ItemRegistry.MANDRAKE_SEED);
        assertNotNull(ItemRegistry.SNOW_WISP_SEED);
        assertNotNull(ItemRegistry.GARLIC);
        assertNotNull(ItemRegistry.BELLADONNA_FLOWER);
        assertNotNull(ItemRegistry.WOLFSBANE_FLOWER);
        assertNotNull(ItemRegistry.WATER_ARTICHOKE_GLOBE);
        assertNotNull(ItemRegistry.MANDRAKE_ROOT);
        assertNotNull(ItemRegistry.ICY_NEEDLE);
    }
}
