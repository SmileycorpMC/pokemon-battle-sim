package net.smileycorp.battlesimulator.common.battle;

import net.smileycorp.battlesimulator.common.battle.event.BattleEvent;

import java.util.List;
import java.util.Random;

public class BattleInstance {

	public final Random random = new Random();

	private boolean isInverse;
	private List<BattleEvent> events;
	private Weather currentWeather;
	private Terrain currentTerrain;



}
