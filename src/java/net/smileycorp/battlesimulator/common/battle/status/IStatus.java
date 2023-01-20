package net.smileycorp.battlesimulator.common.battle.status;

import net.smileycorp.battlesimulator.common.battle.BattleInstance;
import net.smileycorp.battlesimulator.common.battle.Pokemon;
import net.smileycorp.battlesimulator.common.battle.moves.MoveBattleInstance;

public interface IStatus {
	public void onAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move);

	public void onRecieveAttack(Pokemon pokemon, BattleInstance battle, MoveBattleInstance move);

	public void turnEnd(Pokemon pokemon, BattleInstance battle);

	public void onSwitch(Pokemon pokemon, BattleInstance battle);
}