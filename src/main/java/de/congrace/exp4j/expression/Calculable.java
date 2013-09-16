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
package de.congrace.exp4j.expression;

import java.util.Collection;

import gnu.trove.map.TObjectDoubleMap;

/**
 * This is the basic result class of the exp4j {@link ExpressionBuilder}.
 *
 * @author frank asseg
 */
public interface Calculable {
	/**
	 * Calculate the result of the expression.
	 *
	 * @return the result of the calculation.
	 */
	public double calculate();

	/**
	 * Set a variable value for the calculation.
	 *
	 * @param name the variable name.
	 * @param value the value of the variable.
	 */
	public void setVariable(String name, double value);

	/**
	 * Set the variable values for the calculation.
	 *
	 * @param variables the variable name-value map.
	 */
	public void setVariables(TObjectDoubleMap<String> variables);

	/**
	 * Return the name of the variables that need to be set for proper calculation.
	 *
	 * @return The variable names.
	 */
	public Collection<String> getVariableNames();
}
