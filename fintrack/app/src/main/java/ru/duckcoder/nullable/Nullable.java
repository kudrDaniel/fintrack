package ru.duckcoder.nullable;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;

public class Nullable<T> implements Serializable {
    private T value;

    private Nullable() {}

    public static <V> Nullable<V> of(@Nonnull V value) {
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
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Nullable<?>)
            if (((Nullable<?>) object).get() != null)
                if (this.value != null)
                    return ((Nullable<?>) object).get().equals(this.value);
        return false;
    }

    @Override
    public int hashCode() {
        return this.value == null
                ? 0
                : this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value == null
                ? null
                : this.value.toString();
    }
}
