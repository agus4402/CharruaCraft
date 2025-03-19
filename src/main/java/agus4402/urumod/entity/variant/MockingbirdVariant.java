package agus4402.urumod.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum MockingbirdVariant {
    DEFAULT(0);
    private static final MockingbirdVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                                                                                         comparingInt(MockingbirdVariant::getId)).toArray(MockingbirdVariant[]::new);
    private final int id;

    MockingbirdVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static MockingbirdVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
