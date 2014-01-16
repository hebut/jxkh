package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.xypt.service.MessageService;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;

public class MessageLastUserWindow extends MessageUserSelectWindow {
	Listbox userList;
	Button submit, reset;
	List selectUsers;
	WkTUser wkTUser;
	MessageService messageService;
	List selectUserId = new ArrayList();

	public void initWindow(List receList) {
		selectUsers = receList;
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		for (int i = 0; i < receList.size(); i++) {
			WkTUser u = (WkTUser) receList.get(i);
			selectUserId.add(u.getKuId());
		}
		List mlist = messageService.findLastMessage(wkTUser.getKuId());
		userList.setModel(new ListModelList(mlist));
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
				for (int i = 0; i < selectUserId.size(); i++) {
					Long u = (Long) selectUserId.get(i);
					if (u == user.getKuId().longValue())
						arg0.setSelected(true);
				}
			}
		});
	}

	public void onClick$submit() {
		selectUsers.clear();
		Set isets = userList.getSelectedItems();
		Iterator ite = isets.iterator();
		while (ite.hasNext()) {
			Listitem item = (Listitem) ite.next();
			WkTUser u = (WkTUser) item.getValue();
			selectUsers.add(item.getValue());
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$reset() {
		userList.setModel(new ListModelList(new ArrayList()));
	}
}
