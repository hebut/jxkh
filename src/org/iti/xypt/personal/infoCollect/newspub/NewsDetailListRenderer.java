package org.iti.xypt.personal.infoCollect.newspub;
/**
 * 初始化信息发布界面列表
 * 2010-3-18
 * @author whm
 */

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
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
import org.zkoss.zul.Tab;

import com.uniwin.framework.entity.WkTUser;




public class NewsDetailListRenderer implements ListitemRenderer {
	WkTChanel chanel;
	WkTDistribute dis;
	WkTInfo info;
	WkTInfocnt infocnt;
	NewsService newsService;
	OriNewsService orinewsService;
	ListModelList orinfoListModel,writerListModel,rebackListModel,auditListModel,readListModel,pubListModel; 
	Listbox orinfoListbox,writerListbox,auditListbox,readListbox,pubListbox,rebackListbox;
	Tab pubTab,auditTab,readTab;
	public NewsDetailListRenderer(OriNewsService orinewsService,NewsService newsService,ListModelList orinfoListModel,ListModelList writerListModel,
			ListModelList rebackListModel ,ListModelList auditListModel,ListModelList readListModel,ListModelList pubListModel,Listbox orinfoListbox,Listbox writerListbox,Listbox auditListbox,Listbox readListbox,Listbox pubListbox,Listbox rebackListbox)
	{
		this.orinewsService=orinewsService;
		this.newsService=newsService;
		this.orinfoListbox=orinfoListbox;
		this.orinfoListModel=orinfoListModel;
		this.writerListModel=writerListModel;
		this.writerListbox=writerListbox;
	    this.auditListbox=auditListbox;
	    this.auditListModel=auditListModel;
	    this.readListModel=readListModel;
	    this.readListbox=readListbox;
	    this.pubListModel=pubListModel;
	    this.pubListbox=pubListbox;
	    this.rebackListModel=rebackListModel;
	    this.rebackListbox=rebackListbox;
	}
	public void render(Listitem item, Object data) throws Exception 
	{
		if(data instanceof WkTOrinfo)
		{
		WkTOrinfo orinfo=(WkTOrinfo)data;
		 item.setValue(orinfo);
		 item.setHeight("30px");
		 Listcell c=new Listcell(""); //用户Listbox的头一列设置为方框选择项，而加的空数据
		 item.appendChild(c);
		 Listcell c0=new Listcell(item.getIndex()+1+"");
		 item.appendChild(c0);
		 Listcell c1=new Listcell();
		 if(orinfo.getKoiTitle().trim().length()>30){
				String str1 = "";                                                      //消息内容长度小于20字符，全部显示，否则截取前20字符显示
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
			 if(dis.getKbStatus().toString().trim().equals("0")||dis.getKbStatus().toString().trim().equals("2")||dis.getKbStatus().toString().trim().equals("3"))
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
		     OriNewsEditWindow w=(OriNewsEditWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/orinewsdetail.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList(d.getKeId());
				}
			});
		}
}
	
	//点击信息标题查看详情事件
	class LointListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			System.out.println("====");
			Listitem c=(Listitem)event.getTarget().getParent();
		     final WkTDistribute d=(WkTDistribute)c.getValue();
		 	Executions.getCurrent().setAttribute("kiTitle", dis);
		     NewsDetailWindow w=(NewsDetailWindow)Executions.createComponents("/admin/content/newspub/newsdetail.zul",null, null);	
		     w.doHighlighted();
		     w.initWindow(d);
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList(d.getKeId());
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
		     NewsUnchangedDetailWindow w=(NewsUnchangedDetailWindow)Executions.createComponents("/admin/content/newspub/unchangednews.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList(d.getKeId());
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
		 reloadList(dis.getKeId());
		}
	}
//重载列表
public void reloadList(Long keid)
{ 
	
	WkTUser user=(WkTUser)Sessions.getCurrent().getAttribute("user");
    List orinfoList=orinewsService.getNewsOfOrinfo(keid);
    List infoList=newsService.getNewsOfChanelZG(keid,user.getKuId());
    List auditlist=newsService.getNewsOfChanelSS(keid,user.getKuId());
    List publist=newsService.getNewsOfChanelFB(keid,user.getKuId());
    List rebacklist=newsService.getNewsOfChanelTH(keid,user.getKuId());
    List readlist=newsService.getNewsOfChanelYY(keid,user.getKuId());
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

}

