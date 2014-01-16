package com.uniwin.framework.common.listbox;

/**
 * <li>角色列表组件，根据页面使用参数配置，可以是列表或者下拉列表
 * @author DaLei
 * @2010-3-16
 */
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

public class DeptRoleListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RoleService roleService;
	ListModelList tmodelList;
	// 根角色节点
	WkTRole rootRole;
	// 角色所属部门编号必须在此列表中
	List<Long> deptList;
	List<Long> rlist = new ArrayList<Long>();

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		deptList = (List<Long>) Sessions.getCurrent().getAttribute("deptList");
	}

	/**
	 * <li>功能描述：角色选择下拉框，同时选中arg角色。
	 * 
	 * @param arg
	 *            用来显示全部角色列表，同时选中某个角色或者角色分组 void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initRoleSelect(final Long arg) {
		tmodelList = new ListModelList();
		tmodelList.add(rootRole);
		List<WkTRole> rList = roleService.getChildRole(Long.parseLong("0"));
		for (int i = 0; i < rList.size(); i++) {
			WkTRole r = (WkTRole) rList.get(i);
			r.setDept(1);
			tmodelList.add(r);
			List<WkTRole> clist = roleService.getChildRole(r.getKrId(), deptList);
			for (int j = 0; j < clist.size(); j++) {
				WkTRole cr = (WkTRole) clist.get(j);
				cr.setDept(2);
				tmodelList.add(cr);
			}
		}
		this.setModel(tmodelList);
		this.setItemRenderer(new RoleSelectItemRenderer(arg));
	}

	class RoleSelectItemRenderer implements ListitemRenderer {
		Long selectRid;

		public RoleSelectItemRenderer(Long rid) {
			this.selectRid = rid;
		}

		public void render(Listitem item, Object data) throws Exception {
			WkTRole t = (WkTRole) data;
			item.setValue(t);
			int dep = t.getDept();
			String bla = "";
			while (dep > 0) {
				bla += "　";
				dep--;
			}
			if (selectRid != null && t.getKrId().intValue() == selectRid.intValue()) {
				item.setSelected(true);
			}
			item.setLabel(bla + t.getKrName());
		}
	}

	public WkTRole getRootRole() {
		return rootRole;
	}

	public void setRootRole(WkTRole rootRole) {
		if (rootRole == null) {
			rootRole = new WkTRole();
			rootRole.setKrId(0L);
			rootRole.setKrName("　");
			rootRole.setDept(0);
		}
		this.rootRole = rootRole;
	}

	public void setDeptList(List<Long> deptList) {
		this.deptList = deptList;
	}
	@SuppressWarnings("unchecked")
	public List<Long> getRidList() {
		if (rlist.size() == 0) {
			List<WkTRole> inlist = tmodelList.getInnerList();
			for (int i = 0; i < inlist.size(); i++) {
				WkTRole r = (WkTRole) inlist.get(i);
				rlist.add(r.getKrId());
			}
		}
		return rlist;
	}
}
