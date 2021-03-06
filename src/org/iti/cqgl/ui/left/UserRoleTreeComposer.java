/**
 * 
 */
package org.iti.cqgl.ui.left;

import java.util.List;

import org.iti.bysj.ui.left.RoleTitleTreeModel;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
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
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

/**
 * 
 * @author DaLei
 * @version $Id: UserRoleTreeComposer.java,v 1.1 2011/08/31 07:01:18 ljb Exp $
 */
public class UserRoleTreeComposer extends BaseLeftTreeComposer {

	Tree cqglTree;
	WkTUser user;
	// 当前三级标题树的父标题编号
	Long tid;
	WkTTitle title;
	XyUserRoleService xyUserRoleService;
	TitleService titleService;
	Panel cqglPanel;
	Listbox delist;
	DepartmentService departmentService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tid = (Long) centerTabbox.getAttribute("tid");
		title = (WkTTitle) titleService.get(WkTTitle.class, tid);
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
				initJxglTree(dept.getKdId());
				checkTitle();
			}
		});

		cqglTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem arg0, Object arg1) throws Exception {
				if (arg1 instanceof WkTTitle) {
					WkTTitle title = (WkTTitle) arg1;
					arg0.setValue(title);
					arg0.setLabel(title.getKtName());
					arg0.setVisible(false);
					Treeitem pitem = arg0.getParentItem();
					if (pitem.getValue() instanceof XyUserrole) {
						arg0.setVisible(true);
					} else {
						WkTTitle ptitle = (WkTTitle) pitem.getValue();
						XyUserrole rootRole = getRootRole(pitem);
						List ctlist = titleService.getTitleOfRoleAccess(ptitle.getKtId(), rootRole.getId().getKrId());
						for (int i = 0; i < ctlist.size(); i++) {
							WkTTitle t = (WkTTitle) ctlist.get(i);
							if (t.getKtId().intValue() == title.getKtId().intValue()) {
								arg0.setVisible(true);
							}
						}
					}
				} else {
					XyUserrole ur = (XyUserrole) arg1;
					WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, ur.getId().getKrId());
					arg0.setLabel(role.getKrName());
					arg0.setValue(ur);
				}
			}
		});
		cqglTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem item = cqglTree.getSelectedItem();
				if (item.getTreechildren() != null)
					return;
				if (item.getValue() instanceof WkTTitle) {
					WkTTitle t = (WkTTitle) item.getValue();
					Component c = (Component) creatTab("cqgl", title != null ? title.getKtName().trim() : "出勤管理", t.getKtContent() + "index.zul", cqglPanel, true);
					if (c instanceof BaseWindow) {
						BaseWindow baseWindow = (BaseWindow) c;
						baseWindow.setXyUserRole(getRootRole(item));
						
						if (delist.getSelectedItem() == null) {
							baseWindow.setSchoolDept((WkTDept) delist.getModel().getElementAt(0));
						} else {
							baseWindow.setSchoolDept((WkTDept) delist.getSelectedItem().getValue());
						}
						baseWindow.initWindow();
					}
				}
			}
		});

		initdeList();

		if (delist.getModel().getSize() == 0) {
			return;
		} else {
			delist.setSelectedIndex(0);
			WkTDept dept = (WkTDept) delist.getModel().getElementAt(0);
			initJxglTree(dept.getKdId());
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
		List tlist = cqglTree.getTreechildren().getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			if (item.getTreechildren() == null || item.getTreechildren().getChildren() == null) {
				item.setVisible(false);
			} else {
				item.setOpen(true);
			}
		}
	}

	private void initJxglTree(Long kdid) {
		List roleList = xyUserRoleService.getUserRole(user.getKuId(), kdid);
		RoleTitleTreeModel roleTitleTreeModel = new RoleTitleTreeModel(roleList, titleService, tid);
		cqglTree.setModel(roleTitleTreeModel);
	}

	private void initdeList() {
		List dlist = departmentService.getRootList(user.getKuId());
		delist.setModel(new ListModelList(dlist));
	}
}
