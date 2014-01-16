package org.iti.xypt.personal.notice;

import java.util.List;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.MessageService;
import org.zkoss.zul.AbstractTreeModel;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;

public class NoticeTargetTreeModel extends AbstractTreeModel {
	MessageService messageService;
	Long kdid;

	public NoticeTargetTreeModel(Object root, MessageService messageService, Long kdid) {
		super(root);
		this.kdid = kdid;
	}

	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isLeaf(Object data) {
		if (data instanceof List) {
			return false;
		} else if (data instanceof WkTRole) {
			WkTRole r = (WkTRole) data;
			return false;
		} else if (data instanceof WkTDept) {
			WkTDept d = (WkTDept) data;
		} else if (data instanceof Integer) {
		} else if (data instanceof XyClass) {
			XyClass cl = (XyClass) data;
		} else {
			//System.out.println("nonnonooo");
		}
		return false;
	}
}
