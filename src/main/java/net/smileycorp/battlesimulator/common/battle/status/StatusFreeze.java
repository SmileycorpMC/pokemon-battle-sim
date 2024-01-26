package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.EnumMoveFailReason;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;
import net.smileycorp.battlesimulator.common.data.EnumType;

public class StatusFreeze extends Status {

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {
		if (move.thaws()) {
			pokemon.removeStatus();
		} else if (battle.random.nextInt(5)==1) {
			pokemon.removeStatus();
		} else move.setFailed(EnumMoveFailReason.FROZEN);
	}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {
		if (move.getType() == EnumType.FIRE || move.thaws()) pokemon.removeStatus();
	}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {}

}
