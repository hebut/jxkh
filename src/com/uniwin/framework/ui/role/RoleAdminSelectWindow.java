package com.uniwin.framework.ui.role;

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
import com.uniwin.framework.service.RoleService;

public class RoleAdminSelectWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox roleSelect;
	String argAdmins;
	String[] args;
	Long schid;
	char garde;
	RoleService roleService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		roleSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTRole role = (WkTRole) data;
				item.setValue(data);
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(role.getKrName());
				if (argAdmins != null)
					for (int i = 0; i < args.length; i++) {
						if (args[i].equalsIgnoreCase(String.valueOf(role.getKrId()))) {
							item.setSelected(true);
						}
					}
				item.appendChild(c0);
				item.appendChild(c1);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<WkTRole> getSelectRoles() {
		Set<Listitem> rset = roleSelect.getSelectedItems();
		Iterator<Listitem> it = rset.iterator();
		List<WkTRole> rlist = new ArrayList<WkTRole>();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			rlist.add((WkTRole) item.getValue());
		}
		return rlist;
	}

	public void initWindow(Long schid, char grade) {
		this.schid = schid;
		this.garde = grade;
		List<WkTRole> rlist = roleService.findSelectAdmins(schid, grade);
		roleSelect.setModel(new ListModelList(rlist));
	}

	public void setArgAdmins(String argAdmins) {
		this.argAdmins = argAdmins;
		if (argAdmins != null)
			this.args = argAdmins.split(",");
	}

	public void onClick$submit() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		initWindow(schid, garde);
	}

	public void onClick$close() {
		this.detach();
	}
}
