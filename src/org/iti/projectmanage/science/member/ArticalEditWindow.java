package org.iti.projectmanage.science.member;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.ArchiveService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Fileupload;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;


public class ArticalEditWindow extends Window implements AfterCompose 
{
	private static final long serialVersionUID = -8709990417721577437L;
	WkTUser user;
	ArticalListWindow articallistWindow;//参考文献列表窗口，用于调用其初始化方法，从而刷新参考文献列表
	GhXm xm;
	private GH_ARCHIVE archive;
	private Label projectName,filename;
	private Textbox title,source,author,keywords,fundNo,clc,issue;
	private Listbox sourceType,postType;
	private Datebox publishTime;
	private FCKeditor readFeel;
	private Button upload,download,delete;
	private Toolbarbutton save;
	private ArchiveService archiveService;
	Media media;
		
	public void afterCompose() 
	{
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//获取当前登录用户		
	}
	
	/**
	 * 初始化参考文献编辑窗口
	 */
	public void initWindow()
	{
		projectName.setValue(xm.getKyMc());
		title.setValue(archive.getArName());
		source.setValue(archive.getArSource());
		sourceType.setSelectedIndex(archive.getArSourceType()-1);
		source.setValue(archive.getArSource());
		author.setValue(archive.getArAuthor());
		keywords.setValue(archive.getArKeyWord());
		fundNo.setValue(archive.getArFundNum());
		postType.setSelectedIndex(archive.getArType());
		clc.setValue(archive.getArCLC());
		issue.setValue(archive.getArIssue());
		publishTime.setValue(DateUtil.getDate(archive.getArPostDate()));
		readFeel.setValue(archive.getArReadFeel());
		//参考文献记录中的文献来源字段内容不为空，且该内容指定的文件存在，则显示该字段内容，
		//并将上传按钮设为不可见（即该部分只支持上传单个附件），将下载和删除按钮设为可见
		if(null!=archive.getArPath()&&!("".equals(archive.getArPath())))
		{
			//参考文献的存储路径(包括文件名)，具体为：存储跟目录+项目ID+文件名
			String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
				+ File.separator+xm.getKyId() + File.separator + archive.getArPath();
			File archiveFile = new File(archivePath);
			if(archiveFile.exists())
			{
				filename.setValue(archive.getArPath());
				upload.setVisible(false);
				download.setVisible(true);
				delete.setVisible(true);
			}
		}
		//如果当前登录用户为该参考文献的上传者，或者是该参考文献所属项目的负责人，则具有该文献记录的编辑
		//和附件的上传、删除功能；否则，保存按钮不可见，附件的删除和上传按钮不可见
		if(!user.getKuId().equals(archive.getArUpUserId())&&!user.getKuId().equals(xm.getKuId()))
		{
			save.setVisible(false);
			delete.setVisible(false);
			upload.setVisible(false);
			this.setTitle("查看参考文献");
		}
	}
	
