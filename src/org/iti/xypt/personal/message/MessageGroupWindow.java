package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.framework.entity.WkTUser;

public class MessageGroupWindow extends MessageUserSelectWindow {
	Listbox groupSelect, userList, userListSelected;
	Button choose, remove, search, submit, reset;
	List selectUsers;
	WkTUser wkTUser;
	MemberService memberService;
	List selectUserId = new ArrayList();
	ListModelList userListModel, userListSelectedModel;

	public void initWindow(List receList) {
		selectUsers = receList;
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		List grouplist = memberService.findGroup(wkTUser.getKuId(), QzMember.ACCEPT_YES, QzMember.AGREE_YES);
		groupSelect.setModel(new ListModelList(grouplist));
		groupSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				QzMember member = (QzMember) arg1;
				QzGroup group = (QzGroup) memberService.get(QzGroup.class, member.getQzId());
				arg0.setLabel(group.getQzName());
				arg0.setValue(group);
			}
		});
		if(groupSelect.getItemCount()!=0)
			groupSelect.getItemAtIndex(0).setSelected(true);
		for (int i = 0; i < receList.size(); i++) {
			WkTUser u = (WkTUser) receList.get(i);
			selectUserId.add(u.getKuId());
		}
		userListModel = new ListModelList(new ArrayList());
		userListSelectedModel = new ListModelList(receList);
		userListSelected.setModel(userListSelectedModel);
	}

	public List<WkTUser> getSelectUser() {
		return selectUsers;
	}

	public void initShow() {
		userList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, final Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(user.getKuSex().trim().equalsIgnoreCase(WkTUser.SEX_MAN) ? "ÄÐ" : "Å®");
				Listcell c3 = new Listcell(user.getDept() == null ? "" : user.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
		userListSelected.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, final Object arg1) throws Exception {
				arg0.setValue(arg1);
				WkTUser user = (WkTUser) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(user.getKuSex().trim().equalsIgnoreCase(WkTUser.SEX_MAN) ? "ÄÐ" : "Å®");
				Listcell c3 = new Listcell(user.getDept() == null ? "" : user.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
	}

	public void onClick$search() {
		QzGroup group = (QzGroup) groupSelect.getSelectedItem().getValue();
		List mlist = memberService.findJoinGroupExceptMyself(group.getQzId(), wkTUser.getKuId(), 2);
		for (int i = 0; i < userListSelectedModel.getInnerList().size(); i++) {
			for (int j = 0; j < mlist.size(); j++) {
				WkTUser u1 = (WkTUser) userListSelectedModel.getInnerList().get(i);
				WkTUser u2 = (WkTUser) mlist.get(j);
				if (u1.getKuId() == u2.getKuId().longValue()) {
					System.out.println(u2.getKuName());
					mlist.remove(j);
				}
			}
		}
		userListModel = new ListModelList(mlist);
		userList.setModel(userListModel);
	}

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
		userList.setModel(userListModel);
		userListSelected.setModel(userListSelectedModel);
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
		userList.setModel(userListModel);
		userListSelected.setModel(userListSelectedModel);
	}

	public void onClick$submit() {
		selectUsers = userListSelectedModel.getInnerList();
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		userListModel = new ListModelList(new ArrayList());
		userListSelectedModel = new ListModelList(selectUsers);
		userList.setModel(userListModel);
		userListSelected.setModel(userListSelectedModel);
		groupSelect.getItemAtIndex(0).setSelected(true);
	}
}
