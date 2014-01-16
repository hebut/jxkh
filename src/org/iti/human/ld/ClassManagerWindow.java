package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class ClassManagerWindow extends BaseWindow {
	West westDeptPanel;
	Tree westDeptTree;
	WkTDept rootDept;
	Panel classPanel;
	DepartmentService departmentService;
	WkTDept selectDept;
	// 组建
	/**
	 * 当前选择组织节点下最终系列表
	 */
	List dlist;
	GradeListbox gradeList;
	Listbox classlist;
	StudentService studentService;
	XyClassService xyClassService;
	Textbox nameSearch;
	Integer selectGrade = 0;

	public void initShow() {
		westDeptTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept d = (WkTDept) data;
				if (d.getKdType().trim().equalsIgnoreCase(WkTDept.BUMEN)) {
					item.setImage("/images/tree/dept/bumen.gif");
				} else {
					item.setImage("/images/tree/dept/danwei.gif");
				}
				item.setHeight("25px");
				item.setValue(d);
				item.setLabel(d.getKdName());
				if (selectDept != null && selectDept.getKdId().longValue() == d.getKdId().longValue()) {
					item.setSelected(true);
				}
				item.setOpen(true);
			}
		});
		westDeptTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				selectDept = (WkTDept) westDeptTree.getSelectedItem().getValue();
				initPanel();
			}
		});
		classlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				item.setValue(data);
				final XyClass xyClass = (XyClass) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(xyClass.getClGrade() + "");
				Listcell c3 = new Listcell(xyClass.getClName());
				final Long stcount = studentService.countByClass(xyClass.getClId());
				Listcell c4 = new Listcell(stcount + "");
				Listcell c5 = new Listcell(xyClass.getXiDept());
				Listcell c6 = new Listcell(xyClass.getYuanDept());
				Listcell c7 = new Listcell();
				Hbox innerbox = new Hbox();
				InnerButton inEdit = new InnerButton();
				inEdit.setLabel("编辑");
				inEdit.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						final ClassEditWindow cedit = (ClassEditWindow) Executions.createComponents("/admin/human/class/edit.zul", null, null);
						cedit.setDept(selectDept);
						cedit.setXyClass(xyClass);
						cedit.setDefaultGrade(xyClass.getClGrade());
						if (stcount.longValue() > 0L) {
							cedit.setUpdateStudent(true);
						}
						cedit.doHighlighted();
						cedit.initWindow();
						cedit.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initPanel();
								cedit.detach();
							}
						});
					}
				});
				InnerButton inDelete = new InnerButton();
				inDelete.setLabel("删除");
				inDelete.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						if (Messagebox.show("您确认删除此班级吗？", "删除班级", Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) == 1) {
							xyClassService.delete(xyClass);
							initPanel();
						}
					}
				});
				innerbox.appendChild(inEdit);
				innerbox.appendChild(inDelete);
				if (stcount.longValue() > 0L) {
					inDelete.setVisible(false);
				}
				c7.appendChild(innerbox);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
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
		classPanel.setTitle("管理" + selectDept.getKdName().trim() + "班级");
		dlist = new ArrayList();
		loadDeptList(westDeptTree.getSelectedItem());
		gradeList.setDeptList(dlist);
		gradeList.setGrade(selectGrade);
		gradeList.loadList();
		onClick$search();
	}

	public void onClick$addClass() {
		final ClassNewWindow cnew = (ClassNewWindow) Executions.createComponents("/admin/human/class/new.zul", null, null);
		cnew.setDept(selectDept);
		cnew.setDefaultGrade(selectGrade.intValue() == 0 ? null : selectGrade);
		cnew.doHighlighted();
		cnew.initWindow();
		cnew.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initPanel();
				cnew.detach();
			}
		});
	}

	public void onClick$search() {
		this.selectGrade = gradeList.getSelectGrade();
		List clist = xyClassService.findByGradeAndName(gradeList.getSelectGrade(), nameSearch.getValue(), dlist);
		classlist.setModel(new ListModelList(clist));
	}

	private void loadDeptList(Treeitem item) {
		if (item.getTreechildren() == null) {
			dlist.add(item.getValue());
			return;
		}
		List clist = item.getTreechildren().getChildren();
		for (int i = 0; i < clist.size(); i++) {
			Treeitem it = (Treeitem) clist.get(i);
			loadDeptList(it);
		}
	}

	private void loadTree() {
		List rlist = new ArrayList();
		rlist.add(rootDept);
		DepartmentTreeModel dtm = new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU);
		westDeptTree.setModel(dtm);
	}

	public void onClick$reset() {
		this.selectGrade = 0;
		selectDept = null;
		initWindow();
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
	}
}
