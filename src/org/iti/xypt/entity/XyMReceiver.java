package org.iti.xypt.entity;

/**
 * XyMReceiver entity. @author MyEclipse Persistence Tools
 */

public class XyMReceiver implements java.io.Serializable {

	// Fields
	
	public static final Short STATE_UNREAD=0,STATE_READED=1,STATE_DELETE=2;

	private XyMReceiverId id;
	private Short xmrState;//״̬

	// Constructors

	/** default constructor */
	public XyMReceiver() {
	}

	/** full constructor */
	public XyMReceiver(XyMReceiverId id, Short xmrState) {
		this.id = id;
		this.xmrState = xmrState;
	}

	// Property accessors

	public XyMReceiverId getId() {
		return this.id;
	}

	public void setId(XyMReceiverId id) {
		this.id = id;
	}

	public Short getXmrState() {
		return this.xmrState;
	}

	public void setXmrState(Short xmrState) {
		this.xmrState = xmrState;
	}

}