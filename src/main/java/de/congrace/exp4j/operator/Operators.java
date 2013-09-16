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
package de.congrace.exp4j.operator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Operators {
	public static final AddOperator ADD = new AddOperator();
	public static final SubstractOperator SUBSTRACT = new SubstractOperator();
	public static final MultiplyOperator MULTIPLY = new MultiplyOperator();
	public static final DivideOperator DIVIDE = new DivideOperator();
	public static final ModuloOperator MODULO = new ModuloOperator();
	public static final PowerOperator POWER = new PowerOperator();
	public static final MinusOperator MINUS = new MinusOperator();
	private static final Map<String, Operator> OPERATORS = new HashMap<String, Operator>();

	static {
		for (Field objectField : Operators.class.getDeclaredFields()) {
			objectField.setAccessible(true);
			try {
				final Object object = objectField.get(null);
				if (object instanceof Operator) {
					register((Operator) object);
				}
			} catch (Exception ex) {
				System.out.println("Could not properly reflect Operators!");
				ex.printStackTrace();
			}
		}
	}

	public static void register(Operator operator) {
		OPERATORS.put(operator.getSymbol(), operator);
	}

	public static Operator getOperator(String symbol) {
		return OPERATORS.get(symbol);
	}

	public static Map<String, Operator> getOperatorMap() {
		return OPERATORS;
	}
}
