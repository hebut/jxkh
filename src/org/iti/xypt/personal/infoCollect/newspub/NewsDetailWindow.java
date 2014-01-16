   package org.iti.xypt.personal.infoCollect.newspub;
/**
 * 显示信息详情界面
 * @author whm
 * 2010-3-20
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
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
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;


public class NewsDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	//信息数据访问接口
	NewsService newsService;
	TaskService taskService;
	//管理日志数据访问接口
	MLogService mlogService;
    WkTInfocnt Infocnt ;
	WkTInfo info,in,i;
	WkTDistribute dis,d;
	WkTInfocntId infocntid;
	WkTFlog flog;
	WkTFile wfile;
	WkTUser user;
	WkTFileId fileid;
	//详细信息界面涉及的各种组件
	Textbox kititle,kititle2,kbtitle,kikeys,kiimage,kisource,kimemo,kcid,share,advice,taskname;
	Datebox kivaliddate;
	Toolbarbutton choice,up,down,choice1,download,upImage,upfile,choose;
	Checkbox cuti,kimail,kisite;
	FCKeditor kicontent;
	Hbox tupian,suggest,pics;
	ListModelList writeListModel,rebackListModel,auditListModel,readListModel,pubListModel;
	Tab writerTab,readTab,auditTab,rebackTab,pubTab;
	Window win;
	Html add;
	Label file;
	Listbox upList;
	List nameList=new ArrayList();
	List userDeptList,flist,slist,silist,zlist,plist;
	ListModelList modelListbox;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
        modelListbox=new ListModelList(nameList);
    	user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
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
		//选择共享栏目
		choice1.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {	
				Executions.getCurrent().setAttribute("mul","1");
				NewsTaskSelectWindow w=(NewsTaskSelectWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/newtasksel.zul", null,null);
			 	w.doHighlighted();
			 	w.initWindow();
			 	w.addEventListener(Events.ON_CHANGE, new EventListener(){
					public void onEvent(Event arg0) throws Exception 	
					{
			 	        Executions.getCurrent().setAttribute("state","1");
						NewsTaskSelectWindow addwin=(NewsTaskSelectWindow)arg0.getTarget();
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
		//选择发布频道
		choose.addEventListener(Events.ON_CLICK,  new EventListener(){
			public void onEvent(Event event) throws Exception {
				Executions.getCurrent().setAttribute("mul","0");
				NewsTaskSelectWindow w=(NewsTaskSelectWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/newtasksel.zul", null,null);
				w.doHighlighted();
				w.initWindow();
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						NewsTaskSelectWindow w=(NewsTaskSelectWindow) arg0.getTarget();
						plist=w.getSlist();
						WkTExtractask et=(WkTExtractask) plist.get(0);
						taskname.setValue(et.getKeName());
					}
				});
			}	
		});
	}

	private String initflog(WkTFlog flog) 
	{
		this.flog=flog;
		return  flog.getKflMemo();
	}
	
//判断信息类型，根据类型显示不一样的界面，并获得详细信息
	public void initWindow(WkTDistribute dis) throws IOException
	{
		  this.dis=dis;
		  WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
		  String state=dis.getKbStatus().toString().trim();
		 if(state.equals("1"))
		 {
			 suggest.setVisible(false);
		 }
		 else if(state.equals("4"))
		 { 
			 List floglist=newsService.getflog(dis.getKbId());
			 if(floglist.size()!=0)
			 {
			 WkTFlog flog=(WkTFlog) floglist.get(0);
			 advice.setText(initflog(flog));	
			 }
		 } 
		 if(dis.getKbMail().trim().equals("1"))
			 kimail.setChecked(true);
		initInfocnt(newsService.getChildNewsContent(info.getKiId()));
		kititle.setValue(info.getKiTitle());
		kititle2.setValue(info.getKiTitle2());
		kikeys.setValue(info.getKiKeys());
		kimemo.setValue(info.getKiMemo());
		kisource.setValue(info.getKiSource());
		kbtitle.setValue(dis.getKbTitle());
		taskname.setValue(e.getKeName());
		kivaliddate.setText(info.getKiValiddate().toString());
		  List shareList= newsService.getNewsOfShareChanel(info.getKiId());
		    StringBuffer sb=new StringBuffer();
		    for(int i=0;i<shareList.size();i++)
		    {
		    	WkTDistribute d=(WkTDistribute)shareList.get(i);
				sb.append(newsService.getChanelState(d.getKeId()).getKeName().trim()+",");
				share.setValue(sb.toString());		
		    }
		if(dis.getKbMail().toString().trim().equals("1"))
				{
			kimail.setChecked(true);
				}
		if(dis.getKbStrong().toString().trim().equals("1"))
		{
			cuti.setChecked(true);
		}	
       //加载附件
         flist=newsService.getFile(dis.getKiId());
		 modelListbox.addAll(flist);
	     upList.setModel(modelListbox);
 		pics.getChildren().clear();
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

//获取信息内容
public void  initInfocnt(List rlist)
	{
		String con="";
		for(int i=0;i<rlist.size();i++)
		{
			WkTInfocnt inf=(WkTInfocnt)rlist.get(i);
			con+=inf.getKiContent();
		}
		kicontent.setValue(con);
	}
//保存信息
public void onClick$save() throws InterruptedException, IOException
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
	  WkTInfo info=newsService.getWkTInfo(dis.getKiId());
	  WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(dis.getKeId()).get(0);
	info.setKiTitle(kititle.getValue());
	info.setKiTitle2(kititle2.getValue());
	if(!taskname.getValue().trim().equals(e.getKeName().trim()))
	{
	WkTExtractask et=(WkTExtractask) plist.get(0);
	info.setKeId(et.getKeId());
	}
	info.setKiKeys(kikeys.getValue());
	info.setKiMemo(kimemo.getValue());
	info.setKiValiddate(kivaliddate.getText());
	info.setKiSource(kisource.getValue());
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
         //保存附件
 		if(modelListbox.getInnerList().size()!=0&&modelListbox.getInnerList()!=null)
 		{List flist=modelListbox.getInnerList();
 		  for(int i=0;i<flist.size();i++){
 			  if(flist.get(i) instanceof Media)
 			  {
 			  Media media=(Media)flist.get(i);
 			  String fileName=DateUtil.getDateTimeString(new Date())+"_"+info.getKiId().toString()+"_"+media.getName().toString(); 	
 			  saveToFile( media,info.getKiId(),info.getKuId());
 				WkTFile file=new WkTFile();
 		       WkTFileId fileid=new WkTFileId(info.getKiId(),fileName,media.getName().toString(),info.getKuId(),"1","0");
 		        file.setId(fileid);
 		        newsService.save(file);
 				}
 		  }
 		}  
     dis.setKeId(info.getKeId());
     dis.setKbTitle(kbtitle.getValue());
     dis.setKbStatus("1");
     if(kimail.isChecked())
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
     newsService.update(dis);
     //保存共享栏目记录
     if(share.getValue().equals(null))
     {
    	List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
    	if(zlist.size()!=0)
    	{
    	for(int i=0;i<zlist.size();i++)
    		newsService.delete((WkTDistribute)zlist.get(i));
    	}
     }
     else
     {
    List disnew= newsService.getNewsOfShareNew(dis.getKiId());
    List zlist= newsService.getNewsOfShareChanel(dis.getKiId());
    if(disnew.size()!=0)
    {
 	if(zlist.size()!=0)
	{
	for(int i=0;i<zlist.size();i++)
		newsService.delete((WkTDistribute)zlist.get(i));
	}
     	for(int i=0;i<disnew.size();i++)
     	{
     		WkTDistribute	distri=(WkTDistribute)disnew.get(i);
     		Date d=new Date();
     		distri.setKbDtime(ConvertUtil.convertDateAndTimeString(d.getTime()));
     		distri.setKbStatus("1");
     		distri.setKbMail(dis.getKbMail());
     		distri.setKbStrong(dis.getKbStrong());
     		distri.setKbTitle(dis.getKbTitle());
     	    newsService.update(distri);
     	}	
    }
     }
     Messagebox.show("保存成功！");
	 this.detach();
	 Events.postEvent(Events.ON_CHANGE, this, null);
}
//发布信息
public void onClick$saudit() throws InterruptedException, IOException
{

	 if(kititle.getValue().equals(null))
		{
		 Messagebox.show("标题不能为空！");
         return;
        }
	 //更新WkTInfo表
	 Date da=new Date();
	 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
    info.setKiValiddate(kivaliddate.getText());
	 info.setKiTitle(kititle.getText());
	 info.setKiTitle2(kititle2.getText());
	 info.setKiKeys(kikeys.getText());
    info.setKiSource(kisource.getText());
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
   	 info.setKiAddress(null); 
   	 //更新WkTInfocnt表
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
   	     infocnt.setKiFlag("0");
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
			  saveToFile( media,info.getKiId(),info.getKuId());
			  WkTFile file=new WkTFile();
		       WkTFileId fileid=new WkTFileId(info.getKiId(),fileName,media.getName().toString(),info.getKuId(),"1","0");
		        file.setId(fileid);
		        newsService.save(file);
				}
		  }
		}
		 //更新WkTDistribute表
		dis.setKeId(info.getKeId());
    //如果所在频道需要审核则送审，否则直接发布
dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
dis.setKbTitle(kbtitle.getText());
dis.setKbFlag("0");
if(cuti.isChecked())
	 dis.setKbStrong("1");
else 
	 dis.setKbStrong("0");
WkTExtractask e=(WkTExtractask) taskService.getTaskBykeId(info.getKeId()).get(0);
WkTTasktype t=(WkTTasktype) taskService.getTpyeById(e.getKtaId());
    if(t.getKtaAudit().toString().trim().equals("0"))
		{dis.setKbStatus("0");}
		else 
			{dis.setKbStatus("2");}
    newsService.update(dis);
   //共享栏目保存
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
    		WkTExtractask et=(WkTExtractask) taskService.getTaskBykeId(distri.getKeId()).get(0);
    		WkTTasktype tt=(WkTTasktype) taskService.getTpyeById(e.getKtaId());
    		if(tt.getKtaAudit().toString().equals("0"))
    		distri.setKbStatus("0");
    		else distri.setKbStatus("2");
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
     		WkTExtractask et=(WkTExtractask) taskService.getTaskBykeId(distri.getKeId()).get(0);
    		WkTTasktype tt=(WkTTasktype) taskService.getTpyeById(e.getKtaId());
    		if(tt.getKtaAudit().toString().equals("0"))
    		distri.setKbStatus("0");
    		else distri.setKbStatus("2");
     		distri.setKbStrong(dis.getKbStrong());
     		distri.setKbTitle(dis.getKbTitle());
     	    newsService.update(distri);		    		    		    	
    	}	
   	}
    }
    //删除共享到主栏目的信息
	  if(share.getText().toString()!="")
	  {
		  List di=newsService.getDistribute(info.getKiId(),dis.getKeId());
		 if(di.size()!=0)
			 newsService.delete((WkTDistribute)di.get(0));
	  }
	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
    mlogService.saveMLog(WkTMlog.FUNC_CMS, "发布信息，id:"+info.getKiId(), user);
    Messagebox.show("操作成功！");
    this.detach();
    Events.postEvent(Events.ON_CHANGE, this, null);
				}
				
 //保存上传的附件
 public void saveToFile(Media media,Long mlid,Long kuid) throws IOException{
		if (media != null) { 
			InputStream objin=media.getStreamData(); 
			String fileName=DateUtil.getDateTimeString(new Date())+"_"+mlid.toString()+"_"+media.getName().toString(); 	
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
		    String uploadDir=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");	
			 if(uploadDir == null){
			    	System.out.println("无法访问存储目录！");
				    return;
			 }	
			 
			 File fUploadDir2 = new File(uploadDir);	//在upload文件下创建登录用户文件夹
			 String path2=fUploadDir2.getCanonicalPath(); //保存在path路径下
			 if(!fUploadDir2.exists()){
				    if(!fUploadDir2.mkdir()){
				    	 System.out.println("无法创建存储目录！");
					     return;
				     }			
			 }		    	
			    String name=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info"+"//"+fileName.toString().trim());	
		    FileOutputStream out=null;
			try {
				out = new FileOutputStream(name);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    DataOutputStream objout=new DataOutputStream(out); 
		    try {
				Files.copy(objout,objin);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			if(out!=null){
				out.close();
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
				Button b=new Button();
				b.setLabel("删除");
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
//重置
 public void onClick$reset() throws IOException
 {
 	initWindow(dis);
 }
 public void onClick$back()
 {
	 win.detach();
 }
//删除单条信息
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
		 //删除信息内容
		 WkTInfo  info=newsService.getWkTInfo(dis.getKiId());
		 if(info.getKiType().trim().equals("1"))
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
		 }
		 else if(dis.getKbFlag().toString().trim().equals("1"))
				 {
			 newsService.delete(dis);
				 }
		WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		 mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+dis.getKiId(), user);
		 Messagebox.show("删除成功！");	
         win.detach();
         Events.postEvent(Events.ON_CHANGE, this, null);
		}
 }

 public ListModelList getModelList()
 {
 	return modelListbox;
 }
}
