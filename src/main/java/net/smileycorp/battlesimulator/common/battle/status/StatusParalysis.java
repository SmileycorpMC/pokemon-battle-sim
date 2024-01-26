package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.EnumMoveFailReason;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;
import net.smileycorp.battlesimulator.common.stat.EnumStat;

public class StatusParalysis extends Status {

	public StatusParalysis() {
		statModifiers.put(EnumStat.SPD, 0.5f);
	}

	@Override
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {
		if (battle.random.nextInt(4)==0) move.setFailed(EnumMoveFailReason.PARALYZED);
	}

	@Override
	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move) {}

	@Override
	public void turnEnd(Pokemon pokemon, BattleInstance battle) {}

	@Override
	public void onSwitch(Pokemon pokemon, BattleInstance battle) {}

}
