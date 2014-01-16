package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTInfo entity. @author MyEclipse Persistence Tools
 */

public class WkTInfo implements java.io.Serializable {

	// Fields

	private Long kiId;
	private Long keId;
	private String kiTitle;
	private String kiTitle2;
	private String kiSource;
	private String kiAuthname;
	private Long kuId;
	private String kiPtime;
	private String kiUrl;
	private String kiRegion;
	private String kuName;
	private Integer kiOrdno;
	private String kiImage;
	private String kiType;
	private String kiTop;
	private String kiAddress;
	private Integer kiHits;
	private String kiCtime;
	private String kiKeys;
	private String kiMemo;
	private String kiValiddate;
	private String kiShow;
	private String fields;
	private Boolean isNew;
	
	// Constructors

	/** default constructor */
	public WkTInfo() {
	}

	/** minimal constructor */
	public WkTInfo(Long kiId, Long keId,String kiShow) {
		this.kiId = kiId;
		this.keId=keId;
		this.kiShow = kiShow;
	}

	/** old constructor */
	public WkTInfo(Long kiId,Long keId, String kiPtime,String kiUrl,String kiRegion,String kiTitle,String kiAuthname, String kiTitle2, String kiSource,
			Long kuId, String kuName, Integer kiOrdno, String kiImage,
			String kiType, String kiTop, String kiAddress, Integer kiHits,
			String kiCtime, String kiKeys, String kiMemo, String kiValiddate,
			String kiShow) {
		this.keId=keId;
		this.kiId = kiId;
		this.kiTitle = kiTitle;
		this.kiTitle2 = kiTitle2;
		this.kiSource = kiSource;
		this.kiAuthname=kiAuthname;
		this.kuId = kuId;
		this.kuName = kuName;
		this.kiPtime=kiPtime;
		this.kiUrl=kiUrl;
		this.kiRegion=kiRegion;
		this.kiOrdno = kiOrdno;
		this.kiImage = kiImage;
		this.kiType = kiType;
		this.kiTop = kiTop;
		this.kiAddress = kiAddress;
		this.kiHits = kiHits;
		this.kiCtime = kiCtime;
		this.kiKeys = kiKeys;
		this.kiMemo = kiMemo;
		this.kiValiddate = kiValiddate;
		this.kiShow = kiShow;
	}

	/** full constructor */
	public WkTInfo(Long kiId,Long keId, String kiAuthname, String kiPtime,String kiUrl,String kiRegion,String kiTitle, String kiTitle2, String kiSource,
			Long kuId, String kuName, Integer kiOrdno, String kiImage,
			String kiType, String kiTop, String kiAddress, Integer kiHits,
			String kiCtime, String kiKeys, String kiMemo, String kiValiddate,
			String kiShow, String fields, Boolean isNew) {
		this.kiId = kiId;
		this.keId=keId;
		this.kiTitle = kiTitle;
		this.kiTitle2 = kiTitle2;
		this.kiSource = kiSource;
		this.kuId = kuId;
		this.kiAuthname=kiAuthname;
		this.kuName = kuName;
		this.kiOrdno = kiOrdno;
		this.kiPtime=kiPtime;
		this.kiUrl=kiUrl;
		this.kiRegion=kiRegion;
		this.kiImage = kiImage;
		this.kiType = kiType;
		this.kiTop = kiTop;
		this.kiAddress = kiAddress;
		this.kiHits = kiHits;
		this.kiCtime = kiCtime;
		this.kiKeys = kiKeys;
		this.kiMemo = kiMemo;
		this.kiValiddate = kiValiddate;
		this.kiShow = kiShow;
		this.fields = fields;
		this.isNew = isNew;
	}
	
	
	// Property accessors

	public Long getKiId() {
		return this.kiId;
	}

	public void setKiId(Long kiId) {
		this.kiId = kiId;
	}
	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public String getKiTitle() {
		return this.kiTitle;
	}

	public void setKiTitle(String kiTitle) {
		this.kiTitle = kiTitle;
	}

	public String getKiTitle2() {
		return this.kiTitle2;
	}

	public void setKiTitle2(String kiTitle2) {
		this.kiTitle2 = kiTitle2;
	}
	public String getKiAuthname() {
		return this.kiAuthname;
	}

	public void setKiAuthname(String kiAuthname) {
		this.kiAuthname = kiAuthname;
	}
	public String getKiSource() {
		return this.kiSource;
	}

	public void setKiSource(String kiSource) {
		this.kiSource = kiSource;
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

	public Integer getKiOrdno() {
		return this.kiOrdno;
	}

	public void setKiOrdno(Integer kiOrdno) {
		this.kiOrdno = kiOrdno;
	}

	public String getKiImage() {
		return this.kiImage;
	}

	public void setKiImage(String kiImage) {
		this.kiImage = kiImage;
	}

	public String getKiType() {
		return this.kiType;
	}

	public void setKiType(String kiType) {
		this.kiType = kiType;
	}

	public String getKiTop() {
		return this.kiTop;
	}

	public void setKiTop(String kiTop) {
		this.kiTop = kiTop;
	}

	public String getKiAddress() {
		return this.kiAddress;
	}

	public void setKiAddress(String kiAddress) {
		this.kiAddress = kiAddress;
	}

	public Integer getKiHits() {
		return this.kiHits;
	}

	public void setKiHits(Integer kiHits) {
		this.kiHits = kiHits;
	}

	public String getKiCtime() {
		return this.kiCtime;
	}

	public void setKiCtime(String kiCtime) {
		this.kiCtime = kiCtime;
	}

	public String getKiKeys() {
		return this.kiKeys;
	}

	public void setKiKeys(String kiKeys) {
		this.kiKeys = kiKeys;
	}

	public String getKiMemo() {
		return this.kiMemo;
	}

	public void setKiMemo(String kiMemo) {
		this.kiMemo = kiMemo;
	}

	public String getKiValiddate() {
		return this.kiValiddate;
	}

	public void setKiValiddate(String kiValiddate) {
		this.kiValiddate = kiValiddate;
	}

	public String getKiShow() {
		return this.kiShow;
	}

	public void setKiShow(String kiShow) {
		this.kiShow = kiShow;
	}

	public String getKiPtime() {
		return this.kiPtime;
	}

	public void setKiPtime(String kiPtime) {
		this.kiPtime = kiPtime;
	}
	public String getKiUrl() {
		return this.kiUrl;
	}

	public void setKiUrl(String kiUrl) {
		this.kiUrl = kiUrl;
	}
	public String getKiRegion() {
		return this.kiRegion;
	}

	public void setKiRegion(String kiRegion) {
		this.kiRegion = kiRegion;
	}
	

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}


	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
}