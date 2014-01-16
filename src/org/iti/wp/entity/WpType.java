package org.iti.wp.entity;

/**
 * WpType entity. @author MyEclipse Persistence Tools
 */

public class WpType implements java.io.Serializable {

	// Fields

	private Long wtId;
	private String wtName;
	private Long kdId;
	private String wtType;

	// Constructors

	/** default constructor */
	public WpType() {
	}

	/** full constructor */
	public WpType(String wtName, Long kdId, String wtType) {
		this.wtName = wtName;
		this.kdId = kdId;
		this.wtType = wtType;
	}

	// Property accessors

	public Long getWtId() {
		return this.wtId;
	}

	public void setWtId(Long wtId) {
		this.wtId = wtId;
	}

	public String getWtName() {
		return this.wtName;
	}

	public void setWtName(String wtName) {
		this.wtName = wtName;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public String getWtType() {
		return this.wtType;
	}

	public void setWtType(String wtType) {
		this.wtType = wtType;
	}

}