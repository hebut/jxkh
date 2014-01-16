/**
 * 
 */
package org.iti.human.ld;

import java.util.List;

import org.iti.bysj.ui.left.RoleTitleTreeModel;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

/**
 * 
 * @author DaLei
 * @version $Id: LeadManagerTreeComposer.java,v 1.1 2011/08/31 07:03:00 ljb Exp $
 */
public class LeadManagerTreeComposer extends BaseLeftTreeComposer {
	Tree leaderTree;
	WkTUser user;
	// 当前三级标题树的父标题编号
	Long tid;
	XyUserRoleService xyUserRoleService;
	TitleService titleService;
	Panel leaderPanel;
	DepartmentService departmentService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tid = (Long) centerTabbox.getAttribute("tid");
		leaderTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem arg0, Object arg1) throws Exception {
				if (arg1 instanceof WkTTitle) {
					WkTTitle title = (WkTTitle) arg1;
					arg0.setValue(title);
					arg0.setLabel(title.getKtName());
					/**
					 * arg0.setVisible(false); Treeitem pitem=arg0.getParentItem(); if(pitem.getValue() instanceof XyUserrole){ arg0.setVisible(true); }else{ WkTTitle ptitle=(WkTTitle) pitem.getValue(); XyUserrole rootRole=getRootRole(pitem); List ctlist=titleService.getTitleOfRoleAccess(ptitle.getKtId(), rootRole.getId().getKrId()); for(int i=0;i<ctlist.size();i++){ WkTTitle t=(WkTTitle)ctlist.get(i); if(t.getKtId().intValue()==title.getKtId().intValue()){ arg0.setVisible(true); } } }
					 **/
				} else {
					XyUserrole ur = (XyUserrole) arg1;
					WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, ur.getId().getKrId());
					arg0.setLabel(role.getKrName());
					arg0.setValue(ur);
					arg0.setOpen(true);
				}
			}
		});
		leaderTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem item = leaderTree.getSelectedItem();
				if (item.getTreechildren() != null)
					return;
				if (item.getValue() instanceof WkTTitle) {
					WkTTitle t = (WkTTitle) item.getValue();
					HumanBaseWindow bwin = (HumanBaseWindow) creatTab("leader", "人事管理", t.getKtContent().substring(0, t.getKtContent().indexOf("?")), leaderPanel, true);
					XyUserrole ur = (XyUserrole) item.getParentItem().getValue();
					bwin.setXyUserRole(ur);
					String rid = t.getKtContent().substring(t.getKtContent().indexOf("?") + 1, t.getKtContent().indexOf("&"));
					bwin.setRole((WkTRole) xyUserRoleService.get(WkTRole.class, Long.parseLong(rid)));
					String leave = t.getKtContent().substring(t.getKtContent().indexOf("&") + 1);
					bwin.setLeave(Integer.parseInt(leave));
					bwin.setTitle(t.getKtName());
					bwin.initWindow();
				}
			}
		});
		initLeaderTree();
	}

	private XyUserrole getRootRole(Treeitem item) {
		Treeitem pitem = item.getParentItem();
		if (pitem.getValue() instanceof XyUserrole) {
			return (XyUserrole) pitem.getValue();
		}
		return getRootRole(pitem);
	}

	private void initLeaderTree() {
		List roleList = xyUserRoleService.getUserRoleOfTitle(user.getKuId(), tid);
		RoleTitleTreeModel roleTitleTreeModel = new RoleTitleTreeModel(roleList, titleService, tid);
		leaderTree.setModel(roleTitleTreeModel);
	}
}
