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
	// ���ݰ���
	private AnnotateDataBinder binder;
	// ��Ϣ����
	private Map params;
	// ��Ϣ�б�
	private List msglist;
	// ������Ϣ�б��
	private Listbox sendMsglistbox;
	// �༭��ɾ����Ϣ���
	private Toolbarbutton deleteMsg, editMsg;
	MessageService messageService;
	WkTUser wkTUser;
	boolean group = true;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// �������б�Listbox������
		sendMsglistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				if (data instanceof LongTime) {
					LongTime lt = (LongTime) data;
					item.setLabel(lt.getName() + " (" + lt.getMlist().size() + "��)");
					item.setCheckable(false);
					return;
				}
				final XyMessage message = (XyMessage) data;
				item.setValue(message);
				item.setHeight("25px");
				Listcell c0 = new Listcell("");
				Listcell c1 = new Listcell();
				StringBuffer sb = new StringBuffer(message.getXmReceivers());
				// ���ռ��˵����Ƴ��ȳ���10�ַ�ʱ����ȡǰ10�ַ�������������ʱ����ʾȫ��
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
				str = ConvertUtil.htmlTotxt(str); // ���ú���������FCK�༭����ʽ����������
				int len = str.trim().length();
				if (len > 20) {
					String str1 = ""; // ��Ϣ���ݳ���С��20�ַ���ȫ����ʾ�������ȡǰ20�ַ���ʾ
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
		// ɾ����Ϣ����
		deleteMsg.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				if (sendMsglistbox.getSelectedItem() == null) {
					Messagebox.show("����ѡ��Ҫɾ������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					return;
				} else {
					Set sel = sendMsglistbox.getSelectedItems();
					List deleteList = new ArrayList();
					Iterator it = sel.iterator();
					if (Messagebox.show("ȷ��Ҫɾ������Ϣ��", "��ʾ", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
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
	 * <li>������������������ void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		// ���ݵ�¼ʱ���û�������MsgTList������֮��ͬ�ߣ���Ϊ���û��ѷ���Ϣ
		GroupsModel gmodel = new NoticeGroupsModel(wkTUser, messageService, NoticeGroupsModel.TYPE_M_SEND);
		sendMsglistbox.setModel(gmodel);
	}
}
