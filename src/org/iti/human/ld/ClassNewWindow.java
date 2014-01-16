package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTDept;

public class ClassNewWindow extends FrameworkWindow {
	Label deptOwner;
	Listbox gradeSelect;
	Textbox className, classSname;
	XyClassService xyClassService;
	/**
	 * 需要传的参数
	 */
	Integer defaultGrade;
	WkTDept dept;

	public void initShow() {
		gradeSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Integer grade = (Integer) data;
				item.setValue(data);
				item.setLabel(grade + "级");
				if (defaultGrade != null && grade.intValue() == defaultGrade.intValue()) {
					item.setSelected(true);
				}
			}
		});
	}

	@Override
	public void initWindow() {
		Calendar ca = Calendar.getInstance();
		List ylist = new ArrayList();
		int year = ca.get(Calendar.YEAR);
		for (int i = year - 4; i < year + 5; i++) {
			ylist.add(new Integer(i));
		}
		gradeSelect.setModel(new ListModelList(ylist));
		deptOwner.setValue(dept.getKdName());
	}

	public Integer getSelectGrade() {
		if (gradeSelect.getSelectedItem() == null) {
			return (Integer) gradeSelect.getModel().getElementAt(0);
		} else {
			return (Integer) gradeSelect.getSelectedItem().getValue();
		}
	}

	public void onClick$submit() {
		XyClass xyClass = new XyClass();
		xyClass.setClGrade(getSelectGrade());
		xyClass.setKdId(dept.getKdId());
		xyClass.setClName(className.getValue());
		xyClass.setClSname(classSname.getValue());
		if (xyClassService.findByCnameAndKdid(className.getValue(), dept.getKdSchid()).size() > 0) {
			throw new WrongValueException(className, "班级名称已经存在");
		}
		if (xyClassService.findByScnameAndKdid(classSname.getValue(), dept.getKdSchid()).size() > 0) {
			throw new WrongValueException(classSname, "班级简称已经存在");
		}
		xyClassService.save(xyClass);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		className.setRawValue(null);
		classSname.setRawValue(null);
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
