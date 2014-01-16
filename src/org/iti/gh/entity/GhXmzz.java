package org.iti.gh.entity;

import org.iti.gh.service.XmService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhXmzz entity. @author MyEclipse Persistence Tools
 */

public class GhXmzz implements java.io.Serializable {

	
	public static final Short FLAG_NO=0,FLAGG_YES=1;
	
	public static final Short HYLW=1,QKLW=2,KYZZ=3,JYJC=4,FMZL=5,RJZZ=6;
	// Fields
	private Long zzId;
	private Long kyId;
	private Long lwId;
	private String zzQk;
	private Long kuId;
	private Short lwType;
	
	GhXm xm;
	// Constructors

	/** default constructor */
	public GhXmzz() {
	}

	/** minimal constructor */
	public GhXmzz(Long zzId, Long kyId, Long kuId) {
		this.zzId = zzId;
		this.kyId = kyId;
		this.kuId = kuId;
	}


	/** full constructor */
	public GhXmzz(Long zzId, Long kyId, Long lwId,Short lwType, String zzQk, Long kuId) {
		this.zzId = zzId;
		this.kyId = kyId;
		this.lwId = lwId;
		this.lwType=lwType;
		this.zzQk = zzQk;
		this.kuId = kuId;
		
	}

	// Property accessors

	public GhXm getUser() {
		if(xm==null){
			XmService xmService=(XmService)BeanFactory.getBean("xmService");
			this.xm=(GhXm)xmService.get(GhXm.class, kyId);
		}
		return xm;
	}
	public Long getZzId() {
		return this.zzId;
	}

	public void setZzId(Long zzId) {
		this.zzId = zzId;
	}

	public Long getKyId() {
		return this.kyId;
	}

	public void setKyId(Long kyId) {
		this.kyId = kyId;
	}

	public Long getLwId() {
		return this.lwId;
	}

	public void setLwId(Long lwId) {
		this.lwId = lwId;
	}

	public String getZzQk() {
		return this.zzQk;
	}

	public void setZzQk(String zzQk) {
		this.zzQk = zzQk;
	}


	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Short getLwType() {
		return lwType;
	}

	public void setLwType(Short lwType) {
		this.lwType = lwType;
	}
	

}