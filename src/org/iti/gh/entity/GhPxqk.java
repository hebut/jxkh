package org.iti.gh.entity;




/**
 * GhPxqk entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GhPxqk implements java.io.Serializable {

	// Fields

	private Long pxId;
	private String pxMainpoint;
	private String pxStarttime;
	private String pxEndtime;
	private String pxContent;
	private String pxPlace;
	private String pxProve;
	private Long kuId;
	

	// Constructors

	
	/** default constructor */
	public GhPxqk() {
	}

	/** minimal constructor */
	public GhPxqk(Long pxId) {
		this.pxId = pxId;
	}

	/** full constructor */
	public GhPxqk(Long pxId, String pxMainpoint, String pxStarttime,
			String pxEndtime, String pxContent, String pxPlace, String pxProve) {
		this.pxId = pxId;
		this.pxMainpoint = pxMainpoint;
		this.pxStarttime = pxStarttime;
		this.pxEndtime = pxEndtime;
		this.pxContent = pxContent;
		this.pxPlace = pxPlace;
		this.pxProve = pxProve;
	}

	// Property accessors

	public Long getPxId() {
		return this.pxId;
	}

	public void setPxId(Long pxId) {
		this.pxId = pxId;
	}

	public String getPxMainpoint() {
		return this.pxMainpoint;
	}

	public void setPxMainpoint(String pxMainpoint) {
		this.pxMainpoint = pxMainpoint;
	}

	public String getPxStarttime() {
		return this.pxStarttime;
	}

	public void setPxStarttime(String pxStarttime) {
		this.pxStarttime = pxStarttime;
	}

	public String getPxEndtime() {
		return this.pxEndtime;
	}

	public void setPxEndtime(String pxEndtime) {
		this.pxEndtime = pxEndtime;
	}

	public String getPxContent() {
		return this.pxContent;
	}

	public void setPxContent(String pxContent) {
		this.pxContent = pxContent;
	}

	public String getPxPlace() {
		return this.pxPlace;
	}

	public void setPxPlace(String pxPlace) {
		this.pxPlace = pxPlace;
	}

	public String getPxProve() {
		return this.pxProve;
	}

	public void setPxProve(String pxProve) {
		this.pxProve = pxProve;
	}
	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

}