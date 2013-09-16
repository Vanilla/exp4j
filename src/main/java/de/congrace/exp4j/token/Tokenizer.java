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
package de.congrace.exp4j.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.function.Function;
import de.congrace.exp4j.operator.Operator;

public class Tokenizer {
	private final Set<String> variableNames;
	private final Map<String, Function> functions;
	private final Map<String, Operator> operators;

	public Tokenizer(Set<String> variableNames, Map<String, Function> functions, Map<String, Operator> operators) {
		this.variableNames = variableNames;
		this.functions = functions;
		this.operators = operators;
	}

	private boolean isDigitOrDecimalSeparator(char c) {
		return Character.isDigit(c) || c == '.';
	}

	private boolean isVariable(String name) {
		if (variableNames != null) {
			for (String var : variableNames) {
				if (name.equals(var)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isFunction(String name) {
		return functions.containsKey(name);
	}

	private boolean isNotationSeparator(char c) {
		return c == 'e' || c == 'E';
	}

	private boolean isOperatorCharacter(char c) {
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}

	public List<Token> getTokens(final String expression) throws UnparsableExpressionException, UnknownFunctionException {
		final List<Token> tokens = new ArrayList<Token>();
		final char[] chars = expression.toCharArray();
		int openBraces = 0;
		int openCurly = 0;
		int openSquare = 0;
		// Iterate over the chars and fork on different types of input.
		Token lastToken;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == ' ') {
				continue;
			}
			if (Character.isDigit(c)) {
				final StringBuilder valueBuilder = new StringBuilder(1);
				// Handle the numbers of the expression.
				valueBuilder.append(c);
				int numberLen = 1;
				boolean lastCharNotationSeparator = false; // Needed to determine if a + or - following an e/E is a unary operation.
				boolean notationSeparatorOccured = false; // To check if only one notation separator has occurred.
				while (chars.length > i + numberLen) {
					if (isDigitOrDecimalSeparator(chars[i + numberLen])) {
						valueBuilder.append(chars[i + numberLen]);
						lastCharNotationSeparator = false;
					} else if (isNotationSeparator(chars[i + numberLen])) {
						if (notationSeparatorOccured) {
							throw new UnparsableExpressionException("Number can have only one notation separator 'e/E'");
						}
						valueBuilder.append(chars[i + numberLen]);
						lastCharNotationSeparator = true;
						notationSeparatorOccured = true;
					} else if (lastCharNotationSeparator && (chars[i + numberLen] == '-' || chars[i + numberLen] == '+')) {
						valueBuilder.append(chars[i + numberLen]);
						lastCharNotationSeparator = false;
					} else {
						break; // Break out of the while loop here, since the number seem finished.
					}
					numberLen++;
				}
				i += numberLen - 1;
				lastToken = new NumberToken(valueBuilder.toString());
			} else if (Character.isLetter(c) || c == '_') {
				// Can be a variable or function.
				final StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset && (Character.isLetter(chars[i + offset]) || Character.isDigit(chars[i + offset]) || chars[i + offset] == '_')) {
					nameBuilder.append(chars[i + offset++]);
				}
				String name = nameBuilder.toString();
				if (this.isVariable(name)) {
					// A variable.
					i += offset - 1;
					lastToken = new VariableToken(name);
				} else if (this.isFunction(name)) {
					// might be a function.
					i += offset - 1;
					lastToken = new FunctionToken(name, functions.get(name));
				} else {
					// An unknown symbol was encountered.
					throw new UnparsableExpressionException(expression, c, i + 1);
				}
			} else if (c == ',') {
				// A function separator, hopefully.
				lastToken = new FunctionSeparatorToken();
			} else if (isOperatorCharacter(c)) {
				// Might be an operation.
				StringBuilder symbolBuilder = new StringBuilder();
				symbolBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset && (isOperatorCharacter(chars[i + offset])) && isOperatorStart(symbolBuilder.toString() + chars[i + offset])) {
					symbolBuilder.append(chars[i + offset]);
					offset++;
				}
				String symbol = symbolBuilder.toString();
				if (operators.containsKey(symbol)) {
					i += offset - 1;
					lastToken = new OperatorToken(symbol, operators.get(symbol));
				} else {
					throw new UnparsableExpressionException(expression, c, i + 1);
				}
			} else if (c == '(') {
				openBraces++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '{') {
				openCurly++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '[') {
				openSquare++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == ')') {
				openBraces--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '}') {
				openCurly--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == ']') {
				openSquare--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else {
				// An unknown symbol was encountered.
				throw new UnparsableExpressionException(expression, c, i + 1);
			}
			tokens.add(lastToken);
		}
		if (openCurly != 0 || openBraces != 0 | openSquare != 0) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("There are ");
			boolean first = true;
			if (openBraces != 0) {
				errorBuilder.append(Math.abs(openBraces)).append(" unmatched parentheses ");
				first = false;
			}
			if (openCurly != 0) {
				if (!first) {
					errorBuilder.append("and ");
				}
				errorBuilder.append(Math.abs(openCurly)).append(" unmatched curly brackets ");
				first = false;
			}
			if (openSquare != 0) {
				if (!first) {
					errorBuilder.append("and ");
				}
				errorBuilder.append(Math.abs(openSquare)).append(" unmatched square brackets ");
			}
			errorBuilder.append("in expression '").append(expression).append("'");
			throw new UnparsableExpressionException(errorBuilder.toString());
		}
		return tokens;
	}

	private boolean isOperatorStart(String op) {
		for (String operatorName : operators.keySet()) {
			if (operatorName.startsWith(op)) {
				return true;
			}
		}
		return false;
	}
}
