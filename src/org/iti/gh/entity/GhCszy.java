package org.iti.gh.entity;



/**
 * GhCszy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GhCszy implements java.io.Serializable {

	// Fields

	private Long csId;
	private Long csPid;
	private String csSubname;

	// Constructors

	/** default constructor */
	public GhCszy() {
	}

	/** minimal constructor */
	public GhCszy(Long csId, Long csPid) {
		this.csId = csId;
		this.csPid = csPid;
	}

	/** full constructor */
	public GhCszy(Long csId, Long csPid, String csSubname) {
		this.csId = csId;
		this.csPid = csPid;
		this.csSubname = csSubname;
	}

	// Property accessors

	public Long getCsId() {
		return this.csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public Long getCsPid() {
		return this.csPid;
	}

	public void setCsPid(Long csPid) {
		this.csPid = csPid;
	}

	public String getCsSubname() {
		return this.csSubname;
	}

	public void setCsSubname(String csSubname) {
		this.csSubname = csSubname;
	}

}