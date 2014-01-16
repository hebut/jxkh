package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhJlhz entity. @author MyEclipse Persistence Tools
 */

public class GhJlhz implements java.io.Serializable {

	// Fields

	public static final Short IFCJ_YES=1,IFCJ_NO=2;
	private Long hzId;
	private String hzMc;
	private String hzSj;
	private String hzKssj;
	private String hzJssj;
	private String hzDx;
	private Short hzIfcj;
	private Short hzLx;
	private Long kuId;
	//ÉóºË×´Ì¬
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	public static final Short GNHZ=0,GJHZ=1;
	WkTUser user;
	WkTUser auditUser;
	private Short auditState;
	private Long auditUid;
	private String reason;
	private String hzRemark;
	
	// Constructors

	/** default constructor */
	public GhJlhz() {
	}

	/** minimal constructor */
	public GhJlhz(Long hzId, Short hzIfcj, Long kuId) {
		this.hzId = hzId;
		this.hzIfcj = hzIfcj;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhJlhz(Long hzId, String hzMc,String hzSj, String hzKssj,String hzJssj, String hzDx,
			Short hzIfcj,Short hzLx, Long kuId, String hzRemark,Short auditState,Long auditUid,String reason) {
		this.hzId = hzId;
		this.hzMc = hzMc;
		this.hzSj=hzSj;
		this.hzKssj = hzKssj;
		this.hzJssj=hzJssj;
		this.hzDx = hzDx;
		this.hzIfcj = hzIfcj;
		this.hzLx = hzLx;
		this.kuId = kuId;
		this.hzRemark = hzRemark;
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
	public Long getHzId() {
		return this.hzId;
	}

	public void setHzId(Long hzId) {
		this.hzId = hzId;
	}

	public String getHzMc() {
		return this.hzMc;
	}

	public void setHzMc(String hzMc) {
		this.hzMc = hzMc;
	}

	public String getHzDx() {
		return this.hzDx;
	}

	public void setHzDx(String hzDx) {
		this.hzDx = hzDx;
	}

	public Short getHzIfcj() {
		return this.hzIfcj;
	}

	public void setHzIfcj(Short hzIfcj) {
		this.hzIfcj = hzIfcj;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getHzRemark() {
		return this.hzRemark;
	}

	public void setHzRemark(String hzRemark) {
		this.hzRemark = hzRemark;
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

	public String getHzKssj() {
		return hzKssj;
	}

	public void setHzKssj(String hzKssj) {
		this.hzKssj = hzKssj;
	}

	public String getHzJssj() {
		return hzJssj;
	}

	public void setHzJssj(String hzJssj) {
		this.hzJssj = hzJssj;
	}

	public String getHzSj() {
		return hzSj;
	}

	public void setHzSj(String hzSj) {
		this.hzSj = hzSj;
	}

	public Short getHzLx() {
		return hzLx;
	}

	public void setHzLx(Short hzLx) {
		this.hzLx = hzLx;
	}
	
}