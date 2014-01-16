package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTRole;

public class NoticeRoleSelectWindow extends Window implements AfterCompose {
	Listbox roleSelect;
	/**
	 * 能够选择的角色列表，传过来的参数
	 */
	List roleList;
	/**
	 * 已经选择的角色的编号列表和角色列表
	 */
	List ridList, selectedRoleList;

	public void initShow() {
		roleSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTRole role = (WkTRole) data;
				item.setValue(data);
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(role.getKrName());
				if (ridList.contains(role.getKrId())) {
					item.setSelected(true);
				}
				item.appendChild(c0);
				item.appendChild(c1);
			}
		});
	}

	public void initWindow(List roleList, List selectList) {
		ridList = new ArrayList();
		this.roleList = roleList;
		roleSelect.setModel(new ListModelList(roleList));
		selectedRoleList = selectList;
		if (selectList != null) {
			for (int i = 0; i < selectList.size(); i++) {
				WkTRole r = (WkTRole) selectList.get(i);
				ridList.add(r.getKrId());
			}
		}
	}

	public void onClick$submit() {
		List temlist = new ArrayList();
		Set rset = roleSelect.getSelectedItems();
		Iterator it = rset.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			temlist.add(item.getValue());
		}
		this.selectedRoleList = temlist;
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$reset() {
		initWindow(roleList, selectedRoleList);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
	}

	public List getSelectedRoleList() {
		return selectedRoleList;
	}

	public void setSelectedRoleList(List selectedRoleList) {
		this.selectedRoleList = selectedRoleList;
	}
}
