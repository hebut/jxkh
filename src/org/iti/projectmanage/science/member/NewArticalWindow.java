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
	ArticalListWindow articallistWindow;//�ο������б��ڣ����ڵ������ʼ���������Ӷ�ˢ�²ο������б�
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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//��ȡ��ǰ��¼�û�
	}
	
	/**
	 * ��ʼ����ǰ����
	 */
	public void initWindow()
	{
		projectName.setValue(xm.getKyMc());
	}
	
	/**
	 * ����ZK�ϴ��������saveFile()�������ϴ����ļ����Ƶ�������
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
			+ File.separator+xm.getKyId() ;//�ο����׵Ĵ洢·��������Ϊ���洢��Ŀ¼+��ĿID
		File pathDir = new File(archivePath);
		if(!(pathDir.exists()))
		{
			boolean isCreate = pathDir.mkdirs();
			if(isCreate==false)
			{
				try {
					Messagebox.show("�ο����״洢·������ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
				if(Messagebox.NO==Messagebox.show("�Ѵ���ͬ���Ĳο����ף��Ƿ�����ϴ���", "ȷ��", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
				{
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String fileNum = pathDir.listFiles().length+1+"";
			title.setValue("("+fileNum+")"+mediaName);
			filename.setValue("("+fileNum+")"+mediaName);
			//�������ͬ�����ļ����򽫵�ǰ�ļ���ǰ����"(�����ļ������ļ��ĸ���+1)"
			path = archivePath + File.separator+"("+fileNum+")"+mediaName;
		}else{
			title.setValue(media.getName().substring(0,media.getName().lastIndexOf(".")));
			filename.setValue(media.getName());
		}
		delete.setVisible(true);
		upload.setVisible(false);
	}
	
	/**
	 * �Ƿ�ɾ���ο����׵ĸ���
	 */
	public void onClick$delete()
	{
		try {
			if(Messagebox.NO==Messagebox.show("�Ƿ�ȷ��ɾ�����ļ�", "ȷ��", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
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
	 * �����´����Ĳο����׼�¼����ˢ�²ο������б�
	 */
	public void onClick$save()
	{
		try {
			if(null==title.getValue()||"".equals(title.getValue()))
			{
				Messagebox.show("����δ�ϴ��ο����ף���ȷ�ϣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(readFeel.getValue().length()>254)
			{
				Messagebox.show("�Ķ��������ݹ����������±༭��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
//		archive.setArThema("")	//�����ֶ�
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
			Messagebox.show("��ӳɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){
			try {
				Messagebox.show("�ο��������ʧ�ܣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		articallistWindow.initWindow();
		this.detach();
	}
	
	/**
	 * �رյ�ǰ����
	 */
	public void onClick$close()
	{
		this.detach();
	}
	
	/**
	 * ���ϴ��ļ����浽������ָ����Ŀ¼��
	 * @param media ZK�ϴ������õ�ʵ��
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
			+ File.separator+xm.getKyId() ;//�ο����׵Ĵ洢·��������Ϊ���洢��Ŀ¼+��ĿID
		String mediaName =  filename.getValue(); 
		String path = archivePath+ File.separator +mediaName;
		/*
		 *˵����
		 *1����̨��ȡ�ϴ����ݵķ����������ĸ���getStreamData(),getString(),getStringReader(),getByteData() 
		 *2������isBinary()��isMemory()�ķ���ֵѡ�������ĸ�������
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