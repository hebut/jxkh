package org.iti.xypt.personal.infoCollect.MessageCenter;

import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class DistributeNews extends Window implements AfterCompose{

	Label infoSubject;
	public void afterCompose() {
		// TODO Auto-generated method stub
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWin(WkTDistribute distribute) {
		// TODO Auto-generated method stub
		infoSubject.setValue(distribute.getKbTitle());
	}

}
