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
     * <li>功能描述：写文件
     * @author bobo
     * @serialData 2010-7-23
     * 
     * @param  path      路径
     * @param  source    源文件
     * @param  filepre   目的文件
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
		}// 将字节一个个写入文件
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
     * <li>功能描述：创建文件夹
     * @author bobo
     * @serialData 2010-7-22
     * @param  baseDir      路径
     * @return null
     */
	public static void mkHtmlFolder(String baseDir){
		if(baseDir == null){
		    System.out.println("无法访问存储目录！");
		    return;
	    }		    
	    File fUploadDir = new File(baseDir);	
	    if(!fUploadDir.exists()){
			    if(!fUploadDir.mkdir()){
			    	   System.out.println("无法创建存储目录！");      //如果upload文件夹不存在，用于创建该文件夹
				       return;
			    }			
	     }	     
	}
	
	/**
     * <li>功能描述：读取文件内容
     * @author bobo
     * @serialData 2010-7-23
     * @param  path         文件存放路径
     * @return sb.toString()字符串
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
     * <li>功能描述：读取文件内容
     * @author bobo
     * @serialData 2010-7-23
     * @param  path         文件存放路径
     * @return sb.toString()字符串
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
	* <li>功能描述：写文件 
    * @author bobo
    * @serialData 2010-7-22
    * 
	* @param path 文件的路径 
	* @param content 写入文件的内容 
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
	   * 删除指定文件夹
	   * @author bobo
	   * @serialData 2010-7-28
	   * 
	   * @param path 
	   *        文件夹路径
	   */
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	 /**
	   * 删除指定文件夹下所有文件
	   * @author bobo
	   * @serialData 2010-7-28
	   * 
	   * @param path 
	   *        文件夹路径
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
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }

	   /**
	    * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		* @param path 图片路径，包括图片名称，如"d:\\111.jpg"
		* @return 图片文件的BASE64编码
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
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	   }
	   
	   /**
	    * 对字节数组字符串进行Base64解码并生成图片
	    * @param imgStr 图片的Base64编码字节数组字符串
	    * @param targetPath 生成图片的路径，包括图片名称，如"d:\\111.jpg"
	    * @return 是否解码成功
	    */
	   public static boolean GenerateImage(String imgStr,String targetPath)
	    {
	        if (imgStr == null) //图像数据为空
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	            //Base64解码
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            //生成jpeg图片
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