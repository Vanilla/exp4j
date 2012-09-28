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
import java.util.Stack;

import gnu.trove.map.TObjectDoubleMap;

import de.congrace.exp4j.token.CalculationToken;
import de.congrace.exp4j.token.Token;

public class RPNExpression implements Calculable {
	private final List<Token> tokens;
	private final String expression;
	private final TObjectDoubleMap<String> variables;

	public RPNExpression(List<Token> tokens, String expression, TObjectDoubleMap<String> variables) {
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
	@Override
	public double calculate() {
		final Stack<Double> stack = new Stack<Double>();
		for (final Token t : tokens) {
			((CalculationToken) t).mutateStackForCalculation(stack, variables);
		}
		return stack.pop();
	}

	@Override
	public String getExpression() {
		return expression;
	}

	@Override
	public void setVariable(String name, double value) {
		variables.put(name, value);
	}

	@Override
	public void setVariables(TObjectDoubleMap<String> variables) {
		this.variables.putAll(variables);
	}
}
