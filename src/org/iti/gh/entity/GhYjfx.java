package org.iti.gh.entity;

/**
 * GhYjfx entity. @author MyEclipse Persistence Tools
 */

public class GhYjfx implements java.io.Serializable {

	// Fields 

	private Long gyId;
	private String gyName;
	private Long kdId;

	// Constructors

	/** default constructor */
	public GhYjfx() {
	}

	/** full constructor */
	public GhYjfx(Long gyId, String gyName, Long kdId) {
		this.gyId = gyId;
		this.gyName = gyName;
		this.kdId = kdId;
	}

	// Property accessors

	public Long getGyId() {
		return this.gyId;
	}

	public void setGyId(Long gyId) {
		this.gyId = gyId;
	}

	public String getGyName() {
		return this.gyName;
	}

	public void setGyName(String gyName) {
		this.gyName = gyName;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

}