package de.congrace.exp4j.function;

import de.congrace.exp4j.math.ArcTrigMathHelper;

public class ArcCosineFunction extends Function {
	public ArcCosineFunction() {
		super("acos");
	}

	@Override
	public double applyFunction(double... args) {
		return ArcTrigMathHelper.acos(args[0]);
	}
}
