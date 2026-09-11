package com.pointlessbuilding.scratchpad.UI;

import com.mojang.math.Axis;
import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.tools.ScratchpadTools;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ScratchpadToolsUI extends Screen{

    private static final ResourceLocation TOOL_BOX = ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, "textures/gui/scratchpad_tools_overlay.png");

    private ToolButton<LocalPlayer> clapWipeTool;

    public ScratchpadToolsUI() {
        super(Component.literal("Scratchpad Tools"));
    }

    @Override
    protected void init() {
        super.init();
        if(this.minecraft != null && this.minecraft.player != null) {
            LocalPlayer player = this.minecraft.player;
            int i = this.width / 2;
            clapWipeTool = new ToolButton<>((i-99) + 152, 10, 
                TOOL_BOX, 152,
                ScratchpadTools::clapWipe, player);

            this.addRenderableWidget(clapWipeTool);
        }
    }
    
    @Override
    public void tick() {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = this.width / 2;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, -90.0f);
        guiGraphics.blit(TOOL_BOX, i - 99, 0, 0, 0, 198, 64);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(i, 55.0f, 0.0f);
            guiGraphics.pose().mulPose(Axis.ZP.rotation((float)Math.PI));
            guiGraphics.pose().translate(-9.0f, -9.0f, 0.0f);
            guiGraphics.blit(TOOL_BOX, 0, 0, 198, 0, 18, 18);
            guiGraphics.pose().popPose();

        guiGraphics.pose().popPose();

        for(Renderable renderable: this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }

    }

    @Override
    public void removed() {
        super.removed();
        ScratchpadOverlay overlay = ScratchpadOverlay.getById();
        if(overlay != null) overlay.startOverlayCloseAnimation();
    }

}
