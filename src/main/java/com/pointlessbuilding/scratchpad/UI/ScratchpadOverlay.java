package com.pointlessbuilding.scratchpad.UI;

import com.mojang.math.Axis;
import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ScratchpadOverlay implements IGuiOverlay{

    public static final String OVERLAY_ID = "scratchpad_tools";
    private static final ResourceLocation TOOL_BOX = ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, "textures/gui/scratchpad_tools_overlay.png");

    private static ScratchpadOverlay INSTANCE;
    private static int ARROW_ANIM_TIME = 20;
    private int arrowTicks = 0;

    private static int ANIM_TIME = 10;
    private int animTicks = 0;
    private boolean playCloseAnim = false;
    private boolean playOpeningAnim = false;

    public static ScratchpadOverlay getById() {
        if(INSTANCE == null) {
            var find = GuiOverlayManager.findOverlay(ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, OVERLAY_ID));
            if(find == null) return null;
            else INSTANCE = (ScratchpadOverlay) find.overlay();
        }
        
        return INSTANCE;
    }

    public void startOverlayCloseAnimation() {
        animTicks = 0;
        playCloseAnim = true;
    }

    public void startOverlayOpeningAnimation() {
        animTicks = 0;
        playOpeningAnim = true;
    }

    public void tick() {
        arrowTicks++;
        if(arrowTicks >= ARROW_ANIM_TIME) arrowTicks = 0;

        if(playOpeningAnim || playCloseAnim) {
            animTicks++;
            if(animTicks >= ANIM_TIME) {
                if(playOpeningAnim == true) Minecraft.getInstance().setScreen(new ScratchpadToolsUI());
                playOpeningAnim = playCloseAnim = false;
            }
        }
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        float anim_progress = 1.0f;
        if(playOpeningAnim || playCloseAnim) {
            anim_progress = (animTicks + partialTick) / ANIM_TIME;
            anim_progress = (float) Math.sin((anim_progress * Math.PI) / 2.0f);
        }

        float y_offset = -46.0f, rad_offset = 0.0f;
        if(playCloseAnim) {
            y_offset = Mth.lerp(anim_progress, 0.0f, -46.0f);
            rad_offset = Mth.lerp(anim_progress, (float)Math.PI, (float)(2*Math.PI));
        }
        else if(playOpeningAnim){
            y_offset = Mth.lerp(anim_progress, -46.0f, 0.0f);
            rad_offset = Mth.lerp(anim_progress, 0.0f, (float) Math.PI);
        }

        int i = screenWidth / 2;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, y_offset, -90.0f);
        guiGraphics.blit(TOOL_BOX, i - 99, 0, 0, 0, 198, 64);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(i, 55.0f, 0.0f);
            guiGraphics.pose().mulPose(Axis.ZP.rotation(rad_offset));
            guiGraphics.pose().translate(-9.0f, -10.0f, 0.0f);
            int offset = arrowTicks < ARROW_ANIM_TIME/2 ? 0 : -1;
            guiGraphics.blit(TOOL_BOX, 0, offset, 198, 0, 18, 18);
            guiGraphics.pose().popPose();
        
        guiGraphics.pose().popPose();
    }

}
