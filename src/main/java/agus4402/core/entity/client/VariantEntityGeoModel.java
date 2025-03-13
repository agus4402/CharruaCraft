package agus4402.core.entity.client;

import agus4402.core.entity.VariantGeoEntity;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.EnumMap;
import java.util.Map;

public  class VariantEntityGeoModel<T extends Enum<T>, K extends GeoEntity> extends DefaultedEntityGeoModel<VariantGeoEntity<K>> {

    protected final Map<T, ResourceLocation> VARIANT_LOCATIONS;

    protected VariantEntityGeoModel(ResourceLocation modelLocation, Class<T> variantClass, boolean turnsHead) {
        super(modelLocation, turnsHead);
        this.VARIANT_LOCATIONS = Util.make(Maps.newEnumMap(variantClass), this::variantSetup);
    }

    protected void variantSetup(EnumMap<T, ResourceLocation> map){

    }

    @Override
    public ResourceLocation getTextureResource(VariantGeoEntity<K> animatable) {
        return VARIANT_LOCATIONS.get(animatable.getVariant());
    }

}