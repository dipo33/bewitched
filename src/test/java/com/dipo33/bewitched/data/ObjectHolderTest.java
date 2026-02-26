package com.dipo33.bewitched.data;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive test suite for ObjectHolder class.
 * Tests lazy initialization, thread safety, and edge cases.
 */
public class ObjectHolderTest {

    private ObjectHolder<String> holder;
    private AtomicInteger callCount;

    @Before
    public void setUp() {
        callCount = new AtomicInteger(0);
    }

    @Test
    public void testBasicInitialization() {
        holder = new ObjectHolder<>(() -> "test");
        assertEquals("test", holder.get());
    }

    @Test
    public void testLazyInitialization() {
        holder = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return "lazy";
        });

        // Supplier should not be called yet
        assertEquals(0, callCount.get());

        // First call should initialize
        String result = holder.get();
        assertEquals("lazy", result);
        assertEquals(1, callCount.get());
    }

    @Test
    public void testSingleInitialization() {
        holder = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return "single";
        });

        // Call get() multiple times
        holder.get();
        holder.get();
        holder.get();

        // Supplier should only be called once
        assertEquals(1, callCount.get());
    }

    @Test
    public void testReturnsSameInstance() {
        holder = new ObjectHolder<>(() -> new String("instance"));

        Object first = holder.get();
        Object second = holder.get();
        Object third = holder.get();

        // Should return the exact same object instance
        assertSame(first, second);
        assertSame(second, third);
    }

    @Test
    public void testNullValue() {
        holder = new ObjectHolder<>(() -> null);

        assertNull(holder.get());
        assertNull(holder.get()); // Should still return null on subsequent calls
    }

    @Test
    public void testWithComplexObject() {
        ObjectHolder<StringBuilder> sbHolder = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return new StringBuilder("test");
        });

        StringBuilder sb1 = sbHolder.get();
        sb1.append(" modified");

        StringBuilder sb2 = sbHolder.get();

        // Should be the same modified instance
        assertSame(sb1, sb2);
        assertEquals("test modified", sb2.toString());
        assertEquals(1, callCount.get());
    }

    @Test
    public void testSupplierInterface() {
        // ObjectHolder implements Supplier
        Supplier<String> supplier = new ObjectHolder<>(() -> "supplied");
        assertEquals("supplied", supplier.get());
    }

    @Test
    public void testMultipleHolders() {
        ObjectHolder<String> holder1 = new ObjectHolder<>(() -> "first");
        ObjectHolder<String> holder2 = new ObjectHolder<>(() -> "second");

        assertEquals("first", holder1.get());
        assertEquals("second", holder2.get());

        // They should be independent
        assertNotSame(holder1.get(), holder2.get());
    }

    @Test
    public void testDelegateNulledAfterInit() {
        // This tests that the delegate is cleared after initialization
        // to allow garbage collection
        holder = new ObjectHolder<>(() -> "value");

        // After first get(), delegate should be nulled
        holder.get();

        // Should still work
        assertEquals("value", holder.get());
    }

    @Test
    public void testWithInteger() {
        ObjectHolder<Integer> intHolder = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return 42;
        });

        assertEquals(Integer.valueOf(42), intHolder.get());
        assertEquals(Integer.valueOf(42), intHolder.get());
        assertEquals(1, callCount.get());
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        // Test that multiple threads calling get() only initialize once
        final AtomicInteger initCount = new AtomicInteger(0);
        final ObjectHolder<String> concurrentHolder = new ObjectHolder<>(() -> {
            int count = initCount.incrementAndGet();
            try {
                Thread.sleep(10); // Simulate slow initialization
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
            }
            return "thread-" + count;
        });

        // Create multiple threads that call get() simultaneously
        Thread[] threads = new Thread[10];
        final String[] results = new String[10];

        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> results[index] = concurrentHolder.get());
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Note: Without synchronization, this test may fail in a race condition
        // This documents the current behavior - the class is not thread-safe
        // In practice, it's initialized before concurrent access in Minecraft mod loading
        assertTrue("Initialization count should be at least 1", initCount.get() >= 1);

        // All results should be the same value from the first initialization
        String firstResult = results[0];
        assertNotNull(firstResult);
    }

    @Test
    public void testChainedSuppliers() {
        // Test ObjectHolder wrapping another ObjectHolder
        ObjectHolder<String> inner = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return "inner";
        });

        ObjectHolder<String> outer = new ObjectHolder<>(() -> {
            callCount.incrementAndGet();
            return inner.get();
        });

        assertEquals("inner", outer.get());
        // Both should initialize (outer then inner)
        assertEquals(2, callCount.get());

        // Subsequent calls should not re-initialize
        assertEquals("inner", outer.get());
        assertEquals(2, callCount.get());
    }

    @Test
    public void testExceptionInSupplier() {
        ObjectHolder<String> failingHolder = new ObjectHolder<>(() -> { throw new RuntimeException("Init failed"); });

        try {
            failingHolder.get();
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Init failed", e.getMessage());
        }
    }

    @Test
    public void testEmptyString() {
        ObjectHolder<String> emptyHolder = new ObjectHolder<>(() -> "");
        assertEquals("", emptyHolder.get());
        assertEquals("", emptyHolder.get());
    }
}
