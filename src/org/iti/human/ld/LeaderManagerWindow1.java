package org.iti.human.ld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.human.ld.LeaderManagerWindow.DeptTreeModel;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.TeaSelectWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.api.Listheader;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;

public class LeaderManagerWindow1 extends HumanBaseWindow {

	Panel userPanel;
	West westPanel;
	Tree westTree;
	Button addUser;
	Listbox leaderlist;
	Listheader yuan, xi;
	DepartmentService departmentService;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	boolean isSelect = false;
	// 选择的部门
	WkTDept dept;
	DeptTreeModel deptTreeModel;
	Textbox nameSearch, tnoSearch;

	public void setTitle(String title) {
		westPanel.setTitle(title);
		userPanel.setTitle(title.substring(2) + "列表");
		addUser.setLabel("添加" + title.substring(2));
	}

	public void onClick$addUser() {
		final TeaSelectWindow win = (TeaSelectWindow) Executions.createComponents("/admin/common/teaSelect/index.zul", null, null);
		win.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(getRole().getKdId())));
		win.setNoRoleId(getRole().getKrId());
		win.setRootKdId(getXyUserRole().getDept().getKdSchid());
		win.setTitle("选择" + addUser.getLabel().substring(2));
		win.doHighlighted();
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				List slist = win.getSelectUser();
				for (int i = 0; i < slist.size(); i++) {
					Teacher t = (Teacher) slist.get(i);
					XyUserroleId xyrId = new XyUserroleId(getRole().getKrId(), t.getKuId());
					XyUserrole xyr = new XyUserrole(xyrId, getSelectDept().getKdId());
					xyUserRoleService.saveXyUserrole(xyr);
				}
				win.detach();
				loadTree();
				onClick$submit();
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
		List tlist = teacherService.findBydeplistAndrid(dlist, getRole().getKrId(), nameSearch.getValue(), tnoSearch.getValue());
		leaderlist.setModel(new ListModelList(tlist));
	}

	public void initWindow() {
		WkTDept rootDept = (WkTDept) departmentService.get(WkTDept.class, getXyUserRole().getKdId());
		List rlist = new ArrayList();
		Map childMap = new HashMap();
		yuan.setLabel(rootDept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel(rootDept.getGradeName(WkTDept.GRADE_XI));
		switch (getLeave()) {
		case 1:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
			}
			break;
		case 2:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
				childMap.put(rootDept, clist);
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {
					rlist.add(rootDept);
				}
			}
			break;
		case 3:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
				childMap.put(rootDept, clist);
				for (int i = 0; i < clist.size(); i++) {
					WkTDept d = (WkTDept) clist.get(i);
					childMap.put(d, departmentService.getChildDepartment(d.getKdId(), WkTDept.XUEKE));
				}
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {
					rlist.add(rootDept);
					List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.XUEKE);
					childMap.put(rootDept, clist);
				} else {
					rlist.add(rootDept);
				}
			}
			break;
		default:
			break;
		}
		deptTreeModel = new DeptTreeModel(rlist, childMap);
		loadTree();
	}

	private void loadTree() {
		westTree.setModel(deptTreeModel);
	}

	public void initShow() {
		addUser.setDisabled(true);
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
				// if (item.getTreechildren() == null) {
				// Treecell tc2 = new Treecell(xyUserRoleService.countByRidAndKdid(getRole().getKrId(), dept.getKdId()) + "");
				// tr.appendChild(tc2);
				// }
				if (getDept() != null && dept.getKdId().longValue() == getDept().getKdId().longValue()) {
					item.setSelected(true);
				}
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				Treeitem sitem = westTree.getSelectedItem();
				if (sitem.getTreechildren() == null || sitem.getTreechildren().getChildren().size() == 0) {
					addUser.setDisabled(false);
				} else {
					addUser.setDisabled(true);
				}
				setDept((WkTDept) sitem.getValue());
				onClick$submit();
			}
		});
		leaderlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem item, Object data) throws Exception {
				final Teacher tea = (Teacher) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getXiDept(getRole().getKdId()));
				Listcell c5 = new Listcell(tea.getYuDept(getRole().getKdId()));
				Listcell c6 = new Listcell(tea.getTitle().getTiName());
				Listcell c7 = new Listcell();
				XyUserroleId xyid = new XyUserroleId(getRole().getKrId(), tea.getKuId());
				List xylist = xyUserRoleService.find("from XyUserrole as xy where xy.id.kuId=? and xy.id.krId=?", new Object[] { tea.getKuId(), getRole().getKrId() });
				if (xylist.size() != 0) {
					XyUserrole xyuser = (XyUserrole) xylist.get(0);
					WkTDept dept = (WkTDept) teacherService.get(WkTDept.class, xyuser.getKdId());
					c7.setLabel(dept.getKdName());
				}
				Listcell c8 = new Listcell();
				InnerButton inb = new InnerButton("删除");
				inb.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("您确定删除吗", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							XyUserroleId xyrId = new XyUserroleId(getRole().getKrId(), tea.getKuId());
							XyUserrole xyr = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyrId);
							if (null != xyr) {
								xyUserRoleService.deleteXyUserrole(xyr);
								onClick$submit();
							}
							loadTree();
						}
					}
				});
				c8.appendChild(inb);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		});
	}

	private void addDept(Treeitem item, List dlist) {
		if (item.getTreechildren() == null) {
			dlist.add(item.getValue());
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

	class DeptTreeModel extends AbstractTreeModel {

		Map childMap;

		public DeptTreeModel(Object root, Map childMap) {
			super(root);
			this.childMap = childMap;
		}

		public Object getChild(Object parent, int index) {
			if (parent instanceof List) {
				return ((List) parent).get(index);
			}
			List clist = (List) childMap.get(parent);
			return clist.get(index);
		}

		public int getChildCount(Object parent) {
			if (parent instanceof List) {
				return ((List) parent).size();
			}
			List clist = (List) childMap.get(parent);
			return clist == null ? 0 : clist.size();
		}

		public boolean isLeaf(Object node) {
			List clist = (List) childMap.get(node);
			if (null == clist || clist.size() == 0)
				return true;
			return false;
		}
	}

	public WkTDept getDept() {
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
}
