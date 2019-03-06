/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */ 

package alg.np.similarity.metric;

import java.util.Map;
import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader)
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

		// get two rating scores vectors for items X and Y
		Profile ratingsX = reader.getItemProfiles().get(X);
		Profile ratingsY = reader.getItemProfiles().get(Y);
		
		// set initial value of variables
		double sum = 0;
		double ratingX_length =0;
		double ratingY_length =0;
		double multiply_length = 0;
				
		// get all the ids of two items
		Set<Integer> idsX = ratingsX.getIds();
		Set<Integer> idsY = ratingsY.getIds();
				
		// create a loop for each movie that is rated by users
		// calculate the inner product of two vectors
		// calculate the square of modulus of two vectors
		// calculate their multiplication
		for(int i:idsX){
			if(idsY.contains(i)){
				sum += ratingsX.getValue(i) * ratingsY.getValue(i);
				ratingX_length += ratingsX.getValue(i) * ratingsX.getValue(i);
				ratingY_length += ratingsY.getValue(i) * ratingsY.getValue(i);						
				multiply_length = ratingX_length * ratingY_length;
			}
		}
		
		// calculate the return the cosine - if division by zero occurs, return zero 
		return (multiply_length != 0) ? (sum / Math.sqrt(multiply_length)) : 0;
	}
}
