package de.congrace.exp4j.function;

import de.congrace.exp4j.math.TrigMathHelper;

public class SineFunction extends Function {
	public SineFunction() {
		super("sin");
	}

	@Override
	public double applyFunction(double... args) {
		return TrigMathHelper.sin((float) args[0]);
	}
}
