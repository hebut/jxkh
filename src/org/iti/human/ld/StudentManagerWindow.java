/**
 * 
 */
package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
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
import org.zkoss.zul.api.Listheader;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

/**
 * @author DaLei
 * @version $Id: StudentManagerWindow.java,v 1.1 2011/08/31 07:03:01 ljb Exp $
 */
public class StudentManagerWindow extends HumanBaseWindow {
	Panel userPanel;
	West westPanel;
	Tree westTree;
	Button addUser, search;
	Listbox leaderlist;
	Listheader yuan, xi;
	DepartmentService departmentService;
	StudentService studentService;
	XyUserRoleService xyUserRoleService;
	UserService userService;
	BsStudentService bsStudentService;
	boolean isSelect = false;
	WkTDept rootDept, selectDept;
	GradeListbox gradeList;
	Textbox nameSearch, tnoSearch;
	Integer selectGrade = 0;
	List dlist;

	public void setTitle(String title) {
		westPanel.setTitle(title);
		userPanel.setTitle(title.substring(2) + "列表");
		addUser.setLabel("添加" + title.substring(2));
	}

	public void onClick$importStudent() {
		final StudentImportWindow win = (StudentImportWindow) Executions.createComponents("/admin/human/student/importStudent.zul", null, null);
		win.setSchDept(rootDept.getSchDept());
		win.setStudentRole(getRole());
		win.doHighlighted();
		win.initWindow();
	}

	public void onClick$addUser() {
		final StudentNewWindow win = (StudentNewWindow) Executions.createComponents("/admin/human/student/newStudent.zul", null, null);
		win.setGrade(gradeList.getSelectGrade());
		win.setDept(getSelectDept());
		win.setDefaultRole(getRole());
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow();
				win.detach();
			}
		});
	}

	public void onClick$search() {
		this.selectGrade = gradeList.getSelectGrade();
		//System.out.print("nnnnn"+getRole().getKrId());
		List tlist = studentService.findBydeplistAndrid(dlist, getRole().getKrId(), gradeList.getSelectGrade(), nameSearch.getValue(), tnoSearch.getValue());
		//System.out.print("nnnnn"+tlist.size());
		leaderlist.setModel(new ListModelList(tlist));
	}

	public void initWindow() {
		// 首先加载左侧树，默认选择selectDept中组织
		loadTree();
		// 加载中间面板及列表
		initPanel();
		WkTDept dept = (WkTDept) userService.get(WkTDept.class, getRole().getKdId());
		yuan.setLabel(dept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel(dept.getGradeName(WkTDept.GRADE_XI));
	}

	private void loadTree() {
		List rlist = new ArrayList();
		rootDept = (WkTDept) departmentService.get(WkTDept.class, getXyUserRole().getKdId());
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.DANWEI));
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

	public void initShow() {
		addUser.setDisabled(true);
		search.setDisabled(true);
		westTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) userService.get(WkTDept.class, getRole().getKdId());
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
				if (titem.getTreechildren() == null) {
					addUser.setDisabled(false);
				} else {
					addUser.setDisabled(true);
				}
				if (search.isDisabled()) {
					search.setDisabled(false);
				}
				selectDept = (WkTDept) titem.getValue();
				dlist = new ArrayList();
				addDept(westTree.getSelectedItem(), dlist);
				initPanel();
			}
		});
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Student stu = (Student) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(stu.getStId());
				Listcell c3 = new Listcell(stu.getUser().getKuName());
				Listcell c4 = new Listcell(stu.getStClass());
				Listcell c5 = new Listcell(stu.getUser().getXiDept());
				Listcell c6 = new Listcell(stu.getUser().getYuDept());
				// 删除学生功能
				Listcell c7 = new Listcell();
				Hbox inhbox = new Hbox();
				InnerButton ine = new InnerButton("编辑");
				ine.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final StudentEditWindow win = (StudentEditWindow) Executions.createComponents("/admin/human/student/editStudent.zul", null, null);
						win.setDefaultRole(getRole());
						win.setStudent(stu);
						win.setRootDept(rootDept);
						win.doHighlighted();
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
								win.detach();
							}
						});
					}
				});
				inhbox.appendChild(ine);
				List listbs=bsStudentService.findByKuid(stu.getKuId());
				if(listbs!=null&&listbs.size()!=0){
					Label sd=new Label("已锁定");
					inhbox.appendChild(sd);
				}else{
					InnerButton inb = new InnerButton("删除");
					inb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("您确定删除吗", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								List xyList = xyUserRoleService.findByKuidAndKdid(stu.getKuId(), getRole().getKdId());
								for (int i = 0; i < xyList.size(); i++) {
									xyUserRoleService.deleteXyUserrole((XyUserrole) xyList.get(i));
								}
								WkTUser user = (WkTUser) userService.get(WkTUser.class, stu.getKuId());
								studentService.delete(stu);
								if (xyUserRoleService.findByKuid(stu.getKuId()).size() == 0) {
									userService.deleteUser(user);
								}
								initWindow();
							}
						}
					});
					inhbox.appendChild(inb);
				}
				
				
				
				c7.appendChild(inhbox);
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
