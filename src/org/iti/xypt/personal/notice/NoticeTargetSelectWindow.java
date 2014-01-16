package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyClassService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Treechildren;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;

public class NoticeTargetSelectWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6981856380877999002L;
	Tree targetTree;
	MessageService messageService;
	DepartmentService departmentService;
	XyClassService xyClassService;
	XyUserrole xyUserrole;
	WkTRole role;
	DeptTreeModel deptTreeModel;
	List deptList = new ArrayList();
	List cllist = new ArrayList();
	List didlist = null;
	//
	Hbox gdhbox;
	Listbox gdlistox;

	public void initShow() {
		targetTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(final Treeitem item, Object data) throws Exception {
				item.setValue(data);
				Treerow tr = new Treerow();
				Treecell tc1 = new Treecell();
				Treecell tc2 = new Treecell();
				if (data instanceof WkTDept) {
					WkTDept dept = (WkTDept) data;
					tc1.setLabel(dept.getKdName());
					item.setOpen(true);
					if (didlist.contains(dept.getKdId())) {
						item.setSelected(true);
					}
				} else if (data instanceof XyClass) {
					XyClass cl = (XyClass) data;
					tc1.setLabel(cl.getClName());
					if (cllist.contains(cl.getClId())) {
						item.setSelected(true);
					}
				}
				item.appendChild(tr);
				tr.appendChild(tc1);
				tr.appendChild(tc2);
				if (item.getParentItem() == null) {
					InnerButton inb = new InnerButton();
					inb.setLabel("全选班级");
					inb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							InnerButton in = (InnerButton) arg0.getTarget();
							item.setSelected(false);
							if (in.getLabel().equalsIgnoreCase("全选班级")) {
								clickItem(item, true);
								in.setLabel("取消全选");
							} else {
								clickItem(item, false);
								in.setLabel("全选班级");
							}
						}
					});
					tc2.appendChild(inb);
				}
			}
		});
		gdlistox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Integer d = (Integer) data;
				if (d.intValue() == 0) {
					item.setLabel("全部");
				} else {
					item.setLabel(d.intValue() + "");
				}
				item.setValue(data);
			}
		});
		gdlistox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initWindow(xyUserrole, role, deptList);
			}
		});
	}

	private void clickItem(Treeitem item, boolean ck) {
		if (item.getValue() instanceof XyClass) {
			item.setSelected(ck);
		} else {
			Treechildren tch = item.getTreechildren();
			for (int i = 0; tch != null && i < tch.getChildren().size(); i++) {
				Treeitem it = (Treeitem) tch.getChildren().get(i);
				clickItem(it, ck);
			}
		}
	}

	private void loadGdlistbox() {
		gdhbox.setVisible(true);
		if (gdlistox.getModel() == null) {
			Calendar ca = Calendar.getInstance();
			int year = ca.get(Calendar.YEAR);
			List ylist = new ArrayList();
			ylist.add(0);
			for (int i = year - 5; i <= year; i++) {
				ylist.add(i);
			}
			gdlistox.setModel(new ListModelList(ylist));
		}
	}

	private int getGdYear() {
		if (gdlistox.getSelectedItem() == null) {
			return 0;
		} else {
			return Integer.parseInt(gdlistox.getSelectedItem().getValue().toString());
		}
	}

	public void initWindow(XyUserrole xyUserrole, WkTRole role, List deptList) {
		this.xyUserrole = xyUserrole;
		this.role = role;
		this.deptList = deptList;
		WkTDept rootDept = (WkTDept) departmentService.get(WkTDept.class, xyUserrole.getKdId());
		List rlist = new ArrayList();
		Map childMap = new HashMap();
		switch (role.getKrArgs(WkTRole.INDEX_GRADE)) {
		case '1':// 用户管理学校，需要显示学校级别
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
			}
			break;
		case '2':
			if (rootDept.getKdPid().intValue() == 0) {
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(),WkTDept.QUANBU);
				childMap.put(rootDept, clist);
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {
					rlist.add(rootDept);
				}
			}
			break;
		case '3':
			if (rootDept.getKdPid().intValue() == 0) {// 用户管理学校
				rlist.add(rootDept);
				List clist = departmentService.getChildDepartment(rootDept.getKdId(),WkTDept.QUANBU);
				childMap.put(rootDept, clist);// 添加学院
				for (int i = 0; i < clist.size(); i++) {
					WkTDept d = (WkTDept) clist.get(i);
					List xilist = departmentService.getChildDepartment(d.getKdId(),WkTDept.QUANBU);
					childMap.put(d, xilist);
					if (role.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XS) {
						loadGdlistbox();
						for (int j = 0; j < xilist.size(); j++) {
							WkTDept xi = (WkTDept) xilist.get(j);
							List cllist = null;
							if (getGdYear() == 0) {
								cllist = xyClassService.findByKdid(xi.getKdId());
							} else {
								cllist = xyClassService.findByKdid(xi.getKdId(), getGdYear());
							}
							childMap.put(xi, cllist);
						}
					}
				}
			} else {
				WkTDept pdept = (WkTDept) departmentService.get(WkTDept.class, rootDept.getKdPid());
				if (pdept.getKdPid().intValue() == 0) {// rootDept为院级别
					rlist.add(rootDept);
					List clist = departmentService.getChildDepartment(rootDept.getKdId(),WkTDept.QUANBU);
					childMap.put(rootDept, clist);
					if (role.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XS) {
						loadGdlistbox();
						for (int j = 0; j < clist.size(); j++) {
							WkTDept xi = (WkTDept) clist.get(j);
							List cllist = null;
							if (getGdYear() == 0) {
								cllist = xyClassService.findByKdid(xi.getKdId());
							} else {
								cllist = xyClassService.findByKdid(xi.getKdId(), getGdYear());
							}
							childMap.put(xi, cllist);
						}
					}
				} else {// 为系级别
					rlist.add(rootDept);
					if (role.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XS) {
						loadGdlistbox();
						List cllist = null;
						if (getGdYear() == 0) {
							cllist = xyClassService.findByKdid(rootDept.getKdId());
						} else {
							cllist = xyClassService.findByKdid(rootDept.getKdId(), getGdYear());
						}
						childMap.put(rootDept, cllist);
					}
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
		didlist = new ArrayList();
		for (int i = 0; i < deptList.size(); i++) {
			if (deptList.get(i) instanceof WkTDept) {
				WkTDept d = (WkTDept) deptList.get(i);
				didlist.add(d.getKdId());
			} else if (deptList.get(i) instanceof XyClass) {
				XyClass cl = (XyClass) deptList.get(i);
				cllist.add(cl.getClId());
			}
		}
		targetTree.setModel(deptTreeModel);
	}

	public void onClick$submit() {
		List temlist = new ArrayList();
		Set rset = targetTree.getSelectedItems();
		Iterator it = rset.iterator();
		while (it.hasNext()) {
			Treeitem item = (Treeitem) it.next();
			temlist.add(item.getValue());
		}
		deptList = temlist;
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$reset() {
		loadTree();
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		initShow();
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

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}
}
