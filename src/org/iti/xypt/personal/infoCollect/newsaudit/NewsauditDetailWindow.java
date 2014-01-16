package org.iti.xypt.personal.infoCollect.newsaudit;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTFileId;
import org.iti.xypt.personal.infoCollect.entity.WkTFlog;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocntId;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;



/**
 * 管理信息审核中的查看详细信息界面
 * 2010-3-19
 * @author whm
 *
 */
public class NewsauditDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 4915312144706234770L;
	//数据访问接口
	NewsService newsService;
	UserService userService;
    TaskService taskService;
	//管理日志数据访问接口
	MLogService mlogService;
    WkTInfocnt Infocnt ;
    WkTUser user;
	WkTInfo info;
	WkTDistribute dis,d;
	WkTInfocntId infocntid;
	WkTFlog flog;
	List slist;
	Html content;
	//详细信息界面涉及的各种组件
	Textbox kititle,kititle2,kbtitle,kikeys,kiimage,kisource,kimemo, kitype,kflmemo,taskname,share;
	Datebox kivaliddate;
	Toolbarbutton del,down,upfile,download,choose,save,reset;
	Toolbarbutton reback,deUpload,up,upImage,choice1;
	Listbox upList;
	Checkbox cuti,kishow;
	FCKeditor kicontent;
	Separator sep1,sep2;
	Hbox wdnews,tupian,suggest,pics,adv,wd,sharehbox;
	Window win;
	Label fileup;
	List nameList=new ArrayList();
	List userDeptList,flist,plist;
	ListModelList modelListbox;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		final WkTDistribute  di=(WkTDistribute)Executions.getCurrent().getAttribute("d");	  
		 modelListbox=new ListModelList(nameList);      
		//上传附件
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
		
    	pics.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {
				WkTInfo in=newsService.getInfobyBid(di.getKbId());
				WkTUser user=userService.getUserByuid(in.getKuId());
				 String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
				 System.out.println(path); 
				 File f=new File(path.trim()+"\\".trim()+newsService.getWkTInfo(dis.getKiId()).getKiImage().toString().trim());  
				   Filedownload.save(f,null);
			}   		
    	});
    	
    	choose.addEventListener(Events.ON_CLICK,  new EventListener(){
			public void onEvent(Event event) throws Exception {
				Executions.getCurrent().setAttribute("mul", "0");
				NewsauditTaskSelectWindow w=(NewsauditTaskSelectWindow)Executions.createComponents("/admin/content/newsaudit/taskselect.zul", null,null);
				w.doHighlighted();
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						NewsauditTaskSelectWindow ns=(NewsauditTaskSelectWindow) arg0.getTarget();
						plist=ns.getSlist();
						WkTExtractask et=(WkTExtractask) plist.get(0);
						taskname.setValue(et.getKeName());
					}
				});
			}	
		});
    	//选择共享栏目
		choice1.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {	
				Executions.getCurrent().setAttribute("mul","1");
				NewsauditTaskSelectWindow w=(NewsauditTaskSelectWindow)Executions.createComponents("/admin/personal/infoExtract/newsaudit/taskselect.zul", null,null);
			 	w.doHighlighted();
			 	w.initWindow();
			 	w.addEventListener(Events.ON_CHANGE, new EventListener(){
					public void onEvent(Event arg0) throws Exception 	
					{
			 	        Executions.getCurrent().setAttribute("state","1");
			 	       NewsauditTaskSelectWindow addwin=(NewsauditTaskSelectWindow)arg0.getTarget();
						slist=addwin.getSlist();
						StringBuffer sb=new StringBuffer();
						for(int i=0;i<slist.size();i++){
							WkTExtractask c=(WkTExtractask)slist.get(i);
							sb.append(c.getKeName().trim()+",");
							share.setValue(sb.toString());	
							WkTDistribute dist=new WkTDistribute();
							dist.setKeId(c.getKeId());
							dist.setKbFlag("1");
							dist.setKiId(dis.getKiId());
							newsService.save(dist);
						}			
					}
			 	});			 	
			}			
		});
	}
	
	//上传图片
	public void onClick$upImage() throws InterruptedException{
		Media media=null;
		try {
			media = Fileupload.get();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
		  if(media instanceof org.zkoss.image.Image){			   
			  pics.getChildren().clear();
				org.zkoss.zul.Image image = new org.zkoss.zul.Image();
				image.setContent((org.zkoss.image.Image) media);
				image.setWidth("25px");
				image.setHeight("25px");
				image.setParent(pics);
				Toolbarbutton b=new Toolbarbutton();
				b.setImage("/css/img/delAct.gif");
				b.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
				  public void onEvent(Event event) throws Exception {
				    pics.getChildren().clear();
				  }
				});
				b.setParent(pics);
		  }else{
			  Messagebox.show("请选择图片上传！", "上传错误", Messagebox.OK, Messagebox.ERROR);
		  }

	}
	
	//判断信息类型，根据类型显示不一样的界面，并获得详细信息
	public void initWindow(WkTDistribute dist) {
		this.dis= dist;
		WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		WkTExtractask et=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
		if(dis.getKbStatus().toString().trim().equals("2"))
		{
			adv.setVisible(true);
		}
	   if(dis.getKbFlag().toString().trim().equals("1"))
		 {
			 kititle.setReadonly(true);kititle2.setReadonly(true);content.setVisible(true);
			 kivaliddate.setDisabled(true);kikeys.setReadonly(true);kisource.setReadonly(true);
			 kimemo.setReadonly(true);  kishow.setDisabled(true);up.setDisabled(true);
			 upImage.setVisible(false);deUpload.setDisabled(true);adv.setVisible(false);
			 kicontent.setVisible(false);kbtitle.setReadonly(true);	 reback.setVisible(false);
			 choose.setDisabled(true);taskname.setReadonly(true);
			 sep1.setVisible(true);sep2.setVisible(true);save.setVisible(false);reset.setVisible(false);
		 }
	   if(dis.getKbStatus().toString().trim().equals("1"))
	   {
		   sharehbox.setVisible(true);
		   reback.setVisible(false);
		   List shareList= newsService.getNewsOfShareChanel(info.getKiId());
		   if(shareList.size()!=0)
		   {
		    StringBuffer sb=new StringBuffer();
		    for(int i=0;i<shareList.size();i++)
		    {
		    	WkTDistribute d=(WkTDistribute)shareList.get(i);
				sb.append(newsService.getChanelState(d.getKeId()).getKeName().trim()+",");
				share.setValue(sb.toString());		
		    }
		   }
	   }
				 initInfocnt(newsService.getChildNewsContent(info.getKiId()));
				 if(dis.getKbStrong().toString().trim().equals("1"))
					 cuti.setChecked(true);
				 if(dis.getKbMail().toString().trim().equals("1"))
					 kishow.setChecked(true);
		taskname.setValue(et.getKeName());
		kititle.setValue(info.getKiTitle());
		kititle2.setValue(info.getKiTitle2());
		kisource.setValue(info.getKiSource());
		kikeys.setValue(info.getKiKeys());
		kimemo.setValue(info.getKiMemo());
		kbtitle.setValue(dis.getKbTitle());
		 //加载附件
	       flist=newsService.getFile(dis.getKiId());
	       if(flist.size()!=0)
	       {
	       modelListbox.addAll(flist);
	      upList.setModel(modelListbox);	 
	       }
		//初始化显示图片
		if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0){
		   Image img=new Image();
			String pa = "/upload/info"+"/"+info.getKiImage().trim();
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
	

public WkTInfocnt getInfocnt()
{
	return Infocnt;
}
//初始化信息内容
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++){
			WkTInfocnt inf=(WkTInfocnt)rlist.get(i);
			con+=inf.getKiContent();
		}
		if(dis.getKbFlag().toString().trim().equals("0"))
		kicontent.setValue(con);
		else content.setContent(con);
	}

