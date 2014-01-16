package org.iti.gh.szxkry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.entity.BsGproces;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class SelteaWindow extends Window implements AfterCompose {
	Panel userPanel;
	West westPanel;
	Tree westTree;
	Listbox leaderlist;
	Listheader gradeTwoLabel, gradeThrLabel;
	DepartmentService departmentService;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	XyUserrole xyUserRole;
	BsGproces gprocess;
	Textbox nameSearch, tnoSearch;
	/**
	 * 根节点组织编号
	 */
	Long rootKdId;
	Long xykdid;
	WkTRole role;
	public void setXykdid(Long xykdid) {
		this.xykdid = xykdid;
	}
	public void setRole(WkTRole role) {
		this.role = role;
	}
	public void setRootKdId(Long rootKdId) {
		this.rootKdId = rootKdId;
	}
	public void setTitle(String title) {
		super.setTitle(title);
		userPanel.setTitle("学科人员列表");
	}
	public void onClick$search() {
		if (westTree.getSelectedItem() == null) {
			throw new WrongValueException(westTree, "请选择要搜索的单位");
		}
		List dlist = new ArrayList();
		addDept(westTree.getSelectedItem(), dlist);
		List tlist = teacherService.findBydeplistNotInGhuserdept(dlist, nameSearch.getValue(), tnoSearch.getValue(),role.getKrId(),xykdid);
		leaderlist.setModel(new ListModelList(tlist));
	}

	public void initWindow() {
		WkTDept rootDept = (WkTDept) departmentService.get(WkTDept.class, rootKdId);
		gradeTwoLabel.setLabel(rootDept.getGradeName(WkTDept.GRADE_YUAN));
		gradeThrLabel.setLabel(rootDept.getGradeName(WkTDept.GRADE_XI));
		List rlist = new ArrayList();
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService,WkTDept.QUANBU));
	}
	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectUser() {
		List slist = new ArrayList();
		Set s = leaderlist.getSelectedItems();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			slist.add(item.getValue());
		}
		return slist;
	}

	public void initShow() {
		westTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) departmentService.get(WkTDept.class, role.getKdId());
				if (!department.getKdName().equals(dept.getKdName()))
					item.setOpen(false);
				// Treerow tr = new Treerow();
				// Treecell tc1 = new Treecell(dept.getKdName());
				// tr.appendChild(tc1);
				// item.appendChild(tr);
				// Treecell tc2 = new Treecell(xyUserRoleService.countNoBsTeacher(getRole().getKrId(), dept.getKdId(), buId) + "");
				// tr.appendChild(tc2);
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onClick$search();
			}
		});
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Teacher tea = (Teacher) data;
				item.setValue(data);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getXiDept(role.getKdId()));
				Listcell c5 = new Listcell(tea.getYuDept(role.getKdId()));
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
			}
		});
	}

	private void addDept(Treeitem item, List dlist) {
		dlist.add(item.getValue());
		if (item.getTreechildren() == null) {
			return;
		}
		List clist = item.getTreechildren().getChildren();
		for (int i = 0; i < clist.size(); i++) {
			Treeitem it = (Treeitem) clist.get(i);
			addDept(it, dlist);
		}
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}



}
