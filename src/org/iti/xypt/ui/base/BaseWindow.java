package org.iti.xypt.ui.base;

import org.iti.xypt.entity.XyUserrole;

import com.uniwin.framework.entity.WkTDept;

public abstract class BaseWindow extends FrameworkWindow {
	private WkTDept schoolDept;
	private XyUserrole xyUserRole;

	public XyUserrole getXyUserRole() {
		return xyUserRole;
	}

	public void setXyUserRole(XyUserrole xyUserRole) {
		this.xyUserRole = xyUserRole;
	}

	public WkTDept getSchoolDept() {
		return schoolDept;
	}

	public void setSchoolDept(WkTDept schoolDept) {
		this.schoolDept = schoolDept;
	}
}
