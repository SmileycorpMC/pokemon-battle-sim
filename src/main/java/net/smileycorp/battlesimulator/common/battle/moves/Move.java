package net.smileycorp.battlesimulator.common.battle.moves;

import com.google.gson.*;
import net.smileycorp.battlesimulator.common.data.EnumType;
import net.smileycorp.battlesimulator.common.util.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Move {

	public String name;
	public int id;
	public EnumType type;
	public EnumMoveCategory category;
	public EnumTargetType target;
	public int power;
	public int accuracy;
	public int pp;
	public int priority;
	public final List<String> properties = new ArrayList<String>();

	public void exportJson(Path folder) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File(folder.toString() + "/" + TextUtils.removeSpecialChars(name.toLowerCase()) + ".json");
		JsonObject json = new JsonObject();
		json.add("name", new JsonPrimitive(name));
		json.add("type", new JsonPrimitive(type.toString()));
		json.add("category", new JsonPrimitive(category.toString()));
		json.add("target", new JsonPrimitive(target.toString()));
		json.add("power", new JsonPrimitive(power));
		json.add("accuracy", new JsonPrimitive(accuracy));
		json.add("pp", new JsonPrimitive(pp));
		json.add("priority", new JsonPrimitive(priority));
		json.add("z-move_power", new JsonPrimitive(-1));
		json.add("max_move_power", new JsonPrimitive(-1));
		JsonArray properties = new JsonArray();
		for (String prop : this.properties) properties.add(prop);
		json.add("properties", properties);
		JsonArray primary_effects = new JsonArray();
		json.add("primary_effects", primary_effects);
		JsonArray secondary_effects = new JsonArray();
		json.add("secondary_effects", secondary_effects);
		JsonArray z_move_effects = new JsonArray();
		json.add("z-move_effects", z_move_effects);
		file.createNewFile();
		try(FileWriter writer = new FileWriter(file)) {
			writer.write(gson.toJson(json));
		}
	}

	public static Move fromJson(JsonObject json) {
		Move move = new Move();
		move.name = json.get("name").getAsString();
		move.type = EnumType.fromString(json.get("type").getAsString());
		move.category = EnumMoveCategory.fromString(json.get("category").getAsString());
		move.target = EnumTargetType.fromString(json.get("target").getAsString());
		move.power = json.get("power").getAsInt();
		move.accuracy = json.get("accuracy").getAsInt();
		move.pp = json.get("pp").getAsInt();
		move.priority = json.get("priority").getAsInt();
		return move;
	}

}
