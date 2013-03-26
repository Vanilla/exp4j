/*
 Copyright 2011 frank asseg

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
package de.congrace.exp4j.util;

import java.util.Locale;

import de.congrace.exp4j.exception.UnparsableExpressionException;

public abstract class ExpressionUtil {
	/**
	 * normalize a number to an acceptable format for exp4j e.g. normalizing "314e-2" yields "3.14"
	 *
	 * @param number The number to normalize
	 * @param loc The local to use for normalization
	 */
	public static String normalizeNumber(String number, Locale loc) throws UnparsableExpressionException {
		return number.replaceAll("e|E", "*10^");
	}

	/**
	 * normalize a number to an acceptable format for exp4j e.g. normalizing "314e-2" yields "3.14"
	 *
	 * @param number The number to normalize
	 */
	public static String normalizeNumber(String number) throws UnparsableExpressionException {
		return normalizeNumber(number, Locale.getDefault());
	}
}
