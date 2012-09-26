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
package de.congrace.exp4j.expression;

/**
 * This is the basic result class of the exp4j {@link ExpressionBuilder}
 *
 * @author frank asseg
 *
 */
public interface Calculable {
	/**
	 * calculate the result of the expression
	 *
	 * @return the result of the calculation
	 */
	public double calculate();

	/**
	 * calculate the result of the expression
	 *
	 * @param variableValues the values of the variable. The values must be in the same order as the
	 * declaration of variables in the {@link ExpressionBuilder} used to construct this {@link Calculable}
	 * instance
	 * @return the result of the calculation
	 */
	public double calculate(double... variableValues);

	/**
	 * return the expression in reverse polish postfix notation
	 *
	 * @return the expression used to construct this {@link Calculable}
	 */
	public String getExpression();

	/**
	 * set a variable value for the calculation
	 *
	 * @param name the variable name
	 * @param value the value of the variable
	 */
	public void setVariable(String name, double value);
}
