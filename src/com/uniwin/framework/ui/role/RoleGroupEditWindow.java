package com.uniwin.framework.ui.role;

/**
 * <li>角色组编辑窗口解析器，对应的页面admin/system/role/roleGroup.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class RoleGroupEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前编辑的角色组
	 */
	private WkTRole role;
	Textbox roleGname, roleGdesc;
	/**
	 * 角色排序和删除按钮
	 */
	Button sortRole, deleteRole;
	RoleService roleService;
	// 用户管理的组织编号列表
	List<Long> userDeptList;
	WkTUser user;
	Tree roleTree;
	MLogService mlogService;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
	}

	public WkTRole getRole() {
		return role;
	}

	public void initWindow(WkTRole role) {
		this.role = role;
		this.setTitle("编辑角色组：" + role.getKrName());
		roleGname.setValue(role.getKrName());
		roleGdesc.setValue(role.getKrDesc());
	}

	public void onClick$submit() {
		role.setKrName(roleGname.getValue());
		role.setKrDesc(roleGdesc.getValue());
		roleService.update(role);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "编辑角色组，id：" + role.getKrId(), user);
		loadTree();
	}

	public void onClick$reset() {
		initWindow(role);
	}

	/**
	 * <li>功能描述：角色组排序处理 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	public void onClick$sortRole() {
		final RoleSortWindow w = (RoleSortWindow) Executions.createComponents("/admin/system/role/roleSort.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(getRole());
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				w.detach();
			}
		});
	}

	/**
	 * <li>功能描述：删除角色组处理。 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	public void onClick$deleteRole() {
		try {
			if (Messagebox.show("确定删除此角色分组吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (roleService.getChildRole(getRole().getKrId()).size() > 0) {
					Messagebox.show("角色分组包含子角色不能删除", "删除失败", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除角色组，id：" + role.getKrId(), user);
				roleService.delete(role);
				Treeitem edititem = (Treeitem) (roleTree.getSelectedItem().getNextSibling() == null ? roleTree.getSelectedItem().getPreviousSibling() : roleTree.getSelectedItem().getNextSibling());
				if (edititem == null) {
					this.role = null;
				} else {
					this.role = (WkTRole) edititem.getValue();
				}
				loadTree();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <li>功能描述：增加角色按钮的事件处理。 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	public void onClick$addRole() {
		final RoleNewWindow w = (RoleNewWindow) Executions.createComponents("/admin/system/role/roleNew.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(getRole());
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				w.detach();
			}
		});
	}

	/**
	 * <li>功能描述：角色组增加事件处理。 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	public void onClick$addRoleGroup() {
		final RoleGroupNewWindow w = (RoleGroupNewWindow) Executions.createComponents("/admin/system/role/roleGroupNew.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				w.detach();
			}
		});
	}

	/**
	 * <li>功能描述：加载角色树。 void
	 * 
	 * @author DaLei
	 */
	private void loadTree() {
		if (roleTree == null)
			return;
		List<WkTRole> tlist = roleService.getChildRole(Long.parseLong("0"));
		if (user.getKdId().intValue() == 0) {
			RoleTreeModel ttm = new RoleTreeModel(tlist, roleService);
			roleTree.setModel(ttm);
		} else {
			DeptRoleTreeModel ttm = new DeptRoleTreeModel(tlist, roleService, userDeptList);
			roleTree.setModel(ttm);
		}
		openTree(roleTree.getTreechildren(), this.role);
	}

	/**
	 * <li>功能描述：将树节点展开并默认打开某个角色编辑。
	 * 
	 * @param chi
	 * @param dept
	 *            void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	private void openTree(Treechildren chi, WkTRole role) {
		if (chi == null)
			return;
		List<Treeitem> tlist = chi.getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			item.setOpen(true);
			WkTRole r = (WkTRole) item.getValue();
			if (role != null)
				if (r.getKrId().intValue() == role.getKrId().intValue()) {
					item.setSelected(true);
					Events.postEvent(Events.ON_SELECT, roleTree, null);
					return;
				}
			openTree(item.getTreechildren(), role);
		}
	}
}
