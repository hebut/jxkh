package org.iti.projectmanage.teach.member;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import org.iti.gh.common.util.DateUtil;
import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Fileupload;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.iti.xypt.ui.base.BaseWindow;

import com.uniwin.framework.entity.WkTUser;

public class StageReportAddWindow extends BaseWindow {
	Media media;//上传的文件对象
	String mediaName;//上传文件的名称 filename
	String rpath;//上传文件的路径 filepath
	Integer state;//state代表阶段报告上传的状态：0代表未上传，1代表已上传
	GhXm xm = new GhXm();
	Label projectName,filename;
	Textbox title,keyWord;
	Textbox remark;
	Toolbarbutton upload,download,delete;
	Toolbarbutton save,close;
	
	GH_PHASEREPORT ghReport = new GH_PHASEREPORT();
	GH_PHASEREPORTService gh_phasereportService;
	WkTUser user = (WkTUser)Sessions.getCurrent().getAttribute("user");
	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	
	
	

	@Override
	public void initShow() {			
		upload.setTooltiptext("上传");
	}

	@Override
	public void initWindow() {
		projectName.setValue(xm.getKyMc());

	}
	/**
	 * 上传阶段报告
	 * 当点击时只是获取文件的保存路径，并没有将文件上传保存到服务器上
	 */
	public void onClick$upload() {
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");
		try {
			media = Fileupload.get();
			//库中保存的文件的名称为：上传日期（精确到秒）+文件原名
			mediaName =  DateUtil.getDateTimeString(new Date())+media.getName();
			rpath=mediaName;
			//当文件名长度长时只截取其中的前10个显示
			String fileName;
			if(mediaName.length()>10) {
				fileName=mediaName.substring(0, 9)+"...";
			}else {
				fileName=mediaName;
			}

			filename.setValue(fileName);
			filename.setTooltiptext(mediaName);
			filename.setStyle("color:blue");
			state=1;
			download.setVisible(false);			
			delete.setVisible(true);
			delete.setTooltiptext("删除");
			upload.setVisible(false);
			/**
			 * 
			 */

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将文件保存到服务器上
	 * @param media
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void saveFile(Media media) throws InterruptedException, IOException {		
		if(null==media)
		{
			return;
		}
		
		
		/**
		 * 阶段报告上传路径 path of uploading report
		 */

		String reportPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
		+ File.separator+xm.getKyId() ;//阶段报告的存储路径，具体为：存储跟目录+项目ID

		File pathDir = new File(reportPath);
		if(!(pathDir.exists()))
		{
			boolean isCreate = pathDir.mkdirs();
			if(isCreate==false)
			{
				Messagebox.show("参考文献存储路径创建失败！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		}	
		
		String path = reportPath+ File.separator +mediaName;
		File archiveFile = new File(path);
		if(mediaName.endsWith(".txt")||mediaName.endsWith(".project"))
		{
			Reader reader = media.getReaderData();
			Files.copy(archiveFile, reader, "GBK");
			Files.close(reader);
		}else{
			InputStream mediaInput = media.getStreamData(); 
			FileOutputStream fileOutput = new FileOutputStream(path);
		    DataOutputStream dataOutput = new DataOutputStream(fileOutput); 
			Files.copy(dataOutput,mediaInput);
			if(dataOutput!=null){
				dataOutput.close();
			}	
			if(fileOutput!=null){
				fileOutput.close();
			}
			if(mediaInput!=null){
				mediaInput.close();
			}
		}
		
	}
	/**
	 * 下载阶段报告
	 */
	public void onClick$download()
	{
		String reportPath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
			+ File.separator+xm.getKyId() + File.separator + rpath;
		File reportFile = new File(reportPath);
		try 
		{
			if(!reportFile.exists())
			{
				Messagebox.show("阶段报告已经被删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			Filedownload.save(reportFile,null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除已上传阶段报告
	 */
	public void onClick$delete()
	{
		try {
			if(Messagebox.NO==Messagebox.show("是否确定删除该文件", "确认", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
			{
				return;
			}
			//阶段报告(带文件名)的存储路径，具体为：存储跟目录+项目ID+文件名
			boolean isDel = deleteFile();
			if(isDel)
			{
				title.setRawValue("");
				keyWord.setRawValue("");
				filename.setValue("");
				remark.setValue("");
				rpath="";
				state=0; //0代表没有上传
				upload.setVisible(true);
				upload.setTooltiptext("上传");
				delete.setVisible(false);
				download.setVisible(false);
			}else{
				Messagebox.show("文件删除失败,请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public boolean deleteFile()
	{
		String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
			+ File.separator+xm.getKyId() + File.separator + rpath;
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
	/**
	 * 保存
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void onClick$save() throws InterruptedException, IOException {
		saveFile(media);
		if(rpath==null || "".equals(rpath) || title.getValue()==null || keyWord.getValue()==null || "".equals(title.getValue()) || "".equals(keyWord.getValue())) {
			try {
				Messagebox.show("您尚未上传阶段报告或者报告题目为空，请重新填写！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		
		ghReport.setKuLid(user.getKuLid());
		ghReport.setKuId(user.getKuId());
		ghReport.setPhRepoName(title.getValue());
		ghReport.setKeyWord(keyWord.getValue());
		ghReport.setPhRepoRemark(remark.getValue());
		ghReport.setPhRepoUser(user.getKuName());
		ghReport.setProId(xm.getKyId());

		ghReport.setPhRepoDate(DateUtil.convertDateAndTimeString(new Date()));
		ghReport.setPhRepoPath(rpath);
		ghReport.setPhRepoState(state);		
		gh_phasereportService.save(ghReport);
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
	public void onClick$close() {
		this.detach();
	}

	

}
