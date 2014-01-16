package com.uniwin.framework.ui.role;

/**
 * <li>异步角色树数据模型,角色树只显示角色所属部门编号在dlist中的
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

@SuppressWarnings("rawtypes")
public class DeptRoleTreeModel extends AbstractTreeModel {
	RoleService roleService;
	List<Long> dlist;

	public DeptRoleTreeModel(Object root, RoleService roleService, List<Long> dlist) {
		super(root);
		this.roleService = roleService;
		this.dlist = dlist;
	}

	private static final long serialVersionUID = 5023424836651625400L;

	
	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRole(t.getKrId(), dlist).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTRole) {
			WkTRole t = (WkTRole) parent;
			return roleService.getChildRole(t.getKrId(), dlist).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// 判断某节点是否为叶子节点
	public boolean isLeaf(Object node) {
		if (node instanceof WkTRole) {
			WkTRole t = (WkTRole) node;
			return roleService.getChildRole(t.getKrId(), dlist).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
