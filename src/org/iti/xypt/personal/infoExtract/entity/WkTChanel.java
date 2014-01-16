package org.iti.xypt.personal.infoExtract.entity;

/**
 * WkTChanel entity. @author MyEclipse Persistence Tools
 */

public class WkTChanel implements java.io.Serializable {

	// Fields

	private Long kcId;
	private Long kwId;
	private Long kcPid;
	public Long kcKind;
	private String kcTplist;
	private String kcTpdtl;
	private Long kcOrdno;
	private String kcStatus;
	private String kcName;
	private String kcTable;
	private String kcImage;
	private Long kfId;
	private String kcDesc;
	private Integer kcClick;
	private String kcTime;
	private Long kcScore;
	private Long kcScodep;
	private String fields;
	/**
	 * ²ã´Î
	 */
	private Integer Dep;

	// Constructors

	/** default constructor */
	public WkTChanel() {
	}

	/** minimal constructor */
	public WkTChanel(Long kcId, Long kwId, Long kcPid) {
		this.kcId = kcId;
		this.kwId = kwId;
		this.kcPid = kcPid;
	}

	/** old constructor */
	public WkTChanel(Long kcId, Long ksId, Long kcPid, String kcTplist,
			String kcTpdtl, Long kcOrdno, String kcStatus,Long kcKind, String kcName,
			String kcTable, String kcImage, Long kfId, String kcDesc,Integer kcClick,String kcTime,
			Long kcScore,Long kcScodep) {
		this.kcId = kcId;
		this.kwId = kwId;
		this.kcPid = kcPid;
		this.kcTplist = kcTplist;
		this.kcTpdtl = kcTpdtl;
		this.kcOrdno = kcOrdno;
		this.kcStatus = kcStatus;
		this.kcName = kcName;
		this.kcTable = kcTable;
		this.kcImage = kcImage;
		this.kfId = kfId;
		this.kcDesc = kcDesc;
		this.kcKind=kcKind;
		this.kcClick=kcClick;
		this.kcTime=kcTime;
		this.kcScore=kcScore;
		this.kcScodep=kcScodep;
	}
	
	/** full constructor */
	public WkTChanel(Long kcId, Long ksId, Long kcPid, String kcTplist,
			String kcTpdtl, Long kcOrdno, String kcStatus,Long kcKind, String kcName,
			String kcTable, String kcImage, Long kfId, String kcDesc,Integer kcClick,String kcTime,
			Long kcScore,Long kcScodep,String fields) {
		this.kcId = kcId;
		this.kwId = kwId;
		this.kcPid = kcPid;
		this.kcTplist = kcTplist;
		this.kcTpdtl = kcTpdtl;
		this.kcOrdno = kcOrdno;
		this.kcStatus = kcStatus;
		this.kcName = kcName;
		this.kcTable = kcTable;
		this.kcImage = kcImage;
		this.kfId = kfId;
		this.kcDesc = kcDesc;
		this.kcKind=kcKind;
		this.kcClick=kcClick;
		this.kcTime=kcTime;
		this.kcScore=kcScore;
		this.kcScodep=kcScodep;
		this.fields=fields;
	}

	// Property accessors

	public Long getKcId() {
		return this.kcId;
	}

	public void setKcId(Long kcId) {
		this.kcId = kcId;
	}
	public Long getKcKind() {
		return this.kcKind;
	}

	public void setKcKind(Long kcKind) {
		this.kcKind = kcKind;
	}

	public Long getKwId() {
		return this.kwId;
	}

	public void setKwId(Long kwId) {
		this.kwId = kwId;
	}

	public Long getKcPid() {
		return this.kcPid;
	}

	public void setKcPid(Long kcPid) {
		this.kcPid = kcPid;
	}

	public String getKcTplist() {
		return this.kcTplist;
	}

	public void setKcTplist(String kcTplist) {
		this.kcTplist = kcTplist;
	}

	public String getKcTpdtl() {
		return this.kcTpdtl;
	}

	public void setKcTpdtl(String kcTpdtl) {
		this.kcTpdtl = kcTpdtl;
	}

	public Long getKcOrdno() {
		return this.kcOrdno;
	}

	public void setKcOrdno(Long kcOrdno) {
		this.kcOrdno = kcOrdno;
	}

	public String getKcStatus() {
		return this.kcStatus;
	}

	public void setKcStatus(String kcStatus) {
		this.kcStatus = kcStatus;
	}

	public String getKcName() {
		return this.kcName;
	}

	public void setKcName(String kcName) {
		this.kcName = kcName;
	}

	public String getKcTable() {
		return this.kcTable;
	}

	public void setKcTable(String kcTable) {
		this.kcTable = kcTable;
	}

	public String getKcImage() {
		return this.kcImage;
	}

	public void setKcImage(String kcImage) {
		this.kcImage = kcImage;
	}

	public Long getKfId() {
		return this.kfId;
	}

	public void setKfId(Long kfId) {
		this.kfId = kfId;
	}
public Long getKcScodep()
{
	return this.kcScodep;
}
public void setKcScodep(Long kcScodep) 
{
	this.kcScodep = kcScodep;
}
	public String getKcDesc() {
		return this.kcDesc;
	}

	public void setKcDesc(String kcDesc) {
		this.kcDesc = kcDesc;
	}

	public Integer getDep() {
		return Dep;
	}

	public void setDep(Integer dep) {
		Dep = dep;
	}
	public Integer getKcClick() {
		return this.kcClick;
	}

	public void setKcClick(Integer kcClick) {
		this.kcClick = kcClick;
	}
	public Long getKcScore() {
		return this.kcScore;
	}

	public void setKcScore(Long kcScore) {
		this.kcScore = kcScore;
	}
	public String getKcTime() {
		return this.kcTime;
	}

	public void setKcTime(String kcTime) {
		this.kcTime = kcTime;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
	
}