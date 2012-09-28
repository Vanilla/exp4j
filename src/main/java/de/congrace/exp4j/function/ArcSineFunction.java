package de.congrace.exp4j.function;

import de.congrace.exp4j.math.ArcTrigMathHelper;

public class ArcSineFunction extends Function {
	public ArcSineFunction() {
		super("asin");
	}

	@Override
	public double applyFunction(double... args) {
		return ArcTrigMathHelper.asin(args[0]);
	}
}
