package net.smileycorp.battlesimulator.common.data;

public enum EnumExpRate {

	ERRATIC("Erratic", 600000),
	FAST("Fast", 800000),
	MEDIUM_FAST("Medium_Fast", 1000000),
	MEDIUM_SLOW("Medium_Slow", 1059860),
	SLOW("Slow", 1250000),
	FLUCTUATING("Fluctuating", 1640000);

	private final String name;
	private final int totalExp;

	private EnumExpRate(String name, int totalExp) {
		this.name = name;
		this.totalExp = totalExp;
	}

	public String getName() {
		return name;
	}

	public int getTotalExp() {
		return totalExp;
	}

	public static EnumExpRate get(String name) {
		if (name.toLowerCase().equals("medium")) name = "Medium_Fast";
		else if (name.toLowerCase().equals("fast-then-very-slow")) name = "Fluctuating";
		else if (name.toLowerCase().equals("slow-then-very-fast")) name = "Erratic";
		return EnumExpRate.valueOf(name.toUpperCase().replace("-", "_"));
	}

}
