package edu.ualr.oyster.utilities.acma.core;

import edu.ualr.oyster.utilities.acma.blocking.Fingerprint;

public class Entity {
	
	private String pubId;
	private String realName;
	private String fingerPrintName;
	private int position;
	private boolean visible = true;
	public boolean hasAnyNumber = false;
	
	//constructors
	
	public boolean getHasAnyNumber(){
		return this.hasAnyNumber;
	}
	public void setPosition(int position){
		this.position = position;
	}
	public void setRealName(String name){
		this.realName = this.homogeneousFormat(name);
	}
	public void setRealNameWithoutFormatting(String name){
		this.realName = name;
	}
	public void setPubId(String id){
		this.pubId = id;
	}
	public void setFingerPrintName(String name){
		Fingerprint fingerPrint = new Fingerprint();
		this.fingerPrintName = fingerPrint.key(name);
	}
	public void setVisibilityTrue(){
		this.visible = true;
	}
	public void setVisibilityFalse(){
		this.visible = false;
	}
	//GETs
	public int getPosition(){
		return this.position;
	}
	public String getRealName(){
		return this.realName;
	}
	public String getPubId(){
		return this.pubId;
	}
	public String getFingerPrintName(){
		return this.fingerPrintName;
	}
	public boolean getVisibility(){
		return this.visible;
	}
	
	public void formatRealName(){
		this.realName = this.homogeneousFormat(this.realName);
	}
	
	public boolean isString(String string){
		
		boolean isString = false;
		loop:
		for(int i=0;i<string.length();i++){
			if(Character.isLetter(string.charAt(i))){
				isString=true;
				break loop;
			}
		}		
		return isString;
	}
	
	private String homogeneousFormat(String name){
		String temp = "";
		Character c;
		boolean cosecutivesSpaces = false;
		boolean consecutiveshypen = false;
		
		Fingerprint fingerPrint = new Fingerprint();
		
		name = name.trim(); //delete spaces at the beggining and at the end of the string
		name = name.toUpperCase();
	
		int length = name.length();
		
		for(int i=0;i<length;i++){
			c = name.charAt(i);
			
			c = fingerPrint.translate(c);//removes diacritics/stresses
						
			if (java.lang.Character.isLetter(c)){
				temp = temp + c;
				cosecutivesSpaces = false;
				consecutiveshypen = false;
			} else if( java.lang.Character.isWhitespace(c) ){
				if(!cosecutivesSpaces){
					temp = temp + c;
					cosecutivesSpaces = true;
				}
			} else if(c.equals('-')){
				if(!consecutiveshypen){
					String space = "";
					temp = temp + String.format("%-1s", space);
					consecutiveshypen = true;
				}
			} else if(java.lang.Character.isDigit(c)){
				this.hasAnyNumber = true;
			}
		}
				
		return temp;
	}
	
}
