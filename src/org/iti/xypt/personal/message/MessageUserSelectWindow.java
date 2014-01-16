package org.iti.xypt.personal.message;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public abstract class MessageUserSelectWindow extends Window implements AfterCompose {
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	public abstract List<WkTUser> getSelectUser();

	public abstract void initShow();
}
