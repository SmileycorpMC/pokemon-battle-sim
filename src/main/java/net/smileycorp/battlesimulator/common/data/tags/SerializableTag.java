package net.smileycorp.battlesimulator.common.data.tags;

import com.google.gson.JsonElement;

public interface SerializableTag<T> {

	public boolean canRead(JsonElement element);

	public T deserialize(JsonElement element);

	public JsonElement serialize();

	public Class<? extends T> getSerializedClass();

}
