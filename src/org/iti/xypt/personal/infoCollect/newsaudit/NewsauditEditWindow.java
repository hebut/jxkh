package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * 信息审核时初始化信息列表
 * 2010-3-21
 * @author whm
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
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


public class NewsauditEditWindow extends Window implements AfterCompose {
	
	private static final long serialVersionUID = 1L;
	WkTExtractask etask;
	WkTUser user;
	//管理日志数据访问接口
	MLogService mlogService;
	NewsService newsService;
	OriNewsService orinewsService;
	Toolbarbutton deletenews;
	Tab auditTab,readTab,pubTab,OrinfoTab,writeTab;
	 String bt,et,k,s,flag;
		Datebox begintime,endtime;
		Textbox keys,source;
		Listitem title,content,auth,memo;
	//信息列表
	Listbox orinfolistbox,auditListbox,readListbox,pubListbox,writeListbox;
	ListModelList orinfoListModel,pubListModel,readListModel,auditListModel,writeListModel;
	//搜索、审核、删除按钮
	Toolbarbutton auditnews;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		Date date = new Date();
		endtime.setValue(date);
		Date date1 = new Date();
		date1.setDate(1);
		begintime.setValue(date1);
		user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		pubTab.addEventListener(Events.ON_SELECT,new  DisDeleteEventLisenter() );
		writeTab.addEventListener(Events.ON_SELECT,new  DisDeleteEventLisenter() );
		auditTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		readTab.addEventListener(Events.ON_SELECT,new  EnaDeleteEventLisenter() );
		OrinfoTab.addEventListener(Events.ON_SELECT,new  DisDeleteEventLisenter() );
		//批量审核信息
		auditnews.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {	
			if(auditTab.isSelected())
			{
				if(auditListbox.getSelectedItem()==null){
					Messagebox.show("请选择您要审核的信息！");
					return;
				}else{	
					if(Messagebox.show("确定要通过审核吗？", "确认", 
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
					{
						
					Set sel=auditListbox.getSelectedItems();
						Iterator it=sel.iterator();			
						while(it.hasNext()){
							Listitem item=(Listitem)it.next();
							WkTDistribute dis=(WkTDistribute)item.getValue();	
								dis.setKbStatus("0");
								newsService.update(dis);
								 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
								mlogService.saveMLog(WkTMlog.FUNC_CMS, "审核通过信息，id:"+dis.getKiId(), user);
							}			
					}
				}
			}
					else if(readTab.isSelected())
					{
						if(readListbox.getSelectedItem()==null){
							Messagebox.show("请选择您要审核的信息！");
							return;
						}else{	
							if(Messagebox.show("确定要通过审核吗？", "确认", 
									Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
							{
								
							Set sel=readListbox.getSelectedItems();
								Iterator it=sel.iterator();			
								while(it.hasNext()){
									Listitem item=(Listitem)it.next();
									WkTDistribute	 dis=(WkTDistribute)item.getValue();
										dis.setKbStatus("0");
										newsService.update(dis);
										 WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
										mlogService.saveMLog(WkTMlog.FUNC_CMS, "审核通过信息，id:"+dis.getKiId(), user);
									}			
					}
						}
					}
					Messagebox.show("完成审核！");
					reloadList();		
			}
		});
		//批量删除信息
		deletenews.addEventListener(Events.ON_CLICK, new EventListener(){
			public void onEvent(Event event) throws Exception {	
				if(auditTab.isSelected())
				{
				deleteinfo(auditListbox,auditListModel);	
				}
		else if(readTab.isSelected())
		{
					deleteinfo(readListbox,readListModel);	
		}
		else if(pubTab.isSelected())
		{
			deleteinfo(pubListbox,pubListModel);	
		}
		else if(writeTab.isSelected())
		{
			deleteinfo(writeListbox,writeListModel);	
		}
	}
		});
	}
	//设置删除按钮的响应状态
    class DisDeleteEventLisenter implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			 auditnews.setDisabled(true);
		}		
	}
    class EnaDeleteEventLisenter  implements EventListener{
		public void onEvent(Event arg0) throws Exception {
			 auditnews.setDisabled(false);
		}		
	}
	public void initWindow(WkTExtractask etask) {
		this.etask = etask;
		this.setTitle(etask.getKeName());
		reloadList();
	}
	//删除信息
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
                       WkTDistribute dis=(WkTDistribute)item.getValue();	
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
						    	for(int j=0;j<d.size();j++)
						    	newsService.delete((WkTDistribute)d.get(j));
						    	 List ic=newsService.getInfocnt(((WkTInfo)delinfoList.get(i)).getKiId());
						    	 for(int m=0;m<ic.size();m++)
						    	 {
						    		 newsService.delete((WkTInfocnt)ic.get(m));
						    	 }
						    		mlogService.saveMLog(WkTMlog.FUNC_CMS, "删除信息，id:"+((WkTInfo)delinfoList.get(i)).getKiId(), user);
						    	 newsService.delete((WkTInfo)delinfoList.get(i));
						    	
				    	 }
				     }
				     listModel.removeAll(deldisList);
				Messagebox.show("删除成功");
				reloadList();
			}
		}
	}
	//搜索信息
  	public void onClick$searchnews()
  	{
  		 bt = ConvertUtil.convertDateAndTimeString(begintime.getValue());
  		 et = ConvertUtil.convertDateAndTimeString(endtime.getValue());
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
  		
  		 if(readTab.isSelected())
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
  		else if(pubTab.isSelected())
  		{
  			Long  status=0L;
  			List slist=newsService.NewsSearch(etask.getKeId(),status,bt,et,flag,k,s);
  			searchinfo(slist,pubListbox,pubListModel);	
  		}
  		else 
  	  		{
  			List slist=newsService.OriNewsSearch(etask.getKeId(),bt,et,flag,k,s);
  			searchinfo(slist,orinfolistbox,orinfoListModel);	
  	  		}
  	}
	//重载搜索到的信息列表
	public void searchinfo(List slist,Listbox infolistbox,ListModelList infolistmodel)
	{
		infolistmodel.clear();
		infolistmodel.addAll(slist);
		infolistbox.setModel(infolistmodel);	
	}
	//列表重载
	public void reloadList()
	{
		 List orinfoList=orinewsService.getNewsOfOrinfo(etask.getKeId());
		 List auditList=newsService.getNewsOfChanelSS(etask.getKeId());
		 List readList=newsService.getNewsOfChanelYY(etask.getKeId());
		 List pubList=newsService.getNewsOfChanelFB(etask.getKeId());
		 List writeList=newsService.getNewsOfChanelZG(etask.getKeId(), user.getKuId());
			orinfoListModel=new ListModelList();
			orinfoListModel.addAll(orinfoList);
			pubListModel=new ListModelList();
			pubListModel.addAll(pubList);
			readListModel=new ListModelList();
			readListModel.addAll(readList);
			auditListModel=new ListModelList();
			auditListModel.addAll(auditList);
			writeListModel=new ListModelList();
			writeListModel.addAll(writeList);
			orinfolistbox.setModel(orinfoListModel);
			orinfolistbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			auditListbox.setModel(auditListModel);
			auditListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			readListbox.setModel(readListModel);
			readListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			pubListbox.setModel(pubListModel);
			pubListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			writeListbox.setModel(writeListModel);
			writeListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
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
	public void onClick$show()
	{
		ShowFristWindow w=(ShowFristWindow) Executions.createComponents("/admin/personal/infoExtract/newsaudit/showfirst.zul", null,null);
						w.doHighlighted();
						w.initWindow();
	}
	
}
