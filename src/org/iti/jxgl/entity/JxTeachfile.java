package org.iti.jxgl.entity;

import java.util.Date;

/**
 * JxTeachfile entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class JxTeachfile implements java.io.Serializable {

	// Fields

	private Long jtfId;
	private String jtfYears;
	private Short jtfTerm;
	private Long kdId;
	private Long jtfCid;
	private Long jtfKuid;
	private String jtfName;
	private Short jtfType;
	private Long jtfGs;
	private Double jtfSize;
	private String jtfPath;
	private Date jtfTime;

	// Constructors

	/** default constructor */
	public JxTeachfile() {
	}

	/** minimal constructor */
	public JxTeachfile(Long jtfId) {
		this.jtfId = jtfId;
	}

	// Property accessors

	public JxTeachfile(Long jtfId, String jtfYears, Short jtfTerm, Long kdId,
			Long jtfCid, Long jtfKuid, String jtfName, Short jtfType,
			Long jtfGs, Double jtfSize, String jtfPath, Date jtfTime) {
		super();
		this.jtfId = jtfId;
		this.jtfYears = jtfYears;
		this.jtfTerm = jtfTerm;
		this.kdId = kdId;
		this.jtfCid = jtfCid;
		this.jtfKuid = jtfKuid;
		this.jtfName = jtfName;
		this.jtfType = jtfType;
		this.jtfGs = jtfGs;
		this.jtfSize = jtfSize;
		this.jtfPath = jtfPath;
		this.jtfTime = jtfTime;
	}

	public Long getJtfId() {
		return this.jtfId;
	}

	public void setJtfId(Long jtfId) {
		this.jtfId = jtfId;
	}

	public String getJtfYears() {
		return this.jtfYears;
	}

	public void setJtfYears(String jtfYears) {
		this.jtfYears = jtfYears;
	}

	public Short getJtfTerm() {
		return this.jtfTerm;
	}

	public void setJtfTerm(Short jtfTerm) {
		this.jtfTerm = jtfTerm;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getJtfCid() {
		return this.jtfCid;
	}

	public void setJtfCid(Long jtfCid) {
		this.jtfCid = jtfCid;
	}

	

	public Long getJtfKuid() {
		return jtfKuid;
	}

	public void setJtfKuid(Long jtfKuid) {
		this.jtfKuid = jtfKuid;
	}

	public String getJtfName() {
		return this.jtfName;
	}

	public void setJtfName(String jtfName) {
		this.jtfName = jtfName;
	}

	public Short getJtfType() {
		return this.jtfType;
	}

	public void setJtfType(Short jtfType) {
		this.jtfType = jtfType;
	}

	public Long getJtfGs() {
		return this.jtfGs;
	}

	public void setJtfGs(Long jtfGs) {
		this.jtfGs = jtfGs;
	}

	public Double getJtfSize() {
		return this.jtfSize;
	}

	public void setJtfSize(Double jtfSize) {
		this.jtfSize = jtfSize;
	}

	public String getJtfPath() {
		return this.jtfPath;
	}

	public void setJtfPath(String jtfPath) {
		this.jtfPath = jtfPath;
	}

	public Date getJtfTime() {
		return this.jtfTime;
	}

	public void setJtfTime(Date jtfTime) {
		this.jtfTime = jtfTime;
	}

}