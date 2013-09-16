/*
 * This file is part of exp4j.
 *
 * Copyright (c) 2011 Frank Asseg
 * Modifications (c) 2012 Spout LLC <http://www.spout.org/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.congrace.exp4j.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
