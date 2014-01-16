package com.uniwin.framework.common.combobox;

import java.util.List;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;

import com.uniwin.framework.entity.WkTUser;

public class UserCombobox extends Combobox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<?> userList;
	ListModelList userListModel;

	public void afterCompose() {
		userListModel = new ListModelList();
		this.setModel(userListModel);
		this.setItemRenderer(new ComboitemRenderer() {
			public void render(Comboitem item, Object data) throws Exception {
				WkTUser u = (WkTUser) data;
				item.setValue(u);
				item.setLabel(u.getKuName().trim());
			}
		});
	}

	public void setUserList(List<?> userList) {
		this.userList = userList;
		userListModel.clear();
		userListModel.addAll(userList);
	}
}
