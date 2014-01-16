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
 * <li>��������: ���ļ��Ĺ�������
 * 
 * @author DaLei
 * @date 2010-3-18
 */
public class TeacherEditWindow1 extends Window implements AfterCompose {

	// �û����ڸ�������
	WkTDept rootDept;
	Teacher teacher;
	WkTRole defaultRole;
	// ҳ���и��������
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
		editUser.setKuLimit(lTimes.getValue());
		editUser.setKuLtimes(0);
		editUser.setKuSex(kuSex.getSelectedIndex() + 1 + "");
		editUser.setKuName(trueName.getValue());
		editUser.setKuStyle("default");
		editUser.setKuStatus(uStatus.getSelectedItem().getIndex() + "");
		editUser.setKuAutoshow("0");
		WkTDept d = (WkTDept) deptSelect.getSelectedItem().getValue();
		// ����޸Ľ�ʦ������λΪ��ʦ����������λ�����޸��û����е�λ��������Ϊ�����ʦ����
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
		if (bangType.getSelectedIndex() == 0) {// ����
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
