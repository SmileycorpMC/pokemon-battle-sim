package net.smileycorp.battlesimulator.common.stat;

public enum EnumFlavour {

	SPICY("Spicy"),
	SOUR("Sour"),
	DRY("Dry"),
	BITTER("Bitter"),
	SWEET("Sweet");

	private final String name;

	private EnumFlavour(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
