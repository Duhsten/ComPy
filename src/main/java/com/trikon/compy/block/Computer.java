package com.trikon.compy.block;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.trikon.compy.screen.ComputerScreen;
import com.trikon.compy.screen.LightningChannelerScreen;
import com.trikon.compy.tileentity.CompyTileEntities;
import com.trikon.compy.util.CompyLib;
import com.trikon.compy.util.CompySaveData;
import com.trikon.compy.util.Device;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Computer extends Block{

	public Computer() {
		super(AbstractBlock.Properties.of(Material.GLASS));
		// TODO Auto-generated constructor stub
		this.registerDefaultState(
				  this.stateDefinition.any()
				    .setValue(CompyProperties.POWER_STATE, false)
				    .setValue(CompyProperties.FILESYSTEM, "323-232131-232312")
				);
	}  
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
	   super.createBlockStateDefinition(builder);
	   builder.add(CompyProperties.POWER_STATE);
	   builder.add(CompyProperties.FILESYSTEM);
	}
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(CompyProperties.POWER_STATE, false).getBlockState();
	}
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult brtr)
	{
		

		if(!CompyLib.checkForDeviceAt(blockPos.getX(), blockPos.getY(), blockPos.getZ())) {
			CompyLib.addDevice(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		}
		Device d = CompyLib.getDeviceAt(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		System.out.println("Working");
		System.out.println(blockPos.toShortString());
		 
		Minecraft.getInstance().setScreen(new ComputerScreen(d));
		System.out.println(state.getProperties());
		if(world instanceof ServerWorld) {
			CompySaveData.putData("Test", blockPos, (ServerWorld) world);
		}
		
		Path p = Minecraft.getInstance().getSingleplayerServer().getWorldPath(FolderName.ROOT);
		System.out.println(p.toAbsolutePath());
		return ActionResultType.SUCCESS;
		
		
	}
	
	

	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	
	

}
