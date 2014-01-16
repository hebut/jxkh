package org.iti.human.ld;


import java.util.List;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTDept;

public class ClassEditWindow extends FrameworkWindow {
	Label deptOwner;
	Label gradeLabel;
	Textbox className, classSname;
	XyClassService xyClassService;
	/**
	 * 需要传的参数
	 */
	Integer defaultGrade;
	WkTDept dept;
	XyClass xyClass;
	StudentService studentService;
	Boolean updateStudent = false;

	public Boolean getUpdateStudent() {
		return updateStudent;
	}

	public void setUpdateStudent(Boolean updateStudent) {
		this.updateStudent = updateStudent;
	}

	public XyClass getXyClass() {
		return xyClass;
	}

	public void setXyClass(XyClass xyClass) {
		this.xyClass = xyClass;
	}

	public void initShow() {
	}

	public void initWindow() {
		deptOwner.setValue(dept.getKdName());
		gradeLabel.setValue(defaultGrade + "级");
		className.setRawValue(xyClass.getClName());
		classSname.setRawValue(xyClass.getClSname());
	}

	public void onClick$submit() {
		xyClass.setClName(className.getValue());
		xyClass.setClSname(classSname.getValue());
		List xylist = xyClassService.findByCnameAndKdid(className.getValue(), dept.getKdSchid());
		if (xylist.size() > 0) {
			XyClass xy = (XyClass) xylist.get(0);
			if (xy.getClId().longValue() != xyClass.getClId().longValue()) {
				throw new WrongValueException(className, "班级名称已经存在");
			}
		}
		xylist = xyClassService.findByScnameAndKdid(classSname.getValue(), dept.getKdSchid());
		if (xylist.size() > 0) {
			XyClass xy = (XyClass) xylist.get(0);
			if (xy.getClId().longValue() != xyClass.getClId().longValue()) {
				throw new WrongValueException(classSname, "班级简称已经存在");
			}
		}
		xyClassService.update(xyClass);
		if (updateStudent.booleanValue()) {
			studentService.updateStudentClass(xyClass);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow();
	}

	public void onClick$close() {
		this.detach();
	}

	public Integer getDefaultGrade() {
		return defaultGrade;
	}

	public void setDefaultGrade(Integer defaultGrade) {
		this.defaultGrade = defaultGrade;
	}

	public WkTDept getDept() {
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
}
