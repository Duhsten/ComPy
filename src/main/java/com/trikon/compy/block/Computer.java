package com.trikon.compy.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Computer extends Block{

	public Computer() {
		super(AbstractBlock.Properties.of(Material.GLASS));
		// TODO Auto-generated constructor stub
		
	}
	
	
	
	@Override
	public String getDescriptionId() { return "Computer Tier I";}
	

}
