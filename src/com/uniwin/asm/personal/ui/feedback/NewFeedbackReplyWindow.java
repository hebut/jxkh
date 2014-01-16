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
	// ��������
	Label fkgn;
	// �����˽�ɫ
	Label fkjs;
	// ��������
	Textbox fkzt;
	// ��������
	Textbox fknr;
	// ������
	Textbox dfnr;
	XYFeedBackService xyFeedBackService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		// ��������ģ��
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
			Messagebox.show("����û����д�����ݡ�");
			return;
		}
		if (reply != null) {// ��ʱ�ǲ鿴�������ֻ��Ҫ���漸���ֶ�
			if ("".equals(dfnr.getValue()) || dfnr.getValue() == null) {
				reply.setFbrComment("");
			} else {
				reply.setFbrComment(dfnr.getValue());
			}
			reply.setFbrAddtime((new Date()).getTime());
			xyFeedBackService.update(reply);
		} else {// ��ʱ���½�������¼�����
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
		// �����¼�����ˢ��ǰ���ҳ�棬�رյ�ǰ��ҳ��
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
