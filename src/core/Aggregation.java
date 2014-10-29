package edu.ualr.oyster.utilities.acma.core;

public class Aggregation {
	
	String mode = "STRICT"; // Could be STRICT (default) or SUBSET
	
	public void setMode(String mode){
		this.mode = mode;
	}
	
	public String getMode(){
		return this.mode;
	}

	public void calculate_average(Similarity simil){
		
		double sum = 0;
		int i = 0, j = 0;
		
		// sum all the similarity values of the matrix and calculate the average
		for (i = 0;i < simil.getRows(); i++ ){
			for (j = 0; j < simil.getColumns();j++){
				sum = sum + simil.getSimMatrixValue(i,j);
			}
		}
			
		simil.setSimilarityGrade(sum / (simil.getColumns()* simil.getRows()));
	}

			
	public void calculate_max_average(Similarity simil){
		
		double highest = 0, verticalSum = 0, horizontalSum = 0;
		int i = 0, j = 0;
		
		if(this.getMode().equals("STRICT")){
			
			// get the max value of each row and column, sum them, and calculate the average of the biggest value
			// find the highest values of each row						
			for (i = 0;i < simil.getRows(); i++ ){
				for (j = 0; j < simil.getColumns();j++){
					if (simil.getSimMatrixValue(i,j) > highest){
						highest = simil.getSimMatrixValue(i,j);
					}
				}
				verticalSum = verticalSum + highest;
				highest = 0;
			}
			
			simil.setSimilarityGrade(verticalSum / simil.getRows());
			
		}else if(this.getMode().equals("SUBSET")){
			
			// find the highest values of each column
			highest = 0;
			for (j = 0;j < simil.getColumns(); j++ ){
				for (i = 0; i < simil.getRows();i++){
					if (simil.getSimMatrixValue(i,j) > highest){
						highest = simil.getSimMatrixValue(i,j);
					}
				}
				horizontalSum = horizontalSum + highest;
				highest = 0;
			}
			
			simil.setSimilarityGrade(horizontalSum / simil.getColumns());
		}
	}
	
}