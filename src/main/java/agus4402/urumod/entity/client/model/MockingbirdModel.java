package agus4402.urumod.entity.client.model;

import agus4402.core.entity.client.VariantEntityGeoModel;
import agus4402.urumod.Urumod;
import agus4402.urumod.entity.custom.CapybaraEntity;
import agus4402.urumod.entity.custom.MockingbirdEntity;
import agus4402.urumod.entity.variant.CapybaraVariant;
import agus4402.urumod.entity.variant.MockingbirdVariant;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;

public class MockingbirdModel extends VariantEntityGeoModel<MockingbirdVariant, MockingbirdEntity> {

    @Override
    protected void variantSetup(EnumMap<MockingbirdVariant, ResourceLocation> map) {
            map.put(MockingbirdVariant.DEFAULT, new ResourceLocation(Urumod.MOD_ID, "textures/entity/mockingbird.png"));
    }

    public MockingbirdModel() {
        super(new ResourceLocation(Urumod.MOD_ID, "mockingbird"), MockingbirdVariant.class,true);
    }


}
