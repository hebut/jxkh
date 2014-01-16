package org.iti.jxkh.score.manage;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

public class ShowWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 7709604284024931540L;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
}
