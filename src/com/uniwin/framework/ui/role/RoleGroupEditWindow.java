package com.uniwin.framework.ui.role;

/**
 * <li>��ɫ��༭���ڽ���������Ӧ��ҳ��admin/system/role/roleGroup.zul
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
	 * ��ǰ�༭�Ľ�ɫ��
	 */
	private WkTRole role;
	Textbox roleGname, roleGdesc;
	/**
	 * ��ɫ�����ɾ����ť
	 */
	Button sortRole, deleteRole;
	RoleService roleService;
	// �û��������֯����б�
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
		this.setTitle("�༭��ɫ�飺" + role.getKrName());
		roleGname.setValue(role.getKrName());
		roleGdesc.setValue(role.getKrDesc());
	}

	public void onClick$submit() {
		role.setKrName(roleGname.getValue());
		role.setKrDesc(roleGdesc.getValue());
		roleService.update(role);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�༭��ɫ�飬id��" + role.getKrId(), user);
		loadTree();
	}

	public void onClick$reset() {
		initWindow(role);
	}

	/**
	 * <li>������������ɫ�������� void
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
	 * <li>����������ɾ����ɫ�鴦�� void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	public void onClick$deleteRole() {
		try {
			if (Messagebox.show("ȷ��ɾ���˽�ɫ������", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (roleService.getChildRole(getRole().getKrId()).size() > 0) {
					Messagebox.show("��ɫ��������ӽ�ɫ����ɾ��", "ɾ��ʧ��", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "ɾ����ɫ�飬id��" + role.getKrId(), user);
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
	 * <li>�������������ӽ�ɫ��ť���¼����� void
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
	 * <li>������������ɫ�������¼����� void
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
	 * <li>�������������ؽ�ɫ���� void
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
	 * <li>���������������ڵ�չ����Ĭ�ϴ�ĳ����ɫ�༭��
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
