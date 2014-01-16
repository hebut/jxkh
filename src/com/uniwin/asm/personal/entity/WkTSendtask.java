package com.uniwin.asm.personal.entity;

/**
 * WkTSendtask entity. @author MyEclipse Persistence Tools
 */
public class WkTSendtask implements java.io.Serializable {
	// Fields
	private Long ksTaskid;
	private String ksInfoid;
	private String ksSendtype;
	private String ksInfotype;
	private String ksPackid;
	private String ksStatus;
	private Long ksSendpos;
	private String ksInfotime;
	private String ksPacktime;
	private String ksSendstime;
	private String ksSendetime;
	private String ksPackfile;
	private String ksServerurl;
	private String ksUser;
	private String ksPasswd;

	// Constructors
	/** default constructor */
	public WkTSendtask() {
	}

	/** minimal constructor */
	public WkTSendtask(Long ksTaskid) {
		this.ksTaskid = ksTaskid;
	}

	/** full constructor */
	public WkTSendtask(Long ksTaskid, String ksInfoid, String ksSendtype, String ksInfotype, String ksPackid, String ksStatus, Long ksSendpos, String ksInfotime, String ksPacktime, String ksSendstime, String ksSendetime, String ksPackfile, String ksServerurl, String ksUser, String ksPasswd) {
		this.ksTaskid = ksTaskid;
		this.ksInfoid = ksInfoid;
		this.ksSendtype = ksSendtype;
		this.ksInfotype = ksInfotype;
		this.ksPackid = ksPackid;
		this.ksStatus = ksStatus;
		this.ksSendpos = ksSendpos;
		this.ksInfotime = ksInfotime;
		this.ksPacktime = ksPacktime;
		this.ksSendstime = ksSendstime;
		this.ksSendetime = ksSendetime;
		this.ksPackfile = ksPackfile;
		this.ksServerurl = ksServerurl;
		this.ksUser = ksUser;
		this.ksPasswd = ksPasswd;
	}

	// Property accessors
	public Long getKsTaskid() {
		return this.ksTaskid;
	}

	public void setKsTaskid(Long ksTaskid) {
		this.ksTaskid = ksTaskid;
	}

	public String getKsInfoid() {
		return this.ksInfoid;
	}

	public void setKsInfoid(String ksInfoid) {
		this.ksInfoid = ksInfoid;
	}

	public String getKsSendtype() {
		return this.ksSendtype;
	}

	public void setKsSendtype(String ksSendtype) {
		this.ksSendtype = ksSendtype;
	}

	public String getKsInfotype() {
		return this.ksInfotype;
	}

	public void setKsInfotype(String ksInfotype) {
		this.ksInfotype = ksInfotype;
	}

	public String getKsPackid() {
		return this.ksPackid;
	}

	public void setKsPackid(String ksPackid) {
		this.ksPackid = ksPackid;
	}

	public String getKsStatus() {
		return this.ksStatus;
	}

	public void setKsStatus(String ksStatus) {
		this.ksStatus = ksStatus;
	}

	public Long getKsSendpos() {
		return this.ksSendpos;
	}

	public void setKsSendpos(Long ksSendpos) {
		this.ksSendpos = ksSendpos;
	}

	public String getKsInfotime() {
		return this.ksInfotime;
	}

	public void setKsInfotime(String ksInfotime) {
		this.ksInfotime = ksInfotime;
	}

	public String getKsPacktime() {
		return this.ksPacktime;
	}

	public void setKsPacktime(String ksPacktime) {
		this.ksPacktime = ksPacktime;
	}

	public String getKsSendstime() {
		return this.ksSendstime;
	}

	public void setKsSendstime(String ksSendstime) {
		this.ksSendstime = ksSendstime;
	}

	public String getKsSendetime() {
		return this.ksSendetime;
	}

	public void setKsSendetime(String ksSendetime) {
		this.ksSendetime = ksSendetime;
	}

	public String getKsPackfile() {
		return this.ksPackfile;
	}

	public void setKsPackfile(String ksPackfile) {
		this.ksPackfile = ksPackfile;
	}

	public String getKsServerurl() {
		return this.ksServerurl;
	}

	public void setKsServerurl(String ksServerurl) {
		this.ksServerurl = ksServerurl;
	}

	public String getKsUser() {
		return this.ksUser;
	}

	public void setKsUser(String ksUser) {
		this.ksUser = ksUser;
	}

	public String getKsPasswd() {
		return this.ksPasswd;
	}

	public void setKsPasswd(String ksPasswd) {
		this.ksPasswd = ksPasswd;
	}
}