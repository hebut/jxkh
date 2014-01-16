package com.uniwin.framework.ui.role;

/**
 * <li>��ɫ�༭����������Ӧҳ��Ϊadmin/system/role/role.zul
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.RoleService;

public class RoleEditWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ��ǰ�༭��ɫrole����ɫ��prole
	private WkTRole role, groupRole;
	// ��ɫ���Ƽ���ɫ����
	Textbox roleName, roleDesc;
	// �Ƿ�Ĭ�ϼ�����ѡ��
	Checkbox isPro, isShare;//,  isGZL;//isDd,
	// ��ɫ���ݷ��ʽӿ�
	RoleService roleService;
	// ��֯�ṹ���ݷ��ʽӿ�
	DepartmentService departmentService;
	// �ֱ��Ӧ���ӡ����򡢽�ɫ�û���ɾ����ɫ
	Button addRole, sortRole, roleUser, deleteRole, copyRole;
	// �û��������֯����б�
	List<Long> userDeptList;
	// ҳ����������
	Tree roleTree;
	WkTUser user;
	MLogService mlogService;
	/**
	 * ��������¼�
	 */
	Listbox  roleGradeSelect;//roleTypeSelect
//	Row rowAdmins;
	Label schName;//roleAdmins, roleAdminID, 
	Row xtRow, ptRow;//, bsRow;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
