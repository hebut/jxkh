package org.iti.xypt.personal.infoCollect;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

public class ResultReplace extends Window implements AfterCompose{

	public void afterCompose() {
		// TODO Auto-generated method stub
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	
}
