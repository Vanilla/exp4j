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
package de.congrace.exp4j.expression;

import java.util.Map;
import java.util.Stack;

import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.function.Function;
import de.congrace.exp4j.operator.Operator;
import de.congrace.exp4j.token.Token;
import de.congrace.exp4j.token.Tokenizer;
import gnu.trove.map.TObjectDoubleMap;

public abstract class RPNConverter {
	private static String substituteUnaryOperators(String expr, Map<String, Operator> operators) {
		final StringBuilder resultBuilder = new StringBuilder();
		int whitespaceCount = 0;
		for (int i = 0; i < expr.length(); i++) {
			boolean afterOperator = false;
			boolean afterParantheses = false;
			boolean expressionStart = false;
			final char c = expr.charAt(i);
			if (Character.isWhitespace(c)) {
				whitespaceCount++;
				resultBuilder.append(c);
				continue;
			}
			if (resultBuilder.length() == whitespaceCount) {
				expressionStart = true;
			}
			// Check if last char in the result is an operator.
			if (resultBuilder.length() > whitespaceCount) {
				if (isOperatorCharacter(resultBuilder.charAt(resultBuilder.length() - 1 - whitespaceCount), operators)) {
					afterOperator = true;
				} else if (resultBuilder.charAt(resultBuilder.length() - 1 - whitespaceCount) == '(') {
					afterParantheses = true;
				}
			}
			switch (c) {
				case '+':
					if (resultBuilder.length() > 0 && !afterOperator && !afterParantheses && !expressionStart) {
						// Not an unary plus so append the char.
						resultBuilder.append(c);
					}
					break;
				case '-':
					if (resultBuilder.length() > 0 && !afterOperator && !afterParantheses && !expressionStart) {
						// Not unary.
						resultBuilder.append(c);
					} else {
						// Unary so we substitute it.
						resultBuilder.append('\'');
					}
					break;
				default:
					resultBuilder.append(c);
			}
			whitespaceCount = 0;
		}
		return resultBuilder.toString();
	}

	public static RPNExpression toRPNExpression(String infix, TObjectDoubleMap<String> variables, Map<String, Function> customFunctions, Map<String, Operator> operators)
			throws UnknownFunctionException, UnparsableExpressionException {
		final Tokenizer tokenizer = new Tokenizer(variables.keySet(), customFunctions, operators);
		final StringBuilder output = new StringBuilder(infix.length());
		final Stack<Token> operatorStack = new Stack<Token>();
		for (final Token token : tokenizer.getTokens(substituteUnaryOperators(infix, operators))) {
			token.mutateStackForInfixTranslation(operatorStack, output);
		}
		// All tokens read, put the rest of the operations on the output.
		while (operatorStack.size() > 0) {
			output.append(operatorStack.pop().getValue()).append(" ");
		}
		final String postfix = output.toString().trim();
		return new RPNExpression(tokenizer.getTokens(postfix), variables);
	}

	private static boolean isOperatorCharacter(char c, Map<String, Operator> operators) {
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}
}
