package net.smileycorp.battlesimulator.common.stat;


public enum EnumStat implements IEnumStat {

	HP("HP", null),
	ATK("Attack", EnumFlavour.SPICY),
	DEF("Defense", EnumFlavour.SOUR),
	SP_ATK("Special Attack", EnumFlavour.DRY),
	SP_DEF("Special Defense", EnumFlavour.BITTER),
	SPD("Speed", EnumFlavour.SWEET);

	private final String name;
	private final EnumFlavour flavour;

	private EnumStat(String name, EnumFlavour flavour) {
		this.name = name;
		this.flavour = flavour;
	}

	public EnumFlavour getFlavour() {
		return flavour;
	}

	public String getName() {
		return name;
	}

	public static EnumStat fromName(String name) {
		for (EnumStat stat : values()) {
			if (stat.getName().toUpperCase().equals(name.toUpperCase().replace("-", " "))) {
				return stat;
			}
		}
		return null;
	}
}
