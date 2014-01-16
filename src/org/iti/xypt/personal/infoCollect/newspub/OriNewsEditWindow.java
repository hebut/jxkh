package org.iti.xypt.personal.infoCollect.newspub;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocntId;
import org.iti.xypt.personal.infoCollect.entity.WkTOrifile;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class OriNewsEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map params;	
	WkTOrinfo oinfo;
	Textbox kititle,kititle2,kikeys,kisource,kimemo,ptime,taskname;
	FCKeditor kicontent;
	OriNewsService orinewsService;
	NewsService newsService;
	TaskService taskService;
	MLogService mlogService;
	Toolbarbutton reset,choose;
	WkTUser user;
	Checkbox cuti,mail;
	Hbox pics;
	List slist=new ArrayList();
	List flist;
	Listbox upList;
	ListModelList modelListbox;
	public void afterCompose()
	{
		params=this.getAttributes();
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		//选择发布频道
		choose.addEventListener(Events.ON_CLICK,  new EventListener(){
			public void onEvent(Event event) throws Exception {
				NewsTaskSelectWindow w=(NewsTaskSelectWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/newtasksel.zul", null,null);
				Executions.getCurrent().setAttribute("mul","0");
				w.doHighlighted();
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						NewsTaskSelectWindow ns=(NewsTaskSelectWindow) arg0.getTarget();
						slist=ns.getSlist();
						WkTExtractask et=(WkTExtractask) slist.get(0);
						taskname.setValue(et.getKeName());
					}
				});
			}	
		});
	}
	public void initWindow(WkTOrinfo oinfo)
	{
		this.oinfo=oinfo;
		WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(oinfo.getKeId()).get(0);
		kititle.setValue(oinfo.getKoiTitle());
		kititle2.setValue(oinfo.getKoiTitle2());
		kisource.setValue(oinfo.getKoiSource());
		kikeys.setValue(oinfo.getKoiKeys());
		taskname.setValue(e.getKeName());
		kimemo.setValue(oinfo.getKoiMemo());
		ptime.setValue(oinfo.getKoiPtime());
		initOinfocnt(orinewsService.getOriInfocnt(oinfo.getKoiId()));
		  //加载附件
        flist=newsService.getOrifile(oinfo.getKoiId());
        if(flist.size()!=0)
        {
		 modelListbox.addAll(flist);
	     upList.setModel(modelListbox);
        }
		pics.getChildren().clear();
		//初始化显示图片
		if(oinfo.getKoiImage()!=null&&oinfo.getKoiImage().trim().length()>0){
		   Image img=new Image();
	 		String pa = "/upload/info"+"/"+oinfo.getKoiImage().trim();
	 		img.setSrc(pa);
		   img.setWidth("25px");
		   img.setHeight("25px");
		   img.setParent(pics);
		    Button b=new Button();
			b.setLabel("删除");
			b.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
			  public void onEvent(Event event) throws Exception {
			    pics.getChildren().clear();
			  }
			});
			b.setParent(pics);
		}		
		
	}
	public void  initOinfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++)
		{
			WkTOrinfocnt infcnt=(WkTOrinfocnt)rlist.get(i);
			con+=infcnt.getKoiContent();
		}
		kicontent.setValue(con);
	}
	//保存信息
	public void onClick$save() throws InterruptedException
	{
		if(kititle.getValue().equals(""))
		{
			Messagebox.show("请填写信息标题");
			return;
		}
		if(taskname.getValue().equals(""))
		{
			Messagebox.show("请选择信息发布的频道");
			return;
		}
		WkTInfo info=new WkTInfo();
		info.setKiTitle(kititle.getValue());
		info.setKiTitle2(kititle2.getValue());
		if(slist.size()!=0)
		{
		WkTExtractask et=(WkTExtractask) slist.get(0);
		info.setKeId(et.getKeId());
		}
		else
		{
			info.setKeId(oinfo.getKeId());
		}
		info.setKiAuthname(oinfo.getKoiAuthname());
		info.setKiCtime(oinfo.getKoiCtime());
		info.setKiHits(Integer.parseInt("0"));
		info.setKiKeys(kikeys.getValue());
		info.setKiMemo(kimemo.getValue());
		info.setKiOrdno(Integer.parseInt("10"));
		info.setKiPtime(oinfo.getKoiPtime());
		info.setKiAddress(oinfo.getKoiAddress());
		info.setKiImage(oinfo.getKoiImage());
		info.setKiValiddate("1900-01-01");
		info.setKiShow("1");
		info.setKiSource(kisource.getValue());
		info.setKiTop("0");
		info.setKiUrl(oinfo.getKoiUrl());
		info.setKiType("1");
		info.setKuId(user.getKuId());
		info.setKuName(user.getKuName());
         newsService.save(info);
	     Long len=3000L;
	     Long max=kicontent.getValue().length()/len+1;
	     for(Long i=0L;i<max;i++){
	    	 WkTInfocnt infocnt=new WkTInfocnt();
	    	 WkTInfocntId infocntid=new WkTInfocntId(info.getKiId(),i+1);
		     infocnt.setId(infocntid);
		     Long be=i*len;
		     if(i==(max-1)){
		    	 infocnt.setKiContent(kicontent.getValue().substring(be.intValue())); 
		     }else{
		       Long en=(i+1)*len;
		       infocnt.setKiContent(kicontent.getValue().substring(be.intValue(),en.intValue()));
		     }
		     newsService.save(infocnt);
	     }
	     WkTDistribute dis=new WkTDistribute();
	     dis.setKiId(info.getKiId());
	     dis.setKeId(info.getKeId());
	     dis.setKbTitle(info.getKiTitle());
	     dis.setKbRight("0");
	     dis.setKbStatus("1");
	     dis.setKbMail("0");
	     dis.setKbFlag("0");
	     if(mail.isChecked())
	     {
	    	 dis.setKbMail("1");
	     }
	     else 
	     {
	    	 dis.setKbMail("0");
	     }
	     Date date=new Date();
	     dis.setKbDtime(ConvertUtil.convertDateAndTimeString(date.getTime()));
	     if(cuti.isChecked())
	     dis.setKbStrong("1");
	     else     dis.setKbStrong("0");
	     dis.setKbEm("0");
	     newsService.save(dis);
	     Messagebox.show("保存成功！");
		 this.detach();
		 //删除原始信息表内容
	     
	     List ofilelist=orinewsService.getOrifile(oinfo.getKoiId());
	     if(ofilelist.size()!=0)
	     {
	    	 for(int j=0;j<ofilelist.size();j++)
	    	 {
	    	 WkTOrifile file=(WkTOrifile) ofilelist.get(j);
	    	 newsService.delete(file);
	    	 }
	     }
	     List oinfolist=orinewsService.getOriInfocnt(oinfo.getKoiId());
	     for(int i=0;i<oinfolist.size();i++)
	     {
	     WkTOrinfocnt oinfocnt=(WkTOrinfocnt)oinfolist.get(i);
	     newsService.delete(oinfocnt);
	     }
	     newsService.delete(oinfo);
		 Events.postEvent(Events.ON_CHANGE, this, null);
	}
	//发布
	public void onClick$saudit() throws InterruptedException
	{
		if(kititle.getValue().equals(""))
		{
			Messagebox.show("请填写信息标题");
			return;
		}
		if(taskname.getValue().equals(""))
		{
			Messagebox.show("请选择信息发布频道");
			return;
		}
		WkTInfo info=new WkTInfo();
		info.setKiTitle(kititle.getValue());
		info.setKiTitle2(kititle2.getValue());
		if(slist.size()!=0)
		{
		WkTExtractask et=(WkTExtractask) slist.get(0);
		info.setKeId(et.getKeId());
		}
		else
		{
			info.setKeId(oinfo.getKeId());
		}
		info.setKiAuthname(oinfo.getKoiAuthname());
		info.setKiCtime(oinfo.getKoiCtime());
		info.setKiHits(Integer.parseInt("0"));
		info.setKiKeys(kikeys.getValue());
		info.setKiMemo(kimemo.getValue());
		info.setKiOrdno(Integer.parseInt("10"));
		info.setKiPtime(oinfo.getKoiPtime());
		info.setKiAddress(oinfo.getKoiAddress());
		info.setKiImage(oinfo.getKoiImage());
		info.setKiShow("1");
		info.setKiValiddate("1900-01-01");
		info.setKiSource(kisource.getValue());
		info.setKiTop("0");
		info.setKiUrl(oinfo.getKoiUrl());
		info.setKiType("1");
		info.setKuId(user.getKuId());
		info.setKuName(user.getKuName());
         newsService.save(info);
	     Long len=3000L;
	     Long max=kicontent.getValue().length()/len+1;
	     for(Long i=0L;i<max;i++){
	    	 WkTInfocnt infocnt=new WkTInfocnt();
	    	 WkTInfocntId infocntid=new WkTInfocntId(info.getKiId(),i+1);
		     infocnt.setId(infocntid);
		     Long be=i*len;
		     if(i==(max-1)){
		    	 infocnt.setKiContent(kicontent.getValue().substring(be.intValue())); 
		     }else{
		       Long en=(i+1)*len;
		       infocnt.setKiContent(kicontent.getValue().substring(be.intValue(),en.intValue()));
		     }
		     newsService.save(infocnt);
	     }
	     WkTDistribute dis=new WkTDistribute();
	     dis.setKiId(info.getKiId());
	     dis.setKeId(info.getKeId());
	     if(mail.isChecked())
	     {
	    	 dis.setKbMail("1");
	     }
	     else 
	     {
	    	 dis.setKbMail("0");
	     }
	     dis.setKbTitle(info.getKiTitle());
	     dis.setKbRight("0");
	     dis.setKbFlag("0");
	     WkTExtractask wet=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
	     WkTTasktype tt=taskService.getTpyeById(wet.getKtaId());
	     if(tt.getKtaAudit().toString().trim().equals("0"))
	     {
	    	   dis.setKbStatus("0");
	     }
	     else if(tt.getKtaAudit().toString().trim().equals("1"))
	     {
	     dis.setKbStatus("2");
	     }
	     Date date=new Date();
	     dis.setKbDtime(ConvertUtil.convertDateAndTimeString(date.getTime()));
	     if(cuti.isChecked())
	     dis.setKbStrong("1");
	     else     dis.setKbStrong("0");
	     dis.setKbEm("0");
	     newsService.save(dis);
	     Messagebox.show("发布成功！");
	     //删除原始信息表内容
	     List oinfolist=orinewsService.getOriInfocnt(oinfo.getKoiId());
	     for(int i=0;i<oinfolist.size();i++)
	     {
	     WkTOrinfocnt oinfocnt=(WkTOrinfocnt)oinfolist.get(i);
	     newsService.delete(oinfocnt);
	     }
	     List ofilelist=orinewsService.getOrifile(oinfo.getKoiId());
	     if(ofilelist.size()!=0)
	     {
	    	 for(int j=0;j<ofilelist.size();j++)
	    	 {
	    	 WkTOrifile file=(WkTOrifile) ofilelist.get(j);
	    	 newsService.delete(file);
	    	 }
	     }
	     newsService.delete(oinfo);
		 this.detach();
		  Events.postEvent(Events.ON_CHANGE, this, null);
		
	}
	//重置
	public void onClick$reset()
	{
		initWindow(oinfo);
	}

}