package org.iti.feedback.ui.usb.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.base.LeaderBysjWindow;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TeaSelectWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Listheader;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class TeacherWindow1 extends Window implements AfterCompose {

	Panel userPanel;
	West westPanel;
	Tree westTree;
	Listbox leaderlist, deptList;
	Listheader yuan, xi;
	DepartmentService departmentService;
	TeacherService teacherService;
	RoleService roleService;
	UserService userService;
	WkTDept selectDept;
	boolean isSelect = false;
	WkTDept rootDept;
	Textbox nameSearch, tnoSearch;
	WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	Long KdId, Role;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initWindow();
	}

	public void initWindow() {
		List deptlist = departmentService.findDeptByKdidAndType(0L, "1");
		deptList.setModel(new ListModelList(deptlist));
		deptList.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(dept.getKdId());
			}
		});
		deptList.getItemAtIndex(0).setSelected(true);
		WkTDept department = (WkTDept) deptlist.get(0);
		KdId = department.getKdId();
		List rolelist = roleService.findByKdidAndKrname(KdId, "教师");
		if (rolelist.size() != 0) {
			Role = ((WkTRole) rolelist.get(0)).getKrId();
		}
		WkTDept dept = (WkTDept) userService.get(WkTDept.class, KdId);
		yuan.setLabel(dept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel(dept.getGradeName(WkTDept.GRADE_XI));
		loadTree();
		leaderlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem item, Object data) throws Exception {
				final Teacher tea = (Teacher) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getXiDept(KdId));
				Listcell c5 = new Listcell(tea.getYuDept(KdId));
				String psw = EncodeUtil.decodeByDESStr(tea.getUser().getKuPasswd().trim());
				Listcell c6 = new Listcell(psw);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
			}
		});
	}

	public void onClick$reset() {
		nameSearch.setValue(null);
		tnoSearch.setValue(null);
	}

	public void onClick$submit() {
		if (westTree.getSelectedItem() == null) {
			throw new WrongValueException(westTree, "请选择要搜索的单位");
		}
		List dlist = new ArrayList();
		addDept(westTree.getSelectedItem(), dlist);
		List tlist = teacherService.findBydeplistAndrid(dlist, Role, nameSearch.getValue(), tnoSearch.getValue());
		leaderlist.setModel(new ListModelList(tlist));
	}

	private void loadTree() {
		rootDept = (WkTDept) departmentService.get(WkTDept.class, KdId);
		List rlist = new ArrayList();
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU));
		westTree.setTreeitemRenderer(new TreeitemRenderer() {

			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) userService.get(WkTDept.class, KdId);
				if (!department.getKdName().equals(dept.getKdName()))
					item.setOpen(false);
				if (selectDept != null && selectDept.getKdId().longValue() == dept.getKdId().longValue()) {
					item.setSelected(true);
				}
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				selectDept = (WkTDept) westTree.getSelectedItem().getValue();
				onClick$submit();
			}
		});
	}

	public void onSelect$deptList() {
		KdId = Long.parseLong(deptList.getSelectedItem().getValue().toString());
		List rolelist = roleService.findByKdidAndKrname(KdId, "教师");
		if (rolelist.size() != 0) {
			WkTRole role = (WkTRole) rolelist.get(0);
			Role = role.getKrId();
		}
		loadTree();
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

	private WkTDept getSelectDept() {
		return (WkTDept) westTree.getSelectedItem().getValue();
	}

	public void setSelectDept(WkTDept selectDept) {
		this.selectDept = selectDept;
	}
}
