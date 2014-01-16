package com.uniwin.framework.ui.role;

/**
 * <li>��ɫ�û������ڣ���Ӧ�Ľ���ҳ��Ϊadmin/system/role/roleUsers.zul
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
	 * leftΪ���δ���н�ɫ���û��б���� rightΪ�Ҳ�ľ��иĽ�ɫ���û��б����
	 */
	Listbox left, right;
	UserService userService;
	ListModelList leftModel, rightModel;
	/**
	 * ��ǰ�Ľ�ɫ
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
	 * <li>������������ʼ���û�ѡ�񴰿ڡ�
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
	 * <li>��������������2������������ void
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
		StringBuffer sb = new StringBuffer("�༭��ɫ�û�,id:" + role.getKrId() + " �û�:");
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