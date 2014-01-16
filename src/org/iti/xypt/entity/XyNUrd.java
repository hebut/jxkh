package org.iti.xypt.entity;

/**
 * XyNUrd entity. @author MyEclipse Persistence Tools
 */

public class XyNUrd implements java.io.Serializable {

	// Fields
	public static final Short TYPE_KDID=0;
	public static final Short TYPE_CLID=1;

	private XyNUrdId id;

	// Constructors

	/** default constructor */
	public XyNUrd() {
	}
	
	public XyNUrd(XyUserrole xyu) {
		id=new XyNUrdId();
		id.setKdId(xyu.getKdId());
		id.setKrId(xyu.getId().getKrId());
		id.setKuId(xyu.getId().getKuId());
		id.setXnuType(TYPE_KDID);
	}

	/** full constructor */
	public XyNUrd(XyNUrdId id) {
		this.id = id;
	}

	// Property accessors

	public XyNUrdId getId() {
		return this.id;
	}

	public void setId(XyNUrdId id) {
		this.id = id;
	}

}