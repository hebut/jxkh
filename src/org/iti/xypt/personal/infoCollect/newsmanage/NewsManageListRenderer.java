package org.iti.xypt.personal.infoCollect.newsmanage;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tab;

import com.uniwin.framework.entity.WkTUser;



public class NewsManageListRenderer implements ListitemRenderer {
	
	
	
	OriNewsService orinewsService;
	WkTExtractask etask;
	WkTUser user;
	Listbox infomanagelist;
	ListModelList infomanageListModel;
	public NewsManageListRenderer(OriNewsService orinewsService,ListModelList infomanageListModel,
			Listbox infomanagelist)
	{
		this.orinewsService=orinewsService;
		this.infomanageListModel=infomanageListModel;
		this.infomanagelist=infomanagelist;
	}
	public void render(Listitem item, Object data) throws Exception 
	{
		WkTOrinfo u=(WkTOrinfo)data;	
		 item.setValue(u);
		 item.setHeight("30px");
		 Listcell c=new Listcell(""); //用户Listbox的头一列设置为方框选择项，而加的空数据
		 item.appendChild(c);
		 Listcell c0=new Listcell(item.getIndex()+1+"");
		 item.appendChild(c0);
		 if(u.getKoiStatus().toString().trim().equals("0"))
		 {
			 Listcell c1=new Listcell();
			 c1.setImage("/images/ims.news.gif");		
		     item.appendChild(c1);
		 }
		 else
		 {
			 Listcell c1=new Listcell();
			 c1.setImage("/images/ims.readed.gif");
		     item.appendChild(c1);
		 }
		     Listcell c2=new Listcell();
			 if(u.getKoiTitle().length()>30){
					String str1 = "";                                                      //消息内容长度小于20字符，全部显示，否则截取前20字符显示
					str1=u.getKoiTitle().substring(0,30);
						c2=new Listcell(str1+"......");
				}else{
					 c2=new Listcell(u.getKoiTitle());					
				}
			 c2.setTooltiptext("点击查看信息详情");
		 item.appendChild(c2);
		 Listcell c3=new Listcell(u.getKoiPtime());
		 item.appendChild(c3);
		 Listcell c4=new Listcell(u.getKoiSource());
		 item.appendChild(c4);	
		 Listcell c5=new Listcell(u.getKoiCtime());
		 item.appendChild(c5);	
		 c2.addEventListener(Events.ON_CLICK, new InnerListener());
	}
	//点击信息标题查看详情事件
	class InnerListener implements EventListener
	{
		
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
			final WkTOrinfo d=(WkTOrinfo)c.getValue();
			d.setKoiStatus(Long.parseLong("1"));
			orinewsService.update(d);
			reloadList(d.getKeId());
			NewsConEditWindow w=(NewsConEditWindow)Executions.createComponents("/admin/personal/infoExtract/newsmanage/newscon.zul",null, null);	
			w.initWindow(d);
			w.doHighlighted();
		    w.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					reloadList(d.getKeId());
				}
			});
		}
		}
	//加载数据
	public void reloadList(Long keid)
	{ 
		List auditcommentsList=orinewsService.getNewsOfOrinfo(keid);
		infomanageListModel=new ListModelList();
		infomanageListModel.addAll(auditcommentsList);
		infomanagelist.setModel(infomanageListModel);
		infomanagelist.setItemRenderer(new NewsManageListRenderer(orinewsService,infomanageListModel,infomanagelist));
	}	
}