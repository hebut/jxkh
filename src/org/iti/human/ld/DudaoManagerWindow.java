package org.iti.human.ld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.entity.BsUserDudao;
import org.iti.bysj.entity.BsUserDudaoId;
import org.iti.bysj.service.CheckService;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.TeaSelectWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.api.Listheader;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;

public class DudaoManagerWindow extends HumanBaseWindow {
	Panel userPanel;
	West westPanel;
	Tree westTree;
	Button addUser;
	Listbox leaderlist;
	Listheader yuan, xi;
	DepartmentService departmentService;
	TeacherService teacherService;
	XyUserRoleService xyUserRoleService;
	CheckService checkService;
	boolean isSelect = false;
	// 选择的部门
	WkTDept dept;
	DeptTreeModel deptTreeModel;
	Textbox nameSearch, tnoSearch;

	public void setTitle(String title) {
		westPanel.setTitle(title);
		userPanel.setTitle(title.substring(2) + "列表");
		addUser.setLabel("添加" + title.substring(2));
	}

	public void onClick$addUser() {
		final DudaoSelTeaWindow win = (DudaoSelTeaWindow) Executions.createComponents("/admin/human/dudao/selecttea.zul", null, null);
		win.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Teacher.getRoleId(getRole().getKdId())));
		win.setNoRoleId(getRole().getKrId());
		win.setRootKdId(getXyUserRole().getKdId());
		win.setTitle("选择" + addUser.getLabel().substring(2));
		win.setSelectKdid(getSelectDept().getKdId());
		win.doHighlighted();
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
//				List slist = win.getSelectUser();
//				for (int i = 0; i < slist.size(); i++) {
//					Teacher t = (Teacher) slist.get(i);
//					XyUserroleId xyrId = new XyUserroleId(getRole().getKrId(), t.getKuId());
//					XyUserrole xyr = new XyUserrole(xyrId, getSelectDept().getKdId());
//					xyUserRoleService.saveXyUserrole(xyr);
//				}
//				win.detach();
				loadTree();
				onClick$submit();
			}
		});
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

	public void initWindow() {
		WkTDept rootDept = (WkTDept) departmentService.get(WkTDept.class, getXyUserRole().getKdId());
		yuan.setLabel("负责" + rootDept.getGradeName(WkTDept.GRADE_YUAN));
		xi.setLabel("督导所在" + rootDept.getGradeName(WkTDept.GRADE_XI));
		List rlist = new ArrayList();
		Map childMap = new HashMap();
		switch (getLeave()) {
		case 1:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
			}
			break;
		case 2:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
				childMap.put(rootDept, clist);
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {
					rlist.add(rootDept);
				}
			}
			break;
		case 3:
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
				childMap.put(rootDept, clist);
				for (int i = 0; i < clist.size(); i++) {
					WkTDept d = (WkTDept) clist.get(i);
					childMap.put(d, departmentService.getChildDepartment(d.getKdId(), WkTDept.QUANBU));
				}
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {
					rlist.add(rootDept);
					List clist = departmentService.getChildDepartment(rootDept.getKdId(), WkTDept.QUANBU);
					childMap.put(rootDept, clist);
				} else {
					rlist.add(rootDept);
				}
			}
			break;
		default:
			break;
		}
		deptTreeModel = new DeptTreeModel(rlist, childMap);
		loadTree();
	}

	private void loadTree() {
		westTree.setModel(deptTreeModel);
	}

	public void initShow() {
		addUser.setDisabled(true);
		westTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data) throws Exception {
				WkTDept dept = (WkTDept) data;
				item.setValue(data);
				item.setOpen(true);
				Treerow tr = new Treerow();
				Treecell tc1 = new Treecell(dept.getKdName());
				tr.appendChild(tc1);
				item.appendChild(tr);
				if (item.getTreechildren() == null) {
					Treecell tc2 = new Treecell(xyUserRoleService.countByRidAndKdid(getRole().getKrId(), dept.getKdId()) + "");
					tr.appendChild(tc2);
				}
				if (getDept() != null && dept.getKdId().longValue() == getDept().getKdId().longValue()) {
					item.setSelected(true);
				}
			}
		});
		westTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem sitem = westTree.getSelectedItem();
				if (sitem.getTreechildren() == null || sitem.getTreechildren().getChildren().size() == 0) {
					addUser.setDisabled(false);
				} else {
					addUser.setDisabled(true);
				}
				setDept((WkTDept) sitem.getValue());
				onClick$submit();
			}
		});
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Teacher tea = (Teacher) data;
				// 序号
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				// 教师号
				Listcell c2 = new Listcell(tea.getThId());
				// 督导姓名
				Listcell c3 = new Listcell(tea.getUser().getKuName());
				// 督导所在系
				Listcell c4 = new Listcell(tea.getXiDept(getRole().getKdId()));
				// 所负责学院
				List kdlist = checkService.findKdidByKuidAndKdid(tea.getKuId(), getRole().getKdId());
				String str = "";
				if (kdlist.size() < 3) {// 只负责两个或者一个系，则直接显示出来
					for (int i = 0; i < kdlist.size(); i++) {
						BsUserDudao dduser = (BsUserDudao) kdlist.get(i);
						WkTDept dept = (WkTDept) checkService.get(WkTDept.class, dduser.getId().getKdId());
						str += dept.getKdName();
					}
				} else {// 负责三个以上的系
					for (int i = 0; i < 2; i++) {
						BsUserDudao dduser = (BsUserDudao) kdlist.get(i);
						WkTDept dept = (WkTDept) checkService.get(WkTDept.class, dduser.getId().getKdId());
						str += dept.getKdName();
					}
					str += "查看更多";
				}
				if ("".equals(str.trim())) {
					str = "添加";
				}
				InnerButton yuanbt = new InnerButton(str);
				yuanbt.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final DudaoYuanListWindow yuanWin = (DudaoYuanListWindow) Executions.createComponents("/admin/human/dudao/yuanList.zul", null, null);
						yuanWin.setKdId(getRole().getKdId());
						yuanWin.setKuId(tea.getKuId());
						yuanWin.initWindow();
						yuanWin.doHighlighted();
						yuanWin.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								// 先查找出原来负责的所有学院
								List list = checkService.findKdidByKuidAndKdid(tea.getKuId(), getRole().getKdId());
								checkService.deleteAll(list);
								// 重新保存其所负责的学院
								list = yuanWin.getSelectUser();
								for (int i = 0; i < list.size(); i++) {
									WkTDept dept = (WkTDept) list.get(i);
									BsUserDudaoId id = new BsUserDudaoId();
									id.setKdId(dept.getKdId());
									id.setKuId(tea.getKuId());
									BsUserDudao userdao = new BsUserDudao(id);
									checkService.save(userdao);
								}
								yuanWin.detach();
								loadTree();
								onClick$submit();
							}
						});
					}
				});
				Listcell c5 = new Listcell();
				c5.appendChild(yuanbt);
				// 是否督导主任
				final Long krid = xyUserRoleService.getRoleId("督导主任", getRole().getKdId());
				final List zrlist = xyUserRoleService.findByKridAndKuid(krid, tea.getKuId());
				Listcell c6 = new Listcell();
				InnerButton ibyes = new InnerButton();
				InnerButton ibno = new InnerButton();
				if (zrlist.size() == 0) {// 不是督导主任
					ibno.setLabel("否");
					ibno.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("您确定要将其设置为督导主任吗？", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								XyUserrole xyUserrole = new XyUserrole();
								XyUserroleId id = new XyUserroleId();
								id.setKrId(krid);
								id.setKuId(tea.getKuId());
								xyUserrole.setId(id);
								xyUserrole.setKdId(getSelectDept().getKdId());
								xyUserRoleService.saveXyUserrole(xyUserrole);
								loadTree();
								onClick$submit();
							}
						}
					});
					c6.appendChild(ibno);
				} else {// 是督导主任
					ibyes.setLabel("是");
					ibyes.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("您确定要撤销其督导主任的职务吗？", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								XyUserrole xyUserrole = (XyUserrole) zrlist.get(0);
								xyUserRoleService.deleteXyUserrole(xyUserrole);
								loadTree();
								onClick$submit();
							}
						}
					});
					c6.appendChild(ibyes);
				}
				// 功能
				Listcell c7 = new Listcell();
				InnerButton inb = new InnerButton();
				Long ddkrid = xyUserRoleService.getRoleId("教学督导", getRole().getKdId());
				Long ckcount = checkService.countByKuidAndKrid(tea.getKuId(), ddkrid);
				ckcount += checkService.countComByKuidAndKrid(tea.getKuId(), ddkrid);
				if (ckcount == 0) {// 没有发布单向检查结果和综合检查结果
					inb.setLabel("删除");
					inb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (Messagebox.show("您确定删除吗", "请注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
								XyUserroleId xyrId = new XyUserroleId(getRole().getKrId(), tea.getKuId());
								XyUserrole xyr = (XyUserrole) xyUserRoleService.get(XyUserrole.class, xyrId);
								if (null != xyr) {
									xyUserRoleService.deleteXyUserrole(xyr);
								}
								if (zrlist.size() != 0) {// 是督导主任,也删除相关记录
									XyUserrole xyUserrole = (XyUserrole) zrlist.get(0);
									xyUserRoleService.deleteXyUserrole(xyUserrole);
								}
								// 将其所负责的学院记录都删除
								List dlist = checkService.findKdidByKuidAndKdid(tea.getKuId(), getRole().getKdId());
								checkService.deleteAll(dlist);
								loadTree();
								onClick$submit();
							}
						}
					});
					c7.appendChild(inb);
				} else {
					c7.setLabel("锁定");
				}
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

	class DeptTreeModel extends AbstractTreeModel {
		Map childMap;

		public DeptTreeModel(Object root, Map childMap) {
			super(root);
			this.childMap = childMap;
		}

		public Object getChild(Object parent, int index) {
			if (parent instanceof List) {
				return ((List) parent).get(index);
			}
			List clist = (List) childMap.get(parent);
			return clist.get(index);
		}

		public int getChildCount(Object parent) {
			if (parent instanceof List) {
				return ((List) parent).size();
			}
			List clist = (List) childMap.get(parent);
			return clist == null ? 0 : clist.size();
		}

		public boolean isLeaf(Object node) {
			List clist = (List) childMap.get(node);
			if (null == clist || clist.size() == 0)
				return true;
			return false;
		}
	}

	public WkTDept getDept() {
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
}
