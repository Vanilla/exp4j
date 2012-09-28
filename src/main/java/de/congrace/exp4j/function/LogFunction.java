package de.congrace.exp4j.function;

public class LogFunction extends Function {
	public LogFunction() {
		super("log");
	}

	@Override
	public double applyFunction(double... args) {
		return Math.log(args[0]);
	}
}
