package it.uniroma2.sag.kelp.data.dataset.example_selection;

import it.uniroma2.sag.kelp.data.dataset.Dataset;
import it.uniroma2.sag.kelp.data.dataset.DatasetReader;
import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;

import java.io.IOException;
import java.util.List;

public class FirstExampleSelector implements ExampleSelector {

	private int m;

	public FirstExampleSelector(int m) {
		super();
		this.m = m;
	}


	public List<Example> select(Dataset dataset) {
		return dataset.getNextExamples(m);
	}

	public List<Example> select(DatasetReader datasetReader)
			throws IOException, InstantiationException {

		int counter = 1;
		SimpleDataset dataset = new SimpleDataset();
		while (datasetReader.hasNext()) {
			Example e = datasetReader.readNextExample();
			dataset.addExample(e);

			if (counter % 100 == 0) {
				System.out.println("Loaded " + counter
						+ " landmark candidates.");
			}

		}

		return dataset.getShuffledDataset().getNextExamples(m);
	}

}
