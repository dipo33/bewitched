package com.dipo33.bewitched.block;

import static org.junit.Assert.*;

import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;

import org.junit.Before;
import org.junit.Test;

import com.dipo33.bewitched.data.ObjectHolder;

/**
 * Comprehensive test suite for BwBlockCrops validation logic.
 * Tests setter validation, stage mapping, and builder pattern.
 */
public class BwBlockCropsTest {

    private ObjectHolder<Item> mockSeed;
    private ObjectHolder<Item> mockCrop;
    private ObjectHolder<Item> mockAdditionalItem;

    @Before
    public void setUp() {
        // Create mock ObjectHolders that return null (we're not testing Minecraft functionality)
        mockSeed = new ObjectHolder<>(() -> null);
        mockCrop = new ObjectHolder<>(() -> null);
        mockAdditionalItem = new ObjectHolder<>(() -> null);
    }

    @Test
    public void testConstructorWithValidParameters() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        assertNotNull(crop);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullSeed() {
        new BwBlockCrops(null, mockCrop);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullCrop() {
        new BwBlockCrops(mockSeed, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithBothNull() {
        new BwBlockCrops(null, null);
    }

    @Test
    public void testSetStagesValid() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Test all valid stage values
        for (int i = 1; i <= 8; i++) {
            BwBlockCrops result = crop.setStages(i);
            assertSame("setStages should return this for chaining", crop, result);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStagesTooLow() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.setStages(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStagesNegative() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.setStages(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStagesTooHigh() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.setStages(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStagesWayTooHigh() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.setStages(100);
    }

    @Test
    public void testSetStagesBoundaryValues() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Test minimum valid value
        BwBlockCrops result1 = crop.setStages(1);
        assertSame(crop, result1);

        // Test maximum valid value
        BwBlockCrops result8 = crop.setStages(8);
        assertSame(crop, result8);
    }

    @Test
    public void testSetPlantTypeValid() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        BwBlockCrops result = crop.setPlantType(EnumPlantType.Water);
        assertSame("setPlantType should return this for chaining", crop, result);
    }

    @Test(expected = NullPointerException.class)
    public void testSetPlantTypeNull() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.setPlantType(null);
    }

    @Test
    public void testSetPlantTypeDifferentTypes() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Test with different plant types
        crop.setPlantType(EnumPlantType.Crop);
        crop.setPlantType(EnumPlantType.Water);
        crop.setPlantType(EnumPlantType.Desert);
        crop.setPlantType(EnumPlantType.Cave);
        crop.setPlantType(EnumPlantType.Plains);
        crop.setPlantType(EnumPlantType.Beach);
        crop.setPlantType(EnumPlantType.Nether);
    }

    @Test
    public void testAddAdditionalDropsValid() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        BwBlockCrops result = crop.addAdditionalDrops(mockAdditionalItem, 0.5);
        assertSame("addAdditionalDrops should return this for chaining", crop, result);
    }

    @Test(expected = NullPointerException.class)
    public void testAddAdditionalDropsNullItem() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(null, 0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAdditionalDropsNegativeChance() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(mockAdditionalItem, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAdditionalDropsChanceTooHigh() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(mockAdditionalItem, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAdditionalDropsNaNChance() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(mockAdditionalItem, Double.NaN);
    }

    @Test
    public void testAddAdditionalDropsBoundaryValues() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Test minimum valid value
        BwBlockCrops result0 = crop.addAdditionalDrops(mockAdditionalItem, 0.0);
        assertSame(crop, result0);

        // Test maximum valid value
        BwBlockCrops result1 = crop.addAdditionalDrops(mockAdditionalItem, 1.0);
        assertSame(crop, result1);
    }

    @Test
    public void testAddAdditionalDropsMultipleTimes() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        ObjectHolder<Item> item1 = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> item2 = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> item3 = new ObjectHolder<>(() -> null);

        crop.addAdditionalDrops(item1, 0.1);
        crop.addAdditionalDrops(item2, 0.2);
        crop.addAdditionalDrops(item3, 0.3);
    }

    @Test
    public void testBuilderPatternChaining() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop).setStages(5)
            .setPlantType(EnumPlantType.Water)
            .addAdditionalDrops(mockAdditionalItem, 0.1);

