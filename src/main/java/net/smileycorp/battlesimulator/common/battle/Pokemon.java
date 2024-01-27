package net.smileycorp.battlesimulator.common.battle;

import com.google.gson.JsonObject;
import net.smileycorp.battlesimulator.common.battle.status.Status;
import net.smileycorp.battlesimulator.common.battle.status.Status.VolatileStatus;
import net.smileycorp.battlesimulator.common.data.PokemonSpecies;
import net.smileycorp.battlesimulator.common.stat.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pokemon {

	protected final PokemonSpecies species;

	protected String form;

	protected int level;
	protected final BasicStatMap ivs;
	protected final BasicStatMap evs;
	protected final EnumNature nature;

	protected String ability;

	protected int currentHP;

	protected Status status;

	protected BasicStatMap stats;
	protected StatStageMap stat_stages;

	protected Map<String, VolatileStatus> volatileStatuses = new HashMap<>();

	protected Map<String, Object> variables = new HashMap<>();

	public Pokemon(PokemonSpecies species, String form, int level, BasicStatMap ivs, BasicStatMap evs, EnumNature nature) {
		this.species = species;
		this.form = form;
		this.level = level;
		this.ivs = ivs;
		this.evs = evs;
		this.nature = nature;
	}

	public boolean addStatus(Status status) {
		if (this.status!=null) return false;
		this.status = status;
		return true;
	}

	public boolean hasStatus(Status status) {
		return this.status == status;
	}

	public void removeStatus() {
		this.status = null;
	}

	public void damagePokemon(double damage) {
		int dmg = (int)Math.floor(damage);
		currentHP -= dmg;
	}

	public boolean hasVariable(String key) {
		return variables.containsKey(key);
	}

	public Object getVariable(String key) {
		return variables.get(key);
	}

	public void setVariable(String key, Object value) {
		variables.put(key, value);
	}

	public void removeVariable(String key) {
		variables.remove(key);
	}

	public BasicStatMap getStats() {
		if (stats == null) calculateStats();
		return stats;
	}

	private void calculateStats() {
		BasicStatMap baseStats = species.forms.get(form).stats;
		Set<BasicStatMap.StatEntry> stats = new HashSet<>();
		for (EnumStat stat : EnumStat.values()) {
			int value = (int) Math.floor((2d * baseStats.getStat(stat) + ivs.getStat(stat) + Math.floor((double) evs.getStat(stat) / 4d) * level) / 100d);
			if (stat == EnumStat.HP) value += level + 10;
			else value = (int) Math.floor((value + 5d) * nature.getMultiplier(stat));
			stats.add(new BasicStatMap.StatEntry(stat, value));
		}
		this.stats = new BasicStatMap(stats);
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public int getMaxHP() {
		return getStats().getStat(EnumStat.HP);
	}

	public int getStat(EnumStat stat) {
		if (stat == EnumStat.HP) return currentHP;
		return (int) Math.floor(getStats().getStat((EnumStat) stat) * stat_stages.getStatMultiplier(stat));
	}

	public double getMultiplier(EnumBattleStat stat) {
		return stat_stages.getStatMultiplier(stat);
	}

}
