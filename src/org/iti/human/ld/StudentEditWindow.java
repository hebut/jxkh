package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.bysj.ui.listbox.XyClassList;
import org.iti.xypt.entity.Student;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
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
public class StudentEditWindow extends Window implements AfterCompose {
	WkTRole defaultRole;
	WkTDept rootDept;
	// 页面中各种输入框
	Label loginName, trueName, stid;
	Label yuan, xi;
	Textbox uPass, uRpass, uinfo;
	Textbox uBandIp;
	Intbox lTimes;
	Listbox uStatus, bangType, kuSex, bumen, xueyuan, bynf;
	Checkbox autoLogin;
	GradeListbox stGradeList;
	XyClassList stClassList;
	Listbox xueji;
	StudentService studentService;
	XyUserRoleService xyUserRoleService;
	BsStudentService bsStudentService;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;
	GpunitService gpunitService;
	WkTUser user;
	Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		bangType.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				bangTypeHandle();
			}
		});
		stGradeList.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				stClassList.setGrade(stGradeList.getSelectGrade());
				stClassList.loadList();
			}
		});
		xueyuan.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dep = (WkTDept) arg1;
				arg0.setValue(dep);
				arg0.setLabel(dep.getKdName());
				if (student.getUser().getDept().getPdept().getKdId().longValue() == dep.getKdId().longValue()) {
					arg0.setSelected(true);
				}
			}
		});
		bumen.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(dept.getKdName());
				if (student.getUser().getDept().getKdId().longValue() == dept.getKdId().longValue()) {
					arg0.setSelected(true);
				}
			}
		});
		xueji.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Short index = (Short) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(Student.NORMALS[index]);
				if (student.getStNormal().shortValue() == index.shortValue()) {
					arg0.setSelected(true);
				}
			}
		});
		bynf.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				Integer year = (Integer) arg1;
				arg0.setLabel(year + "届");
				if (student != null && student.getStBynf().intValue() == year.intValue()) {
					arg0.setSelected(true);
				}
			}
		});
		xueyuan.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadZY((WkTDept) xueyuan.getSelectedItem().getValue());
			}
		});
		bumen.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				WkTDept dept = (WkTDept) bumen.getSelectedItem().getValue();
				List dlist = new ArrayList();
				dlist.add(dept);
				stGradeList.setDeptList(dlist);
				stGradeList.setGrade(0);
				stGradeList.loadList();
				stClassList.setDept(dept);
				stClassList.setGrade(stGradeList.getSelectGrade());
				stClassList.loadList();
			}
		});
		bangTypeHandle();
	}

	private Integer getBynf() {
		if (bynf.getSelectedItem() == null) {
			return (Integer) bynf.getModel().getElementAt(0);
		} else {
			return (Integer) bynf.getSelectedItem().getValue();
		}
	}

	private void loadZY(WkTDept value) {
		List xlist = departmentService.getChildDepartment(value.getKdId(), WkTDept.DANWEI);
		bumen.setModel(new ListModelList(xlist));
	}

	/**
	 * 需要提前设置defaultRole,student变量
	 * 
	 */
	public void initWindow() {
		// 学院列表,系列表
		List ylist = new ArrayList(), xilist = new ArrayList();
		if (rootDept.getKdLevel().intValue() == 1) {
			ylist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.DANWEI);
			xilist = departmentService.getChildDepartment(student.getUser().getDept().getPdept().getKdId(), WkTDept.DANWEI);
		} else if (rootDept.getKdLevel().intValue() == 2) {
			ylist.add(rootDept);
			xilist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.DANWEI);
		} else {
			ylist.add(rootDept.getPdept());
			xilist.add(student.getUser().getDept());
		}
		yuan.setValue(rootDept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setValue(rootDept.getGradeName(WkTDept.GRADE_XI));
		xueyuan.setModel(new ListModelList(ylist));
		bumen.setModel(new ListModelList(xilist));
		stClassList.setGrade(student.getStGrade());
		stClassList.setXyClass(student.getXyClass());
		stGradeList.setGrade(student.getStGrade());
		List dlist = new ArrayList();
		dlist.add(student.getUser().getDept());
		stGradeList.setDeptList(dlist);
		stGradeList.loadList();
		stClassList.setDept(student.getUser().getDept());
		stClassList.loadList();
		loginName.setValue(student.getUser().getKuLid());
		trueName.setValue(student.getUser().getKuName());
		stid.setValue(student.getStId() + "");
		kuSex.setSelectedIndex(Integer.valueOf(student.getUser().getKuSex().trim()) - 1);
		uPass.setValue(student.getUser().getKuPasswd());
		uRpass.setValue(student.getUser().getKuPasswd());
		if (student.getUser().getKuStatus() != null) {
			uStatus.setSelectedIndex(Integer.valueOf(student.getUser().getKuStatus()));
		}
		lTimes.setValue(student.getUser().getKuLimit() == null ? 0 : student.getUser().getKuLimit());
		bangType.setSelectedIndex(Integer.valueOf(student.getUser().getKuBindtype().trim()));
		bangTypeHandle();
		if (student.getUser().getKuAutoenter().trim().equalsIgnoreCase("1")) {
			autoLogin.setChecked(true);
		} else {
			autoLogin.setChecked(false);
		}
		uinfo.setValue(student.getUser().getKuIntro());
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
	}

	public void onClick$submit() throws InterruptedException {
		WkTUser editUser = student.getUser();
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
		editUser.setKuName(trueName.getValue().trim());
		editUser.setKuLimit(lTimes.getValue());
		editUser.setKuLtimes(0);
		editUser.setKuSex(kuSex.getSelectedIndex() + 1 + "");
		editUser.setKuStyle("default");
		editUser.setKuStatus(uStatus.getSelectedItem().getIndex() + "");
		editUser.setKuAutoshow("0");
		WkTDept selectDept;
		if (bumen.getSelectedItem() == null) {
			selectDept = (WkTDept) bumen.getModel().getElementAt(0);
		} else {
			selectDept = (WkTDept) bumen.getSelectedItem().getValue();
		}
		editUser.setKdId(selectDept.getKdId());
		editUser.setKuIntro(uinfo.getValue());
		if (stClassList.getSelectClass() == null) {
			throw new WrongValueException(stClassList, "请选择班级，如果无班级请换年级");
		}
		userService.update(editUser);
		Short stNormal = 0;
		if (xueji.getSelectedItem() == null) {
			stNormal = 0;
		} else {
			stNormal = (Short) xueji.getSelectedItem().getValue();
		}
		student.setStNormal(stNormal);
		student.setStClass(stClassList.getSelectClass().getClSname());
		student.setClId(stClassList.getSelectClass().getClId());
		student.setStGrade(stClassList.getSelectClass().getClGrade());
		student.setStBynf(getBynf());
		studentService.update(student);
		List list = bsStudentService.findByKuid(student.getKuId());
		if (list.size() != 0) {
			BsStudent stu = (BsStudent) list.get(0);
			BsGpunit gpunit=(BsGpunit) gpunitService.get(BsGpunit.class, stu.getBuId());
			if(student.getStBynf()>gpunit.getGprocess().getBsGrade()){
				stu.setStNormal(Short.valueOf("4"));
			}else{
				stu.setStNormal(stNormal);
			}
			studentService.update(stu);
		}
		XyUserroleId xyuId = new XyUserroleId(getDefaultRole().getKrId(), editUser.getKuId());
		XyUserrole xyu = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyuId);
		if (xyu.getKdId().longValue() != selectDept.getKdId().longValue()) {
			xyu.setKdId(selectDept.getKdId());
			xyUserRoleService.update(xyu);
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "更新用户，id:" + editUser.getKuId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
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
			uBandIp.setValue(null);
			autoLogin.setDisabled(false);
			autoLogin.setChecked(false);
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(WkTRole defaultRole) {
		this.defaultRole = defaultRole;
	}
}
