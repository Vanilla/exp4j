package de.congrace.exp4j.function;

import de.congrace.exp4j.math.ArcTrigMathHelper;

public class ArcTangentFunction extends Function {
	public ArcTangentFunction() {
		super("atan");
	}

	@Override
	public double applyFunction(double... args) {
		return ArcTrigMathHelper.atan(args[0]);
	}
}
