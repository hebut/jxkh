package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.personal.notice.LongTime;
import org.iti.xypt.personal.notice.NoticeGroupsModel;
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
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class MessageReceiveWindow extends Window implements AfterCompose {
	// 已收到消息列表框
	private Listbox receMsglistbox;
	// 删除消息按钮组件
	MessageService messageService;
	WkTUser wkTUser;
	boolean group = true;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		receMsglistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				if (data instanceof LongTime) {
					LongTime lt = (LongTime) data;
					item.setLabel(lt.getName() + " (" + lt.getMlist().size() + "个)");
					item.setCheckable(false);
					return;
				}
				final XyMessage message = (XyMessage) data;
				item.setValue(message);
				item.setHeight("25px");
				Listcell c = new Listcell("");
				final Listcell c1 = new Listcell();
				XyMReceiverId xymId = new XyMReceiverId(message.getXmId(), wkTUser.getKuId());
				XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
				if (xym != null && xym.getXmrState().shortValue() == XyMReceiver.STATE_UNREAD.shortValue()) {
					c1.setImage("/images/ims.news.gif");
				} else {
					c1.setImage("/images/ims.readed.gif");
				}
				Listcell c3 = new Listcell(message.getXmSender());
				Listcell c4 = new Listcell();
				InnerButton inb = new InnerButton();
				inb.setLabel(message.getXmSubject());
				c4.appendChild(inb);
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						MessageViewWindow nvw = (MessageViewWindow) Executions.createComponents("/admin/personal/message/receive/view.zul", null, null);
						nvw.doHighlighted();
						nvw.initWindow(message);
						c1.setImage("/images/ims.readed.gif");
					}
				});
				Label lb = new Label();
				String str = message.getXmContent();
				str = ConvertUtil.htmlTotxt(str); // 调用函数，过滤FCK编辑的样式，留下内容
				int len = str.trim().length();
				if (len > 20) {
					String str1 = ""; // 消息内容长度小于20字符，全部显示，否则截取前20字符显示
					str1 = str.substring(0, 20);
					lb.setValue(" " + str1);
				} else {
					lb.setValue(" " + str);
				}
				lb.setStyle("color:#888888");
				c4.appendChild(lb);
				Listcell c_keyword = new Listcell(message.getXmKeyword());
				Listcell c6 = new Listcell(ConvertUtil.convertYRString(message.getXmSndtime()));
				item.appendChild(c);
				item.appendChild(c1);
				item.appendChild(c4);
				item.appendChild(c_keyword);
				item.appendChild(c3);
				item.appendChild(c6);
			}
		});
		reloadGrid();
	}

	public void onSort$titleHeader() {
		if (group) {
			group = false;
			sortlist();
		}
	}

	public void onSort$keywordHeader() {
		if (group) {
			group = false;
			sortlist();
		}
	}

	public void onSort$fromHeader() {
		if (group) {
			group = false;
			sortlist();
		}
	}

	public void onSort$timeHeader() {
		if (group) {
			group = false;
			sortlist();
		}
	}

	private void sortlist() {
		List msglist = messageService.findUserMessage(wkTUser.getKuId());
		receMsglistbox.setModel(new ListModelList(msglist));
	}

	public void onClick$deleteMsg() throws InterruptedException {
		if (receMsglistbox.getSelectedItem() == null) {
			Messagebox.show("请您选择要删除的消息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			return;
		} else {
			Set sel = receMsglistbox.getSelectedItems();
			List deleteList = new ArrayList();
			List sldel = new ArrayList();
			Iterator it = sel.iterator();
			// 用于多选删除提示
			if (Messagebox.show("确定要删除此消息吗？", "提示", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				while (it.hasNext()) {
					Listitem item = (Listitem) it.next();
					deleteList.add(item.getValue());
				}
				for (int i = 0; i < deleteList.size(); i++) {
					XyMessage me = (XyMessage) deleteList.get(i);
					messageService.deleteMessage(me, wkTUser.getKuId());
				}
				if (group) {
					reloadGrid();
				} else {
					sortlist();
				}
			}
		}
	}

	public void onClick$replyMsg() {
		Set set = receMsglistbox.getSelectedItems();
		Iterator it = set.iterator();
		List userList = new ArrayList();
		Boolean flag;
		while (it.hasNext()) {
			flag = false;
			Listitem item = (Listitem) it.next();
			XyMessage message = (XyMessage) item.getValue();
			if (message == null)
				continue;
			WkTUser user = (WkTUser) messageService.get(WkTUser.class, message.getKuId());
			for (int i = 0; i < userList.size(); i++) {
				WkTUser u = (WkTUser) userList.get(i);
				if (u.getKuId().longValue() == user.getKuId().longValue()) {
					flag = true;
				}
			}
			if (!flag)
				userList.add(user);
		}
		MessageNewWindow win = (MessageNewWindow) Executions.createComponents("/admin/personal/message/new/index.zul", null, null);
		win.addReceiver(userList);
		win.doHighlighted();
		win.setWidth("800px");
		win.setBorder("normal");
		win.setTitle("回复消息");
		win.setClosable(true);
	}

	/**
	 * <li>功能描述：和数据加载器 void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		GroupsModel gmodel = new NoticeGroupsModel(wkTUser, messageService, NoticeGroupsModel.TYPE_M_RECEIVER);
		receMsglistbox.setModel(gmodel);
	}
}
