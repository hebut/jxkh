package com.uniwin.framework.ui.role;

/**
 * <li>角色管理中左侧角色树解析器，对应页面admin\system\role\index.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

public class RoleTreeComposer extends BaseLeftTreeComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tree roleTree;
	RoleService roleService;
	AbstractTreeModel ttm;
	// 角色编辑窗口
	RoleEditWindow rWindow;
	// 角色组编辑窗口
	RoleGroupEditWindow rgWindow;
	// 当前用户
	WkTUser user;
	DepartmentService departmentService;
	List<Long> userDeptList;
	Panel rolePanel;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		userDeptList = (List<Long>) session.getAttribute("userDeptList");
		roleTree.setTreeitemRenderer(new RoleItemRenderer());
		loadTree();
		openTree(roleTree.getTreechildren());
		roleTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Treeitem it = roleTree.getSelectedItem();
				openEditWindow(it);
			}
		});
		((Treeitem) roleTree.getTreechildren().getChildren().get(0)).setSelected(true);
		openEditWindow(roleTree.getSelectedItem());
	}

	/**
	 * <li>功能描述：加载角色树。 void
	 * 
	 * @author DaLei
	 */
	public void loadTree() {
		List<WkTRole> tlist = roleService.getChildRole(Long.parseLong("0"));
		ttm = new DeptRoleTreeModel(tlist, roleService, userDeptList);
		roleTree.setModel(ttm);
	}

	/**
	 * <li>功能描述：打开编辑角色窗口。
	 * 
	 * @param it
	 *            void
	 * @author DaLei
	 */
	private void openEditWindow(Treeitem it) {
		WkTRole r = (WkTRole) it.getValue();
		Executions.getCurrent().setAttribute("deptList", userDeptList);
		if (r.getKrPid().intValue() > 0) {
			Component c = creatTab("roleEdit", "角色管理", "/admin/system/role/role.zul", rolePanel);
			if (c != null) {
				rWindow = (RoleEditWindow) c;
			}
			rWindow.initWindow(r);
		} else {
			Component c = creatTab("roleEdit", "角色管理", "/admin/system/role/roleGroup.zul", rolePanel);
			if (c != null) {
				rgWindow = (RoleGroupEditWindow) c;
			}
			rgWindow.initWindow(r);
		}
	}

	/**
	 * <li>功能描述：将树节点展开。
	 * 
	 * @param chi
	 *            void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	private void openTree(Treechildren chi) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			item.setOpen(true);
			openTree(item.getTreechildren());
		}
	}
}
