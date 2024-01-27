package net.smileycorp.battlesimulator.common.data;

import com.google.gson.*;
import net.smileycorp.battlesimulator.common.data.ability.AbilityDescriptor;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap.StatEntry;
import net.smileycorp.battlesimulator.common.stat.EnumStat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

public class PokemonSpecies {

	public String name, unlocalizedName;
	public int id;
	public int genderRatio;
	public final List<String> tags = new ArrayList<>();
	public int hatchCounter, baseHappiness, catchRate;
	public EnumExpRate expRate;
	public List<EnumEggGroup> eggGroups = new ArrayList<>();
	public Map<String, Form> forms = new HashMap<>();

	public static class Form {

		public String formName = "";

		public List<EnumType> types = new ArrayList<>();

		public AbilityDescriptor ability1, ability2, hiddenAbility;

		public List<AbilityDescriptor> eventAbilities = new ArrayList<>();

		public BasicStatMap stats;

		public double height, weight;

		public Map<EnumStat, Integer> evDrops = new HashMap<>();

		public int expDrop;

		public String classification = "";

		public List<EvolutionData> evolutions = new ArrayList<>();

		public boolean isBaseForm;

		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			JsonArray typesJson = new JsonArray();
			for (EnumType type : types) typesJson.add(type.toString());
			json.add("types", typesJson);
			json.add("ability1", ability1.serialize());
			json.add("ability2", ability2 == null ? new JsonPrimitive("") : ability2.serialize());
			json.add("hiddenAbility", hiddenAbility == null ? new JsonPrimitive("") : hiddenAbility.serialize());
			JsonArray eventAbilitiesJson = new JsonArray();
			for (AbilityDescriptor ability : eventAbilities) {
				eventAbilitiesJson.add(ability.serialize());
			}
			json.add("eventAbilities", eventAbilitiesJson);
			JsonArray evolutionsJson = new JsonArray();
			for (EvolutionData evolutions : evolutions) {
				evolutionsJson.add(evolutions.toJson());
			}
			json.add("evolutions", evolutionsJson);
			json.add("stats", stats.toJson());
			json.add("height", new JsonPrimitive(height));
			json.add("weight", new JsonPrimitive(weight));
			JsonArray evsJson = new JsonArray();
			for (Entry<EnumStat, Integer> entry : evDrops.entrySet()) {
				JsonObject entryJson = new JsonObject();
				entryJson.add("stat", new JsonPrimitive(entry.getKey().toString()));
				entryJson.add("value", new JsonPrimitive(entry.getValue()));
				evsJson.add(entryJson);
			}
			json.add("evs", evsJson);
			json.add("expDrop", new JsonPrimitive(expDrop));
			json.add("classification", new JsonPrimitive(classification));
			json.add("isBaseForm", new JsonPrimitive(isBaseForm));
			return json;
		}

		public Form copy() {
			Form newForm = new Form();
			newForm.formName = formName;
			newForm.types.addAll(types);
			newForm.ability1 = ability1;
			newForm.ability2 = ability2;
			newForm.hiddenAbility = hiddenAbility;
			newForm.eventAbilities.addAll(eventAbilities);
			newForm.stats = new BasicStatMap(stats.entrySet());
			newForm.height = height;
			newForm.weight = weight;
			newForm.evDrops.putAll(evDrops);
			newForm.expDrop = expDrop;
			newForm.classification = classification;
			newForm.isBaseForm = isBaseForm;
			return newForm;
		}

