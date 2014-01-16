package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhXshy entity. @author MyEclipse Persistence Tools
 */

public class GhXshy implements java.io.Serializable {

	// Fields
	public static final Short IFCJ_YES=1,IFCJ_NO=2;
	public static final String EFF_ZC="1",EFF_CJ="2",EFF_DL="3";
	private Long hyId;
	private String hyMc;
	private String hySj;
	private Integer hyZrs;
	private Integer hyJwrs;
	private Short hyIfcj;
	private Long kuId;
	//hlj
	private String hyPlace;
	private String hyTheme;
	private String hyEffect;
	private String hyRemarks;
	WkTUser user;
	WkTUser auditUser;
	private Short auditState;
	private Long auditUid;
	private String reason;
	private Short hyLx;
	public static final Short GNHY=0,GJHY=1;
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	// Constructors

	/** default constructor */
	public GhXshy() {
	}

	/** minimal constructor */
	public GhXshy(Long hyId, Short hyIfcj, Long kuId) {
		this.hyId = hyId;
		this.hyIfcj = hyIfcj;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhXshy(Long hyId, String hyMc, String hySj, Integer hyZrs,
			Integer hyJwrs, Short hyIfcj,Short hyLx, Long kuId, String hyPlace,
			String hyTheme,String hyEffect,String hyRemarks,Short auditState,Long auditUid,String reason) {
		this.hyId = hyId;
		this.hyMc = hyMc;
		this.hySj = hySj;
		this.hyZrs = hyZrs;
		this.hyJwrs = hyJwrs;
		this.hyIfcj = hyIfcj;
		this.kuId = kuId;
		this.hyPlace = hyPlace;
		this.hyTheme = hyTheme;
		this.hyEffect = hyEffect;
		this.hyRemarks = hyRemarks;
		this.auditUid = auditUid;
		this.reason = reason;
		this.auditState=auditState;
		this.hyLx = hyLx;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public Long getHyId() {
		return this.hyId;
	}

	public void setHyId(Long hyId) {
		this.hyId = hyId;
	}

	public String getHyMc() {
		return this.hyMc;
	}

	public void setHyMc(String hyMc) {
		this.hyMc = hyMc;
	}

	public String getHySj() {
		return this.hySj;
	}

	public void setHySj(String hySj) {
		this.hySj = hySj;
	}

	public Integer getHyZrs() {
		return this.hyZrs;
	}

	public void setHyZrs(Integer hyZrs) {
		this.hyZrs = hyZrs;
	}

	public Integer getHyJwrs() {
		return this.hyJwrs;
	}

	public void setHyJwrs(Integer hyJwrs) {
		this.hyJwrs = hyJwrs;
	}

	public Short getHyIfcj() {
		return this.hyIfcj;
	}

	public void setHyIfcj(Short hyIfcj) {
		this.hyIfcj = hyIfcj;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getHyPlace() {
		return hyPlace;
	}

	public void setHyPlace(String hyPlace) {
		this.hyPlace = hyPlace;
	}

	public String getHyTheme() {
		return hyTheme;
	}

	public void setHyTheme(String hyTheme) {
		this.hyTheme = hyTheme;
	}

	public String getHyEffect() {
		return hyEffect;
	}

	public void setHyEffect(String hyEffect) {
		this.hyEffect = hyEffect;
	}

	public String getHyRemarks() {
		return hyRemarks;
	}

	public void setHyRemarks(String hyRemarks) {
		this.hyRemarks = hyRemarks;
	}
	public WkTUser getAuditUser() {
		if(auditUser==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.auditUser=(WkTUser)userService.get(WkTUser.class, auditUid);
		}
		return auditUser;
	}

	public void setAuditUser(WkTUser auditUser) {
		this.auditUser = auditUser;
	}

	public Short getAuditState() {
		return auditState;
	}

	public void setAuditState(Short auditState) {
		this.auditState = auditState;
	}

	public Long getAuditUid() {
		return auditUid;
	}

	public void setAuditUid(Long auditUid) {
		this.auditUid = auditUid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Short getHyLx() {
		return hyLx;
	}

	public void setHyLx(Short hyLx) {
		this.hyLx = hyLx;
	}
	

}