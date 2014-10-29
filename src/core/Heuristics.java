package edu.ualr.oyster.utilities.acma.core;

import java.util.Iterator;
import java.util.ArrayList;


public class Heuristics extends Similarity {

//**********************************************************************************************//
//				              UNSTRUCTURED DATA COMPARISON						  		        //
//	It takes the alphanumerics and divide them in two groups: numbers and characters are put 	//
//	separately, so the similarity calculation is more accurate, as numbers are compared against //
//	numbers and characters against characters													//
//**********************************************************************************************//
	public static void heuristic(Similarity simil, String[] args){
							
		double similarityGrade_int = 0;
		double similarityGrade_string = 0;
		
		boolean similarityIntPerformed = false;
		boolean similarityStringPerformed = false;
		
		ArrayList<Entity> source_string;
		ArrayList<Entity> target_string;
		
		ArrayList<Entity> source_int;
		ArrayList<Entity> target_int;
		
		ArrayList<Entity>[] intAndString_source;
		ArrayList<Entity>[] intAndString_target;
					
		simil.initialization_oyster(args,2); //1: semicolon 2:space

		
		//Separates the alphanumeric in two groups: characters on one side and numbers on the other
		intAndString_source = Heuristics.separateNumbersFromCharacters(simil.getSource().iterator());
		intAndString_target = Heuristics.separateNumbersFromCharacters(simil.getTarget().iterator());
				
		source_int = intAndString_source[0];
		source_string = intAndString_source[1];
		target_int = intAndString_target[0];
		target_string = intAndString_target[1];
		
		// If both source and target have numbers to compare, the comparison is carried on
		if(source_int.size()>0 & target_int.size()>0){
			
			similarityIntPerformed = true;
			
			similarityGrade_int = Heuristics.fairComparison(simil, source_int, target_int);
			
		}
		// If both source and target have characters to compare, the comparison is carried on
		if (source_string.size() > 0 & target_string.size() > 0){
			
			similarityStringPerformed = true;
			
			similarityGrade_string = Heuristics.fairComparison(simil, source_string, target_string);			
		}
		
		// Calculate the similarity grade considering which comparison had been performed
		if (similarityIntPerformed && similarityStringPerformed){
			simil.setSimilarityGrade((similarityGrade_int+similarityGrade_string)/2.0);
		} else if (similarityIntPerformed){
			simil.setSimilarityGrade(similarityGrade_int);
		} else if (similarityStringPerformed){
			simil.setSimilarityGrade(similarityGrade_string);
		}
				
	}
	
	
	private static ArrayList<Entity>[] separateNumbersFromCharacters(Iterator<Entity> itr){
		
		ArrayList<Entity> string = new ArrayList<Entity>();
		ArrayList<Entity> integer = new ArrayList<Entity>();
		ArrayList<Entity>[] intSeparatedFromString = (ArrayList<Entity>[])new ArrayList[2];
				
		 while(itr.hasNext()){
			Entity entity = itr.next();
			
			if(entity.hasAnyNumber){
				entity.setRealNameWithoutFormatting(entity.getFingerPrintName());
				entity.setPosition(integer.size());
				integer.add(entity);
			}else{
				entity.setPosition(string.size());
				string.add(entity);
			}
		}
		 
		 intSeparatedFromString[0] = integer;
		 intSeparatedFromString[1] = string;
		 		 
		 return intSeparatedFromString;
		
	}
	
	
	private static double fairComparison(Similarity simil, ArrayList<Entity> source, ArrayList<Entity> target){
		
		int rows,columns;
		double similarityGrade = 0;
		Aggregation aggregation = simil.getAggregation();
					
		if(source.size() < target.size()){
			rows = target.size();
			columns = source.size();
			ArrayList<Entity> temp = source;
			source = target;
			target = temp;
		} else {
			rows = source.size();
			columns = target.size();
		}
		
		simil.setSimiMatrix(rows, columns);				
		
		// Comparison process			
		Comparison.calculate_similarity_of_some_visible_entities(source, target, simil, false);
		
		// Calculate the similarity grade integers
		aggregation.calculate_max_average(simil);
		
		similarityGrade = simil.getSimilarityGrade();
		
		return similarityGrade;
		
	}
}