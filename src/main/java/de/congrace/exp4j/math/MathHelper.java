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

public class MathHelper {
	public static final double HALF_PI = 0.5 * Math.PI;
	public static final double TWO_PI = 2 * Math.PI;

	public static int floor(double x) {
		int y = (int) x;
		if (x < y) {
			return y - 1;
		}
		return y;
	}

	private static double inverseSqrt(double x) {
		final double xhalves = 0.5d * x;
		x = Double.longBitsToDouble(0x5FE6EB50C7B537AAl - (Double.doubleToRawLongBits(x) >> 1));
		return x * (1.5d - xhalves * x * x);
	}

	public static double sqrt(final double x) {
		return x * inverseSqrt(x);
	}

	/**
	 * Stores the size that spans a fixed amount of bits<br> For example: 1, 2, 4, 8, 16 and 32 sizes match this description.
	 */
	public static class BitSize {
		public final int SIZE;
		public final int HALF_SIZE;
		public final int DOUBLE_SIZE;
		public final int MASK;
		public final int BITS;
		public final int DOUBLE_BITS;
		public final int AREA;
		public final int HALF_AREA;
		public final int DOUBLE_AREA;
		public final int VOLUME;
		public final int HALF_VOLUME;
		public final int DOUBLE_VOLUME;

		public BitSize(int bitCount) {
			this.BITS = bitCount;
			this.SIZE = 1 << bitCount;
			this.AREA = this.SIZE * this.SIZE;
			this.VOLUME = this.AREA * this.SIZE;
			this.HALF_SIZE = this.SIZE >> 1;
			this.HALF_AREA = this.AREA >> 1;
			this.HALF_VOLUME = this.VOLUME >> 1;
			this.DOUBLE_SIZE = this.SIZE << 1;
			this.DOUBLE_AREA = this.AREA << 1;
			this.DOUBLE_VOLUME = this.VOLUME << 1;
			this.DOUBLE_BITS = this.BITS << 1;
			this.MASK = this.SIZE - 1;
		}
	}
}
