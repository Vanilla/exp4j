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
package de.congrace.exp4j.operator;

/**
 * This class is used to create custom operators for use in expressions<br/> The
 * applyOperation(double[] values) will have to be implemented by users of this class. <br/>
 * <b>Example</b><br/>
 * <code><pre>{@code} CustomOperator greaterEq = new CustomOperator(">=", true, 4, 2) { double
 * applyOperation(double[] values) { if (values[0] >= values[1]){ return 1d; }else{ return 0d; } }
 * }; Calculable calc = new ExpressionBuilder("1>=2").withOperation(greaterEq).build();
 * assertTrue(0d == calc.calculate());
 * </pre></code> When constructing {@link CustomOperator} special attention has to be given to the
 * precedence of the operation. see http://en.wikipedia.org/wiki/Order_of_operations. The
 * precendence values for the builtin operators are as follows: <br/> Addition and Subtraction (+,-)
 * have precedence 1<br/> Division Multiplication, and Modulo (/,*,%) have precedence 3<br/>
 * Exponentiation (^) has precendence 5 <br/> Unary minus and plus (+1,-1) have precedence 7
 *
 * @author frank asseg
 *
 */
public abstract class CustomOperator {
	private final boolean leftAssociative;
	private final String symbol;
	private final int precedence;
	private final int operandCount;

	/**
	 * Create a new {@link CustomOperator} for two operands
	 *
	 * @param symbol the symbol to be used in expressions to identify this operation
	 * @param leftAssociative true is the operation is left associative
	 * @param precedence the precedence of the operation
	 */
	protected CustomOperator(final String symbol, final boolean leftAssociative, final int precedence) {
		this.leftAssociative = leftAssociative;
		this.symbol = symbol;
		this.precedence = precedence;
		this.operandCount = 2;
	}

	/**
	 * Create a new {@link CustomOperator}
	 *
	 * @param symbol the symbol to be used in expressions to identify this operation
	 * @param leftAssociative true is the operation is left associative
	 * @param precedence the precedence of the operation
	 * @param operandCount the number of operands of the operation. A value of 1 means the operation
	 * takes one operand. Any other value means the operation takes 2 arguments.
	 */
	protected CustomOperator(final String symbol, final boolean leftAssociative, final int precedence,
			final int operandCount) {
		this.leftAssociative = leftAssociative;
		this.symbol = symbol;
		this.precedence = precedence;
		this.operandCount = operandCount == 1 ? 1 : 2;
	}

	/**
	 * Create a left associative {@link CustomOperator} with precedence value of 1 that uses two
	 * operands
	 *
	 * @param symbol the {@link String} to use a symbol for this operation
	 */
	protected CustomOperator(final String symbol) {
		this.leftAssociative = true;
		this.symbol = symbol;
		this.precedence = 1;
		this.operandCount = 2;
	}

	/**
	 * Create a left associative {@link CustomOperator} for two operands
	 *
	 * @param symbol the {@link String} to use a symbol for this operation
	 * @param precedence the precedence of the operation
	 */
	protected CustomOperator(final String symbol, final int precedence) {
		this.leftAssociative = true;
		this.symbol = symbol;
		this.precedence = precedence;
		this.operandCount = 2;
	}

	/**
	 * Apply the custom operation on the two operands and return the result as an double An example
	 * implementation for a multiplication could look like this:
	 *
	 * <pre>
	 * <code>{@code} double applyOperation(double[] values) { return values[0]*values[1]; }
	 * </pre>
	 *
	 * </code>
	 *
	 * @param values the operands for the operation. If the {@link CustomOperator} uses only one
	 * operand such as a factorial the operation has to be applied to the first element of the
	 * values array. If the
	 *            {@link CustomOperator} uses two operands the operation has to be applied to the first two
	 * items in the values array, with special care given to the operator associativity. The operand
	 * to the left of the symbol is the first element in the array while the operand to the right is
	 * the second element of the array.
	 * @return the result of the operation
	 */
	public abstract double applyOperation(double[] values);

	public String getSymbol() {
		return symbol;
	}

	public int getOperandCount() {
		return operandCount;
	}

	public boolean isLeftAssociative() {
		return leftAssociative;
	}

	public int getPrecedence() {
		return precedence;
	}
}
