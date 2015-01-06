package it.uniroma2.sag.kelp.utils.evaluation;

import gnu.trove.map.hash.TObjectFloatHashMap;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.predictionfunction.Prediction;
import it.uniroma2.sag.kelp.predictionfunction.classifier.ClassificationOutput;

import java.util.ArrayList;
/**
 * This is an instance of an Evaluator. 
 * It allows to compute the some common measure for classification tasks. 
 * It computes precision, recall, f1s for each class, and a global accuracy.
 * 
 * @author Giuseppe Castellucci
 */
public class ClassificationEvaluator extends Evaluator {
	private ArrayList<Label> labels;
	private TObjectFloatHashMap<Label> correctCounter = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> predictedCounter = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> toBePredictedCounter = new TObjectFloatHashMap<Label>();

	private TObjectFloatHashMap<Label> precisions = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> recalls = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> f1s = new TObjectFloatHashMap<Label>();
	
	private int total,correct;
	private float accuracy;

	/**
	 * Initialize a new F1Evaluator that will work on the specified classes
	 * @param labels
	 */
	public ClassificationEvaluator(ArrayList<Label> labels) {
		this.labels = labels;
		initializeCounters();
	}

	private void initializeCounters() {
		for (Label l : labels) {
			correctCounter.put(l, 0.0f);
			toBePredictedCounter.put(l, 0.0f);
			predictedCounter.put(l, 0.0f);
			precisions.put(l, -1.0f);
			recalls.put(l, -1.0f);
			f1s.put(l, -1.0f);
		}
		total=0;
		correct=0;
		accuracy=0.0f;
	}

	/**
	 * Return the precision map
	 * 
	 * @return
	 */
	public TObjectFloatHashMap<Label> getPrecisions() {
		return precisions;
	}

	/**
	 * Return the recall map
	 * 
	 * @return
	 */
	public TObjectFloatHashMap<Label> getRecalls() {
		return recalls;
	}

	/**
	 * Return the F1 map
	 * 
	 * @return
	 */
	public TObjectFloatHashMap<Label> getF1s() {
		return f1s;
	}

	public void addCount(Example test, Prediction prediction) {
		ClassificationOutput tmp = (ClassificationOutput)prediction;
		Label predicted = tmp.getPredictedClasses().get(0);
		Label gold = test.getLabels()[0];
		toBePredictedCounter.put(gold, toBePredictedCounter.get(gold) + 1);
		predictedCounter.put(predicted, predictedCounter.get(predicted) + 1);
		
		if (test.isExampleOf(predicted)) {
			correctCounter.put(gold, correctCounter.get(gold) + 1);
			correct++;
		}
		
		total++;
	}

	public void compute() {
		for (Label l : labels) {
			float precision = 0.0f;
			float recall = 0.0f;
			float f1 = 0.0f;
			if (correctCounter.get(l) != 0 && predictedCounter.get(l) != 0 && toBePredictedCounter.get(l) != 0) {
				precision = correctCounter.get(l) / predictedCounter.get(l);
				recall = correctCounter.get(l) / toBePredictedCounter.get(l);
				f1 = (2 * precision * recall) / (precision + recall);
			}
			
			precisions.put(l, precision);
			recalls.put(l, recall);
			f1s.put(l, f1);
		}
		
		accuracy = (float)correct/(float)total;
	}

	/**
	 * Return the precision for the specified label
	 * 
	 * @return precision of Label l
	 */
	public float getPrecisionFor(Label l) {
		if (precisions.containsKey(l))
			return precisions.get(l);
		return -1.0f;
	}

	/**
	 * Return the recall for the specified label
	 * 
	 * @return recall of Label l
	 */
	public float getRecallFor(Label l) {
		if (recalls.containsKey(l))
			return recalls.get(l);
		return -1.0f;
	}

	/**
	 * Return the f1 for the specified label
	 * 
	 * @return f1 of Label l
	 */
	public float getF1For(Label l) {
		if (f1s.containsKey(l))
			return f1s.get(l);
		return -1.0f;
	}
	
	/**
	 * Return the accuracy
	 * 
	 * @return accuracy
	 */
	public float getAccuracy() {
		return accuracy;
	}

	/**
	 * Return the mean of the F1 scores considering all the labels involved
	 * 
	 * @return mean F1 of all Label{s}
	 */
	public float getMeanF1() {
		return getMeanF1For(labels);
	}

	/**
	 * Return the mean of the F1 scores considering the specified labels
	 * 
	 * @return mean F1 of specified Label{s} ls
	 */
	public float getMeanF1For(ArrayList<Label> ls) {
		float sum = 0.0f;
		for (Label l : ls) {
			sum += f1s.get(l);
		}
		float mean = sum / (float) ls.size();
		return mean;
	}

	/**
	 * Clear all the counters for a new processing.
	 */
	@Override
	public void clear() {
		correctCounter.clear();
		predictedCounter.clear();
		toBePredictedCounter.clear();
		precisions.clear();
		recalls.clear();
		f1s.clear();
		accuracy = 0.0f;
	}
	
	/**
	 * Print the counters in a human-readable format
	 */
	@SuppressWarnings("unused")
	private void printCounters() {
		for (Label l : labels) {
			System.out.println(l);
			System.out.print("\t");
			printCounters(l);
		}
		
	}
	
	/**
	 * Print the counters of the specified Label l.
	 * 
	 * @param l
	 */
	public void printCounters(Label l) {
		System.out.println(correctCounter.get(l) + " " + predictedCounter.get(l) + " " + toBePredictedCounter.get(l));
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Label l : labels) {
			b.append(l + "\t" + precisions.get(l) + "\t" + recalls.get(l)
					+ "\t" + f1s.get(l) + "\n");
		}
		return b.toString().trim();
	}
}
