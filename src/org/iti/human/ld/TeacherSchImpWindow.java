package org.iti.human.ld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class TeacherSchImpWindow extends FrameworkWindow {
	Listbox teacherlist;
	Textbox nameSearch, tnoSearch;
	Button search;
	/**
	 * 需要导入学校的教师角色
	 */
	WkTRole importTeacherRole;
	DepartmentService departmentService;
	XyUserRoleService xyUserRoleService;
	TeacherService teacherService;
	Map<Long, Long> schRoleMap = new HashMap<Long, Long>();
	// 上级部门选择组件
	DeptListbox dselect;

	@Override
	public void initShow() {
		teacherlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Teacher tea = (Teacher) data;
				item.setValue(data);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getTitle().getTiName());
				Listcell c5 = new Listcell(tea.getXiDept(getSelectDept().getKdSchid()));
				Listcell c6 = new Listcell(tea.getYuDept(getSelectDept().getKdSchid()));
				// 添加删除教师功能
				Listcell c7 = new Listcell(tea.getSchDept(getSelectDept().getKdSchid()));
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
			}
		});
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectUser() {
		List slist = new ArrayList();
		Set s = teacherlist.getSelectedItems();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			slist.add(item.getValue());
		}
		return slist;
	}

	public void onClick$search() {
		List dlist = new ArrayList();
		dlist.add(getSelectDept());
		List tlist = teacherService.findBydeplistAndrid(dlist, getSelectSchTeacherRole(), getImportTeacherRole().getKrId(), nameSearch.getValue(), tnoSearch.getValue());
		teacherlist.setModel(new ListModelList(tlist));
	}

	/**
	 * 获得选择学校的教师角色编号
	 * 
	 * @return
	 */
	private Long getSelectSchTeacherRole() {
		return schRoleMap.get(getSelectDept().getKdSchid());
	}

	private WkTDept getSelectDept() {
		if (dselect.getSelectedItem() == null) {
			return (WkTDept) dselect.getModel().getElementAt(0);
		} else {
			return (WkTDept) dselect.getSelectedItem().getValue();
		}
	}

	@Override
	public void initWindow() {
		List schlist = departmentService.getChildDepartment(0L, getImportTeacherRole().getKdId());
		List schfilter = new ArrayList();
		for (int i = 0; i < schlist.size(); i++) {
			WkTDept schdept = (WkTDept) schlist.get(i);
			Long rid = xyUserRoleService.getRoleId("教师", schdept.getKdId());
			if (rid == null) {
				continue;
			}
			schfilter.add(schdept);
			schRoleMap.put(schdept.getKdId(), rid);
		}
		if (schfilter.size() == 0) {
			search.setDisabled(true);
			return;
		}
		dselect.initTeacherImportDept(getImportTeacherRole().getKdId());
	}

	public WkTRole getImportTeacherRole() {
		return importTeacherRole;
	}

	public void setImportTeacherRole(WkTRole importTeacherRole) {
		this.importTeacherRole = importTeacherRole;
	}
}
