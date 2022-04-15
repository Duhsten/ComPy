package com.trikon.compy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.trikon.compy.Compy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class CompySaveData extends WorldSavedData{

	public static final String NAME = Compy.MODID + "_example";
	private final List<SaveStorageObject> DATA = new ArrayList<>();
	public CompySaveData(String p_i2141_1_) {
		super(p_i2141_1_);
		// TODO Auto-generated constructor stub
	}
	
	public CompySaveData() {
		this(NAME);
	}
	@Override
	public void load(CompoundNBT nbt) {
		// TODO Auto-generated method stub
		CompoundNBT saveData = nbt.getCompound("saveData");
		for(int i = 0; saveData.contains("data"+i); i++) {
			DATA.add(SaveStorageObject.serialize(saveData.getCompound("data"+i)));
		}
		
	}
	
	public static void putData(SaveStorageObject object, ServerWorld world) {
		CompySaveData data = world.getDataStorage().computeIfAbsent(CompySaveData::new, CompySaveData.NAME);
		data.DATA.add(object);
		data.setDirty();
		
	}
	
	public static CompySaveData getData(ServerWorld world) {
		return world.getDataStorage().get(CompySaveData::new, CompySaveData.NAME);
	}
	
	public static void putData(String computer, BlockPos pos, ServerWorld world)
	{
		CompySaveData.putData(new SaveStorageObject(computer, pos), world);
	}
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		// TODO Auto-generated method stub
		CompoundNBT saveData = new CompoundNBT();
		
		for(ListIterator<SaveStorageObject> iterator = DATA.listIterator(); iterator.hasNext();) {
			saveData.put("data"+iterator.nextIndex(), iterator.next().deserializer());
		}
		nbt.put("saveData", saveData);
		return nbt;
	}
	
	static class SaveStorageObject {
		private final String computer;
		private final BlockPos pos;
		SaveStorageObject(String comp, BlockPos pos){
			this.computer = comp;
			this.pos = pos;
			System.out.println(this.computer);
		}
			public CompoundNBT deserializer() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putString("computer", computer);
				nbt.putLong("pos", pos.asLong());
				return nbt;
			}
			public static SaveStorageObject serialize(CompoundNBT nbt) {
				return new SaveStorageObject(nbt.getString("computer"), BlockPos.of(nbt.getLong("pos")));
				
			}
		}
	}
