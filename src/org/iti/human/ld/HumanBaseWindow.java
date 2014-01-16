/**
 * 
 */
package org.iti.human.ld;

import org.iti.xypt.ui.base.BaseWindow;

import com.uniwin.framework.entity.WkTRole;

/**
 * <li>项目名称: xypt <li>功能描述: 该文件的功能描述
 * 
 * @author DaLei
 * @version $Id: HumanBaseWindow.java,v 1.1 2011/08/31 07:03:00 ljb Exp $
 */
public abstract class HumanBaseWindow extends BaseWindow {
	WkTRole role;

	public WkTRole getRole() {
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	int leave;

	public int getLeave() {
		return leave;
	}

	public void setLeave(int leave) {
		this.leave = leave;
	}
}
