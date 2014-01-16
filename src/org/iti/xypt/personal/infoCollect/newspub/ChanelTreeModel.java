package org.iti.xypt.personal.infoCollect.newspub;
/**
 * <li>��Ŀ��ģ�ͣ�
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
//�ɸ���Ŀ�����Һ�����Ŀ����
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
//��ú��ӵ���Ŀ
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
//�жϵ�ǰ�ڵ��Ƿ�ΪҶ�ӽڵ�
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
