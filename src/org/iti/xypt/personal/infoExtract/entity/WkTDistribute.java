package org.iti.xypt.personal.infoExtract.entity;

/**
 * WkTDistribute entity. @author MyEclipse Persistence Tools
 */

public class WkTDistribute implements java.io.Serializable {

	// Fields

	public static final String STATUS_PUB="0";
	public static final String STATUS_EDIT="1";
	public static final String STATUS_SONGSHEN="2";
	public static final String STATUS_READED="3";//рятд
	public static final String STATUS_BACK="4";
	private Long kbId;
	private Long kiId;
	private Long keId;
	private String kbFlag;
	private String kbTitle;
	private String kbRight;
	private String kbStatus;
	private String kbColor;
	private String kbEm;
	private String kbStrong;
	private String kbDtime;
	private String kbMail;
	
	private Integer kbShow;

	// Constructors

	public Integer getKbShow() {
		return kbShow;
	}

	public void setKbShow(Integer kbShow) {
		this.kbShow = kbShow;
	}

	/** default constructor */
	public WkTDistribute() {
	}

	/** minimal constructor */
	public WkTDistribute(Long kbId) {
		this.kbId = kbId;
	}

	/** full constructor */
	public WkTDistribute(Long kbId, Long kiId, Long keId, String kbFlag,
			String kbTitle, String kbRight, String kbStatus, String kbColor,
			String kbEm, String kbStrong, String kbDtime,String kbMail) {
		this.kbId = kbId;
		this.kiId = kiId;
		this.keId = keId;
		this.kbFlag = kbFlag;
		this.kbTitle = kbTitle;
		this.kbRight = kbRight;
		this.kbStatus = kbStatus;
		this.kbColor = kbColor;
		this.kbEm = kbEm;
		this.kbStrong = kbStrong;
		this.kbDtime = kbDtime;
		this.kbMail=kbMail;

	}

	// Property accessors

	public Long getKbId() {
		return this.kbId;
	}

	public void setKbId(Long kbId) {
		this.kbId = kbId;
	}

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

	public String getKbFlag() {
		return this.kbFlag;
	}

	public void setKbFlag(String kbFlag) {
		this.kbFlag = kbFlag;
	}
	
	public String getKbMail() {
		return this.kbMail;
	}

	public void setKbMail(String KbMail) {
		this.kbMail = KbMail;
	}

	public String getKbTitle() {
		return this.kbTitle;
	}

	public void setKbTitle(String kbTitle) {
		this.kbTitle = kbTitle;
	}

	public String getKbRight() {
		return this.kbRight;
	}

	public void setKbRight(String kbRight) {
		this.kbRight = kbRight;
	}

	public String getKbStatus() {
		return this.kbStatus;
	}

	public void setKbStatus(String kbStatus) {
		this.kbStatus = kbStatus;
	}

	public String getKbColor() {
		return this.kbColor;
	}

	public void setKbColor(String kbColor) {
		this.kbColor = kbColor;
	}

	public String getKbEm() {
		return this.kbEm;
	}

	public void setKbEm(String kbEm) {
		this.kbEm = kbEm;
	}

	public String getKbStrong() {
		return this.kbStrong;
	}

	public void setKbStrong(String kbStrong) {
		this.kbStrong = kbStrong;
	}

	public String getKbDtime() {
		return this.kbDtime;
	}

	public void setKbDtime(String kbDtime) {
		this.kbDtime = kbDtime;
	}

}