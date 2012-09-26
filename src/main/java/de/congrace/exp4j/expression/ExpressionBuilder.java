/*
 Copyright 2011 frank asseg

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
package de.congrace.exp4j.expression;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.congrace.exp4j.exception.InvalidCustomFunctionException;
import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.function.Function;
import de.congrace.exp4j.operator.Operator;

/**
 * This is Builder implementation for the exp4j API used to create a Calculable instance for the
 * user
 *
 * @author frank asseg
 *
 */
public class ExpressionBuilder {
	/**
	 * Property name for unary precedence choice. You can set
	 * System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE,"false") in order to change evaluation from
	 * an expression like "-3^2" from "(-3)^2" to "-(3^2)"
	 */
	public static final String PROPERTY_UNARY_HIGH_PRECEDENCE = "exp4j.unary.precedence.high";
	private final Map<String, Double> variables = new LinkedHashMap<String, Double>();
	private final Map<String, Function> customFunctions;
	private final Map<String, Operator> builtInOperators;
	private Map<String, Operator> customOperators = new HashMap<String, Operator>();
	private final List<Character> validOperatorSymbols;
	private final boolean highUnaryPrecedence;
	private String expression;

	/**
	 * Create a new ExpressionBuilder
	 *
	 * @param expression the expression to evaluate
	 */
	public ExpressionBuilder(String expression) {
		this.expression = expression;
		highUnaryPrecedence = System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE) == null
				|| !System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE).equals(
				"false");
		customFunctions = getBuiltinFunctions();
		builtInOperators = getBuiltinOperators();
		validOperatorSymbols = getValidOperators();
	}

	private List<Character> getValidOperators() {
		return Arrays.asList('!', '#', '§', '$', '&', ';', ':', '~', '<', '>',
				'|', '=');
	}

	private Map<String, Operator> getBuiltinOperators() {
		Operator add = new Operator("+") {
			@Override
			public double applyOperation(double[] values) {
				return values[0] + values[1];
			}
		};
		Operator sub = new Operator("-") {
			@Override
			public double applyOperation(double[] values) {
				return values[0] - values[1];
			}
		};
		Operator div = new Operator("/", 3) {
			@Override
			public double applyOperation(double[] values) {
				return values[0] / values[1];
			}
		};
		Operator mul = new Operator("*", 3) {
			@Override
			public double applyOperation(double[] values) {
				return values[0] * values[1];
			}
		};
		Operator mod = new Operator("%", true, 3) {
			@Override
			public double applyOperation(double[] values) {
				return values[0] % values[1];
			}
		};
		Operator umin = new Operator("\'", false,
				this.highUnaryPrecedence ? 7 : 5, 1) {
			@Override
			public double applyOperation(double[] values) {
				return -values[0];
			}
		};
		Operator pow = new Operator("^", false, 5, 2) {
			@Override
			public double applyOperation(double[] values) {
				return Math.pow(values[0], values[1]);
			}
		};
		Map<String, Operator> operations = new HashMap<String, Operator>();
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);
		operations.put("\'", umin);
		operations.put("^", pow);
		operations.put("%", mod);
		return operations;
	}

	private Map<String, Function> getBuiltinFunctions() {
		try {
			Function abs = new Function("abs") {
				@Override
				public double applyFunction(double... args) {
					return Math.abs(args[0]);
				}
			};
			Function acos = new Function("acos") {
				@Override
				public double applyFunction(double... args) {
					return Math.acos(args[0]);
				}
			};
			Function asin = new Function("asin") {
				@Override
				public double applyFunction(double... args) {
					return Math.asin(args[0]);
				}
			};
			Function atan = new Function("atan") {
				@Override
				public double applyFunction(double... args) {
					return Math.atan(args[0]);
				}
			};
			Function cbrt = new Function("cbrt") {
				@Override
				public double applyFunction(double... args) {
					return Math.cbrt(args[0]);
				}
			};
			Function ceil = new Function("ceil") {
				@Override
				public double applyFunction(double... args) {
					return Math.ceil(args[0]);
				}
			};
			Function cos = new Function("cos") {
				@Override
				public double applyFunction(double... args) {
					return Math.cos(args[0]);
				}
			};
			Function cosh = new Function("cosh") {
				@Override
				public double applyFunction(double... args) {
					return Math.cosh(args[0]);
				}
			};
			Function exp = new Function("exp") {
				@Override
				public double applyFunction(double... args) {
					return Math.exp(args[0]);
				}
			};
			Function expm1 = new Function("expm1") {
				@Override
				public double applyFunction(double... args) {
					return Math.expm1(args[0]);
				}
			};
			Function floor = new Function("floor") {
				@Override
				public double applyFunction(double... args) {
					return Math.floor(args[0]);
				}
			};
			Function log = new Function("log") {
				@Override
				public double applyFunction(double... args) {
					return Math.log(args[0]);
				}
			};
			Function sine = new Function("sin") {
				@Override
				public double applyFunction(double... args) {
					return Math.sin(args[0]);
				}
			};
			Function sinh = new Function("sinh") {
				@Override
				public double applyFunction(double... args) {
					return Math.sinh(args[0]);
				}
			};
			Function sqrt = new Function("sqrt") {
				@Override
				public double applyFunction(double... args) {
					return Math.sqrt(args[0]);
				}
			};
			Function tan = new Function("tan") {
				@Override
				public double applyFunction(double... args) {
					return Math.tan(args[0]);
				}
			};
			Function tanh = new Function("tanh") {
				@Override
				public double applyFunction(double... args) {
					return Math.tanh(args[0]);
				}
			};
			Map<String, Function> customFunctions = new HashMap<String, Function>();
			customFunctions.put("abs", abs);
			customFunctions.put("acos", acos);
			customFunctions.put("asin", asin);
			customFunctions.put("atan", atan);
			customFunctions.put("cbrt", cbrt);
			customFunctions.put("ceil", ceil);
			customFunctions.put("cos", cos);
			customFunctions.put("cosh", cosh);
			customFunctions.put("exp", exp);
			customFunctions.put("expm1", expm1);
			customFunctions.put("floor", floor);
			customFunctions.put("log", log);
			customFunctions.put("sin", sine);
			customFunctions.put("sinh", sinh);
			customFunctions.put("sqrt", sqrt);
			customFunctions.put("tan", tan);
			customFunctions.put("tanh", tanh);
			return customFunctions;
		} catch (InvalidCustomFunctionException e) {
			// this should not happen...
			throw new RuntimeException(e);
		}
	}

	/**
	 * build a new {@link Calculable} from the expression using the supplied variables
	 *
	 * @return the {@link Calculable} which can be used to evaluate the expression
	 * @throws UnknownFunctionException when an unrecognized function name is used in the expression
	 * @throws UnparsableExpressionException if the expression could not be parsed
	 */
	public Calculable build() throws UnknownFunctionException,
			UnparsableExpressionException {
		for (Operator op : customOperators.values()) {
			for (int i = 0; i < op.getSymbol().length(); i++) {
				if (!validOperatorSymbols.contains(op.getSymbol().charAt(i))) {
					throw new UnparsableExpressionException(
							op.getSymbol()
							+ " is not a valid symbol for an operator please choose from: !,#,§,$,&,;,:,~,<,>,|,=");
				}
			}
		}
		for (String varName : variables.keySet()) {
			checkVariableName(varName);
			if (customFunctions.containsKey(varName)) {
				throw new UnparsableExpressionException("Variable '" + varName
						+ "' cannot have the same name as a function");
			}
		}
		builtInOperators.putAll(customOperators);
		return RPNConverter.toRPNExpression(expression, variables,
				customFunctions, builtInOperators);
	}

	private void checkVariableName(String varName)
			throws UnparsableExpressionException {
		char[] name = varName.toCharArray();
		for (int i = 0; i < name.length; i++) {
			if (i == 0) {
				if (!Character.isLetter(name[i]) && name[i] != '_') {
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i);
				}
			} else {
				if (!Character.isLetter(name[i]) && !Character.isDigit(name[i]) && name[i] != '_') {
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i);
				}
			}
		}
	}

	/**
	 * add a custom function instance for the evaluator to recognize
	 *
	 * @param function the {@link Function} to add
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withCustomFunction(Function function) {
		customFunctions.put(function.getName(), function);
		return this;
	}

	public ExpressionBuilder withCustomFunctions(
			Collection<Function> functions) {
		for (Function f : functions) {
			withCustomFunction(f);
		}
		return this;
	}

	/**
	 * set the value for a variable
	 *
	 * @param variableName the variable name e.g. "x"
	 * @param value the value e.g. 2.32d
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariable(String variableName, double value) {
		variables.put(variableName, value);
		return this;
	}

	/**
	 * set the variables names used in the expression without setting their values
	 *
	 * @param variableNames vararg {@link String} of the variable names used in the expression
	 * @return the ExpressionBuilder instance
	 */
	public ExpressionBuilder withVariableNames(String... variableNames) {
		for (String variable : variableNames) {
			variables.put(variable, null);
		}
		return this;
	}

	/**
	 * set the values for variables
	 *
	 * @param variableMap a map of variable names to variable values
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariables(Map<String, Double> variableMap) {
		for (Entry<String, Double> v : variableMap.entrySet()) {
			variables.put(v.getKey(), v.getValue());
		}
		return this;
	}

	/**
	 * set a {@link Operator} to be used in the expression
	 *
	 * @param operation the {@link Operator} to be used
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperation(Operator operation) {
		customOperators.put(operation.getSymbol(), operation);
		return this;
	}

	/**
	 * set a {@link Collection} of {@link Operator} to use in the expression
	 *
	 * @param operations the {@link Collection} of {@link Operator} to use
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperations(
			Collection<Operator> operations) {
		for (Operator op : operations) {
			withOperation(op);
		}
		return this;
	}

	/**
	 * set the mathematical expression for parsing
	 *
	 * @param expression a mathematical expression
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withExpression(String expression) {
		this.expression = expression;
		return this;
	}
}
