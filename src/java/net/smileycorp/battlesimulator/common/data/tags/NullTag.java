package net.smileycorp.battlesimulator.common.data.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class NullTag implements SerializableTag<JsonNull> {

	@Override
	public boolean canRead(JsonElement element) {
		return element.isJsonNull();
	}

	@Override
	public JsonNull deserialize(JsonElement element) {
		return JsonNull.INSTANCE;
	}

	@Override
	public JsonElement serialize() {
		return JsonNull.INSTANCE;
	}

	@Override
	public Class<JsonNull> getSerializedClass() {
		return JsonNull.class;
	}

}
