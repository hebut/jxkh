package com.uniwin.asm.personal.ui.feedback;

import java.util.Date;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.XYFeedBack;
import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;

public class NewFeedbackReplyWindow extends Window implements AfterCompose {
	WkTUser user;
	XYFeedBack feedback;
	XYFeedBackReply reply;
	// 反馈功能
	Label fkgn;
	// 反馈人角色
	Label fkjs;
	// 反馈主题
	Textbox fkzt;
	// 反馈内容
	Textbox fknr;
	// 答复内容
	Textbox dfnr;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		// 反馈功能模块
		if (feedback != null) {
			fkgn.setValue(feedback.getTitle().getKtName());
			fkjs.setValue(feedback.getRole().getKrName());
			fkzt.setValue(feedback.getFbTitle());
			fknr.setValue(feedback.getFbComment());
		}
		if (reply != null) {
			dfnr.setValue(reply.getFbrComment());
		}
	}

	public void onClick$submit() throws InterruptedException {
		XYFeedBackReply re = new XYFeedBackReply();
		if ("".equals(dfnr.getValue())) {
			Messagebox.show("您还没有填写答复内容。");
			return;
		}
		if (reply != null) {// 此时是查看的情况，只需要保存几个字段
			if ("".equals(dfnr.getValue()) || dfnr.getValue() == null) {
				reply.setFbrComment("");
			} else {
				reply.setFbrComment(dfnr.getValue());
			}
			reply.setFbrAddtime((new Date()).getTime());
			xyFeedBackService.update(reply);
		} else {// 此时是新建反馈记录的情况
			re.setKuId(user.getKuId());
			re.setFbrAddtime((new Date()).getTime());
			re.setFbId(feedback.getFbId());
			if ("".equals(dfnr.getValue()) || dfnr.getValue() == null) {
				re.setFbrComment("");
			} else {
				re.setFbrComment(dfnr.getValue());
			}
			xyFeedBackService.save(re);
			feedback.setFbisRep(XYFeedBack.REP_YES);
			xyFeedBackService.update(feedback);
		}
		// 传递事件，来刷新前面的页面，关闭当前的页面
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$cancel() {
		this.detach();
	}

	public WkTUser getUser() {
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	public XYFeedBack getFeedback() {
		return feedback;
	}

	public void setFeedback(XYFeedBack feedback) {
		this.feedback = feedback;
	}

	public XYFeedBackReply getReply() {
		return reply;
	}

	public void setReply(XYFeedBackReply reply) {
		this.reply = reply;
	}
}
