/**
 * 
 */
package com.uniwin.framework.ui.role;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.AuthService;
import com.uniwin.framework.service.RoleService;

/**
 * 
 * @author DaLei
 * @version $Id: RoleCopyWindow.java,v 1.1 2011/08/31 07:03:05 ljb Exp $
 */
public class RoleCopyWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WkTUser user;
	Tree roleChooseTree;
	RoleService roleService;
	List<Long> userDeptList;
	AbstractTreeModel ttm;
	AuthService authService;
	WkTRole editRole;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		roleChooseTree.setTreeitemRenderer(new RoleItemRenderer());
		loadTree();
		openTree(roleChooseTree.getTreechildren());
	}

	public void loadTree() {
		List<WkTRole> tlist = roleService.getChildRole(Long.parseLong("0"));
		ttm = new DeptRoleTreeModel(tlist, roleService, userDeptList);
		roleChooseTree.setModel(ttm);
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

	@SuppressWarnings("unchecked")
	public void onClick$submit() throws InterruptedException {
		Set<Treeitem> srole = roleChooseTree.getSelectedItems();
		Iterator<Treeitem> it = srole.iterator();
		authService.deleteByRole(editRole);
		while (it.hasNext()) {
			Treeitem sitem = (Treeitem) it.next();
			WkTRole crole = (WkTRole) sitem.getValue();
			authService.copyAuthByRole(crole, editRole);
		}
		Messagebox.show("拷贝权限成功!", "拷贝角色权限:", Messagebox.OK, Messagebox.INFORMATION);
		this.detach();
	}

	public void onClick$reset() {
		loadTree();
	}

	public void onClick$close() {
		this.detach();
	}

	public WkTRole getEditRole() {
		return editRole;
	}

	public void setEditRole(WkTRole editRole) {
		this.editRole = editRole;
	}
}
