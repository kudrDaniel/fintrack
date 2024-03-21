package ru.duckcoder.fintrack.core.util;

import lombok.NonNull;

import java.io.Serializable;

public class Nullable<T> implements Serializable {
    private T value;

    private Nullable() {}

    public static <V> Nullable<V> of(@NonNull V value) {
        Nullable<V> result = new Nullable<>();
        result.value = value;
        return result;
    }

    public static <V> Nullable<V> empty() {
        Nullable<V> result = new Nullable<>();
        result.value = null;
        return result;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public T get() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.value == null
                ? 0
                : this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Nullable<?>)
                && this.isPresent()
                && ((Nullable<?>) obj).isPresent()
                && this.value.equals(((Nullable<?>) obj).get());
    }

    @Override
    public String toString() {
        return new StringBuilder("value:").append(this.value).toString();
    }
}
