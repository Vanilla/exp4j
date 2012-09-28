package de.congrace.exp4j.operator;

public class MultiplyOperator extends Operator {
	public MultiplyOperator() {
		super("*", 3);
	}

	@Override
	public double applyOperation(double[] values) {
		return values[0] * values[1];
	}
}
