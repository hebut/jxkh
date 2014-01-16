package org.iti.xypt.personal.infoCollect;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class PageNext {

	public synchronized void extractNextPage(LinkCollection collection,String encond,String pageSign,Integer pageCount){
		
		Integer count=collection.getunVisitedUrlNum();
		List<String> uList=new ArrayList<String>();
		for(int i=0;i<count;i++){
			String u=collection.unVisitedUrlDeQueue();
			uList.add(u);
		}
		
		String u;
		Integer bCoun;
		for(int j=0;j<uList.size();j++){
			//ѭ����һҳ                  
			bCoun=pageCount;
			collection.addUnvisitedUrl(uList.get(j).toString(),3);
			u=uList.get(j).toString();
			pageSign=pageSign.replace("*", ".*");
			
			if(bCoun==0){
				while(collection.getunVisitedUrlNum()<=1000){
					String extractNextUrl1 = null;
					Parser parser;
					try {
						parser = new Parser(u);
						parser.setEncoding(encond);
						NodeFilter filter=new LinkRegexFilter(pageSign);
						NodeList list=parser.extractAllNodesThatMatch(filter);
						if(list.size()==0){
							System.out.println("��ȡ��һ����ַʧ�ܣ�");
							break;
						}else{
							LinkTag linkTag=(LinkTag)list.elementAt(0);
							extractNextUrl1=linkTag.extractLink();
							u=extractNextUrl1;
							LinkCollection.addUnvisitedUrl(extractNextUrl1,1);
							parser.reset();
						}
						
						} catch (ParserException e) {
							System.out.println("��ȡ��ַʧ�ܣ�");
					    }
						
				}
			}else{
				while(bCoun>0){
					String extractNextUrl1 = null;
					Parser parser;
					System.out.println("����: "+u);
					try {
						parser = new Parser(u);
						parser.setEncoding(encond);
						NodeFilter filter=new LinkRegexFilter(pageSign);
						NodeList list=parser.extractAllNodesThatMatch(filter);
						if(list.size()==0){
							System.out.println("��ȡ��һ����ַʧ�ܣ�");
							break;
						}else{
							LinkTag linkTag=(LinkTag)list.elementAt(0);
							extractNextUrl1=linkTag.extractLink();
							u=extractNextUrl1;
							collection.addUnvisitedUrl(extractNextUrl1,1);
						}
						parser.reset();
							
						} catch (ParserException e) {
							System.out.println("��ȡ��ַʧ�ܣ�");
						}
					bCoun--;
				}
				
			}
			
		}//for
	
		
	}
}

