package com.pointlessbuilding.scratchpad.UI;

import com.mojang.math.Axis;
import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ScratchpadToolsUI extends Screen{

    private static final ResourceLocation TOOL_BOX = ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, "textures/gui/scratchpad_tools_overlay.png");

    public ScratchpadToolsUI() {
        super(Component.literal("Scratchpad Tools"));
    }

    @Override
    protected void init() {
    }
    
    @Override
    public void tick() {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        float y_offset = 0.0f;
        float rad_offset = (float) Math.PI;

        int i = this.width / 2;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, y_offset, -90.0f);
        guiGraphics.blit(TOOL_BOX, i - 99, 0, 0, 0, 198, 64);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(i, 55.0f, 0.0f);
            guiGraphics.pose().mulPose(Axis.ZP.rotation(rad_offset));
            guiGraphics.pose().translate(-9.0f, -9.0f, 0.0f);
            guiGraphics.blit(TOOL_BOX, 0, 0, 198, 0, 18, 18);
            guiGraphics.pose().popPose();

        guiGraphics.pose().popPose();

    }

    @Override
    public void removed() {
        super.removed();
        ScratchpadOverlay overlay = ScratchpadOverlay.getById();
        if(overlay != null) overlay.startOverlayCloseAnimation();
    }

}
