package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentSortWindow;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class OrganizeManagerWindow1 extends BaseWindow {

	West westDeptPanel;
	Tree westDeptTree;
	WkTDept rootDept;
	Panel deptPanel;
	DepartmentService departmentService;
	UserService userService;
	WkTDept selectDept;
	Textbox dname, ddesc, dnumber, gradeOne, gradeTwo, gradeThr;
	Radio bumen, danwei, xueke;
	Button addDept, deleteDept;
	Label pdeptLabel;
	Row gradeNameRow, descRow;

	public void initShow() {
		westDeptTree.setTreeitemRenderer(new TreeitemRenderer() {

			public void render(Treeitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				if (d.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
					item.setImage("/images/tree/dept/bumen.gif");
					item.setVisible(false);
				} else if (d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
					item.setImage("/images/tree/dept/danwei.gif");
				} else {
					item.setImage("/images/tree/dept/xueke.gif");
				}
				item.setHeight("25px");
				item.setValue(d);
				item.setLabel(d.getKdName());
				if (item.getLevel() == 2 && !d.getKdType().trim().equalsIgnoreCase(WkTDept.XUEKE)) {
					item.setVisible(false);
				}
				if (item.getLevel() == 0) {
					item.setOpen(true);
				} else {
					item.setOpen(false);
				}
				if (selectDept != null && selectDept.getKdId().longValue() == d.getKdId().longValue()) {
					item.setSelected(true);
				}
			}
		});
		westDeptTree.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event event) throws Exception {
				selectDept = (WkTDept) westDeptTree.getSelectedItem().getValue();
				initPanel();
				if (westDeptTree.getSelectedItem().getLevel() == 2) {
					bumen.setDisabled(true);
					danwei.setDisabled(true);
					xueke.setDisabled(true);
				} else {
					bumen.setDisabled(false);
					danwei.setDisabled(false);
					xueke.setDisabled(false);
				}
			}
		});
	}

	public void initWindow() {
		loadTree();
		if (selectDept == null) {
			Treeitem item = (Treeitem) westDeptTree.getTreechildren().getChildren().get(0);
			item.setSelected(true);
			selectDept = (WkTDept) item.getValue();
		}
		initPanel();
	}

	private void initPanel() {
		List listuser = userService.findUserBykdid(selectDept.getKdId());
		if (listuser != null && listuser.size() != 0) {
			deleteDept.setDisabled(true);
		} else {
			deleteDept.setDisabled(false);
		}
		WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, selectDept.getKdPid());
		if (pdept == null) {
			danwei.setDisabled(false);
			pdeptLabel.setValue("顶级");
		} else if (pdept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			danwei.setDisabled(true);
			pdeptLabel.setValue(pdept.getKdName());
		} else {
			danwei.setDisabled(false);
			pdeptLabel.setValue(pdept.getKdName());
		}
		dname.setValue(selectDept.getKdName());
		ddesc.setValue(selectDept.getKdDesc());
		dnumber.setValue(selectDept.getKdNumber());
		if (selectDept.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
			danwei.setChecked(true);
			bumen.setChecked(false);
			xueke.setChecked(false);
		} else if (selectDept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			bumen.setChecked(true);
			danwei.setChecked(false);
			xueke.setChecked(false);
		} else {
			bumen.setChecked(false);
			danwei.setChecked(false);
			xueke.setChecked(true);
		}
		System.out.println(rootDept.getKdId().longValue() + "-" + selectDept.getKdId().longValue());
		if (rootDept.getKdId().longValue() == selectDept.getKdId().longValue()) {
			deleteDept.setVisible(false);
			// sortDept.setVisible(false);
		} else {
			deleteDept.setVisible(true);
			// sortDept.setVisible(true);
		}
		if (selectDept.getKdLevel().intValue() == 3) {
			addDept.setVisible(false);
		} else {
			addDept.setVisible(true);
		}
		if (selectDept.getKdLevel().intValue() == 1) {
			descRow.setVisible(false);
			gradeNameRow.setVisible(true);
			String[] gradeName = selectDept.getKdDesc().split(",");
			gradeOne.setValue(gradeName[1]);
			gradeTwo.setValue(gradeName[2]);
			gradeThr.setValue(gradeName[3]);
		} else {
			descRow.setVisible(true);
			gradeNameRow.setVisible(false);
		}
	}

	private void loadTree() {
		List rlist = new ArrayList();
		rlist.add(rootDept);
		DepartmentTreeModel dtm = new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU);
		westDeptTree.setModel(dtm);
	}

	public void onClick$submit() throws InterruptedException {
		selectDept.setKdName(dname.getValue());
		selectDept.setKdNumber(dnumber.getValue());
		if (bumen.isChecked()) {
			selectDept.setKdType(WkTDept.BUMEN);
		} else if (danwei.isChecked()) {
			selectDept.setKdType(WkTDept.DANWEI);
		} else {
			selectDept.setKdType(WkTDept.XUEKE);
		}
		if (selectDept.getKdLevel().intValue() == 1) {
			if (gradeThr.getValue() == null || gradeThr.getValue().length() == 0) {
				throw new WrongValueException(gradeThr, "不能为空");
			} else if (gradeTwo.getValue() == null || gradeTwo.getValue().length() == 0) {
				throw new WrongValueException(gradeTwo, "不能为空");
			} else if (gradeOne.getValue() == null || gradeOne.getValue().length() == 0) {
				throw new WrongValueException(gradeOne, "不能为空");
			}
			String desc = "," + gradeOne.getValue() + "," + gradeTwo.getValue() + "," + gradeThr.getValue();
			selectDept.setKdDesc(desc);
		} else {
			WkTDept numDept = departmentService.findByKdnumberAndKdSchid(dnumber.getValue().trim(), selectDept.getKdSchid());
			if (numDept != null && numDept.getKdId().longValue() != selectDept.getKdId().longValue()) {
				throw new WrongValueException(dnumber, "编号已经存在,与" + numDept.getKdName() + "相同");
			}
			selectDept.setKdDesc(ddesc.getValue());
		}
		departmentService.update(selectDept);
		if (selectDept.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
			releateUpdateDept(departmentService.getChildDepartment(selectDept.getKdId(), WkTDept.QUANBU));
		}
		if (Messagebox.show("保存成功!是否刷新组织树?", "编辑部门", Messagebox.OK, Messagebox.INFORMATION) == 1) {
			initWindow();
		}
	}

	private void releateUpdateDept(List dlist) {
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept d = (WkTDept) dlist.get(i);
			if (d.getKdType().trim().equalsIgnoreCase(WkTDept.DANWEI)) {
				d.setKdType(WkTDept.BUMEN);
				departmentService.update(d);
			}
			releateUpdateDept(departmentService.getChildDepartment(d.getKdId(), WkTDept.QUANBU));
		}
	}

	public void onClick$addDept() {
		final OrganizeNewWindow w = (OrganizeNewWindow) Executions.createComponents("/admin/human/Organize1/deptNew.zul", null, null);
		w.doHighlighted();
		w.setParentDept(selectDept);
		w.initWindow();
		w.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				loadTree();
				w.detach();
			}
		});
	}

	public void onClick$reset() {
		initWindow();
	}

	// public void onClick$sortDept() {
	// final DepartmentSortWindow w = (DepartmentSortWindow) Executions.createComponents("/admin/human/Organize/deptSort.zul", null, null);
	// w.doHighlighted();
	// w.initWindow(selectDept);
	// w.addEventListener(Events.ON_CHANGE, new EventListener() {
	//
	// public void onEvent(Event arg0) throws Exception {
	// initWindow();
	// w.detach();
	// }
	// });
	// }
	public void onClick$deleteDept() {
		try {
			if (Messagebox.show("确定删除此部门吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (departmentService.getChildDepartment(selectDept.getKdId(), WkTDept.QUANBU).size() > 0) {
					Messagebox.show("角色包含子部门不能删除", "删除失败", Messagebox.OK, Messagebox.EXCLAMATION);
				} else {
					WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, selectDept.getKdPid());
					departmentService.delete(selectDept);
					this.selectDept = pdept;
					loadTree();
					initPanel();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
	}
}
