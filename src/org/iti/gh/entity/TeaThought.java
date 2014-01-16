package org.iti.gh.entity;

/**
 * TeaThought entity. @author MyEclipse Persistence Tools
 */

public class TeaThought implements java.io.Serializable {

	// Fields

	private Long ttId;
	private Long kuId;
	private String ttYear;
	private String ttContent;

	// Constructors

	/** default constructor */
	public TeaThought() {
	}

	/** full constructor */
	public TeaThought(Long kuId, String ttYear, String ttContent) {
		this.kuId = kuId;
		this.ttYear = ttYear;
		this.ttContent = ttContent;
	}

	// Property accessors

	public Long getTtId() {
		return this.ttId;
	}

	public void setTtId(Long ttId) {
		this.ttId = ttId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getTtYear() {
		return this.ttYear;
	}

	public void setTtYear(String ttYear) {
		this.ttYear = ttYear;
	}

	public String getTtContent() {
		return this.ttContent;
	}

	public void setTtContent(String ttContent) {
		this.ttContent = ttContent;
	}

}