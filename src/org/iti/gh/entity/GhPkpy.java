package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhPkpy entity. @author MyEclipse Persistence Tools
 */

public class GhPkpy implements java.io.Serializable {

	// Fields

	private Long pkId;
	private Long kuId;
	private String pkName;
	private String pkLevel;
	private String pkTime;
	private String pkDept;
	//ÉóºË×´Ì¬
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	WkTUser user;
	WkTUser auditUser;
	private Short auditState;
	private Long auditUid;
	private String reason;
	 

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
	/** default constructor */
	public GhPkpy() {
	}

	/** minimal constructor */
	public GhPkpy(Long pkId, Long kuId) {
		this.pkId = pkId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhPkpy(Long pkId, Long kuId, String pkName, String pkLevel,
			String pkTime, String pkDept,Short auditState,Long auditUid,String reason) {
		this.pkId = pkId;
		this.kuId = kuId;
		this.pkName = pkName;
		this.pkLevel = pkLevel;
		this.pkTime = pkTime;
		this.pkDept = pkDept;
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
	public Long getPkId() {
		return this.pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getPkName() {
		return this.pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkLevel() {
		return this.pkLevel;
	}

	public void setPkLevel(String pkLevel) {
		this.pkLevel = pkLevel;
	}

	public String getPkTime() {
		return this.pkTime;
	}

	public void setPkTime(String pkTime) {
		this.pkTime = pkTime;
	}

	public String getPkDept() {
		return this.pkDept;
	}

	public void setPkDept(String pkDept) {
		this.pkDept = pkDept;
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

}