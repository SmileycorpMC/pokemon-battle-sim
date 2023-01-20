package net.smileycorp.battlesimulator.common.battle;

import java.util.List;
import java.util.Random;

import net.smileycorp.battlesimulator.common.battle.event.BattleEvent;

public class BattleInstance {

	public final Random random = new Random();

	private boolean isInverse;
	private List<BattleEvent> events;
	private Weather currentWeather;
	private Terrain currentTerrain;



}
