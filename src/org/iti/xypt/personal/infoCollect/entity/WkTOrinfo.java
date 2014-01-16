package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTOrinfo entity. @author MyEclipse Persistence Tools
 */

public class WkTOrinfo implements java.io.Serializable {

	// Fields

	private Long koiId;
	private Long keId;
	private String koiTitle;
	private String koiTitle2;
	private String koiSource;
	private String koiAuthname;
	private String koiImage;
	private String koiType;
	private String koiAddress;
	private String koiPtime;
	private String koiCtime;
	private String koiKeys;
	private String koiMemo;
	private String koiUrl;
	private Long koiStatus;
//	private String koiEtime;

	private String koiIsread;
	// Constructors

	public String getKoiIsread() {
		return koiIsread;
	}

	public void setKoiIsread(String koiIsread) {
		this.koiIsread = koiIsread;
	}

	/** default constructor */
	public WkTOrinfo() {
	}

	/** minimal constructor */
	public WkTOrinfo(Long koiId,Long keId) {
		this.koiId=koiId;
		this.keId = keId;
	}

	/** full constructor */
	public WkTOrinfo(Long koiId,Long keId, String koiTitle, String koiTitle2,
			String koiSource, String koiAuthname, String koiImage,
			String koiType, String koiAddress, String koiPtime, String koiKeys,
			String koiMemo, String koiUrl,Long koiStatus,String koiCtime) {
		this.koiId=koiId;
		this.keId = keId;
		this.koiTitle = koiTitle;
		this.koiTitle2 = koiTitle2;
		this.koiSource = koiSource;
		this.koiAuthname = koiAuthname;
		this.koiImage = koiImage;
		this.koiType = koiType;
		this.koiAddress = koiAddress;
		this.koiPtime = koiPtime;
		this.koiKeys = koiKeys;
		this.koiMemo = koiMemo;
		this.koiUrl = koiUrl;
		this.koiStatus=koiStatus;
		this.koiCtime=koiCtime;
//		this.koiEtime = koiEtime;
	}

	// Property accessors

	public Long getKoiId() {
		return this.koiId;
	}

	public void setKoiId(Long koiId) {
		this.koiId = koiId;
	}

	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public String getKoiTitle() {
		return this.koiTitle;
	}

	public void setKoiTitle(String koiTitle) {
		this.koiTitle = koiTitle;
	}

	public String getKoiTitle2() {
		return this.koiTitle2;
	}

	public void setKoiTitle2(String koiTitle2) {
		this.koiTitle2 = koiTitle2;
	}

	public String getKoiSource() {
		return this.koiSource;
	}

	public void setKoiSource(String koiSource) {
		this.koiSource = koiSource;
	}

	public String getKoiAuthname() {
		return this.koiAuthname;
	}

	public void setKoiAuthname(String koiAuthname) {
		this.koiAuthname = koiAuthname;
	}

	public String getKoiImage() {
		return this.koiImage;
	}

	public void setKoiImage(String koiImage) {
		this.koiImage = koiImage;
	}

	public String getKoiType() {
		return this.koiType;
	}

	public void setKoiType(String koiType) {
		this.koiType = koiType;
	}

	public String getKoiAddress() {
		return this.koiAddress;
	}

	public void setKoiAddress(String koiAddress) {
		this.koiAddress = koiAddress;
	}

	public String getKoiPtime() {
		return this.koiPtime;
	}

	public void setKoiPtime(String koiPtime) {
		this.koiPtime = koiPtime;
	}
	public String getKoiCtime() {
		return this.koiCtime;
	}

	public void setKoiCtime(String koiCtime) {
		this.koiCtime = koiCtime;
	}

	public String getKoiKeys() {
		return this.koiKeys;
	}

	public void setKoiKeys(String koiKeys) {
		this.koiKeys = koiKeys;
	}

	public String getKoiMemo() {
		return this.koiMemo;
	}

	public void setKoiMemo(String koiMemo) {
		this.koiMemo = koiMemo;
	}

	public String getKoiUrl() {
		return this.koiUrl;
	}

	public void setKoiUrl(String koiUrl) {
		this.koiUrl = koiUrl;
	}
	public Long getKoiStatus() {
		return this.koiStatus;
	}

	public void setKoiStatus(Long koiStatus) {
		this.koiStatus = koiStatus;
	}

}