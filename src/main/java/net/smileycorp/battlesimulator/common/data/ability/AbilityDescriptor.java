package net.smileycorp.battlesimulator.common.data.ability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.JsonElement;

public interface AbilityDescriptor {

	public JsonElement serialize();

	public Collection<String> getAbilities();

	public static AbilityDescriptor read(JsonElement element) {
		if (element == null) return null;
		if (element.isJsonNull()) return null;
		if (element.isJsonArray() ) {
			List<String> abilities = new ArrayList<>();
			for (JsonElement ability : element.getAsJsonArray()) {
				String str = ability.getAsString();
				if (!str.isEmpty()) abilities.add(str);
			}
			return new AsOneDescriptor(abilities);
		}
		String str = element.getAsString();
		if (!str.isEmpty()) return new SimpleAbilityDescriptor(str);
		return null;
	}

}
