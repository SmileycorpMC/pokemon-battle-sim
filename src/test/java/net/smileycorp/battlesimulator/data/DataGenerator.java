package net.smileycorp.battlesimulator.data;

import com.google.gson.*;
import net.smileycorp.battlesimulator.common.Main;
import net.smileycorp.battlesimulator.common.battle.moves.EnumMoveCategory;
import net.smileycorp.battlesimulator.common.battle.moves.EnumTargetType;
import net.smileycorp.battlesimulator.common.battle.moves.Move;
import net.smileycorp.battlesimulator.common.data.*;
import net.smileycorp.battlesimulator.common.data.PokemonSpecies.Form;
import net.smileycorp.battlesimulator.common.data.ability.SimpleAbilityDescriptor;
import net.smileycorp.battlesimulator.common.data.tags.NullTag;
import net.smileycorp.battlesimulator.common.data.tags.NumberTag;
import net.smileycorp.battlesimulator.common.data.tags.SerializableTag;
import net.smileycorp.battlesimulator.common.data.tags.StringTag;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap.StatEntry;
import net.smileycorp.battlesimulator.common.stat.EnumStat;
import net.smileycorp.battlesimulator.common.util.TextUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

public class DataGenerator {

	public static void main(String[] args) throws Exception {
		//fetchPokemonData();
		fixPokemonData();
		//fetchMoveData();
		//fixMoveData();
	}

	public static void fetchPokemonData() throws IOException, URISyntaxException {
		String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		File folder = new File(path + "/data/pokemon");
		folder.mkdirs();
		int count = fetchJson("https://pokeapi.co/api/v2/pokemon-species/?limit=10000").get("count").getAsInt();
		for (int i = 666; i<= count; i++) {
			JsonObject speciesJson = fetchJson("https://pokeapi.co/api/v2/pokemon-species/"+i);
			PokemonSpecies pokemon = new PokemonSpecies();
			pokemon.name = speciesJson.get("names").getAsJsonArray().get(8).getAsJsonObject().get("name").getAsString();
			pokemon.id = speciesJson.get("id").getAsInt();
			pokemon.genderRatio = speciesJson.get("gender_rate").getAsInt();
			pokemon.hatchCounter = speciesJson.get("hatch_counter").getAsInt();
			if (speciesJson.get("is_baby").getAsBoolean()) pokemon.tags.add("baby");
			if (speciesJson.get("is_legendary").getAsBoolean()) pokemon.tags.add("legendary");
			if (speciesJson.get("is_mythical").getAsBoolean()) pokemon.tags.add("mythical");
			pokemon.baseHappiness = speciesJson.get("base_happiness").getAsInt();
			pokemon.catchRate = speciesJson.get("capture_rate").getAsInt();
			pokemon.expRate = EnumExpRate.get(speciesJson.get("growth_rate").getAsJsonObject().get("name").getAsString());
			for (JsonElement element : speciesJson.get("egg_groups").getAsJsonArray()) {
				pokemon.eggGroups.add(EnumEggGroup.getGroup(element.getAsJsonObject().get("name").getAsString().replace("-", "")));
			}
			String classification = TextUtils.properCase(speciesJson.get("genera").getAsJsonArray().get(7)
					.getAsJsonObject().get("genus").getAsString().toLowerCase()
					.replace("_", " "));
			for (JsonElement element : speciesJson.get("varieties").getAsJsonArray()) {
				String url = element.getAsJsonObject().get("pokemon").getAsJsonObject().get("url").getAsString();
				JsonObject formJson = fetchJson(url);
				Form form = new Form();
				form.formName =  formatFormName(formJson.get("name").getAsString(), pokemon.name);
				for (JsonElement type : formJson.get("types").getAsJsonArray()) {
					form.types.add(EnumType.valueOf(type.getAsJsonObject().get("type").getAsJsonObject()
							.get("name").getAsString().toUpperCase()));
				}
				for (JsonElement ability : formJson.get("abilities").getAsJsonArray()) {
					JsonObject entry = ability.getAsJsonObject();
					String value = entry.get("ability").getAsJsonObject().get("name").getAsString();
					value = TextUtils.properCase(value.replace("-", " "));
					if (value.equals("Soul Heart")) value = "Soul-Heart";
					switch (entry.get("slot").getAsInt()) {
					case 1:
						form.ability1 = new SimpleAbilityDescriptor(TextUtils.properCase(value));
						break;
					case 2:
						form.ability2 = new SimpleAbilityDescriptor(TextUtils.properCase(value));
						break;
					case 3:
						form.hiddenAbility = new SimpleAbilityDescriptor(TextUtils.properCase(value));
						break;
					default: break;
					}
				}
				Set<StatEntry> statSet = new HashSet<>();
				for (JsonElement statJson : formJson.get("stats").getAsJsonArray()) {
					JsonObject entry = statJson.getAsJsonObject();
					EnumStat stat = EnumStat.fromName(entry.get("stat").getAsJsonObject().get("name").getAsString());
					statSet.add(new StatEntry(stat, entry.get("base_stat").getAsInt()));
					int ev = entry.get("effort").getAsInt();
					if (ev > 0) form.evDrops.put(stat, ev);
				}
				form.stats = new BasicStatMap(statSet);
				form.expDrop = formJson.get("base_experience").getAsInt();
				form.height = formJson.get("height").getAsInt()/10d;
				form.weight = formJson.get("weight").getAsInt()/10d;
				form.classification = classification;
				JsonArray extraForms = formJson.get("forms").getAsJsonArray();
				if (extraForms.size()>1) {
					for (JsonElement formEntry : extraForms) {
						Form extraForm = form.copy();
						JsonObject extraFormJson = fetchJson(formEntry.getAsJsonObject().get("url").getAsString());
						extraForm.formName = formatFormName(extraFormJson.get("form_name").getAsString(), pokemon.name);
						if (extraForm.formName.equals("normal")) extraForm.formName = "default";
						extraForm.types.clear();
						for (JsonElement type : extraFormJson.get("types").getAsJsonArray()) {
							try {
								extraForm.types.add(EnumType.valueOf(type.getAsJsonObject().get("type").getAsJsonObject()
										.get("name").getAsString().toUpperCase()));
							} catch (Exception e) {
								continue;
							}
						}
						if (isBaseForm(extraForm.formName)) extraForm.isBaseForm = true;
						pokemon.forms.add(extraForm);
						//fetchSprites(path, pokemon, extraForm, extraFormJson);
					}
				} else {
					if (isBaseForm(form.formName)) form.isBaseForm = true;
					pokemon.forms.add(form);
					//fetchSprites(path, pokemon, form, formJson);
				}
			}
			pokemon.exportJson(folder.toPath());
			return;
		}
	}

