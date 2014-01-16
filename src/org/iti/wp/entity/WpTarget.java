package org.iti.wp.entity;

/**
 * WpTarget entity. @author MyEclipse Persistence Tools
 */

public class WpTarget implements java.io.Serializable {

	// Fields

	private Long wtaId;
	private String wtaName;
	private String wtaValue;
	private String wtaNotes;
	private Long wtaPid;
	private Long wtId;
    //²ã´Î
	private Integer dep;
	// Constructors

	/** default constructor */
	public WpTarget() {
	}

	/** minimal constructor */
	public WpTarget(Long wtaPid, Long wtId) {
		this.wtaPid = wtaPid;
		this.wtId = wtId;
	}

	/** full constructor */
	public WpTarget(String wtaName, String wtaValue, String wtaNotes,
			Long wtaPid, Long wtId) {
		this.wtaName = wtaName;
		this.wtaValue = wtaValue;
		this.wtaNotes = wtaNotes;
		this.wtaPid = wtaPid;
		this.wtId = wtId;
	}

	// Property accessors

	public Long getWtaId() {
		return this.wtaId;
	}

	public void setWtaId(Long wtaId) {
		this.wtaId = wtaId;
	}

	public String getWtaName() {
		return this.wtaName;
	}

	public void setWtaName(String wtaName) {
		this.wtaName = wtaName;
	}

	public String getWtaValue() {
		return this.wtaValue;
	}

	public void setWtaValue(String wtaValue) {
		this.wtaValue = wtaValue;
	}

	public String getWtaNotes() {
		return this.wtaNotes;
	}

	public void setWtaNotes(String wtaNotes) {
		this.wtaNotes = wtaNotes;
	}

	public Long getWtaPid() {
		return this.wtaPid;
	}

	public void setWtaPid(Long wtaPid) {
		this.wtaPid = wtaPid;
	}

	public Long getWtId() {
		return this.wtId;
	}

	public void setWtId(Long wtId) {
		this.wtId = wtId;
	}
	public Integer getDep() {
		return dep;
	}

	public void setDep(Integer dep) {
		this.dep = dep;
	}
}