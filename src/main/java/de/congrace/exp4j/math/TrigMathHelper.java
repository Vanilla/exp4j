/*
 * This file is part of exp4j.
 *
 * Copyright (c) 2011 Frank Asseg
 * Modifications (c) 2012 Spout LLC <http://www.spout.org/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.congrace.exp4j.math;

import de.congrace.exp4j.math.MathHelper.BitSize;

/**
 * A class designed for Sinus operations using a table lookup system.
 */
public class TrigMathHelper {
	private static final BitSize SIN_SCALE = new BitSize(16); // Used to compute the size and mask to use for sin.
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
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place.
	 *
	 * @param angle in radians.
	 * @return the tangent of the angle.
	 */
	public static float tan(float angle) {
		int idx = MathHelper.floor(angle * SIN_CONVERSION_FACTOR);
		return sinRaw(idx) / cosRaw(idx);
	}

	/**
	 * Sinus calculation using a table.<br> For double-precision sin values, use the MathHelper sin function.<br><br>
	 *
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place.
	 *
	 * @param angle the angle in radians.
	 * @return the sinus of the angle.
	 */
	public static float sin(float angle) {
		return sinRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}

	/**
	 * Cosinus calculation using a table.<br> For double-precision cos values, use the MathHelper cos function.<br><br>
	 *
	 * <b>No interpolation is performed:</b> Accuracy is up to the 5th decimal place.
	 *
	 * @param angle the angle in radians.
	 * @return the cosinus of the angle.
	 */
	public static float cos(float angle) {
		return cosRaw(MathHelper.floor(angle * SIN_CONVERSION_FACTOR));
	}
}
