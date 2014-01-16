package org.iti.feedback.ui.usb.student;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.StudentService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class StudentWindow extends Window implements AfterCompose {
	Textbox nameSearch, tnoSearch;
	Button search;
	Listbox leaderlist, deptList;
	Listheader yuan, xi;
	Tree westTree;
	List dlist;
	GradeListbox gradeList;
	WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	WkTDept rootDept, selectDept;;
	UserService userService;
	DepartmentService departmentService;
	StudentService studentService;
	RoleService roleService;
	Integer selectGrade = 0;
	Long Role, KdId;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initWindow();
	}

	public void initWindow() {
		List deptlist = departmentService.findDeptByKdidAndType(0L, "1");
		deptList.setModel(new ListModelList(deptlist));
		deptList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(dept.getKdId());
			}
		});
		deptList.getItemAtIndex(0).setSelected(true);
		WkTDept department = (WkTDept) deptlist.get(0);
		KdId = department.getKdId();
		List rolelist = roleService.findByKdidAndKrname(KdId, "学生");
		if (rolelist.size() != 0) {
			WkTRole role = (WkTRole) rolelist.get(0);
			Role = role.getKrId();
		}
		search.setDisabled(true);
		loadTree();
		initPanel();
		WkTDept dept = (WkTDept) userService.get(WkTDept.class, KdId);
		yuan.setLabel(dept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel(dept.getGradeName(WkTDept.GRADE_XI));
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Student stu = (Student) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(stu.getStId());
				Listcell c3 = new Listcell(stu.getUser().getKuName());
				Listcell c4 = new Listcell(stu.getStClass());
				Listcell c5 = new Listcell(stu.getUser().getXiDept());
				Listcell c6 = new Listcell(stu.getUser().getYuDept());
				Listcell c7=new Listcell(stu.getUser().getKuPasswd());
				
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

	private void loadTree() {
		List rlist = new ArrayList();
		rootDept = (WkTDept) departmentService.get(WkTDept.class, KdId);
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.DANWEI));
		westTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) userService.get(WkTDept.class, KdId);
				if (!department.getKdName().equals(dept.getKdName()))
					item.setOpen(false);
				if (selectDept != null && selectDept.getKdId().intValue() == dept.getKdId().intValue()) {
					item.setSelected(true);
				}
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem titem = westTree.getSelectedItem();
				if (search.isDisabled()) {
					search.setDisabled(false);
				}
				selectDept = (WkTDept) titem.getValue();
				dlist = new ArrayList();
				addDept(westTree.getSelectedItem(), dlist);
				initPanel();
			}
		});
	}

	private void initPanel() {
		if (dlist == null) {
			return;
		}
		gradeList.setDeptList(dlist);
		gradeList.setGrade(selectGrade);
		gradeList.loadList();
		onClick$search();
	}

	private void addDept(Treeitem item, List dlist) {
		if (item.getTreechildren() == null) {
			dlist.add(item.getValue());
			return;
		}
		List clist = item.getTreechildren().getChildren();
		for (int i = 0; i < clist.size(); i++) {
			Treeitem it = (Treeitem) clist.get(i);
			addDept(it, dlist);
		}
	}

	public void onClick$search() {
		this.selectGrade = gradeList.getSelectGrade();
		List tlist = studentService.findBydeplistAndrid(dlist, Role, gradeList.getSelectGrade(), nameSearch.getValue(), tnoSearch.getValue());
		leaderlist.setModel(new ListModelList(tlist));
	}

	public void onSelect$deptList() {
		KdId = Long.parseLong(deptList.getSelectedItem().getValue().toString());
		List rolelist = roleService.findByKdidAndKrname(KdId, "学生");
		if (rolelist.size() != 0) {
			WkTRole role = (WkTRole) rolelist.get(0);
			Role = role.getKrId();
		}
		loadTree();
		initPanel();
	}

	private WkTDept getSelectDept() {
		return (WkTDept) westTree.getSelectedItem().getValue();
	}

	public Integer getSelectGrade() {
		return selectGrade;
	}

	public void setSelectGrade(Integer selectGrade) {
		this.selectGrade = selectGrade;
	}
}
