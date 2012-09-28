package de.congrace.exp4j.operator;

public class SubstractOperator extends Operator {
	public SubstractOperator() {
		super("-");
	}

	@Override
	public double applyOperation(double[] values) {
		return values[0] - values[1];
	}
}
