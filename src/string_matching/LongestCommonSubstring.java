package edu.ualr.oyster.utilities.acma.string_matching;

public class LongestCommonSubstring {
	
	public int[] longest_Common_Substring(String S1, String S2)
	{
	    int Start_s1 = 0;
	    int Max = 0;

	    int[] results = new int[2];
	    
	    for (int i = 0; i < S1.length(); i++)
	    {
	        for (int j = 0; j < S2.length(); j++)
	        {
	            int x = 0;
	            while (S1.charAt(i + x) == S2.charAt(j + x))
	            {
	            	x++;
	                if (((i + x) >= S1.length()) || ((j + x) >= S2.length())) break;
	            }
	            if (x > Max)
	            {
	                Max = x;
	                Start_s1 = i;
	            }
	         }
	    }
	    
	    results[0] = Start_s1;
	    results[1] = Max;
	    
	    return results;
	}
	
	public double lcs_similarity_grade(String author1, String author2) {
		
		int[] lcs_results = new int[2];
		double distance = 0;
		int start = 0;
		int lenght = 0;
		double shortestLenght = 0;
		String longestAuthor = "";
		String shortestAuthor = "";
		String eliminate = "";
				
		if (author1.length() <= author2.length()){
			longestAuthor = author2;
			shortestAuthor = author1;
		}else {
			longestAuthor = author1;
			shortestAuthor = author2;
		}

		shortestLenght = shortestAuthor.length();
		
		lcs_results = longest_Common_Substring(longestAuthor, shortestAuthor);
		
		start = lcs_results[0];
		lenght = lcs_results[1];
		
		while(lenght >1){ // it looks for token of size 2 or greater 

								
			eliminate = longestAuthor.substring(start, start + lenght);
			longestAuthor = longestAuthor.replace(eliminate, "");
			shortestAuthor = shortestAuthor.replace(eliminate, "");
						
			distance = distance + lenght;
			
			lcs_results = longest_Common_Substring(longestAuthor, shortestAuthor);
			
			start = lcs_results[0];
			lenght = lcs_results[1];
			
		}
			
		distance = distance / shortestLenght;
		
		return distance;
			
	}
	
}