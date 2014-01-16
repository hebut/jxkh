package org.iti.gh.ui.listbox;

import java.util.ArrayList;
import java.util.List;


import org.iti.gh.common.util.Department;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class TeacherListbox extends Listbox implements AfterCompose {
	DepartmentService departmentService;
	TeacherService teacherService;
	List teacherList = new ArrayList();
	Department jxglDep = new Department();
	UserService userService;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	public TeacherListbox() {
		this.departmentService = (DepartmentService) BeanFactory.getBean("departmentService");
		this.teacherService = (TeacherService) BeanFactory.getBean("teacherService");
	}

	public List initTeacherlist(Long kdid) {
		// 学院的所有专业
		// System.out.println("initTeacherlist_kdid=="+kdid);
		// System.out.println("Dep=="+jxglDep);
		Long collegeid = jxglDep.getCollegeByid(kdid);
		// System.out.println("initTeacherlist_collegeid=="+collegeid);
		WkTDept dept = departmentService.findByDid(collegeid);
		List deplist = departmentService.getChildDepartment(collegeid, WkTDept.DANWEI);// 学院及所有系列表
		deplist.add(departmentService.get(WkTDept.class, collegeid));
		Teacher tea = new Teacher();
		Long sid = dept.getKdPid();// 学校编号
		Long rid = tea.getRoleId(sid);// 学校角色编号
		List teacherlist = teacherService.findBydeplistAndrid(deplist, rid);
		List userlist = new ArrayList();
		if (teacherlist != null && teacherlist.size() > 0) {
			userlist = userService.findNameByTeacher(teacherlist);
			// userlist.addAll(ttee);
		}
		/*
		 * for (int i = 0; i < teacherlist.size(); i++) { WkTUser user = ((Teacher) teacherlist.get(i)).getUser(); userlist.add(user); }
		 */
		return userlist;
	}

	public void initTaskQueryTeacher(Long rid, Long selectkdid, Long kdid) {
		List deplist = new ArrayList();
		WkTDept wd = departmentService.findByDid(kdid);
		List clist = departmentService.getChildDepartment(kdid, WkTDept.QUANBU);
		if (clist != null && clist.size() > 0) {// 学院
			if (selectkdid.compareTo(Long.valueOf("0")) == 0) {// "--请选择--"
				deplist = departmentService.getChildDepartment(kdid, WkTDept.QUANBU);// 学院及所有系列表
				deplist.add(departmentService.get(WkTDept.class, kdid));
			} else {
				WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, selectkdid);
				deplist.add(dept);
			}
		} else {// 系
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, kdid);
			deplist.add(dept);
		}
		List teacherlist = teacherService.findBydeplistAndrid(deplist, rid);
		List userlist = new ArrayList();
		WkTUser user = new WkTUser();
		user.setKuName("--请选择--");
		user.setKuId(Long.valueOf("0"));
		userlist.add(user);
		if (teacherlist != null && teacherlist.size() > 0) {
			List ttee = userService.findNameByTeacher(teacherlist);
			userlist.addAll(ttee);
		}
		/*
		 * for (int i = 0; i < teacherlist.size(); i++) { WkTUser suser = ((Teacher) teacherlist.get(i)).getUser(); userlist.add(suser); }
		 */
		this.setModel(new ListModelList(userlist));
		this.setSelectedIndex(0);
	}

	public void initAsignTeacher(Long rid, Long selectkdid, Long kdid) {
		List deplist = new ArrayList();
		WkTDept wd = departmentService.findByDid(kdid);
		List clist = departmentService.getChildDepartment(kdid, WkTDept.DANWEI);
		if (clist != null && clist.size() > 0) {// 学院
			if (selectkdid.compareTo(Long.valueOf("0")) == 0) {// "--请选择--"
				deplist = departmentService.getChildDepartment(kdid, WkTDept.DANWEI);// 学院及所有系列表
				deplist.add(departmentService.get(WkTDept.class, kdid));
			} else {
				WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, selectkdid);
				deplist.add(dept);
			}
		} else {// 系
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, kdid);
			deplist.add(dept);
		}
		List teacherlist = teacherService.findBydeplistAndrid(deplist, rid);
		List userlist = new ArrayList();
		WkTUser user = new WkTUser();
		user.setKuName("--请选择--");
		user.setKuId(Long.valueOf("0"));
		userlist.add(user);
		if (teacherlist != null && teacherlist.size() > 0) {
			List ttee = userService.findNameByTeacher(teacherlist);
			userlist.addAll(ttee);
		}
		this.setModel(new ListModelList(userlist));
		this.setSelectedIndex(0);
	}
}
