package org.iti.xypt.personal.infoCollect.entity;

/**
 * XyMReceiver entity. @author MyEclipse Persistence Tools
 */

public class WkTNewsRead implements java.io.Serializable {

	// Fields
	
	public static final Short STATE_READED=1,STATE_DELETE=2;

	private WkTNewsReadId id;
	private Short State;

	// Constructors

	/** default constructor */
	public WkTNewsRead() {
	}

	/** full constructor */
	public WkTNewsRead(WkTNewsReadId id, Short State) {
		this.id = id;
		this.State = State;
	}

	// Property accessors

	public WkTNewsReadId getId() {
		return this.id;
	}

	public void setId(WkTNewsReadId id) {
		this.id = id;
	}

	public Short getState() {
		return this.State;
	}

	public void setState(Short State) {
		this.State = State;
	}

}