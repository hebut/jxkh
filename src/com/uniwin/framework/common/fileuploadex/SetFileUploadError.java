package com.uniwin.framework.common.fileuploadex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class SetFileUploadError {
	public static void SetErrorToConf(String Error,String Path,String Key){
		Properties properties=new Properties();
		try {
			FileInputStream fis=new FileInputStream(Path);
			try {
				byte[] errorGBKByte=Error.getBytes("GBK");
				String errorGBK=new String(errorGBKByte);
				properties.load(fis);
				fis.close();
				properties.setProperty(Key, errorGBK);
				PrintStream ps=new PrintStream(Path);
				properties.list(ps);
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static String GetErrorFromConf(String Path){
		String error="";
		Properties properties=new Properties();
		try {
			FileInputStream fis=new FileInputStream(Path);
			try {
				properties.load(fis);
				error=new String(properties.getProperty("fileTooLarge").getBytes("ISO8859-1"),"GBK");
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return error;
	}
}
