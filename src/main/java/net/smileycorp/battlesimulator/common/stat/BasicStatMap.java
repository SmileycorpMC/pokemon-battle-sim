package net.smileycorp.battlesimulator.common.stat;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap.StatEntry;

import java.util.*;

public class BasicStatMap implements Iterable<StatEntry> {

	private Map<EnumStat, Integer> map = new HashMap<EnumStat, Integer>();

	public BasicStatMap(int hp, int attack, int defense, int spAttack, int spDefense, int speed) {
		map.put(EnumStat.HP, hp);
		map.put(EnumStat.ATK, attack);
		map.put(EnumStat.DEF, defense);
		map.put(EnumStat.SP_ATK, spAttack);
		map.put(EnumStat.SP_DEF, spDefense);
		map.put(EnumStat.SPD, speed);
	}

	public BasicStatMap(Set<StatEntry> stats) {
		for (StatEntry entry : stats) {
			map.put(entry.getStat(), entry.getValue());
		}
	}

	public int getStat(EnumStat stat) {
		return map.get(stat);
	}

	@Override
	public Iterator<StatEntry> iterator() {
		return new Itr();
	}

	public Set<StatEntry> entrySet() {
		Set<StatEntry> set = new HashSet<StatEntry>();
		forEach((entry) -> set.add(entry));
		return set;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		for (StatEntry entry : this) {
			json.add(entry.getStat().toString(), new JsonPrimitive(entry.getValue()));
		}
		return json;
	}

	private class Itr implements Iterator<StatEntry> {

		private int i = 0;

		@Override
		public boolean hasNext() {
			return i < EnumStat.values().length;
		}

		@Override
		public StatEntry next() {
			EnumStat stat = EnumStat.values()[i];
			i++;
			return new StatEntry(stat, map.get(stat));
		}

	}

	public static class StatEntry {

		private final EnumStat stat;
		private int value;

		public StatEntry(EnumStat stat, int value) {
			this.stat = stat;
			this.value = value;
		}

		public EnumStat getStat() {
			return stat;
		}

		public int getValue() {
			return value;
		}

	}

}
