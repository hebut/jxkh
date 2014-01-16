package org.iti.jxkh.waterprint;

import java.io.File;

import com.iti.common.util.UploadUtil;

public class Test {
	public static void main(String[] args) {
		Watermark w = new Watermark();
		// w.markImage();
		w.markImage("c:/hebut.png", "d:/demo/中南大学专业分流流程.jpg",
				"d:/demo1/流流程.jpg", -30);

		String filePath = "";
		try {
			filePath = UploadUtil.getRealPath("/jxkh/writing/1/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file1 = new File(filePath);
		if (file1.exists()) {
			File[] fileArray = file1.listFiles();
			 for(File file : fileArray){
				 System.out.println(file.getName());
				 System.out.println(file.getPath());
			 }
		}
	}
}
