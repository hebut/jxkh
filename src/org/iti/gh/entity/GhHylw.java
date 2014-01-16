package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhHylw entity. @author MyEclipse Persistence Tools
 */
public class GhHylw implements java.io.Serializable {
	private Long lwId;
	private String lwMc;
	private String lwFbsj;
	private String lwKw;
	private String lwHymc;
	private Short lwLx;
	private Long kuId;
	private String lwEname;
	private String lwAll;
	private String lwPlace;
	private String lwTime;
	private String lwHost;
	private String lwPages;
	private String lwPublish;
	private String lwRecord;
	private String lwNum;
	private String lwRemark;
	private Short lwZd;
	private Short auditState;
	private Long auditUid;
	private String auditReason;
	// Fields
	public static final Short KYLW = 1, JYLW = 2;
	// 审核状态
	public static final Short AUDIT_NO = 0, AUDIT_YES = 1;
	// 学科分类（0自然科学，1社会科学）
	public static final Short LWZY = 0, LWSH = 1;
	// 0为指导学生，1为指导教师
	public static final Short LWZDXS = 0, LWZDJS = 1;
	public static final String SCI="1",EI="2",ISTP="3",CSCD="4";
	WkTUser user;
	WkTUser auditUser;

	public WkTUser getAuditUser() {
		if (auditUser == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.auditUser = (WkTUser) userService.get(WkTUser.class, auditUid);
		}
		return auditUser;
	}

	public void setAuditUser(WkTUser auditUser) {
		this.auditUser = auditUser;
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	// Constructors
	/** default constructor */
	public GhHylw() {
	}

	/** full constructor */
	public GhHylw(String lwMc, String lwFbsj, String lwKw, Short lwLx, Long kuId, String lwAll, String lwEname, String lwPlace, String lwTime, String lwHymc, String lwHost, String lwPages, String lwPublish, String lwRecord, String lwNum, String lwRemark, Short lwZd, Short auditState, Long auditUid, String auditReason) {
		this.lwMc = lwMc;
		this.lwFbsj = lwFbsj;
		this.lwKw = lwKw;
		this.lwHymc = lwHymc;
		this.lwLx = lwLx;
		this.kuId = kuId;
		this.lwEname = lwEname;
		this.lwAll = lwAll;
		this.lwPlace = lwPlace;
		this.lwTime = lwTime;
		this.lwHost = lwHost;
		this.lwPages = lwPages;
		this.lwPublish = lwPublish;
		this.lwRecord = lwRecord;
		this.lwNum = lwNum;
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

	public String getLwPlace() {
		return this.lwPlace;
	}

	public void setLwPlace(String lwPlace) {
		this.lwPlace = lwPlace;
	}

	public String getLwTime() {
		return this.lwTime;
	}

	public void setLwTime(String lwTime) {
		this.lwTime = lwTime;
	}

	public String getLwHost() {
		return this.lwHost;
	}

	public void setLwHost(String lwHost) {
		this.lwHost = lwHost;
	}

	public String getLwPages() {
		return this.lwPages;
	}

	public void setLwPages(String lwPages) {
		this.lwPages = lwPages;
	}

	public String getLwPublish() {
		return this.lwPublish;
	}

	public void setLwPublish(String lwPublish) {
		this.lwPublish = lwPublish;
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

	public String getLwHymc() {
		return lwHymc;
	}

	public void setLwHymc(String lwHymc) {
		this.lwHymc = lwHymc;
	}

	public String getLwEname() {
		return lwEname;
	}

	public void setLwEname(String lwEname) {
		this.lwEname = lwEname;
	}
}