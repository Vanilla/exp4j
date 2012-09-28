package de.congrace.exp4j.function;

import de.congrace.exp4j.math.TrigMathHelper;

public class CosineFunction extends Function {
	public CosineFunction() {
		super("cos");
	}

	@Override
	public double applyFunction(double... args) {
		return TrigMathHelper.cos((float) args[0]);
	}
}
