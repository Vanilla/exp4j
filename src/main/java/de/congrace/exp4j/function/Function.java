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

/**
 * this classed is used to create custom functions for exp4j<br/> <br/> <b>Example</b><br/>
 * <code><pre>{@code} Function fooFunc = new Function("foo") { public double applyFunction(double
 * value) { return value*Math.E; } }; double varX=12d; Calculable calc = new
 * ExpressionBuilder("foo(x)").withCustomFunction(fooFunc).withVariable("x",varX).build();
 * assertTrue(calc.calculate() == Math.E * varX); }</pre></code>
 *
 * @author frank asseg
 *
 */
public abstract class Function {
	private final int argumentCount;
	private final String name;

	/**
	 * create a new single value input Function with a set name
	 *
	 * @param value the name of the function (e.g. foo)
	 * @param argumentCount the minimum number of arguments this function needs
	 */
	protected Function(String name, int argumentCount) {
		this.argumentCount = argumentCount;
		this.name = name;
		if (name.isEmpty() || !Character.isLetter(name.charAt(0))) {
			throw new IllegalArgumentException("Invalid function name: " + name);
		}
	}

	/**
	 * create a new single value input Function with a set name
	 *
	 * @param value the name of the function (e.g. foo)
	 */
	protected Function(String name) {
		this(name, 1);
	}

	public abstract double applyFunction(double... args);

	public String getName() {
		return name;
	}

	public int getArgumentCount() {
		return argumentCount;
	}
}
