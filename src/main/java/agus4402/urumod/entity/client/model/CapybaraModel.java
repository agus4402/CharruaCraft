package agus4402.urumod.entity.client.model;

import agus4402.core.entity.VariantGeoEntity;
import agus4402.core.entity.client.VariantEntityGeoModel;
import agus4402.urumod.Urumod;
import agus4402.urumod.entity.custom.CapybaraEntity;
import agus4402.urumod.entity.variant.CapybaraVariant;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

import java.util.EnumMap;
import java.util.Map;

public class CapybaraModel extends VariantEntityGeoModel<CapybaraVariant, CapybaraEntity> {

    @Override
    protected void variantSetup(EnumMap<CapybaraVariant, ResourceLocation> map) {
            map.put(CapybaraVariant.DEFAULT, new ResourceLocation(Urumod.MOD_ID, "textures/entity/capybara.png"));
            map.put(CapybaraVariant.DARK, new ResourceLocation(Urumod.MOD_ID, "textures/entity/capybara-dark.png"));
    }

    @Override
    public void setCustomAnimations(VariantGeoEntity<CapybaraEntity> animatable, long instanceId, AnimationState<VariantGeoEntity<CapybaraEntity>> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    public CapybaraModel() {
        super(new ResourceLocation(Urumod.MOD_ID, "capybara"), CapybaraVariant.class,true);
    }


}
