package agus4402.core.entity.examples;

import java.util.Arrays;
import java.util.Comparator;

public enum EntityVariantEnum {
    DEFAULT(0),
    DARK(1);

    private static final EntityVariantEnum[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                                                                                         comparingInt(EntityVariantEnum::getId)).toArray(EntityVariantEnum[]::new);
    private final int id;

    EntityVariantEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EntityVariantEnum byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
