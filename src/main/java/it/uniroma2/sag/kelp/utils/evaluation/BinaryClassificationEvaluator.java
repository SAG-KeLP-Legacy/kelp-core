/*
 * Copyright 2014 Simone Filice and Giuseppe Castellucci and Danilo Croce and Roberto Basili
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.uniroma2.sag.kelp.utils.evaluation;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.predictionfunction.Prediction;

/**
 * This is an instance of an Evaluator. It allows to compute the some common
 * measure for binary classification tasks.
 * 
 * @author Giuseppe Castellucci
 */
public class BinaryClassificationEvaluator extends Evaluator {
	private Label positiveLabel;
	
	private int total, correctAcc, correctForF1, predictedForF1; 
	private float accuracy, precision, recall, f1;
	
	public BinaryClassificationEvaluator(Label positiveClass) {
		this.positiveLabel = positiveClass;
		initializeCounters();
	}

	private void initializeCounters() {
		total = 0;
		correctAcc = 0;
		accuracy = 0.0f;
		
		correctForF1=0;
		predictedForF1=0;
		precision=0.0f;
		recall=0.0f;
		f1=0.0f;
	}

	public void addCount(Example test, Prediction prediction) {
		total++;
		if (prediction.getScore(positiveLabel) >= 0)
			predictedForF1++;
		if (prediction.getScore(positiveLabel) >= 0
				&& test.isExampleOf(positiveLabel)) {
			correctAcc++;
			correctForF1++;
		} else if (prediction.getScore(positiveLabel) < 0
				&& !test.isExampleOf(positiveLabel))
			correctAcc++;
	}

	public void compute() {		
		precision = (float) correctForF1 / (float) predictedForF1;
		recall = (float) correctForF1 / (float) total;
		f1 = 2 * precision * recall
				/ (precision + recall);

		accuracy = (float) correctAcc / (float) total;
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
	 * Return the precision considering all classes together
	 * 
	 * @return precision
	 */
	public float getPrecision() {
		return precision;
	}

	/**
	 * Return the recall considering all classes together
	 * 
	 * @return recall
	 */
	public float getRecall() {
		return recall;
	}

	/**
	 * Return the f1 considering all classes together
	 * 
	 * @return f1
	 */
	public float getF1() {
		return f1;
	}

	/**
	 * Clear all the counters for a new processing.
	 */
	@Override
	public void clear() {
		total = 0;
		correctAcc = 0;
		accuracy = 0.0f;
		correctForF1=0;
		predictedForF1=0;
		precision=0.0f;
		recall=0.0f;
		f1=0.0f;
	}

	/**
	 * Print the counters in a human-readable format
	 */
	@SuppressWarnings("unused")
	private void printCounters() {
		System.out.println("Accuracy measures");
		System.out.println("\tCorrect: " + correctAcc);
		System.out.println("\tTotal: " + total);
		System.out.println("F1 measures");
		System.out.println("\tCorrect: " + correctForF1);
		System.out.println("\tPredicted: " + predictedForF1);
		System.out.println("\tToBePredicted: " + total);
	}
}
