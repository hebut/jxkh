package com.iti.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

/**
 * ���ܣ��ļ����أ���ֹ��ͬ��������س����ļ�����������
 * @author liujianbo
 * 
 */
public class DownLoadFile {
	// log
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DownLoadFile.class);
	/**
	 * <li>�������������ظ���
	 * @param path 
	 *         ����·��
	 * @author liujianbo
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 * @2011-8-10
	 */
	public static void onDownfile(String path) throws UnsupportedEncodingException, FileNotFoundException, InterruptedException {
		String fname="";
		if(path.length()>0){
			int len = path.length();
			int last= path.lastIndexOf("/");
			fname=path.substring(last+1, len);
			
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();  
			if (request.getHeader("User-Agent").indexOf("MSIE") != -1) {//IE  
				fname = URLEncoder.encode(fname, "UTF-8");  
			} else {// FireFox  
				fname = MimeUtility.encodeText(fname, "GBK", "B");  
			}  
			File f = new File(path);
			if(f.exists()){
				Filedownload.save(new FileInputStream(path), "application/octet-stream", fname);
			}else{
				Messagebox.show("�����ص��ļ��Ѿ��������ˣ�����ѯϵͳ����Ա��", "������ʾ", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}	
}
