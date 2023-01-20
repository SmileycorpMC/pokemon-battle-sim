package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;
import net.smileycorp.battlesimulator.common.stat.EnumStat;

public class StatusFrostbite extends Status {

	public StatusFrostbite() {
		statModifiers.put(EnumStat.SP_ATK, 0.5f);
	}

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {
		if (move.thaws()) {
			pokemon.removeStatus();
		}
	}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {
		pokemon.damagePokemon(pokemon.getMaxHP()/16);
	}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {}

}
