package com.uniwin.framework.common.fileuploadex;

import java.io.File;

public class IsZipExists {
	public static boolean isExists(String path){
		File file=new File(path);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
	public static void delZipFile(String path){
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
	}
}
