package net.smileycorp.battlesimulator.common.battle;

import com.google.gson.JsonObject;
import net.smileycorp.battlesimulator.common.battle.status.Status;
import net.smileycorp.battlesimulator.common.battle.status.Status.VolatileStatus;
import net.smileycorp.battlesimulator.common.data.PokemonSpecies;
import net.smileycorp.battlesimulator.common.stat.BasicStatMap;

import java.util.HashMap;
import java.util.Map;

public class Pokemon {

	protected PokemonSpecies species;

	protected int form;

	protected int currentHP;

	protected Status status;

	protected BasicStatMap stats;

	protected Map<String, VolatileStatus> volatileStatuses = new HashMap<>();

	protected Map<String, Object> variables = new HashMap<>();

	public Pokemon(JsonObject json) {

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

	public int getCurrentHP() {
		return currentHP;
	}

	public int getMaxHP() {
		return 0;
	}

}
