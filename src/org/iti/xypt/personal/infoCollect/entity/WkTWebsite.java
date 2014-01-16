package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFlog entity. @author MyEclipse Persistence Tools
 */

public class WkTWebsite implements java.io.Serializable {

	// Fields

	private Long kwId;
	private Long kwPid;
	private String  kwName;
	private String kwStatus;
	private String kwVersion;
	private String kwEpname;
	private String kwUrl;
	private String kwGenmgr;
	private String kwPhone;
	private String kwFax;
	private String kwEmail;
	private String kwPostid;
	private String kwAddress;
	private String kwStyle;
	private String all;
	private Integer Dep;
	
	private String kwLocation;

	// Constructors

	/** default constructor */
	public WkTWebsite() {
	}

	/** minimal constructor */
	public WkTWebsite(Long kwId,Long kwPid) {
		this.kwId = kwId;
		this.kwPid=kwPid;
	}

	/** full constructor */
	public WkTWebsite(Long kwId, Long kwPid, String  kwName, String kwStatus,
	 String kwVersion,
	 String kwEpname,
	 String kwGenmgr,
	String kwPhone,
	String kwUrl,
	 String kwFax,
	 String kwEmail,
	 String kwPostid,
	 String kwAddress,
	String kwStyle,
	String all,
	String kwLocation) {
		this.kwId = kwId;
		this.kwPid = kwPid;
		this.kwName = kwName;
		this.kwStatus = kwStatus;
		this.kwVersion = kwVersion;
		this.kwEpname = kwEpname;
		this.kwGenmgr = kwGenmgr;
		this.kwPhone = kwPhone;
		this.kwFax = kwFax;
		this.kwEmail = kwEmail;
		this.kwPostid = kwPostid;
		this.kwAddress = kwAddress;
		this.kwStyle=kwStyle;
		this.kwUrl=kwUrl;
		this.kwLocation = kwLocation;
	}

	// Property accessors

	public String getKwLocation() {
		return kwLocation;
	}

	public void setKwLocation(String kwLocation) {
		this.kwLocation = kwLocation;
	}

	public Long getKwId() {
		return this.kwId;
	}

	public void setKwId(Long kwId) {
		this.kwId = kwId;
	}

	public Long getkwPid() {
		return this.kwPid;
	}

	public void setkwPid(Long kwPid) {
		this.kwPid =kwPid;
	}

	public String getkwName() {
		return this.kwName;
	}

	public void setkwName(String kwName) {
		this.kwName = kwName;
	}

	public String getkwStatus() {
		return this.kwStatus;
	}

	public void setkwUrl(String kwUrl) {
		this.kwUrl = kwUrl;
	}
	public String getkwUrl() {
		return this.kwUrl;
	}

	public void setkwStatus(String kwStatus) {
		this.kwStatus = kwStatus;
	}

	public String getkwVersion() {
		return this.kwVersion;
	}

	public void setkwVersion(String kwVersion) {
		this.kwVersion = kwVersion;
	}

	public String getkwEpname() {
		return this.kwEpname;
	}

	public void setkwEpname(String kwEpname) {
		this.kwEpname = kwEpname;
	}

	public String getkwGenmgr() {
		return this.kwGenmgr;
	}

	public void setkwGenmgr(String kwGenmgr) {
		this.kwGenmgr = kwGenmgr;
	}
	public String getkwPhone() {
		return this.kwPhone;
	}

	public void setkwPhone(String kwPhone) {
		this.kwPhone = kwPhone;
	}
	public String getkwFax() {
		return this.kwFax;
	}

	public void setkwFax(String kwFax) {
		this.kwFax = kwFax;
	}
	public String getkwEmail() {
		return this.kwEmail;
	}

	public void setkwEmail(String kwEmail) {
		this.kwEmail = kwEmail;
	}
	public String getkwPostid() {
		return this.kwPostid;
	}

	public void setkwPostid(String kwPostid) {
		this.kwPostid = kwPostid;
	}
	public String getkwAddress() {
		return this.kwAddress;
	}

	public void setkwAddress(String kwAddress) {
		this.kwAddress = kwAddress;
	}
	public String getkwStyle() {
		return this.kwStyle;
	}

	public void setkwStyle(String kwStyle) {
		this.kwStyle = kwStyle;
	}

	public Integer getDep() {
		return Dep;
	}

	public void setDep(Integer dep) {
		Dep = dep;
	}
}