package org.iti.xypt.personal.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.entity.BsPhase;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.service.PhaseService;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyUserRoleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;

public class MessageReceivePanel extends Panel implements AfterCompose {
	// 已收到消息列表框
	private Listbox messagelistbox;
	// 删除消息按钮组件
	MessageService messageService;
	PhaseService bsphaseService;
	WkTUser wkTUser;
	Map nreadMap = new HashMap();
	boolean newNotice = false;
	Toolbarbutton allMessage;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		messagelistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final Listcell c1 = new Listcell();
				Listcell c2 = new Listcell();
				Listcell c3 = new Listcell();
				Listcell c4 = new Listcell();
				if (data instanceof XyMessage) {
					final XyMessage message = (XyMessage) data;
					item.setValue(message);
					item.setHeight("25px");
					if (nreadMap.get(message) != null) {
						c1.setImage("/images/ims.news.gif");
					} else {
						c1.setImage("/images/ims.readed.gif");
					}
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
					c2.setLabel(str);
					c2.setStyle("color:blue");
					InnerButton inb = new InnerButton();
					String str0 = null;
					if (message.getXmSubject() == null) {
						str0 = "";
					} else {
						str0 = message.getXmSubject().trim();
						int len = str0.trim().length();
						if (len > 14) {
							str0 = str0.substring(0, 13) + "...";
						} else {
							str0 = message.getXmSubject();
						}
					}
					inb.setLabel(str0);
					c3.appendChild(inb);
					inb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							MessageViewWindow nvw = (MessageViewWindow) Executions.createComponents("/admin/personal/message/receive/view.zul", null, null);
							nvw.doHighlighted();
							nvw.initWindow(message);
							c1.setImage("/images/ims.readed.gif");
						}
					});
					c4.setLabel(ConvertUtil.convertYRString(message.getXmSndtime()));
					item.appendChild(c1);
					item.appendChild(c2);
					item.appendChild(c3);
					item.appendChild(c4);
				}else{
					Listcell c8=new Listcell(data.toString());
					c8.setSpan(4);
					c8.setStyle("color:red");
					item.appendChild(c8);
				}
				
			}
		});
		reloadGrid();
	}

	/**
	 * <li>功能描述：和数据加载器 void
	 */
	public void reloadGrid() {
		Date now=new Date();
		Long time=now.getTime();
//		String mes=null;
		List mlist=new ArrayList();
//		List listphase;
//		listphase=bsphaseService.findByKuidAndTime(wkTUser.getKuId(),time);
//		if(listphase.size()==0){
//			listphase=bsphaseService.findByKuidAndTime2(wkTUser.getKuId(),time);
//			if(listphase.size()==0){
//				listphase=bsphaseService.findByKuidAndTime3(wkTUser.getKuId(),time);;
//				if(listphase.size()==0){
//					mes="当前不处于任何阶段";
//				}else{
//					BsPhase phase=(BsPhase) listphase.get(0);
//					mes="当前正处于"+phase.getBpName()+":"+ConvertUtil.convertString(new Date(phase.getBpStart()))+"--"+ConvertUtil.convertString(new Date(phase.getBpEnd()));
//				}
//			}else{
//				BsPhase phase=(BsPhase) listphase.get(0);
//				mes="当前正处于"+phase.getBpName()+":"+ConvertUtil.convertString(new Date(phase.getBpStart()))+"--"+ConvertUtil.convertString(new Date(phase.getBpEnd()));
//			
//			}
//		}else{
//			BsPhase phase=(BsPhase) listphase.get(0);
//			mes="当前正处于"+phase.getBpName()+":"+ConvertUtil.convertString(new Date(phase.getBpStart()))+"--"+ConvertUtil.convertString(new Date(phase.getBpEnd()));
//		}
//		mlist.add(mes);
		newNotice = false;
		List list = messageService.findUserMessage(wkTUser.getKuId());
		mlist.addAll(list);
		if (mlist.size() > 8) {
			mlist = mlist.subList(0, 8);
		}
		for (int i = 0; i < mlist.size(); i++) {
			if (mlist.get(i) instanceof XyMessage) {
				XyMessage message = (XyMessage) mlist.get(i);
				XyMReceiverId xymId = new XyMReceiverId(message.getXmId(), wkTUser.getKuId());
				XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
				if (xym == null || xym.getXmrState().shortValue() == XyMReceiver.STATE_UNREAD.shortValue()) {
					nreadMap.put(message, "yes");
					newNotice = true;
				}
			}
			
		}
		messagelistbox.setModel(new ListModelList(mlist));
	}

	public void onClick$allMessage() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public boolean isNewNotice() {
		return newNotice;
	}

	public void setNewNotice(boolean newNotice) {
		this.newNotice = newNotice;
	}
}
