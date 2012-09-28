package de.congrace.exp4j.function;

public class FloorFunction extends Function {
	public FloorFunction() {
		super("floor");
	}

	@Override
	public double applyFunction(double... args) {
		return Math.floor(args[0]);
	}
}
