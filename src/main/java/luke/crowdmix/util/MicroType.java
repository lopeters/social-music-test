package luke.crowdmix.util;

import java.util.Objects;

public class MicroType<T> {
    private final T value;

    public MicroType(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public boolean equals(Object other) {
        return other instanceof MicroType && Objects.equals(value, ((MicroType) other).value);
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return String.valueOf(value);
    }
}
