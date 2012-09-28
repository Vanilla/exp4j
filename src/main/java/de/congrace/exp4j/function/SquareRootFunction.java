package de.congrace.exp4j.function;

public class SquareRootFunction extends Function {
	public SquareRootFunction() {
		super("sqrt");
	}

	@Override
	public double applyFunction(double... args) {
		return Math.sqrt(args[0]);
	}
}
