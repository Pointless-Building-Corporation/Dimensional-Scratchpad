package com.pointlessbuilding.scratchpad.UI;

import java.util.function.Consumer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class ToolButton<T> extends AbstractButton{

    private ResourceLocation TOOL_BOX;
    private int toolOffset;
    private final Consumer<T> toolCall;
    private final T params;

    public ToolButton(int pX, int pY, ResourceLocation TOOL_BOX, int toolOffset, Consumer<T> toolCall, T params) {
        super(pX, pY, 32, 32, CommonComponents.EMPTY);
        this.TOOL_BOX = TOOL_BOX;
        this.toolOffset = toolOffset;
        this.toolCall = toolCall;
        this.params = params;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, -90.0f);
        guiGraphics.blit(TOOL_BOX, this.getX(), this.getY(), toolOffset, 10, 32, 32);
        guiGraphics.pose().popPose();
    }

    @Override
    public void onPress() {
        this.toolCall.accept(this.params);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}
    
}
