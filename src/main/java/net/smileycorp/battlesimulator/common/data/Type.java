package net.smileycorp.battlesimulator.common.data;

import com.google.gson.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Type {

    protected final String unlocalizedName;
    protected final String name;
    protected final Color colour;
    protected final List<String> properties = new ArrayList<>();
    protected final Map<String, Float> type_effectiveness = new TreeMap<>();
    protected final JsonArray events;

    private Type(String name, JsonObject json) {
        this.unlocalizedName = name;
        this.name = json.get("name").getAsString();
        JsonArray colour =  json.get("colour").getAsJsonArray();
        this.colour = new Color(colour.get(0).getAsInt(), colour.get(1).getAsInt(), colour.get(2).getAsInt());
        if (json.has("properties")) for (JsonElement element : json.get("properties").getAsJsonArray()) properties.add(element.getAsString());
        JsonElement effectiveness = json.get("effectiveness");
        if (effectiveness.isJsonArray()) for (JsonElement element : effectiveness.getAsJsonArray()) {
            JsonObject entry = element.getAsJsonObject();
            type_effectiveness.put(entry.get("type").getAsString(), entry.get("multiplier").getAsFloat());
        }
        if (effectiveness.isJsonObject()) effectiveness.getAsJsonObject().asMap().forEach((k, e) -> type_effectiveness.put(k, e.getAsFloat()));
        events = json.get("events").getAsJsonArray();
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public String getName() {
        return name;
    }

    public Color getColour() {
        return colour;
    }

    public List<String> getProperties() {
        return properties;
    }

    public float getEffectiveness(Type type) {
        if (type_effectiveness.containsKey(type.getUnlocalizedName())) return type_effectiveness.get(type.getUnlocalizedName());
        return 1;
    }

    public void exportJson(Path folder) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File file = new File(folder.toString() + "/" + unlocalizedName + ".json");
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        JsonArray colourjson = new JsonArray();
        colourjson.add(colour.getRed());
        colourjson.add(colour.getGreen());
        colourjson.add(colour.getBlue());
        json.add("colour", colourjson);
        JsonArray props = new JsonArray();
        for (String property : properties) props.add(property);
        json.add("properties", props);
        JsonObject effectiveness = new JsonObject();
        type_effectiveness.forEach(effectiveness::addProperty);
        json.add("effectiveness", effectiveness);
        json.add("events", events);
        file.createNewFile();
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(gson.toJson(json));
        }
    }

    public static Type fromJson(String name, JsonObject json) {
        return new Type(name, json);
    }

}
