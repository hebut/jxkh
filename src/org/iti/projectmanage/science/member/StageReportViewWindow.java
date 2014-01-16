package org.iti.projectmanage.science.member;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import org.iti.gh.entity.GH_PHASEREPORT;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.GH_PHASEREPORTService;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Fileupload;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;

public class StageReportViewWindow extends Window implements AfterCompose {
	Media media;
	String mediaName;
	String rpath;//�ļ��洢·��
	boolean isDelete=false; //�����ж��ļ��Ƿ�ɾ��
	Integer state;
	Label projectName;
	Textbox reportName,keyWord,remark;
	Label downloadFile;
	Toolbarbutton download,delete,upload;
	Toolbarbutton submit,back;
	
	GhXm xm = new GhXm();
	GH_PHASEREPORT ghReport = new GH_PHASEREPORT();
	GH_PHASEREPORTService gh_phasereportService;	
	@Override
	public void afterCompose() {		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
	}
	public void initWindow(GhXm xm,GH_PHASEREPORT ghReport) {
		WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		projectName.setValue(xm.getKyMc());
		reportName.setValue(ghReport.getPhRepoName());
		keyWord.setValue(ghReport.getKeyWord());
		remark.setValue(ghReport.getPhRepoRemark());
		
		rpath = ghReport.getPhRepoPath();
		String fileName;
		if(rpath.length()>10) {
			fileName=rpath.substring(0, 9)+"...";
		}else {
			fileName=rpath;
		}
		downloadFile.setValue(fileName);
		downloadFile.setTooltiptext(rpath);
		downloadFile.setStyle("color:blue");
		
		
		upload.setVisible(false);
		if(!ghReport.getKuLid().equals(user.getKuLid())) {
			reportName.setReadonly(true);
			keyWord.setReadonly(true);
			remark.setReadonly(true);
			delete.setVisible(false);
			upload.setVisible(false);
			submit.setVisible(false);
			download.setVisible(true);
			download.setTooltiptext("����");
		}
		this.xm = xm;
		this.ghReport = ghReport;
	}
	/**
	 * ���ؽ׶α���
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
				Messagebox.show("�׶α����Ѿ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
	 * ɾ�����ϴ��׶α���
	 */
	public void onClick$delete()
	{
		try {
			if(Messagebox.NO==Messagebox.show("�Ƿ�ȷ��ɾ�����ļ�", "ȷ��", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
			{
				return;
			}
			//�׶α���(���ļ���)�Ĵ洢·��������Ϊ���洢��Ŀ¼+��ĿID+�ļ���
			boolean isDel = deleteFile();
			if(isDel)
			{				
				downloadFile.setValue("");				
				rpath="";				
				gh_phasereportService.delete(ghReport);
				
				isDelete=true;
				Events.postEvent(Events.ON_CHANGE, this, null);				
				/**
				 * 
				 */
				upload.setVisible(true);
				upload.setTooltiptext("�ϴ�");
				download.setVisible(false);
				delete.setVisible(false);
			}else{
				Messagebox.show("�ļ�ɾ��ʧ��,�����ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
	 * �ϴ��׶α���
	 */
	public void onClick$upload() {
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");
		//Media media;
		try {
			media = Fileupload.get();
			mediaName =  DateUtil.getDateTimeString(new Date())+media.getName();
			rpath=mediaName;
			String fileName;
			if(mediaName.length()>10) {
				fileName=mediaName.substring(0, 9)+"...";
			}else {
				fileName=mediaName;
			}

			downloadFile.setValue(fileName);
			downloadFile.setTooltiptext(mediaName);
			downloadFile.setStyle("color:blue");
			state=1;
			download.setVisible(false);			
			delete.setVisible(true);
			delete.setTooltiptext("ɾ��");
			upload.setVisible(false);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void saveFile(Media media) throws InterruptedException, IOException {		
		if(null==media)
		{
			return;
		}
		
		
		/**
		 * �׶α����ϴ�·�� path of uploading report
		 */

		String reportPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/report")
		+ File.separator+xm.getKyId() ;//�׶α���Ĵ洢·��������Ϊ���洢��Ŀ¼+��ĿID
		
		File pathDir = new File(reportPath);
		if(!(pathDir.exists()))
		{
			boolean isCreate = pathDir.mkdirs();
			if(isCreate==false)
			{
				Messagebox.show("�׶α���洢·������ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
	 * ������� submit change
	 * @throws IOException 
	 * @throws InterruptedException 
	 */	
	public void onClick$submit() throws InterruptedException, IOException {
		saveFile(media);
		if(isDelete) {
			if(rpath==null || "".equals(rpath) || reportName.getValue()==null || keyWord.getValue()==null ||  "".equals(reportName.getValue()) || "".equals(keyWord.getValue())) {
				try {
					Messagebox.show("����δ�ϴ��׶α�����߱�����ĿΪ�գ���������д��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;				
			}
			ghReport.setPhRepoName(reportName.getValue());
			ghReport.setKeyWord(keyWord.getValue());
			ghReport.setPhRepoRemark(remark.getValue());
			ghReport.setPhRepoState(state);
			ghReport.setPhRepoPath(rpath);
			gh_phasereportService.save(ghReport);
			Events.postEvent(Events.ON_CHANGE, this, null);
			this.detach();
		}else {
			ghReport.setPhRepoName(reportName.getValue());
			ghReport.setKeyWord(keyWord.getValue());
			ghReport.setPhRepoRemark(remark.getValue());
			ghReport.setPhRepoState(state);
			ghReport.setPhRepoPath(rpath);
			gh_phasereportService.update(ghReport);
			Events.postEvent(Events.ON_CHANGE, this, null);
			this.detach();
		}
		
	}
	public void onClick$back() {
		this.detach();
	}

}
