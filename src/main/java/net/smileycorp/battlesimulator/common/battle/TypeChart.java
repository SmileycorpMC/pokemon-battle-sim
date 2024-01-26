package net.smileycorp.battlesimulator.common.battle;

import static net.smileycorp.battlesimulator.common.data.EnumType.BUG;
import static net.smileycorp.battlesimulator.common.data.EnumType.DARK;
import static net.smileycorp.battlesimulator.common.data.EnumType.DRAGON;
import static net.smileycorp.battlesimulator.common.data.EnumType.ELECTRIC;
import static net.smileycorp.battlesimulator.common.data.EnumType.FAIRY;
import static net.smileycorp.battlesimulator.common.data.EnumType.FIGHTING;
import static net.smileycorp.battlesimulator.common.data.EnumType.FIRE;
import static net.smileycorp.battlesimulator.common.data.EnumType.FLYING;
import static net.smileycorp.battlesimulator.common.data.EnumType.GHOST;
import static net.smileycorp.battlesimulator.common.data.EnumType.GRASS;
import static net.smileycorp.battlesimulator.common.data.EnumType.GROUND;
import static net.smileycorp.battlesimulator.common.data.EnumType.ICE;
import static net.smileycorp.battlesimulator.common.data.EnumType.NORMAL;
import static net.smileycorp.battlesimulator.common.data.EnumType.POISON;
import static net.smileycorp.battlesimulator.common.data.EnumType.PSYCHIC;
import static net.smileycorp.battlesimulator.common.data.EnumType.ROCK;
import static net.smileycorp.battlesimulator.common.data.EnumType.STEEL;
import static net.smileycorp.battlesimulator.common.data.EnumType.WATER;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.smileycorp.battlesimulator.common.data.EnumType;

public class TypeChart {

	private Map<EnumType, Map<EnumType, Float>> map = new HashMap<EnumType, Map<EnumType, Float>>();

	public static TypeChart DEFAULT_CHART = getDefaultChart();

	public static TypeChart LOADED_CHART = DEFAULT_CHART;

	public TypeChart add(EnumType defender, EnumType attacker, float multiplier) {
		if (!map.containsKey((defender))) map.put(defender, new HashMap<EnumType, Float>());
		map.get(defender).put(attacker, multiplier);
		return this;
	}

	public float getEffectiveness(EnumType defender, EnumType attacker, boolean isInverse) {
		if (!map.containsKey((defender))) return 1;
		Map<EnumType, Float> submap = map.get(defender);
		if (submap == null) return 1;
		if (!submap.containsKey((attacker))) return 1;
		float value = submap.get(attacker);
		if (isInverse) {
			if (value<=0) value = 0.5f;
			value = 1/value;
		}
		return value;
	}

	public static TypeChart importChart(File file) {
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
	}

	public void exportChart(File file) {
		int length = EnumType.values().length;
		BufferedImage image = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
		for (EnumType defender : EnumType.values()) {
			Map<EnumType, Float> effectiveness = map.get(defender);
			for (EnumType attacker : EnumType.values()) {
				int colour = 0xffffff;
				if (effectiveness!=null) {
					if (effectiveness.containsKey(attacker)) {
						float value = effectiveness.get(attacker);
						if (value <= 0) colour = 0x000000;
						else if (value < 1) colour = 0xFF0000;
						else if (value > 1) colour = 0x00FF00;
					}
				}
				image.setRGB(defender.ordinal(), attacker.ordinal(), colour);
			}
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static TypeChart getDefaultChart() {
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
	}

}
