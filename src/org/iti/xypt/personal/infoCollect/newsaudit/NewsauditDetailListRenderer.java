package org.iti.xypt.personal.infoCollect.newsaudit;
/**
 * 初始化信息审核界面列表
 * @author whm
 */
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;



public class NewsauditDetailListRenderer implements ListitemRenderer {

	OriNewsService orinewsService;
	NewsService newsService;
	Listbox orinfolistbox,auditListbox,readListbox,pubListbox,writeListbox;
	ListModelList orinfoListModel,pubListModel,readListModel,auditListModel,writeListModel;
	WkTExtractask etask;
	NewsauditEditWindow auditWin;
	public NewsauditDetailListRenderer(OriNewsService orinewsService,NewsService newsService,
			ListModelList orinfoListModel,ListModelList writeListModel,ListModelList auditListModel,ListModelList readListModel,ListModelList pubListModel,
			Listbox auditListbox,Listbox readListbox,Listbox writeListbox,Listbox pubListbox,Listbox orinfolistbox)
	{
		this.orinewsService=orinewsService;
		this.newsService=newsService;
		this.orinfolistbox=orinfolistbox;
		this.orinfoListModel=orinfoListModel;
		this.writeListbox=writeListbox;
		this.writeListModel=writeListModel;
		this.auditListbox=auditListbox;
		this.auditListModel=auditListModel;
		this.readListbox=readListbox;
		this.readListModel=readListModel;
		this.pubListbox=pubListbox;
		this.pubListModel=pubListModel;
		
	}
	
