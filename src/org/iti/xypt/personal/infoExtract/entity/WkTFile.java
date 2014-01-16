package org.iti.xypt.personal.infoExtract.entity;

/**
 * WkTFile entity. @author MyEclipse Persistence Tools
 */

public class WkTFile implements java.io.Serializable {

	// Fields

	private WkTFileId id;

	// Constructors

	/** default constructor */
	public WkTFile() {
	}

	/** full constructor */
	public WkTFile(WkTFileId id) {
		this.id = id;
	}

	// Property accessors

	public WkTFileId getId() {
		return this.id;
	}

	public void setId(WkTFileId id) {
		this.id = id;
	}

}