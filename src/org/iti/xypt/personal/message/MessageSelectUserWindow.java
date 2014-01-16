package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

public class MessageSelectUserWindow extends Window implements AfterCompose {
	MessageAllUserWindow allUserSelect;
//	MessageGroupWindow groupUserSelect;
	MessageLastUserWindow lastUserSelect;
//	MessageBysjSelectWindow bysjUserSelect;
	List selectUsers = new ArrayList();
	Tab allUserSelectTab, myGroupSelectTab, lastUserSelectTab, bysjUserSelectTab;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		allUserSelect = (MessageAllUserWindow) Executions.createComponents("/admin/personal/message/new/allUserSelect.zul", allUserSelectTab.getLinkedPanel(), null);
//		groupUserSelect = (MessageGroupWindow) Executions.createComponents("/admin/personal/message/new/groupUserSelect.zul", myGroupSelectTab.getLinkedPanel(), null);
		lastUserSelect = (MessageLastUserWindow) Executions.createComponents("/admin/personal/message/new/lastUserSelect.zul", lastUserSelectTab.getLinkedPanel(), null);
//		bysjUserSelect = (MessageBysjSelectWindow) Executions.createComponents("/admin/personal/message/new/bysjUserSelect.zul", bysjUserSelectTab.getLinkedPanel(), null);
		allUserSelect.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				selectUsers = allUserSelect.getSelectUsers();
				sendEvents();
			}
		});
//		groupUserSelect.addEventListener(Events.ON_CHANGE, new EventListener() {
//			public void onEvent(Event arg0) throws Exception {
//				selectUsers = groupUserSelect.getSelectUser();
//				sendEvents();
//			}
//		});
		lastUserSelect.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				selectUsers = lastUserSelect.getSelectUser();
				sendEvents();
			}
		});
//		bysjUserSelect.addEventListener(Events.ON_CHANGE, new EventListener() {
//			public void onEvent(Event arg0) throws Exception {
//				selectUsers = bysjUserSelect.getSelectUser();
//				sendEvents();
//			}
//		});
	}

	public void initWindow(List recelist) {
		if (recelist != null) {
			allUserSelect.initWindow(recelist);
//			groupUserSelect.initWindow(recelist);
			lastUserSelect.initWindow(recelist);
//			bysjUserSelect.initWindow(recelist);
//			if (!bysjUserSelect.isBsTeacher()) {
//				bysjUserSelectTab.getLinkedPanel().setVisible(false);
//				bysjUserSelectTab.setVisible(false);
//			}
		}
	}

	private void sendEvents() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public List getSelectUsers() {
		return selectUsers;
	}
}
