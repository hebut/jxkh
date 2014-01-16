package org.iti.xypt.personal.infoCollect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;



public class HttpClientSource {

	
	public  /*String*/static void main(String[] args)  /*AnalySource(String path,String encond)*/{
		
		String path="http://www.cutech.edu.cn/cn/kyjj/gdxxbsdkyjj/2008/12/1229477774472191.htm";
		String encond="gbk";
		if(path!=null){
			
		HttpClient httpClient=new HttpClient();
		
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encond);
    	try {
    		
    		httpClient.setConnectionTimeout(150000);
		} catch (Exception e) {
			System.out.println(path+" ���ӳ�ʱ��");
		}
		
    	String htmlRet="";  
    	HttpMethod get=new GetMethod(path);

		try {
		            httpClient.executeMethod(get);

		            BufferedReader reader=new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(),encond));
		            String tmp=null;
		                     
		            while((tmp=reader.readLine())!=null){
		                htmlRet+=tmp;
		}


		} catch (HttpException e) {
		            e.printStackTrace();
		} catch (IOException e) {
		            e.printStackTrace();
		}finally{
		            get.releaseConnection();
		}
//		htmlRet=htmlRet.replace("&nbsp;", "").trim();
		System.out.println(htmlRet);
//    		return htmlRet;
    	
		}
//		return null;
		
		
	}
	
	
public  String AnalySource(String path,String encond){
		
		if(path!=null){
			
		HttpClient httpClient=new HttpClient();
		
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encond);
    	try {
    		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		} catch (Exception e) {
			System.out.println(path+" �������ӳ�ʱ��");
		}
		
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);
    	
		HttpMethod get = null;
		try {
			get=new GetMethod(path);
		} catch (Exception e) {
			System.out.println("��ַ���Ϸ���");
			return null;
		}
    	get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
    	
    	String htmlRet=null;
    	
			try {
		         int statusCode=httpClient.executeMethod(get);
		         if(statusCode==HttpStatus.SC_OK){
		        	 
		            BufferedReader reader=new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(),encond));
		            String tmp=null;
		                     
		            while((tmp=reader.readLine())!=null){
		                htmlRet+=tmp;
		            }
		            
		            reader.close();
		         }else{
		        	 System.out.println("״̬�룺 "+statusCode);
		         }

			} catch (HttpException e) {
				System.out.println("��ַ��Э�鲻��ȷ��");
			} catch (IOException e) {
				System.out.println("���������쳣");
			}finally{
				get.releaseConnection();
			}

    		return htmlRet;
    	
		}
		return null;
		
	}
	
	
}