//信息发布
public void onClick$pass() throws InterruptedException, IOException
{  
	if(kititle.getValue().equals(""))
{Messagebox.show("标题不能为空！");
return;}
	if(Messagebox.show("确定要通过吗？", "确认", 
		Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
{ 
Date da=new Date();
 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
 WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
 if(kivaliddate.getText().trim().equals(""))
 {
	 info.setKiValiddate("1900-1-1");
 }
 else
 {
info.setKiValiddate(kivaliddate.getText());
 }
 if(dis.getKbStatus().toString().trim().equals("1"))
 {
	 info.setKuId(user.getKuId());
	 info.setKuName(user.getKuName());
 }
info.setKiTitle(kititle.getText());
info.setKiTitle2(kititle2.getText());
info.setKiSource(kisource.getText());
if(dis.getKbFlag().toString().trim().equals("0"))
{
if(!taskname.getValue().trim().equals(e.getKeName().trim()))
{
WkTExtractask et=(WkTExtractask) plist.get(0);
info.setKeId(et.getKeId());
}
}
if (newsService.getWkTInfo(dis.getKiId()).getKiType().toString().trim().equals("1"))
{
info.setKiAddress("");
}
info.setKiKeys(kikeys.getText());
info.setKiMemo(kimemo.getText());
info.setKiCtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
//保存图片
List clist=pics.getChildren();		 
if(clist.size()>0){
	Image img=(Image)clist.get(0);
 if(img.getContent()!=null){
	byte[] out=img.getContent().getByteData();
	String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
	String fileName=img.getContent().getName(); 
	String path = repSrc+"\\"+fileName;
	File f = new File(path);
	FileOutputStream fos = new FileOutputStream(new File(path));
	fos.write(out);
	fos.close();		
	info.setKiImage(fileName);
 }
}else{
	if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0){
		String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
		File f = new File(repSrc+"\\"+info.getKiImage().trim());
		if(f.exists()){
			f.delete();
		}
	}
	info.setKiImage("");
}
	 //保存信息内容至WkTInfocnt
if(dis.getKbFlag().toString().trim().equals("0"))
{
List cntlist=newsService.getInfocnt(dis.getKiId());
for(int i=0;i<cntlist.size();i++)
{
	 WkTInfocnt infocnt=(WkTInfocnt)cntlist.get(i);
	 newsService.delete(infocnt);
}
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
}
newsService.update(info);
//将信息的情况保存到WkTDistribute表中
if(dis.getKbFlag().toString().trim().equals("0"))
{
dis.setKeId(info.getKeId());
}
dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
dis.setKbTitle(kbtitle.getText());
if(cuti.isChecked())
	 dis.setKbStrong("1");
else dis.setKbStrong("0");
if(kishow.isChecked())
	dis.setKbMail("1");
	else
		dis.setKbMail("0");
//共享栏目保存
if(dis.getKbStatus().toString().trim().equals("1"))
{
if(share.getValue().equals(""))
{
	List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
	if(zlist.size()!=0)
	{
	for(int i=0;i<zlist.size();i++)
		newsService.delete((WkTDistribute)zlist.get(i));}
}
else 
{
	if(newsService.getNewsOfShareNew(dis.getKiId()).size()!=0)
	{
	 List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
	if(zlist.size()!=0)
	{
	for(int i=0;i<zlist.size();i++)
		newsService.delete((WkTDistribute)zlist.get(i));
	}
	List disnew= newsService.getNewsOfShareNew(dis.getKiId());
	for(int i=0;i<disnew.size();i++)
	{
		WkTDistribute	distri=(WkTDistribute)disnew.get(i);
		distri.setKbColor(dis.getKbColor());
		distri.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
		distri.setKbEm(dis.getKbEm());
		distri.setKbRight(dis.getKbRight());
		distri.setKbStatus("0");
		distri.setKbStrong(dis.getKbStrong());
		distri.setKbTitle(dis.getKbTitle());
		distri.setKbMail(dis.getKbMail());
	    newsService.update(distri);
	}	
	}
	else
	{
		List diso= newsService.getNewsOfShareChanel(dis.getKiId());
	for(int i=0;i<diso.size();i++)
	{
		WkTDistribute	distri=(WkTDistribute)diso.get(i);
 		distri.setKbColor(dis.getKbColor());
 		distri.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
 		distri.setKbEm(dis.getKbEm());
 		distri.setKbRight(dis.getKbRight());
		distri.setKbStatus("0");
 		distri.setKbStrong(dis.getKbStrong());
 		distri.setKbTitle(dis.getKbTitle());
 	    newsService.update(distri);		    		    		    	
	}	
	}
}
}
dis.setKbStatus("0");
newsService.update(dis);
//保存附件
if(modelListbox.getInnerList().size()!=0&&modelListbox.getInnerList()!=null)
{List flist=modelListbox.getInnerList();
  for(int i=0;i<flist.size();i++){
	  if(flist.get(i) instanceof Media)
	  {
	  Media media=(Media)flist.get(i);
	  String fileName=DateUtil.getDateTimeString(new Date())+"_"+info.getKiId().toString()+"_"+media.getName().toString(); 	
	  saveToFile( media,dis.getKiId(),info.getKuId());
		}
  }
}
mlogService.saveMLog(WkTMlog.FUNC_CMS, "审核通过信息，id:"+dis.getKiId(), user);
	Messagebox.show("发布成功！");
	Events.postEvent(Events.ON_CHANGE, this, null);
	win.detach();
}
}

//将信息退回给信息编写者
public void onClick$reback() throws InterruptedException, IOException
{
	  
	 if(kititle.getValue().equals(""))
	 {Messagebox.show("标题不能为空！");
	 return;}	
			if(Messagebox.show("确定要退回吗？", "确认", 
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
{
Date da=new Date();
WkTInfo info=newsService.getWkTInfo(dis.getKiId());
WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
info.setKiValiddate(kivaliddate.getText());
info.setKiTitle(kititle.getText());
info.setKiSource(kisource.getText());
info.setKiTitle2(kititle2.getText()); 
if (newsService.getWkTInfo(dis.getKiId()).getKiType().toString().trim().equals("1"))
{
info.setKiAddress(null);
}
if(!taskname.getValue().trim().equals(e.getKeName().trim()))
{
WkTExtractask et=(WkTExtractask) plist.get(0);
info.setKeId(et.getKeId());
}
info.setKiKeys(kikeys.getText());
info.setKiMemo(kimemo.getText());
info.setKiCtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
//保存图片
List clist=pics.getChildren();	
if(clist.size()>0){
	Image img=(Image)clist.get(0);
if(img.getContent()!=null){
	byte[] out=img.getContent().getByteData();
	String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
	String fileName=img.getContent().getName().trim(); 
	String path = repSrc+"\\"+fileName;
	File f = new File(repSrc+"\\"+info.getKiImage());
	if(f.exists()){
		f.delete();
	}
	f = new File(path);
	FileOutputStream fos = new FileOutputStream(new File(path));
	fos.write(out);
	fos.close();		
	info.setKiImage(fileName);
}
}else{
	if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0){
		String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
		File f = new File(repSrc+"\\"+info.getKiImage());
		if(f.exists()){
			f.delete();
		}
	}
	info.setKiImage("");
}
	 //保存信息内容至WkTInfocnt
List cntlist=newsService.getInfocnt(dis.getKiId());
for(int i=0;i<cntlist.size();i++)
{
	 WkTInfocnt infocnt=(WkTInfocnt)cntlist.get(i);
	 newsService.delete(infocnt);
}
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
newsService.update(info);
//保存附件
if(modelListbox.getInnerList().size()!=0&&modelListbox.getInnerList()!=null)
{List flist=modelListbox.getInnerList();
 for(int i=0;i<flist.size();i++){
	  if(flist.get(i) instanceof Media)
	  {
	  Media media=(Media)flist.get(i);
	  String fileName=DateUtil.getDateTimeString(new Date())+"_"+info.getKiId().toString()+"_"+media.getName().toString(); 	
	  saveToFile( media,dis.getKiId(),info.getKuId());
		}
 }
}
//将新信息的发布情况保存到WkTDistribute表中
dis.setKeId(info.getKeId());
dis.setKbStatus("4");
dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
dis.setKbTitle(kbtitle.getText());
if(cuti.isChecked())
	 dis.setKbStrong("1");
else dis.setKbStrong("0");
newsService.update(dis);
if(dis.getKbFlag().trim().equals("0"))
{
List blist=newsService.getDistributeList(dis.getKiId());
if(blist.size()>1)
{
for(int i=0;i<blist.size();i++)
{
	WkTDistribute dis=(WkTDistribute)blist.get(i);
	dis.setKbStatus("4");
	newsService.update(dis);
}
}
}
if(newsService.getDistributeShare(info.getKiId()).size()!=0)
{
	List list=newsService.getDistributeShare(info.getKiId());
	for(int i=0;i<list.size();i++)
	{
	WkTDistribute d=(WkTDistribute) newsService.getDistributeShare(info.getKiId()).get(i);
	d.setKbStatus("4");
	newsService.update(d);
	}
}
    //保存处理信息  
   WkTFlog flog=new WkTFlog();
	flog.setKflMemo(kflmemo.getText());
	flog.setKflTime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	flog.setKuId(user.getKuId());
	flog.setKuName(user.getKuName());
	flog.setKbId(dis.getKbId());
	flog.setKfId(null);
	newsService.save(flog);
	Messagebox.show("已成功将信息退回！");
	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	mlogService.saveMLog(WkTMlog.FUNC_CMS, "退回信息，id:"+dis.getKiId(), user);
	Events.postEvent(Events.ON_CHANGE, this, null);
	win.detach(); 		
}
		}

//保存信息至已阅信息列表
public void onClick$save() throws InterruptedException, IOException
{
    
	 if(kititle.getValue().equals(""))
	 {Messagebox.show("标题不能为空！");
	 return;}
	 Date da=new Date();
	 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
	 WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
	 info.setKiValiddate(kivaliddate.getText());
	 info.setKiTitle(kititle.getText());
	 info.setKiTitle2(kititle2.getText());
	 info.setKiTop("0");
	 info.setKiHits(0);
	 info.setKiSource(kisource.getText());
	 if(!taskname.getValue().trim().equals(e.getKeName().trim()))
	 {
	 WkTExtractask et=(WkTExtractask) plist.get(0);
	 info.setKeId(et.getKeId());
	 }
	if (newsService.getWkTInfo(dis.getKiId()).getKiType().toString().trim().equals("1"))
	 {
	 info.setKiAddress(null);
	 }
	 info.setKiKeys(kikeys.getText());
	 info.setKiMemo(kimemo.getText());
	 info.setKiCtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	//保存图片
	 List clist=pics.getChildren();		 
		if(clist.size()>0){
			Image img=(Image)clist.get(0);
		 if(img.getContent()!=null){
			byte[] out=img.getContent().getByteData();
			String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
			String fileName=img.getContent().getName(); 
			String path = repSrc+"\\"+fileName;
			File f = new File(repSrc+"\\"+info.getKiImage());
			if(f.exists()){
				f.delete();
			}
			f = new File(path);
			FileOutputStream fos = new FileOutputStream(new File(path));
			fos.write(out);
			fos.close();		
			info.setKiImage(fileName);
		 }
		}else{
			if(info.getKiImage()!=null&&info.getKiImage().trim().length()>0){
				String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
				File f = new File(repSrc+"\\"+info.getKiImage());
				if(f.exists()){
					f.delete();
				}
			}
			info.setKiImage("");
		}
	 	 //保存信息内容至WkTInfocnt
		  List cntlist=newsService.getInfocnt(dis.getKiId());
		     for(int i=0;i<cntlist.size();i++)
		     {
		    	 WkTInfocnt infocnt=(WkTInfocnt)cntlist.get(i);
		    	 newsService.delete(infocnt);
		     }
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
	 	
	 newsService.update(info);
	 //将新信息的发布情况保存到WkTDistribute表中
	 dis.setKeId(info.getKeId());
	
	 dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	 dis.setKbTitle(kbtitle.getText());
	 if(cuti.isChecked())
	 	 dis.setKbStrong("1");
	 else dis.setKbStrong("0");
	//共享栏目保存
	 if(dis.getKbStatus().toString().trim().equals("1"))
	 {
	 if(share.getValue().equals(""))
	 {
	 	List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
	 	if(zlist.size()!=0)
	 	{
	 	for(int i=0;i<zlist.size();i++)
	 		newsService.delete((WkTDistribute)zlist.get(i));}
	 }
	 else 
	 {
	 	if(newsService.getNewsOfShareNew(dis.getKiId()).size()!=0)
	 	{
	 	 List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
	 	if(zlist.size()!=0)
	 	{
	 	for(int i=0;i<zlist.size();i++)
	 		newsService.delete((WkTDistribute)zlist.get(i));
	 	}
	 	List disnew= newsService.getNewsOfShareNew(dis.getKiId());
	 	for(int i=0;i<disnew.size();i++)
	 	{
	 		WkTDistribute	distri=(WkTDistribute)disnew.get(i);
	 		distri.setKbColor(dis.getKbColor());
	 		distri.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	 		distri.setKbEm(dis.getKbEm());
	 		distri.setKbRight(dis.getKbRight());
	 		distri.setKbStatus("1");
	 		distri.setKbStrong(dis.getKbStrong());
	 		distri.setKbTitle(dis.getKbTitle());
	 		distri.setKbMail(dis.getKbMail());
	 	    newsService.update(distri);
	 	}	
	 	}
	 	else
	 	{
	 		List diso= newsService.getNewsOfShareChanel(dis.getKiId());
	 	for(int i=0;i<diso.size();i++)
	 	{
	 		WkTDistribute	distri=(WkTDistribute)diso.get(i);
	  		distri.setKbColor(dis.getKbColor());
	  		distri.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	  		distri.setKbEm(dis.getKbEm());
	  		distri.setKbRight(dis.getKbRight());
	 		distri.setKbStatus("1");
	  		distri.setKbStrong(dis.getKbStrong());
	  		distri.setKbTitle(dis.getKbTitle());
	  	    newsService.update(distri);		    		    		    	
	 	}	
	 	}
	 }
	 }
	 if(dis.getKbStatus().toString().trim().equals("1"))
	 {
dis.setKbStatus("1");
	 }
else  if(dis.getKbStatus().toString().trim().equals("2"))
	 {
	 dis.setKbStatus("3");
	 }
	 newsService.update(dis);
	//保存附件
		if(modelListbox.getInnerList().size()!=0&&modelListbox.getInnerList()!=null)
		{List flist=modelListbox.getInnerList();
		  for(int i=0;i<flist.size();i++){
			  if(flist.get(i) instanceof Media)
			  {
			  Media media=(Media)flist.get(i);
			  String fileName=DateUtil.getDateTimeString(new Date())+"_"+info.getKiId().toString()+"_"+media.getName().toString(); 	
			  saveToFile( media,dis.getKiId(),info.getKuId());
				}
		  }
		} 
		WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	mlogService.saveMLog(WkTMlog.FUNC_CMS, "已阅信息，id:"+dis.getKiId(), user);	
	Messagebox.show("保存成功！");
	Events.postEvent(Events.ON_CHANGE, this, null);
	win.detach();
}
//保存上传的附件至附件列表
public void saveToFile(Media media,Long infoid,Long kuid) throws IOException{
	if (media != null) { 
		//isBinary二进制文件情况，如下处理
		if(media.isBinary()){			
			InputStream objin=media.getStreamData(); 
			String fileName=DateUtil.getDateTimeString(new Date())+"_"+infoid.toString()+"_"+media.getName().toString(); 
			String Name=media.getName(); 
			String pa=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");	
		    if(pa == null){
				    System.out.println("无法访问存储目录！");
				    return;
		    }		    
		    File fUploadDir = new File(pa);	
		    if(!fUploadDir.exists()){
				    if(!fUploadDir.mkdir()){
				    	   System.out.println("无法创建存储目录！");                             
					       return;
				    }			
		     }
			String path1=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");	
			 if(path1 == null){
			    	System.out.println("无法访问存储目录！");
				    return;
			 }	
			 File fUploadDir2 = new File(path1);	                                   
			 if(!fUploadDir2.exists()){
				    if(!fUploadDir2.mkdir()){
				    	 System.out.println("无法创建存储目录！");
					     return;
				     }			
			 }	
			String path=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info")+"//"+fileName.trim();
		    FileOutputStream out=null;
			out = new FileOutputStream(path);
		    DataOutputStream objout=new DataOutputStream(out); 		   
			Files.copy(objout,objin);
			
			if(out!=null){
				out.close();
			}	
			 WkTFile file=new WkTFile();
		       WkTFileId fileid=new WkTFileId(infoid,fileName,media.getName().toString(),kuid,"1","0");
		        file.setId(fileid);
		        newsService.save(file);
	 }
		//否则做如下处理	：
		else{
		 
		 if(media.getName().endsWith(".txt")||media.getName().endsWith(".project")){
	            Reader r = media.getReaderData(); 
	            String fileName=DateUtil.getDateTimeString(new Date())+"_"+infoid.toString()+"_"+media.getName().toString(); 
				String Name=media.getName();  
			    String pa=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");	
			    if(pa == null){
					    System.out.println("无法访问存储目录！");
					    return;
			    }		    
			    File fUploadDir = new File(pa);	
			    if(!fUploadDir.exists()){
					    if(!fUploadDir.mkdir()){
					    	   System.out.println("无法创建存储目录！");                          
						       return;
					    }			
			     }
		    
			    String path1=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");	
		        if(path1 == null){
			    	System.out.println("无法访问存储目录！");
				    return;
		        }	
			    File fUploadDir2 = new File(path1);	                                 
			    if(!fUploadDir2.exists()){
				    if(!fUploadDir2.mkdir()){
				    	 System.out.println("无法创建存储目录！");
					     return;
				     }			
			    }		    
			    String path2=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info"+"//"+fileName.trim());
			    File f = new File(path2);
			    Files.copy(f,r,null);
			    Files.close(r);	
			    WkTFile file=new WkTFile();
			       WkTFileId fileid=new WkTFileId(infoid,fileName,media.getName().toString(),kuid,"1","0");
			        file.setId(fileid);
			        newsService.save(file);
		 	}
		 
	 	}
	  }
	
}
//下载或查看图片
public void onClick$pics() throws FileNotFoundException
	{
		 String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
		   File f=new File(path.trim()+"\\".trim()+newsService.getWkTInfo(dis.getKiId()).getKiImage().toString().trim());  
		   Filedownload.save(f,null);
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

//删除上传的文件
public void onClick$deUpload(){
	
	 if(modelListbox.getSize()==0) return;
	 Listitem it=upList.getSelectedItem();		 
	 if(it==null) {
		 if(modelListbox.getSize()>0){
			 it=upList.getItemAtIndex(0);
		 }
	 }
	 
	 if(it.getValue() instanceof WkTFile){
	   WkTFile f=(WkTFile)it.getValue();
	   String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
	   File file=new File(path+"\\"+f.getId().getKfName());
	   if(file.exists()){
		 file.delete();
	   }
	   newsService.delete(it.getValue());
	 }
	 modelListbox.remove(it.getIndex());
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
		 //删除引用此信息的信息
		 List slist=newsService.getInfoSiteTime(dis.getKbId().toString());
		 if(slist.size()!=0)
		 { 
			 for(int i=0;i<slist.size();i++)
			 { WkTInfo in=(WkTInfo) slist.get(i);
			 List dlist= newsService.getDistributeList(in.getKiId());
			 if(dlist.size()!=0)
			 {
				 for(int j=0;j<dlist.size();j++)
				 {
				 WkTDistribute d=(WkTDistribute) dlist.get(j);
				 newsService.delete(d);
			     }
			 }
			 newsService.delete(in);
			 }
		 }
		List d=newsService.getDistributeList(dis.getKiId());
		for(int i=0;i<d.size();i++)
		{
		 newsService.delete((WkTDistribute)d.get(i));
		}
		 newsService.delete(newsService.getWkTInfo(dis.getKiId()));
		 List cnt=newsService.getInfocnt(dis.getKiId());
		 for(int j=0;j<cnt.size();j++)
			 newsService.delete((WkTInfocnt)cnt.get(j));
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
	         this.detach();
	         Events.postEvent(Events.ON_CHANGE, this, null);}
}
//重置
public void onClick$reset()
{pics.getChildren().clear();
kflmemo.setValue("");
	initWindow(dis);
}
public ListModelList getModelList()
{
	return modelListbox;
}
}