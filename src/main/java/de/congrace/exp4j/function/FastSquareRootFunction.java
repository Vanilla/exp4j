package de.congrace.exp4j.function;

import de.congrace.exp4j.math.MathHelper;

public class FastSquareRootFunction extends Function {
	public FastSquareRootFunction() {
		super("fsqrt");
	}

	@Override
	public double applyFunction(double... args) {
		return MathHelper.sqrt(args[0]);
	}
}
