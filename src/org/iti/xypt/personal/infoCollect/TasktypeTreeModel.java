package org.iti.xypt.personal.infoCollect;
/**
 * <li>վ����ģ�ͣ�
 */

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;
import org.zkoss.zul.AbstractTreeModel;




public class TasktypeTreeModel extends AbstractTreeModel {

	private static final long serialVersionUID = -9031880675642847049L;
	TaskService taskService;

	public TasktypeTreeModel(Object root, TaskService taskService) {
		super(root);
		this.taskService = taskService;
	}
	//�ɸ���Ŀ�����Һ�����Ŀ����
	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTTasktype) {
			WkTTasktype w = (WkTTasktype) parent;
			return taskService.getChildType(w.getKtaId()).get(index);
		} 
		else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}
//��ú��ӵ���Ŀ
	public int getChildCount(Object parent) {
		if (parent instanceof WkTTasktype) {
			WkTTasktype w = (WkTTasktype) parent;
			return taskService.getChildType(w.getKtaId()).size();
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}
//�жϵ�ǰ�ڵ��Ƿ�ΪҶ�ӽڵ�
	public boolean isLeaf(Object node) {
		if (node instanceof WkTTasktype) {
			WkTTasktype w = (WkTTasktype) node;
			return taskService.getChildType(w.getKtaId()).size() > 0 ? false
					: true;
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
