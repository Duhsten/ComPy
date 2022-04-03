package com.trikon.compy.item;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import com.trikon.compy.*;
public class CompyItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Compy.MODID);
	
	public static final RegistryObject<Item> TABLET = ITEMS.register("tablet", () -> new Item(new Item.Properties()));
	
	
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
