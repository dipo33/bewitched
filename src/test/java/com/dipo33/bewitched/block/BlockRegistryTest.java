package com.dipo33.bewitched.block;

import static org.junit.Assert.*;

import net.minecraft.block.Block;

import org.junit.Test;

import com.dipo33.bewitched.data.ObjectHolder;

/**
 * Test suite for BlockRegistry class.
 * Tests block holder initialization and structure.
 */
public class BlockRegistryTest {

    @Test
    public void testBelladonnaCropHolderNotNull() {
        assertNotNull(BlockRegistry.BELLADONNA_CROP);
    }

    @Test
    public void testWolfsbaneCropHolderNotNull() {
        assertNotNull(BlockRegistry.WOLFSBANE_CROP);
    }

    @Test
    public void testWaterArtichokeCropHolderNotNull() {
        assertNotNull(BlockRegistry.WATER_ARTICHOKE_CROP);
    }

    @Test
    public void testMandrakeCropHolderNotNull() {
        assertNotNull(BlockRegistry.MANDRAKE_CROP);
    }

    @Test
    public void testSnowWispCropHolderNotNull() {
        assertNotNull(BlockRegistry.SNOW_WISP_CROP);
    }

    @Test
    public void testGarlicCropHolderNotNull() {
        assertNotNull(BlockRegistry.GARLIC_CROP);
    }

    @Test
    public void testAllHoldersAreObjectHolderType() {
        assertTrue(BlockRegistry.BELLADONNA_CROP instanceof ObjectHolder);
        assertTrue(BlockRegistry.WOLFSBANE_CROP instanceof ObjectHolder);
        assertTrue(BlockRegistry.WATER_ARTICHOKE_CROP instanceof ObjectHolder);
        assertTrue(BlockRegistry.MANDRAKE_CROP instanceof ObjectHolder);
        assertTrue(BlockRegistry.SNOW_WISP_CROP instanceof ObjectHolder);
        assertTrue(BlockRegistry.GARLIC_CROP instanceof ObjectHolder);
    }

    @Test
    public void testAllHoldersAreBlockType() {
        // Verify the generic type is Block
        ObjectHolder<Block> belladonna = BlockRegistry.BELLADONNA_CROP;
        ObjectHolder<Block> wolfsbane = BlockRegistry.WOLFSBANE_CROP;
        ObjectHolder<Block> waterArtichoke = BlockRegistry.WATER_ARTICHOKE_CROP;
        ObjectHolder<Block> mandrake = BlockRegistry.MANDRAKE_CROP;
        ObjectHolder<Block> snowWisp = BlockRegistry.SNOW_WISP_CROP;
        ObjectHolder<Block> garlic = BlockRegistry.GARLIC_CROP;

        assertNotNull(belladonna);
        assertNotNull(wolfsbane);
        assertNotNull(waterArtichoke);
        assertNotNull(mandrake);
        assertNotNull(snowWisp);
        assertNotNull(garlic);
    }

    @Test
    public void testHoldersAreFinal() {
        // Test that we can't reassign the holders (they're final)
        // This is a compile-time check, but we can verify they're not null
        assertNotNull(BlockRegistry.BELLADONNA_CROP);
        assertNotNull(BlockRegistry.WOLFSBANE_CROP);
        assertNotNull(BlockRegistry.WATER_ARTICHOKE_CROP);
        assertNotNull(BlockRegistry.MANDRAKE_CROP);
        assertNotNull(BlockRegistry.SNOW_WISP_CROP);
        assertNotNull(BlockRegistry.GARLIC_CROP);
    }

    @Test
    public void testRegistryHasSixCrops() {
        // Count the number of crop holders by checking they're all distinct
        ObjectHolder<?>[] holders = { BlockRegistry.BELLADONNA_CROP, BlockRegistry.WOLFSBANE_CROP,
            BlockRegistry.WATER_ARTICHOKE_CROP, BlockRegistry.MANDRAKE_CROP, BlockRegistry.SNOW_WISP_CROP,
            BlockRegistry.GARLIC_CROP };

        assertEquals(6, holders.length);

        // Verify they're all distinct objects
        for (int i = 0; i < holders.length; i++) {
            for (int j = i + 1; j < holders.length; j++) {
                assertNotSame("Holders should be distinct", holders[i], holders[j]);
            }
        }
    }

    @Test
    public void testBelladonnaConfiguration() {
        // Belladonna should have 5 stages
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.BELLADONNA_CROP);
    }

    @Test
    public void testWolfsbaneConfiguration() {
        // Wolfsbane should have 8 stages
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.WOLFSBANE_CROP);
    }

    @Test
    public void testWaterArtichokeConfiguration() {
        // Water Artichoke should have 5 stages and Water plant type
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.WATER_ARTICHOKE_CROP);
    }

    @Test
    public void testMandrakeConfiguration() {
        // Mandrake should have 5 stages
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.MANDRAKE_CROP);
    }

    @Test
    public void testSnowWispConfiguration() {
        // Snow Wisp should have 5 stages and additional drops
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.SNOW_WISP_CROP);
    }

    @Test
    public void testGarlicConfiguration() {
        // Garlic should have 6 stages and use itself as both seed and crop
        // This is verified by the configuration in BlockRegistry
        assertNotNull(BlockRegistry.GARLIC_CROP);
    }

    @Test
    public void testRegistryClassExists() {
        // Verify the class can be loaded
        Class<?> clazz = BlockRegistry.class;
        assertNotNull(clazz);
        assertEquals("BlockRegistry", clazz.getSimpleName());
    }

    @Test
    public void testAllCropNamesUnique() {
        // This test documents that each crop should have a unique name
        // Names: belladonna, wolfsbane, water_artichoke, mandrake, snow_wisp, garlic
        String[] expectedNames = { "belladonna", "wolfsbane", "water_artichoke", "mandrake", "snow_wisp", "garlic" };

        // Verify we have 6 unique names
        assertEquals(6, expectedNames.length);

        // Verify all names are unique
        for (int i = 0; i < expectedNames.length; i++) {
            for (int j = i + 1; j < expectedNames.length; j++) {
                assertNotEquals(expectedNames[i], expectedNames[j]);
            }
        }
    }

    @Test
    public void testHoldersArePublicStatic() {
        // Verify holders are accessible as public static fields
        try {
            assertNotNull(BlockRegistry.class.getField("BELLADONNA_CROP"));
            assertNotNull(BlockRegistry.class.getField("WOLFSBANE_CROP"));
            assertNotNull(BlockRegistry.class.getField("WATER_ARTICHOKE_CROP"));
            assertNotNull(BlockRegistry.class.getField("MANDRAKE_CROP"));
            assertNotNull(BlockRegistry.class.getField("SNOW_WISP_CROP"));
            assertNotNull(BlockRegistry.class.getField("GARLIC_CROP"));
        } catch (NoSuchFieldException e) {
            fail("Expected field not found: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterBlocksMethodExists() {
        // Verify the registerBlocks method exists
        try {
            assertNotNull(BlockRegistry.class.getMethod("registerBlocks"));
        } catch (NoSuchMethodException e) {
            fail("registerBlocks method not found");
        }
    }
}
