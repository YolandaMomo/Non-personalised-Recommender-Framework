/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */ 

package alg.np.similarity.metric;

import java.util.Map;
import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class GenomeMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new GenomeMetric object
	 * @param reader - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader)
	{
		this.reader = reader;
	}
	
	/**
	 * computes the similarity between items
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y)
	{
		// calculate similarity using Cosine
		
		// get two genome scores vectors for items X and Y
		Profile genomeX = reader.getItem(X).getGenomeScores();
		Profile genomeY = reader.getItem(Y).getGenomeScores();
		
		// set initial value of variables
		double sum = 0;
		double genomeX_length = 0;
		double genomeY_length = 0;
		double multiply_length = 0;
				
				
		// calculate the inner product of two vectors
		// calculate the square of modulus of two vectors
		// calculate their multiplication
		for(int i=1; i<=genomeX.getSize(); i++){
			sum += genomeX.getValue(i) * genomeY.getValue(i);
			genomeX_length += genomeX.getValue(i) * genomeX.getValue(i);
			genomeY_length += genomeY.getValue(i) * genomeY.getValue(i);
			multiply_length = genomeX_length * genomeY_length;
		}
		
		// calculate the return the cosine - if division by zero occurs, return zero 
		return (multiply_length != 0) ? (sum / Math.sqrt(multiply_length)) : 0;
	}
}