		public static Form fromJson(String name, JsonObject formJson) {
			Form form = new Form();
			form.formName = name;
			for (JsonElement element : formJson.get("types").getAsJsonArray()) {
				form.types.add(EnumType.valueOf(element.getAsString().toUpperCase()));
			}
			if (formJson.has("ability1")) form.ability1 = AbilityDescriptor.read(formJson.get("ability1"));
			if (formJson.has("ability2")) form.ability2 = AbilityDescriptor.read(formJson.get("ability2"));
			if (formJson.has("hiddenAbility")) form.hiddenAbility = AbilityDescriptor.read(formJson.get("hiddenAbility"));
			if (formJson.has("eventAbilities")) for (JsonElement element : formJson.get("eventAbilities").getAsJsonArray()) {
				form.eventAbilities.add(AbilityDescriptor.read(element));
			}
			Set<StatEntry> set = new HashSet<>();
			for (Entry<String, JsonElement> entry : formJson.get("stats").getAsJsonObject().entrySet()) {
				set.add(new StatEntry(EnumStat.valueOf(entry.getKey()), entry.getValue().getAsInt()));
			}
			form.stats = new BasicStatMap(set);
			form.height = formJson.get("height").getAsDouble();
			form.weight = formJson.get("weight").getAsDouble();
			for (JsonElement element : formJson.get("evs").getAsJsonArray()) {
				JsonObject evJson = element.getAsJsonObject();
				form.evDrops.put(EnumStat.valueOf(evJson.get("stat").getAsString()), evJson.get("value").getAsInt());
			}
			form.expDrop = formJson.get("expDrop").getAsInt();
			form.classification = formJson.get("classification").getAsString();
			form.isBaseForm = formJson.get("isBaseForm").getAsBoolean();
			return form;
		}

	}

	public void exportJson(Path folder) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File(folder.toString() + "/" + unlocalizedName + ".json");
		JsonObject json = new JsonObject();
		json.add("name", new JsonPrimitive(name));
		json.add("id", new JsonPrimitive(id));
		json.add("genderRatio", new JsonPrimitive(genderRatio));
		JsonArray tagsJson = new JsonArray();
		for (String tag : tags) tagsJson.add(tag);
		json.add("tags", tagsJson);
		json.add("hatchCounter", new JsonPrimitive(hatchCounter));
		json.add("baseHappiness", new JsonPrimitive(baseHappiness));
		json.add("catchRate", new JsonPrimitive(catchRate));
		json.add("expRate", new JsonPrimitive(expRate.getName()));
		JsonArray eggGroupsJson = new JsonArray();
		for (EnumEggGroup eggGroup : eggGroups) eggGroupsJson.add(eggGroup.getName());
		json.add("eggGroups", eggGroupsJson);
		JsonObject formsJson = new JsonObject();
		for (Form form : forms.values()) formsJson.add(form.formName, form.toJson());
		json.add("forms", formsJson);
		file.createNewFile();
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(gson.toJson(json));
		}
	}

	public static PokemonSpecies fromJson(String unlocalizedName, JsonObject pokemonJson) {
		PokemonSpecies pokemon = new PokemonSpecies();
		pokemon.unlocalizedName = unlocalizedName;
		pokemon.name = pokemonJson.get("name").getAsString();
		pokemon.id = pokemonJson.get("id").getAsInt();
		pokemon.genderRatio = pokemonJson.get("genderRatio").getAsInt();
		if (pokemonJson.has("canDynamax")) if (!pokemonJson.get("canDynamax").getAsBoolean()) pokemon.tags.add("cant_dynamax");
		if (pokemonJson.has("isLegendary")) if (pokemonJson.get("isLegendary").getAsBoolean()) pokemon.tags.add("legendary");
		if (pokemonJson.has("isMythical")) if (pokemonJson.get("isMythical").getAsBoolean()) pokemon.tags.add("mythical");
		if (pokemonJson.has("isRestricted")) if (pokemonJson.get("isRestricted").getAsBoolean()) pokemon.tags.add("restricted");
		if (pokemonJson.has("isBaby")) if (pokemonJson.get("isBaby").getAsBoolean()) pokemon.tags.add("baby");
		pokemon.hatchCounter = pokemonJson.get("hatchCounter").getAsInt();
		pokemon.baseHappiness = pokemonJson.get("baseHappiness").getAsInt();
		pokemon.catchRate = pokemonJson.get("catchRate").getAsInt();
		pokemon.expRate = EnumExpRate.get(pokemonJson.get("expRate").getAsString());
		for (JsonElement element : pokemonJson.get("eggGroups").getAsJsonArray()) {
			pokemon.eggGroups.add(EnumEggGroup.getGroup(element.getAsString()));
		}
		for (Entry<String, JsonElement> entry : pokemonJson.get("forms").getAsJsonObject().entrySet()) {
			String name = entry.getKey();
			pokemon.forms.put(name, Form.fromJson(name, entry.getValue().getAsJsonObject()));
		}
		return pokemon;
	}

}
