package org.iti.xypt.personal.infoExtract.entity;

/**
 * WkTExtractask entity. @author MyEclipse Persistence Tools
 */

public class WkTExtractask implements java.io.Serializable {

	// Fields
	
	private Long keId;
	private Long ktaId;
	private String keName;
	private String keRemk;
	private String keBeginurl;
	private String keUrlcount;
	private String keBegincount;
	private String keTime;
	private Long keStatus;
	private Long kePubtype;
	private Long keDefinite;
	// Constructors
	private String keAudit;
	

	public String getKeAudit() {
		return keAudit;
	}

	public void setKeAudit(String keAudit) {
		this.keAudit = keAudit;
	}

	public String getKePerssion() {
		return kePerssion;
	}

	public void setKePerssion(String kePerssion) {
		this.kePerssion = kePerssion;
	}

	public String getKeInfosource() {
		return keInfosource;
	}

	public void setKeInfosource(String keInfosource) {
		this.keInfosource = keInfosource;
	}

	private String keInfosource;
	private String kePerssion;
	
	public  final String BEGIN="开始";
	public  final String END="停止";
	public  final String TIME_TASK="定时任务";
	
	public Long getKeDefinite() {
		return keDefinite;
	}

	public void setKeDefinite(Long keDefinite) {
		this.keDefinite = keDefinite;
	}

	public Long getKePubtype() {
		return kePubtype;
	}

	public void setKePubtype(Long kePubtype) {
		this.kePubtype = kePubtype;
	}

	/** default constructor */
	public WkTExtractask() {
	}

	/** minimal constructor */
	public WkTExtractask(Long keId,Long ktaId) {
		this.ktaId = ktaId;
		this.keId= keId;
	}

	/** full constructor */
	public WkTExtractask(Long keId,Long ktaId, String keName, String keRemk,
			String keBeginurl, String keUrlcount, String keBegincount,
			String keTime, Long keStatus,Long kePubtype,Long keDefinite,String keAudit,String keInfosource,String kePerssion) {
		this.keId= keId;
		this.ktaId = ktaId;
		this.keName = keName;
		this.keRemk = keRemk;
		this.keBeginurl = keBeginurl;
		this.keUrlcount = keUrlcount;
		this.keBegincount = keBegincount;
		this.keTime = keTime;
		this.keStatus = keStatus;
		this.kePubtype=kePubtype;
		this.keDefinite=keDefinite;
		this.keAudit=keAudit;
		this.keInfosource=keInfosource;
		this.kePerssion=kePerssion;
	}

	// Property accessors

	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public Long getKtaId() {
		return this.ktaId;
	}

	public void setKtaId(Long ktaId) {
		this.ktaId = ktaId;
	}

	public String getKeName() {
		return this.keName;
	}

	public void setKeName(String keName) {
		this.keName = keName;
	}

	public String getKeRemk() {
		return this.keRemk;
	}

	public void setKeRemk(String keRemk) {
		this.keRemk = keRemk;
	}

	public String getKeBeginurl() {
		return this.keBeginurl;
	}

	public void setKeBeginurl(String keBeginurl) {
		this.keBeginurl = keBeginurl;
	}

	public String getKeUrlcount() {
		return this.keUrlcount;
	}

	public void setKeUrlcount(String keUrlcount) {
		this.keUrlcount = keUrlcount;
	}

	public String getKeBegincount() {
		return this.keBegincount;
	}

	public void setKeBegincount(String keBegincount) {
		this.keBegincount = keBegincount;
	}

	public String getKeTime() {
		return this.keTime;
	}

	public void setKeTime(String keTime) {
		this.keTime = keTime;
	}

	public Long getKeStatus() {
		return this.keStatus;
	}

	public void setKeStatus(Long keStatus) {
		this.keStatus = keStatus;
	}

}