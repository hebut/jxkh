package org.iti.xypt.personal.infoCollect.newsmanage;
/**
 * <li>վ����ģ�ͣ�
 */

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTUsersort;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;
import org.zkoss.zul.AbstractTreeModel;




public class UsersortreeModel extends AbstractTreeModel {

	private static final long serialVersionUID = -9031880675642847049L;
	WebsiteService websiteService;

	public UsersortreeModel(Object root, WebsiteService websiteService) {
		super(root);
		this.websiteService = websiteService;
	}
//�ɸ���Ŀ�����Һ�����Ŀ����
	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTUsersort) {
			WkTUsersort w = (WkTUsersort) parent;
			return websiteService.getChildUsersort(w.getKusId()).get(index);
		} 
		else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}
//��ú��ӵ���Ŀ
	public int getChildCount(Object parent) {
		if (parent instanceof WkTUsersort) {
			WkTUsersort w = (WkTUsersort) parent;
			return websiteService.getChildUsersort(w.getKusId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}
//�жϵ�ǰ�ڵ��Ƿ�ΪҶ�ӽڵ�
	public boolean isLeaf(Object node) {
		if (node instanceof WkTUsersort) {
			WkTUsersort w = (WkTUsersort) node;
			return websiteService.getChildUsersort(w.getKusId()).size() > 0 ? false
					: true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}

}
