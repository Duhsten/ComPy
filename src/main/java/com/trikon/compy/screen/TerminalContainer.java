package com.trikon.compy.screen;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.trikon.compy.os.Computer;
import com.trikon.compy.os.Terminal;
import com.trikon.compy.util.GuiRenderer;
import com.trikon.compy.util.TextRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.text.ITextComponent;

public class TerminalContainer extends Widget {

	private static final float TERMINATE_TIME = 0.5f;

    private final Computer computer;

    // The positions of the actual terminal
    private final int innerX;
    private final int innerY;
    private final int innerWidth;
    private final int innerHeight;

    private float terminateTimer = -1;
    private float rebootTimer = -1;
    private float shutdownTimer = -1;

    private int lastMouseButton = -1;
    private int lastMouseX = -1;
    private int lastMouseY = -1;
    
	public TerminalContainer(Computer computer, int x, int y, int w, int h,
			ITextComponent name) {
		
		super(x, y, w, h, name);
		 this.computer = computer;
        innerX = x + GuiRenderer.MARGIN;
        innerY = y + GuiRenderer.MARGIN;
        innerWidth = w * TextRenderer.FONT_WIDTH;
        innerHeight = h * TextRenderer.FONT_HEIGHT;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	 public void render( @Nonnull MatrixStack transform, int mouseX, int mouseY, float partialTicks )
	    {
	
	    }
	
	
	
	
}
