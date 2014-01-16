package org.iti.xypt.personal.infoCollect.newspub;
/**
 * 选择引用信息界面
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;



public class InfoSelectWindow extends Window implements AfterCompose {

	Listbox detaillistbox;
	ListModelList dmodellist;
	NewsService newsService;
	List list;
	ArrayList ilist= new ArrayList();
	Window infosel;
	WkTDistribute dis;
	Toolbarbutton choose;
	public void afterCompose() 
	{
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	//点击确定按钮
public void onClick$choose()
{
	Set sel=detaillistbox.getSelectedItems();
	Iterator it=sel.iterator();
	  while(it.hasNext()){
	    	 Listitem item=(Listitem)it.next();
			 dis=(WkTDistribute)item.getValue();	
			 WkTInfo infor=newsService.getWkTInfo(dis.getKiId());
		     ilist.add(0,infor);
	  }
	  Events.postEvent(Events.ON_CHANGE,this,null);
	  infosel.detach();
}
//加载数据
	public void initWindow(WkTChanel chanel) {
		dmodellist=new ListModelList();
		list=newsService.getInfoByCid(chanel.getKcId());
		dmodellist.addAll(list);
		detaillistbox.setModel(dmodellist);
		detaillistbox.setItemRenderer(new detailItemRender(dmodellist));
		
	}
	//绑定数据
	class detailItemRender implements ListitemRenderer{
		
		ListModelList dmodellist;

		public detailItemRender(ListModelList dmodellist) {
			this.dmodellist=dmodellist;
		}

		public void render(Listitem item, Object data) throws Exception {
		 WkTDistribute dis=(WkTDistribute)data;
		 WkTInfo infor=newsService.getWkTInfo(dis.getKiId());
		  item.setValue(dis);
		  item.setHeight("25px");
		  Listcell c=new Listcell(""); //用户Listbox的头一列设置为方框选择项，而加的空数据
		  Listcell c0=new Listcell(item.getIndex()+1+"");
		  Listcell c1=new Listcell(infor.getKiTitle());
		  Listcell c2=new Listcell(infor.getKuName());
		  Listcell c3=new Listcell(dis.getKbDtime());
		  Listcell c4=new Listcell(infor.getKiHits().toString());
		  item.appendChild(c);
		  item.appendChild(c0);
		  item.appendChild(c1);
		  item.appendChild(c2);
		  item.appendChild(c3);
		  item.appendChild(c4);
		  c1.setTooltiptext("点击查看详情");
		  c1.addEventListener(Events.ON_CLICK, new InnerListener());
		}
	}
	//查看信息详情
	class InnerListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem c=(Listitem)event.getTarget().getParent();
			WkTDistribute dis=(WkTDistribute)c.getValue();
			Executions.getCurrent().setAttribute("kiTitle", dis);
			NewsSelDetailWindow w=(NewsSelDetailWindow)Executions.createComponents("/admin/personal/infoExtract/newspub/newseldetail.zul",null, null);	
			w.doHighlighted();
		}
		}
	//关闭窗口
    public void onClick$back(){
    	
    	infosel.detach();
    }
    public List getIlist() {
		return ilist;
	}

	public void setIlist(ArrayList ilist) {
		this.ilist = ilist;
	}
}