package de.congrace.exp4j.operator;

public class AddOperator extends Operator {
	public AddOperator() {
		super("+");
	}

	@Override
	public double applyOperation(double[] values) {
		return values[0] + values[1];
	}
}
