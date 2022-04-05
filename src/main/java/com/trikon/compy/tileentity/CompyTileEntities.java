package com.trikon.compy.tileentity;

import com.trikon.compy.Compy;
import com.trikon.compy.block.CompyBlocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CompyTileEntities {
	
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Compy.MODID);
	
	 public static RegistryObject<TileEntityType<LightningChannelerTile>> LIGHTNING_CHANNELER_TILE =
	            TILE_ENTITIES.register("lightning_channeler_tile", () -> TileEntityType.Builder.of(
	                    LightningChannelerTile::new, CompyBlocks.COMPUTER.get()).build(null));
	public static void register(IEventBus eventBus) {
		TILE_ENTITIES.register(eventBus);
	}
}
