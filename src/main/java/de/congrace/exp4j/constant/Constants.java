package de.congrace.exp4j.constant;

import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

public class Constants {
	private static final TObjectDoubleMap<String> CONSTANTS = new TObjectDoubleHashMap<String>();

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
		return CONSTANTS;
	}

	public static String replaceConstants(String expression) {
		final TObjectDoubleIterator<String> iterator = CONSTANTS.iterator();
		while (iterator.hasNext()) {
			iterator.advance();
			expression = expression.replaceAll("\\Q" + iterator.key() + "\\E", Double.toString(iterator.value()));
		}
		return expression;
	}
}
