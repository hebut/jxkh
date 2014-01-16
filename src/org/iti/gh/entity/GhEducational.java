package org.iti.gh.entity;



/**
 * GhEducational entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GhEducational implements java.io.Serializable {

	// Fields

	private Long edId;
	private Long edPid;
	private String edEduname;

	// Constructors

	/** default constructor */
	public GhEducational() {
	}

	/** minimal constructor */
	public GhEducational(Long edId, Long edPid) {
		this.edId = edId;
		this.edPid = edPid;
	}

	/** full constructor */
	public GhEducational(Long edId, Long edPid, String edEduname) {
		this.edId = edId;
		this.edPid = edPid;
		this.edEduname = edEduname;
	}

	// Property accessors

	public Long getEdId() {
		return this.edId;
	}

	public void setEdId(Long edId) {
		this.edId = edId;
	}

	public Long getEdPid() {
		return this.edPid;
	}

	public void setEdPid(Long edPid) {
		this.edPid = edPid;
	}

	public String getEdEduname() {
		return this.edEduname;
	}

	public void setEdEduname(String edEduname) {
		this.edEduname = edEduname;
	}

}