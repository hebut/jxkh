package com.uniwin.asm.personal.entity;

/**
 * WkTUsrfav entity. @author MyEclipse Persistence Tools
 */
public class WkTUsrfav implements java.io.Serializable {
	// Fields
	private Long kufId;
	private Long kuId;
	private String ktId;
	private String kufName;
	private String kufUrl;

	// Constructors
	/** default constructor */
	public WkTUsrfav() {
	}

	/** minimal constructor */
	public WkTUsrfav(Long kufId, Long kuId, String ktId) {
		this.kufId = kufId;
		this.kuId = kuId;
		this.ktId = ktId;
	}

	/** full constructor */
	public WkTUsrfav(Long kufId, Long kuId, String ktId, String kufName, String kufUrl) {
		this.kufId = kufId;
		this.kuId = kuId;
		this.ktId = ktId;
		this.kufName = kufName;
		this.kufUrl = kufUrl;
	}

	// Property accessors
	public Long getKufId() {
		return this.kufId;
	}

	public void setKufId(Long kufId) {
		this.kufId = kufId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKtId() {
		return this.ktId;
	}

	public void setKtId(String ktId) {
		this.ktId = ktId;
	}

	public String getKufName() {
		return this.kufName;
	}

	public void setKufName(String kufName) {
		this.kufName = kufName;
	}

	public String getKufUrl() {
		return this.kufUrl;
	}

	public void setKufUrl(String kufUrl) {
		this.kufUrl = kufUrl;
	}
}