	private static boolean isBaseForm(String name) {
		return name.equals("default") || name.equals("alola") || name.equals("galar") || name.equals("hisui");
	}

	@SuppressWarnings("unused")
	private static void fetchSprites(String path, PokemonSpecies pokemon, Form form, JsonObject json) throws IOException {
		for (Entry<String, JsonElement> entry : json.get("sprites").getAsJsonObject().entrySet()) {
			if (entry.getValue().isJsonPrimitive()) {
				String folder = path + "/assets/pokemon/" + TextUtils.removeSpecialChars(pokemon.name.toLowerCase()) + "/" + form.formName;
				new File(folder).mkdirs();
				saveImage(entry.getValue().getAsString(), new File( folder + "/" + entry.getKey() + ".png"));
			}
		}
	}

	private static void saveImage(String url, File file) {
		try {
			file.createNewFile();
			try (CloseableHttpClient client = HttpClients.createDefault()) {
				try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						try (FileOutputStream outstream = new FileOutputStream(file)) {
							entity.writeTo(outstream);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println(url);
			e.printStackTrace();
		}
	}

	private static JsonObject fetchJson(String url) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			URI uri = new URI(url);
			String message = EntityUtils.toString(client.execute(new HttpGet(uri)).getEntity());
			return JsonParser.parseString(message).getAsJsonObject();
		} catch (IOException | URISyntaxException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String formatFormName(String formName, String speciesName) {
		if(formName.toLowerCase().equals(speciesName.toLowerCase())) return "default";
		return formName.replace(speciesName.toLowerCase()+"-", "");
	}

	private static void fixPokemonData() throws Exception {
		String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		List<Entry<FormReference, EvolutionData>> evolutionTable = readEvolutionTable(path);
		List<FormReference> broken_evolutions = new ArrayList<>();
		List<String> missing_happiness = new ArrayList<>();
		File folder = new File(path + "/data/pokemon");
		for (File subfolder : folder.listFiles()) {
			if (subfolder.isDirectory()) {
				for (File file: subfolder.listFiles()) {
					FileReader reader = new FileReader(file);
					System.out.println(file);
					PokemonSpecies pokemon = PokemonSpecies.fromJson(file.getName().replace(".json", ""), JsonParser.parseReader(reader).getAsJsonObject());
					reader.close();
					if (pokemon.id <= 905)  {
						JsonObject speciesJson = fetchJson("https://pokeapi.co/api/v2/pokemon-species/"+pokemon.id);
						if (speciesJson.get("base_happiness").isJsonNull()) missing_happiness.add(pokemon.unlocalizedName);
						else pokemon.baseHappiness = speciesJson.get("base_happiness").getAsInt();
						pokemon.catchRate = speciesJson.get("capture_rate").getAsInt();
					}
					System.out.println(pokemon.unlocalizedName);
					List<Entry<Form, Entry<FormReference, EvolutionData>>> evolutions = new ArrayList<>();
					for (Entry<FormReference, EvolutionData> entry : evolutionTable) {
						if (entry.getValue() != null) {
							FormReference ref = entry.getKey();
							if (ref.getSpecies().equals(pokemon.unlocalizedName)) {
								for (Form form : pokemon.forms) {
									if (ref.getForm().equals(form.formName)) {
										evolutions.add(new SimpleEntry<>(form, entry));
										break;
									}
								}
							}
						}
					}
					for (Entry<Form, Entry<FormReference, EvolutionData>> entry : evolutions) {
						entry.getKey().evolutions.add(entry.getValue().getValue());
						evolutionTable.remove(entry.getValue());
					}
					pokemon.exportJson(subfolder.toPath());
				}
			}
		}
		JsonArray array = new JsonArray();
		for(Entry<FormReference, EvolutionData> entry : evolutionTable) {
			JsonObject obj = entry.getKey().toJson();
			if (entry.getValue() != null) {
				obj.add("data", entry.getValue().toJson());
				array.add(obj);
			}
			else broken_evolutions.add(entry.getKey());
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File(path + "/export/unfinished_evolutions.json");
		file.createNewFile();
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(gson.toJson(array));
		}
		System.out.println(file.getAbsolutePath());
		JsonArray array1 = new JsonArray();
		for(FormReference entry : broken_evolutions) {
			array1.add(entry.toJson());
		}
		File file1 = new File(path + "/export/broken_evolutions.json");
		file1.createNewFile();
		try(FileWriter writer = new FileWriter(file1)) {
			writer.write(gson.toJson(array1));
		}
		System.out.println(file1.getAbsolutePath());
		JsonArray array2 = new JsonArray();
		for(String name : missing_happiness) {
			array2.add(name);
		}
		File file2 = new File(path + "/export/missing_happiness.json");
		file2.createNewFile();
		try(FileWriter writer = new FileWriter(file2)) {
			writer.write(gson.toJson(array2));
		}
		System.out.println(file2.getAbsolutePath());
	}

	private static List<Entry<FormReference, EvolutionData>> readEvolutionTable(String path) throws Exception {
		List<Entry<FormReference, EvolutionData>> table = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(path+"/data/evolutions.csv"))) {
			while (scanner.hasNextLine()) {
				String[] values = scanner.nextLine().split(",");
				for (int i = 0; i < values.length; i++) {
					if (values[i].equals("-")) {
						values[i] = null;
					}
				}
				FormReference ref = FormReference.fromName(values[0], values[1]);
				EvolutionData data = null;
				try {
					FormReference ref2 = FormReference.fromName(values[2], values[3]);
					EvolutionMethod method =  EvolutionMethod.get(values[4]);
					SerializableTag<?> tag = null;
					if (values[5] == null) {
						tag = new NullTag();
					} else {
						try {
							tag = new NumberTag(Integer.valueOf(values[5]));
						} catch (Exception e) {
							tag = new StringTag(values[5]);
						}
					}
					data = EvolutionData.of(ref2, method, tag);
				} catch (Exception e) {
					System.out.println("failed to read " + values);
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				table.add(new SimpleEntry<>(ref, data));
			}
			scanner.close();
		}
		return table;
	}

	public static void fetchMoveData() throws IOException, URISyntaxException {
		String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		File folder = new File(path + "/data/moves");
		folder.mkdirs();
		int count = fetchJson("https://pokeapi.co/api/v2/move/?limit=10000").get("count").getAsInt();
		for (int i = 1; i<= count; i++) {
			JsonObject moveJson = fetchJson("https://pokeapi.co/api/v2/move/"+i);
			Move move = new Move();
			move.name = moveJson.get("names").getAsJsonArray().get(7).getAsJsonObject().get("name").getAsString();
			move.id = i;
			move.type = EnumType.fromString(moveJson.get("type").getAsJsonObject().get("name").getAsString());
			move.category = EnumMoveCategory.fromString(moveJson.get("damage_class").getAsJsonObject().get("name").getAsString());
			move.target = EnumTargetType.parse(moveJson);
			JsonElement power = moveJson.get("power");
			move.power = power instanceof JsonNull ? -1 : power.getAsInt();
			JsonElement accuracy = moveJson.get("accuracy");
			move.accuracy = accuracy instanceof JsonNull ? 101 : accuracy.getAsInt();
			move.pp = moveJson.get("pp").getAsInt();
			move.priority = moveJson.get("priority").getAsInt();
			move.exportJson(folder.toPath());
		}
	}

	private static void fixMoveData() throws URISyntaxException, IOException {
		String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		File folder = new File(path + "/data/moves");
		for (File file : folder.listFiles()) {
			FileReader reader = new FileReader(file);
			System.out.println(file);
			Move move = Move.fromJson(JsonParser.parseReader(reader).getAsJsonObject());
			reader.close();
			move.exportJson(folder.toPath());
		}
	}

}
