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
	 *            是否为新建附件
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
		// 设置类型
		this.xmType = xmType;
		// 设置附件大小
		deskTop.getWebApp().getConfiguration().setMaxUploadSize(
				maxSize.intValue() * 1024);
		// 配置文件路径
		confPath = (Sessions.getCurrent().getWebApp().getRealPath("/") + "/WEB-INF/classes/conf.properties")
				.trim();
		SetFileUploadError.SetErrorToConf(fileTooLargeError, confPath,
				"fileTooLarge");
		// 生成文件url路径
		this.uploadFolder=uploadFolder;
		this.urlPath = ("/upload/"+this.uploadFolder+"/" + XmTypeConvert(xmType) + "/").trim();
		realPath = deskTop.getWebApp().getRealPath(urlPath).trim();
				
	}

	/**
	 * 附件上传
	 * 
	 * @param deskTop
	 *            this.getDesktop()
	 * @param xmType
	 *            类型
	 * @param maxSize
	 *            附件尺寸,单位MB
	 * @param fileTooLargeError
	 *            附件过大错误
	 * @param dlgTitle
	 *            上传窗口标题
	 */
	public Media Upload(Desktop deskTop, Integer xmType, Integer maxSize,
			String fileTooLargeError, String dlgTitle) {
		return Upload(deskTop,xmType,maxSize,fileTooLargeError,dlgTitle,"xkjs");
	}
	/**
	 * 附件上传
	 * 
	 * @param deskTop
	 *            this.getDesktop()
	 * @param xmType
	 *            类型
	 * @param maxSize
	 *            附件尺寸,单位MB
	 * @param fileTooLargeError
	 *            附件过大错误
	 * @param dlgTitle
	 *            上传窗口标题
	 * @param uploadFolder
	 * 				附件所在upload目录下文件夹
	 * @return
	 */
	public Media Upload(Desktop deskTop, Integer xmType, Integer maxSize,
			String fileTooLargeError, String dlgTitle,String uploadFolder) {

		this.Init(deskTop, xmType, maxSize, fileTooLargeError, dlgTitle,uploadFolder);
		// 创建上传窗口
		try {
			MediaUploadPackage mUP = Fileupload.get_MUP("附件上传------文件大小不大于"+ maxSize.intValue() + "MB");
			if (mUP.getIsCancel()) {
				return null;
			}
			if (mUP.getMedia0() != null) {
				media = mUP.getMedia0();
				r = (new Random()).nextInt();
				path = (realPath + "\\" + "_" + r + "_" + media.getName())
						.trim();
				file = new File(path);
				if (file.exists()) {// 文件存在
					Messagebox.show("文件名：" + media.getName()
							+ "已经存在,请选择其他附件或修改文件名！", "提示", Messagebox.OK,
							Messagebox.EXCLAMATION);
					return null;
				}
			} else {
				Messagebox.show("文件不存在！", "提示", Messagebox.OK,
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
	 * 初始化窗口时读取附件信息
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
	 * 初始化窗口时读取附件信息
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
			// 验证文件存储情况与数据库数据的一致性
			if (this.gf == null) {
				Messagebox.show("文件存储错误", "异常", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				String path = deskTop.getWebApp().getRealPath(
						this.gf.getfPath());
				File temp = new File(path);
				if (!temp.exists()) {
					// 数据库记录与文件存储不一致，记录存在但文件不存在，删除记录并提示错误
					ghfileService.delete(gf);
					Messagebox.show("文件存储错误", "异常", Messagebox.OK,
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
			// 验证文件存储情况与数据库数据的一致性
			if (this.honourFile == null) {
				Messagebox.show("文件存储错误", "异常", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				String path = deskTop.getWebApp().getRealPath(
						this.honourFile.getFilePath());
				File temp = new File(path);
				if (!temp.exists()) {
					// 数据库记录与文件存储不一致，记录存在但文件不存在，删除记录并提示错误
					ghfileService.delete(honourFile);
					Messagebox.show("文件存储错误", "异常", Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
				this.file = temp;
			}
		}
	}

	/**
	 * 删除附件
	 */
	public void DeleteFJ() {
		// 文件存在
		if (file.exists()) {
			// 删除物理文件
			file.delete();
			// 删除数据库记录
			ghfileService.delete(this.gf);
		}
	}

	/**
	 * 保存附件
	 * 
	 * @param fxmID
	 *            文档ID
	 */
	public void SaveToDataBase(Long fxmId) {
		// 检测制定目录是否存在
		File folderExist = new File(this.realPath);
		if (!folderExist.exists()) {
			folderExist.mkdir();
		}
		//
		if (this.gf == null) {
			// 文件路径入库
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
			// 向服务器写入文件
			if (media.getName().endsWith(".txt")
					|| media.getName().endsWith(".project")) {
				// 针对txt文件使用特殊流
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
						Messagebox.show(e.getMessage(), "异常", Messagebox.OK,
								Messagebox.EXCLAMATION);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (IOException e) {
					try {
						Messagebox.show(e.getMessage(), "异常", Messagebox.OK,
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
	 * 文件名生成
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
	 * 项目类型转换
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
			type = "application";//申请书
			break;
		case 20:
			type = "contract";//合同书
			break;
		case 21:
			type = "interimReport";//中期报告
			break;
		case 22:
			type = "projectReport";//立项报告
			break;
		case 23:
			type = "techContract";//技术合同书
			break;
		case 24:
			type = "honour";//荣誉称号附件
			break;
		}
		
		return type;
	}

	/**
	 * 上传列表中所以文件
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
