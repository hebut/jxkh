package com.uniwin.framework.common.fileuploadex;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;

public class DownloadFJ {
	private Filedownload fdl = null;

	
	private List<?> list;

	private Desktop desktop;

	private ZipOutputStream zipOut;
	private int buffSize;
	private byte[] buf;
	private int readedBytes;
	private Long fxmId = 0l;
	private String realPath = "";
	private String uploadFolder="";
	private String zipFileName = "";
	
	private UploadFJ ufj=null;
	/**
	 * 
	 */
	public DownloadFJ() {
		this(null, 0l,"temp", null, 512);
	}

	/**
	 * 附件列表
	 * 
	 * @param list
	 */
	public DownloadFJ(Desktop desktop, Long fxmId,String uploadFolder, ListModelList fileModel,
			int zipBufSize) {
		this.desktop = desktop;
		this.fxmId = fxmId;
		this.list = fileModel.getInnerList();
		this.uploadFolder=uploadFolder;

		this.buffSize = zipBufSize;

		Init();
	}
	
	/**
	 * 单个文件下载
	 * 
	 */
	public DownloadFJ(UploadFJ ufj) {
		this.ufj=ufj;
	}
	private void Init() {
		this.realPath = desktop.getWebApp().getRealPath("/upload/"+this.uploadFolder+"/").trim();
		this.zipFileName = this.realPath + "\\" + "_" + this.fxmId + "_"+ ".zip";
		this.buf=new byte[this.buffSize];
	}

	/**
	 * this.Desktop()
	 * 
	 * @return
	 */
	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	/**
	 * 设置文件列表
	 * 
	 * @param list
	 */
	public void setList(ListModelList list) {
		this.list = list.getInnerList();
	}

	
	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	/**
	 * 设置压缩用缓冲区
	 * 
	 * @return
	 */
	public int getBuffSize() {
		return buffSize;
	}

	public void setBuffSize(int buffSize) {
		this.buffSize = buffSize;
	}
	
	/**
	 * 检测打包文件是否存在
	 * @return
	 */
	private boolean IsZipExists(){
		return IsZipExists.isExists(this.realPath + "\\" + "_" + this.fxmId + "_"+ ".zip");
	}

	/**
	 * 打包压缩文件组
	 * 
	 * @return
	 */
	private boolean DoZip() {
		//SimpleDateFormat sDateFormate=new SimpleDateFormat("yymmddhhmmss");
		try {
			this.zipOut = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFileName)));
			this.zipOut.setEncoding("GBK");
			HandleDir();
			this.zipOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void HandleDir() {
		FileInputStream in = null;
		// File[] files=new File[this.list.size()];
		for (int i = 0; i < this.list.size(); i++) {
			File temp = ((UploadFJ) this.list.get(i)).getFile();
			if (temp.exists()) {
				try {
					in = new FileInputStream(temp);
					String name = new String(temp.getName().getBytes(), "GBK");
					this.zipOut.putNextEntry(new ZipEntry(name));

					while ((this.readedBytes = in.read(this.buf)) > 0) {
						this.zipOut.write(this.buf, 0, this.readedBytes);
					}
					this.zipOut.closeEntry();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					continue;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 下载文件
	 */
	@SuppressWarnings("static-access")
	public void DoDownloadZip() {
		if (!this.IsZipExists()) {
			this.DoZip();
		}
		this.fdl = new Filedownload();
		File zipFile = new File(this.zipFileName);
		if (zipFile.exists()) {
			try {
				this.fdl.save(zipFile, ".zip");
			} catch (FileNotFoundException e) {
				zipFile.delete();
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void DoDownloadSingle(){
		this.fdl=new Filedownload();
		if(this.ufj==null){
			return ;
		}else{
			if(this.ufj.file.exists()){
				try {
					this.fdl.save(this.ufj.file,"*");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				return ;
			}
		}
	}

	public static void ListModelListDownloadCommand(Desktop desktop, Long kuId,
			ListModelList fileModel) {
		(new DownloadFJ(desktop, kuId,"xkjs", fileModel, 512)).DoDownloadZip();
	}
	public static void DownloadCommand(UploadFJ ufj){
		(new DownloadFJ(ufj)).DoDownloadSingle();
	}
}
