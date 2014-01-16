package org.iti.human.ld;

import java.util.Date;
import java.util.List;

import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.TitleSelectHbox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;

/**
 * <li>功能描述: 该文件的功能描述
 * 
 * @author DaLei
 * @date 2010-3-18
 */
public class TeacherNewWindow extends Window implements AfterCompose {
	// 用户所在根本部门
	WkTDept rootDept;
	// 新建用户时选择的部门
	WkTDept dept;
	WkTRole defaultRole;
	// 页面中各种输入框
	Textbox loginName, trueName, thid, uPass, uRpass, uinfo, serach;
	Textbox uBandIp;
	Intbox lTimes;
	Listbox uStatus, bangType, kuSex;
	Checkbox autoLogin;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	DeptListbox deptSelect;
	TitleSelectHbox titleHbox;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;
	WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
	}

	public WkTDept getDept() {
		return dept;
	}

	public void initWindow() {
		deptSelect.setRootDept(getRootDept());
		deptSelect.setRootID(getRootDept().getKdId());
		deptSelect.initNewDeptSelect(getDept());
		bangTypeHandle();
	}

	public void onClick$submit() throws InterruptedException {
		WkTUser editUser = new WkTUser();
		if (uPass.getValue().trim().equalsIgnoreCase(uRpass.getValue().trim())) {
			editUser.setKuPasswd(EncodeUtil.encodeByMD5(uPass.getValue().toString().trim()));
		} else {
			Messagebox.show("密码输入错误", "错误", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		// 如果用户选择不绑定，则设置其不能自动登陆，同时将绑定IP地址设置空.
		// 如果用户选择IP绑定，首先设置绑定的IP地址，如果输入则设置为输入IP，否则设置该用户上传登陆IP地址。
		// 选择IP绑定并且设置绑定IP后，判断用户是否设置自动登陆
		if (bangType.getSelectedIndex() == 0) {// 选择不绑定
			editUser.setKuBindtype(WkTUser.BAND_NO);
			editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			editUser.setKuBindaddr("");
		} else {
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				editUser.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "绑定地址输入错误!");
			}
			editUser.setKuBindtype(WkTUser.BAND_YES);
			if (autoLogin.isChecked()) {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_YES);
			} else {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			}
		}
		editUser.setKuLid(loginName.getValue().trim());
		if (userService.getUsersByLogin(editUser.getKuLid()).size() > 0) {
			throw new WrongValueException(loginName, "用户名已经存在！");
		}
		editUser.setKuName(trueName.getValue().trim());
		editUser.setKuLimit(lTimes.getValue());
		editUser.setKuLtimes(0);
		editUser.setKuSex(kuSex.getSelectedIndex() + 1 + "");
		editUser.setKuStyle("default");
		editUser.setKuRegdate(DateUtil.getDateTimeString(new Date()));
		editUser.setKuStatus(uStatus.getSelectedItem().getIndex() + "");
		editUser.setKuAutoshow("0");
		WkTDept d = (WkTDept) deptSelect.getSelectedItem().getValue();
		editUser.setKdId(d.getKdId());
		editUser.setKuIntro(uinfo.getValue());
		List tlist = teacherService.findByThidAndKdid(thid.getValue(), getDept().getKdSchid());
		if (tlist.size() > 0) {
			throw new WrongValueException(thid, "教师号已经存在相同");
		}
		Teacher tea = new Teacher();
		tea.setKdId(getDefaultRole().getKdId());
		tea.setThId(thid.getValue());
		userService.save(editUser);
		tea.setKuId(editUser.getKuId());
		tea.setThSearch(serach.getValue());
		tea.setThTitle(titleHbox.getSelectTitle().getTiId());
		teacherService.save(tea);
		XyUserroleId xyuId = new XyUserroleId(getDefaultRole().getKrId(), editUser.getKuId());
		XyUserrole xyu = new XyUserrole(xyuId, editUser.getKdId());
		xyUserRoleService.saveXyUserrole(xyu);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "增加用户，id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow();
		loginName.setRawValue(null);
		trueName.setRawValue(null);
		uBandIp.setRawValue(null);
		lTimes.setRawValue(0);
		kuSex.setSelectedIndex(0);
		autoLogin.setChecked(false);
		bangType.setSelectedIndex(0);
		bangTypeHandle();
		uStatus.setSelectedIndex(1);
		uPass.setRawValue(null);
		uRpass.setRawValue(null);
		thid.setRawValue(null);
		serach.setRawValue(null);
		uinfo.setRawValue(null);
	}

	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// 不绑定
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			autoLogin.setChecked(false);
			autoLogin.setDisabled(true);
		} else {
			uBandIp.setReadonly(false);
			uBandIp.setValue(null);
			autoLogin.setDisabled(false);
			autoLogin.setChecked(false);
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	public WkTRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(WkTRole defaultRole) {
		this.defaultRole = defaultRole;
	}
}
