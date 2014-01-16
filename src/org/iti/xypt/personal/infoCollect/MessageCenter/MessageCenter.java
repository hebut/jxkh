package org.iti.xypt.personal.infoCollect.MessageCenter;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.service.NewsService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.framework.entity.WkTUser;

public class MessageCenter extends Panel implements AfterCompose {

	
	WkTUser wkTUser;
	private Listbox netlist;
	private Toolbarbutton allinfo;
	NewsService newsService;
	
	public void afterCompose() {
		// TODO Auto-generated method stub
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		netlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				final WkTDistribute distribute=(WkTDistribute)arg1;
				item.setValue(distribute);
				item.setHeight("25px");
				final Listcell c1 = new Listcell();
				c1.setImage("/images/ims.news.gif");
				
				Listcell c2 = new Listcell();
				

				c2.setStyle("color:blue");
				
				InnerButton inb = new InnerButton();
				
				String str0 = null;
				if (distribute.getKbTitle() == null) {
					str0 = "";
				} else {
					str0 = distribute.getKbTitle().trim();
					int len = str0.trim().length();
					if (len > 14) {
						str0 = str0.substring(0, 13) + "...";
					} else {
						str0 = distribute.getKbTitle();
					}
				}
				inb.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event event) throws Exception {                  
						
						DistributeNews distributeNews=(DistributeNews)Executions.createComponents("/admin/personal/message/new/info.zul", null, null);
						distributeNews.doHighlighted();
						distributeNews.initWin(distribute);
					}
				});
				
				inb.setLabel(str0);
				c2.appendChild(inb);
				
				item.appendChild(c1);
				item.appendChild(c2);
			}
			
		});
		
		reloadGrid();
	}

	private void reloadGrid() {
		// TODO Auto-generated method stub
		List list = newsService.findAll(WkTDistribute.class);
		netlist.setModel(new ListModelList(list));
		
	}

	public void onClick$allinfo() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
}
