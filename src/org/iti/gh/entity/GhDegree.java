package org.iti.gh.entity;



/**
 * GhDegree entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GhDegree implements java.io.Serializable {

	// Fields

	private Long deId;
	private String deDegname;

	// Constructors

	/** default constructor */
	public GhDegree() {
	}

	/** minimal constructor */
	public GhDegree(Long deId) {
		this.deId = deId;
	}

	/** full constructor */
	public GhDegree(Long deId, String deDegname) {
		this.deId = deId;
		this.deDegname = deDegname;
	}

	// Property accessors

	public Long getDeId() {
		return this.deId;
	}

	public void setDeId(Long deId) {
		this.deId = deId;
	}

	public String getDeDegname() {
		return this.deDegname;
	}

	public void setDeDegname(String deDegname) {
		this.deDegname = deDegname;
	}

}