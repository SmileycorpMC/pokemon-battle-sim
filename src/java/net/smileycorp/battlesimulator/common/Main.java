package net.smileycorp.battlesimulator.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import net.smileycorp.battlesimulator.common.battle.TypeChart;

public class Main {
	public static void main(String[] args) {
		try {
			String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
			File file = new File(path + "/data/type_chart/default.png");
			file.mkdirs();
			file.createNewFile();
			TypeChart.DEFAULT_CHART.exportChart(file);
		} catch (URISyntaxException|IOException e) {
			e.printStackTrace();
		}
	}
}
