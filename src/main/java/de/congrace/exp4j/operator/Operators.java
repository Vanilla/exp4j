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
