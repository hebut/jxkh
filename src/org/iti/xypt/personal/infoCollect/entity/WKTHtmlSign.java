package org.iti.xypt.personal.infoCollect.entity;

public class WKTHtmlSign implements java.io.Serializable{

	/**
	 * @param ksId
	 * @param ksName
	 * @param ksValue
	 * @param ksOrderid
	 */
	public WKTHtmlSign(Long ksId, String ksName, String ksValue, Long ksOrderid) {
		super();
		this.ksId = ksId;
		this.ksName = ksName;
		this.ksValue = ksValue;
		this.ksOrderid = ksOrderid;
	}
	/**
	 * 
	 */
	public WKTHtmlSign() {
		super();
	}
	private Long ksId;
	private String ksName;
	private String ksValue;
	public Long getKsId() {
		return ksId;
	}
	public void setKsId(Long ksId) {
		this.ksId = ksId;
	}
	public String getKsName() {
		return ksName;
	}
	public void setKsName(String ksName) {
		this.ksName = ksName;
	}
	public String getKsValue() {
		return ksValue;
	}
	public void setKsValue(String ksValue) {
		this.ksValue = ksValue;
	}
	private Long ksOrderid;
	public Long getKsOrderid() {
		return ksOrderid;
	}
	public void setKsOrderid(Long ksOrderid) {
		this.ksOrderid = ksOrderid;
	}
	
}
