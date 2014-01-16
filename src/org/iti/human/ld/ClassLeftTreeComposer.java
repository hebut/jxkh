package org.iti.human.ld;

import java.util.List;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

/**
 * 
 * @author DaLei
 * @version $Id: ClassLeftTreeComposer.java,v 1.1 2011/08/31 07:03:00 ljb Exp $
 */
public class ClassLeftTreeComposer extends BaseLeftTreeComposer {
	Tree classTree;
	WkTUser user;
	// 当前三级标题树的父标题编号
	Long tid;
	XyUserRoleService xyUserRoleService;
	TitleService titleService;
	Panel classPanel;
	Listbox delist;
	DepartmentService departmentService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tid = (Long) centerTabbox.getAttribute("tid");
		delist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(arg1);
			}
		});
		delist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				WkTDept dept = (WkTDept) delist.getSelectedItem().getValue();
				initClassTree(dept.getKdId());
				checkTitle();
			}
		});
		classTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem arg0, Object arg1) throws Exception {
				if (arg1 instanceof WkTDept) {
					WkTDept dept = (WkTDept) arg1;
					arg0.setLabel(dept.getKdName());
				} else {
					XyUserrole ur = (XyUserrole) arg1;
					WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, ur.getId().getKrId());
					arg0.setLabel(role.getKrName());
				}
				arg0.setValue(arg1);
			}
		});
		classTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem item = classTree.getSelectedItem();
				if (item.getTreechildren() != null)
					return;
				if (item.getValue() instanceof WkTDept) {
					WkTDept d = (WkTDept) item.getValue();
					Component c = (Component) creatTab("class", "管理班级", "/admin/human/class/manager.zul", classPanel);
					if (c instanceof ClassManagerWindow) {
						ClassManagerWindow baseWindow = (ClassManagerWindow) c;
						baseWindow.setXyUserRole(getRootRole(item));
						baseWindow.setRootDept(d);
						baseWindow.initWindow();
					}
				}
			}
		});
		initdeList();
		if (delist.getModel().getSize() == 0) {
			return;
		} else {
			WkTDept dept = (WkTDept) delist.getModel().getElementAt(0);
			initClassTree(dept.getKdId());
			checkTitle();
		}
	}

	private XyUserrole getRootRole(Treeitem item) {
		Treeitem pitem = item.getParentItem();
		if (pitem.getValue() instanceof XyUserrole) {
			return (XyUserrole) pitem.getValue();
		}
		return getRootRole(pitem);
	}

	private void checkTitle() {
		List tlist = classTree.getTreechildren().getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			if (item.getTreechildren() == null || item.getTreechildren().getChildren() == null) {
				item.setVisible(false);
			} else {
				item.setOpen(true);
			}
		}
	}

	private void initClassTree(Long kdid) {
		List roleList = xyUserRoleService.getUserRoleOfTitleAndDept(user.getKuId(), tid, kdid);
		OrganizeTreeModel organizeTreeModel = new OrganizeTreeModel(roleList, departmentService);
		classTree.setModel(organizeTreeModel);
	}

	private void initdeList() {
		List dlist = departmentService.getRootList(user.getKuId());
		delist.setModel(new ListModelList(dlist));
	}
}