        assertNotNull(crop);
    }

    @Test
    public void testBuilderPatternPartial() {
        // Test that not all methods need to be called
        BwBlockCrops crop1 = new BwBlockCrops(mockSeed, mockCrop).setStages(5);

        BwBlockCrops crop2 = new BwBlockCrops(mockSeed, mockCrop).setPlantType(EnumPlantType.Water);

        BwBlockCrops crop3 = new BwBlockCrops(mockSeed, mockCrop).addAdditionalDrops(mockAdditionalItem, 0.5);

        assertNotNull(crop1);
        assertNotNull(crop2);
        assertNotNull(crop3);
    }

    @Test
    public void testRealWorldUsageBelladonna() {
        // Simulate the BELLADONNA_CROP configuration from BlockRegistry
        ObjectHolder<Item> seed = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> flower = new ObjectHolder<>(() -> null);

        BwBlockCrops crop = new BwBlockCrops(seed, flower).setStages(5);

        assertNotNull(crop);
    }

    @Test
    public void testRealWorldUsageWolfsbane() {
        // Simulate the WOLFSBANE_CROP configuration from BlockRegistry
        ObjectHolder<Item> seed = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> flower = new ObjectHolder<>(() -> null);

        BwBlockCrops crop = new BwBlockCrops(seed, flower).setStages(8);

        assertNotNull(crop);
    }

    @Test
    public void testRealWorldUsageWaterArtichoke() {
        // Simulate the WATER_ARTICHOKE_CROP configuration from BlockRegistry
        ObjectHolder<Item> seed = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> globe = new ObjectHolder<>(() -> null);

        BwBlockCrops crop = new BwBlockCrops(seed, globe).setStages(5)
            .setPlantType(EnumPlantType.Water);

        assertNotNull(crop);
    }

    @Test
    public void testRealWorldUsageSnowWisp() {
        // Simulate the SNOW_WISP_CROP configuration from BlockRegistry
        ObjectHolder<Item> seed = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> snowball = new ObjectHolder<>(() -> null);
        ObjectHolder<Item> icyNeedle = new ObjectHolder<>(() -> null);

        BwBlockCrops crop = new BwBlockCrops(seed, snowball).setStages(5)
            .addAdditionalDrops(icyNeedle, 0.1D);

        assertNotNull(crop);
    }

    @Test
    public void testRealWorldUsageGarlic() {
        // Simulate the GARLIC_CROP configuration from BlockRegistry
        ObjectHolder<Item> garlic = new ObjectHolder<>(() -> null);

        BwBlockCrops crop = new BwBlockCrops(garlic, garlic).setStages(6);

        assertNotNull(crop);
    }

    @Test
    public void testAddAdditionalDropsVaryingChances() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Test various valid chance values
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.0);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.1);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.25);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.5);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.75);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 0.99);
        crop.addAdditionalDrops(new ObjectHolder<>(() -> null), 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAdditionalDropsInfinityChance() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(mockAdditionalItem, Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAdditionalDropsNegativeInfinityChance() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);
        crop.addAdditionalDrops(mockAdditionalItem, Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testStagesCanBeSetMultipleTimes() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        crop.setStages(3);
        crop.setStages(5);
        crop.setStages(8);
        crop.setStages(1);

        // Should not throw any exceptions
        assertNotNull(crop);
    }

    @Test
    public void testPlantTypeCanBeSetMultipleTimes() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        crop.setPlantType(EnumPlantType.Crop);
        crop.setPlantType(EnumPlantType.Water);
        crop.setPlantType(EnumPlantType.Desert);

        // Should not throw any exceptions
        assertNotNull(crop);
    }

    @Test
    public void testComplexChaining() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop).setStages(5)
            .addAdditionalDrops(new ObjectHolder<>(() -> null), 0.1)
            .setPlantType(EnumPlantType.Water)
            .addAdditionalDrops(new ObjectHolder<>(() -> null), 0.2)
            .setStages(6)
            .addAdditionalDrops(new ObjectHolder<>(() -> null), 0.3);

        assertNotNull(crop);
    }

    @Test
    public void testAddAdditionalDropsSameItemMultipleTimes() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        // Adding the same item multiple times with different chances should be allowed
        crop.addAdditionalDrops(mockAdditionalItem, 0.1);
        crop.addAdditionalDrops(mockAdditionalItem, 0.2);
        crop.addAdditionalDrops(mockAdditionalItem, 0.3);

        assertNotNull(crop);
    }

    @Test
    public void testValidationErrorMessages() {
        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        try {
            crop.setStages(0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(
                e.getMessage()
                    .contains("1 and 8"));
            assertTrue(
                e.getMessage()
                    .contains("0"));
        }

        try {
            crop.setStages(10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(
                e.getMessage()
                    .contains("1 and 8"));
            assertTrue(
                e.getMessage()
                    .contains("10"));
        }

        try {
            crop.addAdditionalDrops(mockAdditionalItem, -0.5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(
                e.getMessage()
                    .contains("0.0 and 1.0"));
            assertTrue(
                e.getMessage()
                    .contains("-0.5"));
        }

        try {
            crop.addAdditionalDrops(mockAdditionalItem, 1.5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(
                e.getMessage()
                    .contains("0.0 and 1.0"));
            assertTrue(
                e.getMessage()
                    .contains("1.5"));
        }
    }

    @Test
    public void testNullPointerErrorMessages() {
        try {
            new BwBlockCrops(null, mockCrop);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertTrue(
                e.getMessage()
                    .contains("seed"));
        }

        try {
            new BwBlockCrops(mockSeed, null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertTrue(
                e.getMessage()
                    .contains("crop"));
        }

        BwBlockCrops crop = new BwBlockCrops(mockSeed, mockCrop);

        try {
            crop.setPlantType(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertTrue(
                e.getMessage()
                    .contains("plantType"));
        }

        try {
            crop.addAdditionalDrops(null, 0.5);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertTrue(
                e.getMessage()
                    .contains("item"));
        }
    }
}
