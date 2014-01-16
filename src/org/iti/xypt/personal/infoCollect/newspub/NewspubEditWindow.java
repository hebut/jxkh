package org.iti.xypt.personal.infoCollect.newspub;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTFile;
import org.iti.xypt.personal.infoCollect.entity.WkTFileId;
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
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;



public class NewspubEditWindow extends Window implements AfterCompose {
	
	private static final long serialVersionUID = -2451318625439388515L;
	//信息数据访问接口
	NewsService newsService;
	//管理日志数据访问接口
	MLogService mlogService;
	TaskService taskService;
	OriNewsService orinewsService;
	WkTExtractask etask;
	WkTInfocnt infocnt;
	WkTUser user;
	Textbox searchtext;
	WkTInfo info,selected;
	WkTDistribute dis;
	Map params;	
	 String bt,et,k,s,flag;
	Datebox begintime,endtime;
	Textbox keys,source;
	Listitem title,content,auth,memo;
	//界面上的各种组件
	Tab writerTab,readTab,auditTab,rebackTab,pubTab,orinfoTab; 
	//信息列表
	Listbox orinfoListbox,writerListbox,auditListbox,readListbox,pubListbox,rebackListbox;
	ListModelList orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel;
	List  slist=new ArrayList();
	//搜索、添加、删除按钮
	Toolbarbutton addnews,deletenews;
	Button searchnews,auditbutton;
	public void afterCompose() {
		params=this.getAttributes();
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		Date date = new Date();
		endtime.setValue(date);
		Date date1 = new Date();
		date1.setDate(1);
		begintime.setValue(date1);
	      //增加新信息
		addnews.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {
				NewsNewWindow w=(NewsNewWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/new.zul", null, null);
				w.initWindow(etask);
				w.doHighlighted();
				w.addEventListener(Events.ON_CHANGE, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						reloadList();
						
					}
				});
			}
		});
		orinfoTab.addEventListener(Events.ON_SELECT,new  DisDeleteEventLisenter() );
		pubTab.addEventListener(Events.ON_SELECT,new  DisDeleteEventLisenter() );
		auditTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		rebackTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		readTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		writerTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		
		orinfoTab.addEventListener(Events.ON_SELECT,new  EnaAuditEventLisenter() );
		pubTab.addEventListener(Events.ON_SELECT,new  DisAuditEventLisenter() );
		auditTab.addEventListener(Events.ON_SELECT,new  DisAuditEventLisenter() );
		rebackTab.addEventListener(Events.ON_SELECT,new  DisAuditEventLisenter() );
		readTab.addEventListener(Events.ON_SELECT,new  DisAuditEventLisenter() );
		writerTab.addEventListener(Events.ON_SELECT,new  EnaAuditEventLisenter() );
