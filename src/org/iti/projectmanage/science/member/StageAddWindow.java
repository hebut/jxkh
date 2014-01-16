package org.iti.projectmanage.science.member;



import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;



public class StageAddWindow extends BaseWindow {
	
	Listbox  stageNum;  
	Listbox stage;
	

@Override
	public void initShow() {
	// TODO Auto-generated method stub
	
//	stage.setVisible(false);
	stage.setRows(4);
	stage.setItemRenderer(new ListitemRenderer() {

		public void render(Listitem arg0, Object arg1) throws Exception {
			// TODO Auto-generated method stub
			
			Listcell c0 = new Listcell(arg0.getIndex()+1+"");
			Listcell c1 = new Listcell();			
			
			Textbox tb1 = new Textbox();
			
			tb1.setWidth("98%");
			tb1.setParent(c1);			
			Listcell c2 = new Listcell();
			Datebox db2 = new Datebox();
			
			db2.setWidth("80%");
			db2.setParent(c2);
			
			Listcell c3 = new Listcell();
			Datebox db3 = new Datebox();
			
			db3.setWidth("80%");
			db3.setParent(c3);
			
			arg0.appendChild(c0);
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
		
			
			
		}
		
	});
	
	stageNum.addEventListener(Events.ON_SELECT, new EventListener() {

		public void onEvent(Event arg0) throws Exception {
			// TODO Auto-generated method stub
			List list=new ArrayList();
			switch (stageNum.getSelectedIndex()) {
			case 0:
				stage.setVisible(false);
				break;
			case 1:
				list.add("");
				stage.setModel(new ListModelList(list));
				stage.setVisible(true);				
				break;
			case 2:
				list.add("");
				list.add("");
				stage.setModel(new ListModelList(list));
				stage.setVisible(true);				
				break;
			case 3:
				list.add("");
				list.add("");
				list.add("");
				stage.setModel(new ListModelList(list));
				stage.setVisible(true);				
				break;
			case 4:
				list.add("");
				list.add("");
				list.add("");
				list.add("");
				stage.setModel(new ListModelList(list));
				stage.setVisible(true);
			default:
				break;
			}
		}		
	});
	
	
	}

@Override
	public void initWindow() {
	// TODO Auto-generated method stub
	
	}

	public void onClick$close() {
		this.detach();
	}
	

}
