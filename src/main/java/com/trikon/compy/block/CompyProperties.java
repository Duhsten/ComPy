package com.trikon.compy.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class CompyProperties extends BlockStateProperties {
	public static final BooleanProperty POWER_STATE = BooleanProperty.create("power");
	public static final StringProperty FILESYSTEM = StringProperty.create("filesystem", "323-232131-232312");
}
