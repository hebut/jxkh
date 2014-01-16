package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.common.listbox.DeptListbox;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

public class MessageAllUserWindow extends MessageUserSelectWindow {
	Listbox userList, userListSelected;
	ListModelList userListModel, userListSelectedModel;
	Textbox trueName;
	UserService userService;
	List selectUsers;
	List selectUserId = new ArrayList();
	Checkbox teacherCheck, studentCheck, graduateCheck;
	DeptListbox deptSelect;
	DepartmentService departmentService;

	public void onClick$choose() {
		Set isets = userList.getSelectedItems();
		Iterator ite = isets.iterator();
		List hlist = new ArrayList();
		while (ite.hasNext()) {
			Listitem item = (Listitem) ite.next();
			WkTUser u = (WkTUser) item.getValue();
			if (!selectUserId.contains(u.getKuId())) {
				hlist.add(item.getValue());
			}
		}
		userListModel.removeAll(hlist);
		userListSelectedModel.addAll(hlist);
	}

	public void onClick$remove() {
		Set isets = userListSelected.getSelectedItems();
		Iterator ite = isets.iterator();
		List hlist = new ArrayList();
		while (ite.hasNext()) {
			Listitem item = (Listitem) ite.next();
			WkTUser u = (WkTUser) item.getValue();
			selectUserId.remove(u.getKuId());
			hlist.add(item.getValue());
		}
		userListSelectedModel.removeAll(hlist);
		userListModel.addAll(hlist);
	}

	public void onClick$reset() {
		initWindow(getSelectUsers());
	}

//	private void reloadList() {
//		userList.setModel(new ListModelList(getSelectUsers()));
//	}

	/**
	 * 初始化窗口，需要已经选择的用户列表
	 * 
	 * @param receList
	 */
	public void initWindow(List receList) {
		setSelectUsers(receList);
		userListModel = new ListModelList(new ArrayList<WkTUser>());
		userListSelectedModel = new ListModelList(receList);
		for (int i = 0; i < receList.size(); i++) {
			WkTUser u = (WkTUser) receList.get(i);
			selectUserId.add(u.getKuId());
		}
		userList.setModel(userListModel);
		userListSelected.setModel(userListSelectedModel);
	}

	public void onClick$search() {
		userListModel.clear();
		WkTDept dept = (WkTDept) deptSelect.getSelectedItem().getValue();
		List userlist = userService.findUserForGroupByKdIdAndName(dept.getKdId(), trueName.getValue(), teacherCheck.isChecked(), studentCheck.isChecked(), graduateCheck.isChecked());
		userListModel.addAll(userlist);
	}

//	private void addChildDept(List dlist, WkTDept dept) {
//		dlist.add(dept);
//		List clist = departmentService.getChildDepartment(dept.getKdId(), WkTDept.QUANBU);
//		if (clist != null && clist.size() > 0) {
//			for (int i = 0; i < clist.size(); i++) {
//				WkTDept d = (WkTDept) clist.get(i);
//				addChildDept(dlist, d);
//			}
//		}
//	}

	public void onClick$submit() {
		selectUsers = userListSelectedModel.getInnerList();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectUsers() {
		return selectUsers;
	}

	public void setSelectUsers(List selectUsers) {
		this.selectUsers = selectUsers;
	}

	public List<WkTUser> getSelectUser() {
		return userListSelectedModel.getInnerList();
	}

	public void initShow() {
		userList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, final Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTUser u = (WkTUser) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(u.getKuName());
				Listcell c2 = new Listcell(u.getKuSex().trim().equalsIgnoreCase(WkTUser.SEX_MAN) ? "男" : "女");
				Listcell c3 = new Listcell(u.getDept() == null ? "" : u.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);// arg0.appendChild(c4);
			}
		});
		userListSelected.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, final Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTUser u = (WkTUser) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(u.getKuName());
				Listcell c2 = new Listcell(u.getKuSex().trim().equalsIgnoreCase(WkTUser.SEX_MAN) ? "男" : "女");
				Listcell c3 = new Listcell(u.getDept() == null ? "" : u.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);// arg0.appendChild(c4);
			}
		});
		WkTUser user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		deptSelect.setRootDept(null);
		deptSelect.setRootID(0L);
		deptSelect.initNewDeptSelect(user.getDept());
	}
}
