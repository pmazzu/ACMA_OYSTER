package edu.ualr.oyster.utilities.acma.core;

import edu.ualr.oyster.utilities.acma.string_matching.JaroWinklerDistance;
import edu.ualr.oyster.utilities.acma.string_matching.LongestCommonSubstring;

import java.util.*;

import edu.ualr.oyster.utilities.NYSIISCode;
import edu.ualr.oyster.utilities.OysterNickNameTable;
import edu.ualr.oyster.utilities.OysterAliasTable;

public class Comparison{

	/**
	 * When the similarity of two authors exceeds the threshold,
	 * all the cells in that column and rows will not be compared anymore.
	 * Thats why each author has an attribute called "visible", which will be set to TRUE or FALSE
	 * depending the results of the comparison.  
	 * @param The similarity matrix, and the authors to be compared
	 * @return N/A 
	 */

	public static void calculate_similarity_of_some_visible_entities(ArrayList<Entity> authors_i, ArrayList<Entity> authors_j, Similarity simil, boolean check){
		
		double value = 0;
		float checkpoint = 0;
		int row_counter = 0;
		Iterator<Entity> itr_i;
		Iterator<Entity> itr_j;
		Entity author_row;
		Entity author_column = null;
			
		if (check){
			checkpoint = Math.round(authors_i.size() * 0.8)-1;// the -1 is because the array index starts from 0 (i)
		}
								
		first_loop:
		while(true){
			itr_i = authors_i.iterator();
			//second_loop:
			while(itr_i.hasNext()){
				author_row = itr_i.next();
				itr_i.remove();//will not use this entity anymore.  I keep just the reference in author_row
				value=0;
				itr_j = authors_j.iterator();
				third_loop:
				while(itr_j.hasNext()){
					author_column = itr_j.next();
			
					value = similarity(author_row.getRealName(), author_column.getRealName(), simil);
					simil.increaseComparisons();
					simil.updateSimMatrix(author_row.getPosition(),
							              author_column.getPosition(),
							              value);
						
					if (value >= simil.getThreshold()){
						itr_j.remove();
						simil.increaseAssertions();
						break third_loop;
					}
				}
				
				if(check){
					if ( row_counter == checkpoint){
						simil.calculate_partial_similarity_grade(row_counter,simil.getAggregation().getMode());
						if(simil.getPartialSimilarityGrade() < simil.getNoSimilarityThreshold()){
							break first_loop; // It is not useful continue with the comparison if the .8 of the authors similarity is below the threshold
						}
					}
				}
				row_counter = row_counter + 1;
			}
			if(!itr_i.hasNext()){
				break first_loop;	
			}
		}
	}

	public static void calculate_similarity_of_all_visible_entities(ArrayList<Entity> authors_i, ArrayList<Entity> authors_j, Similarity simil){
		
		double value = 0;
		double max = 0;
		Iterator<Entity> itr_i;
		Iterator<Entity> itr_j;
		Entity author_row;
		Entity author_column = null;
		Entity author_j_to_delete = null;
			
		itr_i = authors_i.iterator();
		
		while(itr_i.hasNext()){
			author_row = itr_i.next();
			itr_i.remove();//will not use this entity anymore.  I keep just the reference in author_row
			// the author of this row will not be compared again against other author
			value=0;
			itr_j = authors_j.iterator();
			third_loop:
			while(itr_j.hasNext()){
				author_column = itr_j.next();
		
				value = similarity(author_row.getRealName(), author_column.getRealName(), simil);
				simil.increaseComparisons();
				simil.updateSimMatrix(author_row.getPosition(),
						              author_column.getPosition(),
						              value);
					
				if (value > max && value >= simil.getThreshold()){
					max = value;
					author_j_to_delete = author_column;
					simil.increaseAssertions();
					break third_loop;
				}
			}
			if (max != 0){
				authors_j.remove(author_j_to_delete); // the author of this column will not be compared again against other author
			}
		}
	}

	public static double similarity(String author1, String author2, Similarity simil){
		
		double similarity_jw = 0; // Jaro-Winkler
		double similarity_lcs = 0; //Longest Common Substring
		double similarity_rms = 0; // Root Mean Square
		double similarity_nickname = 0; //nickname
		double similarity_alias = 0; //nickname
		double similarity_exact = 0; //exact matching
		double similarity_nysiis = 0; // NYSII algorithm
		ArrayList<Double> similarity_values = new ArrayList<Double>();
				
		int comparators = simil.getComparatorsList().length;
		
		if (comparators >0){	
			
			for(int i=0;i<comparators;i++){
				
				switch (simil.getComparatorIndex(simil.getComparatorsList()[i])){
					case 0://rms
						
						JaroWinklerDistance jw = new JaroWinklerDistance();
						
						//Because the JW implementation returns 0 when are equals and 1 when they are different
						similarity_jw = 1 - jw.distance(author1, author2);
								
						LongestCommonSubstring lcs = new LongestCommonSubstring();
						
						similarity_lcs = lcs.lcs_similarity_grade(author1, author2);
								
						similarity_rms = Math.sqrt( ( Math.pow(similarity_jw, 2) + Math.pow(similarity_lcs, 2) ) / 2);
						
						similarity_values.add(similarity_rms);
						
						break;
					case 1://nickname
						
					    /** Nickname/Alias lookup */
					    OysterNickNameTable nnTable;
						
						nnTable = new OysterNickNameTable();
						
						if (nnTable.isNicknamePair(author1, author2)){
							similarity_nickname = 1;
						}
						
						similarity_values.add(similarity_nickname);
						
						break;
					case 2://exact Matching
						
						if (author1.equals(author2)){
							similarity_exact = 1;
						}
						
						similarity_values.add(similarity_exact); 
						
						break;
					case 3: //alias
					    /** Alias lookup */
					    OysterAliasTable aTable;
						
					    aTable = new OysterAliasTable();
						
						if (aTable.isAliasPair(author1, author2)){
							similarity_alias = 1;
						}
						
						similarity_values.add(similarity_alias);
												
						break;
					case 4: //NYSII
						
						/** New York State Identification and Intelligence System operator */
					    NYSIISCode nysiis;
						
				        nysiis = new NYSIISCode();
				        
				        if (nysiis.compareNYSIISCodes(author1, author2)){
				        	similarity_nysiis = 1;;
				        }
				        
				        similarity_values.add(similarity_nysiis);
						
						break;
						
					default:
						break;
				}
			}
		}
		
		return max(similarity_values);
	}
	
	
	public static double max(ArrayList<Double> similarity_values){
			
		Iterator<Double> itr = similarity_values.iterator();
		
		double max = -1;
		double simil_value = 0;
		
		while(itr.hasNext()){
			simil_value = itr.next();
			
			if(simil_value > max){
				max = simil_value;
			}	
		}
		
		return max;
		
	}
	
}