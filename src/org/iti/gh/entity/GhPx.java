package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhPx entity. @author MyEclipse Persistence Tools
 */

public class GhPx implements java.io.Serializable {

	// Fields

	private Long pxId;
	private String pxMc;
	private String pxDw;
	private String pxJx;
	private String pxBrzy;
	private Long kuId;
	//hlj
	private String pxDj;
	private String pxTime;
	private String pxStu;
	//ÉóºË×´Ì¬
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	WkTUser user;
	WkTUser auditUser;
	private Short auditState;
	private Long auditUid;
	private String reason;
	// Constructors

	/** default constructor */
	public GhPx() {
	}

	/** minimal constructor */
	public GhPx(Long pxId, Long kuId) {
		this.pxId = pxId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhPx(Long pxId, String pxMc, String pxDw, String pxJx,
			String pxBrzy, Long kuId, String pxDj,String pxTime,String pxStu,Short auditState,Long auditUid,String reason) {
		this.pxId = pxId;
		this.pxMc = pxMc;
		this.pxDw = pxDw;
		this.pxJx = pxJx;
		this.pxBrzy = pxBrzy;
		this.kuId = kuId;
		this.pxDj = pxDj;
		this.pxTime = pxTime;
		this.pxStu = pxStu;
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
	public Long getPxId() {
		return this.pxId;
	}

	public void setPxId(Long pxId) {
		this.pxId = pxId;
	}

	public String getPxMc() {
		return this.pxMc;
	}

	public void setPxMc(String pxMc) {
		this.pxMc = pxMc;
	}

	public String getPxDw() {
		return this.pxDw;
	}

	public void setPxDw(String pxDw) {
		this.pxDw = pxDw;
	}

	public String getPxJx() {
		return this.pxJx;
	}

	public void setPxJx(String pxJx) {
		this.pxJx = pxJx;
	}

	public String getPxBrzy() {
		return this.pxBrzy;
	}

	public void setPxBrzy(String pxBrzy) {
		this.pxBrzy = pxBrzy;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getPxDj() {
		return pxDj;
	}

	public void setPxDj(String pxDj) {
		this.pxDj = pxDj;
	}

	public String getPxTime() {
		return pxTime;
	}

	public void setPxTime(String pxTime) {
		this.pxTime = pxTime;
	}

	public String getPxStu() {
		return pxStu;
	}

	public void setPxStu(String pxStu) {
		this.pxStu = pxStu;
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
	
	
}