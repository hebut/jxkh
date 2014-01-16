package com.uniwin.framework.ui.parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LogViewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File viewFile;
	Textbox logContent;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow(File file) throws FileNotFoundException {
		this.viewFile = file;
		readFileByByte();
	}

	@SuppressWarnings("unused")
	public void readFileByByte() {
		InputStream in = null;
		StringBuffer sb = new StringBuffer("");
		try {
			// 一次读多个字节
			byte[] tempbytes = new byte[1024];
			int byteread;
			in = new FileInputStream(viewFile.getAbsoluteFile());
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				sb.append(new String(tempbytes));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
		logContent.setValue(sb.toString());
	}

	public void readFileByLine() {
		StringBuffer sb = new StringBuffer("");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(viewFile));
			String tempString = null;
			//int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString + "\\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		logContent.setValue(sb.toString());
	}
}
