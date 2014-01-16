package org.iti.gh.entity;

/**
 * GhQkdc entity. @author MyEclipse Persistence Tools
 */

public class GhQkdc implements java.io.Serializable {

	
	public static final String BEFORE10 = "截至到2010年7月情况";
	public static final String BETWEEN11TO15 = "2011-2015目标";
	public static final String AT2011 = "2011年";
	public static final String AT2012 = "2012年";
	public static final String AT2013 = "2013年";
	public static final String AT2014 = "2014年";
	public static final String AT2015 = "2015年";
	// Fields

	private Long qkId;
	private Long kuId;
	private String kyxmGj;
	private String kyxmSb;
	private String kyxmHx;
	private String kyxmQt;
	private String kycgLw;
	private String kycgZl;
	private String kyjlGj;
	private String kyjlSb;
	private String kyjlQt;
	private String kyhzGj;
	private String kyhzGn;
	private String kyhzHf;
	private String kyhzQt;
	private String jxcgGj;
	private String jxcgSb;
	private String jxcgCb;
	private String jxcgQt;
	private String nf;

	// Constructors

	/** default constructor */
	public GhQkdc() {
	}

	/** minimal constructor */
	public GhQkdc(Long qkId, Long kuId) {
		this.qkId = qkId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhQkdc(Long qkId, Long kuId, String kyxmGj, String kyxmSb,
			String kyxmHx, String kyxmQt, String kycgLw, String kycgZl,
			String kyjlGj, String kyjlSb, String kyjlQt, String kyhzGj,
			String kyhzGn, String kyhzHf, String kyhzQt, String jxcgGj,
			String jxcgSb, String jxcgCb, String jxcgQt, String nf) {
		this.qkId = qkId;
		this.kuId = kuId;
		this.kyxmGj = kyxmGj;
		this.kyxmSb = kyxmSb;
		this.kyxmHx = kyxmHx;
		this.kyxmQt = kyxmQt;
		this.kycgLw = kycgLw;
		this.kycgZl = kycgZl;
		this.kyjlGj = kyjlGj;
		this.kyjlSb = kyjlSb;
		this.kyjlQt = kyjlQt;
		this.kyhzGj = kyhzGj;
		this.kyhzGn = kyhzGn;
		this.kyhzHf = kyhzHf;
		this.kyhzQt = kyhzQt;
		this.jxcgGj = jxcgGj;
		this.jxcgSb = jxcgSb;
		this.jxcgCb = jxcgCb;
		this.jxcgQt = jxcgQt;
		this.nf = nf;
	}

	// Property accessors

	public Long getQkId() {
		return this.qkId;
	}

	public void setQkId(Long qkId) {
		this.qkId = qkId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKyxmGj() {
		return this.kyxmGj;
	}

	public void setKyxmGj(String kyxmGj) {
		this.kyxmGj = kyxmGj;
	}

	public String getKyxmSb() {
		return this.kyxmSb;
	}

	public void setKyxmSb(String kyxmSb) {
		this.kyxmSb = kyxmSb;
	}

	public String getKyxmHx() {
		return this.kyxmHx;
	}

	public void setKyxmHx(String kyxmHx) {
		this.kyxmHx = kyxmHx;
	}

	public String getKyxmQt() {
		return this.kyxmQt;
	}

	public void setKyxmQt(String kyxmQt) {
		this.kyxmQt = kyxmQt;
	}

	public String getKycgLw() {
		return this.kycgLw;
	}

	public void setKycgLw(String kycgLw) {
		this.kycgLw = kycgLw;
	}

	public String getKycgZl() {
		return this.kycgZl;
	}

	public void setKycgZl(String kycgZl) {
		this.kycgZl = kycgZl;
	}

	public String getKyjlGj() {
		return this.kyjlGj;
	}

	public void setKyjlGj(String kyjlGj) {
		this.kyjlGj = kyjlGj;
	}

	public String getKyjlSb() {
		return this.kyjlSb;
	}

	public void setKyjlSb(String kyjlSb) {
		this.kyjlSb = kyjlSb;
	}

	public String getKyjlQt() {
		return this.kyjlQt;
	}

	public void setKyjlQt(String kyjlQt) {
		this.kyjlQt = kyjlQt;
	}

	public String getKyhzGj() {
		return this.kyhzGj;
	}

	public void setKyhzGj(String kyhzGj) {
		this.kyhzGj = kyhzGj;
	}

	public String getKyhzGn() {
		return this.kyhzGn;
	}

	public void setKyhzGn(String kyhzGn) {
		this.kyhzGn = kyhzGn;
	}

	public String getKyhzHf() {
		return this.kyhzHf;
	}

	public void setKyhzHf(String kyhzHf) {
		this.kyhzHf = kyhzHf;
	}

	public String getKyhzQt() {
		return this.kyhzQt;
	}

	public void setKyhzQt(String kyhzQt) {
		this.kyhzQt = kyhzQt;
	}

	public String getJxcgGj() {
		return this.jxcgGj;
	}

	public void setJxcgGj(String jxcgGj) {
		this.jxcgGj = jxcgGj;
	}

	public String getJxcgSb() {
		return this.jxcgSb;
	}

	public void setJxcgSb(String jxcgSb) {
		this.jxcgSb = jxcgSb;
	}

	public String getJxcgCb() {
		return this.jxcgCb;
	}

	public void setJxcgCb(String jxcgCb) {
		this.jxcgCb = jxcgCb;
	}

	public String getJxcgQt() {
		return this.jxcgQt;
	}

	public void setJxcgQt(String jxcgQt) {
		this.jxcgQt = jxcgQt;
	}

	public String getNf() {
		return this.nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

}