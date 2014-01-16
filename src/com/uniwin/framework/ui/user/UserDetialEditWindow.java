package com.uniwin.framework.ui.user;

/**
 * <li>用户详细信息编辑界面,/admin/system/user/userDetail.zul
 * @author DaLei
 * @date 2010-4-5
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
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

public class UserDetialEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * editUser 为此页面编辑的用户对象,user为当前用户 dept 为当前用户所在部门
	 */
	WkTUser editUser, user;
	WkTDept dept;
	Textbox loginName,folkId, trueName, uEmail, uPhone, uQuestion, uAnswer;
	private Textbox uPass,uRpass;
	Textbox uOrangize, uBandIp;
	Intbox uLeave, lTimes;
	Datebox uBirth;
	Radio uMan, uWonmen;
	Listbox uStyle, uStatus, bangType;
	Checkbox autoLogin;
	private Radio jobIn,jobOut;
	DepartmentService departmentService;
	UserService userService;
	// 部门下拉框选择组件
	DeptListbox deptSelect;
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
		WkTUser edituser = (WkTUser) Executions.getCurrent().getAttribute("editUser");
		initWindow(edituser);
	}

	/**
	 * <li>功能描述：处理页面保存点击事件，保存编辑后的用户信息.。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$submit() {
		// 如果用户选择不绑定，则设置其不能自动登陆，同时将绑定IP地址设置空.
		// 如果用户选择IP绑定，首先设置绑定的IP地址，如果输入则设置为输入IP，否则设置该用户上传登陆IP地址。
		// 选择IP绑定并且设置绑定IP后，判断用户是否设置自动登陆
		if (bangType.getSelectedIndex() == 0) {// 选择不绑定
			editUser.setKuBindtype(WkTUser.BAND_NO);
			editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			editUser.setKuBindaddr("");
		} else {
			editUser.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				editUser.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "绑定地址输入错误!");
			}
			if (autoLogin.isChecked()) {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_YES);
			} else {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			}
		}
		if (uPass.getValue().trim().equalsIgnoreCase(uRpass.getValue().trim())) {
			editUser.setKuPasswd(EncodeUtil.encodeByMD5(uPass.getValue()));
		} else {
			try {
				Messagebox.show("密码输入错误", "错误", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		/*if (userService.getUsersByLogin(editUser.getKuLid()).size() > 0) {
			throw new WrongValueException(loginName, "用户名已经存在！");
		}*/
		if (loginName.getValue() == null || "".equals(loginName.getValue())) {
			throw new WrongValueException(loginName, "用户名不能为空！");
		}else {
			if (userService.getUsersByLogin(loginName.getValue().trim()).size() > 0) {
				if(!loginName.getValue().trim().equals(editUser.getKuLid()))
					throw new WrongValueException(loginName, "用户名已经存在！");
			}
		}		
		if(folkId.getValue() == null || "".equals(folkId.getValue())) {
			throw new WrongValueException(folkId, "员工编号必填，请填写！");
		}else {
			if(userService.getUserByStaffid(folkId.getValue().trim()).size() > 0) {
				if(!folkId.getValue().trim().equals(editUser.getStaffId()))
					throw new WrongValueException(folkId, "员工编号已存在，请重新填写！");
			}
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
		editUser.setKuName(trueName.getValue());
		editUser.setStaffId(folkId.getValue().trim());
		if(jobIn.isChecked()) {
			editUser.setKuType(Integer.valueOf(WkTUser.JOB_IN));
		}else if(jobOut.isChecked()) {
			editUser.setKuType(Integer.valueOf(WkTUser.JOB_OUT));
		}
		editUser.setKuEmail(uEmail.getValue());
		editUser.setKuPhone(uPhone.getValue());
		editUser.setKuQuestion(uQuestion.getValue());
		editUser.setKuAnswer(uAnswer.getValue());
		editUser.setKuCompany(uOrangize.getValue());
		editUser.setKuLevel(uLeave.getValue());
		editUser.setKuLimit(Integer.valueOf(lTimes.getValue()));
		if (uBirth.getValue() != null) {
			editUser.setKuBirthday(DateUtil.getDateString(uBirth.getValue()));
		}
		if (uMan.isChecked()) {
			editUser.setKuSex(WkTUser.SEX_MAN);
		} else {
			editUser.setKuSex(WkTUser.SEX_WOMEN);
		}
		editUser.setKuStyle(String.valueOf(uStyle.getSelectedItem().getValue()));
		editUser.setKuStatus(uStatus.getSelectedIndex() + "");
		WkTDept d = (WkTDept) deptSelect.getSelectedItem().getValue();
		editUser.setKdId(d.getKdId());
		userService.update(editUser);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "编辑用户信息，id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>功能描述：重置页面信息。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$reset() {
		initWindow(editUser);
	}

	/**
	 * <li>功能描述：用户角色选项编辑事件。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$userRole() {
		WkTUser u = getUser();
		UserRoleSelectWindow w = (UserRoleSelectWindow) Executions.createComponents("/admin/system/user/userRoleSelect.zul", null, null);
		w.doHighlighted();
		w.initWindow(u);
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTUser getUser() {
		return editUser;
	}

	/**
	 * <li>功能描述：初始化页面信息显示。
	 * 
	 * @param edituser
	 *            void
	 * @author DaLei
	 */
	public void initWindow(WkTUser edituser) {
		this.editUser = edituser;
		loginName.setValue(editUser.getKuLid());
		trueName.setValue(editUser.getKuName());
		folkId.setValue(editUser.getStaffId());
		if(editUser.getKuType() != null) {
			switch(editUser.getKuType().intValue()) {
			case WkTUser.JOB_IN:
				jobIn.setChecked(true);
				break;
			case WkTUser.JOB_OUT:
				jobOut.setChecked(true);
				break;
			}
		}		
		uEmail.setValue(editUser.getKuEmail());
		uPhone.setValue(editUser.getKuPhone());
		uQuestion.setValue(editUser.getKuQuestion());
		uAnswer.setValue(editUser.getKuAnswer());
		uOrangize.setValue(editUser.getKuCompany() == null ? "" : editUser.getKuCompany().trim());
		uLeave.setValue(editUser.getKuLevel());
		uBandIp.setValue(editUser.getKuBindaddr());
		lTimes.setValue(editUser.getKuLimit() == null ? 0 : editUser.getKuLimit());
		if (editUser.getKuPasswd() != null && !"".equals(editUser.getKuPasswd())) {			
			uPass.setValue(editUser.getKuPasswd());
			uRpass.setValue(editUser.getKuPasswd());
		}
		if (editUser.getKuBirthday() != null && editUser.getKuBirthday().length() > 0) {
			uBirth.setValue(DateUtil.getDate(editUser.getKuBirthday()));
		}
		if (editUser.getKuSex().equalsIgnoreCase("2")) {
			uWonmen.setChecked(true);
		} else {
			uMan.setChecked(true);
		}
		if (editUser.getKuAutoenter().trim().equalsIgnoreCase("1")) {
			autoLogin.setChecked(true);
		} else {
			autoLogin.setChecked(false);
		}
		if (editUser.getKuStatus() != null) {
			uStatus.setSelectedIndex(Integer.valueOf(editUser.getKuStatus()));
		}
		bangType.setSelectedIndex(Integer.valueOf(editUser.getKuBindtype().trim()));
		bangTypeHandle();
		// 初始化部门列表
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		deptSelect.initNewDeptSelect(((WkTDept) departmentService.get(WkTDept.class, editUser.getKdId())));
	}

	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// 不绑定
			uBandIp.setRawValue(null);
			uBandIp.setReadonly(true);
			autoLogin.setChecked(false);
			autoLogin.setDisabled(true);
		} else {
			uBandIp.setReadonly(false);
			uBandIp.setValue(editUser.getKuBindaddr());
			autoLogin.setDisabled(false);
			if (editUser.getKuAutoenter().trim().equalsIgnoreCase("1")) {
				autoLogin.setChecked(true);
			} else {
				autoLogin.setChecked(false);
			}
		}
	}

	public WkTDept getDept() {
		return dept;
	}
}
