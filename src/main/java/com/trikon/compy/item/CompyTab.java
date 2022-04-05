package com.trikon.compy.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CompyTab {

	public static final ItemGroup COMPY_TAB = new ItemGroup("compyTab")
	{

			@Override
			public ItemStack makeIcon() {
				// TODO Auto-generated method stub
				return new ItemStack(CompyItems.TABLET.get());
			}
			
	};
}
