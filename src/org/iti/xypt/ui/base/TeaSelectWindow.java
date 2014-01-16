/**
 * 
 */
package org.iti.xypt.ui.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

/**
 * @author DaLei
 * @version $Id: TeaSelectWindow.java,v 1.1 2011/08/31 07:03:04 ljb Exp $
 */
public class TeaSelectWindow extends Window implements AfterCompose {

	Panel userPanel;
	West westPanel;
	Tree westTree;
	Listbox leaderlist;
	DepartmentService departmentService;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	Textbox nameSearch, tnoSearch;
	XyUserrole xyUserRole;
	/**
	 * 列表中需要列出的角色用户的角色
	 */
	WkTRole role;
	/**
	 * 要求这里用户不能具有角色编号
	 */
	Long noRoleId;
	/**
	 * 根节点组织编号
	 */
	Long rootKdId;

	public void setTitle(String title) {
		super.setTitle(title);
		userPanel.setTitle(role.getKrName() + "列表");
	}

	public void initWindow() {
		WkTDept rootDept = (WkTDept) departmentService.get(WkTDept.class, rootKdId);
		List rlist = new ArrayList();
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU));
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

	public void onClick$reset() {
		nameSearch.setValue(null);
		tnoSearch.setValue(null);
	}

	public void initShow() {
		westTree.setTreeitemRenderer(new TreeitemRenderer() {

			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) departmentService.get(WkTDept.class, getRole().getKdId());
				if (!department.getKdName().equals(dept.getKdName()))
					item.setOpen(false);
				// Treerow tr = new Treerow();
				// Treecell tc1 = new Treecell(dept.getKdName());
				// tr.appendChild(tc1);
				// item.appendChild(tr);
				// Treecell tc2 = new Treecell(xyUserRoleService.countByRidAndKdid(getRole().getKrId(), dept.getKdId(), getNoRoleId()) + "");
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
				Listcell c4 = new Listcell(tea.getXiDept(getRole().getKdId()));
				Listcell c5 = new Listcell(tea.getYuDept(getRole().getKdId()));
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

	public void onClick$search() {
		if (westTree.getSelectedItem() == null) {
			throw new WrongValueException(westTree, "请选择要搜索的单位");
		}
		List dlist = new ArrayList();
		addDept(westTree.getSelectedItem(), dlist);
		List tlist = teacherService.findBydeplistAndrid(dlist, getRole().getKrId(), noRoleId, nameSearch.getValue(), tnoSearch.getValue());
		leaderlist.setModel(new ListModelList(tlist));
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	public XyUserrole getXyUserRole() {
		return xyUserRole;
	}

	public void setXyUserRole(XyUserrole xyUserRole) {
		this.xyUserRole = xyUserRole;
	}

	public Long getRootKdId() {
		return rootKdId;
	}

	public void setRootKdId(Long rootKdId) {
		this.rootKdId = rootKdId;
	}

	public WkTRole getRole() {
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	public Long getNoRoleId() {
		return noRoleId;
	}

	public void setNoRoleId(Long noRoleId) {
		this.noRoleId = noRoleId;
	}
}
