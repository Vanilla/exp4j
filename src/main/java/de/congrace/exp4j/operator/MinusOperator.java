package de.congrace.exp4j.operator;

public class MinusOperator extends Operator {
	public MinusOperator() {
		super("'", false, 7, 1);
	}

	@Override
	public double applyOperation(double[] values) {
		return -values[0];
	}
}
