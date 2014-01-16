package com.uniwin.framework.common.listbox;

/**
 * <li>��ɫ�б����������ҳ��ʹ�ò������ã��������б���������б�
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
	// ����ɫ�ڵ�
	WkTRole rootRole;
	// ��ɫ�������ű�ű����ڴ��б���
	List<Long> deptList;
	List<Long> rlist = new ArrayList<Long>();

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		deptList = (List<Long>) Sessions.getCurrent().getAttribute("deptList");
	}

	/**
	 * <li>������������ɫѡ��������ͬʱѡ��arg��ɫ��
	 * 
	 * @param arg
	 *            ������ʾȫ����ɫ�б�ͬʱѡ��ĳ����ɫ���߽�ɫ���� void
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
				bla += "��";
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
			rootRole.setKrName("��");
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
