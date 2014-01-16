/**
 * 
 */
package org.iti.human.ld;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.bysj.ui.listbox.GradeListbox;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.service.YjsService;
import org.iti.xypt.ui.base.TeaSelectWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
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
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.api.Listheader;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;
import com.uniwin.framework.ui.dept.DepartmentTreeModel;

public class XkManagerWindow extends HumanBaseWindow {

	Panel userPanel;
	West westPanel;
	Tree westTree;
	Button addUser, search;
	Listbox leaderlist;
	Listheader yuan, xi;
	DepartmentService departmentService;
	YjsService yjsService;
	XyUserRoleService xyUserRoleService;
	TeacherService teacherService;
	RoleService roleService;
	UserService userService;
	boolean isSelect = false;
	WkTDept rootDept, selectDept;
	Textbox nameSearch, tnoSearch;
	Integer selectGrade = 0;
	List dlist;
	WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");

	public void setTitle(String title) {
		westPanel.setTitle(title);
		userPanel.setTitle(title.substring(2) + "列表");
		addUser.setLabel("添加" + title.substring(2));
	}

	public void onClick$addUser() {
		List rlist = roleService.findByName("学科负责人");
		final WkTDept dept = (WkTDept) westTree.getSelectedItem().getValue();
		if (rlist.size() != 0) {
			final Long role = (Long) rlist.get(0);
			final TeaSelectWindow win = (TeaSelectWindow) Executions.createComponents("/admin/common/teaSelect/index.zul", null, null);
			win.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(user.getDept().getKdSchid())));
			win.setNoRoleId(getRole().getKrId());
			win.setTitle("学科负责人");
			win.setRootKdId(user.getDept().getKdSchid());
			win.doHighlighted();
			win.initWindow();
			win.addEventListener(Events.ON_CHANGE, new EventListener() {

				public void onEvent(Event arg0) throws Exception {
					List ulist = win.getSelectUser();
					for (int i = 0; i < ulist.size(); i++) {
						Teacher tea = (Teacher) ulist.get(i);
						WkTUser user = (WkTUser) xyUserRoleService.get(WkTUser.class, tea.getKuId());
						XyUserroleId xyuId = new XyUserroleId(role, user.getKuId());
						XyUserrole xyu = new XyUserrole(xyuId, dept.getKdId());
						xyUserRoleService.saveXyUserrole(xyu);
					}
					win.detach();
					initPanel();
				}
			});
		}
	}

	public void onClick$search() {
		WkTDept dept = (WkTDept) westTree.getSelectedItem().getValue();
		List tlist = xyUserRoleService.findByKridAndKdidAndNameAndThid(getRole().getKrId(), dept.getKdId(), nameSearch.getValue(), tnoSearch.getValue());
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
		westTree.setModel(new DepartmentTreeModel(rlist, departmentService, WkTDept.XUEKE));
	}

	private void initPanel() {
		if (dlist == null) {
			return;
		}
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
				final XyUserrole xyu = (XyUserrole) data;
				Teacher tea = teacherService.findBtKuid(xyu.getUser().getKuId());
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(tea.getThId());
				Listcell c3 = new Listcell(xyu.getUser().getKuName());
				Listcell c4 = new Listcell(xyu.getUser().getXiDept());
				Listcell c5 = new Listcell(xyu.getUser().getYuDept());
				Listcell c6 = new Listcell(tea.getTitle().getTiName());
				WkTDept dept = (WkTDept) teacherService.get(WkTDept.class, xyu.getKdId());
				Listcell c7 = new Listcell(dept.getKdName());
				// 删除学生功能
				Listcell c8 = new Listcell();
				InnerButton inb = new InnerButton("删除");
				inb.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("您确定删除吗", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xyUserRoleService.deleteXyUserrole(xyu);
							initPanel();
						}
					}
				});
				c8.appendChild(inb);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
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
