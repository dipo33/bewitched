package com.dipo33.bewitched.data;

import java.util.function.Supplier;

public class ObjectHolder<T> implements Supplier<T> {
    private Supplier<? extends T> delegate;
    private T value;
    private boolean initialized;

    /**
     * Creates an ObjectHolder that will lazily initialize its value using the given delegate.
     *
     * @param delegate
     *     the supplier used to produce the value on the first call to {@code get()}
     */
    public ObjectHolder(Supplier<? extends T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Lazily initializes and returns the held value, memoizing the result for subsequent calls.
     *
     * @return the cached value produced by the delegate supplier
     */
    @Override
    public T get() {
        if (!this.initialized) {
            this.value = this.delegate.get();
            this.initialized = true;
            delegate = null;
        }

        return value;
    }
}
