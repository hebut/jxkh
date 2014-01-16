package com.uniwin.asm.personal.entity;

import java.util.Date;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * QzMessage entity. @author MyEclipse Persistence Tools
 */
public class QzMessage implements java.io.Serializable {
	// Fields
	private Long mgId;
	private Long sjId;
	private Long mgSpeaker;
	private String mgContent;
	private String mgPath;
	private Date mgTime;
	WkTUser user;

	// Constructors
	/** default constructor */
	public QzMessage() {
	}

	/** minimal constructor */
	public QzMessage(Long mgId) {
		this.mgId = mgId;
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, mgSpeaker);
		}
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	/** full constructor */
	public QzMessage(Long mgId, Long sjId, Long mgSpeaker, String mgContent, String mgPath, Date mgTime) {
		this.mgId = mgId;
		this.sjId = sjId;
		this.mgSpeaker = mgSpeaker;
		this.mgContent = mgContent;
		this.mgPath = mgPath;
		this.mgTime = mgTime;
	}

	// Property accessors
	public Long getMgId() {
		return this.mgId;
	}

	public void setMgId(Long mgId) {
		this.mgId = mgId;
	}

	public Long getSjId() {
		return this.sjId;
	}

	public void setSjId(Long sjId) {
		this.sjId = sjId;
	}

	public Long getMgSpeaker() {
		return this.mgSpeaker;
	}

	public void setMgSpeaker(Long mgSpeaker) {
		this.mgSpeaker = mgSpeaker;
	}

	public String getMgContent() {
		return this.mgContent;
	}

	public void setMgContent(String mgContent) {
		this.mgContent = mgContent;
	}

	public String getMgPath() {
		return this.mgPath;
	}

	public void setMgPath(String mgPath) {
		this.mgPath = mgPath;
	}

	public Date getMgTime() {
		return this.mgTime;
	}

	public void setMgTime(Date mgTime) {
		this.mgTime = mgTime;
	}
}