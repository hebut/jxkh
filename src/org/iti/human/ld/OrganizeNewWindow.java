package org.iti.human.ld;

import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class OrganizeNewWindow extends FrameworkWindow {

	WkTDept parentDept;
	/**
	 * 下面是编辑框中的属性
	 */
	Textbox dname, ddesc, dnumber;
	DepartmentService departmentService;
	Radio bumen, danwei, xueke;
	Label pdeptLabel;

	public void initShow() {
	}

	public void initWindow() {
		pdeptLabel.setValue(parentDept.getKdName());
		if (parentDept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			bumen.setChecked(true);
			danwei.setDisabled(true);
			xueke.setDisabled(true);
		} else {
			danwei.setDisabled(false);
			xueke.setDisabled(false);
		}
	}

	public void onClick$submit() {
		WkTDept newDept = new WkTDept();
		newDept.setKdName(dname.getValue());
		newDept.setKdDesc(ddesc.getValue());
		WkTDept numDept = departmentService.findByKdnumberAndKdSchid(dnumber.getValue().trim(), parentDept.getKdSchid());
		if (numDept != null) {
			throw new WrongValueException(dnumber, "编号已经存在,与" + numDept.getKdName() + "相同");
		}
		newDept.setKdNumber(dnumber.getValue());
		newDept.setKdPid(parentDept.getKdId());
		newDept.setKdOrder(100L);
		newDept.setKdLevel(parentDept.getKdLevel() + 1);
		newDept.setKdSchid(parentDept.getKdSchid());
		if (bumen.isChecked()) {
			newDept.setKdType(WkTDept.BUMEN);
		} else if (danwei.isChecked()) {
			newDept.setKdType(WkTDept.DANWEI);
		} else {
			newDept.setKdType(WkTDept.XUEKE);
		}
		departmentService.save(newDept);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTDept getParentDept() {
		return parentDept;
	}

	public void setParentDept(WkTDept parentDept) {
		this.parentDept = parentDept;
	}

	public void onClick$reset() {
		initWindow();
		dname.setRawValue(null);
		ddesc.setRawValue(null);
		dnumber.setRawValue(null);
	}
}
