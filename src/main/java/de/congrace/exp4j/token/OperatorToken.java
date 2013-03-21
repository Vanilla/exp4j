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
package de.congrace.exp4j.token;

import java.util.Stack;

import gnu.trove.stack.TDoubleStack;
import gnu.trove.map.TObjectDoubleMap;

import de.congrace.exp4j.operator.Operator;

/**
 * {@link Token} for Operations like +,-,*,/,% and ^
 *
 * @author fas@congrace.de
 */
public class OperatorToken extends CalculationToken {
	private Operator operation;

	/**
	 * construct a new {@link OperatorToken}
	 *
	 * @param value the symbol (e.g.: '+')
	 * @param operation the {@link Operator} of this {@link Token}
	 */
	public OperatorToken(String value, Operator operation) {
		super(value);
		this.operation = operation;
	}

	/**
	 * apply the {@link Operator}
	 *
	 * @param values the doubles to operate on
	 * @return the result of the {@link Operator}
	 */
	public double applyOperation(double... values) {
		return operation.applyOperation(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OperatorToken) {
			final OperatorToken t = (OperatorToken) obj;
			return t.getValue().equals(this.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public void mutateStackForCalculation(TDoubleStack stack, TObjectDoubleMap<String> variableValues) {
		final double[] operands = new double[operation.getOperandCount()];
		for (int i = 0; i < operation.getOperandCount(); i++) {
			operands[operation.getOperandCount() - i - 1] = stack.pop();
		}
		stack.push(operation.applyOperation(operands));
	}

	@Override
	public void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		Token before;
		while (!operatorStack.isEmpty() && (before = operatorStack.peek()) != null
				&& (before instanceof OperatorToken || before instanceof FunctionToken)) {
			if (before instanceof FunctionToken) {
				operatorStack.pop();
				output.append(before.getValue()).append(" ");
			} else {
				final OperatorToken stackOperator = (OperatorToken) before;
				if (this.isLeftAssociative() && this.getPrecedence() <= stackOperator.getPrecedence()) {
					output.append(operatorStack.pop().getValue()).append(" ");
				} else if (!this.isLeftAssociative() && this.getPrecedence() < stackOperator.getPrecedence()) {
					output.append(operatorStack.pop().getValue()).append(" ");
				} else {
					break;
				}
			}
		}
		operatorStack.push(this);
	}

	private boolean isLeftAssociative() {
		return operation.isLeftAssociative();
	}

	private int getPrecedence() {
		return operation.getPrecedence();
	}
}