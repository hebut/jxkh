package org.iti.xypt.personal.infoCollect;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.ParsingDetector;

import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.lexer.Page;

public class WebEncoding {

	public String AnalyEnconding(String path){
	URL url=null;
	try {
		url=new URL(path);
	} catch (MalformedURLException e) {
		System.out.println("ÍøÖ·½âÎö´íÎó£¡");
	}
	CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance(); 
	detector.add(new ParsingDetector(false)); 
	java.nio.charset.Charset charset = null;  
	try {  
	      charset = detector.detectCodepage(url);  
	} catch (Exception ex) {
		System.out.println("Á´½Ó³¬Ê±£¡");
//		ex.printStackTrace();
	}
	if(charset!=null){
		
	 if(charset.name().equalsIgnoreCase("utf-8")||charset.name().equals("UTF-8")){
		 Page.GaoBinDEFAULT_CHARSET="utf-8";
      }else{
    	  Page.GaoBinDEFAULT_CHARSET="gb2312";
     }
	   return Page.getGaoBinDEFAULT_CHARSET();
	   
	}else{
		System.out.println("Á´½Ó³¬Ê±£¬ÍøÕ¾±àÂëÎ´ÄÜ½âÎö£¡");
		return null;
	}
	
	   
	}
}
