package de.congrace.exp4j.operator;

public class DivideOperator extends Operator {
	public DivideOperator() {
		super("/", 3);
	}

	@Override
	public double applyOperation(double[] values) {
		if (values[1] == 0) {
			throw new ArithmeticException("Division by zero!");
		}
		return values[0] / values[1];
	}
}
