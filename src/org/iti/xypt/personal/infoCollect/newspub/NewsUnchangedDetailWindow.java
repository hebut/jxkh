package org.iti.xypt.personal.infoCollect.newspub;
/**
 * ���ƶԲ��ɸ��ĵ���Ϣ�Ĳ鿴
 * @author whm
 * 2010-3-18
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTFlog;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocntId;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class NewsUnchangedDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	WkTInfocnt Infocnt ;
	WkTInfo info,inf;
	WkTDistribute dis;
	WkTInfocntId infocntid;
	WkTUser user;
	WkTFlog flog;
	//��Ϣ���ݷ��ʽӿ�b
	NewsService newsService;
	TaskService taskService;
	//������־���ݷ��ʽӿ�
	MLogService mlogService;
	//�����޸ĵĽ������õ��ĸ������
	Textbox kititle,kititle2,kbtitle,kikeys,kisource,kimemo,kcid,share;
	Textbox fbtitle,kitype,infoname,kivaliddate;
	Button del,down,download;
	Toolbarbutton back,chegao,delete;
	Listbox upList;
	Checkbox cuti,mail;
	Label file;
	Html kicontent;
	Hbox wdnews,tupian,pics;
	ListModelList modelListbox;
	List nameList=new ArrayList();
	List flist;
	Image kiimage;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
        modelListbox=new ListModelList(nameList);
		upList.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				if(arg1 instanceof Media){
				 Media media=(Media)arg1;
				 arg0.setValue(arg1);
				 arg0.setLabel(media.getName());
				}else{
				 WkTFile f=(WkTFile)arg1;
				 arg0.setValue(arg1);
				 arg0.setLabel(f.getId().getKfShowname());
				}
			}			
		});		
	}

	public WkTInfo getInfo() {
		return info;
	}
	//�ж���Ϣ״̬�����ƽ�����ʾ����ʼ����Ϣֵ
	public void initWindow(WkTDistribute dis) {
		this.dis=dis;
		 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 WkTExtractask task=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
		 String state=dis.getKbStatus().trim();
		 chegao.setVisible(false);
		 if(dis.getKbStatus().toString().trim().equals("0"))
		 {
			 chegao.setVisible(true);
		 }
		 if(state.equals("0"))
		 {
			 delete.setVisible(false);
		 }
		initInfocnt(newsService.getChildNewsContent(info.getKiId()));
		 if(dis.getKbStrong().toString().trim().equals("1"))
			 cuti.setChecked(true);
		 if(dis.getKbMail().trim().equals("1"))
			 mail.setChecked(true);
		kititle.setValue(info.getKiTitle());
		kititle2.setValue(info.getKiTitle2());
		kisource.setValue(info.getKiSource());
		kikeys.setValue(info.getKiKeys());
		kimemo.setValue(info.getKiMemo());
		kbtitle.setValue(dis.getKbTitle());
		kcid.setValue(task.getKeName());	
		kivaliddate.setValue(info.getKiValiddate());
		//��ʼ����ʾͼƬ
		if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0)
		{
			String pa = "/upload/info"+"/"+info.getKiImage().trim();
			kiimage.setSrc(pa);
			
		}
		  //���ظ���
		   if(info.getKiType().trim().equals("1"))
        		 {
			   flist=newsService.getFile(dis.getKiId());
        		 modelListbox.addAll(flist);
        	     upList.setModel(modelListbox);
        		 }
	}
	
	public WkTInfocntId getInfocntid()
	{
		return infocntid;
	}
	public void  initInfocntid(WkTInfocntId infocntid)
		{
			this.infocntid=infocntid;	
		}
	//���ѷ�����Ϣ����
	public void onClick$chegao() throws InterruptedException
	{
		if(Messagebox.show("ȷ��������", "ȷ��", 
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
		{
		dis.setKbStatus("1");
		newsService.update(dis);
		 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		mlogService.saveMLog(WkTMlog.FUNC_CMS, "���壬id:"+dis.getKiId(), user);
		//������Ϣ����
		if(newsService.getDistributeShare(dis.getKiId()).size()!=0)
		{
			 List list=new ArrayList();
			 list=newsService.getDistributeShare(dis.getKiId());
			for(int i=0;i<list.size();i++)
			{ 
				WkTDistribute d=(WkTDistribute)list.get(i);
				d.setKbStatus("1");
				newsService.update(d);
			}
		}
		Messagebox.show("����ɹ���");
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();	
		}
	}
	//���ػ�鿴ͼƬ
	 public void onClick$kiimage() throws FileNotFoundException
		{
			 String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
			   File f=new File(path.trim()+"\\".trim()+newsService.getWkTInfo(dis.getKiId()).getKiImage().toString().trim());  
			   Filedownload.save(f,null);
		}
	//���ظ����ļ�
	public void onClick$down() throws InterruptedException, FileNotFoundException{
		
		 if(modelListbox.getSize()==0) return;
		 
		 Listitem it=upList.getSelectedItem();		 
		 if(it==null) {
			 if(modelListbox.getSize()>0){
				 it=upList.getItemAtIndex(0);
			 }
		 }
		 if(it.getValue() instanceof Media){
			 Filedownload.save((Media)it.getValue());
		 }else{
		  WkTFile f=(WkTFile)it.getValue();
		  String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
		  Filedownload.save(new File(path+"\\"+f.getId().getKfName()), null);
		 }
	}
public WkTInfocnt getInfocnt()
{
	return Infocnt;
}
//��ȡ��Ϣ�ĵ�����
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++){
			WkTInfocnt inf=(WkTInfocnt)rlist.get(i);
			con+=inf.getKiContent();
		}
		kicontent.setContent(con);
	}
//ɾ��������Ϣ
public void onClick$delete() throws InterruptedException
{  
	if(Messagebox.show("ȷ��Ҫɾ����", "ȷ��", 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
	{  
	if(dis.getKbFlag().toString().trim().equals("0"))
	{
	 if(newsService.getFile(dis.getKiId()).size()!=0)
	 {
		 List annex=new ArrayList();
		 annex=newsService.getFile(dis.getKiId());
		 for(int i=0;i<annex.size();i++)
		 {
			 newsService.delete((WkTFile)annex.get(i));
		 }
	 }
	 if((newsService.getWkTInfo(dis.getKiId())).getKiType().trim().equals("1"))
	 {
	List cnt=newsService.getInfocnt(dis.getKiId());
	if(cnt.size()!=0)
	{
	for(int i=0;i<cnt.size();i++)
	{
	 newsService.delete((WkTInfocnt)cnt.get(i));
	}
	}
	 }
	List d=newsService.getDistributeList(dis.getKiId());
	for(int i=0;i<d.size();i++)
	{
	 newsService.delete((WkTDistribute)d.get(i));
	}
	 newsService.delete(newsService.getWkTInfo(dis.getKiId()));
	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	  mlogService.saveMLog(WkTMlog.FUNC_CMS, "ɾ����Ϣ��id:"+dis.getKiId(), user);
	}
	else 
	 {
		 newsService.delete(dis);
		 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		  mlogService.saveMLog(WkTMlog.FUNC_CMS, "ɾ����Ϣ��id:"+dis.getKiId(), user);
	 }
	 Messagebox.show("ɾ���ɹ���");	
     this.detach();
     Events.postEvent(Events.ON_CHANGE, this, null);
	}
    }
}
