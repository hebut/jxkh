package org.iti.projectmanage.science.member;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

public class StageUnitWindow extends Window implements AfterCompose {
	Window stageUnit;
	Panel unitMange;
	public void afterCompose() {
		// TODO Auto-generated method stub
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void onClick$back()
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
		stageUnit.detach();
	}
	public void onClick$add()
	{
		unitMange.setVisible(false);
//		UnitAddWindow w=(UnitAddWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/addUnit.zul", null, null);
//		w.addEventListener(Events.ON_CHANGE, new EventListener() {
//			public void onEvent(Event arg0) throws Exception {
//				initShow();
//			}
//		});
//		stageUnit.appendChild(w);
	}
	public void initShow()
	{
		unitMange.setVisible(true);
	}

}
