package net.smileycorp.battlesimulator.common.data.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class NumberTag implements SerializableTag<Number> {

	protected Number val;
	protected Class<? extends Number> clazz = Number.class;

	public NumberTag(Number val) {
		this.val = val;
	}

	@Override
	public boolean canRead(JsonElement element) {
		if (!element.isJsonPrimitive()) return false;
		return element.getAsJsonPrimitive().isNumber();
	}

	@Override
	public Number deserialize(JsonElement element) {
		val = element.getAsNumber();
		clazz = val.getClass();
		return val;
	}

	@Override
	public JsonElement serialize() {
		return val == null ? JsonNull.INSTANCE : new JsonPrimitive(val);
	}

	@Override
	public Class<? extends Number> getSerializedClass() {
		return clazz;
	}

}
