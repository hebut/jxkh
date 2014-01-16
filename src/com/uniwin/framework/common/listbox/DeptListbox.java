package com.uniwin.framework.common.listbox;

/**
 * <li>��֯�����б����������ҳ��ʹ�ò������ã��������б���������б�
 * @author DaLei
 * @2010-3-16
 */
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class DeptListbox extends Listbox implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DepartmentService departmentService;
	ListModelList dmodelList;
	// �б��һ����ʾ����
	String topDept;
	// ������
	WkTDept rootDept;
	Long rootID;
	List<Long> dlist = new ArrayList<Long>();

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * <li>�����������ݹ������rml����Ӳ��Ŷ������в����dept�����Ӳ���
	 * 
	 * @param rml
	 *            �洢�б�
	 * @param pid
	 *            �������б�
	 * @param dep
	 *            ��ǰ���Ų�Σ�����������ʾ�˲���ǰ�ո���Ŀ
	 * @param dept
	 *            ����ʾ���� void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void addDeptListBoxItem(ListModelList rml, Long pid, int dep, Long dept) {
		List<WkTDept> tList;
		if (dept == null) {
			tList = departmentService.getChildDepartment(pid,WkTDept.QUANBU);
		} else {
			tList = departmentService.getChildDepartment(pid, dept);
		}
		for (int i = 0; i < tList.size(); i++) {
			WkTDept d = (WkTDept) tList.get(i);
			d.setDep(dep + 1);
			rml.add(d);
			addDeptListBoxItem(rml, d.getKdId(), dep + 1, dept);
		}
	}

	/**
	 * <li>������������ʼ���б��������Ӧ���ű༭�еĸ�����ѡ�񣬲�����arg���ţ�Ĭ��ѡ���丸���š�
	 * 
	 * @param arg
	 *            ��ǰ���ţ�ѡ���丸���� void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initPDeptSelect(final WkTDept arg) {
		dmodelList = new ListModelList();
		rootDept.setDep(0);
		dmodelList.add(rootDept);
		if (arg.getKdId().intValue() != rootID.intValue()) {
			addDeptListBoxItem(dmodelList, rootID, 0, arg.getKdId());
		}
		this.setModel(dmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				item.setValue(d);
				int dep = d.getDep();
				String bla = "";
				while (dep > 0) {
					bla += "��";
					dep--;
				}
				if (arg != null && d.getKdId().intValue() == arg.getKdPid().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + d.getKdName().trim());
			}
		});
	}

	public void initTeacherImportDept(Long kdid) {
		dmodelList = new ListModelList();
		addDeptListBoxItem(dmodelList, 0L, 0, kdid);
		this.setModel(dmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				item.setValue(d);
				int dep = d.getDep();
				String bla = "";
				while (dep > 0) {
					bla += "��";
					dep--;
				}
				item.setLabel(bla + d.getKdName().trim());
			}
		});
	}

	/**
	 * <li>������������ʼ�������б������������ʾȫ�����ţ�ͬʱѡ��arg���š�
	 * 
	 * @param arg
	 *            Ҫѡ��Ĳ��� void
	 * @author DaLei
	 * @2010-3-16
	 */
	public void initNewDeptSelect(final WkTDept arg) {
		dmodelList = new ListModelList();
		rootDept.setDep(0);
		dmodelList.add(rootDept);
		addDeptListBoxItem(dmodelList, rootDept.getKdId(), 0, null);
		this.setModel(dmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				item.setValue(d);
				int dep = d.getDep();
				String bla = "";
				while (dep > 0) {
					bla += "��";
					dep--;
				}
				if (arg != null && d.getKdId().intValue() == arg.getKdId().intValue()) {
					item.setSelected(true);
				}
				item.setLabel(bla + d.getKdName().trim());
			}
		});
	}

	public void setRootDept(WkTDept rootDept) {
		if (rootDept == null) {
			rootDept = new WkTDept();
			rootDept.setKdName("����");
			rootDept.setKdId(0L);
			rootDept.setKdLevel(0);
			rootDept.setKdType(WkTDept.DANWEI);
		}
		this.rootDept = rootDept;
	}

	public void setRootID(Long rootID) {
		this.rootID = rootID;
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public  List<Long> getDidList() {
		if (dlist.size() == 0) {
			synchronized(this){
				List<?> inlist = dmodelList.getInnerList();
				for (int i = 0; i < inlist.size(); i++) {
					WkTDept d = (WkTDept) inlist.get(i);
					dlist.add(d.getKdId());
				}
			}
		}
		return dlist;
	}

	public ListModelList getDmodelList() {
		return dmodelList;
	}

	public void setDmodelList(ListModelList dmodelList) {
		this.dmodelList = dmodelList;
	}
}
