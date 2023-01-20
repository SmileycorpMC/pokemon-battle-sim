package net.smileycorp.battlesimulator.common.data.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class StringTag implements SerializableTag<String> {

	protected String str;

	public StringTag(String str) {
		this.str = str;
	}

	@Override
	public boolean canRead(JsonElement element) {
		if (!element.isJsonPrimitive()) return false;
		return element.getAsJsonPrimitive().isString();
	}

	@Override
	public String deserialize(JsonElement element) {
		str = element.getAsString();
		return str;
	}

	@Override
	public JsonElement serialize() {
		return str == null ? JsonNull.INSTANCE : new JsonPrimitive(str);
	}

	@Override
	public Class<String> getSerializedClass() {
		return String.class;
	}

}
