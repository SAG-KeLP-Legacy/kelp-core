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
package it.uniroma2.sag.kelp.kernel;

import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.example.SimpleExample;
import it.uniroma2.sag.kelp.data.representation.Representation;

/**
 * It is a kernel that operates exploiting directly on a specific
 * representation.
 * 
 * @author Simone Filice
 */
public abstract class DirectKernel<T extends Representation> extends Kernel {
	protected String representation;

	/**
	 * Initializes a kernel operating directly on a specific representation
	 * identified by <code>representationIdentifier</code>
	 * 
	 * @param representationIdentifier
	 *            the identifier of the representation to be directly exploited
	 * 
	 */
	public DirectKernel(String representationIdentifier) {
		super();
		this.representation = representationIdentifier;

	}

	public DirectKernel() {
	}

	/**
	 * @return the representation
	 */
	public String getRepresentation() {
		return representation;
	}

	/**
	 * @param representation
	 *            the representation to set
	 */
	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected float kernelComputation(Example exA, Example exB) {
		if (exA instanceof SimpleExample && exB instanceof SimpleExample) {
			SimpleExample exampleA = (SimpleExample) exA;
			SimpleExample exampleB = (SimpleExample) exB;
			return kernelComputation((T)exampleA.getRepresentation(representation), (T)exampleB.getRepresentation(representation));
		} else {
			throw new java.lang.IllegalArgumentException(
					"Invalid object: expected two SimpleExamples to performe the inner product");
		}

	}
	
	/**
	 * Computes the kernel similarity between two specific representations 
	 * 
	 * @param repA the first representation in the kernel similarity
	 * @param repB the second representation in the kernel similarity
	 * @return the kernel similarity
	 */
	protected abstract float kernelComputation(T repA, T repB);
}