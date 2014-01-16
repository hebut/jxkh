package org.iti.jxkh.selecttitle;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.service.TitleService;

public class JxTitleTreeModel  extends AbstractTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1761525226584453785L;
	
	private TitleService titleService;
	
	
	public JxTitleTreeModel(Object root,TitleService titleService) {
		super(root);
		this.titleService = titleService;
	}
	
	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) parent;
			WkTTitle tc = titleService.getChildTitle(t.getKtId()).get(index);
			return tc;
		} else if (parent instanceof List) {
			List<?> clist =  (List<?>) parent;
			return clist.get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) parent;
			return titleService.getChildTitle(t.getKtId()).size();
		} else if (parent instanceof List) {
			List<?> clist = (List<?>) parent;
			return clist.size();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof WkTTitle) {
			WkTTitle t = (WkTTitle) node;
			return titleService.getChildTitle(t.getKtId()).size() > 0 ? false : true;
		} else if (node instanceof List) {
			List<?> clist = (List<?>) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}

}
