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

package it.uniroma2.sag.kelp.data.example;

import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.representation.Normalizable;
import it.uniroma2.sag.kelp.data.representation.Representation;
import it.uniroma2.sag.kelp.data.representation.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * An <code>Example</code> composed by a set of <code>Representation</code>s.
 * @author Simone Filice
 */
@JsonTypeName("simple")
public class SimpleExample extends Example {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8619029107770256417L;
	
	private HashMap<String,Representation> representations;
	
	/**
	 * Initializes an empty example (0 labels and 0 representations)
	 */
	public SimpleExample(){
		super();
		this.representations = new HashMap<String,Representation>();
	}
	
	/**
	 * Initializes a SimpleExample with the input representations and labels
	 * 
	 * @param labels
	 *            the classes whose this instance is a positive example
	 * @param representations
	 *            the various representations in which the instance is
	 *            represented
	 */
	public SimpleExample(Label[] labels, HashMap<String, Representation> representations) {
		this();
		this.setLabels(labels);
		this.setRepresentations(representations);
	}
	
	/**
	 * Sets the example representations
	 * 
	 * @param representations
	 *            to associate to this example
	 */
	public void setRepresentations(HashMap<String, Representation> representations) {

		this.representations.clear();
		this.representations=representations;
	}
	
	/**
	 * Returns the example representations
	 * 
	 * @return the representations of this example
	 */
	public Map<String, Representation> getRepresentations() {
		return this.representations;
	}
	
	/**
	 * Adds a representation to this example
	 * 
	 * @param representationName the identifier of the representation to be added
	 * @param representation the representation to be added
	 */
	public void addRepresentation(String representationName, Representation representation) {
		representations.put(representationName, representation);
	}
	
	/**
	 * Returns the number of representations in which this example is modeled
	 * 
	 * @return the number of representations
	 */
	@JsonIgnore
	public int getNumberOfRepresentations() {
		return representations.size();
	}
	
	/**
	 * Returns the representation corresponding to <code>representationName</code>
	 * 
	 * @param representationName it is a representation identifier
	 * @return the representation corresponding to <code>representationName</code>
	 */
	public Representation getRepresentation(String representationName) {
		return this.representations.get(representationName);
	}
	
	@Override
	public boolean equals(Object example){
		if(example instanceof SimpleExample){
			SimpleExample that = (SimpleExample) example;
			return super.equals(that) && this.equalsIgnoreLabels(that);
		}
		return false;		
	}
	
	
	/**
	 * Asserts whether this example and the input one have identical
	 * representations, ignoring their labels
	 * 
	 * @param example
	 * @return true if this example and the input one have identical
	 *         representations, false otherwise
	 */
	public boolean equalsIgnoreLabels(SimpleExample example) {
		if (example == null) {
			return false;
		}
		if (this == example) {
			return true;
		}
		if (this.getNumberOfRepresentations() == example.getNumberOfRepresentations()) {
			for(Entry<String, Representation> entry: this.representations.entrySet()){
				if(!entry.getValue().equals(example.getRepresentation(entry.getKey()))){
					return false;
				}
			}
			
		} else {
			return false;
		}

		return true;
	}
	
	/**
	 * Normalize each normalizable representation of this example
	 */
	@Override
	public void normalize() {
		for(Representation rep : this.representations.values()){
			if(rep instanceof Normalizable){
				((Normalizable)rep).normalize();
			}
		}
	}
	
	@Override
	public String toString() {
		String ret = this.getTextualLabelPart();
		for(Entry<String, Representation> entry: this.representations.entrySet()){
			Representation representation = entry.getValue();
			String identifier = entry.getKey();
			
			ret += ExampleFactory.getTextualRepresentation(representation, identifier)					
					+ ExampleFactory.REPRESENTATION_SEPARATOR;
			
		}
		return ret.toString();
	}
	
	public String printExample(String... representations) {
		if (representations == null || representations.length==0) {
			return toString();
		} else {
			StringBuilder ret = new StringBuilder();
			ret.append(this.getTextualLabelPart());
			for (String repr : representations) {
				Representation representation = this.representations.get(repr);
				ret.append(ExampleFactory.getTextualRepresentation(representation, repr)					
						+ ExampleFactory.REPRESENTATION_SEPARATOR);
			}
			return ret.toString().trim();
		}
	}

	@Override
	public Vector getZeroVector(String representationIdentifier) {
		Vector rep = (Vector) getRepresentation(representationIdentifier);
		return rep.getZeroVector();
	}
}
