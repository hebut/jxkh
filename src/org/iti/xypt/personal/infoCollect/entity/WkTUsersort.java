package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFlog entity. @author MyEclipse Persistence Tools
 */

public class WkTUsersort implements java.io.Serializable {

	// Fields

	private Long kusId;
	private Long kusPid;
	private String  kusName;
	// Constructors

	/** default constructor */
	public WkTUsersort() {
	}

	/** minimal constructor */
	public WkTUsersort(Long kusId,Long kusPid) {
		this.kusId = kusId;
		this.kusPid=kusPid;
	}

	/** full constructor */
	public WkTUsersort(Long kusId, Long kusPid, String  kusName) {
		this.kusId = kusId;
		this.kusPid = kusPid;
		this.kusName = kusName;
	}

	// Property accessors


	public Long getKusId() {
		return this.kusId;
	}

	public void setKusId(Long kusId) {
		this.kusId = kusId;
	}

	public Long getkusPid() {
		return this.kusPid;
	}

	public void setkusPid(Long kusPid) {
		this.kusPid =kusPid;
	}

	public String getkusName() {
		return this.kusName;
	}

	public void setkusName(String kusName) {
		this.kusName = kusName;
	}

}