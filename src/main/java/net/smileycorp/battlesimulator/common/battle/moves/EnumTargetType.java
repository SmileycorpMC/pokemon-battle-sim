package net.smileycorp.battlesimulator.common.battle.moves;

import com.google.gson.JsonObject;

public enum EnumTargetType {

	SELF, SELECT_SELF_OR_ALLY, SELECT_SELF_OR_ADJACENT_ALLY, SELECT_AJACENT, SELECT_ANY, SELECT_AJACENT_ALLY, SELECT_ALLY, SELECT_OPPONENT, ALL, ALL_ADJACENT, ALL_OTHER, ALL_OPPONENTS, ALL_ADJACENT_OPPONENTS, ALL_ALLY,
	SELF_AND_ALLIES, OPPOSITE, RANDOM_ADJACENT_OPPONENT;

	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public static EnumTargetType fromString(String value) {
		return EnumTargetType.valueOf(value.toUpperCase());
	}

	public static EnumTargetType parse(JsonObject moveJson) {
		String type = moveJson.get("target").getAsJsonObject().get("name").getAsString();
		switch (type) {
		case "specific-move":
			return SELF;
		case "selected-pokemon-me-first":
			return SELECT_OPPONENT;
		case "ally":
			return SELECT_AJACENT_ALLY;
		case "users-field":
			return SELF_AND_ALLIES;
		case "user-or-ally":
			return SELECT_SELF_OR_ADJACENT_ALLY;
		case "opponents-field":
			return ALL_OPPONENTS;
		case "user":
			return SELF;
		case "random-opponent":
			return RANDOM_ADJACENT_OPPONENT;
		case "all-other-pokemon":
			return ALL_OTHER;
		case "selected-pokemon":
			return SELECT_AJACENT;
		case "all-opponents":
			return ALL_ADJACENT_OPPONENTS;
		case "entire-field":
			return ALL;
		case "user-and-allies":
			return SELF_AND_ALLIES;
		case "all-pokemon":
			return ALL;
		default:
			return null;
		}
	}

}