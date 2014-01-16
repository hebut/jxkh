package com.uniwin.framework.entity;

/**
 * WkTMlog entity. @author MyEclipse Persistence Tools
 */
public class WkTMlog implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	public static final String FUNC_ADMIN = "admin";
	public static final String FUNC_CMS = "cms";
	private Long kmlId;
	private String kmlTime;
	private String kmlIp;
	private Long kuId;
	private String kuName;
	private String kmlFunc;
	private String kmlDesc;

	// Constructors
	/** default constructor */
	public WkTMlog() {
	}

	/** minimal constructor */
	public WkTMlog(Long kmlId) {
		this.kmlId = kmlId;
	}

	/** full constructor */
	public WkTMlog(Long kmlId, String kmlTime, String kmlIp, Long kuId, String kuName, String kmlFunc, String kmlDesc) {
		this.kmlId = kmlId;
		this.kmlTime = kmlTime;
		this.kmlIp = kmlIp;
		this.kuId = kuId;
		this.kuName = kuName;
		this.kmlFunc = kmlFunc;
		this.kmlDesc = kmlDesc;
	}

	// Property accessors
	public Long getKmlId() {
		return this.kmlId;
	}

	public void setKmlId(Long kmlId) {
		this.kmlId = kmlId;
	}

	public String getKmlTime() {
		return this.kmlTime;
	}

	public void setKmlTime(String kmlTime) {
		this.kmlTime = kmlTime;
	}

	public String getKmlIp() {
		return this.kmlIp;
	}

	public void setKmlIp(String kmlIp) {
		this.kmlIp = kmlIp;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKuName() {
		return this.kuName;
	}

	public void setKuName(String kuName) {
		this.kuName = kuName;
	}

	public String getKmlFunc() {
		return this.kmlFunc;
	}

	public void setKmlFunc(String kmlFunc) {
		this.kmlFunc = kmlFunc;
	}

	public String getKmlDesc() {
		return this.kmlDesc;
	}

	public void setKmlDesc(String kmlDesc) {
		this.kmlDesc = kmlDesc;
	}
}