package org.iti.jxgl.ui.listbox;

/**
 * <li>������������λ�б�
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
	DepartmentService departmentService;
	ListModelList dmodelList;
	List dlist = new ArrayList();

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * <li>������������ʼ�����ε�λ�б�
	 */
	public void initClDeplist(final WkTDept d) {
		final WkTDept de = new WkTDept();
		de.setKdName("--��ѡ��--");
		de.setKdId(0L);
		dlist.add(de);
		dlist.addAll(departmentService.findKkDep());
		dmodelList = new ListModelList(dlist);
		this.setModel(dmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				if (d != null && dept.getKdId().intValue() == d.getKdId().intValue()) {
					arg0.setSelected(true);
				}
				arg0.setLabel(dept.getKdName());
			}
		});
	}

	public void initdeplist(final Long kdid) {
		// ѧԺ������רҵ
		List dlist = new ArrayList();
		// WkTDept wd=new WkTDept();
		// wd.setKdName("--��ѡ��--");
		// dlist.add(wd);
		List clist = departmentService.getChildDepartment(kdid, WkTDept.DANWEI);
		if (clist != null && clist.size() > 0) {
			dlist.addAll(clist);
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, kdid);
			clist = departmentService.getChildDepartment(dept.getKdPid(), WkTDept.DANWEI);
			dlist.addAll(clist);
		}
		this.setModel(new ListModelList(dlist));
		if (this.getModel().getSize() > 0) {
			this.setSelectedIndex(0);
		}
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dep = (WkTDept) arg1;
				arg0.setValue(dep);
				if (dep.getKdId().intValue() == kdid.intValue()) {
					arg0.setSelected(true);
				}
				arg0.setLabel(dep.getKdName());
			}
		});
	}

	// ��ʼ��ѧԺ�б� ����
	public void initCollagelDeplist(final Long sid, final WkTDept d) {
		dlist.addAll(departmentService.getChildDepartment(sid, WkTDept.DANWEI));
		dmodelList = new ListModelList(dlist);
		this.setModel(dmodelList);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setValue(dept);
				if (d != null && dept.getKdId().intValue() == d.getKdId().intValue()) {
					arg0.setSelected(true);
				}
				arg0.setLabel(dept.getKdName());
			}
		});
	}

	public void initdeplistxy(Long kdid) {
		// ѧԺ������רҵ author ����
		List dlist = new ArrayList();
		WkTDept wd = new WkTDept();
		wd.setKdId(Long.valueOf("0"));
		wd.setKdName("--��ѡ��--");
		dlist.add(wd);
		List clist = departmentService.getChildDepartment(kdid, WkTDept.DANWEI);
		if (clist != null && clist.size() > 0) {
			dlist.addAll(clist);
		} else {
			WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, kdid);
			clist = departmentService.getChildDepartment(dept.getKdPid(), WkTDept.DANWEI);
			dlist.addAll(clist);
		}
		this.setModel(new ListModelList(dlist));
		this.setSelectedIndex(0);
	}

	public void initdeplistx(Long kdid) {
		// ϵ
		List dlist = new ArrayList();
		WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, kdid);
		dlist.add(dept);
		this.setModel(new ListModelList(dlist));
		this.setSelectedIndex(0);
	}
}
