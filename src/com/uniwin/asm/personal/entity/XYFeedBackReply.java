package com.uniwin.asm.personal.entity;

import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;

public class XYFeedBackReply implements java.io.Serializable {
	Long fbrId;
	Long kuId;
	Long fbId;
	String fbrComment;
	Long fbrAddtime;
	private WkTUser user;

	public XYFeedBackReply() {
	}

	public XYFeedBackReply(Long fbrId, Long kuId, Long fbId, String fbrComment, Long fbrAddtime) {
		super();
		this.fbrId = fbrId;
		this.kuId = kuId;
		this.fbId = fbId;
		this.fbrComment = fbrComment;
		this.fbrAddtime = fbrAddtime;
	}

	public WkTUser getUser() {
		if (user == null) {
			XYFeedBackService xyFeedBackService = (XYFeedBackService) BeanFactory.getBean("xyFeedBackService");
			user = (WkTUser) xyFeedBackService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public Long getFbrId() {
		return fbrId;
	}

	public void setFbrId(Long fbrId) {
		this.fbrId = fbrId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getFbId() {
		return fbId;
	}

	public void setFbId(Long fbId) {
		this.fbId = fbId;
	}

	public String getFbrComment() {
		return fbrComment;
	}

	public void setFbrComment(String fbrComment) {
		this.fbrComment = fbrComment;
	}

	public Long getFbrAddtime() {
		return fbrAddtime;
	}

	public void setFbrAddtime(Long fbrAddtime) {
		this.fbrAddtime = fbrAddtime;
	}
}
