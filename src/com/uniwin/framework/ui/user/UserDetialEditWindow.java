package com.uniwin.framework.ui.user;

/**
 * <li>�û���ϸ��Ϣ�༭����,/admin/system/user/userDetail.zul
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
	 * editUser Ϊ��ҳ��༭���û�����,userΪ��ǰ�û� dept Ϊ��ǰ�û����ڲ���
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
	// ����������ѡ�����
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
	 * <li>��������������ҳ�汣�����¼�������༭����û���Ϣ.�� void
	 * 
	 * @author DaLei
	 */
	public void onClick$submit() {
		// ����û�ѡ�񲻰󶨣��������䲻���Զ���½��ͬʱ����IP��ַ���ÿ�.
		// ����û�ѡ��IP�󶨣��������ð󶨵�IP��ַ���������������Ϊ����IP���������ø��û��ϴ���½IP��ַ��
		// ѡ��IP�󶨲������ð�IP���ж��û��Ƿ������Զ���½
		if (bangType.getSelectedIndex() == 0) {// ѡ�񲻰�
			editUser.setKuBindtype(WkTUser.BAND_NO);
			editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			editUser.setKuBindaddr("");
		} else {
			editUser.setKuBindtype(WkTUser.BAND_YES);
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				editUser.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "�󶨵�ַ�������!");
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
				Messagebox.show("�����������", "����", Messagebox.OK, Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		/*if (userService.getUsersByLogin(editUser.getKuLid()).size() > 0) {
			throw new WrongValueException(loginName, "�û����Ѿ����ڣ�");
		}*/
		if (loginName.getValue() == null || "".equals(loginName.getValue())) {
			throw new WrongValueException(loginName, "�û�������Ϊ�գ�");
		}else {
			if (userService.getUsersByLogin(loginName.getValue().trim()).size() > 0) {
				if(!loginName.getValue().trim().equals(editUser.getKuLid()))
					throw new WrongValueException(loginName, "�û����Ѿ����ڣ�");
			}
		}		
		if(folkId.getValue() == null || "".equals(folkId.getValue())) {
			throw new WrongValueException(folkId, "Ա����ű������д��");
		}else {
			if(userService.getUserByStaffid(folkId.getValue().trim()).size() > 0) {
				if(!folkId.getValue().trim().equals(editUser.getStaffId()))
					throw new WrongValueException(folkId, "Ա������Ѵ��ڣ���������д��");
			}
		}		
		if(trueName.getValue() == null || "".equals(trueName.getValue())) {
			throw new WrongValueException(trueName, "��ʵ�����������д��");
		}
		if(uEmail.getValue() == null || "".equals(uEmail.getValue())) {
			throw new WrongValueException(uEmail, "��������������д��");
		}
		if(uPhone.getValue() == null || "".equals(uPhone.getValue())) {
			throw new WrongValueException(uPhone, "�绰�������д��");
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
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�༭�û���Ϣ��id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>��������������ҳ����Ϣ�� void
	 * 
	 * @author DaLei
	 */
	public void onClick$reset() {
		initWindow(editUser);
	}

	/**
	 * <li>�����������û���ɫѡ��༭�¼��� void
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
	 * <li>������������ʼ��ҳ����Ϣ��ʾ��
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
		// ��ʼ�������б�
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		deptSelect.setRootDept(pdept);
		deptSelect.setRootID(user.getKdId());
		deptSelect.initNewDeptSelect(((WkTDept) departmentService.get(WkTDept.class, editUser.getKdId())));
	}

	private void bangTypeHandle() {
		if (bangType.getSelectedIndex() == 0) {// ����
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
