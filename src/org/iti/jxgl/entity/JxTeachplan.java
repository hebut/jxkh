package org.iti.jxgl.entity;

import java.util.Date;

/**
 * JxTeachplan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class JxTeachplan implements java.io.Serializable {

	// Fields

	private Long jtpId;
	private Integer jtSumstate;
	private Short jtpUpstate;
	private Date jtpUptime;
	private String jtpPath;
	private Short jtpLrstate;
	private Date jtpLrtime;
	private String jtpMark;
	private Short jtpState;
	private String jtpOpinion;
	
	public JxTeachplan(Long jtpId, Integer jtSumstate, Short jtpUpstate,
			Date jtpUptime, String jtpPath, Short jtpLrstate, Date jtpLrtime,
			String jtpMark, Short jtpState, String jtpOpinion) {
		super();
		this.jtpId = jtpId;
		this.jtSumstate = jtSumstate;
		this.jtpUpstate = jtpUpstate;
		this.jtpUptime = jtpUptime;
		this.jtpPath = jtpPath;
		this.jtpLrstate = jtpLrstate;
		this.jtpLrtime = jtpLrtime;
		this.jtpMark = jtpMark;
		this.jtpState = jtpState;
		this.jtpOpinion = jtpOpinion;
	}

	
	
	
	public Short getJtpState() {
		return jtpState;
	}




	public void setJtpState(Short jtpState) {
		this.jtpState = jtpState;
	}




	public Short getJtpstate() {
		return jtpState;
	}

	public void setJtpstate(Short jtpstate) {
		this.jtpState = jtpstate;
	}

	public String getJtpOpinion() {
		return jtpOpinion;
	}

	public void setJtpOpinion(String jtpOpinion) {
		this.jtpOpinion = jtpOpinion;
	}

	

	// Constructors

	/** default constructor */
	public JxTeachplan() {
	}

	/** minimal constructor */
	public JxTeachplan(Long jtpId) {
		this.jtpId = jtpId;
	}

	

	// Property accessors

	public Long getJtpId() {
		return this.jtpId;
	}

	public void setJtpId(Long jtpId) {
		this.jtpId = jtpId;
	}

	public Integer getJtSumstate() {
		return this.jtSumstate;
	}

	public void setJtSumstate(Integer jtSumstate) {
		this.jtSumstate = jtSumstate;
	}

	public Short getJtpUpstate() {
		return this.jtpUpstate;
	}

	public void setJtpUpstate(Short jtpUpstate) {
		this.jtpUpstate = jtpUpstate;
	}

	public Date getJtpUptime() {
		return this.jtpUptime;
	}

	public void setJtpUptime(Date jtpUptime) {
		this.jtpUptime = jtpUptime;
	}

	public String getJtpPath() {
		return this.jtpPath;
	}

	public void setJtpPath(String jtpPath) {
		this.jtpPath = jtpPath;
	}

	public Short getJtpLrstate() {
		return this.jtpLrstate;
	}

	public void setJtpLrstate(Short jtpLrstate) {
		this.jtpLrstate = jtpLrstate;
	}

	public Date getJtpLrtime() {
		return this.jtpLrtime;
	}

	public void setJtpLrtime(Date jtpLrtime) {
		this.jtpLrtime = jtpLrtime;
	}

	public String getJtpMark() {
		return this.jtpMark;
	}

	public void setJtpMark(String jtpMark) {
		this.jtpMark = jtpMark;
	}

	}

