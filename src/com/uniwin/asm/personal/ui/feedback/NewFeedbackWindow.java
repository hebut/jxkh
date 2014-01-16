package com.uniwin.asm.personal.ui.feedback;

import java.util.Date;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.XYFeedBack;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;

public class NewFeedbackWindow extends Window implements AfterCompose {
	WkTRole role;
	WkTTitle ti;
	WkTUser user;
	XYFeedBack feedback;
	// 反馈功能
	Label fkgn;
	// 反馈人角色
	Label fkjs;
	// 反馈主题
	Textbox fkzt;
	// 反馈内容
	Textbox fknr;
	Toolbarbutton submit;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow1() {
		fkzt.setReadonly(true);
		fknr.setReadonly(true);
		submit.setVisible(false);
	}
	
	public void initWindow() {
		// 反馈功能模块
		if (ti != null) {
			fkgn.setValue(ti.getKtName());
		} else if (feedback != null) {
			fkgn.setValue(feedback.getTitle().getKtName());
		}
		// 反馈人角色
		if (role != null) {
			fkjs.setValue(role.getKrName());
		} else if (feedback != null) {
			fkjs.setValue(feedback.getRole().getKrName());
		}
		if (feedback != null) {
			fkzt.setValue(feedback.getFbTitle());
			fknr.setValue(feedback.getFbComment());
		}
	}

	public void onClick$submit() throws InterruptedException {
		XYFeedBack fed = new XYFeedBack();
		if ("".equals(fkzt.getValue())) {
			Messagebox.show("您还没有填写反馈主题。");
			return;
		}
		if (feedback != null) {// 此时是查看的情况，只需要保存几个字段
			feedback.setFbAddtime((new Date().getTime()));
			feedback.setFbTitle(fkzt.getValue());
			if ("".equals(fknr.getValue()) || fknr.getValue() == null) {
				feedback.setFbComment("");
			} else {
				feedback.setFbComment(fknr.getValue());
			}
			feedback.setFbAddtime((new Date()).getTime());
			xyFeedBackService.update(feedback);
		} else {// 此时是新建反馈记录的情况
			fed.setKuId(user.getKuId());
			fed.setKrId(role.getKrId());
			fed.setKtId(ti.getKtId());
			fed.setFbTitle(fkzt.getValue());
			if ("".equals(fknr.getValue()) || fknr.getValue() == null) {
				fed.setFbComment("");
			} else {
				fed.setFbComment(fknr.getValue());
			}
			fed.setFbAddtime((new Date()).getTime());
			fed.setFbisRep(XYFeedBack.REP_NO);
			xyFeedBackService.save(fed);
		}
		// 传递事件，来刷新前面的页面，关闭当前的页面
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$cancel() {
		this.detach();
	}

	public WkTRole getRole() {
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	public WkTTitle getTi() {
		return ti;
	}

	public void setTi(WkTTitle ti) {
		this.ti = ti;
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
}
