/**
 * 
 */
package org.iti.xypt.ui.base;

import org.iti.xypt.entity.XyUserrole;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

/**
 * @author DaLei
 * @version $Id: XzrWindow.java,v 1.1 2011/08/31 07:03:04 ljb Exp $
 */
public abstract class XzrWindow extends Window implements AfterCompose {
	XyUserrole xyUserRole;

	public XyUserrole getXyUserRole() {
		return xyUserRole;
	}

	public void setXyUserRole(XyUserrole xyUserRole) {
		this.xyUserRole = xyUserRole;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public abstract void initWindow();
}
