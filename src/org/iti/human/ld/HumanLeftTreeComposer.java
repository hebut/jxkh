package org.iti.human.ld;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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

/**
 * @author DaLei
 * @version $Id: HumanLeftTreeComposer.java,v 1.1 2011/08/31 07:03:00 ljb Exp $
 */
public class HumanLeftTreeComposer extends BaseLeftTreeComposer {

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
	WkTDept school;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		user = (WkTUser) session.getAttribute("user");
		tid = (Long) centerTabbox.getAttribute("tid");
		schlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(arg1);
				if (!("".equals(getProOfCookie(user.getKuId() + "school")) || getProOfCookie(user.getKuId() + "school") == null)) {
					Long kdid = Long.parseLong(getProOfCookie(user.getKuId() + "school"));
					school = (WkTDept) departmentService.get(WkTDept.class, kdid);
					if (school.getKdId().longValue() == dept.getKdId().longValue()) {
						arg0.setSelected(true);
					}
				}
			}
		});
		schlist.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				WkTDept dept = (WkTDept) schlist.getSelectedItem().getValue();
				Long kdid = dept.getKdId();
				setProOfCookie(user.getKuId() + "school", kdid + "");
				initHumanTree(dept.getKdId());
				checkTitle();
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
				if (item.getValue() instanceof WkTRole) {
					WkTRole r = (WkTRole) item.getValue();
					String url = "/admin/human/";
					XyUserrole ur = (XyUserrole) item.getParentItem().getValue();
					if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_LD) {
						url = url + "ld/main.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_JS) {
						url = url + "teacher/index.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XS) {
						url = url + "student/index.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_FDY) {
						url = url + "fdy/index.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_DD) {
						url = url + "dudao/index.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_YJS) {
						url = url + "yjs/index.zul";
					} else if (r.getKrArgs(WkTRole.INDEX_TYPE) == WkTRole.TYPE_XK) {
						url = url + "xk/index.zul";
					}
					HumanBaseWindow bwin = (HumanBaseWindow) creatTab("leader", "人事管理", url, humanPanel, true);
					bwin.setXyUserRole(ur);
					bwin.setRole(r);
					bwin.setLeave(Integer.parseInt(String.valueOf(r.getKrArgs(WkTRole.INDEX_GRADE))));
					bwin.setTitle(item.getLabel());
					bwin.initWindow();
				}
			}
		});
		// 初始化学校选择下列列表
		initdeList();
		if (schlist.getModel().getSize() == 0) {
			return;
		} else {
			String str = getProOfCookie(user.getKuId() + "school");
			if ("".equals(str) || str == null) {
				// 没有选择过毕设过程那么默认为第一个
				school = (WkTDept) schlist.getModel().getElementAt(0);
			} else {
				Long kdid = Long.parseLong(str);
				school = (WkTDept) departmentService.get(WkTDept.class, kdid);
			}
		}
		initHumanTree(school.getKdId());
		checkTitle();
	}

	private void initHumanTree(Long kdid) {
		List roleList = xyUserRoleService.getUserRole(user.getKuId(), kdid);
		if (roleList.size() == 0 && user.getKdId().longValue() == 0L) {
			// 如果是超级管理员
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
			dlist = departmentService.getChildDepartment(0L, WkTDept.QUANBU);
		}
		schlist.setModel(new ListModelList(dlist));
	}

	private void checkTitle() {
		List tlist = humanTree.getTreechildren().getChildren();
		for (int i = 0; i < tlist.size(); i++) {
			Treeitem item = (Treeitem) tlist.get(i);
			if (item.getTreechildren() == null || item.getTreechildren().getChildren() == null || item.getTreechildren().getChildren().size() == 0) {
				item.setVisible(false);
			} else {
				item.setOpen(true);
			}
		}
	}

	/**
	 * <li>功能描述：获得cookie中某属性的值。
	 * 
	 * @param pname
	 * @return String
	 * @author DATIAN
	 */
	public String getProOfCookie(String pname) {
		Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (pname.equals(cookies[i].getName())) {
					String fs = cookies[i].getValue();
					return fs;
				}
			}
		}
		return "";
	}

	/**
	 * <li>功能描述：向cookie中写入值。
	 * 
	 * @param pname
	 * @param fs
	 * @author DATIAN
	 * @2010-3-24
	 */
	public void setProOfCookie(String pname, String fs) {
		Cookie cookie = new Cookie(pname, fs);
		cookie.setMaxAge(60 * 60 * 24 * 30);// store 30 days
		String cp = Executions.getCurrent().getContextPath();
		cookie.setPath(cp);
		((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookie);
	}
}
