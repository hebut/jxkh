package com.uniwin.asm.personal.entity;

/**
 * WkTRcvtask entity. @author MyEclipse Persistence Tools
 */
public class WkTRcvtask implements java.io.Serializable {
	// Fields
	private Long krRtaskid;
	private String krRinfoid;
	private String krInfotype;
	private String krInfoid;
	private String krPackid;
	private String krStatus;
	private String krRcvstime;
	private String krRcvetime;
	private String krUnpackstime;
	private String krUnpacketime;
	private String krPackfile;

	// Constructors
	/** default constructor */
	public WkTRcvtask() {
	}

	/** minimal constructor */
	public WkTRcvtask(Long krRtaskid) {
		this.krRtaskid = krRtaskid;
	}

	/** full constructor */
	public WkTRcvtask(Long krRtaskid, String krRinfoid, String krInfotype, String krInfoid, String krPackid, String krStatus, String krRcvstime, String krRcvetime, String krUnpackstime, String krUnpacketime, String krPackfile) {
		this.krRtaskid = krRtaskid;
		this.krRinfoid = krRinfoid;
		this.krInfotype = krInfotype;
		this.krInfoid = krInfoid;
		this.krPackid = krPackid;
		this.krStatus = krStatus;
		this.krRcvstime = krRcvstime;
		this.krRcvetime = krRcvetime;
		this.krUnpackstime = krUnpackstime;
		this.krUnpacketime = krUnpacketime;
		this.krPackfile = krPackfile;
	}

	// Property accessors
	public Long getKrRtaskid() {
		return this.krRtaskid;
	}

	public void setKrRtaskid(Long krRtaskid) {
		this.krRtaskid = krRtaskid;
	}

	public String getKrRinfoid() {
		return this.krRinfoid;
	}

	public void setKrRinfoid(String krRinfoid) {
		this.krRinfoid = krRinfoid;
	}

	public String getKrInfotype() {
		return this.krInfotype;
	}

	public void setKrInfotype(String krInfotype) {
		this.krInfotype = krInfotype;
	}

	public String getKrInfoid() {
		return this.krInfoid;
	}

	public void setKrInfoid(String krInfoid) {
		this.krInfoid = krInfoid;
	}

	public String getKrPackid() {
		return this.krPackid;
	}

	public void setKrPackid(String krPackid) {
		this.krPackid = krPackid;
	}

	public String getKrStatus() {
		return this.krStatus;
	}

	public void setKrStatus(String krStatus) {
		this.krStatus = krStatus;
	}

	public String getKrRcvstime() {
		return this.krRcvstime;
	}

	public void setKrRcvstime(String krRcvstime) {
		this.krRcvstime = krRcvstime;
	}

	public String getKrRcvetime() {
		return this.krRcvetime;
	}

	public void setKrRcvetime(String krRcvetime) {
		this.krRcvetime = krRcvetime;
	}

	public String getKrUnpackstime() {
		return this.krUnpackstime;
	}

	public void setKrUnpackstime(String krUnpackstime) {
		this.krUnpackstime = krUnpackstime;
	}

	public String getKrUnpacketime() {
		return this.krUnpacketime;
	}

	public void setKrUnpacketime(String krUnpacketime) {
		this.krUnpacketime = krUnpacketime;
	}

	public String getKrPackfile() {
		return this.krPackfile;
	}

	public void setKrPackfile(String krPackfile) {
		this.krPackfile = krPackfile;
	}
}