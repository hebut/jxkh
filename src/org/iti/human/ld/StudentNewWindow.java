package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.bysj.ui.listbox.XyClassList;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyNUrdId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.common.util.IPUtil;
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
public class StudentNewWindow extends Window implements AfterCompose {
	// 新建用户时选择的部门
	WkTDept dept;
	WkTRole defaultRole;
	// 页面中各种输入框
	Textbox loginName, trueName, stid, uPass, uRpass, uinfo;
	Textbox uBandIp;
	Intbox lTimes;
	Listbox uStatus, bangType, kuSex;
	Checkbox autoLogin;
	GradeListbox stGradeList;
	XyClassList stClassList;
	StudentService studentService;
	XyUserRoleService xyUserRoleService;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;
	Integer grade;
	WkTUser user;
	Listbox xueji, bynf;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		xueji.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Short index = (Short) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(Student.NORMALS[index]);
				if (arg0.getIndex() == 0) {
					arg0.setSelected(true);
				}
			}
		});
		bynf.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				Integer year = (Integer) arg1;
				arg0.setLabel(year + "届");
				if (arg0.getIndex() == 2) {
					arg0.setSelected(true);
				}
			}
		});
		stGradeList.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				stClassList.setGrade(stGradeList.getSelectGrade());
				stClassList.loadList();
			}
		});
		List xjlist = new ArrayList();
		String[] str = Student.NORMALS;
		for (short i = 0; i < str.length; i++) {
			xjlist.add(i);
		}
		xueji.setModel(new ListModelList(xjlist));
		List bynflist = new ArrayList();
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);
		for (int i = -2; i < 8; i++) {
			bynflist.add(year + i);
		}
		bynf.setModel(new ListModelList(bynflist));
		bangTypeHandle();
	}

	public WkTDept getDept() {
		return dept;
	}

	private Integer getBynf() {
		if (bynf.getSelectedItem() == null) {
			return (Integer) bynf.getModel().getElementAt(0);
		} else {
			return (Integer) bynf.getSelectedItem().getValue();
		}
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
		editUser.setKdId(getDept().getKdId());
		editUser.setKuIntro(uinfo.getValue());
		if (stClassList.getSelectClass() == null) {
			throw new WrongValueException(stClassList, "请选择班级，如果无班级请换年级");
		}
		String sid = stid.getValue();
		List slist = studentService.findBySid(sid);
		for (int i = 0; i < slist.size(); i++) {
			Student st = (Student) slist.get(i);
			if (st.getUser().getDept().getKdSchid().longValue() == getDept().getKdSchid().longValue()) {
				throw new WrongValueException(stid, "学号已经存在，与" + st.getUser().getKuName() + "相同");
			}
		}
		userService.save(editUser);
		Student stu = new Student();
		stu.setStId(sid);
		stu.setKuId(editUser.getKuId());
		stu.setStClass(stClassList.getSelectClass().getClSname());
		stu.setClId(stClassList.getSelectClass().getClId());
		stu.setStGrade(stClassList.getSelectClass().getClGrade());
		Short stNormal = 0;
		if (xueji.getSelectedItem() == null) {
			stNormal = 0;
		} else {
			stNormal = (Short) xueji.getSelectedItem().getValue();
		}
		stu.setStNormal(stNormal);
		stu.setStBynf(getBynf());
		studentService.save(stu);
		XyUserroleId xyuId = new XyUserroleId(getDefaultRole().getKrId(), editUser.getKuId());
		XyUserrole xyu = new XyUserrole(xyuId, editUser.getKdId());
		xyUserRoleService.saveXyUserrole(xyu);
		XyNUrdId xnurid = new XyNUrdId(xyuId.getKrId(), xyuId.getKuId(), stu.getClId(), XyNUrd.TYPE_CLID);
		XyNUrd xnu = new XyNUrd(xnurid);
		xyUserRoleService.save(xnu);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "增加用户，id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
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

	public void setDept(WkTDept dept) {
		this.dept = dept;
		List dlist = new ArrayList();
		dlist.add(dept);
		stGradeList.setDeptList(dlist);
		stGradeList.loadList();
		stClassList.setDept(dept);
		stClassList.loadList();
	}

	public WkTRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(WkTRole defaultRole) {
		this.defaultRole = defaultRole;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
		stClassList.setGrade(grade);
		stGradeList.setGrade(grade);
	}
}
