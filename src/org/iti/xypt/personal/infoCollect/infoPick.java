package org.iti.xypt.personal.infoCollect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.zkoss.util.logging.Log;
import org.zkoss.zhtml.Pre;




public class infoPick {
	
	
	public synchronized String[] extractByTags(String path,String encond,WkTExtractask extractask,List<WkTPickreg> pList,WkTGuidereg guidereg){
		
//		Log log=Log.lookup(infoPick.class);
		
		WkTPickreg pReg;
		String[] extract=new String[pList.size()];
		String regBegin,regEnd;
		String source = null;
		Integer count=pList.size();
		
//			Parser parser = null;
		/*try {
			parser = new Parser(path);
			parser.setEncoding(encond);
			NodeList list;
			list = parser.parse(null);
			source=list.toHtml();*/
		HttpClientSource clientSource=new HttpClientSource();
	    source =clientSource.AnalySource(path, encond);

		if(source!=null){
			Pattern pattern = Pattern.compile("\r|\n");
			Matcher matcher=pattern.matcher(source);
			source=matcher.replaceAll("");
		}else{
			return null;
		}
		//内容处理范围
		String conScopeBegin=guidereg.getKgConbegin();
		String conScopeEnd=guidereg.getKgConend();
		if(((conScopeBegin!=null && !conScopeBegin.equals(""))|| (conScopeEnd!=null && !conScopeEnd.equals(""))) && guidereg.getKgLevel().equals("true")){
			
			Integer begin,end;
			if(conScopeBegin!=null && conScopeEnd==null){
				begin=source.indexOf(conScopeBegin);
				end=source.length();
			}else if(conScopeBegin==null && conScopeEnd!=null){
				begin=0;
				end=source.indexOf(conScopeEnd);
			}else{
				begin=source.indexOf(conScopeBegin);
				end=source.indexOf(conScopeEnd,begin+conScopeBegin.length());
			}
			if(begin==-1 || end==-1){
				source=null;
				System.out.println("内容提取范围标志错误！");
			}else{
				begin=begin+conScopeBegin.length();
				source=source.substring(begin,end);
			}
		}
		String downImage;
		String oVal,nVal;
		for(int t=0;t<pList.size();t++){
			
			pReg=(WkTPickreg)pList.get(t);
			regBegin=pReg.getKpRegbegin();
			regEnd=pReg.getKpRegend();
			downImage=pReg.getKpLoadimag();//图片
			oVal=pReg.getKpOldvalue();
			nVal=pReg.getKpNewvalue();
			
			Integer beg=source.indexOf(regBegin);
			Integer e=source.indexOf(regEnd,beg+regBegin.length());
			String result = null;
			if(beg!=-1 && e!=-1 && (beg<e)){
			 
			 beg=beg+regBegin.length();
			 result=source.substring(beg, e);
			
			}else{
			  System.out.println(path+pReg.getKpRegname()+" 规则采集失败！");
			}
		if(result==null || result.equals("")|| result.equals(null)){
				extract[t]="";
				count--;
		}else{
		
			if(pReg.getKpRetainTags()!=null){
			
//			List<String> tagList=retainTags(result);
			String tagSource=pReg.getKpRetainTags().replace("false,", "").trim();
			HtmlTags htmlTags=new HtmlTags();
			List<String> tList=htmlTags.analyTags(tagSource);
			
			StringBuffer sb=new StringBuffer();
			String sourceTag;
			for(int a=0;a<tList.size();a++){
				sourceTag=(String) tList.get(a);
				sb.append("(?!(?i)");
				sb.append(sourceTag);
				sb.append("|/(?i)");
				sb.append(sourceTag);
				sb.append(")");
			}
			String regExp="<"+sb.toString()+"[^>]*>";
			extract[t]=result.replaceAll(regExp, "");
			
			if(oVal!=null || nVal!=null){
				extract[t]=extract[t].replace(oVal, nVal);
			}
			/*String sourceTag,dataTag;
			for(int a=0;a<tList.size();a++){
				sourceTag=(String) tList.get(a);
				for(int b=0;b<tagList.size();b++){
					dataTag=(String) tagList.get(b);
					if(sourceTag.trim().equals(dataTag.trim())){
						
						if(sourceTag.equals("IMG") && downImage.equals("true") && extractask.getKePubtype()==Long.parseLong("1")){
							
							result=showContentPic(result,extractask,encond,path);
						}
						tagList.remove(b);
					}
				}
			}
			
			extract[t]=removeTag(result, tagList);*/
			
		}else{
			
			
			result=result.replaceAll("\t|\n|\r", "");
			result=result.replaceAll("</?[^>]+>", "");
			extract[t]=result;
			if(oVal!=null || nVal!=null){
				extract[t]=extract[t].replace(oVal, nVal);
			}
			
			Integer totalLen=source.length();
			if((e+regEnd.length()<source.length())){
				source=source.substring(e+regEnd.length(),totalLen);
			}
		
		
	}
  }
		
}
			if(count==0){
				return null;
			}else{
				return extract;
			}
		
		/*} catch (ParserException e3) {
			e3.printStackTrace();
		}
			
			
		return null;*/
			
}
	
