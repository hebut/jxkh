package com.uniwin.framework.ui.dept;

/**
 * <li>组织结构的树的数据模型
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zul.AbstractTreeModel;

@SuppressWarnings("rawtypes")
public class ClassTreeModel extends AbstractTreeModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClassTreeModel(Object root) {
		super(root);
	}

	public Object getChild(Object parent, int index) {
		 if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		 if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// 判断某节点是否为叶子节点
	public boolean isLeaf(Object node) {
		 if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
