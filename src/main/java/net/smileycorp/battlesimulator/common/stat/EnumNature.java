package net.smileycorp.battlesimulator.common.stat;

import static net.smileycorp.battlesimulator.common.stat.EnumStat.*;

public enum EnumNature {

	ADAMANT("Adamant", ATK, SP_ATK),
	BASHFUL("Bashful", SP_ATK, SP_ATK),
	BOLD("Bold", DEF, ATK),
	BRAVE("Brave", ATK, SPD),
	CALM("Calm", SP_DEF, ATK),
	CAREFUL("Careful", SP_DEF, SP_ATK),
	DOCILE("Docile", DEF, DEF),
	GENTLE("Gentle", SP_DEF, DEF),
	HARDY("Hardy", ATK, ATK),
	HASTY("Hasty", SPD, DEF),
	IMPISH("Impish", DEF, SP_ATK),
	JOLLY("Jolly", SPD, SP_ATK),
	LAX("Lax", DEF, SP_DEF),
	LONELY("Lonely", ATK, DEF),
	MILD("Mild", SP_ATK, DEF),
	MODEST("Modest", SP_ATK, ATK),
	NAIVE("Naive", SPD, SP_DEF),
	NAUGHTY("Naughty", ATK, SP_DEF),
	QUIET("Quiet", SP_ATK, SPD),
	QUIRKY("Quirky", SP_DEF, SP_DEF),
	RASH("Rash", SP_ATK, SP_DEF),
	RELAXED("Relaxed", DEF, SPD),
	SASSY("Sassy", SP_DEF, SPD),
	SERIOUS("Serious", SPD, SPD),
	TIMID("Timid", SPD, ATK);

	private final String name;
	private final EnumStat raised, lowered;

	private EnumNature(String name, EnumStat raised, EnumStat lowered) {
		this.name = name;
		this.raised = raised;
		this.lowered = lowered;
	}

	@Override
	public String toString() {
		return name;
	}

	public EnumStat getRaisedStat() {
		return raised;
	}

	public EnumStat getLoweredStat() {
		return lowered;
	}

	public boolean isNeutral() {
		return lowered == raised;
	}

	public double getMultiplier(EnumStat stat) {
		if (isNeutral()) return 1;
		if (stat == raised) return 1.1;
		if (stat == lowered) return 0.9;
		return 1;
	}

}