//		//判断不同信息状态中删除功能的作用
		deletenews.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {	
				if(writerTab.isSelected())
				{
				deleteinfo(writerListbox,writerListModel);	
				}
	   	else if(auditTab.isSelected())
		{
					deleteinfo(auditListbox,auditListModel);	
		}
		else if(readTab.isSelected())
		{
					deleteinfo(readListbox,readListModel);	
		}
		else if(rebackTab.isSelected())
		{
					deleteinfo(rebackListbox,rebackListModel);	
		}
	}
		});	
		
	}
	// 批量删除信息	 
	public void deleteinfo(Listbox listbox,ListModelList listModel) throws InterruptedException
	{
		if(listbox.getSelectedItem()==null)
		{
			Messagebox.show("请选择您要删除的信息！");
			return;
		}
		else{	
			if(Messagebox.show("确定要删除吗？", "确认", 
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
			{
			Set sel=listbox.getSelectedItems();
				Iterator it=sel.iterator();
				List delinfoList=new ArrayList();
				List deldisList=new ArrayList();
				List delinfocntList=new ArrayList();
				     while(it.hasNext()){
				    	 Listitem item=(Listitem)it.next();
						 dis=(WkTDistribute)item.getValue();	
						 delinfoList.add(newsService.getWkTInfo(dis.getKiId()));
						 deldisList.add(dis);
						 delinfocntList.add(newsService.getInfocnt(dis.getKiId()));
				     }
				     for(int i=0;i<deldisList.size();i++)
				     {
				    	 if((((WkTDistribute)deldisList.get(i)).getKbFlag()).trim().equals("1"))
					     {
				    		 newsService.delete((WkTDistribute)deldisList.get(i));
					     }
				    	 else 
				    	 {   
				    		 List d=newsService.getDistributeList(((WkTDistribute)deldisList.get(i)).getKiId());
				    		 List f=newsService.getFile(((WkTDistribute)deldisList.get(i)).getKiId());
				    		 if(f.size()!=0)
				    		 {
				    		 for(int n=0;n<f.size();n++)
				    			 newsService.delete((WkTFile)f.get(n));
				    		 }
						    	for(int j=0;j<d.size();j++)
						    	newsService.delete((WkTDistribute)d.get(j));
						    	 newsService.delete(delinfoList.get(i));
						    	 List ic=newsService.getInfocnt(((WkTInfo)delinfoList.get(i)).getKiId());
						    	 for(int j=0;j<ic.size();j++)
						    		 newsService.delete(ic.get(j));
						    	 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
						    	 mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+((WkTInfo)delinfoList.get(i)).getKiId(), user);
				    	 }
				     }
				     listModel.removeAll(deldisList);
				Messagebox.show("删除成功");
				reloadList();
			}
		}
	}
	public void initWindow( WkTExtractask etask)
	{
		this.etask = etask;
		this.setTitle(etask.getKeName());
		reloadList();
	}
	//设置pub.zul界面删除按钮的响应状态
    class DisDeleteEventLisenter implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			 deletenews.setDisabled(true);
		}		
	}
    class EnaDeleteEventLisenter  implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			 deletenews.setDisabled(false);
		}		
	}	
  //设置pub.zul界面送审按钮的响应状态
    class DisAuditEventLisenter implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			 auditbutton.setDisabled(true);
		}		
	}
    class EnaAuditEventLisenter  implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			auditbutton.setDisabled(false);
		}		
	}	
	
  //对撰稿中信息的批量送审功能
  	public void onClick$auditbutton() throws InterruptedException
  	{
  		if(writerTab.isSelected())
  		{
  		if(writerListbox.getSelectedItem()==null)
  		{
  			Messagebox.show("请选择您要送审的信息！");
  			return;
  		}
  		else
  		{	
  			if(Messagebox.show("确定要送审吗？", "确认", 
  					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
  			{  
  				Set sel=writerListbox.getSelectedItems();
  				 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
  				Iterator it=sel.iterator();			
  				while(it.hasNext()){
  						 Listitem item=(Listitem)it.next();
  						 dis=(WkTDistribute)item.getValue();	
  					  List  d=newsService.getDistributeList(dis.getKiId());
  					  for(int i=0;i<d.size();i++)
  					  {
  						  WkTDistribute disi=(WkTDistribute)d.get(i);
  						WkTExtractask ett=(WkTExtractask)taskService.getTaskBykeId(disi.getKeId()).get(0);
  						 WkTTasktype tas=taskService.getTpyeById(ett.getKtaId());
  						String kfid=tas.getKtaAudit().toString();
  						if(kfid.equals("0"))
  						{
  							dis=(WkTDistribute)d.get(i);
  							dis.setKbStatus("0");
  					     	newsService.update(dis);
  					     mlogService.saveMLog(WkTMlog.FUNC_CMS, "发布信息，id:"+dis.getKiId(), user);
  						}
  						else if(kfid.equals("1")) 
  						{
  							dis=(WkTDistribute)d.get(i);
  							dis.setKbStatus("2");
  					     	newsService.update(dis);
  					     	mlogService.saveMLog(WkTMlog.FUNC_CMS, "送审信息，id:"+dis.getKiId(), user);
  						}
  					  }		
  					  if(dis.getKbFlag().toString().trim().equals("0"))
  					  {
  						  List slist=newsService.getDistributeShare(dis.getKiId());
  						  if(slist.size()!=0)
  						  {
  							  for(int i=0;i<slist.size();i++)
  							  {
  							  WkTDistribute di=(WkTDistribute) slist.get(i);
  							WkTExtractask ett=(WkTExtractask)taskService.getTaskBykeId(di.getKeId()).get(0);
  	  						 WkTTasktype tas=taskService.getTpyeById(ett.getKtaId());
  	  						 if(tas.getKtaAudit().toString().trim().equals("0"))
  	  						 {
  	  							 di.setKbStatus("0");
  	  							 newsService.update(di);
  	  						 }
  	  						 else
  	  						 {
  	  						 di.setKbStatus("2");
	  							 newsService.update(di);
  	  						 }
  							  }
  						  }
  					  }
  					 }
  				Messagebox.show("操作成功！");
  				 reloadList();
  			}
  			else {return;}
  		}
  		}
  		else if(orinfoTab.isSelected())
  		{

  	  		if(orinfoListbox.getSelectedItem()==null)
  	  		{
  	  			Messagebox.show("请选择您要送审的信息！");
  	  			return;
  	  		}
  	  		else
  	  		{	
  	  			if(Messagebox.show("确定要送审吗？", "确认", 
  	  					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
  	  			{  
  	  				Set sel=orinfoListbox.getSelectedItems();
  	  				 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
  	  				Iterator it=sel.iterator();			
  	  				while(it.hasNext()){
  	  						 Listitem item=(Listitem)it.next();
  	  						WkTOrinfo oinfo=(WkTOrinfo)item.getValue();	
  	  					    WkTInfo info=new WkTInfo();
  	  					    info.setKeId(oinfo.getKeId());
  	  			            info.setKiAddress(oinfo.getKoiAddress());
  	  			            info.setKiAuthname(oinfo.getKoiAuthname());
  	  			            info.setKiCtime(oinfo.getKoiCtime());
  	  			            info.setKiHits(Integer.parseInt("0"));
  	  			            info.setKiImage(oinfo.getKoiImage());
  	  			            info.setKiKeys(oinfo.getKoiKeys());
  	  			            info.setKiMemo(oinfo.getKoiMemo());
  	  			            info.setKiOrdno(Integer.parseInt("10"));
  	  			            info.setKiPtime(oinfo.getKoiPtime());
  	  			            info.setKiRegion("");
  	  			            info.setKiShow("1");
  	  			            info.setKiSource(oinfo.getKoiSource());
  	  			            info.setKiTitle(oinfo.getKoiTitle());
  	  			            info.setKiTitle2(oinfo.getKoiTitle2());
  	  			            info.setKiTop("0");
  	  			            info.setKiType("1");
  	  			            info.setKiUrl("");
  	  			            info.setKiValiddate("1900-1-1");
  	  			            info.setKuId(user.getKuId());
  	  			            info.setKuName(user.getKuName());
  	  			            newsService.save(info);
  	  			            WkTDistribute dis=new WkTDistribute();
  	  			            dis.setKiId(info.getKiId());
  	  			            dis.setKeId(info.getKeId());
  	  			            dis.setKbFlag("0");
  	  			            dis.setKbTitle(info.getKiTitle());
  	  			            Date d=new Date();
  	                        dis.setKbDtime(ConvertUtil.convertDateAndTimeString(d.getTime()));
  	  			            dis.setKbRight("0");
  	  			            dis.setKbMail("0");
  	  			            dis.setKbStrong("0");
  	  			            dis.setKbEm("0");
  	  			      	   WkTExtractask ett=(WkTExtractask)taskService.getTaskBykeId(dis.getKeId()).get(0);
  							 WkTTasktype tas=taskService.getTpyeById(ett.getKtaId());
  							String kfid=tas.getKtaAudit().toString();
  							if(kfid.equals("0"))
  							{
  								dis.setKbStatus("0");
  								mlogService.saveMLog(WkTMlog.FUNC_CMS, "发布信息，id:"+dis.getKiId(), user);
  							}
  							else if(kfid.equals("1"))
  							{
  								dis.setKbStatus("2");
  								mlogService.saveMLog(WkTMlog.FUNC_CMS, "送审信息，id:"+dis.getKiId(), user);
  							}
  	  			            newsService.save(dis);
  	  			            if(orinewsService.getOriInfocnt(oinfo.getKoiId()).size()!=0)
  	  			            {
  	  			            	List cntlist=orinewsService.getOriInfocnt(oinfo.getKoiId());
  	  			            for(int i=0;i<cntlist.size();i++)
  	  			            { 
  	  			            	WkTOrinfocnt orinfocnt=(WkTOrinfocnt) cntlist.get(i);
  	  			            	 WkTInfocnt infocnt=new WkTInfocnt();
  	  						     WkTInfocntId infocntid=new WkTInfocntId(info.getKiId(),orinfocnt.getId().getKoiSubid());
  	  						     infocnt.setId(infocntid);
  	  						   infocnt.setKiContent(orinfocnt.getKoiContent()); 
  	  						   newsService.save(infocnt);
  	  						   newsService.delete(orinfocnt);
  	  			            }
  	  			            }
  	  			            if(orinewsService.getOrifile(oinfo.getKoiId()).size()!=0)
  	  			            {
  	  			            	List flist=orinewsService.getOrifile(oinfo.getKoiId());
  	  			            	WkTOrifile ofile=new WkTOrifile();
  	  			            	WkTFile file=new WkTFile();
  	  			            	 WkTFileId fileid=new WkTFileId(info.getKiId(),ofile.getId().getKofName(),ofile.getId().getKofShowname(),ofile.getId().getKuId(),"1","0");
  	  					        file.setId(fileid);
  	  					        newsService.save(file);
  	  					        newsService.delete(ofile);
  	  			            }
  	  			            	newsService.delete(oinfo);
  	  					 }
  	  				Messagebox.show("操作成功！");
  	  				 reloadList();
  	  			}
  	  			else {return;}
  	  		}
  		}
  	}	
  	//搜索信息
  	public void onClick$searchnews() throws InterruptedException
  	{
  		 bt = ConvertUtil.convertDateAndTimeString(begintime.getValue());
  		 et = ConvertUtil.convertDateAndTimeString(endtime.getValue());
  		 if(bt.compareTo(et)>0)
  		 {
  			 Messagebox.show("开始时间不能大于截止时间");
  			 return;
  		 }
  		if(!keys.getValue().equals(""))  
  		{
  			k=keys.getValue();
  			if(content.isSelected()) flag="1";
  			else flag="2";
  		}
  		else k="";
  		if(!source.getValue().equals(""))
  		{s=source.getValue().trim();}
  		else s="";
  		if(writerTab.isSelected())
  		{
  			Long  status=1L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,writerListbox,writerListModel);	
  		}
  		else if(readTab.isSelected())
  		{
  			Long  status=3L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,readListbox,readListModel);	
  		}
  		else if(auditTab.isSelected())
  		{
  			Long  status=2L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,auditListbox,auditListModel);	
  		}
  		else if(rebackTab.isSelected())
  		{
  			Long  status=4L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,rebackListbox,rebackListModel);	
  		}
  		else if(pubTab.isSelected())
  		{
  			Long  status=0L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,pubListbox,pubListModel);	
  		}
  		else 
  	  		{
  			List slist=newsService.OriNewsSearch(etask.getKeId(),bt,et,flag,k,s);
  			searchinfo(slist,orinfoListbox,orinfoListModel);	
  	  		}
  	}
	//重载搜索到的信息列表
	public void searchinfo(List slist,Listbox infolistbox,ListModelList infolistmodel)
	{
		infolistmodel.clear();
		infolistmodel.addAll(slist);
		infolistbox.setModel(infolistmodel);	
	}
