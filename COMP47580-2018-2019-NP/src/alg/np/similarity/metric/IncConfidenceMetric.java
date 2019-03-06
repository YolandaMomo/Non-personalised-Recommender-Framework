/**
 * Compute the similarity between two items based on increase in confidence
 */ 

package alg.np.similarity.metric;

import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class IncConfidenceMetric implements SimilarityMetric
{
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items 
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * @param reader - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader)
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
		// calculate similarity using conf(X => Y) / conf(!X => Y)
		
		// get two incconfidence scores vectors for items X and Y
		Profile IncConfidenceX = reader.getItemProfiles().get(X);
		Profile IncConfidenceY = reader.getItemProfiles().get(Y);
		
		// set initial value of variables
		double supp_X_nominator = 0;
		double supp_X_Y_nominator = 0;
		double supp_not_X_nominator = 0;
		double supp_not_X_and_Y_nominator = 0;
		double conf_X_Y = 0;
		double conf_not_X_Y = 0;
		double similarity = 0;
				
		// get all the ids of two items
		Set<Integer> idsX = IncConfidenceX.getIds();
		Set<Integer> idsY = IncConfidenceY.getIds();	
				
		// create a loop for each movie in the dataset
		// calculate nominators of supp(X) and supp(!X)
		// for each movie in movie two vectors that is rated by users
		// calculate nominators of supp(X,Y) and supp(!X,Y)
		for(int i:idsX){
			if(IncConfidenceX.getValue(i) >= RATING_THRESHOLD){
				supp_X_nominator ++;
			}
			else supp_not_X_nominator ++;
			
			if(idsY.contains(i)){
				if(IncConfidenceX.getValue(i) >= RATING_THRESHOLD && IncConfidenceY.getValue(i) >= RATING_THRESHOLD){
					supp_X_Y_nominator ++;
				}
				if(IncConfidenceX.getValue(i)< RATING_THRESHOLD && IncConfidenceY.getValue(i) >= RATING_THRESHOLD){
					supp_not_X_and_Y_nominator ++;
				}
			}
		}
				
		// check if denominators of the conf(X,Y), conf(!X,Y) and the similarity are 0
		if(supp_X_nominator == 0) 
			conf_X_Y = 0;
		else conf_X_Y = supp_X_Y_nominator / supp_X_nominator;
				
		if(supp_not_X_nominator == 0) 
			conf_not_X_Y = 0;
		else conf_not_X_Y = supp_not_X_and_Y_nominator / supp_not_X_nominator;
		
		// return the result value
		return (conf_not_X_Y != 0) ? (similarity = conf_X_Y / conf_not_X_Y) : 0;
	}
}
