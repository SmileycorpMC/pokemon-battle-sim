package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;

public class StatusBadlyPoison extends Status {

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {
		if (pokemon.hasVariable("badlyPoisonTimer")) {
			pokemon.setVariable("badlyPoisonTimer", (int)pokemon.getVariable("badlyPoisonTimer") + 1);
		} else {
			pokemon.setVariable("badlyPoisonTimer", 1);
		}
		pokemon.damagePokemon(pokemon.getMaxHP()/16 * (int)pokemon.getVariable("badlyPoisonTimer"));
	}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {
		if (pokemon.hasVariable("badlyPoisonTimer")) pokemon.removeVariable("badlyPoisonTimer");
	}

}
