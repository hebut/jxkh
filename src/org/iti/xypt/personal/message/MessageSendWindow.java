package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class MessageSendWindow extends Window implements AfterCompose {
	private UserService userService;
	// 数据绑定器
	private AnnotateDataBinder binder;
	// 消息集合
	private Map params;
	// 消息列表
	private List msglist;
	// 发送消息列表框
	private Listbox sendMsglistbox;
	// 编辑和删除消息组件
	private Toolbarbutton deleteMsg, editMsg;
	MessageService messageService;
	WkTUser wkTUser;
	boolean group = true;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// 发件箱列表Listbox监听器
		sendMsglistbox.setItemRenderer(new ListitemRenderer() {
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
				Listcell c0 = new Listcell("");
				Listcell c1 = new Listcell();
				StringBuffer sb = new StringBuffer(message.getXmReceivers());
				// 当收件人的名称长度超过10字符时，截取前10字符。当鼠标放其上时，显示全部
				if (sb.length() > 8) {
					String str0 = sb.substring(0, 8);
					c1 = new Listcell(str0 + "..");
				} else {
					c1 = new Listcell(sb.toString());
				}
				c1.setTooltiptext(sb.toString());
				Listcell c2 = new Listcell();
				InnerButton inb = new InnerButton();
				inb.setLabel(message.getXmSubject());
				c2.appendChild(inb);
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
				c2.appendChild(lb);
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						MessageViewWindow nvw = (MessageViewWindow) Executions.createComponents("/admin/personal/message/receive/view.zul", null, null);
						nvw.doHighlighted();
						nvw.initWindow(message);
					}
				});
				Listcell c3 = new Listcell(ConvertUtil.convertYRString(message.getXmSndtime()));
				Listcell c_keyword = new Listcell(message.getXmKeyword());
				item.appendChild(c0);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c_keyword);
				item.appendChild(c3);
			}
		});
		// 删除消息功能
		deleteMsg.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (sendMsglistbox.getSelectedItem() == null) {
					Messagebox.show("请您选择要删除的消息！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				} else {
					Set sel = sendMsglistbox.getSelectedItems();
					List deleteList = new ArrayList();
					Iterator it = sel.iterator();
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

	public void onSort$targetHeader() {
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
		List msglist = messageService.findMessageByKuid(wkTUser.getKuId());
		sendMsglistbox.setModel(new ListModelList(msglist));
	}

	/**
	 * <li>功能描述：加载数据 void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		// 根据登录时的用户，查找MsgTList表中与之相同者，即为该用户已发消息
		GroupsModel gmodel = new NoticeGroupsModel(wkTUser, messageService, NoticeGroupsModel.TYPE_M_SEND);
		sendMsglistbox.setModel(gmodel);
	}
}
