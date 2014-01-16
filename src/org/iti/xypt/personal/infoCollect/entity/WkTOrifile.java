package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFile entity. @author MyEclipse Persistence Tools
 */

public class WkTOrifile implements java.io.Serializable {

	// Fields

	private WkTOrifileId id;

	// Constructors

	/** default constructor */
	public WkTOrifile() {
	}

	/** full constructor */
	public WkTOrifile(WkTOrifileId id) {
		this.id = id;
	}

	// Property accessors

	public WkTOrifileId getId() {
		return this.id;
	}

	public void setId(WkTOrifileId id) {
		this.id = id;
	}

}