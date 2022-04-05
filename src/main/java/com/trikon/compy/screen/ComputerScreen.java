package com.trikon.compy.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.trikon.compy.Compy;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ComputerScreen extends Screen {
	
	private final ResourceLocation GUI = new ResourceLocation(Compy.MODID,
            "textures/gui/screen.png");
	public ComputerScreen() {
		super(new TranslationTextComponent("screen.computer.display"));
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	        this.renderBackground(matrixStack);
	        super.render(matrixStack, mouseX, mouseY, partialTicks);
	        this.renderTooltip(matrixStack, title, mouseX, mouseY);
	    }
	 @Override
	public void renderBackground(MatrixStack matrixStack) {
	        RenderSystem.color4f(1f, 1f, 1f, 1f);
	        this.minecraft.getTextureManager().getTexture(GUI);
	        int i = this.width;
	        int j = this.height;
	        this.blit(matrixStack, i, j, 0, 0, this.width, this.height);

	       
	    }
}
