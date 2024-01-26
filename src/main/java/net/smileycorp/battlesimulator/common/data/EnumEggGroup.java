package net.smileycorp.battlesimulator.common.data;

public enum EnumEggGroup {

	AMORPHOUS("Amorphous", "Indeterminate"),
	BUG("Bug"),
	DRAGON("Dragon"),
	FAIRY("Fairy"),
	FIELD("Field", "Ground"),
	FLYING("Flying"),
	GRASS("Grass", "Plant"),
	HUMAN_LIKE("Human-like", "Humanshape"),
	MINERAL("Mineral"),
	MONSTER("Monster"),
	WATER_1("Water_1"),
	WATER_2("Water_2"),
	WATER_3("Water_3"),
	UNDISCOVERED("Undiscovered", "No_Eggs"),
	DITTO("Ditto");

	private final String name;
	private final String[] altNames;

	private EnumEggGroup(String name, String... altNames) {
		this.name = name;
		this.altNames = altNames;
	}

	public String getName() {
		return name;
	}

	public static EnumEggGroup getGroup(String name) {
		name = name.toLowerCase().replace("_", "");
		for(EnumEggGroup group : values()) {
			if (group.name.toLowerCase().replace("_", "").equals(name)) {
				return group;
			}
			for (String altName : group.altNames) {
				if (altName.toLowerCase().replace("_", "").equals(name)) {
					return group;
				}
			}
		}
		return null;
	}

}
