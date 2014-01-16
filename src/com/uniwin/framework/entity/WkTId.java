package com.uniwin.framework.entity;

import java.math.BigDecimal;

/**
 * WkTId entity. @author MyEclipse Persistence Tools
 */
public class WkTId implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String kidKey;
	private BigDecimal kidValue;

	// Constructors
	/** default constructor */
	public WkTId() {
	}

	/** full constructor */
	public WkTId(String kidKey, BigDecimal kidValue) {
		this.kidKey = kidKey;
		this.kidValue = kidValue;
	}

	// Property accessors
	public String getKidKey() {
		return this.kidKey;
	}

	public void setKidKey(String kidKey) {
		this.kidKey = kidKey;
	}

	public BigDecimal getKidValue() {
		return this.kidValue;
	}

	public void setKidValue(BigDecimal kidValue) {
		this.kidValue = kidValue;
	}
}