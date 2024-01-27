package net.smileycorp.battlesimulator.common.stat;

import net.smileycorp.battlesimulator.common.stat.IEnumStat;

public enum EnumBattleStat implements IEnumStat {

	ACCURACY("Accuracy"),
	EVASION("Evasion");

	private final String name;

	private EnumBattleStat(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
