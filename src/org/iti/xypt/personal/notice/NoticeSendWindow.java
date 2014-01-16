package org.iti.xypt.personal.notice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
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
import com.uniwin.framework.service.UserService;

public class NoticeSendWindow extends Window implements AfterCompose {
	private UserService userService;
	// ���ݰ���
	private AnnotateDataBinder binder;
	// ��Ϣ����
	private Map params;
	// ��Ϣ�б�
	private List msglist;
	// ������Ϣ�б��
	private Listbox noticeList;
	MessageService messageService;
	WkTUser wkTUser;
	boolean group = true;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// �������б�Listbox������
		noticeList.setItemRenderer(new ListitemRenderer() {
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
				// Listcell c_index=new Listcell(item.getIndex()+1+"");
				Listcell c_readstate = new Listcell();
				Listcell c_subject = new Listcell();
				InnerButton inb = new InnerButton();
				inb.setLabel(message.getXmSubject());
				c_subject.appendChild(inb);
				inb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						NoticeViewWindow nvw = (NoticeViewWindow) Executions.createComponents("/admin/personal/notice/receive/view.zul", null, null);
						nvw.doHighlighted();
						nvw.initWindow(message);
					}
				});
				Label lb = new Label();
				String str = message.getXmContent();
				str = ConvertUtil.htmlTotxt(str); // ���ú���������FCK�༭����ʽ����������
				int len = str.trim().length();
				if (len > 20) {
					String str1 = ""; // ��Ϣ���ݳ���С��20�ַ���ȫ����ʾ�������ȡǰ20�ַ���ʾ
					str1 = str.substring(0, 20);
					lb.setValue(" -" + str1);
				} else {
					lb.setValue(" -" + str);
				}
				lb.setStyle("color:#888888");
				c_subject.appendChild(lb);
				c_subject.setStyle("padding-left: 12px;");
				Listcell c_keyword = new Listcell(message.getXmKeyword());
				Listcell c_receiver = new Listcell(message.getXmReceivers());
				Listcell c_time = new Listcell(ConvertUtil.convertYRString(message.getXmSndtime()));
				item.appendChild(c_readstate);
				item.appendChild(c_subject);
				item.appendChild(c_keyword);
				item.appendChild(c_receiver);
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
		List msglist = messageService.findNoticeByKuid(wkTUser.getKuId());
		noticeList.setModel(new ListModelList(msglist));
	}

	public void onClick$deleteNotice() throws InterruptedException {
		if (noticeList.getSelectedItem() == null) {
			Messagebox.show("����ѡ��Ҫɾ������Ϣ��", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			return;
		} else {
			Set sel = noticeList.getSelectedItems();
			List deleteList = new ArrayList();
			Iterator it = sel.iterator();
			// ���ڶ�ѡɾ����ʾ
			if (Messagebox.show("ȷ��Ҫɾ������Ϣ��", "��ʾ", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				while (it.hasNext()) {
					Listitem item = (Listitem) it.next();
					deleteList.add(item.getValue());
				}
				for (int i = 0; i < deleteList.size(); i++) {
					XyMessage me = (XyMessage) deleteList.get(i);
					messageService.deleteNotice(me);
				}
				if (group) {
					reloadGrid();
				} else {
					sortlist();
				}
			}
		}
	}

	/**
	 * <li>����������ת����ҳ��׫д����Ϣ void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void onClick$editMsg() {
		if (noticeList.getSelectedItem() == null || noticeList.getSelectedItem().getValue() == null) {
			return;
		}
		final NoticeEditWindow win = (NoticeEditWindow) Executions.createComponents("/admin/personal/notice/send/edit.zul", null, null);
		win.doHighlighted();
		win.initWindow((XyMessage) noticeList.getSelectedItem().getValue());
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				if (group) {
					reloadGrid();
				} else {
					sortlist();
				}
				win.detach();
			}
		});
	}

	/**
	 * <li>������������������ void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {
		// ���ݵ�¼ʱ���û�������MsgTList������֮��ͬ�ߣ���Ϊ���û��ѷ���Ϣ
		// List msglist=messageService.findNoticeByKuid(wkTUser.getKuId());
		// noticeList.setModel(new ListModelList(msglist));
		group = true;
		GroupsModel gmodel = new NoticeGroupsModel(wkTUser, messageService, NoticeGroupsModel.TYPE_N_SEND);
		noticeList.setModel(gmodel);
	}

	/**
	 * <li>������������Ϣ��ʾ����
	 * 
	 * @param String
	 *            message
	 * @author bobo
	 * @2010-3-30
	 */
	public int showMessage(String message) {
		try {
			return Messagebox.show(message, "��Ϣ", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * <li>����������������ʽ��ֻ��������
	 * 
	 * @param String
	 *            s
	 * @author bobo
	 * @2010-4-22
	 */
	public static String htmlTotxt(String s) {
		try {
			for (;;) {
				int i = s.indexOf("<xml>");
				int j = s.indexOf("</xml>");
				String tag = s.substring(i, j + 6);
				s = replacehtml(s, tag, "");
			}
		} catch (Exception e) {
		}
		try {
			for (;;) {
				int i = s.indexOf("<style");
				int j = s.indexOf("</style>");
				String tag = s.substring(i, j + 8);
				s = replacehtml(s, tag, "");
			}
		} catch (Exception e) {
		}
		BufferedReader in = new BufferedReader(new StringReader(s));
		String tag = "";
		int chr;
		try {
			while ((chr = in.read()) != -1) {
				tag = "";
				if (chr == '<') {
					tag = "";
					while ((chr = in.read()) != '>') {
						if (chr == -1) {
							return "html���Ƕ�ײ�������";
						}
						tag = tag + (char) chr;
					}
				}
				s = replacehtml(s, "<" + tag + ">", "");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		s = s.replaceAll("&nbsp;", "");
		return s;
	}

	// ��str�е�����oldStr�滻ΪnewStr
	public static String replacehtml(String str, String oldStr, String newStr) {
		if (str != null) {
			int index = 0;
			int oldLen = oldStr.length(); // oldStr�ַ�������
			int newLen = newStr.length(); // newStr�ַ�������
			while (true) {
				index = str.indexOf(oldStr, index);
				if (index == -1) {
					return str;
				} else {
					str = str.substring(0, index) + newStr + str.substring(index + oldLen);
					index += newLen;
				}
			}
		} else {
			return "";
		}
	}

	public List getMsglist() {
		return msglist;
	}

	public void setMsglist(List msglist) {
		this.msglist = msglist;
	}
}
