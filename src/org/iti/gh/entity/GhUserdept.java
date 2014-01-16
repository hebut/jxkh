package org.iti.gh.entity;

/**
 * GhUserdept entity. @author MyEclipse Persistence Tools
 */

public class GhUserdept implements java.io.Serializable {

	// Fields

	private GhUserdeptId id;

	// Constructors

	/** default constructor */
	public GhUserdept() {
	}

	/** full constructor */
	public GhUserdept(GhUserdeptId id) {
		this.id = id;
	}

	// Property accessors

	public GhUserdeptId getId() {
		return this.id;
	}

	public void setId(GhUserdeptId id) {
		this.id = id;
	}

}