	/**
	 * 调用ZK上传组件，用saveFile()方法将上传的文件复制到服务器
	 */
	public void onClick$upload()
	{
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");//windows操作系统中.txt文件的默认编码方式为GBK，此处若设为其他，上传.txt文件易出现乱码
		try {
			media = Fileupload.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(null==media)
		{
			return;
		}
		String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
			+ File.separator+xm.getKyId();//参考文献的存储路径，具体为：存储跟目录+上传者ID
		File pathDir = new File(archivePath);
		if(!(pathDir.exists()))
		{
			boolean isCreate = pathDir.mkdirs();
			if(isCreate==false)
			{
				try {
					Messagebox.show("参考文献存储路径创建失败！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		String mediaName =  media.getName(); 
		String path = archivePath+ File.separator +mediaName;
		File archiveFile = new File(path);
		if(archiveFile.exists())
		{
			try {
				if(Messagebox.NO==Messagebox.show("已存在同名的参考文献，是否继续上传？", "确认", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
				{
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String fileNum = pathDir.listFiles().length+1+"";
			title.setValue("("+fileNum+")"+mediaName);
			filename.setValue("("+fileNum+")"+mediaName);
			//如果存在同名的文件，则将当前文件名前加上"(所在文件夹下文件的个数+1)"
			path = archivePath + File.separator+"("+fileNum+")"+mediaName;
		}else{
			title.setValue(media.getName().substring(0,media.getName().lastIndexOf(".")));
			filename.setValue(media.getName());
		}
		delete.setVisible(true);
		upload.setVisible(false);
	}
	
	/**
	 * 是否删除参考文献的附件
	 */
	public void onClick$delete()
	{
		try {
			if(Messagebox.NO==Messagebox.show("是否确定删除该文件", "确认", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
			{
				return;
			}
			title.setRawValue("");
			filename.setValue("");
			upload.setVisible(true);
			download.setVisible(false);
			delete.setVisible(false);
			media = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存用户对当前文献记录的修改，然后刷新参考文献列表
	 */
	public void onClick$save()
	{
		
		try {
			if(null==filename.getValue()||"".equals(filename.getValue()))
			{
				if(Messagebox.NO==Messagebox.show("您尚未上传新的参考文献，是否恢复原有参考文献？", "确认", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION)){
					return;
				}else{
					title.setValue(archive.getArName());
					String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
						+ File.separator+xm.getKyId() + File.separator + archive.getArPath();
					File archiveFile = new File(archivePath);
					if(archiveFile.exists())
					{
						filename.setValue(archive.getArPath());
						upload.setVisible(false);
						download.setVisible(true);
						delete.setVisible(true);
					}
				}
			}
			if(readFeel.getValue().length()>254)
			{
				Messagebox.show("阅读感想内容过长，请重新编辑！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String basePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive");
		String formerPath = basePath + File.separator+xm.getKyId() + File.separator + archive.getArPath();
		archive.setArAuthor(author.getValue().trim());
		archive.setArFundNum(fundNo.getValue().trim());
		archive.setArKeyWord(keywords.getValue().trim());
		archive.setArName(title.getValue().trim());
		archive.setArPath(filename.getValue());
		archive.setArPostDate(DateUtil.getDateString(publishTime.getValue()));
		archive.setArProId(xm.getKyId());
		archive.setArReadFeel(readFeel.getValue().trim());
		archive.setArSource(source.getValue().trim());
		archive.setArSourceType(sourceType.getSelectedIndex()+1);
//		archive.setArThema("")	//备用字段
		archive.setArType(postType.getSelectedIndex());
		archive.setArCLC(clc.getValue().trim());
		archive.setArIssue(issue.getValue().trim());
		archive.setArCategory(GhXm.KYXM);
		archive.setArUpUserId(user.getKuId());
		String archivePath =basePath + File.separator+xm.getKyId() + File.separator + filename.getValue();
		File archiveFile = new File(archivePath);
		if(archiveFile.exists())//若服务器中上传的参考文献附件存在，则将文献的上传日期设为该文件的最终修改日期
		{
			archive.setArUpTime(DateUtil.getDateTimeString(new Date(archiveFile.lastModified())));
		}else{
			archive.setArUpTime(DateUtil.getDateTimeString(new Date()));
		}
		try{
			if(null!=media)
			{
				if(!deleteFile(formerPath))
				{
					Messagebox.show("原有参考文献附件删除失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				saveFile(media);
			}
			archiveService.update(archive);
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){
			try {
				Messagebox.show("参考文献保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		articallistWindow.initWindow();
		this.detach();
	}
	
	/**
	 * 关闭当前窗口。若参考文献附件已被删除，强行关闭该窗口将删除当前文献记录
	 */
	public void onClick$close()
	{
		this.detach();
	}
	
	/**
	 * 将上传文件复制到服务器指定的目录下
	 * @param media ZK上传组件获得的实体
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void saveFile(Media media) throws IOException, InterruptedException
	{
		if(null==media)
		{
			return;
		}
		String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
			+ File.separator+xm.getKyId() ;//参考文献的存储路径，具体为：存储跟目录+项目ID
		String mediaName =  filename.getValue(); 
		String path = archivePath+ File.separator +mediaName;
		/*
		 *说明：
		 *1、后台读取上传内容的方法有以下四个：getStreamData(),getString(),getStringReader(),getByteData() 
		 *2、根据isBinary()和isMemory()的返回值选择以上四个方法。
		 */
		if(media.isBinary())
		{
			InputStream ins = media.getStreamData();
			FileOutputStream fos = new FileOutputStream(path);
			byte[] buf = new byte[1024];
			int len;
            while ((len = ins.read(buf)) > 0){
                fos.write(buf, 0, len);
            }
            ins.close();
            fos.close();

		}else if(media.inMemory()){
			 String str = media.getStringData();
	         FileOutputStream fos=new FileOutputStream(path);
	         OutputStreamWriter osw=new OutputStreamWriter(fos);
	         BufferedWriter bw=new BufferedWriter(osw);
	         bw.write(str);
	         bw.close();
		}		
	}
	
	/**
	 * 下载参考文献附件
	 */
	public void onClick$download()
	{
		String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
			+ File.separator+xm.getKyId() + File.separator + filename.getValue();
		File archiveFile = new File(archivePath);
		try 
		{
			if(!archiveFile.exists())
			{
				Messagebox.show("参考文献已经被删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			Filedownload.save(archiveFile,null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除当前上传的参考文献附件
	 * @return isDel
	 */
	public boolean deleteFile(String archivePath)
	{
		File archiveFile = new File(archivePath);
		boolean isDel = false;
		if(archiveFile.exists())
		{
			isDel = archiveFile.delete();
		}else{
			isDel = true;
		}
		return isDel;
	}
	
	public ArticalListWindow getArticallistWindow() {
		return articallistWindow;
	}

	public void setArticallistWindow(ArticalListWindow articallistWindow) {
		this.articallistWindow = articallistWindow;
	}

	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public GH_ARCHIVE getArchive() {
		return archive;
	}

	public void setArchive(GH_ARCHIVE archive) {
		this.archive = archive;
	}
}