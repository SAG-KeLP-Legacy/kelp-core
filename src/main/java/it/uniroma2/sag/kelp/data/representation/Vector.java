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

package it.uniroma2.sag.kelp.data.representation;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * It is a Vectorial representation
 * 
 * @author Simone Filice
 *
 */
public interface Vector extends Normalizable {

	/**
	 * Returns the dot product between this vector and <code>vector</code>
	 * 
	 * @param vector
	 * @return the dot product
	 */
	public float innerProduct(Vector vector);

	/**
	 * Compute the point-wise product of this vector with the one in
	 * <code>vector</code>.
	 * 
	 * @param vector the vector used for the point-wise product
	 */
	public void pointWiseProduct(Vector vector);

	/**
	 * Add a <code>vector</code> to this vector
	 * 
	 * @param vector
	 *            the vector to be added
	 */
	public void add(Vector vector);

	/**
	 * Add a <code>vector</code> multiplied by <code>coeff</code> to this vector
	 * 
	 * @param coeff
	 *            the <code>vector</code> coefficient
	 * @param vector
	 *            the vector to be added
	 */
	public void add(float coeff, Vector vector);

	/**
	 * Add a <code>vector</code> multiplied by <code>vectorCoeff</code> to this
	 * vector multiplied by
	 * 
	 * @param coeff
	 *            the coefficient of this vector
	 * @param vectorCoeff
	 *            the <code>vector</code> coefficient
	 * @param vector
	 *            the vector to be added
	 */
	public void add(float coeff, float vectorCoeff, Vector vector);

	/**
	 * Returns a vector whose values are all 0. The returned vector has the same
	 * dimensions of this
	 * 
	 * @return a zero vector
	 */
	public Vector getZeroVector();

	/**
	 * Returns a map containing all the non-zero features
	 * 
	 * @return the non zero features
	 */
	@JsonIgnore
	public Map<String, Float> getActiveFeatures();

}
