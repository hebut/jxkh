package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTGuidereg entity. @author MyEclipse Persistence Tools
 */

public class WkTGuidereg implements java.io.Serializable {

	// Fields

	private Long kgId;
	private Long keId;
	private String kgName;
	private String kgLevel;
	private String kgNextlevel;
	private String kgRegular;
	
	public String getKgRegular() {
		return kgRegular;
	}

	public void setKgRegular(String kgRegular) {
		this.kgRegular = kgRegular;
	}

	public String getKgNextlevel() {
		return kgNextlevel;
	}

	public void setKgNextlevel(String kgNextlevel) {
		this.kgNextlevel = kgNextlevel;
	}

	private String kgModel;
	private String kgNextpage;
	public String getKgNextpage() {
		return kgNextpage;
	}

	public void setKgNextpage(String kgNextpage) {
		this.kgNextpage = kgNextpage;
	}

	private String kgPagesign;
	private Integer kgPagecount;
	public Integer getKgPagecount() {
		return kgPagecount;
	}

	public void setKgPagecount(Integer kgPagecount) {
		this.kgPagecount = kgPagecount;
	}

	private Long kgOrderid;
	private String kgUrlbegin;
	public String getKgUrlbegin() {
		return kgUrlbegin;
	}

	public void setKgUrlbegin(String kgUrlbegin) {
		this.kgUrlbegin = kgUrlbegin;
	}

	public String getKgUrlend() {
		return kgUrlend;
	}

	public void setKgUrlend(String kgUrlend) {
		this.kgUrlend = kgUrlend;
	}

	public String getKgCirculate() {
		return kgCirculate;
	}

	public void setKgCirculate(String kgCirculate) {
		this.kgCirculate = kgCirculate;
	}

	private String kgUrlend;
	private String kgCirculate;
	private String kgConbegin;
	public String getKgConbegin() {
		return kgConbegin;
	}

	public void setKgConbegin(String kgConbegin) {
		this.kgConbegin = kgConbegin;
	}

	public String getKgConend() {
		return kgConend;
	}

	public void setKgConend(String kgConend) {
		this.kgConend = kgConend;
	}

	private String kgConend;
	// Constructors

	/** default constructor */
	public WkTGuidereg() {
	}

	/** minimal constructor */
	public WkTGuidereg(Long kgId,Long keId) {
		this.kgId=kgId;
		this.keId = keId;
	}

	/** full constructor */
	public WkTGuidereg(Long kgId,Long keId, String kgName, String kgLevel,String kgNextlevel,
			String kgModel, String kgNextpage, String kgPagesign,
			Integer kgPagecount, Long kgOrderid, String kgUrlbegin,
			String kgUrlend,String kgCirculate,String kgConbegin,String kgConend,String kgRegular) {
		this.kgId=kgId;
		this.keId = keId;
		this.kgName = kgName;
		this.kgLevel = kgLevel;
		this.kgNextlevel=kgNextlevel;
		this.kgModel = kgModel;
		this.kgNextpage = kgNextpage;
		this.kgPagesign = kgPagesign;
		this.kgPagecount = kgPagecount;
		this.kgOrderid = kgOrderid;
		this.kgUrlbegin=kgUrlbegin;
		this.kgUrlend=kgUrlend;
		this.kgCirculate=kgCirculate;
		this.kgConbegin=kgConbegin;
		this.kgConend=kgConend;
		this.kgRegular=kgRegular;
	}

	// Property accessors

	public Long getKgId() {
		return this.kgId;
	}

	public void setKgId(Long kgId) {
		this.kgId = kgId;
	}

	public Long getKeId() {
		return this.keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	public String getKgName() {
		return this.kgName;
	}

	public void setKgName(String kgName) {
		this.kgName = kgName;
	}

	public String getKgLevel() {
		return this.kgLevel;
	}

	public void setKgLevel(String kgLevel) {
		this.kgLevel = kgLevel;
	}

	public String getKgModel() {
		return this.kgModel;
	}

	public void setKgModel(String kgModel) {
		this.kgModel = kgModel;
	}

	public String getKgPagesign() {
		return this.kgPagesign;
	}

	public void setKgPagesign(String kgPagesign) {
		this.kgPagesign = kgPagesign;
	}
	public Long getKgOrderid() {
		return this.kgOrderid;
	}

	public void setKgOrderid(Long kgOrderid) {
		this.kgOrderid = kgOrderid;
	}


}