package it.uniroma2.sag.kelp.data.dataset.example_selection;

import it.uniroma2.sag.kelp.data.dataset.Dataset;
import it.uniroma2.sag.kelp.data.example.Example;

import java.util.List;

public interface ExampleSelector {

	public List<Example> select(Dataset dataset);
}
