package com.uniwin.framework.entity;

/**
 * WkTTitle entity. @author MyEclipse Persistence Tools
 */
public class WkTTitle implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	public static final String TYPE_MR = "21";
	public static final String TYPE_ZD = "22";
	public static final String SHOW_DEPT = "1";
	private Long ktId;
	private Long ksId;
	private String ktName; 
	private Long ktPid;
	private String ktContent;
	private String ktType;
	private String ktNewwin;
	private String ktSecurity;
	private Long ktOrdno;
	private String ktImage;
	private Long ktHits;
	private String ktMemo;
	// ²ã´Î
	private Integer dep;

	// Constructors
	/** default constructor */
	public WkTTitle() {
	}

	/** minimal constructor */
	public WkTTitle(Long ktId, Long ksId, String ktName, Long ktPid) {
		this.ktId = ktId;
		this.ksId = ksId;
		this.ktName = ktName;
		this.ktPid = ktPid;
	}

	/** full constructor */
	public WkTTitle(Long ktId, Long ksId, String ktName, Long ktPid, String ktContent, String ktType, String ktNewwin, String ktSecurity, Long ktOrdno, String ktImage, Long ktHits, String ktMemo) {
		this.ktId = ktId;
		this.ksId = ksId;
		this.ktName = ktName;
		this.ktPid = ktPid;
		this.ktContent = ktContent;
		this.ktType = ktType;
		this.ktNewwin = ktNewwin;
		this.ktSecurity = ktSecurity;
		this.ktOrdno = ktOrdno;
		this.ktImage = ktImage;
		this.ktHits = ktHits;
		this.ktMemo = ktMemo;
	}

	// Property accessors
	public Long getKtId() {
		return this.ktId;
	}

	public void setKtId(Long ktId) {
		this.ktId = ktId;
	}

	public Long getKsId() {
		return this.ksId;
	}

	public void setKsId(Long ksId) {
		this.ksId = ksId;
	}

	public String getKtName() {
		return this.ktName;
	}

	public void setKtName(String ktName) {
		this.ktName = ktName;
	}

	public Long getKtPid() {
		return this.ktPid;
	}

	public void setKtPid(Long ktPid) {
		this.ktPid = ktPid;
	}

	public String getKtContent() {
		return this.ktContent;
	}

	public void setKtContent(String ktContent) {
		this.ktContent = ktContent;
	}

	public String getKtType() {
		return this.ktType;
	}

	public void setKtType(String ktType) {
		this.ktType = ktType;
	}

	public String getKtNewwin() {
		return this.ktNewwin;
	}

	public void setKtNewwin(String ktNewwin) {
		this.ktNewwin = ktNewwin;
	}

	public String getKtSecurity() {
		return this.ktSecurity;
	}

	public void setKtSecurity(String ktSecurity) {
		this.ktSecurity = ktSecurity;
	}

	public Long getKtOrdno() {
		return this.ktOrdno;
	}

	public void setKtOrdno(Long ktOrdno) {
		this.ktOrdno = ktOrdno;
	}

	public String getKtImage() {
		return this.ktImage;
	}

	public void setKtImage(String ktImage) {
		this.ktImage = ktImage;
	}

	public Long getKtHits() {
		return this.ktHits;
	}

	public void setKtHits(Long ktHits) {
		this.ktHits = ktHits;
	}

	public String getKtMemo() {
		return this.ktMemo;
	}

	public void setKtMemo(String ktMemo) {
		this.ktMemo = ktMemo;
	}

	public Integer getDep() {
		return dep;
	}

	public void setDep(Integer dep) {
		this.dep = dep;
	}
}