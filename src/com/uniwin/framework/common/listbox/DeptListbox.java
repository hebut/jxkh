package com.uniwin.framework.common.listbox;

/**
 * <li>组织部门列表组件，根据页面使用参数配置，可以是列表或者下拉列表
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
	// 列表第一个显示名称
	String topDept;
	// 父部门
	WkTDept rootDept;
	Long rootID;
	List<Long> dlist = new ArrayList<Long>();

	public void afterCompose() {
		Components.wireVariables(this, this);
	}

	/**
	 * <li>功能描述：递归迭代想rml中添加部门对象，其中不添加dept及其子部门
	 * 
	 * @param rml
	 *            存储列表
	 * @param pid
	 *            父部门列表
	 * @param dep
	 *            当前部门层次，用来控制显示此部门前空格数目
	 * @param dept
	 *            不显示部门 void
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
	 * <li>功能描述：初始化列表组件，对应部门编辑中的父部门选择，不包含arg部门，默认选择其父部门。
	 * 
	 * @param arg
	 *            当前部门，选中其父部门 void
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
					bla += "　";
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
					bla += "　";
					dep--;
				}
				item.setLabel(bla + d.getKdName().trim());
			}
		});
	}

	/**
	 * <li>功能描述：初始化部门列表组件，用来显示全部部门，同时选择arg部门。
	 * 
	 * @param arg
	 *            要选择的部门 void
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
					bla += "　";
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
			rootDept.setKdName("顶级");
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
