package org.iti.jxkh.membermanage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;


public class MemberManageWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox deptList;
	private Treerow y;
	private Listbox memberList;
	private Listcell dept;
	


	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
		y.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				deptList.setVisible(false);
				deptList.setVisible(true);
			}
		});
		dept.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {	
				memberList.setVisible(false);
				memberList.setVisible(true);
			}
		});
		
	}

	

}
