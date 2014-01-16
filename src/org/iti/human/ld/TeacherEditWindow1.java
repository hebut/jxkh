package org.iti.human.ld;

import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.TitleSelectHbox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
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
public class TeacherEditWindow1 extends Window implements AfterCompose {

	// 用户所在根本部门
	WkTDept rootDept;
	Teacher teacher;
	WkTRole defaultRole;
	// 页面中各种输入框
	Textbox uPass, uRpass, uinfo, serach, trueName;
	Textbox uBandIp;
	Intbox lTimes;
	Listbox kuSex;
	Listbox uStatus, bangType;
	Checkbox autoLogin;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	DeptListbox deptSelect;
	TitleSelectHbox titleHbox;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;
	Label loginName, thid;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
	}

	public void initWindow() {
		deptSelect.setRootDept(getRootDept());
		deptSelect.setRootID(getRootDept().getKdId());
		deptSelect.initNewDeptSelect(teacher.getDept(getRootDept().getKdSchid()));
		loginName.setValue(teacher.getUser().getKuLid());
		trueName.setValue(teacher.getUser().getKuName());
		thid.setValue(teacher.getThId());
		kuSex.setSelectedIndex(Integer.valueOf(teacher.getUser().getKuSex().trim()) - 1);
		uPass.setValue(EncodeUtil.decodeByDESStr(teacher.getUser().getKuPasswd().trim()));
		uRpass.setValue(EncodeUtil.decodeByDESStr(teacher.getUser().getKuPasswd().trim()));
		titleHbox.setTitle(teacher.getTitle());
		if (teacher.getUser().getKuStatus() != null) {
			uStatus.setSelectedIndex(Integer.valueOf(teacher.getUser().getKuStatus()));
		}
		lTimes.setValue(teacher.getUser().getKuLimit() == null ? 0 : teacher.getUser().getKuLimit());
		bangType.setSelectedIndex(Integer.valueOf(teacher.getUser().getKuBindtype().trim()));
		bangTypeHandle();
		if (teacher.getUser().getKuAutoenter().trim().equalsIgnoreCase("1")) {
			autoLogin.setChecked(true);
		} else {
			autoLogin.setChecked(false);
		}
		uinfo.setValue(teacher.getUser().getKuIntro());
		serach.setValue(teacher.getThSearch());
	}

	public void onClick$submit() throws InterruptedException {
		WkTUser editUser = teacher.getUser();
		if (uPass.getValue().trim().equalsIgnoreCase(uRpass.getValue().trim())) {
			editUser.setKuPasswd(EncodeUtil.encodeByDES(uPass.getValue().toString().trim()));
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
		editUser.setKuLimit(lTimes.getValue());
		editUser.setKuLtimes(0);
		editUser.setKuSex(kuSex.getSelectedIndex() + 1 + "");
		editUser.setKuName(trueName.getValue());
		editUser.setKuStyle("default");
		editUser.setKuStatus(uStatus.getSelectedItem().getIndex() + "");
		editUser.setKuAutoshow("0");
		WkTDept d = (WkTDept) deptSelect.getSelectedItem().getValue();
		// 如果修改教师所属单位为教师根本所属单位，则修改用户表中单位，否则即认为借调教师调整
		if (editUser.getDept().getKdSchid().longValue() == d.getKdSchid().longValue()) {
			editUser.setKdId(d.getKdId());
		}
		editUser.setKuIntro(uinfo.getValue());
		userService.update(editUser);
		teacher.setThSearch(serach.getValue());
		teacher.setThTitle(titleHbox.getSelectTitle().getTiId());
		teacherService.update(teacher);
		XyUserroleId xyuId = new XyUserroleId(getDefaultRole().getKrId(), editUser.getKuId());
		XyUserrole xyu = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyuId);
		if (xyu.getKdId().longValue() != d.getKdId().longValue()) {
			xyu.setKdId(d.getKdId());
			xyUserRoleService.update(xyu);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow();
	}

	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// 不绑定
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			autoLogin.setChecked(false);
			autoLogin.setDisabled(true);
		} else {
			uBandIp.setReadonly(false);
			uBandIp.setValue(teacher.getUser().getKuBindaddr());
			autoLogin.setDisabled(false);
			if (teacher.getUser().getKuAutoenter().trim().equalsIgnoreCase("1")) {
				autoLogin.setChecked(true);
			} else {
				autoLogin.setChecked(false);
			}
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
	}

	public WkTRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(WkTRole defaultRole) {
		this.defaultRole = defaultRole;
	}
}
