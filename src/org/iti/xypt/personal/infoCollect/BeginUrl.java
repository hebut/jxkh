package org.iti.xypt.personal.infoCollect;

import java.util.ArrayList;
import java.util.List;

public class BeginUrl {

	public List<String> analyUrl(String characters){
		 List<String> all=new ArrayList<String>();
		 List<Integer> l=new ArrayList<Integer>();
		 l.add(-1);
		 for(int i=0;i<characters.length();i++){
			 if(characters.charAt(i)=='$'){
				 l.add(i);
			 }
		 }
		 
		 for(int j=0;j+1<l.size();j++){
			 String ss=characters.substring(l.get(j)+1,l.get(j+1));
			 all.add(ss);
		 }
		return all;
	}
}
