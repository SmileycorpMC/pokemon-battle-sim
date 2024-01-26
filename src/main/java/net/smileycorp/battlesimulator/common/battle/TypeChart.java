package net.smileycorp.battlesimulator.common.battle;

import net.smileycorp.battlesimulator.common.data.Type;
import net.smileycorp.battlesimulator.common.data.Types;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeChart {

	private final List<Type> loaded_types;
	private final Map<Type, Map<Type, Float>> map;

	private TypeChart(List<Type> loaded_types, Map<Type, Map<Type, Float>> map) {
		this.loaded_types = loaded_types;
		this.map = map;
	}

	public float getEffectiveness(Type attacker, Type defender, boolean isInverse) {
		if (!map.containsKey((attacker))) return 1;
		Map<Type, Float> submap = map.get(attacker);
		if (submap == null) return 1;
		if (!submap.containsKey((defender))) return 1;
		float value = submap.get(defender);
		if (isInverse) {
			if (value <= 0) value = 0.5f;
			value = 1 / value;
		}
		return value;
	}

	/*public static TypeChart importChart(File file) {
		try {
			TypeChart chart = new TypeChart();
			BufferedImage image = ImageIO.read(file);
			for (EnumType defender : EnumType.values()) {
				Map<EnumType, Float> effectiveness = new HashMap<EnumType, Float>();
				for (EnumType attacker : EnumType.values()) {
					int colour = image.getRGB(defender.ordinal(), attacker.ordinal());
					if (colour == 0x000000) {
						effectiveness.put(attacker, 0f);
					} else if (colour == 0xFF0000) {
						effectiveness.put(attacker, 0.5f);
					} else if (colour == 0x00FF00) {
						effectiveness.put(attacker, 2f);
					}
				}
				chart.map.put(defender, effectiveness);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/

	public void exportChart(File file) {
		int length = loaded_types.size() + 1;
		BufferedImage image = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, 0xFFFFFF);
		for (int i = 0; i < loaded_types.size(); i++) {
			Type attacker = loaded_types.get(i);
			image.setRGB(i + 1, 0, attacker.getColour().getRGB());
			image.setRGB(0, i + 1, attacker.getColour().getRGB());
			Map<Type, Float> effectiveness = map.get(attacker);
			for (int j = 0; j < loaded_types.size(); j++) {
				Type defender = loaded_types.get(j);
				int colour = 0xFFFFFF;
				if (effectiveness != null) {
					if (effectiveness.containsKey(defender)) {
						float value = effectiveness.get(defender);
						if (value <= 0) colour = 0x000000;
						else if (value < 1) colour = 0xFF0000;
						else if (value > 1) colour = 0x00FF00;
					}
				}
				image.setRGB(j + 1, i + 1, colour);
			}
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static TypeChart getDefaultChart() {
		return new TypeChart().add(NORMAL, FIGHTING, 2)
				.add(NORMAL, GHOST, 0)
				.add(FIGHTING, FLYING, 2)
				.add(FIGHTING, ROCK, 0.5f)
				.add(FIGHTING, BUG, 0.5f)
				.add(FIGHTING, PSYCHIC, 2)
				.add(FIGHTING, DARK, 0.5f)
				.add(FIGHTING, FAIRY, 2)
				.add(FLYING, FIGHTING, 0.5f)
				.add(FLYING, GROUND, 0)
				.add(FLYING, ROCK, 2)
				.add(FLYING, BUG, 0.5f)
				.add(FLYING, GRASS, 0.5f)
				.add(FLYING, ELECTRIC, 2)
				.add(FLYING, ICE, 2)
				.add(POISON, FIGHTING, 0.5f)
				.add(POISON, POISON, 0.5f)
				.add(POISON, GROUND, 2)
				.add(POISON, BUG, 0.5f)
				.add(POISON, GRASS, 0.5f)
				.add(POISON, PSYCHIC, 2)
				.add(POISON, FAIRY, 0.5f)
				.add(GROUND, POISON, 0.5f)
				.add(GROUND, ROCK, 0.5f)
				.add(GROUND, WATER, 2)
				.add(GROUND, GRASS, 2)
				.add(GROUND, ELECTRIC, 0)
				.add(GROUND, ICE, 2)
				.add(ROCK, NORMAL, 0.5f)
				.add(ROCK, FIGHTING, 2)
				.add(ROCK, FLYING, 0.5f)
				.add(ROCK, POISON, 0.5f)
				.add(ROCK, GROUND, 2)
				.add(ROCK, STEEL, 2)
				.add(ROCK, FIRE, 0.5f)
				.add(ROCK, WATER, 2)
				.add(ROCK, GRASS, 2)
				.add(BUG, FIGHTING, 0.5f)
				.add(BUG, FLYING, 2)
				.add(BUG, GROUND, 0.5f)
				.add(BUG, ROCK, 2)
				.add(BUG, FIRE, 2)
				.add(BUG, GRASS, 0.5f)
				.add(GHOST, NORMAL, 0)
				.add(GHOST, FIGHTING, 0)
				.add(GHOST, POISON, 0.5f)
				.add(GHOST, BUG, 0.5f)
				.add(GHOST, GHOST, 2)
				.add(GHOST, DARK, 2)
				.add(STEEL, NORMAL, 0.5f)
				.add(STEEL, FIGHTING, 2)
				.add(STEEL, FLYING, 0.5f)
				.add(STEEL, POISON, 0)
				.add(STEEL, GROUND, 2)
				.add(STEEL, ROCK, 0.5f)
				.add(STEEL, BUG, 0.5f)
				.add(STEEL, STEEL, 0.5f)
				.add(STEEL, FIRE, 2)
				.add(STEEL, GRASS, 0.5f)
				.add(STEEL, PSYCHIC, 0.5f)
				.add(STEEL, ICE, 0.5f)
				.add(STEEL, DRAGON, 0.5f)
				.add(STEEL, FAIRY, 0.5f)
				.add(FIRE, GROUND, 2)
				.add(FIRE, ROCK, 2)
				.add(FIRE, BUG, 0.5f)
				.add(FIRE, STEEL, 0.5f)
				.add(FIRE, FIRE, 0.5f)
				.add(FIRE, WATER, 2)
				.add(FIRE, GRASS, 0.5f)
				.add(FIRE, ICE, 0.5f)
				.add(FIRE, FAIRY, 0.5f)
				.add(WATER, STEEL, 0.5f)
				.add(WATER, FIRE, 0.5f)
				.add(WATER, WATER, 0.5f)
				.add(WATER, GRASS, 2)
				.add(WATER, ELECTRIC, 2)
				.add(WATER, ICE, 0.5f )
				.add(GRASS, FLYING, 2)
				.add(GRASS, POISON, 2)
				.add(GRASS, GROUND, 0.5f)
				.add(GRASS, BUG, 2)
				.add(GRASS, FIRE, 2)
				.add(GRASS, WATER, 0.5f)
				.add(GRASS, GRASS, 0.5f)
				.add(GRASS, ELECTRIC, 0.5f)
				.add(GRASS, ICE, 2)
				.add(ELECTRIC, FLYING, 0.5f)
				.add(ELECTRIC, GROUND, 2)
				.add(ELECTRIC, STEEL, 0.5f)
				.add(ELECTRIC, ELECTRIC, 0.5f)
				.add(PSYCHIC, FIGHTING, 0.5f)
				.add(PSYCHIC, BUG, 2)
				.add(PSYCHIC, GHOST, 2)
				.add(PSYCHIC, PSYCHIC, 0.5f)
				.add(PSYCHIC, DARK, 2)
				.add(ICE, FIGHTING, 2)
				.add(ICE, ROCK, 2)
				.add(ICE, STEEL, 2)
				.add(ICE, FIRE, 2)
				.add(ICE, ICE, 0.5f)
				.add(DRAGON, FIRE, 0.5f)
				.add(DRAGON, WATER, 0.5f)
				.add(DRAGON, GRASS, 0.5f)
				.add(DRAGON, ELECTRIC, 0.5f)
				.add(DRAGON, ICE, 2)
				.add(DRAGON, DRAGON, 2)
				.add(DRAGON, FAIRY, 2)
				.add(DARK, FIGHTING, 2)
				.add(DARK, BUG, 2)
				.add(DARK, GHOST, 0.5f)
				.add(DARK, PSYCHIC, 0)
				.add(DARK, DARK, 0.5f)
				.add(DARK, FAIRY, 2)
				.add(FAIRY, FIGHTING, 0.5f)
				.add(FAIRY, POISON, 2)
				.add(FAIRY, BUG, 0.5f)
				.add(FAIRY, STEEL, 2)
				.add(FAIRY, DRAGON, 0)
				.add(FAIRY, DARK, 0.5f);
	}*/

	public static final class Builder {

		private List<Type> loaded_types = new ArrayList<>();
		private Map<Type, Map<Type, Float>> map = new HashMap<>();

		public Builder() {
			loaded_types.add(Types.get("normal"));
			loaded_types.add(Types.get("fire"));
			loaded_types.add(Types.get("water"));
			loaded_types.add(Types.get("electric"));
			loaded_types.add(Types.get("grass"));
			loaded_types.add(Types.get("ice"));
			loaded_types.add(Types.get("fighting"));
			loaded_types.add(Types.get("poison"));
			loaded_types.add(Types.get("ground"));
			loaded_types.add(Types.get("flying"));
			loaded_types.add(Types.get("psychic"));
			loaded_types.add(Types.get("bug"));
			loaded_types.add(Types.get("rock"));
			loaded_types.add(Types.get("ghost"));
			loaded_types.add(Types.get("dragon"));
			loaded_types.add(Types.get("dark"));
			loaded_types.add(Types.get("steel"));
			loaded_types.add(Types.get("fairy"));
		}

		public Builder add(Type attacker, Type defender, float multiplier) {
			if (!map.containsKey((attacker))) map.put(attacker, new HashMap<>());
			map.get(attacker).put(defender, multiplier);
			if (!loaded_types.contains(attacker)) loaded_types.add(attacker);
			if (!loaded_types.contains(defender)) loaded_types.add(defender);
			return this;
		}

		public TypeChart build() {
			return new TypeChart(loaded_types, map);
		}

	}

}
