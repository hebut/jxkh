/**
 * 
 */
package org.iti.human.ld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.base.LeaderBysjWindow;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TeaSelectWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Listheader;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

/**
 * @author DaLei
 * @version $Id: TeacherManagerWindow.java,v 1.1 2011/08/31 07:03:01 ljb Exp $
 */
public class TeacherManagerWindow extends HumanBaseWindow {
	Panel userPanel;
	West westPanel;
	Tree westTree;
	Button addUser, impSchTeacher;
	Listbox leaderlist;
	Listheader yuan, xi;
	DepartmentService departmentService;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	UserService userService;
	WkTDept selectDept;
	boolean isSelect = false;
	WkTDept rootDept;
	Textbox nameSearch, tnoSearch;

	public void setTitle(String title) {
		westPanel.setTitle(title);
		userPanel.setTitle(title.substring(2) + "列表");
		addUser.setLabel("添加" + title.substring(2));
	}

	public void onClick$impExcTeacher() {
		final TeacherImportWindow1 win = (TeacherImportWindow1) Executions.createComponents("/admin/human/teacher/importExcTeacher.zul", null, null);
		win.setSchDept(rootDept.getSchDept());
		win.setTeacherRole(getRole());
		win.doHighlighted();
		win.initWindow();
	}

	public void onClick$impSchTeacher() {
		final TeacherSchImpWindow win = (TeacherSchImpWindow) Executions.createComponents("/admin/human/teacher/impSchTeacher.zul", null, null);
		win.setImportTeacherRole(getRole());
		win.initWindow();
		win.doHighlighted();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				if (selectDept == null)
					return;
				List stlist = win.getSelectUser();
				for (int i = 0; i < stlist.size(); i++) {
					Teacher tea = (Teacher) stlist.get(i);
					XyUserroleId xyuId = new XyUserroleId(getRole().getKrId(), tea.getKuId());
					XyUserrole xyu = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyuId);
					if (xyu == null) {
						xyu = new XyUserrole(xyuId, selectDept.getKdId());
						xyUserRoleService.saveXyUserrole(xyu);
					}
				}
				loadTree();
				onClick$submit();
				win.detach();
			}
		});
	}

	public void onClick$addUser() {
		final TeacherNewWindow1 win = (TeacherNewWindow1) Executions.createComponents("/admin/human/teacher/newTeacher.zul", null, null);
		win.setRootDept(rootDept);
		win.setDept(getSelectDept());
		win.setDefaultRole(getRole());
		win.doHighlighted();
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				loadTree();
				onClick$submit();
				win.detach();
			}
		});
	}

	public void initWindow() {
		WkTDept dept = (WkTDept) userService.get(WkTDept.class, getRole().getKdId());
		yuan.setLabel(dept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel(dept.getGradeName(WkTDept.GRADE_XI));
		loadTree();
	}

	public void onClick$submit() {
		if (westTree.getSelectedItem() == null) {
			throw new WrongValueException(westTree, "请选择要搜索的单位");
		}
		List dlist = new ArrayList();
		addDept(westTree.getSelectedItem(), dlist);
		List tlist = teacherService.findBydeplistAndrid(dlist, getRole().getKrId(), nameSearch.getValue(), tnoSearch.getValue());
		leaderlist.setModel(new ListModelList(tlist));
	}

	private void loadTree() {
		rootDept = (WkTDept) departmentService.get(WkTDept.class, getXyUserRole().getKdId());
		List rlist = new ArrayList();
		rlist.add(rootDept);
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.QUANBU));
	}

	public void initShow() {
		addUser.setDisabled(true);
		impSchTeacher.setDisabled(true);
		westTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				item.setLabel(dept.getKdName());
				WkTDept department = (WkTDept) userService.get(WkTDept.class, getRole().getKdId());
				if (!department.getKdName().equals(dept.getKdName()))
					item.setOpen(false);
				// Treerow tr = new Treerow();
				// Treecell tc1 = new Treecell(dept.getKdName());
				// tr.appendChild(tc1);
				// item.appendChild(tr);
				// Treecell tc2 = new Treecell(xyUserRoleService.countByRidAndKdid(getRole().getKrId(), dept.getKdId()) + "");
				// tr.appendChild(tc2);
				if (selectDept != null && selectDept.getKdId().longValue() == dept.getKdId().longValue()) {
					item.setSelected(true);
				}
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				selectDept = (WkTDept) westTree.getSelectedItem().getValue();
				onClick$submit();
				if (!isSelect) {
					addUser.setDisabled(false);
					impSchTeacher.setDisabled(false);
				}
			}
		});
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Teacher tea = (Teacher) data;
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				Listcell c4 = new Listcell(tea.getXiDept(getRole().getKdId()));
				Listcell c5 = new Listcell(tea.getYuDept(getRole().getKdId()));
				// 添加删除教师功能
				Listcell c7 = new Listcell();
				Hbox inhbox = new Hbox();
				InnerButton ine = new InnerButton("编辑");
				ine.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						final TeacherEditWindow1 win = (TeacherEditWindow1) Executions.createComponents("/admin/human/teacher/editTeacher.zul", null, null);
						win.setRootDept(rootDept);
						win.setDefaultRole(getRole());
						win.setTeacher(tea);
						win.doHighlighted();
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								onClick$submit();
								win.detach();
							}
						});
					}
				});
				InnerButton inb = new InnerButton("删除");
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("您确定删除吗", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							// 删除教师需要判断是否为借调删还是所在单位的删除
							if (tea.getUser().getDept().getKdSchid().longValue() == getRole().getKdId().longValue()) {
								// 如果为教师所属单位删除，则删除全部信息
								List xylist = xyUserRoleService.findByKuid(tea.getKuId());
								xyUserRoleService.deleteAll(xylist);
								WkTUser user = (WkTUser) userService.get(WkTUser.class, tea.getKuId());
								teacherService.delete(tea);
								userService.deleteUser(user);
							} else {
								// 如果为借调删除，则只删除角色用户关系
								List xyList = xyUserRoleService.findByKuidAndKdid(tea.getKuId(), getRole().getKdId());
								for (int i = 0; i < xyList.size(); i++) {
									xyUserRoleService.deleteXyUserrole((XyUserrole) xyList.get(i));
								}
							}
							loadTree();
							onClick$submit();
						}
					}
				});
				inhbox.appendChild(ine);
				inhbox.appendChild(inb);
				c7.appendChild(inhbox);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c7);
			}
		});
	}

	private void addDept(Treeitem item, List dlist) {
		dlist.add(item.getValue());
		if (item.getTreechildren() == null) {
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

	public void setSelectDept(WkTDept selectDept) {
		this.selectDept = selectDept;
	}
}
