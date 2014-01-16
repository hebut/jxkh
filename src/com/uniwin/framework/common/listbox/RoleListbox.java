package com.uniwin.framework.common.listbox;

/**
 * <li>角色列表组件，根据页面使用参数配置，可以是列表或者下拉列表
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

public class RoleListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RoleService roleService;
	ListModelList tmodelList;
	String topRole;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * <li>功能描述：向角色列表中添加角色。
	 * 
	 * @param rml
	 *            角色列表
	 * @param pid
	 *            父角色编号
	 * @param dep
	 *            角色层次深度
	 * @param ro
	 *            页面中当前角色，如果空则显示全部角色，否则只显示一级角色 void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void addRoleListBoxItem(ListModelList rml, Long pid, int dep, WkTRole ro) {
		List<WkTRole> rList = roleService.getChildRole(pid);
		for (int i = 0; i < rList.size(); i++) {
			WkTRole r = (WkTRole) rList.get(i);
			r.setDept(dep);
			rml.add(r);
			if (ro == null)
				addRoleListBoxItem(rml, r.getKrId(), dep + 1, ro);
		}
	}

	/**
	 * <li>功能描述：初始化角色组选择下拉框。
	 * 
	 * @param arg
	 *            当前编辑角色，默认选择其父角色 void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initPRoleSelect(final WkTRole arg) {
		tmodelList = new ListModelList();
		if (getTopRole() != null) {
			WkTRole r = new WkTRole();
			r.setKrId(0L);
			r.setKrName(getTopRole());
			r.setDept(0);
			tmodelList.add(r);
		}
		addRoleListBoxItem(tmodelList, Long.parseLong("0"), 0, arg);
		this.setModel(tmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTRole t = (WkTRole) data;
				item.setValue(t);
				int dep = t.getDept();
				String bla = "";
				while (dep > 0) {
					bla += "　";
					dep--;
				}
				if (arg != null && t.getKrId().intValue() == arg.getKrPid().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + t.getKrName());
			}
		});
	}

	/**
	 * <li>功能描述：角色选择下拉框，同时选中arg角色组。
	 * 
	 * @param arg
	 *            不为空时用在新建角色的角色组，为空时用来显示全部角色列表 void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initRoleSelect(final WkTRole arg) {
		tmodelList = new ListModelList();
		if (getTopRole() != null) {
			WkTRole r = new WkTRole();
			r.setKrId(0L);
			r.setKrName(getTopRole());
			r.setDept(0);
			tmodelList.add(r);
		}
		addRoleListBoxItem(tmodelList, Long.parseLong("0"), 0, arg);
		this.setModel(tmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTRole t = (WkTRole) data;
				item.setValue(t);
				int dep = t.getDept();
				String bla = "";
				while (dep > 0) {
					bla += "　";
					dep--;
				}
				if (arg != null && t.getKrId().intValue() == arg.getKrId().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + t.getKrName());
			}
		});
	}

	public String getTopRole() {
		return topRole;
	}

	public void setTopRole(String topRole) {
		this.topRole = topRole;
	}
}
