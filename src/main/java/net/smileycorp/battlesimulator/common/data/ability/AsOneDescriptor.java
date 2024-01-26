package net.smileycorp.battlesimulator.common.data.ability;

import java.util.Collection;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class AsOneDescriptor implements AbilityDescriptor {

	protected final List<String> abilities;

	public AsOneDescriptor(List<String> abilities) {
		this.abilities = abilities;
	}

	@Override
	public JsonElement serialize() {
		JsonArray array = new JsonArray();
		for (String ability : abilities) array.add(ability);
		return array;
	}

	@Override
	public Collection<String> getAbilities() {
		return abilities;
	}

}
