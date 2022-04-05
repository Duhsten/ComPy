package com.trikon.compy.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.trikon.compy.Compy;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LightningChannelerScreen extends Screen {
    public LightningChannelerScreen() {
		super(new TranslationTextComponent("screen.tutorialmod.lightning_channeler"));
		
		// TODO Auto-generated constructor stub
	}
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
	private final ResourceLocation GUI = new ResourceLocation(Compy.MODID,
            "textures/gui/lightning_channeler_gui.png");

        

}