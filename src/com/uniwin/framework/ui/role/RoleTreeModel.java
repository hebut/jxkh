package com.uniwin.framework.ui.role;

/**
 * <li>�첽��ɫ������ģ��
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

@SuppressWarnings("rawtypes")
public class RoleTreeModel extends AbstractTreeModel {
	RoleService roleService;

	public RoleTreeModel(Object root, RoleService roleService) {
		super(root);
		this.roleService = roleService;
	}

	private static final long serialVersionUID = 5023424836651625400L;

	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRole(t.getKrId()).get(index);
		} else if (parent instanceof List) {
			
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRole(t.getKrId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// �ж�ĳ�ڵ��Ƿ�ΪҶ�ӽڵ�
	public boolean isLeaf(Object node) {
		if (node instanceof WkTRole) {
			WkTRole t = (WkTRole) node;
			return roleService.getChildRole(t.getKrId()).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
