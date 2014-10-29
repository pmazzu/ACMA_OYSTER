package edu.ualr.oyster.utilities.acma.core;

public class ACMA {
	
	public boolean multivalued_attr_similarity_calc(String list_1, String list_2, String threshold, String comparators, String aggrMode, String threshold_noSimil){
			
		//String threshold_noSimil = "0.8";
		
		String[] arguments = new String[7];
			
		arguments[0] = list_1; 
		arguments[1] = list_2;
		arguments[2] = threshold;
		arguments[3] = threshold_noSimil;
		arguments[4] = comparators;
		arguments[5] = aggrMode;
		
		boolean similar;
			
		Heuristics simil = new Heuristics();
		
		Heuristics.heuristic(simil, arguments);
			
		if (simil.getSimilarityGrade()> simil.getThreshold()){
			similar = true;
		}else{
			similar = false;
		}		
		
		return similar;
	}
}
