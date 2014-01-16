package com.uniwin.asm.personal.ui.feedback;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.XYFeedBack;
import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.framework.entity.WkTUser;

public class FeedBack extends Window implements AfterCompose {
	WkTUser user;
	XYFeedBack feedback;
	XYFeedBackReply reply;
	// 反馈功能
	Label fkgn;
	// 反馈人角色
	Label fkjs;
	// 反馈主题
	Label fkzt;
	// 反馈内容
	Textbox fknr;
	// 答复内容
	Textbox dfnr;
	// 答复人
	Textbox dfr;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		// 反馈相关信息
		if (feedback != null) {
			fkgn.setValue(feedback.getTitle().getKtName());
			fkjs.setValue(feedback.getRole().getKrName());
			fkzt.setValue(feedback.getFbTitle());
			fknr.setValue(feedback.getFbComment());
		}
		// 答复相关信息
		if (reply != null) {
			dfnr.setValue(reply.getFbrComment());
			dfr.setValue(reply.getUser().getKuName());
		}
	}

	public void onClick$submit() {
		final NewFeedbackWindow fbackWin = (NewFeedbackWindow) Executions.createComponents("/admin/feedback/new/new_feedback.zul", null, null);
		fbackWin.setTi(feedback.getTitle());
		fbackWin.setRole(feedback.getRole());
		fbackWin.setUser(user);
		fbackWin.initWindow();
		fbackWin.doHighlighted();
		fbackWin.doHighlighted();
		fbackWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				sendEvents();
				fbackWin.detach();
			}
		});
	}

	public void sendEvents() {
		Events.postEvent(Events.ON_CHANGE, this, this);
	}

	public void onClick$cancel() {
		this.detach();
	}

	public XYFeedBackReply getReply() {
		return reply;
	}

	public void setReply(XYFeedBackReply reply) {
		this.reply = reply;
	}

	public XYFeedBack getFeedback() {
		return feedback;
	}

	public void setFeedback(XYFeedBack feedback) {
		this.feedback = feedback;
	}

	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}
}
