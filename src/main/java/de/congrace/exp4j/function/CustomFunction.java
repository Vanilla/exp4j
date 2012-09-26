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
package de.congrace.exp4j.function;

import de.congrace.exp4j.exception.InvalidCustomFunctionException;

/**
 * this classed is used to create custom functions for exp4j<br/> <br/> <b>Example</b><br/>
 * <code><pre>{@code} CustomFunction fooFunc = new CustomFunction("foo") { public double
 * applyFunction(double value) { return value*Math.E; } }; double varX=12d; Calculable calc = new
 * ExpressionBuilder("foo(x)").withCustomFunction(fooFunc).withVariable("x",varX).build();
 * assertTrue(calc.calculate() == Math.E * varX); }</pre></code>
 *
 * @author frank asseg
 *
 */
public abstract class CustomFunction {
	private final int argumentCount;
	private final String name;

	/**
	 * create a new single value input CustomFunction with a set name
	 *
	 * @param value the name of the function (e.g. foo)
	 */
	protected CustomFunction(String name) throws InvalidCustomFunctionException {
		this.argumentCount = 1;
		this.name = name;
		int firstChar = (int) name.charAt(0);
		if ((firstChar < 65 || firstChar > 90) && (firstChar < 97 || firstChar > 122)) {
			throw new InvalidCustomFunctionException("functions have to start with a lowercase or uppercase character");
		}
	}

	/**
	 * create a new single value input CustomFunction with a set name
	 *
	 * @param value the name of the function (e.g. foo)
	 * @param argumentCount the minimum number of arguments this function needs
	 */
	protected CustomFunction(String name, int argumentCount) throws InvalidCustomFunctionException {
		this.argumentCount = argumentCount;
		this.name = name;
	}

	public abstract double applyFunction(double... args);

	public String getName() {
		return name;
	}

	public int getArgumentCount() {
		return argumentCount;
	}
}