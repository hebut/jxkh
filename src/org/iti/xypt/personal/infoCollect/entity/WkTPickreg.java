package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTPickreg entity. @author MyEclipse Persistence Tools
 */

public class WkTPickreg implements java.io.Serializable {

	/**
	 * @param kpId
	 * @param keId
	 * @param kpRegname
	 */
	public WkTPickreg(Long kpId, Long keId, String kpRegname) {
		super();
		this.kpId = kpId;
		this.keId = keId;
		this.kpRegname = kpRegname;
	}
	// Fields

	/**
	 * @param kpId
	 * @param keId
	 * @param kpRegname
	 * @param kpReglevel
	 * @param kpRegbegin
	 * @param kpRegend
	 * @param kpRegtag
	 * @param kpTagid
	 * @param kpDataname
	 * @param kpOrderid
	 * @param kpLoadimag
	 * @param kpRetainTags
	 * @param kpOldvalue
	 * @param kpNewvalue
	 */
	public WkTPickreg(Long kpId, Long keId, String kpRegname,
			String kpReglevel, String kpRegbegin, String kpRegend,
			String kpDataname,Long kpOrderid, String kpLoadimag, 
			String kpRetainTags,String kpMerage,String kpOldvalue,String kpNewvalue) {
		super();
		this.kpId = kpId;
		this.keId = keId;
		this.kpRegname = kpRegname;
		this.kpReglevel = kpReglevel;
		this.kpRegbegin = kpRegbegin;
		this.kpRegend = kpRegend;
		this.kpDataname = kpDataname;
		this.kpOrderid = kpOrderid;
		this.kpLoadimag = kpLoadimag;
		this.kpRetainTags = kpRetainTags;
		this.kpMerage=kpMerage;
		this.kpOldvalue=kpOldvalue;
		this.kpNewvalue=kpNewvalue;
	}

	private Long kpId;
	private Long keId;
	private String kpRegname;
	private String kpReglevel;
	
	private String kpRegbegin;
	public String getKpRegbegin() {
		return kpRegbegin;
	}
	public void setKpRegbegin(String kpRegbegin) {
		this.kpRegbegin = kpRegbegin;
	}
	public String getKpRegend() {
		return kpRegend;
	}
	public void setKpRegend(String kpRegend) {
		this.kpRegend = kpRegend;
	}

	private String kpRegend;
	private String kpDataname;
	private Long kpOrderid;
	private String kpMerage;
	private String kpOldvalue;
	public String getKpOldvalue() {
		return kpOldvalue;
	}

	public void setKpOldvalue(String kpOldvalue) {
		this.kpOldvalue = kpOldvalue;
	}

	public String getKpNewvalue() {
		return kpNewvalue;
	}

	public void setKpNewvalue(String kpNewvalue) {
		this.kpNewvalue = kpNewvalue;
	}

	private String kpNewvalue;
	
	public String getKpMerage() {
		return kpMerage;
	}

	public void setKpMerage(String kpMerage) {
		this.kpMerage = kpMerage;
	}

	public Long getKpOrderid() {
		return kpOrderid;
	}
	public void setKpOrderid(Long kpOrderid) {
		this.kpOrderid = kpOrderid;
	}

	private String kpLoadimag;
	private String kpRetainTags;
	
	public String getKpRetainTags() {
		return kpRetainTags;
	}
	public void setKpRetainTags(String kpRetainTags) {
		this.kpRetainTags = kpRetainTags;
	}
	/** default constructor */
	public WkTPickreg() {
	}

	// Property accessors

	public Long getKpId() {
		return this.kpId;
	}

	public void setKpId(Long kpId) {
		this.kpId = kpId;
	}

	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public String getKpRegname() {
		return this.kpRegname;
	}

	public void setKpRegname(String kpRegname) {
		this.kpRegname = kpRegname;
	}

	public String getKpReglevel() {
		return this.kpReglevel;
	}

	public void setKpReglevel(String kpReglevel) {
		this.kpReglevel = kpReglevel;
	}

	public String getKpDataname() {
		return this.kpDataname;
	}

	public void setKpDataname(String kpDataname) {
		this.kpDataname = kpDataname;
	}
	public String getKpLoadimag() {
		return this.kpLoadimag;
	}

	public void setKpLoadimag(String kpLoadimag) {
		this.kpLoadimag = kpLoadimag;
	}

}