	/*图片显示处理*/
	private String showContentPic(String result, WkTExtractask extractask,
			String encond, String path) {
		URL url;
	try {	
		Parser parser=new Parser(result);
		parser.setEncoding(encond);
		NodeFilter filter=new NodeClassFilter(ImageTag.class);
		NodeList sourceList=parser.parse(null);
		NodeList list=sourceList.extractAllNodesThatMatch(filter,true);
		
		if(list.size()>0){
		
			ImageTag iTag;
			for(int l=0;l<list.size();l++){
				iTag=(ImageTag)list.elementAt(l);
			try {
				url=new URL(new URL(path),iTag.getImageURL());
				String httpUrl=url.toString();
		        iTag.setImageURL(httpUrl);
		         
			} catch (MalformedURLException e) {
				System.out.println("网站图片相对网址解析错误！");
			}
			}//for
			result=sourceList.toHtml();
		}
		
	} catch (ParserException e) {
		System.out.println("源码不能解析！");
	}
		return  result;
}
	
	
	/* 图片下载并转化路径 */
	private String imageLocation(String result,WkTExtractask extractask,String encond,String path){
		
		URL url;
		String imagUrl;
		
		try {
			
			String filePath = "F:\\采集图片\\"+extractask.getKeName()+"-"+extractask.getKeId()+"\\";
			File folder=new File(filePath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			Parser parser=new Parser(result);
			parser.setEncoding(encond);
			NodeFilter filter=new NodeClassFilter(ImageTag.class);
			NodeList sourceList=parser.parse(null);
			NodeList list=sourceList.extractAllNodesThatMatch(filter,true);
			
			if(list.size()>0){
			
				ImageTag iTag;
			for(int l=0;l<list.size();l++){
				iTag=(ImageTag)list.elementAt(l);
				try {
//					url=new URL(new URL(path),iTag.getImageURL());
					imagUrl=iTag.getImageURL();
					url=new URL(imagUrl);
					
					String httpUrl=url.toString();
					String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
					
					BufferedInputStream in= new BufferedInputStream(url.openStream());
				    FileOutputStream file= new FileOutputStream(new File(filePath+fileName));
			           int t;
			           while ((t = in.read()) != -1) {
			              file.write(t);
			           }
			           file.close();
			           in.close();
			           iTag.setImageURL(filePath+fileName);
			           System.out.println("图片获取成功");
			         
				} catch (MalformedURLException e) {
					System.out.println("网站图片相对网址解析错误！");
				} catch (IOException e) {
					System.out.println("图片存储位置错误！");
				}
			}//for
				result=sourceList.toHtml();
		  }
			
		} catch (ParserException e) {
			System.out.println("源码不能解析！");
		}
		return  result;
		
	}
	
	
	/* 提取符合标记的标签 */
	private synchronized List<String> retainTags(String source){
		
		final List<String> tagList=new ArrayList<String>();
		try {
			Parser parser=new Parser(source);
			NodeVisitor visitor=new NodeVisitor(){
				public void visitTag(Tag tag){
					if(!tagList.contains(tag.getTagName())){
						tagList.add(tag.getTagName());
					}
				}
			};
			parser.visitAllNodesWith(visitor);
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return tagList;
	}
	
	//此方法需要改进
	private synchronized String removeTag(String source,List<String> tagList){
		String tag;
		for(int i=0;i<tagList.size();i++){
			tag=tagList.get(i);
			source=source.replaceAll("(<"+tag+"[^>]*>|</"+tag+">)","").trim();
			source=source.replaceAll("(<"+tag.toLowerCase()+"[^>]*>|</"+tag.toLowerCase()+">)","").trim();
		}
		return source;
	}
	
}
