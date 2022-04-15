package com.trikon.compy.os;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class TextStyle  {
	
	 public static final Style COMPY = compyStyle();
	 public static final Style OUTPUT = outputStyle();
	 
	 
	 private static final Style compyStyle() {
		 return Style.EMPTY.withBold(true).withColor(TextFormatting.AQUA);
	 }
	 private static final Style outputStyle() {
		 return Style.EMPTY.withColor(TextFormatting.WHITE);
	 }
}
