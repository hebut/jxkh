package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhJxbg entity. @author MyEclipse Persistence Tools
 */

public class GhJxbg implements java.io.Serializable {

	// Fields
	public static final Short IFCJ_YES=1,IFCJ_NO=2;
	
	private Long jxId;
	private String jxJxmc;
	private String jxHymc;
	private String jxSj;
	private String jxBgmc;
	private Short jxIfcj;
	private Short jxLx;
	private Long kuId;
	WkTUser user;
	WkTUser auditUser;
	private String jxSubject;
	private String jxPlace;
	private String jxRemarks;
	private Short auditState;
	private Long auditUid;
	private String reason;
	public static final Short GNJX=0,GJJX=1;
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	// Constructors

	/** default constructor */
	public GhJxbg() {
	}

	/** minimal constructor */
	public GhJxbg(Long jxId, Short jxIfcj, Long kuId) {
		this.jxId = jxId;
		this.jxIfcj = jxIfcj;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhJxbg(Long jxId, String jxJxmc, String jxHymc, String jxSj,
			String jxBgmc, Short jxIfcj,Short jxLx, Long kuId, String jxSubject,
			String jxPlace, String jxRemarks,Short auditState,Long auditUid,String reason) {
		this.jxId = jxId;
		this.jxJxmc = jxJxmc;
		this.jxHymc = jxHymc;
		this.jxSj = jxSj;
		this.jxBgmc = jxBgmc;
		this.jxIfcj = jxIfcj;
		this.kuId = kuId;
		this.jxSubject = jxSubject;
		this.jxPlace = jxPlace;
		this.jxRemarks = jxRemarks;
		this.jxLx = jxLx;
		this.auditUid = auditUid;
		this.reason = reason;
		this.auditState=auditState;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public Long getJxId() {
		return this.jxId;
	}

	public void setJxId(Long jxId) {
		this.jxId = jxId;
	}

	public String getJxJxmc() {
		return this.jxJxmc;
	}

	public void setJxJxmc(String jxJxmc) {
		this.jxJxmc = jxJxmc;
	}

	public String getJxHymc() {
		return this.jxHymc;
	}

	public void setJxHymc(String jxHymc) {
		this.jxHymc = jxHymc;
	}

	public String getJxSj() {
		return this.jxSj;
	}

	public void setJxSj(String jxSj) {
		this.jxSj = jxSj;
	}

	public String getJxBgmc() {
		return this.jxBgmc;
	}

	public void setJxBgmc(String jxBgmc) {
		this.jxBgmc = jxBgmc;
	}

	public Short getJxIfcj() {
		return this.jxIfcj;
	}

	public void setJxIfcj(Short jxIfcj) {
		this.jxIfcj = jxIfcj;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	
	public String getJxSubject() {
		return this.jxSubject;
	}

	public void setJxSubject(String jxSubject) {
		this.jxSubject = jxSubject;
	}

	public String getJxPlace() {
		return this.jxPlace;
	}

	public void setJxPlace(String jxPlace) {
		this.jxPlace = jxPlace;
	}

	public String getJxRemarks() {
		return this.jxRemarks;
	}

	public void setJxRemarks(String jxRemarks) {
		this.jxRemarks = jxRemarks;
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

	public Short getJxLx() {
		return jxLx;
	}

	public void setJxLx(Short jxLx) {
		this.jxLx = jxLx;
	}
	

}