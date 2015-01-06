package it.uniroma2.sag.kelp.utils.evaluation;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.predictionfunction.Prediction;
import it.uniroma2.sag.kelp.predictionfunction.classifier.ClassificationOutput;
/**
 * This is the simplest instance of an Evaluator. 
 * It serves as an example on how to use the evaluators. 
 * 
 * It allows to compute the Accuracy.
 * 
 * @author Giuseppe Castellucci
 */
public class AccuracyEvaluator extends Evaluator {
	private int correct;
	private int total;
	
	private float accuracy;
	
	/**
	 * Initialize a new AccuracyEvaluator that will work on the specified classes
	 * @param labels
	 */
	public AccuracyEvaluator() {
		correct=0;
		total=0;
		accuracy=0.0f;
	}

	public void addCount(Example test, Prediction prediction) {
		ClassificationOutput tmp = (ClassificationOutput)prediction;
		Label predicted = tmp.getPredictedClasses().get(0);
		total++;
		if (test.isExampleOf(predicted))
			correct++;
	}

	public void compute() {
		if (total!=0) {
			accuracy = (float)correct/(float)total;
		}
	}

	/**
	 * Return the accuracy
	 * 
	 * @return mean F1 of all Label{s}
	 */
	public float getAccuracy() {
		return accuracy;
	}

	/**
	 * Clear all the counters for a new processing.
	 */
	@Override
	public void clear() {
		correct=0;
		total=0;
		accuracy=0.0f;
	}
	
	/**
	 * Print the counters in a human-readable format
	 */
	@SuppressWarnings("unused")
	private void printCounters() {
		System.out.println("Total: " + total+"\n");
		System.out.println("Correct: " + correct+"\n");
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Accuracy: " + accuracy+"\n");
		return b.toString().trim();
	}
}
