package net.smileycorp.battlesimulator.common.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class FormReference {

	private final String species, form;

	private FormReference(String species, String form) {
		this.species=species;
		this.form=form;
	}

	public String getSpecies() {
		return species;
	}

	public String getForm() {
		return form;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.add("species", new JsonPrimitive(species));
		json.add("form", new JsonPrimitive(form));
		return json;
	}

	public static FormReference fromName(String species, String form) {
		return new FormReference(species, form);
	}

}
