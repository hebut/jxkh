package com.uniwin.framework.ui.user;

import java.util.Date;
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
public class UserNewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 新建用户时选择的部门
	WkTDept dept;
	// 页面中各种输入框
	Textbox loginName,folkId, trueName, uEmail, uPhone, uPass, uRpass, uQuestion, uAnswer;
	Textbox uOrangize, uBandIp;
	Intbox uLeave, lTimes;
	Datebox uBirth;
	Radio uMan, uWonmen;
	Listbox uStyle, uStatus, bangType;
	Checkbox autoLogin;
	private Radio jobIn,jobOut;
	// 组织部门下拉列表
	DeptListbox deptSelect;
	// 当前用户
	WkTUser user;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;

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

	public void initWindow(WkTDept dept) {
		this.dept = dept;
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		deptSelect.initNewDeptSelect(dept);
	}

	public void onClick$submit() throws InterruptedException {
		WkTUser editUser = new WkTUser();
		if (uPass.getValue().trim().equalsIgnoreCase(uRpass.getValue().trim())) {
			editUser.setKuPasswd(EncodeUtil.encodeByMD5(uPass.getValue()));
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
		if (loginName.getValue() == null || "".equals(loginName.getValue())) {
			throw new WrongValueException(loginName, "用户名不能为空！");
		}else {
			editUser.setKuLid(loginName.getValue().trim());
			if (userService.getUsersByLogin(editUser.getKuLid()).size() > 0) {
				throw new WrongValueException(loginName, "用户名已经存在！");
			}
		}				
		if(folkId.getValue() == null || "".equals(folkId.getValue())) {
			throw new WrongValueException(folkId, "员工编号必填，请填写！");
		}
		if(userService.getUserByStaffid(folkId.getValue().trim()).size() > 0) {
			throw new WrongValueException(folkId, "员工编号已存在，请重新填写！");
		}
		if(trueName.getValue() == null || "".equals(trueName.getValue())) {
			throw new WrongValueException(trueName, "真实姓名必填，请填写！");
		}
		if(uEmail.getValue() == null || "".equals(uEmail.getValue())) {
			throw new WrongValueException(uEmail, "电子邮箱必填，请填写！");
		}
		if(uPhone.getValue() == null || "".equals(uPhone.getValue())) {
			throw new WrongValueException(uPhone, "电话必填，请填写！");
		}
		editUser.setKuName(trueName.getValue().trim());
		editUser.setKuEmail(uEmail.getValue().trim());
		editUser.setKuPhone(uPhone.getValue().trim());
		editUser.setKuQuestion(uQuestion.getValue().trim());
		editUser.setKuAnswer(uAnswer.getValue().trim());
		editUser.setKuCompany(uOrangize.getValue().trim());
		editUser.setKuLevel(uLeave.getValue());
		editUser.setKuLimit(lTimes.getValue());
		editUser.setStaffId(folkId.getValue().trim());
		if(jobIn.isChecked()) {
			editUser.setKuType(Integer.valueOf(WkTUser.JOB_IN));
		}else if(jobOut.isChecked()) {
			editUser.setKuType(Integer.valueOf(WkTUser.JOB_OUT));
		}
		editUser.setKuLtimes(0);
		if (uBirth.getValue() != null) {
			editUser.setKuBirthday(DateUtil.getDateString(uBirth.getValue()));
		}
		if (uMan.isChecked()) {
			editUser.setKuSex(WkTUser.SEX_MAN);
		} else {
			editUser.setKuSex(WkTUser.SEX_WOMEN);
		}
		editUser.setKuStyle(uStyle.getSelectedItem().getLabel());
		editUser.setKuRegdate(DateUtil.getDateTimeString(new Date()));
		editUser.setKuStatus(uStatus.getSelectedItem().getIndex() + "");
		editUser.setKuAutoshow("0");
		WkTDept d = (WkTDept) deptSelect.getSelectedItem().getValue();
		editUser.setKdId(d.getKdId());
		userService.save(editUser);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "增加用户，id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow(getDept());
		loginName.setRawValue(null);
		folkId.setValue("");
		trueName.setRawValue(null);
		uEmail.setRawValue(null);
		uPhone.setRawValue(null);
		uQuestion.setRawValue(null);
		uAnswer.setRawValue(null);
		uOrangize.setRawValue(null);
		uLeave.setRawValue(null);
		uBandIp.setRawValue(null);
		lTimes.setRawValue(0);
		uBirth.setRawValue(null);
		uWonmen.setChecked(false);
		autoLogin.setChecked(false);
		bangType.setSelectedIndex(0);
		bangTypeHandle();
		uStatus.setSelectedIndex(1);
		uPass.setRawValue(null);
		uRpass.setRawValue(null);
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
}
