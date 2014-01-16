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
	Media media;//�ϴ����ļ�����
	String mediaName;//�ϴ��ļ������� filename
	String rpath;//�ϴ��ļ���·�� filepath
	Integer state;//state����׶α����ϴ���״̬��0����δ�ϴ���1�������ϴ�
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
		upload.setTooltiptext("�ϴ�");
	}

	@Override
	public void initWindow() {
		projectName.setValue(xm.getKyMc());

	}
	/**
	 * �ϴ��׶α���
	 * �����ʱֻ�ǻ�ȡ�ļ��ı���·������û�н��ļ��ϴ����浽��������
	 */
	public void onClick$upload() {
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");
		try {
			media = Fileupload.get();
			//���б�����ļ�������Ϊ���ϴ����ڣ���ȷ���룩+�ļ�ԭ��
			mediaName =  DateUtil.getDateTimeString(new Date())+media.getName();
			rpath=mediaName;
			//���ļ������ȳ�ʱֻ��ȡ���е�ǰ10����ʾ
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
			delete.setTooltiptext("ɾ��");
			upload.setVisible(false);
			/**
			 * 
			 */

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ���ļ����浽��������
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
				Messagebox.show("�ο����״洢·������ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
				title.setRawValue("");
				keyWord.setRawValue("");
				filename.setValue("");
				remark.setValue("");
				rpath="";
				state=0; //0����û���ϴ�
				upload.setVisible(true);
				upload.setTooltiptext("�ϴ�");
				delete.setVisible(false);
				download.setVisible(false);
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
	 * ����
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void onClick$save() throws InterruptedException, IOException {
		saveFile(media);
		if(rpath==null || "".equals(rpath) || title.getValue()==null || keyWord.getValue()==null || "".equals(title.getValue()) || "".equals(keyWord.getValue())) {
			try {
				Messagebox.show("����δ�ϴ��׶α�����߱�����ĿΪ�գ���������д��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
