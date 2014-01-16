package com.uniwin.asm.personal.entity;

import java.io.Serializable;

public class DocList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long dlId;
	private Long dtId;
	private String dlPath;
	private String dlName;
	private Long dlSize;
	private String dlInfo;
	private Long dlTime;
	private Long dlKuid;
	
	public Long getDlKuid() {
		return dlKuid;
	}
	public void setDlKuid(Long dlKuid) {
		this.dlKuid = dlKuid;
	}
	public Long getDlTime() {
		return dlTime;
	}
	public void setDlTime(Long dlTime) {
		this.dlTime = dlTime;
	}
	public DocList() {
	}
	public DocList(Long dlId, Long dtId, String dlPath, String dlName,
			Long dlSize, String dlInfo) {
		super();
		this.dlId = dlId;
		this.dtId = dtId;
		this.dlPath = dlPath;
		this.dlName = dlName;
		this.dlSize = dlSize;
		this.dlInfo = dlInfo;
	}
	public Long getDlId() {
		return dlId;
	}
	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}
	public Long getDtId() {
		return dtId;
	}
	public void setDtId(Long dtId) {
		this.dtId = dtId;
	}
	public String getDlPath() {
		return dlPath;
	}
	public void setDlPath(String dlPath) {
		this.dlPath = dlPath;
	}
	public String getDlName() {
		return dlName;
	}
	public void setDlName(String dlName) {
		this.dlName = dlName;
	}
	public Long getDlSize() {
		return dlSize;
	}
	public void setDlSize(Long dlSize) {
		this.dlSize = dlSize;
	}
	public String getDlInfo() {
		return dlInfo;
	}
	public void setDlInfo(String dlInfo) {
		this.dlInfo = dlInfo;
	}
}
