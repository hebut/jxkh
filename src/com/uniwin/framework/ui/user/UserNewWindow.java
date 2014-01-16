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
 * <li>��������: ���ļ��Ĺ�������
 * 
 * @author DaLei
 * @date 2010-3-18
 */
public class UserNewWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// �½��û�ʱѡ��Ĳ���
	WkTDept dept;
	// ҳ���и��������
	Textbox loginName,folkId, trueName, uEmail, uPhone, uPass, uRpass, uQuestion, uAnswer;
	Textbox uOrangize, uBandIp;
	Intbox uLeave, lTimes;
	Datebox uBirth;
	Radio uMan, uWonmen;
	Listbox uStyle, uStatus, bangType;
	Checkbox autoLogin;
	private Radio jobIn,jobOut;
	// ��֯���������б�
	DeptListbox deptSelect;
	// ��ǰ�û�
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
			Messagebox.show("�����������", "����", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		// ����û�ѡ�񲻰󶨣��������䲻���Զ���½��ͬʱ����IP��ַ���ÿ�.
		// ����û�ѡ��IP�󶨣��������ð󶨵�IP��ַ���������������Ϊ����IP���������ø��û��ϴ���½IP��ַ��
		// ѡ��IP�󶨲������ð�IP���ж��û��Ƿ������Զ���½
		if (bangType.getSelectedIndex() == 0) {// ѡ�񲻰�
			editUser.setKuBindtype(WkTUser.BAND_NO);
			editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			editUser.setKuBindaddr("");
		} else {
			try {
				IPUtil.getIPLong(uBandIp.getValue());
				editUser.setKuBindaddr(uBandIp.getValue());
			} catch (Exception e) {
				throw new WrongValueException(uBandIp, "�󶨵�ַ�������!");
			}
			editUser.setKuBindtype(WkTUser.BAND_YES);
			if (autoLogin.isChecked()) {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_YES);
			} else {
				editUser.setKuAutoenter(WkTUser.AUTOENTER_NO);
			}
		}
		if (loginName.getValue() == null || "".equals(loginName.getValue())) {
			throw new WrongValueException(loginName, "�û�������Ϊ�գ�");
		}else {
			editUser.setKuLid(loginName.getValue().trim());
			if (userService.getUsersByLogin(editUser.getKuLid()).size() > 0) {
				throw new WrongValueException(loginName, "�û����Ѿ����ڣ�");
			}
		}				
		if(folkId.getValue() == null || "".equals(folkId.getValue())) {
			throw new WrongValueException(folkId, "Ա����ű������д��");
		}
		if(userService.getUserByStaffid(folkId.getValue().trim()).size() > 0) {
			throw new WrongValueException(folkId, "Ա������Ѵ��ڣ���������д��");
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
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�����û���id:" + editUser.getKuId(), user);
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
		if (bangType.getSelectedIndex() == 0) {// ����
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
