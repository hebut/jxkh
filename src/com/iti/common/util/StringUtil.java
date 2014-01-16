package com.iti.common.util;

public class StringUtil {

	public static String getNewChildren(String Child, Long Id) {
		boolean flag=false;
		for(String s :Child.split(",")){
			if(s.equalsIgnoreCase(Id.toString())){
				flag=true;
			}
		}
		if(!flag){
			if(Child.startsWith(",")){
				return Id.toString()+Child;
			}else{
				return Child+Id.toString()+",";
			}
			
		}else{
			return Child;
		}
		
	}
	public static String delNewChildren(String Child, Long Id) {
		String newChild=","+Child;
		newChild=newChild.replaceAll(","+Id.toString()+",", ",");
		if(newChild.length()>1&&newChild.startsWith(","))
			newChild=newChild.substring(1, newChild.length());
		return newChild;	
	}
}
