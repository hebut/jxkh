package org.iti.jxkh.dutyChange;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;
import com.uniwin.framework.ui.user.UserEditWindow;

public class DutyTreeComposer extends BaseLeftTreeComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tree tree;
	private Panel treePanel;
	
	DepartmentService departmentService;
	DepartmentTreeModel dtm;	
	/**
	 * 部门人员调动列表窗口，根据组织部门选择而变化列表
	 */
	DutyChangeWindow uWindow;
	WkTUser user;
	Treeitem selectItem;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				item.setValue(d);
				Treecell t1 = new Treecell(d.getKdName());
				Treerow row = new Treerow();
				row.appendChild(t1);
				item.appendChild(row);
			}
		});
		tree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Treeitem item = tree.getSelectedItem();
				if (item != null) {
					WkTDept d = (WkTDept) item.getValue();
					openListWindow(d);
				}
			}
		});
		loadTree();
		if (user.getKdId() == 0) {
			openTree(tree.getTreechildren(), (WkTDept) departmentService.get(WkTDept.class, 1L));
		} else {
			openTree(tree.getTreechildren(), (WkTDept) departmentService.get(WkTDept.class, user.getKdId()));
		}
	}
	
	private void openListWindow(WkTDept d) {
		Component c = creatTab("dutyChange", "人员调动管理", "/admin/jxkh/dutyChange/dutyChange.zul", treePanel);
		if (c != null) {
			uWindow = (DutyChangeWindow) c;
		}
		uWindow.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem item = tree.getSelectedItem();
				WkTDept d = (WkTDept) item.getValue();
				loadTree();
				openTree(tree.getTreechildren(), d);
			}
		});
		uWindow.initWindow(d);
	}
	
	/**
	 * <li>功能描述：重新加载树。 void
	 * 
	 * @author DaLei
	 * @date 2010-3-29 11:15:58
	 */
	public void loadTree() {
		List<WkTDept> rlist;
		rlist = departmentService.getChildDepartment(Long.parseLong("0"), WkTDept.QUANBU);
//		if (user.getKdId() == 0) {
//			rlist = departmentService.getChildDepartment(Long.parseLong("0"), WkTDept.QUANBU);
//		} else {
//			rlist = new ArrayList<WkTDept>();
//			rlist.add((WkTDept) departmentService.get(WkTDept.class, user.getKdId()));
//		}
		dtm = new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU);
		tree.setModel(dtm);
	}
	
	/**
	 * <li>功能描述：将树节点递归展开。
	 * 
	 * @param chi 树节点下级孩子节点 void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	public void openTree(Treechildren chi) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			item.setOpen(true);
			openTree(item.getTreechildren());
		}
	}

	/**
	 * <li>功能描述：将树节点展开并默认打开某个部门用户。
	 * 
	 * @param chi
	 * @param dept void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	private void openTree(Treechildren chi, WkTDept dept) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			WkTDept d = (WkTDept) item.getValue();
			if (d.getKdId().intValue() == dept.getKdId().intValue()) {
				tree.setSelectedItem(item);
				if (uWindow == null) {
					openListWindow(d);
				} else {
					uWindow.initWindow(d);
				}
			}
			if (d.getKdLevel() == WkTDept.GRADE_SCH.intValue())
				item.setOpen(true);
			else
				item.setOpen(false);
			openTree(item.getTreechildren(), dept);
		}
	}

}
