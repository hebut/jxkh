package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFlog entity. @author MyEclipse Persistence Tools
 */

public class WkTFlog implements java.io.Serializable {

	// Fields

	private Long kflId;
	private Long kfId;
	private Long kbId;
	private String kflTime;
	private Long kuId;
	private String kflMemo;
	private String kuName;

	// Constructors

	/** default constructor */
	public WkTFlog() {
	}

	/** minimal constructor */
	public WkTFlog(Long kflId) {
		this.kflId = kflId;
	}

	/** full constructor */
	public WkTFlog(Long kflId, Long kfId, Long kbId, String kflTime, Long kuId,
			String kflMemo, String kuName) {
		this.kflId = kflId;
		this.kfId = kfId;
		this.kbId = kbId;
		this.kflTime = kflTime;
		this.kuId = kuId;
		this.kflMemo = kflMemo;
		this.kuName = kuName;
	}

	// Property accessors

	public Long getKflId() {
		return this.kflId;
	}

	public void setKflId(Long kflId) {
		this.kflId = kflId;
	}

	public Long getKfId() {
		return this.kfId;
	}

	public void setKfId(Long kfId) {
		this.kfId = kfId;
	}

	public Long getKbId() {
		return this.kbId;
	}

	public void setKbId(Long kbId) {
		this.kbId = kbId;
	}

	public String getKflTime() {
		return this.kflTime;
	}

	public void setKflTime(String kflTime) {
		this.kflTime = kflTime;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKflMemo() {
		return this.kflMemo;
	}

	public void setKflMemo(String kflMemo) {
		this.kflMemo = kflMemo;
	}

	public String getKuName() {
		return this.kuName;
	}

	public void setKuName(String kuName) {
		this.kuName = kuName;
	}

}