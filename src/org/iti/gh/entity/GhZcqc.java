package org.iti.gh.entity;



/**
 * GhZcqc entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GhZcqc implements java.io.Serializable {

	// Fields

	private Long zcId;
	private String TId;
	private String zcLevel;
	private String zcTime;
	private String zcNum;
	private String zcPubdept;
	private String zcPubtime;
	private String zcQuasym;
	private String zcIdentdept;
	private Long kuId;

	// Constructors

	/** default constructor */
	public GhZcqc() {
	}

	/** minimal constructor */
	public GhZcqc(Long zcId, String TId) {
		this.zcId = zcId;
		this.TId = TId;
	}

	/** full constructor */
	public GhZcqc(Long zcId, String TId, String zcLevel, String zcTime,
			String zcNum, String zcPubdept, String zcPubtime, String zcQuasym,
			String zcIdentdept, Long kuId) {
		this.zcId = zcId;
		this.TId = TId;
		this.zcLevel = zcLevel;
		this.zcTime = zcTime;
		this.zcNum = zcNum;
		this.zcPubdept = zcPubdept;
		this.zcPubtime = zcPubtime;
		this.zcQuasym = zcQuasym;
		this.zcIdentdept = zcIdentdept;
		this.kuId = kuId;
	}

	// Property accessors

	public Long getZcId() {
		return this.zcId;
	}

	public void setZcId(Long zcId) {
		this.zcId = zcId;
	}

	public String getTId() {
		return this.TId;
	}

	public void setTId(String TId) {
		this.TId = TId;
	}

	public String getZcLevel() {
		return this.zcLevel;
	}

	public void setZcLevel(String zcLevel) {
		this.zcLevel = zcLevel;
	}

	public String getZcTime() {
		return this.zcTime;
	}

	public void setZcTime(String zcTime) {
		this.zcTime = zcTime;
	}

	public String getZcNum() {
		return this.zcNum;
	}

	public void setZcNum(String zcNum) {
		this.zcNum = zcNum;
	}

	public String getZcPubdept() {
		return this.zcPubdept;
	}

	public void setZcPubdept(String zcPubdept) {
		this.zcPubdept = zcPubdept;
	}

	public String getZcPubtime() {
		return this.zcPubtime;
	}

	public void setZcPubtime(String zcPubtime) {
		this.zcPubtime = zcPubtime;
	}

	public String getZcQuasym() {
		return this.zcQuasym;
	}

	public void setZcQuasym(String zcQuasym) {
		this.zcQuasym = zcQuasym;
	}

	public String getZcIdentdept() {
		return this.zcIdentdept;
	}

	public void setZcIdentdept(String zcIdentdept) {
		this.zcIdentdept = zcIdentdept;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

}