package com.uniwin.framework.ui.title;

/**
 * <li>标题权限编辑界面,更加详细的的单独权限权限设置,对应页面admin/system/title/titleAuthDet.zul
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.common.listbox.DeptRoleListbox;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;

public class TitleAuthDetWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 角色列表组件
	DeptRoleListbox roleSelect;
	// 部门列表组件
	DeptListbox deptSelect;
	// 当前用户
	WkTUser user;
	DepartmentService departmentService;
	AuthService authService;
	Textbox IPAddr;
	Checkbox accessCK, managerCK;
	// 用户管理部门列表
	List<Long> userDeptList;
	WkTAuth auth;
	MLogService mlogService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
	}

	public void ininWindow(WkTAuth auth) {
		this.auth = auth;
		roleSelect.setRootRole(null);
		roleSelect.setDeptList(userDeptList);
		roleSelect.initRoleSelect(auth.getKrId());
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		if (auth.getKdId() == null) {
			deptSelect.initNewDeptSelect(deptSelect.getRootDept());
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, auth.getKdId());
			if (dept == null) {
				deptSelect.initNewDeptSelect(deptSelect.getRootDept());
			} else {
				deptSelect.initNewDeptSelect(dept);
			}
		}
		if (user.getKdId().intValue() != 0) {
			WkTDept rootDept = new WkTDept();
			rootDept.setKdName(" ");
			rootDept.setKdId(0L);
			rootDept.setDep(0);
			deptSelect.getDmodelList().add(0, rootDept);
			deptSelect.setRootDept(rootDept);
		} else {
			deptSelect.getRootDept().setKdName(" ");
		}
		IPAddr.setValue(auth.getIP());
		if (auth.getKaCode1().intValue() == 1) {
			accessCK.setChecked(true);
		} else {
			accessCK.setChecked(false);
		}
		if (auth.getKaCode2().intValue() == 1) {
			managerCK.setChecked(true);
		} else {
			managerCK.setChecked(false);
		}
	}

	public void onClick$submit() throws InterruptedException {
		WkTRole role = (WkTRole) roleSelect.getSelectedItem().getValue();
		WkTDept dept = (WkTDept) deptSelect.getSelectedItem().getValue();
		auth.setKaType(WkTAuth.TYPE_TITLE);
		auth.setKrId(role.getKrId());
		auth.setKdId(dept.getKdId());
		if (IPAddr.getValue().length() > 0) {
			IPUtil.setIP(auth, IPAddr.getValue());
		}
		if (!(accessCK.isChecked() || managerCK.isChecked())) {
			Messagebox.show("请设置权限类别", "错误提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (role.getKrPid() != null && role.getKrPid().intValue() == 0) {
			return;
		}
		if (role.getKrId().intValue() == 0 && dept.getKdId().intValue() == 0) {
			if (user.getKdId().intValue() != 0) {
				return;
			}
		}
		if (accessCK.isChecked()) {
			auth.setKaCode1(TitleAuthWindow.check);
		} else {
			auth.setKaCode1(TitleAuthWindow.uncheck);
		}
		if (managerCK.isChecked()) {
			auth.setKaCode2(TitleAuthWindow.check);
		} else {
			auth.setKaCode2(TitleAuthWindow.uncheck);
		}
		auth.setKaCode(auth.getKaCode1() + "" + auth.getKaCode2());
		if (auth.getKaId() == null) {
			authService.save(auth);
		} else {
			authService.update(auth);
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "编辑标题权限，id：" + auth.getKaId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		ininWindow(auth);
	}

	public void onClick$close() {
		this.detach();
	}
}
