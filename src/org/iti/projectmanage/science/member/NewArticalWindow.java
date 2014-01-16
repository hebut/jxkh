package org.iti.projectmanage.science.member;

import java.io.BufferedWriter;
import java.io.File;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;


public class NewArticalWindow extends Window implements AfterCompose 
{

	private static final long serialVersionUID = 1122082183636328315L;
	WkTUser user;
	ArticalListWindow articallistWindow;//参考文献列表窗口，用于调用其初始化方法，从而刷新参考文献列表
	GhXm xm;
	private Label projectName,filename;
	private Textbox title,source,author,keywords,fundNo,clc,issue;
	private Listbox sourceType,postType;
	private Datebox publishTime;
	private FCKeditor readFeel;
	private Button upload,delete;
	private ArchiveService archiveService;
	Media media;
		
	public void afterCompose() 
	{
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//获取当前登录用户
	}
	
	/**
	 * 初始化当前窗口
	 */
	public void initWindow()
	{
		projectName.setValue(xm.getKyMc());
	}
	
	/**
	 * 调用ZK上传组件，用saveFile()方法将上传的文件复制到服务器
	 */
	public void onClick$upload()
	{
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");
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
			+ File.separator+xm.getKyId() ;//参考文献的存储路径，具体为：存储跟目录+项目ID
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
			delete.setVisible(false);
			media = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存新创建的参考文献记录，并刷新参考文献列表
	 */
	public void onClick$save()
	{
		try {
			if(null==title.getValue()||"".equals(title.getValue()))
			{
				Messagebox.show("您尚未上传参考文献，请确认！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(readFeel.getValue().length()>254)
			{
				Messagebox.show("阅读感想内容过长，请重新编辑！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GH_ARCHIVE archive = new GH_ARCHIVE();
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
		archive.setArUpUserId(user.getKuId());
		archive.setArCLC(clc.getValue().trim());
		archive.setArIssue(issue.getValue().trim());
		archive.setArCategory(GhXm.KYXM);
		String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
			+ File.separator+xm.getKyId() + File.separator + filename.getValue();
		File archiveFile = new File(archivePath);
		if(archiveFile.exists())
		{
			archive.setArUpTime(DateUtil.getDateTimeString(new Date(archiveFile.lastModified())));
		}else{
			archive.setArUpTime(DateUtil.getDateTimeString(new Date()));
		}
		try{
			archiveService.save(archive);
			saveFile(media);
			Messagebox.show("添加成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){
			try {
				Messagebox.show("参考文献添加失败！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		articallistWindow.initWindow();
		this.detach();
	}
	
	/**
	 * 关闭当前窗口
	 */
	public void onClick$close()
	{
		this.detach();
	}
	
	/**
	 * 将上传文件保存到服务器指定的目录下
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
}