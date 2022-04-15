package com.trikon.compy.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.trikon.compy.Compy;
import com.trikon.compy.os.FrameInfo;
import com.trikon.compy.os.Terminal;
import com.trikon.compy.os.TextBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;

public class TextRenderer {

	private static final Matrix4f IDENTITY = TransformationMatrix.identity().getMatrix();
	private static final ResourceLocation FONT = new ResourceLocation(Compy.MODID, "textures/gui/term_font.png");
	public static final int FONT_HEIGHT = 9;
	public static final int FONT_WIDTH = 6;
	public static final float WIDTH = 256.0f;

	public static final float BACKGROUND_START = (WIDTH - 6.0f) / WIDTH;
	public static final float BACKGROUND_END = (WIDTH - 4.0f) / WIDTH;




	public static void drawQuad(Matrix4f transform, IVertexBuilder buffer, float x, float y, float width, float height,
			float r, float g, float b) {
		buffer.vertex(transform, x, y, 0).color(r, g, b, 1.0f).uv(BACKGROUND_START, BACKGROUND_START).endVertex();
		buffer.vertex(transform, x, y + height, 0).color(r, g, b, 1.0f).uv(BACKGROUND_START, BACKGROUND_END)
				.endVertex();
		buffer.vertex(transform, x + width, y, 0).color(r, g, b, 1.0f).uv(BACKGROUND_END, BACKGROUND_START).endVertex();
		buffer.vertex(transform, x + width, y, 0).color(r, g, b, 1.0f).uv(BACKGROUND_END, BACKGROUND_START).endVertex();
		buffer.vertex(transform, x, y + height, 0).color(r, g, b, 1.0f).uv(BACKGROUND_START, BACKGROUND_END)
				.endVertex();
		buffer.vertex(transform, x + width, y + height, 0).color(r, g, b, 1.0f).uv(BACKGROUND_END, BACKGROUND_END)
				.endVertex();
	}



	

	
	
	private static void bindFont() {
		Minecraft.getInstance().getTextureManager().bind(FONT);
		RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
	}

	
	public static void drawChar( Matrix4f transform, IVertexBuilder buffer, float x, float y, int index, float r, float g, float b )
    {
        // Short circuit to avoid the common case - the texture should be blank here after all.
        if( index == '\0' || index == ' ' ) return;

        int column = index % 16;
        int row = index / 16;

        int xStart = 1 + column * (FONT_WIDTH + 2);
        int yStart = 1 + row * (FONT_HEIGHT + 2);

        buffer.vertex( transform, x, y, 0f ).color( r, g, b, 1.0f ).uv( xStart / WIDTH, yStart / WIDTH ).endVertex();
        buffer.vertex( transform, x, y + FONT_HEIGHT, 0f ).color( r, g, b, 1.0f ).uv( xStart / WIDTH, (yStart + FONT_HEIGHT) / WIDTH ).endVertex();
        buffer.vertex( transform, x + FONT_WIDTH, y, 0f ).color( r, g, b, 1.0f ).uv( (xStart + FONT_WIDTH) / WIDTH, yStart / WIDTH ).endVertex();
        buffer.vertex( transform, x + FONT_WIDTH, y, 0f ).color( r, g, b, 1.0f ).uv( (xStart + FONT_WIDTH) / WIDTH, yStart / WIDTH ).endVertex();
        buffer.vertex( transform, x, y + FONT_HEIGHT, 0f ).color( r, g, b, 1.0f ).uv( xStart / WIDTH, (yStart + FONT_HEIGHT) / WIDTH ).endVertex();
        buffer.vertex( transform, x + FONT_WIDTH, y + FONT_HEIGHT, 0f ).color( r, g, b, 1.0f ).uv( (xStart + FONT_WIDTH) / WIDTH, (yStart + FONT_HEIGHT) / WIDTH ).endVertex();
    }
	
	public static float toGreyscale( double[] rgb )
    {
        return (float) ((rgb[0] + rgb[1] + rgb[2]) / 3);
    }
	