//加载信息列表
	public void reloadList()
	{ 	
		WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
	    List orinfoList=orinewsService.getNewsOfOrinfo(etask.getKeId());
	    List infoList=newsService.getNewsOfChanelZG(etask.getKeId(),user.getKuId());
	    List auditlist=newsService.getNewsOfChanelSS(etask.getKeId(),user.getKuId());
	    List publist=newsService.getNewsOfChanelFB(etask.getKeId(),user.getKuId());
	    List rebacklist=newsService.getNewsOfChanelTH(etask.getKeId(),user.getKuId());
	    List readlist=newsService.getNewsOfChanelYY(etask.getKeId(),user.getKuId());
		orinfoListModel=new ListModelList();
		orinfoListModel.addAll(orinfoList);
		writerListModel=new ListModelList();
		writerListModel.addAll(infoList);
		auditListModel=new ListModelList();
		auditListModel.addAll(auditlist);
		readListModel=new ListModelList();
		readListModel.addAll(readlist);
		pubListModel=new ListModelList();
		pubListModel.addAll(publist);
		rebackListModel=new ListModelList();
		rebackListModel.addAll(rebacklist);
		writerListbox.setModel(writerListModel);
		writerListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
		orinfoListbox.setModel(orinfoListModel);
		orinfoListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
		auditListbox.setModel(auditListModel);
		auditListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
		readListbox.setModel(readListModel);
		readListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
		pubListbox.setModel(pubListModel);
		pubListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
		rebackListbox.setModel(rebackListModel);
		rebackListbox.setItemRenderer(new NewsDetailListRenderer(orinewsService,newsService,orinfoListModel,writerListModel,auditListModel,readListModel,pubListModel,rebackListModel,
				orinfoListbox,writerListbox, auditListbox,readListbox,pubListbox,rebackListbox));
	}	
	//重置搜索条件
	public void onClick$resetnews()
	{
		Date date = new Date();
		endtime.setValue(date);
		Date date1 = new Date();
		date1.setDate(1);
		begintime.setValue(date1);
		keys.setValue("");
		source.setValue("");
	}
}
	

