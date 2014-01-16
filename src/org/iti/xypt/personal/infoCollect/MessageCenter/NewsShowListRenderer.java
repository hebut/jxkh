package org.iti.xypt.personal.infoCollect.MessageCenter;


import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfocnt;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;




public class NewsShowListRenderer implements ListitemRenderer {
	WkTDistribute dis;
	WkTInfo info;
	WkTInfocnt infocnt;
	NewsService newsService;
	ListModelList infoListModel; 
	Listbox list;
	public NewsShowListRenderer(NewsService newsService,ListModelList infoListModel ,Listbox list)
	{
		this.newsService=newsService;
	    this.infoListModel=infoListModel;
	    this.list=list;
	}
	public void render(Listitem item, Object data) throws Exception 
	{
		WkTDistribute dis=(WkTDistribute) data;
		WkTInfo info=newsService.getWkTInfo(dis.getKiId());
		 item.setValue(info);
		 item.setHeight("25px");
		 Listcell c0=new Listcell(item.getIndex()+1+"");
		 item.appendChild(c0);
		 Listcell c1=new Listcell();
		 if(info.getKiTitle().trim().length()>35){
				String str1 = "";                                                      //消息内容长度小于20字符，全部显示，否则截取前20字符显示
				str1=info.getKiTitle().trim().substring(0,35);
					c1=new Listcell(str1+"......");
			}else{
				 c1=new Listcell(info.getKiTitle());					
			}
		 c1.setTooltiptext("点击查看信息详情");
		  c1.addEventListener(Events.ON_CLICK, new PnnerListener());
		 item.appendChild(c1);
		 Listcell c2=new Listcell(info.getKuName());
		 item.appendChild(c2);
		 Listcell c3=new Listcell(dis.getKbDtime());
		 item.appendChild(c3);
	}
	
	
	//点击信息标题查看详情事件
	class PnnerListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
		     final WkTOrinfo i=(WkTOrinfo)c.getValue();
		 	Executions.getCurrent().setAttribute("kiTitle", i);
		     NewsShowDetailWindow w=(NewsShowDetailWindow)Executions.createComponents("/admin/personal/newsaudit/showdetail.zul",null, null);	
		     w.doHighlighted();
		     w.initWindow(i);
		}
}
}

