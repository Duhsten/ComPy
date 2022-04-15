package com.trikon.compy.os;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.trikon.compy.Compy;

@Mod.EventBusSubscriber( modid = Compy.MODID, value = Dist.CLIENT )
public class FrameInfo {
	    private static int tick;
	    private static long renderFrame;

	    private FrameInfo()
	    {
	    	
	    }

	    public static boolean getGlobalCursorBlink()
	    {
	        return (tick / 8) % 2 == 0;
	    }

	    public static long getRenderFrame()
	    {
	        return renderFrame;
	    }

	    @SubscribeEvent
	    public static void onTick( TickEvent.ClientTickEvent event )
	    {
	        if( event.phase == TickEvent.Phase.START ) tick++;
	    }

	    @SubscribeEvent
	    public static void onRenderTick( TickEvent.RenderTickEvent event )
	    {
	        if( event.phase == TickEvent.Phase.START ) renderFrame++;
	    }
	}

