package org.iti.human.ld;

import java.util.List;

import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.framework.entity.WkTRole;

public class HumanRoleTreeModel extends AbstractTreeModel {
	XyUserRoleService xyUserRoleService;

	public HumanRoleTreeModel(Object root, XyUserRoleService xyUserRoleService) {
		super(root);
		this.xyUserRoleService = xyUserRoleService;
	}

	public Object getChild(Object parent, int index) {
		if (parent instanceof XyUserrole) {
			XyUserrole xyu = (XyUserrole) parent;
			WkTRole role = xyu.getRole();
			String[] rids = role.getKrAdmins().split(",");
			return xyUserRoleService.get(WkTRole.class, Long.parseLong(rids[index]));
		} else if (parent instanceof List) {
			List rlist = (List) parent;
			return rlist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof XyUserrole) {
			XyUserrole xyu = (XyUserrole) parent;
			WkTRole role = xyu.getRole();
			if (role.getKrAdmins() == null || role.getKrAdmins().trim().length() == 0) {
				return 0;
			}
			String[] rids = role.getKrAdmins().split(",");
			return rids.length;
		} else if (parent instanceof List) {
			List rlist = (List) parent;
			return rlist.size();
		}
		return 0;
	}

	public boolean isLeaf(Object parent) {
		if (parent instanceof XyUserrole) {
			XyUserrole xyu = (XyUserrole) parent;
			WkTRole role = xyu.getRole();
			if (role.getKrAdmins() == null || role.getKrAdmins().trim().length() == 0) {
				return true;
			}
			String[] rids = role.getKrAdmins().split(",");
			return false;
		} else if (parent instanceof List) {
			List rlist = (List) parent;
			return rlist.size() > 0 ? false : true;
		}
		return true;
	}
}
