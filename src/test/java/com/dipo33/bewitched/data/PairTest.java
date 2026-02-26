package com.dipo33.bewitched.data;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Comprehensive test suite for Pair record class.
 * Tests record properties, equality, and edge cases.
 */
public class PairTest {

    @Test
    public void testBasicConstruction() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        assertEquals("test", pair.first());
        assertEquals(Integer.valueOf(42), pair.second());
    }

    @Test
    public void testNullValues() {
        Pair<String, String> pair = new Pair<>(null, null);
        assertNull(pair.first());
        assertNull(pair.second());
    }

    @Test
    public void testMixedNullValues() {
        Pair<String, Integer> pair1 = new Pair<>("value", null);
        assertEquals("value", pair1.first());
        assertNull(pair1.second());

        Pair<String, Integer> pair2 = new Pair<>(null, 42);
        assertNull(pair2.first());
        assertEquals(Integer.valueOf(42), pair2.second());
    }

    @Test
    public void testEquality() {
        Pair<String, Integer> pair1 = new Pair<>("test", 42);
        Pair<String, Integer> pair2 = new Pair<>("test", 42);

        assertEquals(pair1, pair2);
        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    public void testInequality() {
        Pair<String, Integer> pair1 = new Pair<>("test", 42);
        Pair<String, Integer> pair2 = new Pair<>("test", 43);
        Pair<String, Integer> pair3 = new Pair<>("other", 42);

        assertNotEquals(pair1, pair2);
        assertNotEquals(pair1, pair3);
        assertNotEquals(pair2, pair3);
    }

    @Test
    public void testEqualityWithNull() {
        Pair<String, String> pair1 = new Pair<>(null, "value");
        Pair<String, String> pair2 = new Pair<>(null, "value");
        Pair<String, String> pair3 = new Pair<>("other", "value");

        assertEquals(pair1, pair2);
        assertNotEquals(pair1, pair3);
    }

    @Test
    public void testSelfEquality() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        assertEquals(pair, pair);
    }

    @Test
    public void testToString() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        String str = pair.toString();

        // Record toString typically includes field names and values
        assertNotNull(str);
        assertTrue(str.contains("test"));
        assertTrue(str.contains("42"));
    }

    @Test
    public void testDifferentTypes() {
        Pair<String, Integer> stringIntPair = new Pair<>("text", 100);
        Pair<Double, Boolean> doubleBoolPair = new Pair<>(3.14, true);
        Pair<Integer, Integer> intIntPair = new Pair<>(1, 2);

        assertEquals("text", stringIntPair.first());
        assertEquals(Integer.valueOf(100), stringIntPair.second());

        assertEquals(Double.valueOf(3.14), doubleBoolPair.first());
        assertEquals(Boolean.TRUE, doubleBoolPair.second());

        assertEquals(Integer.valueOf(1), intIntPair.first());
        assertEquals(Integer.valueOf(2), intIntPair.second());
    }

    @Test
    public void testSameTypeValues() {
        Pair<String, String> pair = new Pair<>("first", "second");
        assertEquals("first", pair.first());
        assertEquals("second", pair.second());
        assertNotEquals(pair.first(), pair.second());
    }

    @Test
    public void testIdenticalValues() {
        Pair<String, String> pair = new Pair<>("same", "same");
        assertEquals("same", pair.first());
        assertEquals("same", pair.second());
        assertEquals(pair.first(), pair.second());
    }

    @Test
    public void testComplexObjects() {
        ObjectHolder<String> holder1 = new ObjectHolder<>(() -> "value1");
        ObjectHolder<String> holder2 = new ObjectHolder<>(() -> "value2");

        Pair<ObjectHolder<String>, ObjectHolder<String>> pair = new Pair<>(holder1, holder2);

        assertSame(holder1, pair.first());
        assertSame(holder2, pair.second());
    }

    @Test
    public void testPairOfPairs() {
        Pair<String, Integer> innerPair1 = new Pair<>("a", 1);
        Pair<String, Integer> innerPair2 = new Pair<>("b", 2);
        Pair<Pair<String, Integer>, Pair<String, Integer>> outerPair = new Pair<>(innerPair1, innerPair2);

        assertEquals(innerPair1, outerPair.first());
        assertEquals(innerPair2, outerPair.second());
        assertEquals(
            "a",
            outerPair.first()
                .first());
        assertEquals(
            Integer.valueOf(2),
            outerPair.second()
                .second());
    }

    @Test
    public void testImmutability() {
        // Records are immutable by default
        String originalFirst = "original";
        Integer originalSecond = 42;
        Pair<String, Integer> pair = new Pair<>(originalFirst, originalSecond);

        // Verify we can't change the values (no setters should exist)
        assertEquals(originalFirst, pair.first());
        assertEquals(originalSecond, pair.second());

        // Even if we change the original variables, the pair remains unchanged
        originalFirst = "changed";
        assertEquals("original", pair.first());
    }

    @Test
    public void testEqualityDifferentObjectSameValue() {
        Pair<String, Integer> pair1 = new Pair<>(new String("test"), new Integer(42));
        Pair<String, Integer> pair2 = new Pair<>(new String("test"), new Integer(42));

        assertEquals(pair1, pair2);
    }

    @Test
    public void testNotEqualToNull() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        assertNotEquals(null, pair);
    }

    @Test
    public void testNotEqualToDifferentClass() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        assertNotEquals("test", pair);
        assertNotEquals(42, pair);
    }

    @Test
    public void testHashCodeConsistency() {
        Pair<String, Integer> pair = new Pair<>("test", 42);
        int hash1 = pair.hashCode();
        int hash2 = pair.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCodeWithNulls() {
        Pair<String, String> pair1 = new Pair<>(null, null);
        Pair<String, String> pair2 = new Pair<>(null, null);

        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    public void testDoubleAsType() {
        // This is used in BwBlockCrops for drop chances
        Pair<ObjectHolder<String>, Double> dropPair = new Pair<>(new ObjectHolder<>(() -> "item"), 0.5);

        assertEquals(Double.valueOf(0.5), dropPair.second());
    }

    @Test
    public void testZeroValues() {
        Pair<Integer, Double> pair = new Pair<>(0, 0.0);
        assertEquals(Integer.valueOf(0), pair.first());
        assertEquals(Double.valueOf(0.0), pair.second());
    }

    @Test
    public void testNegativeValues() {
        Pair<Integer, Double> pair = new Pair<>(-1, -0.5);
        assertEquals(Integer.valueOf(-1), pair.first());
        assertEquals(Double.valueOf(-0.5), pair.second());
    }

    @Test
    public void testEmptyString() {
        Pair<String, String> pair = new Pair<>("", "");
        assertEquals("", pair.first());
        assertEquals("", pair.second());
    }

    @Test
    public void testLargeValues() {
        Pair<Long, Double> pair = new Pair<>(Long.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Long.valueOf(Long.MAX_VALUE), pair.first());
        assertEquals(Double.valueOf(Double.MAX_VALUE), pair.second());
    }

    @Test
    public void testUsageAsInBwBlockCrops() {
        // Test the actual usage pattern from BwBlockCrops
        ObjectHolder<String> itemHolder = new ObjectHolder<>(() -> "icy_needle");
        Double dropChance = 0.1;

        Pair<ObjectHolder<String>, Double> additionalDrop = new Pair<>(itemHolder, dropChance);

        assertEquals(itemHolder, additionalDrop.first());
        assertEquals(Double.valueOf(0.1), additionalDrop.second());
        assertEquals(
            "icy_needle",
            additionalDrop.first()
                .get());
    }
}
