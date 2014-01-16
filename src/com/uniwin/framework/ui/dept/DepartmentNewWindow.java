package com.uniwin.framework.ui.dept;

/**
 * <li>部门新建窗口,对应页面为admin/system/organize/deptNew.zul
 * @author DaLei
 * @2010-3-17
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;

public class DepartmentNewWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	WkTDept pDept;

	Textbox dname, ddesc, dnumber;

	Radio bumen, danwei, work, manage;

	DepartmentService departmentService;

	DeptListbox pselect;

	WkTUser user;

	MLogService mlogService;

	// 毕设新来属性
	Textbox gradeOne, gradeTwo, gradeThr;

	Row gradeNameRow, descRow;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	// 根据父部门控制 单位和部门 单选框
	public void onSelect$pselect() {
		WkTDept sd = (WkTDept) pselect.getSelectedItem().getValue();
		if (sd.getKdType().equalsIgnoreCase(WkTDept.BUMEN)) {
			bumen.setChecked(true);
			danwei.setDisabled(true);
		} else {
			danwei.setDisabled(false);
		}
		if (sd.getKdLevel().intValue() == 0) {
			descRow.setVisible(false);
			gradeNameRow.setVisible(true);
		} else {
			descRow.setVisible(true);
			gradeNameRow.setVisible(false);
		}
	}

	public void initWindow(WkTDept dept) {
		pDept = dept;
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, user.getKdId());
		pselect.setRootDept(pdept);
		pselect.setRootID(user.getKdId());
		pselect.initNewDeptSelect(dept);
		if (dept.getKdType().equalsIgnoreCase(WkTDept.BUMEN)) {
			bumen.setChecked(true);
			danwei.setDisabled(true);
		} else {
			danwei.setDisabled(false);
		}
		if (pDept.getKdLevel().intValue() == 0) {
			descRow.setVisible(false);
			gradeNameRow.setVisible(true);
		} else {
			descRow.setVisible(true);
			gradeNameRow.setVisible(false);
		}
	}

	public WkTDept getPDept() {
		return pDept;
	}

	public void onClick$submit() {
		WkTDept newDept = new WkTDept();
		newDept.setKdName(dname.getValue());
		WkTDept pde = (WkTDept) pselect.getSelectedItem().getValue();
		newDept.setKdPid(pde.getKdId());
		//newDept.setKdId(pde.getKdId() + 300);
		newDept.setKdLevel(pde.getKdLevel() + 1);
		newDept.setKdOrder(100L);
		newDept.setKdState(WkTDept.USE_YES);
		newDept.setKdNumber(dnumber.getValue());
		if (bumen.isChecked()) {
			newDept.setKdType(WkTDept.BUMEN);
		} else if (danwei.isChecked()) {
			newDept.setKdType(WkTDept.DANWEI);
		} else {
			newDept.setKdType(WkTDept.XUEKE);
		}
		if (work.isChecked()) {
			newDept.setKdClassify(WkTDept.WORK);
		} else if (manage.isChecked()) {
			newDept.setKdClassify(WkTDept.MANAGE);
		}
		WkTRole groupRole = null;
		if (pde.getKdLevel().longValue() == 0L) {
			if (gradeThr.getValue() == null || gradeThr.getValue().length() == 0) {
				throw new WrongValueException(gradeThr, "不能为空");
			} else if (gradeTwo.getValue() == null || gradeTwo.getValue().length() == 0) {
				throw new WrongValueException(gradeTwo, "不能为空");
			} else if (gradeOne.getValue() == null || gradeOne.getValue().length() == 0) {
				throw new WrongValueException(gradeOne, "不能为空");
			}
			String desc = "," + gradeOne.getValue() + "," + gradeTwo.getValue() + "," + gradeThr.getValue();
			newDept.setKdDesc(desc);
			newDept.setKdSchid(0L);
			groupRole = new WkTRole();
			groupRole.setKrDefault("0");
			groupRole.setKrName(newDept.getKdName());
			groupRole.setKrShare("0");
			groupRole.setKrPid(0L);
		} else {
			WkTDept numDept = departmentService.findByKdnumberAndKdSchid(dnumber.getValue().trim(), pde.getKdSchid());
			if (numDept != null) {
				throw new WrongValueException(dnumber, "编号已经存在,与" + numDept.getKdName() + "相同");
			}
			newDept.setKdDesc(ddesc.getValue());
			newDept.setKdSchid(pde.getKdSchid());
		}
		departmentService.save(newDept);
		if (newDept.getKdSchid().longValue() == 0L) {
			newDept.setKdSchid(newDept.getKdId());
			departmentService.update(newDept);
		}
		if (groupRole != null) {
			groupRole.setKdId(newDept.getKdId());
			departmentService.save(groupRole);
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "新建部门，id：" + newDept.getKdId(), user);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow(getPDept());
		dname.setRawValue(null);
		ddesc.setRawValue(null);
	}

	public void onClick$close() {
		this.detach();
	}
}
