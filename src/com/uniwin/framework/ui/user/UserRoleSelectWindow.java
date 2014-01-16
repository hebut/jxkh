package com.uniwin.framework.ui.user;

/**
 * <li>用户角色选择窗口,对应的页面为admin/system/user/userRoleSelect.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;
import com.uniwin.framework.entity.WkTUseroleId;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

public class UserRoleSelectWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色树，带复选框的，用户已有角色默认选择
	 */
	Tree tree;
	RoleService roleService;
	/**
	 * 当前编辑的用户
	 */
	WkTUser editUser, user;
	ListModelList rlistModel;
	/**
	 * 用户已有角色列表,在树节点显示时候默认选择
	 */
	List<WkTRole> rlistOfUSer;
	DepartmentService departmentService;
	List<Long> userDeptList;
	UserService userService;
	MLogService mlogService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		tree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTRole r = (WkTRole) data;
				item.setValue(r);
				item.setLabel(r.getKrName());
				item.setOpen(true);
				if (r.getKrPid().intValue() == 0) {
					item.setCheckable(false);
				} else {
					for (int i = 0; i < rlistOfUSer.size(); i++) {
						WkTRole ar = (WkTRole) rlistOfUSer.get(i);
						if (ar.getKrId().intValue() == r.getKrId().intValue()) {
							item.setSelected(true);
						}
					}
				}
			}
		});
	}

	public WkTUser getEditUser() {
		return editUser;
	}

	@SuppressWarnings("unchecked")
	public void onClick$submit() {
		rlistOfUSer = roleService.getRoleOfUser(editUser.getKuId(), userDeptList);
		for (int i = 0; i < rlistOfUSer.size(); i++) {
			WkTRole r = (WkTRole) rlistOfUSer.get(i);
			WkTUseroleId urid = new WkTUseroleId(r.getKrId(), editUser.getKuId());
			WkTUserole ur = new WkTUserole(urid);
			userService.delete(ur);
		}
		Set<Treeitem> uset = tree.getSelectedItems();
		Iterator<Treeitem> it = uset.iterator();
		StringBuffer sb = new StringBuffer("编辑用户角色，用户id:" + editUser.getKuId() + " 角色id:");
		while (it.hasNext()) {
			Treeitem titem = (Treeitem) it.next();
			WkTRole r = (WkTRole) titem.getValue();
			if (r.getKrPid().intValue() != 0) {
				WkTUseroleId urid = new WkTUseroleId(r.getKrId(), editUser.getKuId());
				WkTUserole ur = new WkTUserole(urid);
				userService.save(ur);
				sb.append(r.getKrId() + ",");
			}
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
		this.detach();
	}

	public void onClick$reset() {
		initWindow(getEditUser());
	}

	public void onClick$close() {
		this.detach();
	}

	/**
	 * <li>功能描述：初始化用户角色选择页面
	 * 
	 * @param user
	 *            编辑的用户，根据用户初始化树 void
	 * @author DaLei
	 * @2010-3-17
	 */
	public void initWindow(WkTUser user) {
		this.editUser = user;
		this.setTitle(this.getTitle() + ":" + user.getKuName());
		rlistOfUSer = roleService.getRoleOfUser(user.getKuId());
		List<WkTRole> rolist = roleService.getChildRole(Long.parseLong("0"));
		tree.setModel(new UserRoleSelectTreeModel(rolist, roleService, userDeptList));
	}
}
