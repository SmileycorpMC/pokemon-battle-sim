package net.smileycorp.battlesimulator.common.util;

public class TextUtils {

	public static String properCase(String value) {
		String[] split = value.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String string : split) {
			char[] chars = string.toCharArray();
			chars[0] = String.valueOf(chars[0]).toUpperCase().charAt(0);
			builder.append(String.valueOf(chars));
			builder.append(" ");
		}
		return builder.toString().trim();
	}

	public static String removeSpecialChars(String name) {
		return name.replace("\\", "").replace("/", "").replace(":", "")
				.replace("*", "").replace("?", "").replace("\"", "")
				.replace("<", "").replace(">", "").replace("|", "").replace(" ", "_");
	}

}
