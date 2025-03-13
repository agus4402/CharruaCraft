package agus4402.urumod.entity.client;

import agus4402.core.entity.client.VariantEntityGeoModel;
import agus4402.urumod.Urumod;
import agus4402.urumod.entity.custom.CapybaraEntity;
import agus4402.urumod.entity.variant.CapybaraVariant;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.Map;

public class CapybaraModel extends VariantEntityGeoModel<CapybaraVariant, CapybaraEntity> {

    @Override
    protected void variantSetup(EnumMap<CapybaraVariant, ResourceLocation> map) {
            map.put(CapybaraVariant.DEFAULT, new ResourceLocation(Urumod.MOD_ID, "textures/entity/capybara.png"));
            map.put(CapybaraVariant.DARK, new ResourceLocation(Urumod.MOD_ID, "textures/entity/capybara-dark.png"));
    }

    public CapybaraModel() {
        super(new ResourceLocation(Urumod.MOD_ID, "capybara"), CapybaraVariant.class,true);
    }


}
