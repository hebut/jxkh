package org.iti.xypt.personal.infoCollect.newspub;
/**
 * <li>栏目树模型，
 */

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.service.ChanelService;
import org.zkoss.zul.AbstractTreeModel;




public class ChanelTreeModel extends AbstractTreeModel {

	private static final long serialVersionUID = -9031880675642847049L;
	ChanelService chanelService;

	public ChanelTreeModel(Object root, ChanelService chanelService) {
		super(root);
		this.chanelService = chanelService;
	}
//由父栏目对象找孩子栏目对象
	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTChanel) {
			WkTChanel c = (WkTChanel) parent;
			return chanelService.getChildChanel(c.getKcId()).get(index);
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}
//获得孩子的数目
	public int getChildCount(Object parent) {
		if (parent instanceof WkTChanel) {
			WkTChanel c = (WkTChanel) parent;
			return chanelService.getChildChanel(c.getKcId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}
//判断当前节点是否为叶子节点
	public boolean isLeaf(Object node) {
		if (node instanceof WkTChanel) {
			WkTChanel c = (WkTChanel) node;
			return chanelService.getChildChanel(c.getKcId()).size() > 0 ? false
					: true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}

}
