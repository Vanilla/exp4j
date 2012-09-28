package de.congrace.exp4j.math;

import de.congrace.exp4j.math.MathHelper.BitSize;

/**
 * A class designed for Sinus operations using a table lookup system
 */
public class TrigMathHelper {
	private static final BitSize SIN_SCALE = new BitSize(16); // used to compute the size and mask to use for sin
	private static final float[] SIN_TABLE = new float[SIN_SCALE.SIZE];
	private static float SIN_CONVERSION_FACTOR = (float) (SIN_SCALE.SIZE / MathHelper.TWO_PI);
	private static final int COS_OFFSET = SIN_SCALE.SIZE / 4;

	static {
		for (int i = 0; i < SIN_SCALE.SIZE; i++) {
			SIN_TABLE[i] = (float) Math.sin((i * MathHelper.TWO_PI) / SIN_SCALE.SIZE);
		}
	}

	private static float sinRaw(int idx) {
		return SIN_TABLE[idx & SIN_SCALE.MASK];
	}

	private static float cosRaw(int idx) {
		return SIN_TABLE[(idx + COS_OFFSET) & SIN_SCALE.MASK];
	}

	/**
	 * Tangent calculations using a table.<br> <i>sin(angle) / cos(angle)</i><br><br>
	 *
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place
	 *
	 * @param angle in radians
	 * @return the tangent of the angle
	 */
	public static float tan(float angle) {
		int idx = MathHelper.floor(angle * SIN_CONVERSION_FACTOR);
		return sinRaw(idx) / cosRaw(idx);
	}

	/**
	 * Sinus calculation using a table.<br> For double-precision sin values, use the MathHelper sin
	 * function<br><br>
	 *
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place
	 *
	 * @param angle the angle in radians
	 * @return the sinus of the angle
	 */
	public static float sin(float angle) {
		return sinRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}

	/**
	 * Cosinus calculation using a table.<br> For double-precision cos values, use the MathHelper
	 * cos function<br><br>
	 *
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place
	 *
	 * @param angle the angle in radians
	 * @return the cosinus of the angle
	 */
	public static float cos(float angle) {
		return cosRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}
}
