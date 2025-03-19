package agus4402.core.entity;

import agus4402.urumod.entity.variant.CapybaraVariant;
import software.bernie.geckolib.animatable.GeoEntity;

public interface VariantGeoEntity<T> extends GeoEntity {
    void setVariant(T variant);

    public T getVariant();

    int getTypeVariant();
}
