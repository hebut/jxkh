package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * 控制信息审核中不可修改的界面
 * 2010-3-20
 * @author whm
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocntId;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
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

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;


public class NewsUnchangeauditDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	//信息数据访问接口
	NewsService newsService;
	UserService userService;
	TaskService taskService;
	//管理日志数据访问接口
	MLogService mlogService;
	WebsiteService websiteService;
	WkTInfocnt Infocnt ;
	WkTInfo info,inf;
	WkTChanel chanel;
	WkTDistribute dis;
	WkTUser user;
	WkTInfocntId infocntid;
	//不可修改的界面所用到的各种组件
	Textbox kititle,kititle2,kiordno,kbtitle,kikeys,kisource,kimemo,kcid,share,kibdadd;
	Textbox fbtitle,kitype,wselected,cselected,infoname;
	Datebox kivaliddate;
	Html kiaddress,kicontent;
	Toolbarbutton download,chegao;
	Label kishow,inorout,kisite,bdfil;
	Hbox bdnews,ljnews,wdnews,tupian,wd,isite,site;
	Image kiimage;
	Separator sep1,sep2;
	Listbox upList;
	List nameList=new ArrayList();
	List flist;
	ListModelList modelListbox;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
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
	//判断状态,控制页面显示
	public void initWindow(WkTDistribute dist) throws IOException {
		this.dis= dist;
		 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 WkTExtractask task=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
		user=(WkTUser)userService.getUserByuid(info.getKuId());
			initInfocnt(newsService.getChildNewsContent(dis.getKiId()));
		if(dis.getKbStatus().toString().trim().equals("2")||dis.getKbStatus().toString().trim().equals("3"))
		{
			chegao.setVisible(false);
		}
		kititle.setValue(info.getKiTitle());
		kititle2.setValue(info.getKiTitle2());
		kisource.setValue(info.getKiSource());
		kikeys.setValue(info.getKiKeys());
		kimemo.setValue(info.getKiMemo());
		kbtitle.setValue(dis.getKbTitle());
		kivaliddate.setValue(ConvertUtil.convertDate(info.getKiValiddate()));	
        kcid.setValue(task.getKeName());
		//初始化显示图片
		if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0)
		{  
			String pa = "/upload/info"+"/"+info.getKiImage().trim();
			kiimage.setSrc(pa);
		}
		 //加载附件
	            if(info.getKiType().trim().equals("1"))
        		 {flist=newsService.getFile(dis.getKiId());
        		 modelListbox.addAll(flist);
        	     upList.setModel(modelListbox);
        		 }
	}
	
	public WkTChanel getChanel() {
		return chanel;
	}
	private String initChanel(WkTChanel chanel) {
		this.chanel=chanel;
		return chanel.getKcName();
	}

	
public WkTInfocnt getInfocnt()
{
	return Infocnt;
}
//获取信息主要内容
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++){
			WkTInfocnt inf=(WkTInfocnt)rlist.get(i);
			con+=inf.getKiContent();
		}
		kicontent.setContent(con);
	}
//下载附件文件
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
//下载或查看图片
public void onClick$kiimage() throws FileNotFoundException
	{
		 String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
		   File f=new File(path.trim()+"\\".trim()+newsService.getWkTInfo(dis.getKiId()).getKiImage().toString().trim());  
		   Filedownload.save(f,null);
	}
//对已发布信息撤稿
public void onClick$chegao() throws InterruptedException
{
	if(Messagebox.show("确定撤稿吗？", "确认", 
			Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
	{
	dis.setKbStatus("2");
	newsService.update(dis);
	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	mlogService.saveMLog(WkTMlog.FUNC_CMS, "撤稿，id:"+dis.getKiId(), user);
	//共享信息撤稿
	if(dis.getKbFlag().toString().trim().equals("0")&&newsService.getDistributeShare(dis.getKiId()).size()!=0)
	{System.out.println(dis);
		List list=new ArrayList();
        list=newsService.getDistributeShare(dis.getKiId());
		for(int i=0;i<list.size();i++)
		{   
			WkTDistribute d=(WkTDistribute)list.get(i);
			d.setKbStatus("2");
			newsService.update(d);
		}
	}
	Messagebox.show("撤稿成功！");
	Events.postEvent(Events.ON_CHANGE, this, null);
	this.detach();	
	}
}
//删除信息
public void onClick$delete() throws InterruptedException
{  
	 if(Messagebox.show("确定要删除吗？", "确认", 
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
		 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 if(info.getKiType().trim().equals("1"))
		 {
		 List cnt=newsService.getInfocnt(dis.getKiId());
		 for(int j=0;j<cnt.size();j++)
		 newsService.delete((WkTInfocnt)cnt.get(j));
		 }
		List d=newsService.getDistributeList(dis.getKiId());
		for(int i=0;i<d.size();i++)
		{
		 newsService.delete((WkTDistribute)d.get(i));
		}
		 newsService.delete(newsService.getWkTInfo(dis.getKiId()));
		
		 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		 mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+dis.getKiId(), user);
		}
		 else
		 {
			 newsService.delete(dis);
			 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
			 mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+dis.getKiId(), user);
		 }
	         Messagebox.show("删除成功！");	
	     	Events.postEvent(Events.ON_CHANGE, this, null);
	         this.detach();
		}
		
}

}
