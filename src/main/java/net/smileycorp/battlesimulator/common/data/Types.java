package net.smileycorp.battlesimulator.common.data;

import com.google.gson.JsonParser;
import net.smileycorp.battlesimulator.common.Main;
import net.smileycorp.battlesimulator.common.battle.TypeChart;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Types {

    private static final Map<String, Type> TYPES = new TreeMap<>();
    private static TypeChart CHART;

    public static void loadTypes() throws Exception {
        File folder = Paths.get(Main.class.getResource("/data/types").toURI()).toFile();
        System.out.println(folder);
        for (File file : folder.listFiles()) {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            System.out.println(file);
            String name = file.getName().replace(".json", "");
            Type type = Type.fromJson(name, JsonParser.parseReader(reader).getAsJsonObject());
            TYPES.put(name, type);
            reader.close();
        }
        TypeChart.Builder builder = new TypeChart.Builder();
        for (Type attacker : TYPES.values()) {
            if (attacker.type_effectiveness.isEmpty()) continue;
            for (Type defender : TYPES.values()) {
                float multiplier = attacker.getEffectiveness(defender);
                if (multiplier != 1) builder.add(attacker, defender, multiplier);
            }
        }
        CHART = builder.build();
    }

    public static void exportTypes() throws Exception {
        Path folder = Paths.get(Main.class.getResource("/data/types").toURI());
        for (Type type : TYPES.values()) type.exportJson(folder);
    }

    public static TypeChart getLoadedTypeChart() {
        return CHART;
    }

    public static Type get(String type) {
        return TYPES.get(type);
    }
}
