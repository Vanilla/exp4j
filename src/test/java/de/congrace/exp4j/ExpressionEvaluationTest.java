package de.congrace.exp4j;

import org.junit.Test;
import org.junit.Assert;

import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

import de.congrace.exp4j.exception.UnknownFunctionException;
import de.congrace.exp4j.exception.UnparsableExpressionException;
import de.congrace.exp4j.expression.Calculable;
import de.congrace.exp4j.expression.ExpressionBuilder;

public class ExpressionEvaluationTest {
	private static final String EXPRESSION_1 = "-(sin(x * PI) + y * 3 / z) + 2^a + 6 % b";
	private static final double VALUE_1 = 3;
	private static final String EXPRESSION_2 = "14e5 * 3e-3";
	private static final double VALUE_2 = 14e5 * 3e-3;
	private static final TObjectDoubleMap<String> VARIABLES = new TObjectDoubleHashMap<String>();

	static {
		VARIABLES.put("x", 0.5);
		VARIABLES.put("y", 4);
		VARIABLES.put("z", 2);
		VARIABLES.put("a", 3);
		VARIABLES.put("b", 4);
	}

	@Test
	public void test() throws UnparsableExpressionException, UnknownFunctionException {
		final double test1 = new ExpressionBuilder(EXPRESSION_1).withVariables(VARIABLES).build().calculate();
		Assert.assertEquals(VALUE_1, test1, 0);
		final Calculable calculable = new ExpressionBuilder(EXPRESSION_1).withVariableNames(VARIABLES.keySet()).build();
		calculable.setVariables(VARIABLES);
		final double test2 = calculable.calculate();
		Assert.assertEquals(VALUE_1, test2, 0);
		final double test3 = new ExpressionBuilder(EXPRESSION_2).build().calculate();
		Assert.assertEquals(VALUE_2, test3, 0);
	}
}
