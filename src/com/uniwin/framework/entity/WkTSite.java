package com.uniwin.framework.entity;

/**
 * WkTSite entity. @author MyEclipse Persistence Tools
 */
public class WkTSite implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long ksId;
	private String ksEpid;
	private String ksVersion;
	private String ksRegcode;
	private String ksEpname;
	private String ksGenmgr;
	private String ksPhone;
	private String ksFax;
	private String ksEmail;
	private String ksPostid;
	private String ksAddress;
	private String ksStyle;
	private String ksPassword;
	private String ksMac;
	private String ksEndtime;

	private String ksLid;
	public String getKsLid() {
		return ksLid;
	}

	public void setKsLid(String ksLid) {
		this.ksLid = ksLid;
	}

	public String getKsPassword() {
		return ksPassword;
	}

	public void setKsPassword(String ksPassword) {
		this.ksPassword = ksPassword;
	}

	public String getKsMac() {
		return ksMac;
	}

	public void setKsMac(String ksMac) {
		this.ksMac = ksMac;
	}

	public String getKsEndtime() {
		return ksEndtime;
	}

	public void setKsEndtime(String ksEndtime) {
		this.ksEndtime = ksEndtime;
	}

	

	// Constructors
	/** default constructor */
	public WkTSite() {
	}

	/** minimal constructor */
	public WkTSite(Long ksId, String ksEpid, String ksStyle) {
		this.ksId = ksId;
		this.ksEpid = ksEpid;
		this.ksStyle = ksStyle;
	}

	/** full constructor */
	public WkTSite(Long ksId, String ksEpid, String ksVersion, String ksRegcode, String ksEpname, String ksGenmgr, String ksPhone, String ksFax, String ksEmail, String ksPostid, String ksAddress, String ksStyle ,String ksList ,String ksPassword , String ksMac,String ksEndtime) {
		this.ksId = ksId;
		this.ksEpid = ksEpid;
		this.ksVersion = ksVersion;
		this.ksRegcode = ksRegcode;
		this.ksEpname = ksEpname;
		this.ksGenmgr = ksGenmgr;
		this.ksPhone = ksPhone;
		this.ksFax = ksFax;
		this.ksEmail = ksEmail;
		this.ksPostid = ksPostid;
		this.ksAddress = ksAddress;
		this.ksStyle = ksStyle;
		this.ksPassword=ksPassword;
		this.ksLid = ksLid;
		this.ksMac =ksMac;
		this.ksEndtime = ksEndtime;
	}

	// Property accessors
	public Long getKsId() {
		return this.ksId;
	}

	public void setKsId(Long ksId) {
		this.ksId = ksId;
	}

	public String getKsEpid() {
		return this.ksEpid;
	}

	public void setKsEpid(String ksEpid) {
		this.ksEpid = ksEpid;
	}

	public String getKsVersion() {
		return this.ksVersion;
	}

	public void setKsVersion(String ksVersion) {
		this.ksVersion = ksVersion;
	}

	public String getKsRegcode() {
		return this.ksRegcode;
	}

	public void setKsRegcode(String ksRegcode) {
		this.ksRegcode = ksRegcode;
	}

	public String getKsEpname() {
		return this.ksEpname;
	}

	public void setKsEpname(String ksEpname) {
		this.ksEpname = ksEpname;
	}

	public String getKsGenmgr() {
		return this.ksGenmgr;
	}

	public void setKsGenmgr(String ksGenmgr) {
		this.ksGenmgr = ksGenmgr;
	}

	public String getKsPhone() {
		return this.ksPhone;
	}

	public void setKsPhone(String ksPhone) {
		this.ksPhone = ksPhone;
	}

	public String getKsFax() {
		return this.ksFax;
	}

	public void setKsFax(String ksFax) {
		this.ksFax = ksFax;
	}

	public String getKsEmail() {
		return this.ksEmail;
	}

	public void setKsEmail(String ksEmail) {
		this.ksEmail = ksEmail;
	}

	public String getKsPostid() {
		return this.ksPostid;
	}

	public void setKsPostid(String ksPostid) {
		this.ksPostid = ksPostid;
	}

	public String getKsAddress() {
		return this.ksAddress;
	}

	public void setKsAddress(String ksAddress) {
		this.ksAddress = ksAddress;
	}

	public String getKsStyle() {
		return this.ksStyle;
	}

	public void setKsStyle(String ksStyle) {
		this.ksStyle = ksStyle;
	}
}