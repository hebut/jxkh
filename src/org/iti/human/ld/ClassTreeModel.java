package org.iti.human.ld;

/**
 * <li>组织结构的树的数据模型
 */
import java.util.List;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.XyClassService;
import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class ClassTreeModel extends AbstractTreeModel {
	XyClassService xyClassService;
	DepartmentService departmentService;
	Long kuId;

	public ClassTreeModel(Object root, DepartmentService departmentService, XyClassService xyClassService, Long kuId) {
		super(root);
		this.departmentService = departmentService;
		this.xyClassService = xyClassService;
		this.kuId = kuId;
	}

	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTDept) {
			WkTDept d = (WkTDept) parent;
			List dlist = departmentService.findDeptForFDY(d.getKdId(), kuId);
			if (dlist.size() != 0) {
				return dlist.get(index);
			} else {
				List clist = xyClassService.findClassForFDY(d.getKdId(), kuId);
				return clist.get(index);
			}
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTDept) {
			WkTDept d = (WkTDept) parent;
			List dlist = departmentService.findDeptForFDY(d.getKdId(), kuId);
			if (dlist.size() != 0)
				return dlist.size();
			else {
				List clist = xyClassService.findClassForFDY(d.getKdId(), kuId);
				return clist.size();
			}
		} else if (parent instanceof XyClass) {
			return 0;
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// 判断某节点是否为叶子节点
	public boolean isLeaf(Object node) {
		if (node instanceof WkTDept) {
			WkTDept d = (WkTDept) node;
			List dlist = departmentService.findDeptForFDY(d.getKdId(), kuId);
			if (dlist.size() != 0)
				return false;
			else {
				List clist = xyClassService.findClassForFDY(d.getKdId(), kuId);
				return clist.size() > 0 ? false : true;
			}
		} else if (node instanceof XyClass) {
			return true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}