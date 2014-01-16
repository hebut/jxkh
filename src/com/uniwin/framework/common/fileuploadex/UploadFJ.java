package com.uniwin.framework.common.fileuploadex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.iti.gh.entity.GhFile;
import org.iti.gh.service.GhFileService;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.data.struct.MediaUploadPackage;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;

public class UploadFJ {
	WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	Media media = null;
	String urlPath = "";
	Integer xmType = 0;
	String path = "";
	File file = null;
	GhFile gf = null;
	Jxkh_HonourFile honourFile = null;
	
	public GhFile getGf() {
		return gf;
	}

	public void setGf(GhFile gf) {
		this.gf = gf;
	}

	String realPath = "";
	String confPath = "";
	private String uploadFolder="";
	GhFileService ghfileService = (GhFileService) BeanFactory.getBean("ghfileService");
	Desktop deskTop = null;
	boolean isNew = false;// true:add a new file;
	int r = 0;

	public File getFile() {
		return file;
	}

	/**
	 * 
	 * @param isNew
	 *            �Ƿ�Ϊ�½�����
	 */
	public UploadFJ(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * 
	 * @param deskTop
	 * @param xmType
	 * @param maxSize
	 * @param fileTooLargeError
	 * @param dlgTitle
	 */
	private void Init(Desktop deskTop, Integer xmType, Integer maxSize,
			String fileTooLargeError, String dlgTitle,String uploadFolder) {
		this.deskTop = deskTop;
		// ��������
		this.xmType = xmType;
		// ���ø�����С
		deskTop.getWebApp().getConfiguration().setMaxUploadSize(
				maxSize.intValue() * 1024);
		// �����ļ�·��
		confPath = (Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties")
				.trim();
		SetFileUploadError.SetErrorToConf(fileTooLargeError, confPath,
				"fileTooLarge");
		// �����ļ�url·��
		this.uploadFolder=uploadFolder;
		this.urlPath = ("/upload/"+this.uploadFolder+"/" + XmTypeConvert(xmType) + "/").trim();
		realPath = deskTop.getWebApp().getRealPath(urlPath).trim();
				
	}

	/**
	 * �����ϴ�
	 * 
	 * @param deskTop
	 *            this.getDesktop()
	 * @param xmType
	 *            ����
	 * @param maxSize
	 *            �����ߴ�,��λMB
	 * @param fileTooLargeError
	 *            �����������
	 * @param dlgTitle
	 *            �ϴ����ڱ���
	 */
	public Media Upload(Desktop deskTop, Integer xmType, Integer maxSize,
			String fileTooLargeError, String dlgTitle) {
		return Upload(deskTop,xmType,maxSize,fileTooLargeError,dlgTitle,"xkjs");
	}
	/**
	 * �����ϴ�
	 * 
	 * @param deskTop
	 *            this.getDesktop()
	 * @param xmType
	 *            ����
	 * @param maxSize
	 *            �����ߴ�,��λMB
	 * @param fileTooLargeError
	 *            �����������
	 * @param dlgTitle
	 *            �ϴ����ڱ���
	 * @param uploadFolder
	 * 				��������uploadĿ¼���ļ���
	 * @return
	 */
	public Media Upload(Desktop deskTop, Integer xmType, Integer maxSize,
			String fileTooLargeError, String dlgTitle,String uploadFolder) {

		this.Init(deskTop, xmType, maxSize, fileTooLargeError, dlgTitle,uploadFolder);
		// �����ϴ�����
		try {
			MediaUploadPackage mUP = Fileupload.get_MUP("�����ϴ�------�ļ���С������"+ maxSize.intValue() + "MB");
			if (mUP.getIsCancel()) {
				return null;
			}
			if (mUP.getMedia0() != null) {
				media = mUP.getMedia0();
				r = (new Random()).nextInt();
				path = (realPath + "\\" + "_" + r + "_" + media.getName())
						.trim();
				file = new File(path);
				if (file.exists()) {// �ļ�����
					Messagebox.show("�ļ�����" + media.getName()
							+ "�Ѿ�����,��ѡ�������������޸��ļ�����", "��ʾ", Messagebox.OK,
							Messagebox.EXCLAMATION);
					return null;
				}
			} else {
				Messagebox.show("�ļ������ڣ�", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return null;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		return media;
	}

	/**
	 * 
	 */
	public String toString() {
		if (this.media == null) {
			if (this.gf == null) {
				return null;
			} else {
				String path = deskTop.getWebApp().getRealPath(
						this.gf.getfPath());
				this.file = new File(path);
				return NameFilter(file.getName());
			}
		} else {
			return this.media.getName();
		}
	}

	/**
	 * ��ʼ������ʱ��ȡ������Ϣ
	 * 
	 * @param deskTop
	 * @param gf
	 * @throws InterruptedException
	 */
	public void ReadFJ(Desktop deskTop, GhFile gf) throws InterruptedException {
		this.ReadFJ(deskTop, gf,"xkjs");
	}
	/**
	 * 
	 * @param deskTop
	 * @param gf
	 * @throws InterruptedException
	 */
	public void ReadFJ(Desktop deskTop, Jxkh_HonourFile honourFile) throws InterruptedException {
		this.ReadFJ(deskTop, honourFile,"xkjs");
	} 
	/**
	 * ��ʼ������ʱ��ȡ������Ϣ
	 * 
	 * @param deskTop
	 * @param gf
	 * @param uploadFolder
	 * @throws InterruptedException
	 */	
	public void ReadFJ(Desktop deskTop, GhFile gf,String uploadFolder) throws InterruptedException {
		this.Init(deskTop, xmType, 0, "", "",uploadFolder);
		this.gf = gf;
		if (!this.isNew) {
			// ��֤�ļ��洢��������ݿ����ݵ�һ����
			if (this.gf == null) {
				Messagebox.show("�ļ��洢����", "�쳣", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				String path = deskTop.getWebApp().getRealPath(
						this.gf.getfPath());
				File temp = new File(path);
				if (!temp.exists()) {
					// ���ݿ��¼���ļ��洢��һ�£���¼���ڵ��ļ������ڣ�ɾ����¼����ʾ����
					ghfileService.delete(gf);
					Messagebox.show("�ļ��洢����", "�쳣", Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
				this.file = temp;
			}
		}
	}
	/**
	 * 
	 * @param deskTop
	 * @param honourFile
	 * @param uploadFolder
	 * @throws InterruptedException
	 */
	public void ReadFJ(Desktop deskTop, Jxkh_HonourFile honourFile,String uploadFolder) throws InterruptedException {
		this.Init(deskTop, xmType, 0, "", "",uploadFolder);
		this.honourFile = honourFile;
		if (!this.isNew) {
			// ��֤�ļ��洢��������ݿ����ݵ�һ����
			if (this.honourFile == null) {
				Messagebox.show("�ļ��洢����", "�쳣", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				String path = deskTop.getWebApp().getRealPath(
						this.honourFile.getFilePath());
				File temp = new File(path);
				if (!temp.exists()) {
					// ���ݿ��¼���ļ��洢��һ�£���¼���ڵ��ļ������ڣ�ɾ����¼����ʾ����
					ghfileService.delete(honourFile);
					Messagebox.show("�ļ��洢����", "�쳣", Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
				this.file = temp;
			}
		}
	}

	/**
	 * ɾ������
	 */
	public void DeleteFJ() {
		// �ļ�����
		if (file.exists()) {
			// ɾ�������ļ�
			file.delete();
			// ɾ�����ݿ��¼
			ghfileService.delete(this.gf);
		}
	}

	/**
	 * ���渽��
	 * 
	 * @param fxmID
	 *            �ĵ�ID
	 */
	public void SaveToDataBase(Long fxmId) {
		// ����ƶ�Ŀ¼�Ƿ����
		File folderExist = new File(this.realPath);
		if (!folderExist.exists()) {
			folderExist.mkdir();
		}
		//
		if (this.gf == null) {
			// �ļ�·�����
			gf = new GhFile();
			gf.setfPath((urlPath + "_" + r + "_" + media.getName()).trim());
			gf.setxmType(xmType);
			gf.setfxmId(fxmId);
	        gf.setKuid(user.getKuId());
			
	        if(Sessions.getCurrent().getAttribute("check")!=null){
				 Checkbox che =(Checkbox)Sessions.getCurrent().getAttribute("check");
			     if(che.isChecked()){
			    	 gf.setFbackup(1);
			     }else{
			    	 gf.setFbackup(0);
			     }
			     Sessions.getCurrent().removeAttribute("check");
			}else{
				gf.setFbackup(0);
			}
			ghfileService.save(gf);
			// �������д���ļ�
			if (media.getName().endsWith(".txt")
					|| media.getName().endsWith(".project")) {
				// ���txt�ļ�ʹ��������
				Reader reader = media.getReaderData();
				try {
					Files.copy(this.file, reader, null);
					Files.close(reader);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				InputStream ins = media.getStreamData();
				byte[] buf = new byte[1024];
				int len;
				try {
					FileOutputStream out = new FileOutputStream(file);
					while ((len = ins.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.close();
					ins.close();
				} catch (FileNotFoundException e) {
					try {
						Messagebox.show(e.getMessage(), "�쳣", Messagebox.OK,
								Messagebox.EXCLAMATION);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (IOException e) {
					try {
						Messagebox.show(e.getMessage(), "�쳣", Messagebox.OK,
								Messagebox.EXCLAMATION);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �ļ�������
	 * 
	 * @param name
	 * @return
	 */
	private static String NameFilter(String name) {
		String filted = name;
		byte[] temp = null;
		try {
			temp = filted.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int count = 0;
		int i = 0;
		for (; i < temp.length; i++) {
			if (temp[i] == "_".getBytes()[0]) {
				count++;
				if (count == 2)
					break;
			} else
				continue;
		}
		i++;
		int len = temp.length - i;
		byte[] str = new byte[len];
		int j = 0;
		for (; i < temp.length; i++, j++) {
			str[j] = temp[i];
		}
		filted = new String(str);
		return filted;
	}

	/**
	 * ��Ŀ����ת��
	 * 
	 * @param xmType
	 * @return
	 */
	private static String XmTypeConvert(Integer xmType) {
		String type = "";
		switch (xmType) {
		case 1:
			type = "kycg";
			break;
		case 2:
			type = "hjkycg";
			break;
		case 3:
			type = "fbhylw";
			break;
		case 4:
			type = "fbqklw";
			break;
		case 5:
			type = "cbxszz";
			break;
		case 6:
			type = "rjzz";
			break;
		case 7:
			type = "hsqfmzl";
			break;
		case 8:
			type = "wlwnkyjh";
			break;
		case 9:
			type = "qt";
			break;
		case 10:
			type = "jyxm";
			break;
		case 11:
			type = "hjjycg";
			break;
		case 12:
			type = "glpkxyjbgjs";
			break;
		case 13:
			type = "hylwqk";
			break;
		case 14:
			type = "qklwqk";
			break;
		case 15:
			type = "jcqk";
			break;
		case 16:
			type = "jsqk";
			break;
		case 17:
			type = "skqk";
			break;
		case 18:
			type = "yjszsqk";
			break;
		case 19:
			type = "application";//������
			break;
		case 20:
			type = "contract";//��ͬ��
			break;
		case 21:
			type = "interimReport";//���ڱ���
			break;
		case 22:
			type = "projectReport";//�����
			break;
		case 23:
			type = "techContract";//������ͬ��
			break;
		case 24:
			type = "honour";//�����ƺŸ���
			break;
		}
		
		return type;
	}

	/**
	 * �ϴ��б��������ļ�
	 * 
	 * @param fileModel
	 * @param fxmId
	 */
	public static void ListModelListUploadCommand(ListModelList fileModel,Long fxmId) {
		List<?> list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).SaveToDataBase(fxmId);
		}
	}

	public static void UploadCommand(UploadFJ fj, Long fxmId) {
		fj.SaveToDataBase(fxmId);
	}
}
