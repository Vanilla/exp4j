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
package de.congrace.exp4j.token;

import java.util.Stack;

import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.function.Function;
import de.congrace.exp4j.util.ArrayUtil;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.stack.TDoubleStack;

public class FunctionToken extends CalculationToken {
	private final String functionName;
	private final Function function;

	public FunctionToken(String value, Function function) throws UnknownFunctionException {
		super(value);
		if (value == null) {
			throw new UnknownFunctionException(value);
		}
		this.functionName = function.getName();
		this.function = function;
	}

	public String getName() {
		return functionName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionToken) {
			return functionName.equals(((FunctionToken) obj).functionName);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return functionName.hashCode();
	}

	@Override
	public void mutateStackForCalculation(TDoubleStack stack, TObjectDoubleMap<String> variableValues) {
		double[] args = new double[function.getArgumentCount()];
		for (int i = 0; i < function.getArgumentCount(); i++) {
			args[i] = stack.pop();
		}
		stack.push(this.function.applyFunction(ArrayUtil.reverse(args)));
	}

	@Override
	public void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		operatorStack.push(this);
	}
}
