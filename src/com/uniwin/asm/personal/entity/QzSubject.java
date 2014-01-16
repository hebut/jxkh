package com.uniwin.asm.personal.entity;

import java.util.Date;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * QzSubject entity. @author MyEclipse Persistence Tools
 */
public class QzSubject implements java.io.Serializable {
	// Fields
	private Long sjId;
	private Long qzId;
	private Long sjBuilder;
	private String sjTitle;
	private Integer sjType;
	private String sjPath;
	private Date sjTime;
	private String sjContent;
	private Integer sjViewsum;
	private Integer sjImg;
	WkTUser user;

	// Constructors
	/** default constructor */
	public QzSubject() {
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, sjBuilder);
		}
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	/** minimal constructor */
	public QzSubject(Long sjId) {
		this.sjId = sjId;
	}

	/** full constructor */
	public QzSubject(Long sjId, Long qzId, Long sjBuilder, String sjTitle, Integer sjType, String sjPath, Date sjTime, String sjContent, Integer sjViewsum, Integer sjImg) {
		this.sjId = sjId;
		this.qzId = qzId;
		this.sjBuilder = sjBuilder;
		this.sjTitle = sjTitle;
		this.sjType = sjType;
		this.sjPath = sjPath;
		this.sjTime = sjTime;
		this.sjContent = sjContent;
		this.sjViewsum = sjViewsum;
		this.sjImg = sjImg;
	}

	// Property accessors
	public Long getSjId() {
		return this.sjId;
	}

	public void setSjId(Long sjId) {
		this.sjId = sjId;
	}

	public Long getQzId() {
		return this.qzId;
	}

	public void setQzId(Long qzId) {
		this.qzId = qzId;
	}

	public Long getSjBuilder() {
		return this.sjBuilder;
	}

	public void setSjBuilder(Long sjBuilder) {
		this.sjBuilder = sjBuilder;
	}

	public String getSjTitle() {
		return this.sjTitle;
	}

	public void setSjTitle(String sjTitle) {
		this.sjTitle = sjTitle;
	}

	public Integer getSjType() {
		return this.sjType;
	}

	public void setSjType(Integer sjType) {
		this.sjType = sjType;
	}

	public String getSjPath() {
		return this.sjPath;
	}

	public void setSjPath(String sjPath) {
		this.sjPath = sjPath;
	}

	public Date getSjTime() {
		return this.sjTime;
	}

	public void setSjTime(Date sjTime) {
		this.sjTime = sjTime;
	}

	public String getSjContent() {
		return sjContent;
	}

	public void setSjContent(String sjContent) {
		this.sjContent = sjContent;
	}

	public Integer getSjViewsum() {
		return sjViewsum;
	}

	public void setSjViewsum(Integer sjViewsum) {
		this.sjViewsum = sjViewsum;
	}

	public Integer getSjImg() {
		return sjImg;
	}

	public void setSjImg(Integer sjImg) {
		this.sjImg = sjImg;
	}
}