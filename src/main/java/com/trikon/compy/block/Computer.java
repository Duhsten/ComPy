package com.trikon.compy.block;

import com.trikon.compy.screen.ComputerScreen;
import com.trikon.compy.screen.LightningChannelerScreen;
import com.trikon.compy.tileentity.CompyTileEntities;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Computer extends Block{

	public Computer() {
		super(AbstractBlock.Properties.of(Material.GLASS));
		// TODO Auto-generated constructor stub
	}
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult brtr)
	{
		System.out.println("Working");
		
		Minecraft.getInstance().setScreen(new ComputerScreen());
		return ActionResultType.SUCCESS;
	}
	

	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	
	

}
