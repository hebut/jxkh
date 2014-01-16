package org.iti.bysj.entity;

/**
 * BsNsource entity. @author MyEclipse Persistence Tools
 */

public class BsNsource implements java.io.Serializable {

	// Fields
	public static final Short NS_NATURE = 1;
	public static final Short NS_SOURCE = 2;
	private Long bnsId;
	private Long buId;
	private String bnsName;
	private String bnsSname;
	private Short bnsType;

	// Constructors

	/** default constructor */
	public BsNsource() {
	}

	/** full constructor */
	public BsNsource(Long bnsId, Long buId, String bnsName, String bnsSname, Short bnsType) {
		this.bnsId = bnsId;
		this.buId = buId;
		this.bnsName = bnsName;
		this.bnsSname = bnsSname;
		this.bnsType = bnsType;
	}

	// Property accessors

	public Long getBnsId() {
		return this.bnsId;
	}

	public void setBnsId(Long bnsId) {
		this.bnsId = bnsId;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public String getBnsName() {
		return this.bnsName;
	}

	public void setBnsName(String bnsName) {
		this.bnsName = bnsName;
	}

	public String getBnsSname() {
		return this.bnsSname;
	}

	public void setBnsSname(String bnsSname) {
		this.bnsSname = bnsSname;
	}

	public Short getBnsType() {
		return this.bnsType;
	}

	public void setBnsType(Short bnsType) {
		this.bnsType = bnsType;
	}

}