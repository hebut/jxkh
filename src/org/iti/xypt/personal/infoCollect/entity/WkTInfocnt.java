package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTInfocnt entity. @author MyEclipse Persistence Tools
 */

public class WkTInfocnt implements java.io.Serializable {

	// Fields

	private WkTInfocntId id;
	private String kiFlag;
	private String kiContent;

	// Constructors

	/** default constructor */
	public WkTInfocnt() {
	}

	/** minimal constructor */
	public WkTInfocnt(WkTInfocntId id) {
		this.id = id;
	}

	/** full constructor */
	public WkTInfocnt(WkTInfocntId id, String kiFlag, String kiContent) {
		this.id = id;
		this.kiFlag = kiFlag;
		this.kiContent = kiContent;
	}

	// Property accessors

	public WkTInfocntId getId() {
		return this.id;
	}

	public void setId(WkTInfocntId id) {
		this.id = id;
	}

	public String getKiFlag() {
		return this.kiFlag;
	}

	public void setKiFlag(String kiFlag) {
		this.kiFlag = kiFlag;
	}

	public String getKiContent() {
		return this.kiContent;
	}

	public void setKiContent(String kiContent) {
		this.kiContent = kiContent;
	}

}