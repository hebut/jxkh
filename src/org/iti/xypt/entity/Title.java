package org.iti.xypt.entity;

/**
 * Title entity. @author MyEclipse Persistence Tools
 */

public class Title implements java.io.Serializable {

	// Fields

	private String tiId;
	private String ptiId;
	private String tiName;

	// Constructors

	/** default constructor */
	public Title() {
	}

	/** full constructor */
	public Title(String tiId, String ptiId, String tiName) {
		this.tiId = tiId;
		this.ptiId = ptiId;
		this.tiName = tiName;
	}

	// Property accessors

	public String getTiId() {
		return this.tiId;
	}

	public void setTiId(String tiId) {
		this.tiId = tiId;
	}

	public String getPtiId() {
		return this.ptiId;
	}

	public void setPtiId(String ptiId) {
		this.ptiId = ptiId;
	}

	public String getTiName() {
		return this.tiName;
	}

	public void setTiName(String tiName) {
		this.tiName = tiName;
	}
	
	

}