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

import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.congrace.exp4j.token.CalculationToken;
import de.congrace.exp4j.token.Token;

public class RPNExpression implements Calculable {
	final List<Token> tokens;
	final String expression;
	final Map<String, Double> variables;

	public RPNExpression(List<Token> tokens, String expression, final Map<String, Double> variables) {
		super();
		this.tokens = tokens;
		this.expression = expression;
		this.variables = variables;
	}

	/**
	 * calculate the result of the expression and substitute the variables by their values
	 * beforehand
	 *
	 * @param values the variable values to be substituted
	 * @return the result of the calculation
	 * @throws IllegalArgumentException if the variables are invalid
	 */
	public double calculate(double... values) throws IllegalArgumentException {
		if (variables.isEmpty() && values != null) {
			throw new IllegalArgumentException("there are no variables to set values");
		} else if (values != null && values.length != variables.size()) {
			throw new IllegalArgumentException("The are an unequal number of variables and arguments");
		}
		int i = 0;
		if (variables.size() > 0 && values != null) {
			for (Map.Entry<String, Double> entry : variables.entrySet()) {
				entry.setValue(values[i++]);
			}
		}
		final Stack<Double> stack = new Stack<Double>();
		for (final Token t : tokens) {
			((CalculationToken) t).mutateStackForCalculation(stack, variables);
		}
		return stack.pop();
	}

	public String getExpression() {
		return expression;
	}

	public void setVariable(String name, double value) {
		this.variables.put(name, value);
	}

	public double calculate() {
		return calculate(null);
	}
}
