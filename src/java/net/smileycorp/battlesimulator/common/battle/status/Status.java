package net.smileycorp.battlesimulator.common.battle.status;

import java.util.HashMap;
import java.util.Map;

import net.smileycorp.battlesimulator.common.stat.IEnumStat;

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
