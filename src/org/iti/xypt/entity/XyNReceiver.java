package org.iti.xypt.entity;

/**
 * XyNReceiver entity. @author MyEclipse Persistence Tools
 */

public class XyNReceiver implements java.io.Serializable {

	// Fields
	public static final Short TYPE_KDID=0;
	public static final Short TYPE_CLID=1;

	private XyNReceiverId id;

	// Constructors

	/** default constructor */
	public XyNReceiver() {
	}
	 

	/** full constructor */
	public XyNReceiver(XyNReceiverId id) {
		this.id = id;
	}

	// Property accessors

	public XyNReceiverId getId() {
		return this.id;
	}

	public void setId(XyNReceiverId id) {
		this.id = id;
	}

}