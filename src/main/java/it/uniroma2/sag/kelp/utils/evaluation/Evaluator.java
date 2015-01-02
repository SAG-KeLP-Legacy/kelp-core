package it.uniroma2.sag.kelp.utils.evaluation;

import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.utils.exception.NoSuchPerformanceMeasureException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Evaluator {
	public float getPerformanceMeasure(String performanceMeasureMethodName, Object[] args) throws NoSuchPerformanceMeasureException {
		this.compute();
		@SuppressWarnings("rawtypes")
		Class[] methodParameters=null;
		if (args != null) {
			methodParameters = new Class[args.length];
			for (int i=0; i<args.length; ++i) {
				methodParameters[i] = args[i].getClass();
			}
		}
		try {
			Method method = this.getClass().getMethod(performanceMeasureMethodName, methodParameters);
			Object invokedResult = method.invoke(this, args);
			Float res = (Float)invokedResult;
			return res.floatValue();
		} catch (SecurityException e) {
			throw new NoSuchPerformanceMeasureException("Evaluator can't find the specified performance measure: " + performanceMeasureMethodName);
		} catch (NoSuchMethodException e) {
			throw new NoSuchPerformanceMeasureException("Evaluator can't find the specified performance measure: " + performanceMeasureMethodName);
		} catch (IllegalArgumentException e) {
			throw new NoSuchPerformanceMeasureException("Evaluator can't call the specified performance measure: " + performanceMeasureMethodName + " with the specified arguments");
		} catch (IllegalAccessException e) {
			throw new NoSuchPerformanceMeasureException("Evaluator can't call the specified performance measure: " + performanceMeasureMethodName + " with the specified arguments");
		} catch (InvocationTargetException e) {
			throw new NoSuchPerformanceMeasureException("Evaluator can't call the specified performance measure: " + performanceMeasureMethodName + " with the specified arguments");
		}
	}
	
	public abstract void addCount(Label gold, Label predicted);
	public abstract void compute();
}
