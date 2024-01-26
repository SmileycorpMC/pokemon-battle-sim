package net.smileycorp.battlesimulator.common.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.smileycorp.battlesimulator.common.data.tags.SerializableTag;

public class EvolutionData {

	private final FormReference form;
	private final EvolutionMethod method;
	private final SerializableTag<?> value;

	private EvolutionData(FormReference form, EvolutionMethod method, SerializableTag<?> value) {
		this.form = form;
		this.method = method;
		this.value = value;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.add("target", form.toJson());
		json.add("method", new JsonPrimitive(method.toString()));
		json.add("value", value.serialize());
		return json;
	}

	public static EvolutionData of(FormReference form, EvolutionMethod method, SerializableTag<?> value) {
		return method == null ? null : new EvolutionData(form, method, value);
	}

	/*public static EvolutionData fromJson(JsonObject obj) {

		return new EvolutionData(data, method, value);
	}*/

}
