package com.uniwin.framework.entity;

import java.io.Serializable;

public class WkTDocType implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long doctId;
	private String doctName;
	private Long doctKdid;
	public Long getDoctKdid() {
		return doctKdid;
	}
	public void setDoctKdid(Long doctKdid) {
		this.doctKdid = doctKdid;
	}
	public Long getDoctId() {
		return doctId;
	}
	public void setDoctId(Long doctId) {
		this.doctId = doctId;
	}
	public String getDoctName() {
		return doctName;
	}
	public void setDoctName(String doctName) {
		this.doctName = doctName;
	}
	public WkTDocType(Long doctId, String doctName) {
		super();
		this.doctId = doctId;
		this.doctName = doctName;
	}
	public WkTDocType() {
	}
	
	
}