//		roleTypeSelect.addEventListener(Events.ON_SELECT, new EventListener() {
//
//			public void onEvent(Event arg0) throws Exception {
//				if (roleTypeSelect.getSelectedIndex() == 2 || roleTypeSelect.getSelectedIndex() == 4) {
//					rowAdmins.setVisible(true);
//				} else {
//					rowAdmins.setVisible(false);
//				}
//			}
//		});
	}

	/**
	 * <li>����������ҳ�汣�水ť�¼���������
	 * 
	 * @throws InterruptedException void
	 * @author DaLei
	 */
	public void onClick$submit() throws InterruptedException {
		// ��ɫ����
		role.setKrName(roleName.getValue());
		// ��ɫ����
		role.setKrDesc(roleDesc.getValue());
		// �Ƿ�Ĭ��
		if (isPro.isChecked()) {
			role.setKrDefault("1");
		} else {
			role.setKrDefault("0");
		}
		// �Ƿ���
		if (isShare.isChecked()) {
			role.setKrShare("1");
		} else {
			role.setKrShare("0");
		}
		/**
		 * �����¼ӣ��Ƿ񶽵�
		 */
//		if (isDd.isChecked()) {
//			role.setKrArgs(WkTRole.INDEX_ISDD, WkTRole.ISDD_YES);
//		} else {
//			role.setKrArgs(WkTRole.INDEX_ISDD, WkTRole.ISDD_NO);
//		}
		// ���ݹ������ж��Ƿ��ǻ�������Ա����
//		if (isGZL.isChecked()) {
//			role.setKrArgs(WkTRole.INDEX_GZL, WkTRole.GZL_YES);
//		} else {
//			role.setKrArgs(WkTRole.INDEX_GZL, WkTRole.GZL_NO);
//		}
		// ����ѡ��Ľ�ɫ��������Args��������λ
//		role.setKrArgs(WkTRole.INDEX_TYPE, getTypeSelect());
		// ����ѡ��Ľ�ɫ��������Args��������λ
		role.setKrArgs(WkTRole.INDEX_GRADE, getGradeSelect());
		// ���Թ���Ľ�ɫ
//		if (roleTypeSelect.getSelectedIndex() == 2 || roleTypeSelect.getSelectedIndex() == 4) {
//			role.setKrAdmins(roleAdminID.getValue());
//		} else {
//			role.setKrAdmins("");
//		}
		roleService.update(role);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "�༭��ɫ��id��" + role.getKrId(), user);
		Messagebox.show("����ɹ�!", "�༭��ɫ", Messagebox.OK, Messagebox.INFORMATION);
		loadTree();
	}

	/**
	 * �õ�ѡ��Ľ�ɫ����
	 * 
	 * @return ѡ���ɫ���͵�char����
	 */
	/*private char getTypeSelect() {
		char type = ' ';
		switch (roleTypeSelect.getSelectedIndex()) {
		case 0:
			type = WkTRole.TYPE_XS;
			break;
		case 1:
			type = WkTRole.TYPE_JS;
			break;
		case 2:
			type = WkTRole.TYPE_LD;
			break;
		case 3:
			type = WkTRole.TYPE_DD;
			break;
		case 4:
			type = WkTRole.TYPE_FDY;
			break;
		case 5:
			type = WkTRole.TYPE_XSGB;
			break;
		case 6:
			type = WkTRole.TYPE_YJS;
			break;
		case 7:
			type = WkTRole.TYPE_XK;
			break;
		}
		return type;
	}*/

	/**
	 * �õ�ѡ��Ľ�ɫ����
	 * 
	 * @return ѡ���ɫ�����char����
	 */
	private char getGradeSelect() {
		char grade = ' ';
		switch (roleGradeSelect.getSelectedIndex()) {
		case 0:
			grade = '0';
			break;
		case 1:
			grade = '1';
			break;
		case 2:
			grade = '2';
			break;
		case 3:
			grade = '3';
			break;
		}
		return grade;
	}

	public void onClick$reset() {
		initWindow(getRole());
	}

	/*public void onClick$editAdmins() {
		final RoleAdminSelectWindow rw = (RoleAdminSelectWindow) Executions.createComponents("/admin/system/role/roleAdminSelect.zul", null, null);
		rw.doHighlighted();
//		rw.setArgAdmins(roleAdminID.getValue());
		rw.setTitle("ѡ��" + getRole().getKrName() + "������û�");
		rw.initWindow(getRole().getKdId(), getGradeSelect());
		rw.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				List<WkTRole> rlist = rw.getSelectRoles();
				StringBuffer sb = new StringBuffer("");
				StringBuffer sb2 = new StringBuffer("");
				for (int i = 0; i < rlist.size(); i++) {
					WkTRole r = (WkTRole) rlist.get(i);
					sb.append(r.getKrId());
					sb2.append(r.getKrName());
					if (i < (rlist.size() - 1)) {
						sb.append(",");
						sb2.append(",");
					}
				}
//				roleAdminID.setValue(sb.toString());
//				roleAdmins.setValue(sb2.toString());
				rw.detach();
			}
		});
		roleTypeSelect.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				if (roleTypeSelect.getSelectedIndex() == 2 || roleTypeSelect.getSelectedIndex() == 4) {
					rowAdmins.setVisible(true);
				} else {
					rowAdmins.setVisible(false);
				}
			}
		});
	}*/

	public void onClick$addRole() {
		final RoleNewWindow w = (RoleNewWindow) Executions.createComponents("/admin/system/role/roleNew.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(groupRole);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				loadTree();
				w.detach();
			}
		});
	}

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

	public void onClick$roleUser() {
		RoleUserWindow w = (RoleUserWindow) Executions.createComponents("/admin/system/role/roleUsers.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(getRole());
	}

	public void onClick$deleteRole() {
		try {
			if (Messagebox.show("ȷ��ɾ���˽�ɫ��", "ȷ��", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (roleService.getChildRole(getRole().getKrId()).size() > 0) {
					Messagebox.show("��ɫ�����ӽ�ɫ����ɾ��", "ɾ��ʧ��", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "ɾ����ɫ��id��" + role.getKrId(), user);
				roleService.delete(role);
				this.role = (WkTRole) roleService.get(WkTRole.class, role.getKrPid());
				loadTree();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClick$copyRole() {
		RoleCopyWindow copyWindow = (RoleCopyWindow) Executions.createComponents("/admin/system/role/copyRole.zul", null, null);
		copyWindow.setEditRole(role);
		copyWindow.doHighlighted();
	}

	public WkTRole getRole() {
		return role;
	}

	/**
	 * <li>������������ʼ����ɫ�༭
	 * 
	 * @param role void
	 * @author DaLei
	 * @2010-3-17
	 */
	public void initWindow(WkTRole role) {
		this.role = role;
		this.setTitle("�༭��ɫ");
		if (getRole().getWkTDept() == null) {
			schName.setValue("ϵͳ����");
		} else {
			schName.setValue(getRole().getWkTDept().getKdName());
		}
		roleName.setValue(role.getKrName());
		roleDesc.setValue(role.getKrDesc());
		WkTRole r = (WkTRole) roleService.get(WkTRole.class, role.getKrPid());
		this.groupRole = r;
		if (role.getKrShare() != null && role.getKrShare().trim().equalsIgnoreCase("1")) {
			isShare.setChecked(true);
		} else {
			isShare.setChecked(false);
		}
		if (role.getKrDefault() != null && role.getKrDefault().trim().equalsIgnoreCase("1")) {
			isPro.setChecked(true);
		} else {
			isPro.setChecked(false);
		}
//		if (role.getKrArgs(WkTRole.INDEX_ISDD) == WkTRole.ISDD_YES) {
//			isDd.setChecked(true);
//		} else {
//			isDd.setChecked(false);
//		}
//		if (role.getKrArgs(WkTRole.INDEX_GZL) == WkTRole.GZL_YES) {
//			isGZL.setChecked(true);
//		} else {
//			isGZL.setChecked(false);
//		}
//		roleTypeSelect.setSelectedIndex(Integer.valueOf(String.valueOf(role.getKrArgs(WkTRole.INDEX_TYPE))));
		roleGradeSelect.setSelectedIndex(Integer.valueOf(String.valueOf(role.getKrArgs(WkTRole.INDEX_GRADE))));
		if (role.getKrAdmins() != null && role.getKrAdmins().length() > 0) {
			String[] adms = role.getKrAdmins().split(",");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < adms.length; i++) {
				Long rid = Long.parseLong(adms[i]);
				WkTRole ar = roleService.findByRid(rid);
				if (ar != null) {
					sb.append(ar.getKrName() + " ");
				}
			}
//			roleAdmins.setValue(sb.toString());
		} else {
//			roleAdmins.setValue("");
		}
//		roleAdminID.setValue(role.getKrAdmins());
//		if (role.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_LD || role.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_FDY) {
//			rowAdmins.setVisible(true);
//		} else {
//			rowAdmins.setVisible(false);
//		}
		if (getRole().getKdId().longValue() == 0L) {
			ptRow.setVisible(false);
			xtRow.setVisible(true);
//			bsRow.setVisible(false);
		} else {
			ptRow.setVisible(true);
			xtRow.setVisible(false);
//			bsRow.setVisible(true);
		}
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
		openTree(roleTree.getTreechildren(), role);
	}

	/**
	 * <li>���������������ڵ�չ����Ĭ�ϴ�ĳ����ɫ�༭��
	 * 
	 * @param chi
	 * @param dept void
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
			if (r.getKrId().intValue() == role.getKrId().intValue()) {
				item.setSelected(true);
				Events.postEvent(Events.ON_SELECT, roleTree, null);
				return;
			}
			openTree(item.getTreechildren(), role);
		}
	}
}
