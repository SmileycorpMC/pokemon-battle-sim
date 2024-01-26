package net.smileycorp.battlesimulator.common;

import net.smileycorp.battlesimulator.common.data.Types;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) throws Exception {
		try {
			Types.loadTypes();
			File file = Paths.get(Main.class.getResource("/data/type_chart/default.png").toURI()).toFile();
			file.mkdirs();
			file.createNewFile();
			Types.getLoadedTypeChart().exportChart(file);
			Types.exportTypes();
		} catch (URISyntaxException|IOException e) {
			e.printStackTrace();
		}
	}
}
