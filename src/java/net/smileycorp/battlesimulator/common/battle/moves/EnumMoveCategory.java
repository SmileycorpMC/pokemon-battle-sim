package net.smileycorp.battlesimulator.common.battle.moves;

public enum EnumMoveCategory {

	PHYSICAL, SPECIAL, STATUS;

	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public static EnumMoveCategory fromString(String value) {
		return EnumMoveCategory.valueOf(value.toUpperCase());
	}

}