package com.uniwin.framework.entity;

/**
 * WkTUserole entity. @author MyEclipse Persistence Tools
 */
public class WkTUserole implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private WkTUseroleId id;

	// Constructors
	/** default constructor */
	public WkTUserole() {
	}

	/** full constructor */
	public WkTUserole(WkTUseroleId id) {
		this.id = id;
	}

	// Property accessors
	public WkTUseroleId getId() {
		return this.id;
	}

	public void setId(WkTUseroleId id) {
		this.id = id;
	}
}