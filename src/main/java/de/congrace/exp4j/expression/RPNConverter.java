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

import java.util.Map;
import java.util.Stack;

import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.function.CustomFunction;
import de.congrace.exp4j.operator.CustomOperator;
import de.congrace.exp4j.token.Token;
import de.congrace.exp4j.token.Tokenizer;

public abstract class RPNConverter {
	private static String substituteUnaryOperators(String expr, Map<String, CustomOperator> operators) {
		final StringBuilder exprBuilder = new StringBuilder(expr.length());
		final char[] data = expr.toCharArray();
		char lastChar = ' ';
		StringBuilder lastOperation = new StringBuilder();
		for (int i = 0; i < expr.length(); i++) {
			if (exprBuilder.length() > 0) {
				lastChar = exprBuilder.charAt(exprBuilder.length() - 1);
			}
			final char c = data[i];
			if (i > 0 && isOperatorCharacter(expr.charAt(i - 1), operators)) {
				if (!operators.containsKey(lastOperation.toString() + expr.charAt(i - 1))) {
					lastOperation = new StringBuilder();
				}
				lastOperation.append(expr.charAt(i - 1));
			} else if (i > 0 && !Character.isWhitespace(expr.charAt(i - 1))) {
				lastOperation = new StringBuilder();
			}
			switch (c) {
				case '+':
					if (i > 0 && lastChar != '(' && operators.get(lastOperation.toString()) == null) {
						exprBuilder.append(c);
					}
					break;
				case '-':
					if (i > 0 && lastChar != '(' && operators.get(lastOperation.toString()) == null) {
						exprBuilder.append(c);
					} else {
						exprBuilder.append('\'');
					}
					break;
				default:
					if (!Character.isWhitespace(c)) {
						exprBuilder.append(c);
					}
			}
		}
		return exprBuilder.toString();
	}

	public static RPNExpression toRPNExpression(String infix, Map<String, Double> variables,
			Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> operators)
			throws UnknownFunctionException, UnparsableExpressionException {
		final Tokenizer tokenizer = new Tokenizer(variables.keySet(), customFunctions, operators);
		final StringBuilder output = new StringBuilder(infix.length());
		final Stack<Token> operatorStack = new Stack<Token>();
		for (final Token token : tokenizer.getTokens(substituteUnaryOperators(infix, operators))) {
			token.mutateStackForInfixTranslation(operatorStack, output);
		}
		// all tokens read, put the rest of the operations on the output;
		while (operatorStack.size() > 0) {
			output.append(operatorStack.pop().getValue()).append(" ");
		}
		String postfix = output.toString().trim();
		return new RPNExpression(tokenizer.getTokens(postfix), postfix, variables);
	}

	private static boolean isOperatorCharacter(char c, Map<String, CustomOperator> operators) {
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}
}
