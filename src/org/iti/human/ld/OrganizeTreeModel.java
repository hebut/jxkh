package org.iti.human.ld;

import java.util.List;
import org.iti.xypt.entity.XyUserrole;
import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

/**
 * 
 * @author DaLei
 * @version $Id: OrganizeTreeModel.java,v 1.1 2011/08/31 07:03:00 ljb Exp $
 */
public class OrganizeTreeModel extends AbstractTreeModel {
	DepartmentService departmentService;

	public OrganizeTreeModel(Object root, DepartmentService departmentService) {
		super(root);
		this.departmentService = departmentService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object arg0, int arg1) {
		if (arg0 instanceof XyUserrole) {
			XyUserrole urole = (XyUserrole) arg0;
			return departmentService.get(WkTDept.class, urole.getKdId());
		} else if (arg0 instanceof List) {
			List tlist = (List) arg0;
			return tlist.get(arg1);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object arg0) {
		if (arg0 instanceof XyUserrole) {
			XyUserrole urole = (XyUserrole) arg0;
			return 1;
		} else if (arg0 instanceof List) {
			List tlist = (List) arg0;
			return tlist.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object arg0) {
		if (arg0 instanceof XyUserrole) {
			XyUserrole urole = (XyUserrole) arg0;
			return false;
		} else if (arg0 instanceof List) {
			List tlist = (List) arg0;
			return tlist.size() == 0;
		}
		return true;
	}
}
