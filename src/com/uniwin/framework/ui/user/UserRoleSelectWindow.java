package com.uniwin.framework.ui.user;

/**
 * <li>�û���ɫѡ�񴰿�,��Ӧ��ҳ��Ϊadmin/system/user/userRoleSelect.zul
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
	 * ��ɫ��������ѡ��ģ��û����н�ɫĬ��ѡ��
	 */
	Tree tree;
	RoleService roleService;
	/**
	 * ��ǰ�༭���û�
	 */
	WkTUser editUser, user;
	ListModelList rlistModel;
	/**
	 * �û����н�ɫ�б�,�����ڵ���ʾʱ��Ĭ��ѡ��
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
		StringBuffer sb = new StringBuffer("�༭�û���ɫ���û�id:" + editUser.getKuId() + " ��ɫid:");
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
	 * <li>������������ʼ���û���ɫѡ��ҳ��
	 * 
	 * @param user
	 *            �༭���û��������û���ʼ���� void
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
