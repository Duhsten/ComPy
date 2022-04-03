package com.trikon.compy.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import com.trikon.compy.*;
import com.trikon.compy.item.CompyItems;
public class CompyBlocks {

	public static final DeferredRegister<Block> BLOCKS
			= DeferredRegister.create(ForgeRegistries.BLOCKS, Compy.MODID);
	
	public static final RegistryObject<Block> COMPUTER = registerBlock("computer", () -> new Computer());
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}
	
	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		CompyItems.ITEMS.register(name, () -> new BlockItem(block.get(),
					new Item.Properties()));
		
	}
	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
