package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * ѡ��������Ϣʱ�鿴����
 * @author whm
 * 2010-9-9
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTFlog;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class NewsAuditSelDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	WkTInfocnt Infocnt ;
	WkTInfo info,inf;
	WkTChanel chanel;
	WkTDistribute dis;
	WkTUser user;
	WkTFlog flog;
	//��Ϣ���ݷ��ʽӿ�
	NewsService newsService;
	//������־���ݷ��ʽӿ�
	MLogService mlogService;
	//�����޸ĵĽ������õ��ĸ������
	Textbox kititle,kititle2,kbtitle,kitype;
	Button down,download;
	Listbox upList;
	Html kiaddress,kicontent;
	Separator sep1,sep2;
	Hbox bdnews,ljnews,wdnews,tupian,pics,wd;
	ListModelList modelListbox;
	List nameList=new ArrayList();
	Image kiimage;
	Window wins;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
        modelListbox=new ListModelList(nameList);
        //�����б�
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
		newsService=(NewsService)SpringUtil.getBean("newsService");
        dis=(WkTDistribute)Executions.getCurrent().getAttribute("kiTitle");
        initInfo(dis);
	}

	public WkTInfo getInfo() {
		return info;
	}
	//�ж���Ϣ״̬�����ƽ�����ʾ����ʼ����Ϣֵ
	public void initInfo(WkTDistribute dis) 
	{
		 this.dis= dis;
		 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 String state=dis.getKbStatus().trim();
		if(info.getKiType().trim().equals("1"))
		{
			ljnews.setVisible(false);
			bdnews.setVisible(false);
			initInfocnt(newsService.getChildNewsContent(info.getKiId()));
			kitype.setValue("����");   
			sep1.setVisible(true);
			sep2.setVisible(true);
		}
		else if(info.getKiType().trim().equals("2"))
		{
			ljnews.setVisible(false);
			wdnews.setVisible(false);
			wd.setVisible(false);
			tupian.setVisible(false);
            kitype.setValue("�ϴ��ļ�");   
		}
		else if(info.getKiType().trim().equals("3"))
		{
			wdnews.setVisible(false);
			wd.setVisible(false);
			bdnews.setVisible(false);
			tupian.setVisible(false);
			kiaddress.setContent("<a href=\""+info.getKiAddress()+"\" target=\"_blank\">"+info.getKiAddress()+"</a>");
			kitype.setValue("�ⲿ����"); 
		}
		kititle.setValue(info.getKiTitle());
		kititle2.setValue(info.getKiTitle2());
		kbtitle.setValue(dis.getKbTitle()); 
		//��ʼ����ʾͼƬ
		if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0)
		{
			kiimage.setSrc("/upload/info/"+info.getKiImage().trim());
		}
		  //���ظ���
        List flist=newsService.getFile(dis.getKiId());
        modelListbox.addAll(flist);
        upList.setModel(modelListbox);
	}



	//���ػ�鿴ͼƬ
	 public void onClick$kiimage() throws FileNotFoundException
		{
			 String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
			   File f=new File(path.trim()+"\\".trim()+newsService.getWkTInfo(dis.getKiId()).getKiImage().toString().trim());  
			   Filedownload.save(f,null);
		}
	//�����ϴ����ļ�
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
		  String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/");
		  Filedownload.save(new File(path+"\\"+f.getId().getKiId()+"\\"+f.getId().getKfName()), null);
		 }
	}
	//���ر����ļ�
	public void onClick$download() throws FileNotFoundException
	{
		  String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info").trim();
		String fileName=(newsService.getWkTInfo(dis.getKiId())).getKiAddress().trim();
		String name =path.trim()+"\\".trim()+fileName.trim();
		File down=new File(name);
		Filedownload.save(down,null);
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
public void onClick$back()
{
	wins.detach();
}
}
