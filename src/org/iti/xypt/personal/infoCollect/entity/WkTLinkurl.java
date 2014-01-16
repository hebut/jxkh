package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTLinkurl entity. @author MyEclipse Persistence Tools
 */

public class WkTLinkurl implements java.io.Serializable {

	// Fields

	private Long klId;
	private Long keId;
	private String klUrl;
	private String klTime;
	private Long klCount;
	private Long klStatus;

	// Constructors

	/** default constructor */
	public WkTLinkurl() {
	}

	/** minimal constructor */
	public WkTLinkurl(Long klId,Long keId) {
this.klId=klId;
		this.keId = keId;
	}

	/** full constructor */
	public WkTLinkurl(Long klId,Long keId, String klUrl, String klTime, Long klCount,
			Long klStatus) {
this.klId=klId;
		this.keId = keId;
		this.klUrl = klUrl;
		this.klTime = klTime;
		this.klCount = klCount;
		this.klStatus = klStatus;
	}

	// Property accessors

	public Long getKlId() {
		return this.klId;
	}

	public void setKlId(Long klId) {
		this.klId = klId;
	}

	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public String getKlUrl() {
		return this.klUrl;
	}

	public void setKlUrl(String klUrl) {
		this.klUrl = klUrl;
	}

	public String getKlTime() {
		return this.klTime;
	}

	public void setKlTime(String klTime) {
		this.klTime = klTime;
	}

	public Long getKlCount() {
		return this.klCount;
	}

	public void setKlCount(Long klCount) {
		this.klCount = klCount;
	}

	public Long getKlStatus() {
		return this.klStatus;
	}

	public void setKlStatus(Long klStatus) {
		this.klStatus = klStatus;
	}

}