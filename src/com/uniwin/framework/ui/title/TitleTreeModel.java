package com.uniwin.framework.ui.title;

/**
 * <li>标题树的数据模型组件
 */
import java.util.List;

import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

@SuppressWarnings("rawtypes")
public class TitleTreeModel extends AbstractTreeModel {
	TitleService titleService;

	public TitleTreeModel(Object root, TitleService titleService) {
		super(root);
		this.titleService = titleService;
	}

	private static final long serialVersionUID = 5023424836651625400L;

	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) parent;
			return titleService.getChildTitle(t.getKtId()).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) parent;
			return titleService.getChildTitle(t.getKtId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// 判断某节点是否为叶子节点
	public boolean isLeaf(Object node) {
		if (node instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) node;
			return titleService.getChildTitle(t.getKtId()).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
