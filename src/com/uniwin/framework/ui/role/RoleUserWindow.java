package com.uniwin.framework.ui.role;

/**
 * <li>角色用户管理窗口，对应的解析页面为admin/system/role/roleUsers.zul
 * @author DaLei
 * @2010-3-17
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import com.uniwin.framework.common.combobox.UserCombobox;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;
import com.uniwin.framework.entity.WkTUseroleId;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.UserService;

public class RoleUserWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * left为左侧未具有角色的用户列表组件 right为右侧的具有改角色的用户列表组件
	 */
	Listbox left, right;
	UserService userService;
	ListModelList leftModel, rightModel;
	/**
	 * 当前的角色
	 */
	WkTRole role;
	Button addUser, delUser, clearUser;
	List<Long> userDeptList;
	UserCombobox notUsers, ofUsers;
	MLogService mlogService;
	WkTUser user;

	@SuppressWarnings("unchecked")
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		userDeptList = (List<Long>) Sessions.getCurrent().getAttribute("userDeptList");
		leftModel = new ListModelList();
		rightModel = new ListModelList();
		left.setItemRenderer(new UserListitemRenderer());
		right.setItemRenderer(new UserListitemRenderer());
		addUser.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Set<Listitem> isets = left.getSelectedItems();
				Iterator<Listitem> ite = isets.iterator();
				List<Object> hlist = new ArrayList<Object>();
				while (ite.hasNext()) {
					Listitem item = (Listitem) ite.next();
					hlist.add(item.getValue());
				}
				leftModel.removeAll(hlist);
				rightModel.addAll(hlist);
				loadCombobox();
			}
		});
		delUser.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Set<Listitem> isets = right.getSelectedItems();
				Iterator<Listitem> ite = isets.iterator();
				List<Object> hlist = new ArrayList<Object>();
				while (ite.hasNext()) {
					Listitem item = (Listitem) ite.next();
					hlist.add(item.getValue());
				}
				rightModel.removeAll(hlist);
				leftModel.addAll(hlist);
				loadCombobox();
			}
		});
		clearUser.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				leftModel.addAll(rightModel.getInnerList());
				rightModel.removeAll(rightModel.getInnerList());
				loadCombobox();
			}
		});
	}

	public WkTRole getRole() {
		return role;
	}

	/**
	 * <li>功能描述：初始化用户选择窗口。
	 * 
	 * @param role
	 *            void
	 * @author DaLei
	 * @2010-3-17
	 */
	public void initWindow(WkTRole role) {
		this.role = role;
		leftModel.clear();
		rightModel.clear();
		List<WkTUser> userOfRole = userService.getUsersOfRole(role.getKrId(), userDeptList);
		List<WkTUser> userNotRole = userService.getUsersNotRole(role.getKrId(), userDeptList);
		leftModel.addAll(userNotRole);
		rightModel.addAll(userOfRole);
		left.setModel(leftModel);
		right.setModel(rightModel);
		loadCombobox();
	}

	/**
	 * <li>功能描述：更新2个下拉搜索框。 void
	 * 
	 * @author DaLei
	 */
	private void loadCombobox() {
		notUsers.setUserList(leftModel.getInnerList());
		ofUsers.setUserList(rightModel.getInnerList());
	}

	@SuppressWarnings("unchecked")
	public void onClick$submit() {
		List<WkTUser> userOfRole = userService.getUsersOfRole(role.getKrId(), userDeptList);
		for (int i = 0; i < userOfRole.size(); i++) {
			WkTUser u = (WkTUser) userOfRole.get(i);
			WkTUseroleId urid = new WkTUseroleId(role.getKrId(), u.getKuId());
			WkTUserole ur = (WkTUserole) userService.get(WkTUserole.class, urid);
			if (ur != null)
				userService.delete(ur);
		}
		List<Listitem> urole = right.getItems();
		StringBuffer sb = new StringBuffer("编辑角色用户,id:" + role.getKrId() + " 用户:");
		for (int i = 0; i < urole.size(); i++) {
			Listitem item = (Listitem) urole.get(i);
			WkTUser u = (WkTUser) item.getValue();
			WkTUseroleId urid = new WkTUseroleId(role.getKrId(), u.getKuId());
			WkTUserole ur = new WkTUserole(urid);
			userService.save(ur);
			sb.append(u.getKuId() + ",");
		}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
		this.detach();
	}

	public void onClick$reset() {
		initWindow(role);
	}

	public void onClick$close() {
		this.detach();
	}

	public void onSelect$notUsers() {
		Comboitem citem = notUsers.getSelectedItem();
		if (citem == null)
			return;
		WkTUser u = (WkTUser) citem.getValue();
		//List items = left.getItems();
		setListSelect(left, u.getKuId());
	}

	public void onSelect$ofUsers() {
		Comboitem citem = ofUsers.getSelectedItem();
		if (citem == null)
			return;
		WkTUser u = (WkTUser) citem.getValue();
		//List items = right.getItems();
		setListSelect(right, u.getKuId());
	}

	@SuppressWarnings("unchecked")
	private void setListSelect(Listbox listbox, Long uid) {
		List<Listitem> itemList = listbox.getItems();
		for (int i = 0; i < itemList.size(); i++) {
			Listitem it = (Listitem) itemList.get(i);
			it.setSelected(false);
			WkTUser u = (WkTUser) it.getValue();
			if (u.getKuId().intValue() == uid.intValue()) {
				it.setSelected(true);
			}
		}
	}
}

class UserListitemRenderer implements ListitemRenderer {
	public void render(Listitem item, Object data) throws Exception {
		WkTUser u = (WkTUser) data;
		item.setValue(u);
		Listcell c1 = new Listcell(u.getKuName());
		Listcell c2 = new Listcell(u.getKuLid());
		item.appendChild(c1);
		item.appendChild(c2);
	}
}