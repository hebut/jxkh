package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.bysj.ui.listbox.XyClassList;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyNUrdId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.service.YjsService;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
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
 * <li>��������: ���ļ��Ĺ�������
 * 
 * @author DaLei
 * @date 2010-3-18
 */
public class YjsEditWindow extends Window implements AfterCompose {

	WkTRole defaultRole;
	WkTDept rootDept;
	// ҳ���и��������
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
	YjsService yjsService;
	XyUserRoleService xyUserRoleService;
	BsStudentService bsStudentService;
	DepartmentService departmentService;
	UserService userService;
	MLogService mlogService;
	WkTUser user;
	Yjs yjs;

	public Yjs getYjs() {
		return yjs;
	}

	public void setYjs(Yjs yjs) {
		this.yjs = yjs;
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
				if (yjs.getUser().getDept().getPdept().getKdId().longValue() == dep.getKdId().longValue()) {
					arg0.setSelected(true);
				}
			}
		});
		bumen.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(dept.getKdName());
				if (yjs.getUser().getDept().getKdId().longValue() == dept.getKdId().longValue()) {
					arg0.setSelected(true);
				}
			}
		});
		xueji.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				Short index = (Short) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(Student.NORMALS[index]);
				if (yjs.getYjsNormal().shortValue() == index.shortValue()) {
					arg0.setSelected(true);
				}
			}
		});
		bynf.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				Integer year = (Integer) arg1;
				arg0.setLabel(year + "��");
				if (yjs != null && yjs.getYjsBynf().intValue() == year.intValue()) {
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
		List xlist = departmentService.getChildDepartment(value.getKdId(), WkTDept.QUANBU);
		bumen.setModel(new ListModelList(xlist));
	}

	/**
	 * ��Ҫ��ǰ����defaultRole,student����
	 * 
	 */
	public void initWindow() {
		// ѧԺ�б�,ϵ�б�
		List ylist = new ArrayList(), xilist = new ArrayList();
		if (rootDept.getKdLevel().intValue() == 1) {
			ylist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
			xilist = departmentService.getChildDepartment(yjs.getUser().getDept().getPdept().getKdId(), WkTDept.QUANBU);
		} else if (rootDept.getKdLevel().intValue() == 2) {
			ylist.add(rootDept);
			xilist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
		} else {
			ylist.add(rootDept.getPdept());
			xilist.add(yjs.getUser().getDept());
		}
		yuan.setValue(rootDept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setValue(rootDept.getGradeName(WkTDept.GRADE_XI));
		xueyuan.setModel(new ListModelList(ylist));
		bumen.setModel(new ListModelList(xilist));
		stClassList.setGrade(yjs.getYjsGrade());
		stClassList.setXyClass(yjs.getXyClass());
		stGradeList.setGrade(yjs.getYjsGrade());
		List dlist = new ArrayList();
		dlist.add(yjs.getUser().getDept());
		stGradeList.setDeptList(dlist);
		stGradeList.loadList();
		stClassList.setDept(yjs.getUser().getDept());
		stClassList.loadList();
		loginName.setValue(yjs.getUser().getKuLid());
		trueName.setValue(yjs.getUser().getKuName());
		stid.setValue(yjs.getYjsId() + "");
		kuSex.setSelectedIndex(Integer.valueOf(yjs.getUser().getKuSex().trim()) - 1);
		uPass.setValue(EncodeUtil.decodeByDESStr(yjs.getUser().getKuPasswd().trim()));
		uRpass.setValue(EncodeUtil.decodeByDESStr(yjs.getUser().getKuPasswd().trim()));
		if (yjs.getUser().getKuStatus() != null) {
			uStatus.setSelectedIndex(Integer.valueOf(yjs.getUser().getKuStatus()));
		}
		lTimes.setValue(yjs.getUser().getKuLimit() == null ? 0 : yjs.getUser().getKuLimit());
		bangType.setSelectedIndex(Integer.valueOf(yjs.getUser().getKuBindtype().trim()));
		bangTypeHandle();
		if (yjs.getUser().getKuAutoenter().trim().equalsIgnoreCase("1")) {
			autoLogin.setChecked(true);
		} else {
			autoLogin.setChecked(false);
		}
		uinfo.setValue(yjs.getUser().getKuIntro());
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
		WkTUser editUser = yjs.getUser();
		if (uPass.getValue().trim().equalsIgnoreCase(uRpass.getValue().trim())) {
			editUser.setKuPasswd(EncodeUtil.encodeByDES(uPass.getValue()));
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
//		if (stClassList.getSelectClass() == null) {
//			throw new WrongValueException(stClassList, "��ѡ��༶������ް༶�뻻�꼶");
//		}
		userService.update(editUser);
		Short stNormal = 0;
		if (xueji.getSelectedItem() == null) {
			stNormal = 0;
		} else {
			stNormal = (Short) xueji.getSelectedItem().getValue();
		}
		yjs.setYjsNormal(stNormal);
//		yjs.setClId(stClassList.getSelectClass().getClId());
//		yjs.setYjsGrade(stClassList.getSelectClass().getClGrade());
		yjs.setYjsBynf(getBynf());
		yjsService.update(yjs);
		XyUserroleId xyuId = new XyUserroleId(getDefaultRole().getKrId(), editUser.getKuId());
		XyUserrole xyu = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyuId);
		if (xyu.getKdId().longValue() != selectDept.getKdId().longValue()) {
			xyu.setKdId(selectDept.getKdId());
			xyUserRoleService.update(xyu);
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�����û���id:" + editUser.getKuId(), user);
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

	public WkTRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(WkTRole defaultRole) {
		this.defaultRole = defaultRole;
	}
}
