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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

import de.congrace.exp4j.constant.Constants;
import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.function.Function;
import de.congrace.exp4j.function.Functions;
import de.congrace.exp4j.operator.Operator;
import de.congrace.exp4j.operator.Operators;

/**
 * This is Builder implementation for the exp4j API used to create a Calculable instance for the
 * user
 *
 * @author frank asseg
 *
 */
public class ExpressionBuilder {
	private final TObjectDoubleMap<String> variables = new TObjectDoubleHashMap<String>();
	private final Map<String, Function> functions = new HashMap<String, Function>(Functions.getFunctionMap());
	private final Map<String, Operator> operators = new HashMap<String, Operator>(Operators.getOperatorMap());
	private final String expression;

	/**
	 * Create a new ExpressionBuilder
	 *
	 * @param expression the expression to evaluate
	 */
	public ExpressionBuilder(String expression) {
		if (expression.trim().isEmpty()) {
			throw new IllegalArgumentException("Expression can not be empty!.");
		}
		this.expression = expression;
	}

	/**
	 * build a new {@link Calculable} from the expression using the supplied variables
	 *
	 * @return the {@link Calculable} which can be used to evaluate the expression
	 * @throws UnknownFunctionException when an unrecognized function name is used in the expression
	 * @throws UnparsableExpressionException if the expression could not be parsed
	 */
	public Calculable build() throws UnknownFunctionException, UnparsableExpressionException {
		withVariables(Constants.findConstants(expression));
		for (String varName : variables.keySet()) {
			checkVariableName(varName);
			if (functions.containsKey(varName)) {
				throw new UnparsableExpressionException("Variable '" + varName
						+ "' cannot have the same name as a function");
			}
		}
		return RPNConverter.toRPNExpression(expression, variables, functions, operators);
	}

	private void checkVariableName(String varName)
			throws UnparsableExpressionException {
		char[] name = varName.toCharArray();
		for (int i = 0; i < name.length; i++) {
			if (i == 0) {
				if (!Character.isLetter(name[i]) && name[i] != '_') {
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '"
							+ name[i] + " at " + i);
				}
			} else {
				if (!Character.isLetter(name[i]) && !Character.isDigit(name[i]) && name[i] != '_') {
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '"
							+ name[i] + " at " + i);
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
	public ExpressionBuilder withCustomFunctions(Function... functions) {
		for (Function function : functions) {
			Function f = this.functions.put(function.getName(), function);
		}
		return this;
	}

	public ExpressionBuilder withCustomFunctions(Collection<Function> functions) {
		for (Function function : functions) {
			withCustomFunctions(function);
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
	 * @param variableNames collection of {@link String} of the variable names used in the
	 * expression
	 * @return the ExpressionBuilder instance
	 */
	public ExpressionBuilder withVariableNames(Collection<String> variableNames) {
		for (String variable : variableNames) {
			variables.put(variable, 0);
		}
		return this;
	}

	/**
	 * set the values for variables
	 *
	 * @param variableMap a map of variable names to variable values
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariables(TObjectDoubleMap<String> variableMap) {
		final TObjectDoubleIterator<String> iterator = variableMap.iterator();
		while (iterator.hasNext()) {
			iterator.advance();
			variables.put(iterator.key(), iterator.value());
		}
		return this;
	}

	/**
	 * set a {@link Operator} to be used in the expression
	 *
	 * @param operator the {@link Operator} to be used
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperator(Operator operator) {
		operators.put(operator.getSymbol(), operator);
		return this;
	}

	/**
	 * set a {@link Collection} of {@link Operator} to use in the expression
	 *
	 * @param operators the {@link Collection} of {@link Operator} to use
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperators(Collection<Operator> operators) {
		for (Operator op : operators) {
			withOperator(op);
		}
		return this;
	}

	/**
	 * Get the expression for the builder.
	 *
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
}
