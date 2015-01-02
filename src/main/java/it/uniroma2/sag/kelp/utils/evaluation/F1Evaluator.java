package it.uniroma2.sag.kelp.utils.evaluation;

import gnu.trove.map.hash.TObjectFloatHashMap;
import it.uniroma2.sag.kelp.data.label.Label;

import java.util.ArrayList;
/**
 * This is an instance of an Evaluator. 
 * It allows to compute the Precision, Recall and F1.
 * 
 * @author Giuseppe Castellucci
 */
public class F1Evaluator extends Evaluator {
	private ArrayList<Label> labels;
	private TObjectFloatHashMap<Label> correctCounter = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> predictedCounter = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> toBePredictedCounter = new TObjectFloatHashMap<Label>();

	private TObjectFloatHashMap<Label> precisions = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> recalls = new TObjectFloatHashMap<Label>();
	private TObjectFloatHashMap<Label> f1s = new TObjectFloatHashMap<Label>();

	/**
	 * Initialize a new F1Evaluator that will work on the specified classes
	 * @param labels
	 */
	public F1Evaluator(ArrayList<Label> labels) {
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

	public void addCount(Label gold, Label predicted) {
		toBePredictedCounter.put(gold, toBePredictedCounter.get(gold) + 1);
		predictedCounter.put(predicted, predictedCounter.get(predicted) + 1);
		if (predicted.equals(gold)) {
			correctCounter.put(gold, correctCounter.get(gold) + 1);
		}
	}

	public void compute() {
		for (Label l : labels) {
			float precision = correctCounter.get(l) / predictedCounter.get(l);
			float recall = correctCounter.get(l) / toBePredictedCounter.get(l);
			float f1 = (2 * precision * recall) / (precision + recall);
			
			precisions.put(l, precision);
			recalls.put(l, recall);
			f1s.put(l, f1);
		}
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
	public void clear() {
		correctCounter.clear();
		predictedCounter.clear();
		toBePredictedCounter.clear();
		precisions.clear();
		recalls.clear();
		f1s.clear();
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
