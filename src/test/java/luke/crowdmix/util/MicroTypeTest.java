package luke.crowdmix.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MicroTypeTest {
    @Test
    public void microTypesWithSameValueAreEqual() {
        assertEquals(new MicroType<>("foo"), new MicroType<>("foo"));
        assertNotEquals(new MicroType<>("foo"), new MicroType<>("bar"));
        assertNotEquals(new MicroType<>("foo"), new MicroType<>(1));
    }

    @Test
    public void toStringOfValue() {
        assertEquals(new MicroType<>("foo").toString(), "foo");
    }
}