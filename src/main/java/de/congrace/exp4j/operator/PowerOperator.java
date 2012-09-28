package de.congrace.exp4j.operator;

public class PowerOperator extends Operator {
	public PowerOperator() {
		super("^", false, 5, 2);
	}

	@Override
	public double applyOperation(double[] values) {
		return Math.pow(values[0], values[1]);
	}
}
