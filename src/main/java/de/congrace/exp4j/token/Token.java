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

public abstract class Token {
	private final String value;

	/**
	 * construct a new {@link Token}
	 *
	 * @param value the value of the {@link Token}
	 */
	public Token(String value) {
		this.value = value;
	}

	/**
	 * get the value (String representation) of the token
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public abstract void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output);
}
