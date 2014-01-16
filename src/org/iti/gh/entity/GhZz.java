package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhZz entity. @author MyEclipse Persistence Tools
 */

public class GhZz implements java.io.Serializable {

	// Fields

	private Long zzId;
	private Long kuId;
	private Short zzLx;
	private String zzMc;
	private String zzKw;
	private String zzRemark;
	private String zzWorktype;
	private String zzSubjetype;
	private String zzPublitime;
	private String zzEditionno;
	private String zzZb;
	private String zzFzb;
	private String zzBz;
	private String zzIsbn;
	private String zzNature;
	private Short auditState;
	private Long auditUid;
	private String auditReason;
	// Fields
	public static final Short JC=1,ZZ=2;
	//ÉóºË×´Ì¬
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	WkTUser user;
	WkTUser auditUser;
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
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	// Constructors

	/** default constructor */
	public GhZz() {
	}

	/** full constructor */
	public GhZz(Short zzLx, String zzMc,Long kuId, String zzKw,
			String zzRemark, String zzWorktype, String zzSubjetype,
			String zzPublitime, String zzEditionno, String zzZb, String zzFzb,
			String zzBz, String zzIsbn, String zzNature,Short auditState, Long auditUid,
			String auditReason) {
		this.zzLx = zzLx;
		this.zzMc = zzMc;
		this.zzKw = zzKw;
		this.kuId=kuId;
		this.zzRemark = zzRemark;
		this.zzWorktype = zzWorktype;
		this.zzSubjetype = zzSubjetype;
		this.zzPublitime = zzPublitime;
		this.zzEditionno = zzEditionno;
		this.zzZb = zzZb;
		this.zzFzb = zzFzb;
		this.zzBz = zzBz;
		this.zzIsbn = zzIsbn;
		this.zzNature=zzNature;
		this.auditState = auditState;
		this.auditUid = auditUid;
		this.auditReason = auditReason;
	}

	// Property accessors

	public Long getZzId() {
		return this.zzId;
	}

	public void setZzId(Long zzId) {
		this.zzId = zzId;
	}

	public Short getZzLx() {
		return this.zzLx;
	}

	public void setZzLx(Short zzLx) {
		this.zzLx = zzLx;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getZzMc() {
		return this.zzMc;
	}

	public void setZzMc(String zzMc) {
		this.zzMc = zzMc;
	}

	public String getZzKw() {
		return this.zzKw;
	}

	public void setZzKw(String zzKw) {
		this.zzKw = zzKw;
	}


	public String getZzRemark() {
		return this.zzRemark;
	}

	public void setZzRemark(String zzRemark) {
		this.zzRemark = zzRemark;
	}

	public String getZzWorktype() {
		return this.zzWorktype;
	}

	public void setZzWorktype(String zzWorktype) {
		this.zzWorktype = zzWorktype;
	}

	public String getZzSubjetype() {
		return this.zzSubjetype;
	}

	public void setZzSubjetype(String zzSubjetype) {
		this.zzSubjetype = zzSubjetype;
	}

	public String getZzPublitime() {
		return this.zzPublitime;
	}

	public void setZzPublitime(String zzPublitime) {
		this.zzPublitime = zzPublitime;
	}

	public String getZzEditionno() {
		return this.zzEditionno;
	}

	public void setZzEditionno(String zzEditionno) {
		this.zzEditionno = zzEditionno;
	}

	public String getZzZb() {
		return this.zzZb;
	}

	public void setZzZb(String zzZb) {
		this.zzZb = zzZb;
	}

	public String getZzFzb() {
		return this.zzFzb;
	}

	public void setZzFzb(String zzFzb) {
		this.zzFzb = zzFzb;
	}

	public String getZzBz() {
		return this.zzBz;
	}

	public void setZzBz(String zzBz) {
		this.zzBz = zzBz;
	}

	public String getZzIsbn() {
		return this.zzIsbn;
	}

	public void setZzIsbn(String zzIsbn) {
		this.zzIsbn = zzIsbn;
	}

	public Short getAuditState() {
		return this.auditState;
	}

	public void setAuditState(Short auditState) {
		this.auditState = auditState;
	}

	public Long getAuditUid() {
		return this.auditUid;
	}

	public void setAuditUid(Long auditUid) {
		this.auditUid = auditUid;
	}

	public String getAuditReason() {
		return this.auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getZzNature() {
		return zzNature;
	}

	public void setZzNature(String zzNature) {
		this.zzNature = zzNature;
	}

}