	public static int getColour( char c, Colour def )
    {
        return 15 - Terminal.getColour( c, def );
    }
	
	public static void drawString(
	        @Nonnull Matrix4f transform, @Nonnull IVertexBuilder renderer, float x, float y,
	        @Nonnull TextBuffer text, @Nonnull TextBuffer textColour, @Nullable TextBuffer backgroundColour,
	        @Nonnull Palette palette, boolean greyscale, float leftMarginSize, float rightMarginSize
	    )
	    {
	        

	        for( int i = 0; i < text.length(); i++ )
	        {
	            double[] colour = palette.getColour( getColour( textColour.charAt( i ), Colour.WHITE ) );
	            float r, g, b;
	            if( greyscale )
	            {
	                r = g = b = toGreyscale( colour );
	            }
	            else
	            {
	                r = (float) colour[0];
	                g = (float) colour[1];
	                b = (float) colour[2];
	            }

	            // Draw char
	            int index = text.charAt( i );
	            if( index > 255 ) index = '?';
	            drawChar( transform, renderer, x + i * FONT_WIDTH, y, index, r, g, b );
	        }

	    }

	    public static void drawString(
	        float x, float y, @Nonnull TextBuffer text, @Nonnull TextBuffer textColour, @Nullable TextBuffer backgroundColour,
	        @Nonnull Palette palette, boolean greyscale, float leftMarginSize, float rightMarginSize
	    )
	    {
	        bindFont();

	        IRenderTypeBuffer.Impl renderer = Minecraft.getInstance().renderBuffers().bufferSource();
	        drawString( IDENTITY, ((IRenderTypeBuffer) renderer).getBuffer( TextRenderer.Type.MAIN ), x, y, text, textColour, backgroundColour, palette, greyscale, leftMarginSize, rightMarginSize );
	        renderer.endBatch();
	    }
	
	    public static void drawEmptyTerminal( @Nonnull Matrix4f transform, @Nonnull IRenderTypeBuffer renderer, float x, float y, float width, float height )
        {
	    	drawTerminalBorder(transform,renderer,x,y,width,height);
            Colour colour = Colour.BLACK;
            drawQuad( transform, renderer.getBuffer( TextRenderer.Type.MAIN ), x, y, width-3, height-3, colour.getR(), colour.getG(), colour.getB() );
            
        }
	    public static void drawTerminalBorder( @Nonnull Matrix4f transform, @Nonnull IRenderTypeBuffer renderer, float x, float y, float width, float height )
        {
            Colour colour = Colour.CYAN;
            drawQuad( transform, renderer.getBuffer( TextRenderer.Type.MAIN ), x-3, y-3, width+3, height+3, colour.getR(), colour.getG(), colour.getB() );
        }
	    
	public static final class Type extends RenderState
    {
        private static final int GL_MODE = GL11.GL_TRIANGLES;

        private static final VertexFormat FORMAT = DefaultVertexFormats.POSITION_COLOR_TEX;

        public static final RenderType MAIN = RenderType.create(
            "terminal_font", FORMAT, GL_MODE, 1024,
            false, false, // useDelegate, needsSorting
            RenderType.State.builder()
                .setTextureState( new RenderState.TextureState( FONT, false, false ) ) // blur, minimap
                .setAlphaState( DEFAULT_ALPHA )
                .setLightmapState( NO_LIGHTMAP )
                .setWriteMaskState( COLOR_WRITE )
                .createCompositeState( false )
        );

        static final RenderType BLOCKER = RenderType.create(
            "terminal_blocker", FORMAT, GL_MODE, 256,
            false, false, // useDelegate, needsSorting
            RenderType.State.builder()
                .setTextureState( new RenderState.TextureState( FONT, false, false ) ) // blur, minimap
                .setAlphaState( DEFAULT_ALPHA )
                .setWriteMaskState( DEPTH_WRITE )
                .setLightmapState( NO_LIGHTMAP )
                .createCompositeState( false )
        );
        
      

        private Type( String name, Runnable setup, Runnable destroy )
        {
            super( name, setup, destroy );
        }
    }

	

}
