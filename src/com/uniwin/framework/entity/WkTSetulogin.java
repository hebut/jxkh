package com.uniwin.framework.entity;

/**
 * WkTSetulogin entity. @author MyEclipse Persistence Tools
 */

public class WkTSetulogin implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jsessionid;
	private String starttime;

	// Constructors

	/** default constructor */
	public WkTSetulogin() {
	}

	/** full constructor */
	public WkTSetulogin(Integer id, String jsessionid, String starttime) {
		this.id = id;
		this.jsessionid = jsessionid;
		this.starttime = starttime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJsessionid() {
		return this.jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

}