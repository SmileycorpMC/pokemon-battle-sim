package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.EnumMoveFailReason;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;

public class StatusSleep extends Status {

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {
		int timer = pokemon.hasVariable("sleepTimer") ? (int) pokemon.getVariable("sleepTimer") : battle.random.nextInt(3)+1;
		if (timer-- <= 0) {
			pokemon.removeStatus();
			pokemon.removeVariable("sleepTimer");
		} else {
			move.setFailed(EnumMoveFailReason.ALSEEP);
			pokemon.setVariable("sleepTimer", timer);
		}
	}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {}

}
