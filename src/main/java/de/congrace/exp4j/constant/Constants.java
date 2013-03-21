package de.congrace.exp4j.constant;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import gnu.trove.TCollections;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

public class Constants {
	private static final TObjectDoubleMap<String> CONSTANTS = new TObjectDoubleHashMap<String>();
	private static final Pattern CONSTANT_PATTERN = Pattern.compile("[a-zA-Z_]\\w*");

	static {
		register("PI", Math.PI);
		register("E", Math.E);
	}

	public static void register(String name, double value) {
		CONSTANTS.put(name, value);
	}

	public static double getConstant(String name) {
		return CONSTANTS.get(name);
	}

	public static TObjectDoubleMap<String> getConstantMap() {
		return TCollections.unmodifiableMap(CONSTANTS);
	}

	public static TObjectDoubleMap<String> findConstants(String expression) {
		final TObjectDoubleMap<String> found = new TObjectDoubleHashMap<String>();
		final Matcher matcher = CONSTANT_PATTERN.matcher(expression);
		while (matcher.find()) {
			final String match = matcher.group();
			if (CONSTANTS.containsKey(match)) {
				found.put(match, CONSTANTS.get(match));
			}
		}
		return found;
	}

	public static boolean isConstant(String expression) {
		return CONSTANTS.containsKey(expression);
	}
}
