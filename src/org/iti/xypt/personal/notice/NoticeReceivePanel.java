package org.iti.xypt.personal.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Toolbarbutton;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class NoticeReceivePanel extends Panel implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4986477156026276633L;
	// 已收到消息列表框
	private Listbox noticelistbox;
	private Toolbarbutton allNotice;
	MessageService messageService;
	WkTUser wkTUser;
	Tab centerTab;
	Map nreadMap = new HashMap();
	boolean newNotice = false;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		noticelistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final XyMessage message = (XyMessage) data;
				item.setValue(message);
				item.setHeight("25px");
				final Listcell c_index = new Listcell();
				if (nreadMap.get(message) != null) {
					c_index.setImage("/images/ims.news.gif");
				} else {
					c_index.setImage("/images/ims.readed.gif");
				}
				Listcell c_subject = new Listcell();
				InnerButton inb = new InnerButton();
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						NoticeViewWindow nvw = (NoticeViewWindow) Executions.createComponents("/admin/personal/notice/receive/view.zul", null, null);
						nvw.doHighlighted();
						nvw.initWindow(message);
						c_index.setImage("/images/ims.readed.gif");
					}
				});
				String str0 = null;
				if (message.getXmSubject() == null) {
					str0 = "";
				} else {
					str0 = message.getXmSubject();
					int len = str0.trim().length();
					if (len > 14) {
						str0 = str0.substring(0, 13) + "...";
					} else {
						str0 = message.getXmSubject();
					}
				}
				inb.setLabel(str0);
				c_subject.appendChild(inb);
				String str = null;
				if (message.getXmSender() == null) {
					str = "";
				} else {
					str = message.getXmSender();
					int len = str.trim().length();
					if (len > 5) {
						str = str.substring(0, 4) + "...";
					}
				}
				Listcell c_sender = new Listcell(str);
				c_sender.setStyle("color:blue");
				Listcell c_time = new Listcell(ConvertUtil.convertYRString(message.getXmSndtime()));
				item.appendChild(c_index);
				item.appendChild(c_sender);
				item.appendChild(c_subject);
				item.appendChild(c_time);
			}
		});
		reloadGrid();
	}

	/**
	 * <li>功能描述：和数据加载器 void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		newNotice = false;
		messageService.saveUrdByKuid(wkTUser.getKuId());// 正式使用需要注释
		List nlist = null;
		if (wkTUser.getKdId().longValue() == 0L) {
			nlist = messageService.findAllNotice();
		} else {
			nlist = messageService.findUserNotice(wkTUser.getKuId());
		}
		if (nlist.size() > 8) {
			nlist = nlist.subList(0, 8);
		}
		for (int i = 0; i < nlist.size(); i++) {
			XyMessage message = (XyMessage) nlist.get(i);
			XyMReceiverId xymId = new XyMReceiverId(message.getXmId(), wkTUser.getKuId());
			XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
			if (xym == null || xym.getXmrState().shortValue() == XyMReceiver.STATE_UNREAD.shortValue()) {
				nreadMap.put(message, "yes");
				newNotice = true;
			}
		}
		noticelistbox.setModel(new ListModelList(nlist));
	}

	public void onClick$allNotice() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public boolean isNewNotice() {
		return newNotice;
	}

	public void setNewNotice(boolean newNotice) {
		this.newNotice = newNotice;
	}
}
