package com.uniwin.framework.common.listbox;

/**
 * <li>��ɫ�б����������ҳ��ʹ�ò������ã��������б���������б�
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
	 * <li>�������������ɫ�б�����ӽ�ɫ��
	 * 
	 * @param rml
	 *            ��ɫ�б�
	 * @param pid
	 *            ����ɫ���
	 * @param dep
	 *            ��ɫ������
	 * @param ro
	 *            ҳ���е�ǰ��ɫ�����������ʾȫ����ɫ������ֻ��ʾһ����ɫ void
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
	 * <li>������������ʼ����ɫ��ѡ��������
	 * 
	 * @param arg
	 *            ��ǰ�༭��ɫ��Ĭ��ѡ���丸��ɫ void
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
					bla += "��";
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
	 * <li>������������ɫѡ��������ͬʱѡ��arg��ɫ�顣
	 * 
	 * @param arg
	 *            ��Ϊ��ʱ�����½���ɫ�Ľ�ɫ�飬Ϊ��ʱ������ʾȫ����ɫ�б� void
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
					bla += "��";
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
