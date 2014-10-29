package edu.ualr.oyster.utilities.acma.core;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.ArrayList;

import edu.ualr.oyster.utilities.acma.blocking.Fingerprint;

public class Alignment{
	
	/**
	 * Sort the entity arrays using the author's name processed by the FingerPrint Algorithm  
	 * @param an array
	 * @return a sorted array 
	 */
	
	public static ArrayList<Entity> sorting_elements(ArrayList<Entity> authors){
		
		TreeSet<Entity> sorted_authors = new TreeSet<Entity>(authorComp);
		Iterator<Entity> itr = authors.iterator();
        Entity author;        
        
        while(itr.hasNext()){
        	author = itr.next();
        	sorted_authors.add(author);
        	itr.remove();
        }
        
        Iterator<Entity> i = sorted_authors.iterator();
        
        int modifiedPosition = 0;
        
        while (i.hasNext()) {  // join ordered fragments back together
        	author = i.next();
        	author.setPosition(modifiedPosition);
        	modifiedPosition = modifiedPosition + 1;
        	authors.add(author);
        }
        
        return authors;
	}
	
	private static Comparator<Entity> authorComp = new Comparator<Entity>() {
	    @Override
	    public int compare(Entity a1, Entity a2) {
	        return (a1.getFingerPrintName().compareTo(a2.getFingerPrintName()));
	    }
	};
	
	// NOT USEFUL ANYMORE - NOW, THE FINGERPRINT ALGORITHM IS EXECUTED IN THE INITIALIZATION FACE
	public static void fingerprint_over_elements(ArrayList<Entity> authors){
		
		Fingerprint fingerPrint = new Fingerprint();
		
		Iterator<Entity> itr = authors.iterator();
			
		while(itr.hasNext()){
			Entity author = itr.next();
			author.setFingerPrintName(fingerPrint.key(author.getRealName()));
		}
	}

}
