package com.trikon.compy.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.minecraft.state.Property;

public class StringProperty extends Property<String> {

	private String name;
	private String value;
	protected StringProperty(String name, String value) {
		super(name, String.class);
		this.name = name;
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<String> getPossibleValues() {
		List<String> strings = new ArrayList<String>();
		strings.add(value);
		strings.add("");
		// TODO Auto-generated method stub
		return strings;
	}

	@Override
	public String getName(String p_177702_1_) {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Optional<String> getValue(String p_185929_1_) {
		// TODO Auto-generated method stub
		return Optional.of(value);
	}

	public static StringProperty create(String name, String value) {
		// TODO Auto-generated method stub
		return new StringProperty(name, value);
	}
	
}
