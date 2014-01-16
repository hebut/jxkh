package com.uniwin.asm.personal.ui.feedback;

import java.util.ArrayList;
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
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.XYFeedBack;
import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class FeedbackReplyWindow extends Window implements AfterCompose {
	Listbox fkyj;
	WkTUser user;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
	}

	public void initWindow() {
		List list = xyFeedBackService.getAll();
		fkyj.setModel(new ListModelList(list));
		fkyj.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final XYFeedBack feedback = (XYFeedBack) arg1;
				arg0.setValue(arg1);
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				final XYFeedBackReply reply = (XYFeedBackReply) xyFeedBackService.findByFbidAndKuid(feedback.getFbId(), user.getKuId());
				// 意见内容
				Listcell c2 = new Listcell();
				InnerButton nrButton = new InnerButton();
				nrButton.setLabel(feedback.getFbTitle());
				nrButton.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final NewFeedbackReplyWindow replyWin = (NewFeedbackReplyWindow) Executions.createComponents("/admin/feedback/manager/new_feedback.zul", null, null);
						replyWin.setFeedback(feedback);
						replyWin.setUser(user);
						replyWin.setReply(reply);
						replyWin.initWindow();
						replyWin.doHighlighted();
						replyWin.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
								replyWin.detach();
							}
						});
					}
				});
				c2.appendChild(nrButton);
				// 反馈人
				Listcell c3 = new Listcell(feedback.getUser().getKuName());
				// 反馈角色
				Listcell c4 = new Listcell(feedback.getRole().getKrName());
				// 反馈时间
				Listcell c5 = new Listcell(ConvertUtil.convertDateAndTimeString(feedback.getFbAddtime()));
				// 已经回复
				Listcell c6 = new Listcell();
				InnerButton repButton = new InnerButton();
				if (feedback.getFbisRep().shortValue() == XYFeedBack.REP_YES) {
					repButton.setLabel("查看");
				} else {
					repButton.setLabel("尚未答复");
				}
				repButton.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final NewFeedbackReplyWindow replyWin = (NewFeedbackReplyWindow) Executions.createComponents("/admin/feedback/manager/new_feedback.zul", null, null);
						replyWin.setFeedback(feedback);
						replyWin.setUser(user);
						replyWin.setReply(reply);
						replyWin.initWindow();
						replyWin.doHighlighted();
						replyWin.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								initWindow();
								replyWin.detach();
							}
						});
					}
				});
				c6.appendChild(repButton);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}
		});
	}

	/**
	 * 删除反馈
	 */
	public void onClick$del() {
		Set items = fkyj.getSelectedItems();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			XYFeedBack feedback = (XYFeedBack) item.getValue();
			// 如果有反馈，则删除反馈回复
			if (feedback.getFbisRep().shortValue() == XYFeedBack.REP_YES) {
				List replist = xyFeedBackService.findByFbid(feedback.getFbId());
				for (int i = 0; i < replist.size(); i++) {
					XYFeedBackReply rep = (XYFeedBackReply) replist.get(i);
					xyFeedBackService.delete(rep);
				}
			}
			// 删除反馈
			xyFeedBackService.delete(feedback);
		}
		initWindow();
	}

	/**
	 * 删除答复
	 */
	public void onClick$delete() {
		Set items = fkyj.getSelectedItems();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			XYFeedBack feedback = (XYFeedBack) item.getValue();
			XYFeedBackReply rep = xyFeedBackService.findByFbidAndKuid(feedback.getFbId(), user.getKuId());
			if (rep != null) {
				xyFeedBackService.delete(rep);
			}
			feedback.setFbisRep(XYFeedBack.REP_NO);
			xyFeedBackService.update(feedback);
		}
		initWindow();
	}
}
