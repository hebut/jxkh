package com.uniwin.asm.personal.ui.feedback;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.XYFeedBack;
import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class FeedbackWindow extends Window implements AfterCompose {
	// �û�ʵ��
	WkTUser user;
	Listbox fk;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		loadList();
	}

	public void loadList() {
		List list = xyFeedBackService.findByKuid(user.getKuId());
		fk.setModel(new ListModelList(list));
		fk.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final XYFeedBack fback = (XYFeedBack) arg1;
				arg0.setValue(arg1);
				// ���
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// ����ģ��
				Listcell c2 = new Listcell(fback.getTitle().getKtName());
				// �������
				Listcell c3 = new Listcell();
				InnerButton nrButton = new InnerButton();
				nrButton.setLabel(fback.getFbTitle());
				nrButton.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final NewFeedbackWindow fbWin = (NewFeedbackWindow) Executions.createComponents("/admin/feedback/new/new_feedback.zul", null, null);
						if (fback.getFbisRep().shortValue() == XYFeedBack.REP_YES) {
							fbWin.setFeedback(fback);
							fbWin.initWindow1();
						} else {
							fbWin.setFeedback(fback);
						}
						fbWin.initWindow();
						fbWin.doHighlighted();
						fbWin.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								loadList();
								fbWin.detach();
							}
						});
					}
				});
				c3.appendChild(nrButton);
				// ������
				Listcell c4 = new Listcell(user.getKuName());
				// ����ʱ��
				Listcell c5 = new Listcell(ConvertUtil.convertDateAndTimeString(fback.getFbAddtime()));
				// �Ѿ���
				Listcell c6 = new Listcell();
				InnerButton repButton = new InnerButton();
				if (fback.getFbisRep().shortValue() == XYFeedBack.REP_YES) {
					repButton.setLabel("�鿴");
					repButton.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							final FeedBack fbackWin = (FeedBack) Executions.createComponents("/admin/feedback/new/feedback.zul", null, null);
							List list = xyFeedBackService.findByFbid(fback.getFbId());
							if (list.size() != 0) {
								XYFeedBackReply rep = (XYFeedBackReply) list.get(0);
								fbackWin.setReply(rep);
							}
							fbackWin.setUser(user);
							fbackWin.setFeedback(fback);
							fbackWin.initWindow();
							fbackWin.doHighlighted();
							fbackWin.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									fbackWin.detach();
									loadList();
								}
							});
						}
					});
					c6.appendChild(repButton);
				} else {
					c6.setLabel("��δ��");
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
		});
	}

	public void onClick$add() {
		final FuncToRebackWindow funcWin = (FuncToRebackWindow) Executions.createComponents("/admin/feedback/new/funcToReback.zul", null, null);
		funcWin.setUser(user);
		funcWin.loadList();
		funcWin.doHighlighted();
		funcWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				funcWin.detach();
				loadList();
			}
		});
	}

	public void onClick$del() {
		Set items = fk.getSelectedItems();
		Iterator it = items.iterator();
		Integer hfcount=0,count=0;
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			XYFeedBack feedback = (XYFeedBack) item.getValue();
			// ����з�������ɾ�������ظ�
			if(feedback.getFbisRep().shortValue() == XYFeedBack.REP_YES){
				hfcount=hfcount+1;
				continue;
			}else{
				// ɾ������
				count=count+1;
				xyFeedBackService.delete(feedback);
			}
//			if (feedback.getFbisRep().shortValue() == XYFeedBack.REP_YES) {
//				List replist = xyFeedBackService.findByFbid(feedback.getFbId());
//				for (int i = 0; i < replist.size(); i++) {
//					XYFeedBackReply rep = (XYFeedBackReply) replist.get(i);
//					xyFeedBackService.delete(rep);
//				}
//			}
		}
		try {
			Messagebox.show("�����ɾ����"+count+"��δ�ظ��ķ������,"+hfcount+"�������Ϊ�Ѿ��ظ�����ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loadList();
	}
}
