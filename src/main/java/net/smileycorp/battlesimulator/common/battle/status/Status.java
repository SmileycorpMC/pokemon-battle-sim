package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.stat.IEnumStat;

import java.util.HashMap;
import java.util.Map;

public abstract class Status implements IStatus {

	protected Map<IEnumStat, Float> statModifiers = new HashMap<IEnumStat, Float>();

	public Map<IEnumStat, Float> getStatModifiers() {
		return statModifiers;
	}

	public abstract class VolatileStatus implements IStatus {

		protected Map<IEnumStat, Float> statModifiers = new HashMap<IEnumStat, Float>();

		public Map<IEnumStat, Float> getStatModifiers() {
			return statModifiers;
		}
	}

}
