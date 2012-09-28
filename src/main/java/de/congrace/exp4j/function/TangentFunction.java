package de.congrace.exp4j.function;

import de.congrace.exp4j.math.TrigMathHelper;

public class TangentFunction extends Function {
	public TangentFunction() {
		super("tan");
	}

	@Override
	public double applyFunction(double... args) {
		return TrigMathHelper.tan((float) args[0]);
	}
}
