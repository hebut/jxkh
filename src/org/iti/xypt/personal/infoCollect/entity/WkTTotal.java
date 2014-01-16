package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTTotal entity. @author MyEclipse Persistence Tools
 */

public class WkTTotal implements java.io.Serializable {

	// Fields

	private WkTTotalId id;

	// Constructors

	/** default constructor */
	public WkTTotal() {
	}

	/** full constructor */
	public WkTTotal(WkTTotalId id) {
		this.id = id;
	}

	// Property accessors

	public WkTTotalId getId() {
		return this.id;
	}

	public void setId(WkTTotalId id) {
		this.id = id;
	}

}