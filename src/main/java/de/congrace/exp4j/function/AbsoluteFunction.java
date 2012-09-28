package de.congrace.exp4j.function;

public class AbsoluteFunction extends Function {
	public AbsoluteFunction() {
		super("abs");
	}

	@Override
	public double applyFunction(double... args) {
		return Math.abs(args[0]);
	}
}
