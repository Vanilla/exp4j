package de.congrace.exp4j.function;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Functions {
	public static final AbsoluteFunction ABSOLUTE = new AbsoluteFunction();
	public static final CeilingFunction CEILING = new CeilingFunction();
	public static final FloorFunction FLOOR = new FloorFunction();
	public static final ArcSineFunction ARCSINE = new ArcSineFunction();
	public static final ArcCosineFunction ARCCOSINE = new ArcCosineFunction();
	public static final ArcTangentFunction ARCTANGENT = new ArcTangentFunction();
	public static final SineFunction SINE = new SineFunction();
	public static final CosineFunction COSINE = new CosineFunction();
	public static final TangentFunction TANGENT = new TangentFunction();
	public static final SquareRootFunction SQUARE_ROOT = new SquareRootFunction();
	public static final FastSquareRootFunction FAST_SQUARE_ROOT = new FastSquareRootFunction();
	public static final LogFunction LOG = new LogFunction();
	private static final Map<String, Function> FUNCTIONS = new HashMap<String, Function>();

	static {
		for (Field objectField : Functions.class.getDeclaredFields()) {
			objectField.setAccessible(true);
			try {
				final Object object = objectField.get(null);
				if (object instanceof Function) {
					register((Function) object);
				}
			} catch (Exception ex) {
				System.out.println("Could not properly reflect Functions!");
				ex.printStackTrace();
			}
		}
	}

	public static void register(Function function) {
		FUNCTIONS.put(function.getName(), function);
	}

	public static Function getFunction(String name) {
		return FUNCTIONS.get(name);
	}

	public static Map<String, Function> getFunctionMap() {
		return FUNCTIONS;
	}
}
