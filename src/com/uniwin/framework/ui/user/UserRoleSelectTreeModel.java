package com.uniwin.framework.ui.user;

/**
 * <li>�첽��ɫ������ģ��,��ɫ��ֻ��ʾdlist�����ڲ���;dlist�Ѿ��洢��session��
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

@SuppressWarnings("rawtypes")
public class UserRoleSelectTreeModel extends AbstractTreeModel {
	RoleService roleService;
	// �������ű���б�
	List<Long> dlist;

	public UserRoleSelectTreeModel(Object root, RoleService roleService, List<Long> dlist) {
		super(root);
		this.roleService = roleService;
		this.dlist = dlist;
	}

	private static final long serialVersionUID = 5023424836651625400L;

	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRoleOrDefault(t.getKrId(), dlist).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRoleOrDefault(t.getKrId(), dlist).size();
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
			return roleService.getChildRoleOrDefault(t.getKrId(), dlist).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
