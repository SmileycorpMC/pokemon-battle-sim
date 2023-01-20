package net.smileycorp.battlesimulator.common.data;


public enum EnumType {

	NORMAL("Normal"),
	FIGHTING("Fighting"),
	FLYING("Flying"),
	POISON("Poison"),
	GROUND("Ground"),
	ROCK("Rock"),
	BUG("Bug"),
	GHOST("Ghost"),
	STEEL("Steel"),
	FIRE("Fire"),
	WATER("Water"),
	GRASS("Grass"),
	ELECTRIC("Electric"),
	PSYCHIC("Psychic"),
	ICE("Ice"),
	DRAGON("Dragon"),
	DARK("Dark"),
	FAIRY("Fairy");

	private final String name;

	private EnumType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static EnumType fromString(String value) {
		return valueOf(value.toUpperCase());
	}

}
