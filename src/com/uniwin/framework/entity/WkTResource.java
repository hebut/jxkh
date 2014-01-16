package com.uniwin.framework.entity;

/**
 * WkTResource entity. @author MyEclipse Persistence Tools
 */
public class WkTResource implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long kreId;
	private String kreName;
	private String kreType;
	private String kreRname;
	private String krePurview;

	// Constructors
	/** default constructor */
	public WkTResource() {
	}

	/** minimal constructor */
	public WkTResource(Long kreId) {
		this.kreId = kreId;
	}

	/** full constructor */
	public WkTResource(Long kreId, String kreName, String kreType, String kreRname, String krePurview) {
		this.kreId = kreId;
		this.kreName = kreName;
		this.kreType = kreType;
		this.kreRname = kreRname;
		this.krePurview = krePurview;
	}

	// Property accessors
	public Long getKreId() {
		return this.kreId;
	}

	public void setKreId(Long kreId) {
		this.kreId = kreId;
	}

	public String getKreName() {
		return this.kreName;
	}

	public void setKreName(String kreName) {
		this.kreName = kreName;
	}

	public String getKreType() {
		return this.kreType;
	}

	public void setKreType(String kreType) {
		this.kreType = kreType;
	}

	public String getKreRname() {
		return this.kreRname;
	}

	public void setKreRname(String kreRname) {
		this.kreRname = kreRname;
	}

	public String getKrePurview() {
		return this.krePurview;
	}

	public void setKrePurview(String krePurview) {
		this.krePurview = krePurview;
	}
}