package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhRjzz entity. @author MyEclipse Persistence Tools
 */

public class GhRjzz implements java.io.Serializable {

	// Fields

	private Long rjId;
	private String rjName;
	private String rjRegisno;
	private String rjTime;
	private String rjSoftno;
	private String rjPerson;
	private String rjOrder;
	private Long kuId;
	private String yjId;
	private String rjFirtime;
	private Short auditState;
	private Long auditUid;
	private String reason;
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	WkTUser user;
	WkTUser auditUser;
	// Constructors

	/** default constructor */
	public GhRjzz() {
	}

	/** minimal constructor */
	public GhRjzz(Long rjId, Long kuId, String yjId) {
		this.rjId = rjId;
		this.kuId = kuId;
		this.yjId = yjId;
	}

	 
 

	
	public GhRjzz(Long rjId, String rjName, String rjRegisno, String rjTime,
			String rjSoftno, String rjPerson, String rjOrder, Long kuId,
			String yjId, String rjFirtime, Short auditState, Long auditUid,
			String reason) {
		super();
		this.rjId = rjId;
		this.rjName = rjName;
		this.rjRegisno = rjRegisno;
		this.rjTime = rjTime;
		this.rjSoftno = rjSoftno;
		this.rjPerson = rjPerson;
		this.rjOrder = rjOrder;
		this.kuId = kuId;
		this.yjId = yjId;
		this.rjFirtime = rjFirtime;
		this.auditState = auditState;
		this.auditUid = auditUid;
		this.reason = reason;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public Long getRjId() {
		return this.rjId;
	}

	public void setRjId(Long rjId) {
		this.rjId = rjId;
	}

	public String getRjName() {
		return this.rjName;
	}

	public void setRjName(String rjName) {
		this.rjName = rjName;
	}

	public String getRjRegisno() {
		return this.rjRegisno;
	}

	public void setRjRegisno(String rjRegisno) {
		this.rjRegisno = rjRegisno;
	}

	public String getRjTime() {
		return this.rjTime;
	}

	public void setRjTime(String rjTime) {
		this.rjTime = rjTime;
	}

	public String getRjSoftno() {
		return this.rjSoftno;
	}

	public void setRjSoftno(String rjSoftno) {
		this.rjSoftno = rjSoftno;
	}

	public String getRjPerson() {
		return this.rjPerson;
	}

	public void setRjPerson(String rjPerson) {
		this.rjPerson = rjPerson;
	}

	public String getRjOrder() {
		return this.rjOrder;
	}

	public void setRjOrder(String rjOrder) {
		this.rjOrder = rjOrder;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getYjId() {
		return this.yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}
	public String getRjFirtime() {
		return rjFirtime;
	}

	public void setRjFirtime(String rjFirtime) {
		this.rjFirtime = rjFirtime;
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

}