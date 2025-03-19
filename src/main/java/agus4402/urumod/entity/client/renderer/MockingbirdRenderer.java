package agus4402.urumod.entity.client.renderer;

import agus4402.urumod.entity.client.model.CapybaraModel;
import agus4402.urumod.entity.client.model.MockingbirdModel;
import agus4402.urumod.entity.custom.CapybaraEntity;
import agus4402.urumod.entity.custom.MockingbirdEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MockingbirdRenderer extends GeoEntityRenderer<MockingbirdEntity> {
    public MockingbirdRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,  (GeoModel) new MockingbirdModel());
    }
    @Override
    public void render(MockingbirdEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(!entity.isBaby()) {
            poseStack.scale(2f, 2f, 2f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
