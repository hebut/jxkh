package com.iti.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class UploadUtil {
	// private static String
	// uploadType=PropertiesLoader.getPropertiesValue("uploadType", "upload");
	// private static String
	// uploadPath=PropertiesLoader.getPropertiesValue("uploadPath", "upload");
	// private static String
	// absolutepath=PropertiesLoader.getPropertiesValue("path", "upload");
	// @SuppressWarnings("unused")
	// private static String
	// ftpname=PropertiesLoader.getPropertiesValue("ftpname", "upload");
	// @SuppressWarnings("unused")
	// private static String
	// ftppassword=PropertiesLoader.getPropertiesValue("ftppassword", "upload");
	// @SuppressWarnings("unused")
	// private static String
	// ftpaddress=PropertiesLoader.getPropertiesValue("ftpaddress", "upload");
	/**
	 * 
	 * @param media
	 *            Fileupload.get();
	 * @param relativePath
	 *            ���·��
	 * @param filename
	 *            Ҫ����ļ������磺"ѡ��ָ��" ��ע�ⲻ������չ����
	 * @param fileType
	 *            Ҫ����ļ����� ��"pdf,doc"
	 * @return �ȶ�·����Ŀǰ���ݿ�ֱ�Ӵ��·��
	 * @throws Exception
	 */
	public static String saveFile(Media media, String relativePath, String filename, String fileType) throws Exception {
		String path = null;
		String returnpath = null;
		if (null == media) {
			Messagebox.show("��ȡ�����ϴ��������ϴ�ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return returnpath;
		}
		// if(uploadType.equalsIgnoreCase("http")){
		String filePath;
		// if(uploadPath.equalsIgnoreCase("relative")){
		filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/") + File.separator + relativePath;
		// }
		// else if(uploadPath.equalsIgnoreCase("absolute"))
		// filePath=absolutepath+File.separator+relativePath;
		// else throw new Exception("�����쳣");
		File pathDir = new File(filePath);
		if (!(pathDir.exists())) {
			boolean isCreate = pathDir.mkdirs();
			if (isCreate == false) {
				try {
					Messagebox.show("�洢·������ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return returnpath;
			}
		}
		String ftype = "";
		if ((media.getName() != null) && (media.getName().length() > 0)) {
			int i = media.getName().lastIndexOf('.');
			if ((i > 0) && (i < (media.getName().length() - 1))) {
				ftype = media.getName().substring(i + 1);
			}
		}
		if (!rightType(fileType, ftype)) {
			Messagebox.show("�ϴ��ļ����ʹ���ֻ��Ϊ" + fileType.toString(), "ע��", Messagebox.OK, Messagebox.ERROR);
			return "";
		}
		if (filename == null) {
			path = filePath + File.separator + filename + "." + ftype;
			returnpath = relativePath + File.separator + filename + "." + ftype;
		} else {
			path = filePath + File.separator + filename + "." + ftype;
			returnpath = relativePath + File.separator + filename + "." + ftype;
		}
		/*
		 * ˵����1����̨��ȡ�ϴ����ݵķ����������ĸ���getStreamData(),getString(),getStringReader(),
		 * getByteData()2������isBinary()��isMemory()�ķ���ֵѡ�������ĸ�������
		 */
		if (media.isBinary()) {
			InputStream ins = media.getStreamData();
			FileOutputStream fos = new FileOutputStream(path);
			byte[] buf = new byte[1024];
			int len;
			while ((len = ins.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			ins.close();
			fos.close();
		} else if (media.inMemory()) {
			String str = media.getStringData();
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(str);
			bw.close();
			// }
		}
		// else if(uploadType.equalsIgnoreCase("ftp")){
		// ftp
		// System.out.println("ftp");
		// }
		// else
		// throw new Exception("�����쳣");
		return returnpath;
	}

	public static String getRealPath(String relativePath) throws Exception {
		// if(uploadType.equalsIgnoreCase("http")){
		// if(uploadPath.equalsIgnoreCase("relative"))
		return Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/") + File.separator + relativePath;
		// else if(uploadPath.equalsIgnoreCase("absolute")) return
		// absolutepath+File.separator+relativePath;
		// else {
		// throw new Exception("�����쳣");
		// }
		// }
		// else{
		// return null;
		// }
	}

	public static void deleteFile(String path, String filename) {
		// if(uploadType.equalsIgnoreCase("http")){
		File f = new File(path + "\\" + filename);
		if (f.exists()) {
			f.delete();
		}
		// }
	}

	public static void deleteRelativePathFile(String relativePath, String filename) throws Exception {
		deleteFile(getRealPath(relativePath), filename);
	}

	public static boolean rightType(String fileType, String ftype) {
		if (fileType == null || fileType.length() == 0) {
			return true;
		}
		String[] fileTypes = fileType.split(",");
		for (int i = 0; i < fileTypes.length; i++) {
			if (fileTypes[i].equalsIgnoreCase(ftype)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param media
	 *            Fileupload.get();
	 * @param fileType
	 *            doc/xls...
	 * @author liujianbo
	 */
	public static boolean checkFileType(Media media, String fileType) throws InterruptedException {
		String ftype = "";
		if ((media.getName() != null) && (media.getName().length() > 0)) {
			int i = media.getName().lastIndexOf('.');
			if ((i > 0) && (i < (media.getName().length() - 1))) {
				ftype = media.getName().substring(i + 1);
			}
		}
		if (fileType == null || fileType.length() == 0) {
			return true;
		}
		String[] fileTypes = fileType.split(",");
		for (int i = 0; i < fileTypes.length; i++) {
			if (fileTypes[i].equalsIgnoreCase(ftype)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ܣ���ø�·�������·�����߾���·����
	 * 
	 * @param null
	 * @author liujianbo
	 */
	public static String getRootPath() {
		String filePath = null;
		// if(uploadType.equalsIgnoreCase("http")){
		// if(uploadPath.equalsIgnoreCase("relative")){
		filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/");
		// }else if(uploadPath.equalsIgnoreCase("absolute")) {
		// filePath=absolutepath+File.separator;
		// }
		// }
		return filePath;
	}
}
