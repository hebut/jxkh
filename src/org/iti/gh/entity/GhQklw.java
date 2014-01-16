package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhQklw entity. @author MyEclipse Persistence Tools
 */

public class GhQklw implements java.io.Serializable {

	// Fields

	private Long lwId;
	private String lwMc;
	private String lwFbsj;
	private String lwKw;
	private Short lwLx;
	private Long kuId;
	private String lwAll;
	private String lwEname;
	private String lwPages;
	private String lwRecord;
	private String lwNum;
	private String lwLocation;
	private Short lwCenter;
	private String lwFactor;
	private String lwIssn;
	private String lwRemark;
	private Short lwZd;
	private Short auditState;
	private Long auditUid;
	private String auditReason;
	//是否核心期刊(0-否，1-是)
	public static final Short LWHX_NO=0,LWHX_YES=1;
	   //0为指导学生，1为指导教师
	public static final Short LWZDXS=0,LWZDJS=1;
	
	// 1科研期刊论文 2 教研科研期刊论文
	public static final Short KYLW=1,JYLW=2;
	//审核状态
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	public static final String SCI="1",EI="2",ISTP="3",CSCD="4";
	public static final int firstAuthor = 1,secondAuthor= 2,thirdAuthor=3,allAuthor=0;
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
	public GhQklw() {
	}

	/** full constructor */
	public GhQklw(String lwMc, String lwFbsj, String lwKw, Short lwLx,
			Long kuId, String lwAll,String lwEname, String lwPages, String lwRecord,
			String lwNum, String lwLocation, Short lwCenter, String lwFactor,
			String lwIssn, String lwRemark, Short lwZd, Short auditState,
			Long auditUid, String auditReason) {
		this.lwMc = lwMc;
		this.lwFbsj = lwFbsj;
		this.lwKw = lwKw;
		this.lwLx = lwLx;
		this.kuId = kuId;
		this.lwEname=lwEname;
		this.lwAll = lwAll;
		this.lwPages = lwPages;
		this.lwRecord = lwRecord;
		this.lwNum = lwNum;
		this.lwLocation = lwLocation;
		this.lwCenter = lwCenter;
		this.lwFactor = lwFactor;
		this.lwIssn = lwIssn;
		this.lwRemark = lwRemark;
		this.lwZd = lwZd;
		this.auditState = auditState;
		this.auditUid = auditUid;
		this.auditReason = auditReason;
	}

	// Property accessors

	public Long getLwId() {
		return this.lwId;
	}

	public void setLwId(Long lwId) {
		this.lwId = lwId;
	}

	public String getLwMc() {
		return this.lwMc;
	}

	public void setLwMc(String lwMc) {
		this.lwMc = lwMc;
	}

	public String getLwFbsj() {
		return this.lwFbsj;
	}

	public void setLwFbsj(String lwFbsj) {
		this.lwFbsj = lwFbsj;
	}

	public String getLwKw() {
		return this.lwKw;
	}

	public void setLwKw(String lwKw) {
		this.lwKw = lwKw;
	}

	public Short getLwLx() {
		return this.lwLx;
	}

	public void setLwLx(Short lwLx) {
		this.lwLx = lwLx;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getLwAll() {
		return this.lwAll;
	}

	public void setLwAll(String lwAll) {
		this.lwAll = lwAll;
	}

	public String getLwPages() {
		return this.lwPages;
	}

	public void setLwPages(String lwPages) {
		this.lwPages = lwPages;
	}

	public String getLwRecord() {
		return this.lwRecord;
	}

	public void setLwRecord(String lwRecord) {
		this.lwRecord = lwRecord;
	}

	public String getLwNum() {
		return this.lwNum;
	}

	public void setLwNum(String lwNum) {
		this.lwNum = lwNum;
	}

	public String getLwLocation() {
		return this.lwLocation;
	}

	public void setLwLocation(String lwLocation) {
		this.lwLocation = lwLocation;
	}

	public Short getLwCenter() {
		return this.lwCenter;
	}

	public void setLwCenter(Short lwCenter) {
		this.lwCenter = lwCenter;
	}

	public String getLwFactor() {
		return this.lwFactor;
	}

	public void setLwFactor(String lwFactor) {
		this.lwFactor = lwFactor;
	}

	public String getLwIssn() {
		return this.lwIssn;
	}

	public void setLwIssn(String lwIssn) {
		this.lwIssn = lwIssn;
	}

	public String getLwRemark() {
		return this.lwRemark;
	}

	public void setLwRemark(String lwRemark) {
		this.lwRemark = lwRemark;
	}

	public Short getLwZd() {
		return this.lwZd;
	}

	public void setLwZd(Short lwZd) {
		this.lwZd = lwZd;
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

	public String getLwEname() {
		return lwEname;
	}

	public void setLwEname(String lwEname) {
		this.lwEname = lwEname;
	}

}