package net.smileycorp.battlesimulator.common.data.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class ObjectTag implements SerializableTag<JsonObject> {

	protected JsonObject obj;

	@Override
	public boolean canRead(JsonElement element) {
		return element.isJsonObject();
	}

	@Override
	public JsonObject deserialize(JsonElement element) {
		obj = element.getAsJsonObject();
		return obj;
	}

	@Override
	public JsonElement serialize() {
		return obj == null ? JsonNull.INSTANCE : obj;
	}

	@Override
	public Class<JsonObject> getSerializedClass() {
		return JsonObject.class;
	}

}
