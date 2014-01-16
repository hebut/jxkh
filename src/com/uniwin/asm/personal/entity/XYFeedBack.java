package com.uniwin.asm.personal.entity;

import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;

public class XYFeedBack implements java.io.Serializable {
	public static final short REP_YES = 1, REP_NO = 0;
	Long fbId;
	Long kuId;
	Long krId;
	Long ktId;
	String fbTitle;
	String fbComment;
	Long fbAddtime;
	Short fbisRep;
	private WkTTitle title;
	private WkTRole role;
	private WkTUser user;

	public XYFeedBack() {
	}

	public XYFeedBack(Long fbId, Long kuId, Long krId, Long ktId, String fbTitle, String fbComment, Long fbAddtime, Short fbisRep) {
		super();
		this.fbId = fbId;
		this.kuId = kuId;
		this.krId = krId;
		this.ktId = ktId;
		this.fbTitle = fbTitle;
		this.fbComment = fbComment;
		this.fbAddtime = fbAddtime;
		this.fbisRep = fbisRep;
	}

	public WkTUser getUser() {
		if (user == null) {
			XYFeedBackService xyFeedBackService = (XYFeedBackService) BeanFactory.getBean("xyFeedBackService");
			user = (WkTUser) xyFeedBackService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public WkTTitle getTitle() {
		if (title == null) {
			XYFeedBackService xyFeedBackService = (XYFeedBackService) BeanFactory.getBean("xyFeedBackService");
			title = (WkTTitle) xyFeedBackService.get(WkTTitle.class, ktId);
		}
		return title;
	}

	public WkTRole getRole() {
		if (role == null) {
			XYFeedBackService xyFeedBackService = (XYFeedBackService) BeanFactory.getBean("xyFeedBackService");
			role = (WkTRole) xyFeedBackService.get(WkTRole.class, krId);
		}
		return role;
	}

	public Long getFbId() {
		return fbId;
	}

	public void setFbId(Long fbId) {
		this.fbId = fbId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getKrId() {
		return krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

	public Long getKtId() {
		return ktId;
	}

	public void setKtId(Long ktId) {
		this.ktId = ktId;
	}

	public String getFbTitle() {
		return fbTitle;
	}

	public void setFbTitle(String fbTitle) {
		this.fbTitle = fbTitle;
	}

	public String getFbComment() {
		return fbComment;
	}

	public void setFbComment(String fbComment) {
		this.fbComment = fbComment;
	}

	public Long getFbAddtime() {
		return fbAddtime;
	}

	public void setFbAddtime(Long fbAddtime) {
		this.fbAddtime = fbAddtime;
	}

	public Short getFbisRep() {
		return fbisRep;
	}

	public void setFbisRep(Short fbisRep) {
		this.fbisRep = fbisRep;
	}
}
