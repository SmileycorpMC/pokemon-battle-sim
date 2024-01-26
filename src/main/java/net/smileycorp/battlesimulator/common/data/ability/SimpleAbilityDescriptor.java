package net.smileycorp.battlesimulator.common.data.ability;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleAbilityDescriptor implements AbilityDescriptor {

	protected final String ability;

	public SimpleAbilityDescriptor(String ability) {
		this.ability = ability;
	}

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(ability);
	}

	@Override
	public Collection<String> getAbilities() {
		List<String> list = new ArrayList<>();
		list.add(ability);
		return list;
	}

}
