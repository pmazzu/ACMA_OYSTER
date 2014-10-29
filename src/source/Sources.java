package edu.ualr.oyster.utilities.acma.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.LineNumberReader;

import edu.ualr.oyster.utilities.acma.core.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * 
 * @author Pablo Mazzucchi
 *
 */

public class Sources {

	private final static Logger logger_log = Logger.getLogger("LOG");
	
/**
 * The file received should be a .csv file with just 1 file  
 * The objective of this method is opening the file received by parameter, 
 * reading the only line on it and tokenize the hole string, saving each token
 * in an array.  
 * @param The path of a .csv file
 * @return An array 
 */
	
	public int numberOfLines(String path) {
		
		FileReader fr;
		File f;
		int numberOfLines = 0;		
			
		try {
			f = new File(path);
			fr = new FileReader(f);
			
			LineNumberReader  lnr = new LineNumberReader(fr);
			lnr.skip(Long.MAX_VALUE);
			numberOfLines = lnr.getLineNumber() + 1;
			fr.close();	
		
		} 
		catch (IOException ex) {
			logger_log.log(Level.SEVERE, null, ex);
		}
		
		return numberOfLines;
	}
	
	public String[] process_string_semicolon(String list){
		
		String list_splitted[] = null;
		
		list_splitted = list.split(";");
		
		return list_splitted;
	}
	
	public ArrayList<String> generateArrayList(String list){
		
		String list_splitted[] = null;
		
		list_splitted = list.split(";");
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		for(int i=0;i<list_splitted.length;i++){
			arrayList.add(list_splitted[i].toUpperCase(Locale.US));
		}
		
		return arrayList;		
	}
	
	public ArrayList<Entity> process_string_space(String list){
		
		Entity string;
		String string_splitted[] = null;
		ArrayList<Entity> string_1;
		
		string_splitted = list.split(" ");
		
		string_1 = new ArrayList<Entity>(string_splitted.length);
	
		//Create a new object for each author from the source 1, and load it in an array					
		for(int i=0; i < string_splitted.length; i++){
			string = new Entity();
			string.setRealName(string_splitted[i]);
			string.setFingerPrintName(string_splitted[i]);
			string.setPosition(i);
			string_1.add(string);
		}
		
		return string_1;	
	}
	
	
	
	public ArrayList<Entity> process_keywords(ArrayList<String> keywords){
		
		ArrayList<Entity> keywords_final = new ArrayList<Entity>(keywords.size());
		int position = 0;
		
		Iterator<String> itr = keywords.iterator();
		//Create a new object for each keyword, and load it in an array
		while(itr.hasNext()){
			//si la keyword tiene multiples palabras, se podria agregar un split aqui
			// y crear una entidad para cada una, usando un for
			String keyword = itr.next();
			Entity entity = new Entity();
			entity.setRealName(keyword);
			entity.setFingerPrintName(keyword);
			entity.setPosition(position);
			position = position + 1;
			keywords_final.add(entity);			
		}
		
		return keywords_final;
	}
	
	public ArrayList<Entity> process_authors_semicolon(String authorList){
		
		
		Entity author1;
		String author_splitted[] = null;
		ArrayList<Entity> author_1;
		
		author_splitted = authorList.split(";");
		
		author_1 = new ArrayList<Entity>(author_splitted.length);
	
		//Create a new object for each author from the source 1, and load it in an array					
		for(int i=0; i < author_splitted.length; i++){
			author1 = new Entity();
			author1.setRealName(author_splitted[i]);
			author1.setFingerPrintName(author_splitted[i]);
			author1.setPosition(i);
			author_1.add(author1);
		}
		
		return author_1;
		
	}
	
	public String[] process_files_array(String path, int lineNumber) {
		
		BufferedReader br;
		FileReader fr;
		File f;
		String author_splitted[] = null;
		String line = "";

		try {
			f = new File(path);
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			for(int i=1; i <= lineNumber;i++){
				line = br.readLine();	
			}
						
			author_splitted = line.split(";");
			
			br.close();
			fr.close();
		} 
		catch (IOException ex) {
			logger_log.log(Level.SEVERE, null, ex);
		}

		return author_splitted;
	}
	
	public ArrayList<String> process_stringToArrayList(String input){
		
		ArrayList<String> arrayList = new ArrayList<String>();
		String[] array = input.split(";");
		
		for(int i=0;i<array.length;i++){
			arrayList.add(array[i]);
		}
			
		return arrayList;
	}
}