package com.uniwin.asm.personal.entity;

import java.util.Date;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * QzGroup entity. @author MyEclipse Persistence Tools
 */
public class QzGroup implements java.io.Serializable {
	// Fields
	private Long qzId;
	private Long qzUser;
	private Date qzTime;
	private String qzName;
	private Integer qzType;
	private String qzPath;
	private String qzdescrib;
	WkTUser user;

	// Constructors
	/** default constructor */
	public QzGroup() {
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, qzUser);
		}
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	/** minimal constructor */
	public QzGroup(Long qzId) {
		this.qzId = qzId;
	}

	/** full constructor */
	public QzGroup(Long qzId, Long qzUser, Date qzTime, String qzName, Integer qzType, String qzPath, String qzdescrib) {
		this.qzId = qzId;
		this.qzUser = qzUser;
		this.qzTime = qzTime;
		this.qzName = qzName;
		this.qzType = qzType;
		this.qzPath = qzPath;
		this.qzdescrib = qzdescrib;
	}

	// Property accessors
	public Long getQzId() {
		return this.qzId;
	}

	public void setQzId(Long qzId) {
		this.qzId = qzId;
	}

	public Long getQzUser() {
		return this.qzUser;
	}

	public void setQzUser(Long qzUser) {
		this.qzUser = qzUser;
	}

	public Date getQzTime() {
		return this.qzTime;
	}

	public void setQzTime(Date qzTime) {
		this.qzTime = qzTime;
	}

	public String getQzName() {
		return this.qzName;
	}

	public void setQzName(String qzName) {
		this.qzName = qzName;
	}

	public Integer getQzType() {
		return this.qzType;
	}

	public void setQzType(Integer qzType) {
		this.qzType = qzType;
	}

	public String getQzPath() {
		return qzPath;
	}

	public void setQzPath(String qzPath) {
		this.qzPath = qzPath;
	}

	public String getQzdescrib() {
		return qzdescrib;
	}

	public void setQzdescrib(String qzdescrib) {
		this.qzdescrib = qzdescrib;
	}
}