package com.uniwin.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class FileUtil {

	/**
     * <li>����������д�ļ�
     * @author bobo
     * @serialData 2010-7-23
     * 
     * @param  path      ·��
     * @param  source    Դ�ļ�
     * @param  filepre   Ŀ���ļ�
     * @return null
     */
	public static  void writeFile(String path,String source,String filepre) {
		try {
			   BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
	                    new FileOutputStream(path+"\\"+filepre), "UTF-8"));
			   
	           out.write(source);
	           out.close();	
		} catch (FileNotFoundException e) {
			  e.printStackTrace();
		} catch (IOException e) {
			  e.printStackTrace();
		}// ���ֽ�һ����д���ļ�
	}
	
	public static void writeUTF8File(String path,String source,String filepre){
		try{
			FileOutputStream fos = null;           
			OutputStreamWriter osw = null;               
			fos = new FileOutputStream(path+"\\"+filepre);               
			osw = new OutputStreamWriter(fos, "UTF-8");               
			osw.write(source);  
		}catch (FileNotFoundException e) {
			  e.printStackTrace();
		} catch (IOException e) {
			  e.printStackTrace();
		}
		         
	}
	
	/**
     * <li>���������������ļ���
     * @author bobo
     * @serialData 2010-7-22
     * @param  baseDir      ·��
     * @return null
     */
	public static void mkHtmlFolder(String baseDir){
		if(baseDir == null){
		    System.out.println("�޷����ʴ洢Ŀ¼��");
		    return;
	    }		    
	    File fUploadDir = new File(baseDir);	
	    if(!fUploadDir.exists()){
			    if(!fUploadDir.mkdir()){
			    	   System.out.println("�޷������洢Ŀ¼��");      //���upload�ļ��в����ڣ����ڴ������ļ���
				       return;
			    }			
	     }	     
	}
	
	/**
     * <li>������������ȡ�ļ�����
     * @author bobo
     * @serialData 2010-7-23
     * @param  path         �ļ����·��
     * @return sb.toString()�ַ���
     */
	public static String readUTF8(String path){ 		
        StringBuffer sb=new StringBuffer("");   
        try{    
        	   BufferedReader br = new BufferedReader(new InputStreamReader(
   	                new FileInputStream(path), "UTF-8"));
            String s = null;   
            while((s = br.readLine()) != null) {   
                sb.append(s+'\n');   
            }   
            br.close();   
       }catch(Exception e){   
            e.printStackTrace();   
       }   
        return sb.toString();   
    }

	/**
     * <li>������������ȡ�ļ�����
     * @author bobo
     * @serialData 2010-7-23
     * @param  path         �ļ����·��
     * @return sb.toString()�ַ���
     */
	public static String readGBK(String path){ 		
        StringBuffer sb=new StringBuffer("");   
        try{    
        	   BufferedReader br = new BufferedReader(new InputStreamReader(
   	                new FileInputStream(path), "GBK"));
            String s = null;   
            while((s = br.readLine()) != null) {   
                sb.append(s+'\n');   
            }   
            br.close();   
       }catch(Exception e){   
            e.printStackTrace();   
       }   
        return sb.toString();   
    }   

	/** 
	* <li>����������д�ļ� 
    * @author bobo
    * @serialData 2010-7-22
    * 
	* @param path �ļ���·�� 
	* @param content д���ļ������� 
	*/ 
	public static void writerText(String path,String content){ 
		try { 
			BufferedWriter bw1=new BufferedWriter(new FileWriter(path)); 
			bw1.write(content); 
			bw1.flush(); 
			bw1.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}

	
	/**
	   * ɾ��ָ���ļ���
	   * @author bobo
	   * @serialData 2010-7-28
	   * 
	   * @param path 
	   *        �ļ���·��
	   */
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //ɾ����������������
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //ɾ�����ļ���
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	 /**
	   * ɾ��ָ���ļ����������ļ�
	   * @author bobo
	   * @serialData 2010-7-28
	   * 
	   * @param path 
	   *        �ļ���·��
	   */	
	   public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	           return flag;
	       }
	       if (!file.isDirectory()) {
	            return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
	             delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
	             flag = true;
	          }
	       }
	       return flag;
	     }

	   /**
	    * ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
		* @param path ͼƬ·��������ͼƬ���ƣ���"d:\\111.jpg"
		* @return ͼƬ�ļ���BASE64����
	    */
	   public static String getImageStr(String path)
	   {
		   InputStream in = null;
		   byte[] data = null;
		   try 
	        {
	            in = new FileInputStream(path);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        //���ֽ�����Base64����
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//����Base64��������ֽ������ַ���
	   }
	   
	   /**
	    * ���ֽ������ַ�������Base64���벢����ͼƬ
	    * @param imgStr ͼƬ��Base64�����ֽ������ַ���
	    * @param targetPath ����ͼƬ��·��������ͼƬ���ƣ���"d:\\111.jpg"
	    * @return �Ƿ����ɹ�
	    */
	   public static boolean GenerateImage(String imgStr,String targetPath)
	    {
	        if (imgStr == null) //ͼ������Ϊ��
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	            //Base64����
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//�����쳣����
	                    b[i]+=256;
	                }
	            }
	            //����jpegͼƬ
	            OutputStream out = new FileOutputStream(targetPath);    
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } 
	        catch (Exception e) 
	        {
	            return false;
	        }
	    }

}