package org.iti.xypt.personal.notice;

import java.util.List;

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
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class NoticeReceiveWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5996812846888824816L;
	// 已收到消息列表框
	private Listbox noticeList;
	MessageService messageService;
	WkTUser wkTUser;
	boolean group = true;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		noticeList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				if (data instanceof LongTime) {
					LongTime lt = (LongTime) data;
					item.setLabel(lt.getName() + " (" + lt.getMlist().size() + "个)");
					return;
				}
				final XyMessage message = (XyMessage) data;
				item.setValue(message);
				item.setHeight("25px");
				Listcell c_blank = new Listcell();
				final Listcell c_index = new Listcell();
				XyMReceiverId xymId = new XyMReceiverId(message.getXmId(), wkTUser.getKuId());
				XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
				if (xym == null || xym.getXmrState().shortValue() == XyMReceiver.STATE_UNREAD.shortValue()) {
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
				inb.setLabel(message.getXmSubject());
				c_subject.appendChild(inb);
				Label lb = new Label();
				String str = message.getXmContent();
				str = ConvertUtil.htmlTotxt(str); // 调用函数，过滤FCK编辑的样式，留下内容
				int len = str.trim().length();
				if (len > 20) {
					String str1 = ""; // 消息内容长度小于20字符，全部显示，否则截取前20字符显示
					str1 = str.substring(0, 20);
					lb.setValue(" -" + str1);
				} else {
					lb.setValue(" -" + str);
				}
				lb.setStyle("color:#888888");
				c_subject.appendChild(lb);
				c_subject.setStyle("padding-left: 12px;");
				Listcell c_keyword = new Listcell(message.getXmKeyword());
				Listcell c_sender = new Listcell((message.getDept() == null ? "" : message.getDept().getKdName()) + " " + message.getXmSender());
				Listcell c_time = new Listcell(ConvertUtil.convertYRString(message.getXmSndtime()));
				item.appendChild(c_blank);
				item.appendChild(c_index);
				item.appendChild(c_subject);
				item.appendChild(c_keyword);
				item.appendChild(c_sender);
				item.appendChild(c_time);
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
		List msglist = messageService.findUserNotice(wkTUser.getKuId());
		noticeList.setModel(new ListModelList(msglist));
	}

	/**
	 * <li>功能描述：和数据加载器 void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		messageService.saveUrdByKuid(wkTUser.getKuId());// 正式使用需要注释
		GroupsModel gmodel = new NoticeGroupsModel(wkTUser, messageService, NoticeGroupsModel.TYPE_N_RECEIVER);
		noticeList.setModel(gmodel);
	}
}
