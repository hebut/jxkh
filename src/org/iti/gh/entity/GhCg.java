package org.iti.gh.entity;

import org.iti.gh.service.XmService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GhCg implements java.io.Serializable {

	// Fields

	public static final Short CG_KY=1,CG_JY=2;
	public static final Short AUDIT_NO=0,AUDIT_YES=1;
	private Long kyId;
	private String kyMc;
	private String kyLy;
	private String kySj;
	private String kyHjmc;
	private String kyDj;
	private Integer kyGrpm;
	private Integer kyZrs;
	private short kyLx;
	private Short  kyJb;
	private Long kuId;
	
	//hlj
	private String kyIdentorg;
	private String kyPrizenum;
	private String kyPrizedep;
	private String kyNumber;
	private String kyPrizeper;
	private Long kyXmid;
	private Short auditState;
	private Long auditUid;
	private String reason;
	public static final Short CG_GJ=1,CG_SB=2,CG_QT=3;

	WkTUser user;
	WkTUser auditUser;

	// Constructors

	
	/** default constructor */
	public GhCg() {
	}

	/** minimal constructor */
	public GhCg(Long kyId, short kyLx, Long kuId) {
		this.kyId = kyId;
		this.kyLx = kyLx;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhCg(Long kyId, String kyMc, String kyLy, String kySj, String kyHjmc,String kyDj,
			Integer kyGrpm, Integer kyZrs, short kyLx,Short  kyJb, Long kuId,
			String kyIdentorg,String kyPrizenum,String kyPrizedep,String kyNumber,
			String kyPrizeper, Long kyXmid,Short auditState,Long auditUid,String reason) {
		this.kyId = kyId;
		this.kyMc = kyMc;
		this.kyLy = kyLy;
		this.kySj = kySj;
		this.kyHjmc = kyHjmc;
		this.kyDj = kyDj;
		this.kyGrpm = kyGrpm;
		this.kyZrs = kyZrs;
		this.kyLx = kyLx;
		this.kyJb = kyJb;
		this.kuId = kuId;
		this.kyIdentorg = kyIdentorg;
		this.kyPrizenum = kyPrizenum;
		this.kyPrizedep = kyPrizedep;
		this.kyNumber = kyNumber;
		this.kyPrizeper = kyPrizeper;
		this.kyXmid = kyXmid;
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
	


	public Long getKyId() {
		return this.kyId;
	}

	public void setKyId(Long kyId) {
		this.kyId = kyId;
	}

	public String getKyMc() {
		return this.kyMc;
	}

	public void setKyMc(String kyMc) {
		this.kyMc = kyMc;
	}

	public String getKyLy() {
		return this.kyLy;
	}

	public void setKyLy(String kyLy) {
		this.kyLy = kyLy;
	}

	public String getKySj() {
		return this.kySj;
	}

	public void setKySj(String kySj) {
		this.kySj = kySj;
	}

	public String getKyDj() {
		return this.kyDj;
	}

	public void setKyDj(String kyDj) {
		this.kyDj = kyDj;
	}

	public Integer getKyGrpm() {
		return this.kyGrpm;
	}

	public void setKyGrpm(Integer kyGrpm) {
		this.kyGrpm = kyGrpm;
	}

	public Integer getKyZrs() {
		return this.kyZrs;
	}

	public void setKyZrs(Integer kyZrs) {
		this.kyZrs = kyZrs;
	}

	public short getKyLx() {
		return this.kyLx;
	}

	public void setKyLx(short kyLx) {
		this.kyLx = kyLx;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getKyIdentorg() {
		return kyIdentorg;
	}

	public void setKyIdentorg(String kyIdentorg) {
		this.kyIdentorg = kyIdentorg;
	}

	public String getKyPrizenum() {
		return kyPrizenum;
	}

	public void setKyPrizenum(String kyPrizenum) {
		this.kyPrizenum = kyPrizenum;
	}

	public String getKyPrizedep() {
		return kyPrizedep;
	}

	public void setKyPrizedep(String kyPrizedep) {
		this.kyPrizedep = kyPrizedep;
	}
	public String getKyNumber() {
		return kyNumber;
	}

	public void setKyNumber(String kyNumber) {
		this.kyNumber = kyNumber;
	}
	public String getKyPrizeper() {
		return kyPrizeper;
	}

	public void setKyPrizeper(String kyPrizeper) {
		this.kyPrizeper = kyPrizeper;
	}
	public Long getKyXmid() {
		return kyXmid;
	}

	public void setKyXmid(Long kyXmid) {
		this.kyXmid = kyXmid;
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

	public Short getKyJb() {
		return kyJb;
	}

	public void setKyJb(Short kyJb) {
		this.kyJb = kyJb;
	}

	public String getKyHjmc() {
		return kyHjmc;
	}

	public void setKyHjmc(String kyHjmc) {
		this.kyHjmc = kyHjmc;
	}

}