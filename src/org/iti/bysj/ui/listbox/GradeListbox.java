package org.iti.bysj.ui.listbox;

import java.util.List;

import org.iti.xypt.service.XyClassService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class GradeListbox extends Listbox implements AfterCompose {
	List deptList;
	List glist;
	XyClassService xyClassService;
	Integer grade;

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Integer d = (Integer) data;
				item.setValue(d);
				if (d.intValue() == 0) {
					item.setLabel("«Î—°‘Ò");
				} else {
					item.setLabel(d + "");
				}
				if (getGrade() != null)
					if (d.intValue() == getGrade().intValue()) {
						item.setSelected(true);
					}
			}
		});
	}

	public void loadList() {
		glist = xyClassService.getGradeList(getDeptList());
		glist.add(0, new Integer(0));
		this.setModel(new ListModelList(glist));
	}
	public void gradelist() {
		glist = xyClassService.find("select distinct(yjsGrade) from Yjs ", new Object[]{});
		glist.add(0, new Integer(0));
		this.setModel(new ListModelList(glist));
	}
	

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}

	public Integer getSelectGrade() {
		if (this.getModel().getSize() == 0) {
			return null;
		} else if (this.getSelectedItem() == null) {
			// return getGrade()==null?(Integer) this.getModel().getElementAt(0):getGrade();
			if (getGrade() != null && glist.contains(getGrade())) {
				return getGrade();
			} else {
				return (Integer) this.getModel().getElementAt(0);
			}
		} else {
			return (Integer) this.getSelectedItem().getValue();
		}
	}
}
