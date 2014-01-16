package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyNUrdId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
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

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

public class StuHeaderManagerWindow extends HumanBaseWindow {
	West westDeptPanel;
	Tree westDeptTree;
	WkTDept rootDept;
	Panel stuheaderPanel;
	DepartmentService departmentService;
	XyClassService xyClassService;
	XyUserRoleService xyUserRoleService;
	UserService userService;
	WkTDept selectDept;
	XyClass selectClass;
	WkTUser user;
	// 组建
	/**
	 * 当前选择组织节点下最终系列表
	 */
	List dlist, showlist;
	Button addStuHeader;
	Listbox stulist;
	StudentService studentService;
	RoleService roleService;
	Textbox nameSearch, snoSearch;

	public void initShow() {
		westDeptTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				if (data instanceof WkTDept) {
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
				} else if (data instanceof XyClass) {
					XyClass c = (XyClass) data;
					item.setImage("/images/tree/dept/danwei.gif");
					item.setHeight("25px");
					item.setValue(c);
					item.setLabel(c.getClSname());
					if (selectClass != null && selectClass.getClId().longValue() == c.getClId().longValue()) {
						item.setSelected(true);
					}
					item.setOpen(true);
				}
			}
		});
		westDeptTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event event) throws Exception {
				dlist.clear();
				if (westDeptTree.getSelectedItem().getValue() instanceof WkTDept) {
					selectDept = (WkTDept) westDeptTree.getSelectedItem().getValue();
					initPanel();
				}
				if (westDeptTree.getSelectedItem().getValue() instanceof XyClass) {
					selectClass = (XyClass) westDeptTree.getSelectedItem().getValue();
					initPanel();
				}
			}
		});
		stulist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				item.setValue(data);
				final Student stu = (Student) data;
				WkTUser sname = (WkTUser) userService.findSnameBystid(stu.getStId()).get(0);
				//System.out.println(sname.getKuId() + " " + stu.getClId());
				XyClass cla = (XyClass) userService.get(XyClass.class, stu.getClId());
				WkTDept dept = (WkTDept) userService.get(WkTDept.class, cla.getKdId());
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(stu.getStId());
				Listcell c2 = new Listcell(sname.getKuName());
				Listcell c3 = new Listcell(stu.getStClass());
				Listcell c4 = new Listcell(dept.getKdName());
				Listcell c5 = new Listcell();
				InnerButton ibt = new InnerButton("删除");
				ibt.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						XyUserroleId xyrId = new XyUserroleId();
						xyrId.setKuId(stu.getKuId());
						xyrId.setKrId(getRole().getKrId());
						XyUserrole xyu = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyrId);
						if (xyu != null) {
							xyUserRoleService.deleteXyUserrole(xyu);
						}
						initPanel();
					}
				});
				ibt.setParent(c5);
				item.appendChild(c0);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
			}
		});
	}

	public void initWindow() {
		addStuHeader.setLabel("增加" + getRole().getKrName());
		loadTree();
		if (selectDept == null) {
			Treeitem item = (Treeitem) westDeptTree.getTreechildren().getChildren().get(0);
			item.setSelected(true);
			selectDept = (WkTDept) item.getValue();
		}
		initPanel();
	}

	private void initPanel() {
		if (westDeptTree.getSelectedItem().getValue() instanceof WkTDept)
			stuheaderPanel.setTitle("管理" + selectDept.getKdName().trim() + getRole().getKrName());
		else if (westDeptTree.getSelectedItem().getValue() instanceof XyClass)
			stuheaderPanel.setTitle("管理" + selectClass.getClSname().trim() + getRole().getKrName());
		dlist = new ArrayList();
		loadDeptList(westDeptTree.getSelectedItem());
		onClick$search();
	}

	public void onClick$addStuHeader() {
		final StuHeaderListWindow w = (StuHeaderListWindow) Executions.createComponents("/admin/human/stuheader/stuList.zul", null, null);
		w.setSelectItem(westDeptTree.getSelectedItem().getValue());
		w.setShowlist(showlist);
		w.setKuId(user.getKuId());
		w.doHighlighted();
		w.initWindow();
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				for (int i = 0; i < showlist.size(); i++) {
					Student stu = (Student) showlist.get(i);
					xyUserRoleService.deleteAll(xyUserRoleService.findByKridAndKuid(getRole().getKrId(), stu.getKuId()));
				}
				List slist = w.getSelectUser();
				for (int i = 0; i < slist.size(); i++) {
					Student s = (Student) slist.get(i);
					XyUserroleId xyrId = new XyUserroleId();
					XyUserrole xyr = new XyUserrole();
					xyrId.setKuId(s.getKuId());
					xyrId.setKrId(getRole().getKrId());
					xyr.setId(xyrId);
					if (westDeptTree.getSelectedItem().getValue() instanceof WkTDept) {
						WkTDept dept = (WkTDept) westDeptTree.getSelectedItem().getValue();
						xyr.setKdId(dept.getKdId());
					} else if (westDeptTree.getSelectedItem().getValue() instanceof XyClass) {
						XyClass cla = (XyClass) westDeptTree.getSelectedItem().getValue();
						WkTDept dept = (WkTDept) userService.get(WkTDept.class, cla.getKdId());
						xyr.setKdId(dept.getKdId());
					}
					xyUserRoleService.saveXyUserrole(xyr);
					XyNUrdId xnurid = new XyNUrdId(xyrId.getKrId(), xyrId.getKuId(), s.getClId(), XyNUrd.TYPE_CLID);
					XyNUrd xnu = new XyNUrd(xnurid);
					xyUserRoleService.save(xnu);
				}
				initPanel();
				w.detach();
			}
		});
	}

	public void onClick$search() {
		if (westDeptTree.getSelectedItem() == null) {
			throw new WrongValueException(westDeptTree, "请选择要搜索的单位");
		}
		if (westDeptTree.getSelectedItem().getTreechildren() == null)
			addStuHeader.setDisabled(false);
		else
			addStuHeader.setDisabled(true);
		if (westDeptTree.getSelectedItem().getValue() instanceof XyClass) {
			XyClass cla = (XyClass) westDeptTree.getSelectedItem().getValue();
			showlist = roleService.findStuByRole(cla.getClId(), getRole().getKrId(), nameSearch.getValue(), snoSearch.getValue());
		} else if (westDeptTree.getSelectedItem().getValue() instanceof WkTDept) {
			showlist = roleService.findStuByRole(((WkTDept)westDeptTree.getSelectedItem().getValue()).getKdId(), user.getKuId(), getRole().getKrId(), nameSearch.getValue(), snoSearch.getValue());
		}
		stulist.setModel(new ListModelList(showlist));
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
		ClassTreeModel dtm = new ClassTreeModel(rlist, departmentService, xyClassService, user.getKuId());
		westDeptTree.setModel(dtm);
	}

	public void onClick$reset() {
		selectDept = null;
		initWindow();
	}

	public WkTDept getRootDept() {
		return rootDept;
	}

	public void setRootDept(WkTDept rootDept) {
		this.rootDept = rootDept;
	}

	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}
}
