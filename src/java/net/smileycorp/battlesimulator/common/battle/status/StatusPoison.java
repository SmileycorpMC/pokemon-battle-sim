package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;

public class StatusPoison extends Status {

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {
		pokemon.damagePokemon(pokemon.getMaxHP()/8);
	}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {}

}
