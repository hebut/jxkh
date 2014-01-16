package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhFmzl entity. @author MyEclipse Persistence Tools
 */

public class GhFmzl implements java.io.Serializable {
//新加静态变量
	public static final String FM_FM="1",FM_NEW="2",FM_DESIGN="3";
	//专利类别（1发明，2实用新型，3外观设计）
	// Fields
	public static final Short STA_NO=0,STA_YES=1;
	//0无效专利1有效专利
	
	private Long fmId;
	private String fmMc;
	private String fmSj;
	private String fmSqh;
	private Long kuId;
	private Short auditState;
	private Long auditUid;
	private String reason;
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	WkTUser user;
	WkTUser auditUser;
	// Constructors
//新加字段
	private String yjId;
	private String fmCountry;
	private String fmTypes;
	private Short fmStatus;
	private String fmInventor;
	private Integer fmRank;
	private String fmRequestno;
	private String fmRequestdate;
	private String fmReqpublino;
	
	private Long zzId;
	

	/** default constructor */
	public GhFmzl() {
	}

	/** minimal constructor */
	public GhFmzl(Long fmId, Long kuId) {
		this.fmId = fmId;
		this.kuId = kuId;
	}

	/** full constructor */
	 

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public GhFmzl(Long fmId, String fmMc, String fmSj, String fmSqh, Long kuId,
			Short auditState, Long auditUid, String reason, String yjId,
			String fmCountry, String fmTypes, Short fmStatus,
			String fmInventor, Integer fmRank, String fmRequestno,
			String fmRequestdate, String fmReqpublino, Long zzId) {
		super();
		this.fmId = fmId;
		this.fmMc = fmMc;
		this.fmSj = fmSj;
		this.fmSqh = fmSqh;
		this.kuId = kuId;
		this.auditState = auditState;
		this.auditUid = auditUid;
		this.reason = reason;
		this.yjId = yjId;
		this.fmCountry = fmCountry;
		this.fmTypes = fmTypes;
		this.fmStatus = fmStatus;
		this.fmInventor = fmInventor;
		this.fmRank = fmRank;
		this.fmRequestno = fmRequestno;
		this.fmRequestdate = fmRequestdate;
		this.fmReqpublino = fmReqpublino;
		this.zzId = zzId;
	}

	public Long getFmId() {
		return this.fmId;
	}

	public void setFmId(Long fmId) {
		this.fmId = fmId;
	}

	public String getFmMc() {
		return this.fmMc;
	}

	public void setFmMc(String fmMc) {
		this.fmMc = fmMc;
	}

	public String getFmSj() {
		return this.fmSj;
	}

	public void setFmSj(String fmSj) {
		this.fmSj = fmSj;
	}

	public String getFmSqh() {
		return this.fmSqh;
	}

	public void setFmSqh(String fmSqh) {
		this.fmSqh = fmSqh;
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

	public String getFmCountry() {
		return this.fmCountry;
	}

	public void setFmCountry(String fmCountry) {
		this.fmCountry = fmCountry;
	}

	public String getFmTypes() {
		return this.fmTypes;
	}

	public void setFmTypes(String fmTypes) {
		this.fmTypes = fmTypes;
	}

	public Short getFmStatus() {
		return this.fmStatus;
	}

	public void setFmStatus(Short fmStatus) {
		this.fmStatus = fmStatus;
	}

	public String getFmInventor() {
		return this.fmInventor;
	}

	public void setFmInventor(String fmInventor) {
		this.fmInventor = fmInventor;
	}

	public Integer getFmRank() {
		return this.fmRank;
	}

	public void setFmRank(Integer fmRank) {
		this.fmRank = fmRank;
	}

	public String getFmRequestno() {
		return this.fmRequestno;
	}

	public void setFmRequestno(String fmRequestno) {
		this.fmRequestno = fmRequestno;
	}

	public String getFmRequestdate() {
		return this.fmRequestdate;
	}

	public void setFmRequestdate(String fmRequestdate) {
		this.fmRequestdate = fmRequestdate;
	}

	public String getFmReqpublino() {
		return this.fmReqpublino;
	}

	public void setFmReqpublino(String fmReqpublino) {
		this.fmReqpublino = fmReqpublino;
	}

	
	public Long getZzId() {
		return zzId;
	}

	public void setZzId(Long zzId) {
		this.zzId = zzId;
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