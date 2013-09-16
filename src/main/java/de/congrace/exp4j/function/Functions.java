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
package de.congrace.exp4j.function;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Functions {
	public static final AbsoluteFunction ABSOLUTE = new AbsoluteFunction();
	public static final CeilingFunction CEILING = new CeilingFunction();
	public static final FloorFunction FLOOR = new FloorFunction();
	public static final ArcSineFunction ARCSINE = new ArcSineFunction();
	public static final ArcCosineFunction ARCCOSINE = new ArcCosineFunction();
	public static final ArcTangentFunction ARCTANGENT = new ArcTangentFunction();
	public static final SineFunction SINE = new SineFunction();
	public static final CosineFunction COSINE = new CosineFunction();
	public static final TangentFunction TANGENT = new TangentFunction();
	public static final SquareRootFunction SQUARE_ROOT = new SquareRootFunction();
	public static final FastSquareRootFunction FAST_SQUARE_ROOT = new FastSquareRootFunction();
	public static final LogFunction LOG = new LogFunction();
	private static final Map<String, Function> FUNCTIONS = new HashMap<String, Function>();

	static {
		for (Field objectField : Functions.class.getDeclaredFields()) {
			objectField.setAccessible(true);
			try {
				final Object object = objectField.get(null);
				if (object instanceof Function) {
					register((Function) object);
				}
			} catch (Exception ex) {
				System.out.println("Could not properly reflect Functions!");
				ex.printStackTrace();
			}
		}
	}

	public static void register(Function function) {
		FUNCTIONS.put(function.getName(), function);
	}

	public static Function getFunction(String name) {
		return FUNCTIONS.get(name);
	}

	public static Map<String, Function> getFunctionMap() {
		return Collections.unmodifiableMap(FUNCTIONS);
	}

	public static boolean isFunction(String expression) {
		return FUNCTIONS.containsKey(expression);
	}
}
