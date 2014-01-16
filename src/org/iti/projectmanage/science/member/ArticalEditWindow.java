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
	ArticalListWindow articallistWindow;//�ο������б��ڣ����ڵ������ʼ���������Ӷ�ˢ�²ο������б�
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
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//��ȡ��ǰ��¼�û�		
	}
	
	/**
	 * ��ʼ���ο����ױ༭����
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
		//�ο����׼�¼�е�������Դ�ֶ����ݲ�Ϊ�գ��Ҹ�����ָ�����ļ����ڣ�����ʾ���ֶ����ݣ�
		//�����ϴ���ť��Ϊ���ɼ������ò���ֻ֧���ϴ������������������غ�ɾ����ť��Ϊ�ɼ�
		if(null!=archive.getArPath()&&!("".equals(archive.getArPath())))
		{
			//�ο����׵Ĵ洢·��(�����ļ���)������Ϊ���洢��Ŀ¼+��ĿID+�ļ���
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
		//�����ǰ��¼�û�Ϊ�òο����׵��ϴ��ߣ������Ǹòο�����������Ŀ�ĸ����ˣ�����и����׼�¼�ı༭
		//�͸������ϴ���ɾ�����ܣ����򣬱��水ť���ɼ���������ɾ�����ϴ���ť���ɼ�
		if(!user.getKuId().equals(archive.getArUpUserId())&&!user.getKuId().equals(xm.getKuId()))
		{
			save.setVisible(false);
			delete.setVisible(false);
			upload.setVisible(false);
			this.setTitle("�鿴�ο�����");
		}
	}
	
	/**
	 * ����ZK�ϴ��������saveFile()�������ϴ����ļ����Ƶ�������
	 */
	public void onClick$upload()
	{
		Desktop desktop = this.getDesktop();
        Configuration conf = desktop.getWebApp().getConfiguration();
        conf.setMaxUploadSize(1024 * 10);
        conf.setUploadCharset("GBK");//windows����ϵͳ��.txt�ļ���Ĭ�ϱ��뷽ʽΪGBK���˴�����Ϊ�������ϴ�.txt�ļ��׳�������
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
			+ File.separator+xm.getKyId();//�ο����׵Ĵ洢·��������Ϊ���洢��Ŀ¼+�ϴ���ID
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
			download.setVisible(false);
			delete.setVisible(false);
			media = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����û��Ե�ǰ���׼�¼���޸ģ�Ȼ��ˢ�²ο������б�
	 */
	public void onClick$save()
	{
		
		try {
			if(null==filename.getValue()||"".equals(filename.getValue()))
			{
				if(Messagebox.NO==Messagebox.show("����δ�ϴ��µĲο����ף��Ƿ�ָ�ԭ�вο����ף�", "ȷ��", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION)){
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
				Messagebox.show("�Ķ��������ݹ����������±༭��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
//		archive.setArThema("")	//�����ֶ�
		archive.setArType(postType.getSelectedIndex());
		archive.setArCLC(clc.getValue().trim());
		archive.setArIssue(issue.getValue().trim());
		archive.setArCategory(GhXm.KYXM);
		archive.setArUpUserId(user.getKuId());
		String archivePath =basePath + File.separator+xm.getKyId() + File.separator + filename.getValue();
		File archiveFile = new File(archivePath);
		if(archiveFile.exists())//�����������ϴ��Ĳο����׸������ڣ������׵��ϴ�������Ϊ���ļ��������޸�����
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
					Messagebox.show("ԭ�вο����׸���ɾ��ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				saveFile(media);
			}
			archiveService.update(archive);
			Messagebox.show("����ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception e){
			try {
				Messagebox.show("�ο����ױ���ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		articallistWindow.initWindow();
		this.detach();
	}
	
	/**
	 * �رյ�ǰ���ڡ����ο����׸����ѱ�ɾ����ǿ�йرոô��ڽ�ɾ����ǰ���׼�¼
	 */
	public void onClick$close()
	{
		this.detach();
	}
	
	/**
	 * ���ϴ��ļ����Ƶ�������ָ����Ŀ¼��
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
	
	/**
	 * ���زο����׸���
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
				Messagebox.show("�ο������Ѿ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
	 * ɾ����ǰ�ϴ��Ĳο����׸���
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