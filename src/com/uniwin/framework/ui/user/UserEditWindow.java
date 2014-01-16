package com.uniwin.framework.ui.user;

/**
 * <li>用户列表，根据选择的组织机构不同自动更新列表中用户,对应的页面admin/system/user/user.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;

public class UserEditWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 4566946447445706695L;
	/**
	 * 列表中用户所属的组织机构
	 */
	private WkTDept dept;
	// 用户数据访问接口
	UserService userService;
	// 用户列表框
	Listbox userListbox;
	// 用户列表框数据模型
	ListModelList userList;
	Button serchUser, addUser, deleteUser;
	// 搜索输入框
	Textbox serchkey;
	// 当前登陆用户
	WkTUser user;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userListbox.setItemRenderer(new UserDetailListRenderer());
		addUser.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				final UserNewWindow w = (UserNewWindow) Executions.createComponents("/admin/system/user/userNew.zul", null, null);
				w.doHighlighted();
				w.setClosable(true);
				w.initWindow(getDept());
				w.addEventListener(Events.ON_CHANGE, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						reloadDeptTree();
						w.detach();
					}
				});
			}
		});
	}

	/**
	 * <li>功能描述：搜索事件处理函数。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$serchUser() {
		initWindow(getDept());
	}

	/**
	 * <li>功能描述：删除事件处理函数
	 * 
	 * @throws InterruptedException void
	 * @author DaLei
	 */
	@SuppressWarnings("unchecked")
	public void onClick$deleteUser() throws InterruptedException {
		if (Messagebox.show("您确定要删除吗?", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			Set<Listitem> itset = userListbox.getSelectedItems();
			Iterator<Listitem> ite = itset.iterator();
			List<WkTUser> dlist = new ArrayList<WkTUser>();
			while (ite.hasNext()) {
				Listitem it = (Listitem) ite.next();
				dlist.add((WkTUser) it.getValue());
			}
			for (int i = 0; i < dlist.size(); i++) {
				WkTUser u = (WkTUser) dlist.get(i);
				if (u.getKuId().intValue() != user.getKuId().intValue()) {
					mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除用户，id:" + u.getKuId(), user);
					userService.deleteUser(u);
				}
			}
			reloadDeptTree();
		}
	}

	public WkTDept getDept() {
		return dept;
	}

	/**
	 * <li>功能描述：初始化页面
	 * 
	 * @param dept
	 *            void
	 * @author DaLei
	 */
	public void initWindow(WkTDept dept) {
		this.dept = dept;
		this.setTitle("编辑部门用户：" + dept.getKdName());
		reloadList();
	}

	/**
	 * <li>功能描述：重新加载用户列表 void
	 * 
	 * @author DaLei
	 * @2010-3-17
	 */
	private void reloadList() {
		List<WkTUser> ulist;
		if (serchkey.getValue() == null || serchkey.getValue().length() == 0) {
			ulist = userService.getUsersOfDept(this.dept.getKdId());
		} else {
			ulist = userService.getUsersOfDept(this.dept.getKdId(), serchkey.getValue());
		}
		userList = new ListModelList();
		userList.addAll(ulist);
		userListbox.setModel(userList);
	}

	private void reloadDeptTree() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	/**
	 * <li>功能描述:定义列表中数据显示样式
	 * 
	 * @author DaLei
	 * @date 2010-4-12
	 */
	class UserDetailListRenderer implements ListitemRenderer {
		public void render(Listitem item, Object data) throws Exception {
			WkTUser u = (WkTUser) data;
			Integer status = Integer.parseInt(u.getKuStatus());
			String[] statsString = { "未审查", "正常", "查封" };
			item.setValue(u);
			item.setHeight("25px");
			Listcell c0 = new Listcell(item.getIndex() + 1 + "");
			Listcell c1 = new Listcell(u.getKuLid());
			Listcell c2 = new Listcell(u.getKuName());
			Listcell c3 = new Listcell(u.getKuEmail());
			Listcell c4 = new Listcell(statsString[status]);
			Listcell c5 = new Listcell(DateUtil.getDateString(u.getKuRegdate()));
			Listcell c6 = new Listcell(DateUtil.getTimeString(u.getKuLtime()));
			Button b = new Button("角色");
			Listcell c7 = new Listcell();
			c7.appendChild(b);
			b.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem c = (Listitem) event.getTarget().getParent().getParent();
					WkTUser u = (WkTUser) c.getValue();
					final UserRoleSelectWindow w = (UserRoleSelectWindow) Executions.createComponents("/admin/system/user/userRoleSelect.zul", null, null);
					w.doHighlighted();
					w.initWindow(u);
				}
			});
			c7.setTooltiptext("点击进行角色管理");
			c1.addEventListener(Events.ON_CLICK, new InnerListener());
			c2.addEventListener(Events.ON_CLICK, new InnerListener());
			c1.setTooltiptext("点击进行用户编辑");
			c2.setTooltiptext("点击进行用户编辑");
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
		}
	}

	class InnerListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			Listitem c = (Listitem) event.getTarget().getParent();
			WkTUser u = (WkTUser) c.getValue();
			Executions.getCurrent().setAttribute("editUser", u);
			final UserDetialEditWindow w = (UserDetialEditWindow) Executions.createComponents("/admin/system/user/userDetail.zul", null, null);			
			w.setClosable(true);
			w.doHighlighted();
			w.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					reloadDeptTree();
					w.detach();
				}
			});			
		}
	}
}
