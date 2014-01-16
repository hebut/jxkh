package org.iti.xypt.personal.infoCollect.entity;


/**
 * WkTOrinfocnt entity. @author MyEclipse Persistence Tools
 */

public class WkTOrinfocnt implements java.io.Serializable {

	// Fields
	//Ìí¼Ó
	private WkTOriInfocntId id;
	public WkTOriInfocntId getId() {
		return id;
	}

	private String koiFlag;
	private String koiContent;

	// Constructors

	

	public void setId(WkTOriInfocntId id) {
		this.id = id;
	}

	/** default constructor */
	public WkTOrinfocnt() {
		
	}
	
	public WkTOrinfocnt(WkTOriInfocntId id,String kiFlag,String kiContent) {
		this.id=id;
		this.koiFlag=kiFlag;
		this.koiContent=kiContent;
	}


	// Property accessors

	public String getKoiFlag() {
		return this.koiFlag;
	}

	public void setKoiFlag(String koiFlag) {
		this.koiFlag = koiFlag;
	}

	public String getKoiContent() {
		return this.koiContent;
	}

	public void setKoiContent(String koiContent) {
		this.koiContent = koiContent;
	}

}