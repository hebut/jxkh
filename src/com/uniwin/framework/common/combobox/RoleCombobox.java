package com.uniwin.framework.common.combobox;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

public class RoleCombobox extends Combobox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RoleService roleService;
	ListModelList rmodelList;

	public void afterCompose() {
		Components.wireVariables(this, this);
		rmodelList = new ListModelList();
		addRoleListBoxItem(rmodelList, Long.parseLong("0"));
		this.setModel(rmodelList);
		this.setItemRenderer(new ComboitemRenderer() {
			public void render(Comboitem item, Object data) throws Exception {
				WkTRole r = (WkTRole) data;
				item.setValue(r);
				item.setLabel(r.getKrName());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void addRoleListBoxItem(ListModelList rml, Long pid) {
		List<WkTRole> roleList;
		if (pid.intValue() == 0) {
			roleList = roleService.getChildRole(pid);
		} else {
			roleList = roleService.getChildRole(pid, (List<Long>)Sessions.getCurrent().getAttribute("userDeptList"));
		}
		for (int i = 0; i < roleList.size(); i++) {
			WkTRole r = (WkTRole) roleList.get(i);
			if (r.getKrPid().intValue() != 0)
				rml.add(r);
			addRoleListBoxItem(rml, r.getKrId());
		}
	}
}
