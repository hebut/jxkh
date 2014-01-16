package org.iti.xypt.personal.infoCollect.newspub;
/**
 * ������������Ϣ����
 * 2010-3-19
 * @author whm
 */
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
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocntId;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Form;
import org.zkoss.zhtml.Input;
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
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.common.listbox.SourceListbox;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

public class NewsNewWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 1L;
	WkTInfo info,in;
	WkTExtractask task;
	WkTInfocnt infocnt;
	WkTInfocntId infocntid;
	WkTFile file;
	WkTFileId fileid;
	WkTUser user;
	WkTDistribute dis,d;
	//��Ϣ���ݷ��ʽӿ�
	NewsService newsService;
	//�û����ݷ��ʽӿ�
	UserService userService;
	//������־���ݷ��ʽӿ�
	MLogService mlogService;
	RoleService roleService;
	TaskService taskService;
	//�ݴ���Ŀ�б�
	List slist,clist; 
	//��Ϣ�������õĵĸ������
	Textbox kititle,kititle2,kiordno,kbtitle,kikeys,kisource,kimemo,share,taskname;
	Datebox kivaliddate;
	Toolbarbutton choice,choice1,save,saudit,reset,back,choose;
	Listbox fjlist,kitype;
	Checkbox cuti,kishow;
	Hbox fjnews,wdnews,tupian,pics;
	Form kiimage;
	Label lfile;
	ChanelListbox pselected;
	FCKeditor kicontent;
	SourceListbox kisource1;
	Listbox upList;
	List nameList=new ArrayList();
	 List userDeptList,plist;
	ListModelList modelList;
	public void afterCompose() {	
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		userDeptList=(List)Sessions.getCurrent().getAttribute("userDeptList");
		kisource1.initSourceSelect();
		modelList=new ListModelList(nameList);	
		
		upList.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				Media name=(Media)arg1;
				arg0.setValue(arg1);
				arg0.setLabel(name.getName());
				String namefile=name.getName().toString();
				 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");//��õ�¼�û�
				String savename=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info")+"//"+namefile;	//�ļ������� 
			}
		});
		upList.setModel(modelList);	
		//ѡ�񷢲�Ƶ��
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
						Sessions.getCurrent().setAttribute("sel","1");
					}
				});
			}	
		});
		//ѡ������Ŀ
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
							}			
						}
				 	});			 	
				}			
			});
	}
	public void initWindow(WkTExtractask task)
	{
		this.task= task;	
		taskname.setValue(task.getKeName());
		Sessions.getCurrent().setAttribute("sel","0");
	}
	public void onSelect$kisource1()
	{
		kisource.setValue(kisource1.getSelectedItem().getLabel());
	}
	//�ϴ�ͼƬ
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
				b.setLabel("ɾ��");
				b.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
				  public void onEvent(Event event) throws Exception {
				    pics.getChildren().clear();
				  }
				});
				b.setParent(pics);
		  }else{
			  Messagebox.show("��ѡ��ͼƬ�ϴ���", "�ϴ�����", Messagebox.OK, Messagebox.ERROR);
		  }

	}
	//��������Ϣ
	public void onClick$save() throws InterruptedException, IOException
	{
		  if(kititle.getValue().equals("")){				
			  try {
					Messagebox.show("���ⲻ��Ϊ�գ�", "Information", Messagebox.OK, Messagebox.INFORMATION);
					kititle.focus();
			  } catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			//������Ϣ������WkTInfo����
			WkTInfo info=new WkTInfo();
			 Date da=new Date();
			 if(kivaliddate.getText().equals(""))
			 {
				 info.setKiValiddate("1900-1-1");
			 }
			 else
			 {
			 info.setKiValiddate(kivaliddate.getText());
			 }
			 info.setKiTitle(kititle.getText());
			 info.setKiTitle2(kititle2.getText());
			 info.setKiTop("0");
			 info.setKiHits(0);
			 info.setKiOrdno(10);
			 info.setKiKeys(kikeys.getText());
			 info.setKuId(user.getKuId());
			 info.setKuName(user.getKuName());
		     info.setKiSource(kisource.getText());
		     info.setKiMemo(kimemo.getText());
		     info.setKiPtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	    	 info.setKiType("1");
	    	 info.setKiShow("1");
	    	 info.setKiAuthname(user.getKuName());
	    	 info.setKiAddress(null);
	    	 String sel=(String)Sessions.getCurrent().getAttribute("sel");
	    	 System.out.println(sel);
	    	 if(sel.equals("1"))
	    	 {
	    		 WkTExtractask et=(WkTExtractask) plist.get(0);
	    		 info.setKeId(et.getKeId());
	    	 }
	    	 else
	    	 {
	    		 info.setKeId(task.getKeId());
	    	 }
		     newsService.save(info);
		   //����ͼƬ
				List clist=pics.getChildren();		 
					if(clist.size()>0){
						Image img=(Image)clist.get(0);
						 if(img.getContent()!=null){
						byte[] out=img.getContent().getByteData();
						String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
						  if(repSrc == null){
							    System.out.println("�޷����ʴ洢Ŀ¼��");
							    return;
					     }
						  File fUploadDir = new File(repSrc);	
						    if(!fUploadDir.exists()){
								    if(!fUploadDir.mkdir()){
								    	   System.out.println("�޷������洢Ŀ¼��");
									       return;
								    }			
						     }
						String fileName=img.getContent().getName(); 
						String path = repSrc+"\\"+fileName;
						File f = new File(path);
						FileOutputStream fos = new FileOutputStream(new File(path));
						fos.write(out);
						fos.close();		
						info.setKiImage(fileName);
						newsService.update(info);
						 }
					}
					 
			//���渽��
					if(modelList.getInnerList().size()!=0&&modelList.getInnerList()!=null)
					{List flist=modelList.getInnerList();
					  for(int i=0;i<flist.size();i++){
						  Media media=(Media)flist.get(i);
						   saveToFile(media,info.getKiId(),info.getKuId());	 
							}
					  }
					
		   //������Ϣ������WkTInfocnt
					if(info.getKiType().toString().trim().equals("1"))
					{
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
					    
					
		 //������Ϣ�ķ���������浽WkTDistribute����
			  WkTDistribute dis=new WkTDistribute();
			dis.setKbStatus("1");	
		    dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
		    dis.setKbTitle(kbtitle.getText());
		    dis.setKiId(info.getKiId());   
		    if(cuti.isChecked())
		   	 dis.setKbStrong("1");
		    else dis.setKbStrong("0");
			dis.setKeId((info.getKeId()));
			dis.setKbFlag("0");
			if(kishow.isChecked())  dis.setKbMail("1");
			else dis.setKbMail("0");
			  newsService.save(dis);
			  if(share.getText().toString()!="")
			  {
		     for(int i=0;i<slist.size();i++){
		    	 WkTExtractask e=(WkTExtractask)slist.get(i);
					dis.setKeId(e.getKeId());
					dis.setKbFlag("1");
					dis.setKbStatus("1");	
					dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
					dis.setKbTitle(kbtitle.getText());
				    dis.setKiId(info.getKiId());   
					if(cuti.isChecked())
					   dis.setKbStrong("1");
					 else dis.setKbStrong("0");
					if(kishow.isChecked())  dis.setKbMail("1");
					else dis.setKbMail("0");
					    newsService.save(dis);
				}
			  }
			  //ɾ����������Ŀ����Ϣ
			  if(share.getText().toString()!="")
			  {
				 List di=newsService.getDistribute(info.getKiId(),info.getKeId());
				 if(di.size()!=0)
					 newsService.delete((WkTDistribute)di.get(0));
			  }
		     try {
		    	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		  	   mlogService.saveMLog(WkTMlog.FUNC_CMS, "������Ϣ��id:"+info.getKiId(), user);
					Messagebox.show("����ɹ���", "Information", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
				Events.postEvent(Events.ON_CHANGE, this, null);
			this.detach();
					}
	//�����ϴ��ĸ����������б�
	public void saveToFile(Media media,Long infoid,Long kuid) throws IOException{
		if (media != null) { 
			//isBinary�������ļ���������´���
			if(media.isBinary()){			
				InputStream objin=media.getStreamData(); 
				String fileName=DateUtil.getDateTimeString(new Date())+"_"+infoid.toString()+"_"+media.getName().toString(); //����������
				String pa=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
			    if(pa == null){
					    System.out.println("�޷����ʴ洢Ŀ¼��");
					    return;
			    }		    
			    File fUploadDir = new File(pa);	
			    if(!fUploadDir.exists()){
					    if(!fUploadDir.mkdir()){
					    	   System.out.println("�޷������洢Ŀ¼��");                             
						       return;
					    }			
			     }
				String path1=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
				 if(path1 == null){
				    	System.out.println("�޷����ʴ洢Ŀ¼��");
					    return;
				 }	
				 File fUploadDir3 = new File(path1);	
				 if(!fUploadDir3.exists()){
						    if(!fUploadDir3.mkdir()){
						    	   System.out.println("�޷������洢Ŀ¼��");                        		
							       return;
						    }			
				  }
				 File fUploadDir2 = new File(path1+"\\"+infoid);	                                   	
				 if(!fUploadDir2.exists()){
					    if(!fUploadDir2.mkdir()){
					    	 System.out.println("�޷������洢Ŀ¼��");
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
			       WkTFileId fileid=new WkTFileId(infoid,fileName,media.getName().toString().trim(),kuid,"1","0");
			        file.setId(fileid);
			        newsService.save(file);
				
		//���������´���	��
		 }else{
			 
			 if(media.getName().endsWith(".txt")||media.getName().endsWith(".project")){
		            Reader r = media.getReaderData(); 
		            String fileName=DateUtil.getDateTimeString(new Date())+"_"+infoid.toString()+"_"+media.getName().toString(); //��������
				    String pa=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info");
				    if(pa == null){
						    System.out.println("�޷����ʴ洢Ŀ¼��");
						    return;
				    }		    
				    File fUploadDir = new File(pa);	
				    if(!fUploadDir.exists()){
						    if(!fUploadDir.mkdir()){
						    	   System.out.println("�޷������洢Ŀ¼��");                             
							       return;
						    }			
				     }
				    String path2=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/info")+"//"+fileName.trim();
				    File f = new File(path2);
				    Files.copy(f,r,null);
				    Files.close(r);	
				    WkTFile file=new WkTFile();
				       WkTFileId fileid=new WkTFileId(infoid,fileName,media.getName().toString().trim(),kuid,"1","0");
				        file.setId(fileid);
				        newsService.save(file);
			 	}
			 
		 	}
		  }
		
	}
	//��Ϣ���沢����
	public void onClick$saudit() throws InterruptedException, IOException
	{
		  if(kititle.getValue().equals("")){				
			  try {
					Messagebox.show("���ⲻ��Ϊ�գ�", "Information", Messagebox.OK, Messagebox.INFORMATION);
					kititle.focus();
			  } catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}
			//������Ϣ������WkTInfo����
			WkTInfo info=new WkTInfo();
			 Date da=new Date();
			 if(kivaliddate.getText().equals(""))
			 {
				 info.setKiValiddate("1900-1-1");
			 }
			 else
			 {
			 info.setKiValiddate(kivaliddate.getText());
			 }
			 info.setKiTitle(kititle.getText());
			 info.setKiTitle2(kititle2.getText());
			 info.setKiTop("0");
			 info.setKiHits(0);
			 info.setKiOrdno(10);
			 info.setKiKeys(kikeys.getText());
			 info.setKuId(user.getKuId());
			 info.setKuName(user.getKuName());
		     info.setKiSource(kisource.getText());
		     info.setKiMemo(kimemo.getText());
		     info.setKiPtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
	    	 info.setKiType("1");
	    	 info.setKiShow("1");
	    	 info.setKiAuthname(user.getKuName());
	    	 info.setKiAddress(null);
	    	 String sel=(String)Sessions.getCurrent().getAttribute("sel");
	    	 System.out.println(sel);
	    	 if(sel.equals("1"))
	    	 {
	    		 WkTExtractask et=(WkTExtractask) plist.get(0);
	    		 info.setKeId(et.getKeId());
	    	 }
	    	 else
	    	 {
	    		 info.setKeId(task.getKeId());
	    	 }
		     newsService.save(info);
		   //����ͼƬ
				List clist=pics.getChildren();		 
					if(clist.size()>0){
						Image img=(Image)clist.get(0);
						 if(img.getContent()!=null){
						byte[] out=img.getContent().getByteData();
						String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/info");
						  if(repSrc == null){
							    System.out.println("�޷����ʴ洢Ŀ¼��");
							    return;
					     }
						  File fUploadDir = new File(repSrc);	
						    if(!fUploadDir.exists()){
								    if(!fUploadDir.mkdir()){
								    	   System.out.println("�޷������洢Ŀ¼��");
									       return;
								    }			
						     }
						String fileName=img.getContent().getName(); 
						String path = repSrc+"\\"+fileName;
						File f = new File(path);
						FileOutputStream fos = new FileOutputStream(new File(path));
						fos.write(out);
						fos.close();		
						info.setKiImage(fileName);
						newsService.update(info);
						 }
					}
					 
			//���渽��
					if(modelList.getInnerList().size()!=0&&modelList.getInnerList()!=null)
					{List flist=modelList.getInnerList();
					  for(int i=0;i<flist.size();i++){
						  Media media=(Media)flist.get(i);
						   saveToFile(media,info.getKiId(),info.getKuId());	 
							}
					  }
					
		   //������Ϣ������WkTInfocnt
					if(info.getKiType().toString().trim().equals("1"))
					{
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
					    
					
		 //������Ϣ�ķ���������浽WkTDistribute����
			  WkTDistribute dis=new WkTDistribute();
			  WkTExtractask ee=(WkTExtractask) taskService.getTaskBykeId(info.getKeId()).get(0);
			  WkTTasktype type=taskService.getTpyeById(ee.getKtaId());
			  if(type.getKtaAudit().toString().trim().equals("0"))
		  	dis.setKbStatus("0");	
			  else  dis.setKbStatus("2");
		    dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
		    dis.setKbTitle(kbtitle.getText());
		    dis.setKiId(info.getKiId());   
		    if(cuti.isChecked())
		   	 dis.setKbStrong("1");
		    else dis.setKbStrong("0");
			dis.setKeId((info.getKeId()));
			dis.setKbFlag("0");
			if(kishow.isChecked())  dis.setKbMail("1");
			else dis.setKbMail("0");
			  newsService.save(dis);
			  if(share.getText().toString()!="")
			  {
		     for(int i=0;i<slist.size();i++){
		    	 WkTExtractask e=(WkTExtractask)slist.get(i);
					dis.setKeId(e.getKeId());
					dis.setKbFlag("1");
					  WkTTasktype types=taskService.getTpyeById(e.getKtaId());
					  if(types.getKtaAudit().toString().trim().equals("0"))
				  	dis.setKbStatus("0");	
					  else  dis.setKbStatus("2");
					dis.setKbDtime(ConvertUtil.convertDateAndTimeString(da.getTime()));
					dis.setKbTitle(kbtitle.getText());
				    dis.setKiId(info.getKiId());   
					if(cuti.isChecked())
					   dis.setKbStrong("1");
					 else dis.setKbStrong("0");
					if(kishow.isChecked())  dis.setKbMail("1");
					else dis.setKbMail("0");
					    newsService.save(dis);
				}
			  }
			  //ɾ����������Ŀ����Ϣ
			  if(share.getText().toString()!="")
			  {
				 List di=newsService.getDistribute(info.getKiId(),info.getKeId());
				 if(di.size()!=0)
					 newsService.delete((WkTDistribute)di.get(0));
			  }
		     try {
		    	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		  	   mlogService.saveMLog(WkTMlog.FUNC_CMS, "������Ϣ��id:"+info.getKiId(), user);
					Messagebox.show("�����ɹ���", "Information", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
				Events.postEvent(Events.ON_CHANGE, this, null);
			this.detach();
					}
	//����
	public void onClick$reset()
	{
		initWindow(task);kititle.setValue("");kititle2.setValue("");cuti.setChecked(false);
		kbtitle.setValue("");kivaliddate.setText("");kikeys.setValue("");share.setValue("");
		kisource.setValue("");kisource1.setCheckmark(false);kimemo.setValue("");modelList.clear();
		pics.getChildren().clear();kicontent.setValue("");kishow.setChecked(false);
	}
	public ListModelList getModelList()
	{
		return modelList;
	}
}
