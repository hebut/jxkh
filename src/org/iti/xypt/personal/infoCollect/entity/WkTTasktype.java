package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTTasktype entity. @author MyEclipse Persistence Tools
 */

public class WkTTasktype implements java.io.Serializable {

	// Fields

	private Long ktaId;
	private Long ktaPid;
	private String ktaName;
	private String ktaTemp;
	private Long ktaOrdno;
	private Long ktaAudit;
	private String ktaDesc;
	private Integer Dep;

	// Constructors

	/** default constructor */
	public WkTTasktype() {
	}

	/** minimal constructor */
	public WkTTasktype(Long ktaId,Long ktaPid) {
                this.ktaId=ktaId;
		this.ktaPid = ktaPid;
	}

	/** full constructor */
	public WkTTasktype(Long ktaId,Long ktaPid, String ktaName, String ktaTemp,
			Long ktaOrdno, Long ktaAudit, String ktaDesc) {
this.ktaId=ktaId;
		this.ktaPid = ktaPid;
		this.ktaName = ktaName;
		this.ktaTemp = ktaTemp;
		this.ktaOrdno = ktaOrdno;
		this.ktaAudit = ktaAudit;
		this.ktaDesc = ktaDesc;
	}

	// Property accessors

	public Long getKtaId() {
		return this.ktaId;
	}

	public void setKtaId(Long ktaId) {
		this.ktaId = ktaId;
	}

	public Long getKtaPid() {
		return this.ktaPid;
	}

	public void setKtaPid(Long ktaPid) {
		this.ktaPid = ktaPid;
	}

	public String getKtaName() {
		return this.ktaName;
	}

	public void setKtaName(String ktaName) {
		this.ktaName = ktaName;
	}

	public String getKtaTemp() {
		return this.ktaTemp;
	}

	public void setKtaTemp(String ktaTemp) {
		this.ktaTemp = ktaTemp;
	}

	public Long getKtaOrdno() {
		return this.ktaOrdno;
	}

	public void setKtaOrdno(Long ktaOrdno) {
		this.ktaOrdno = ktaOrdno;
	}

	public Long getKtaAudit() {
		return this.ktaAudit;
	}

	public void setKtaAudit(Long ktaAudit) {
		this.ktaAudit = ktaAudit;
	}

	public String getKtaDesc() {
		return this.ktaDesc;
	}

	public void setKtaDesc(String ktaDesc) {
		this.ktaDesc = ktaDesc;
	}
	public Integer getDep() {
		return Dep;
	}

	public void setDep(Integer dep) {
		Dep = dep;
	}

}