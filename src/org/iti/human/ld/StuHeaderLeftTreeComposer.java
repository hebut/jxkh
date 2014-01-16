package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
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
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.TitleService;
import com.uniwin.framework.ui.login.BaseLeftTreeComposer;

public class StuHeaderLeftTreeComposer extends BaseLeftTreeComposer {
	Tree humanTree;
	WkTUser user;
	// 当前三级标题树的父标题编号
	Long tid;
	XyUserRoleService xyUserRoleService;
	TitleService titleService;
	Panel humanPanel;
	Listbox schlist;
	DepartmentService departmentService;
	RoleService roleService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tid = (Long) centerTabbox.getAttribute("tid");
		schlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(arg1);
			}
		});
		schlist.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				WkTDept dept = (WkTDept) schlist.getSelectedItem().getValue();
				initHumanTree(dept.getKdId());
			}
		});
		humanTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem arg0, Object arg1) throws Exception {
				if (arg1 instanceof WkTRole) {
					WkTRole role = (WkTRole) arg1;
					arg0.setValue(role);
					arg0.setLabel("管理" + role.getKrName());
				} else {
					XyUserrole ur = (XyUserrole) arg1;
					WkTRole role = (WkTRole) xyUserRoleService.get(WkTRole.class, ur.getId().getKrId());
					arg0.setLabel(role.getKrName());
					arg0.setValue(ur);
					arg0.setOpen(true);
				}
			}
		});
		humanTree.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Treeitem item = humanTree.getSelectedItem();
				if (item.getTreechildren() != null)
					return;
				List listrole=roleService.findByKdidAndKrname(((WkTDept)schlist.getSelectedItem().getValue()).getKdId(), "辅导员");
				WkTRole role=(WkTRole) listrole.get(0);
				List listxyuserrole=xyUserRoleService.findByKridAndKuid(role.getKrId(), user.getKuId());
				if(listxyuserrole!=null&&listxyuserrole.size()!=0){
					XyUserrole userrole=(XyUserrole) listxyuserrole.get(0);
					WkTDept dept = (WkTDept) departmentService.get(WkTDept.class, userrole.getKdId());
					if (item.getValue() instanceof WkTRole) {
						WkTRole r = (WkTRole) item.getValue();
						StuHeaderManagerWindow w = (StuHeaderManagerWindow) creatTab("stuheader", "管理学生干部", "/admin/human/stuheader/manager.zul", humanPanel, true);
						w.setRootDept(dept);
						w.setUser(user);
						w.setRole(r);
						w.initWindow();
					}
				}
				
			}
		});
		// 初始化学校选择下列列表
		initdeList();
		if (schlist.getModel().getSize() == 0) {
			return;
		} else {
			WkTDept dept = (WkTDept) schlist.getModel().getElementAt(0);
			schlist.setSelectedIndex(0);
			initHumanTree(dept.getKdId());
		}
	}

	private void initHumanTree(Long kdid) {
		List roleList = xyUserRoleService.findFDYRole(user.getKuId(), kdid);
		if (roleList.size() == 0 && user.getKdId().longValue() == 0L) {
			roleList = new ArrayList();
			WkTRole ro = roleService.getShcAmdinRole(kdid);
			if (ro != null) {
				XyUserroleId xyuId = new XyUserroleId(ro.getKrId(), user.getKuId());
				XyUserrole xyu = new XyUserrole(xyuId, kdid);
				roleList.add(xyu);
			}
		}
		HumanRoleTreeModel roleTitleTreeModel = new HumanRoleTreeModel(roleList, xyUserRoleService);
		humanTree.setModel(roleTitleTreeModel);
	}

	private void initdeList() {
		List dlist = departmentService.getRootList(user.getKuId());
		if (dlist.size() == 0 && user.getKdId().longValue() == 0L) {
			dlist = departmentService.getChildDepartment(0L,WkTDept.DANWEI);
		}
		schlist.setModel(new ListModelList(dlist));
	}
}
