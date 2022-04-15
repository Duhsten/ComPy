package com.trikon.compy.os;

import javax.annotation.Nonnull;

import com.trikon.compy.util.Colour;
import com.trikon.compy.util.Palette;
import com.trikon.compy.util.Status;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
public class Terminal
{
    private static final String base16 = "0123456789abcdef";

    private int cursorX = 0;
    private int cursorY = 0;
    private boolean cursorBlink = false;
    private int cursorColour = 0;
    private int cursorBackgroundColour = 15;

    private int width;
    private int height;



 

  

    public Terminal( int width, int height )
    {
        this( width, height, null );
    }

    public Terminal( int width, int height, Runnable changedCallback )
    {
        this.width = width;
        this.height = height;
    }

   

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

  

  
  

    public int getCursorX()
    {
        return cursorX;
    }

    public int getCursorY()
    {
        return cursorY;
    }

    public boolean getCursorBlink()
    {
        return cursorBlink;
    }

    public int getTextColour()
    {
        return cursorColour;
    }

    public int getBackgroundColour()
    {
        return cursorBackgroundColour;
    }

   

   
    public static int getColour( char c, Colour def )
    {
        if( c >= '0' && c <= '9' ) return c - '0';
        if( c >= 'a' && c <= 'f' ) return c - 'a' + 10;
        if( c >= 'A' && c <= 'F' ) return c - 'A' + 10;
        return 15 - def.ordinal();
    }

    public synchronized void scroll( int yDiff )
    {
       
        
    }

   

   

}