	public void render(Listitem item, Object data) throws Exception {
		if(data instanceof WkTOrinfo)
		{
			
		WkTOrinfo orinfo=(WkTOrinfo)data;
		if(getTask()==null){
			  this.etask=orinewsService.getTask(orinfo.getKeId());
			}
		 item.setValue(orinfo);
		 item.setHeight("30px");
		 Listcell c=new Listcell(""); //用户Listbox的头一列设置为方框选择项，而加的空数据
		 item.appendChild(c);
		 Listcell c0=new Listcell(item.getIndex()+1+"");
		 item.appendChild(c0);
		 Listcell c1=new Listcell();
		 if(orinfo.getKoiTitle().trim().length()>30){
				String str1 = "";                                                      
				str1=orinfo.getKoiTitle().trim().substring(0,30);
					c1=new Listcell(str1+"......");
			}else{
				 c1=new Listcell(orinfo.getKoiTitle());					
			}
		 c1.setTooltiptext("点击查看信息详情");
		 item.appendChild(c1);
		  c1.addEventListener(Events.ON_CLICK, new PnnerListener());
		 Listcell c2=new Listcell(orinfo.getKoiPtime());
		 item.appendChild(c2);
		 Listcell c3=new Listcell(orinfo.getKoiSource());
		 item.appendChild(c3);
		 Listcell c4=new Listcell(orinfo.getKoiCtime());
		 item.appendChild(c4);
		
		}
		else if(data instanceof WkTDistribute)
		{
			WkTDistribute dis=(WkTDistribute)data;
			WkTInfo info=newsService.getWkTInfo(dis.getKiId());
			if(getTask()==null){
				  this.etask=orinewsService.getTask(dis.getKeId());
				}
			 item.setValue(dis);
			 item.setHeight("30px");
			 Listcell c=new Listcell(""); //用户Listbox的头一列设置为方框选择项，而加的空数据
			 item.appendChild(c);
			 Listcell c0=new Listcell(item.getIndex()+1+"");
			 item.appendChild(c0);
			 Listcell c1=new Listcell();
			 if(info.getKiTitle().trim().length()>30){
					String str1 = "";                                                     
					str1=info.getKiTitle().trim().substring(0,30);
						c1=new Listcell(str1+"......");
				}else{
					 c1=new Listcell(info.getKiTitle());					
				}
			 c1.setTooltiptext("点击查看信息详情");
			 item.appendChild(c1);
			 if(dis.getKbStatus().toString().trim().equals("0"))
			 {
			  c1.addEventListener(Events.ON_CLICK, new LointListener1());
			 }
			 else
			 {
				 c1.addEventListener(Events.ON_CLICK, new LointListener());
			 }
			 Listcell c2=new Listcell(dis.getKbDtime());
			 item.appendChild(c2);
			 Listcell c3=new Listcell(info.getKiSource());
			 item.appendChild(c3);
			 Listcell c4=new Listcell(info.getKuName());
			 item.appendChild(c4);
			 Listcell c5=new Listcell(info.getKiHits().toString());
			 item.appendChild(c5);
			 if(info.getKiTop().trim().equals("0"))
			 {
			 Listcell c6=new Listcell("置顶");
			 c6.setTooltiptext("点击设置！");
			 item.appendChild(c6);
			 c6.addEventListener(Events.ON_CLICK, new PointListener());
			 }
			 else if(info.getKiTop().trim().equals("1"))
			 {
				 Listcell c6=new Listcell("取消置顶");
				 c6.setTooltiptext("点击设置！");
				 item.appendChild(c6);
				 c6.addEventListener(Events.ON_CLICK, new PointListener());
			 }
	    }

	}
	//点击信息标题查看详情事件
	class PnnerListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
		     final WkTOrinfo d=(WkTOrinfo)c.getValue();
		     OriauditNewsEditWindow w=(OriauditNewsEditWindow)Executions.createComponents("/admin/personal/infoExtract/newsaudit/orinewsdetail.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList();
				}
			});
		}
}
	//点击信息标题查看详情事件
	class LointListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
		    final WkTDistribute d=(WkTDistribute)c.getValue();
		     NewsauditDetailWindow w=(NewsauditDetailWindow)Executions.createComponents("/admin/personal/infoExtract/newsaudit/shenhenews.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList();
				}
			});
		}
}
	
	//点击信息标题查看详情事件
	class LointListener1 implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
		      final WkTDistribute d=(WkTDistribute)c.getValue();
		     NewsUnchangeauditDetailWindow w=(NewsUnchangeauditDetailWindow)Executions.createComponents("/admin/personal/infoExtract/newsaudit/unchangeauditnews.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList();
				}
			});
		}
}
	class PointListener implements EventListener
	{  
		public void onEvent(Event event) throws Exception 
		{
			
		 Listitem c=( Listitem )event.getTarget().getParent();
		 WkTDistribute dis=(WkTDistribute)c.getValue();
		 WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 if(info.getKiTop().trim().equals("0"))
		 {
			info.setKiTop("1");
			 newsService.update(info);
		 }
		 else if(info.getKiTop().trim().equals("1"))
		 {
			 info.setKiTop("0");
			 newsService.update(info);
		 }
		reloadList();
		}
	}
	public WkTExtractask getTask() {
		return etask;
	}

	//重载列表
	public void reloadList()
	
	{	WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
		 List orinfoList=orinewsService.getNewsOfOrinfo(etask.getKeId());
		 List auditList=newsService.getNewsOfChanelSS(etask.getKeId());
		 List readList=newsService.getNewsOfChanelYY(etask.getKeId());
		 List pubList=newsService.getNewsOfChanelFB(etask.getKeId());
		 List writeList=newsService.getNewsOfChanelZG(etask.getKeId(), user.getKuId());
		 orinfoListModel.clear();
			orinfoListModel=new ListModelList();
			orinfoListModel.addAll(orinfoList);
			writeListModel.clear();
			auditListModel.clear();
			readListModel.clear();
			pubListModel.clear();
			writeListModel=new ListModelList();
			writeListModel.addAll(writeList);
			auditListModel=new ListModelList();
			auditListModel.addAll(auditList);
			readListModel=new ListModelList();
			readListModel.addAll(readList);
			pubListModel=new ListModelList();
			pubListModel.addAll(pubList);
			orinfolistbox.setModel(orinfoListModel);
			orinfolistbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			writeListbox.setModel(writeListModel);
			writeListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			auditListbox.setModel(auditListModel);
			auditListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			readListbox.setModel(readListModel);
			readListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));
			pubListbox.setModel(pubListModel);
			pubListbox.setItemRenderer(new NewsauditDetailListRenderer(orinewsService,newsService,orinfoListModel,writeListModel,auditListModel,readListModel,pubListModel,auditListbox,readListbox,writeListbox,pubListbox,orinfolistbox));		
	}
}


