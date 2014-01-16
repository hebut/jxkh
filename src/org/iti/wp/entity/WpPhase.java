package org.iti.wp.entity;

import org.iti.wp.service.WpTypeService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * WpPhase entity. @author MyEclipse Persistence Tools
 */

public class WpPhase implements java.io.Serializable {

	// Fields
	public static Long Usertj=1l,Roletj=0l;
	public static Short SfjsYes=1,SfjsNo=0;
	public Short getWpSfjs() {
		return wpSfjs;
	}

	public void setWpSfjs(Short wpSfjs) {
		this.wpSfjs = wpSfjs;
	}

	private Long wpId;
	private String wpName;
	private Long wpPsstart;
	private Long wpPsend;
	private Long wtId;
	private Long wpType;
    WpType wptype;
    private Short wpSfjs;
	// Constructors
	

	public WpType getWptype() {
		if (wptype == null) {
			WpTypeService wpTypeService = (WpTypeService) BeanFactory.getBean("wpTypeService");
			this.wptype = (WpType) wpTypeService.get(WpType.class, wtId);
		}
		return wptype;
	}

	public void setWptype(WpType wptype) {
		this.wptype = wptype;
	}

	/** default constructor */
	public WpPhase() {
		this.wpSfjs=this.SfjsNo;
	}

	/** minimal constructor */
	public WpPhase(Long wtId) {
		this.wtId = wtId;
	}

	/** full constructor */
	public WpPhase(String wpName,  Long wpPsstart,
			Long wpPsend, Long wtId, Long wpType,Long wtaId,Short wpSfjs) {
		this.wpName = wpName;
		
		this.wpPsstart = wpPsstart;
		this.wpPsend = wpPsend;
		this.wtId = wtId;
		this.wpType = wpType;
		this.wpSfjs = wpSfjs;
	}

	// Property accessors

	public Long getWpId() {
		return this.wpId;
	}

	public void setWpId(Long wpId) {
		this.wpId = wpId;
	}


	public String getWpName() {
		return this.wpName;
	}

	public void setWpName(String wpName) {
		this.wpName = wpName;
	}

	

	public Long getWpPsstart() {
		return this.wpPsstart;
	}

	public void setWpPsstart(Long wpPsstart) {
		this.wpPsstart = wpPsstart;
	}

	public Long getWpPsend() {
		return this.wpPsend;
	}

	public void setWpPsend(Long wpPsend) {
		this.wpPsend = wpPsend;
	}

	public Long getWtId() {
		return this.wtId;
	}

	public void setWtId(Long wtId) {
		this.wtId = wtId;
	}

	public Long getWpType() {
		return this.wpType;
	}

	public void setWpType(Long wpType) {
		this.wpType = wpType;
	}

}