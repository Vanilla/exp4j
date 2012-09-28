package de.congrace.exp4j.function;

public class CeilingFunction extends Function {
	public CeilingFunction() {
		super("ceil");
	}

	@Override
	public double applyFunction(double... args) {
		return Math.ceil(args[0]);
	}
}
