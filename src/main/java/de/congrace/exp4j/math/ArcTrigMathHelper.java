package de.congrace.exp4j.math;

import java.lang.Math;

/**
 * Trigonometry helper specicialized on accurate arc sin/cos/tan calculations<br><br>
 *
 * Source: http://www.developer.nokia.com/Community/Discussion/showthread.php?72840
 */
public class ArcTrigMathHelper {
	private static final double sq2p1 = 2.414213562373095048802e0;
	private static final double sq2m1 = .414213562373095048802e0;
	private static final double p4 = .161536412982230228262e2;
	private static final double p3 = .26842548195503973794141e3;
	private static final double p2 = .11530293515404850115428136e4;
	private static final double p1 = .178040631643319697105464587e4;
	private static final double p0 = .89678597403663861959987488e3;
	private static final double q4 = .5895697050844462222791e2;
	private static final double q3 = .536265374031215315104235e3;
	private static final double q2 = .16667838148816337184521798e4;
	private static final double q1 = .207933497444540981287275926e4;
	private static final double q0 = .89678597403663861962481162e3;

	private static double mxatan(double arg) {
		final double argsq = arg * arg;
		double value = ((((p4 * argsq + p3) * argsq + p2) * argsq + p1) * argsq + p0);
		value /= ((((argsq + q4) * argsq + q3) * argsq + q2) * argsq + q1) * argsq + q0;
		return value * arg;
	}

	private static double msatan(double arg) {
		if (arg < sq2m1) {
			return mxatan(arg);
		}
		if (arg > sq2p1) {
			return MathHelper.HALF_PI - mxatan(1 / arg);
		}
		return MathHelper.HALF_PI / 2 + mxatan((arg - 1) / (arg + 1));
	}

	/**
	 * Calculates the arc tangent of the value specified
	 *
	 * @param value of the tangent
	 * @return tangent arc in radians
	 */
	public static double atan(double value) {
		if (value > 0) {
			return msatan(value);
		} else {
			return -msatan(-value);
		}
	}

	/**
	 * Calculates the arc sinus of the value specified<br><br> Returns NaN if the input value is
	 * outside the sinus range
	 *
	 * @param value of the sinus
	 * @return sinus arc in radians
	 */
	public static double asin(double value) {
		if (value > 1) {
			return Double.NaN;
		} else if (value < 0) {
			return -asin(-value);
		} else {
			double temp = Math.sqrt(1 - value * value);
			if (value > 0.7) {
				return MathHelper.HALF_PI - msatan(temp / value);
			} else {
				return msatan(value / temp);
			}
		}
	}

	/**
	 * Calculates the arc cosinus of the value specified<br><br> Returns NaN if the input value is
	 * outside the cosinus range
	 *
	 * @param value of the cosinus
	 * @return cosinus arc in radians
	 */
	public static double acos(double value) {
		if (value > 1 || value < -1) {
			return Double.NaN;
		} else {
			return MathHelper.HALF_PI - asin(value);
		}
	}
}
