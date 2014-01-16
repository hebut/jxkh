package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhSk entity. @author MyEclipse Persistence Tools
 */

public class GhSk implements java.io.Serializable {

	// Fields

	private Long skId;
	private String skMc;
	private String skZy;
	private String skXs;
	private Long kuId;
	private String thWork;
	private String skGrade;
	private String skYear;
	private Short auditState;
	WkTUser user;
	// Constructors

	/** default constructor */
	public GhSk() {
	}

	/** minimal constructor */
	public GhSk(Long skId, Long kuId) {
		this.skId = skId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhSk(Long skId, String skMc, String skZy, String skXs, Long kuId,String thWork,String skGrade,String skYear,Short auditState) {
		this.skId = skId;
		this.skMc = skMc;
		this.skZy = skZy;
		this.skXs = skXs;
		this.kuId = kuId;
		this.thWork = thWork;
		this.skGrade = skGrade;
		this.skYear = skYear;
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
	public Long getSkId() {
		return this.skId;
	}

	public void setSkId(Long skId) {
		this.skId = skId;
	}

	public String getSkMc() {
		return this.skMc;
	}

	public void setSkMc(String skMc) {
		this.skMc = skMc;
	}

	public String getSkZy() {
		return this.skZy;
	}

	public void setSkZy(String skZy) {
		this.skZy = skZy;
	}

	public String getSkXs() {
		return this.skXs;
	}

	public void setSkXs(String skXs) {
		this.skXs = skXs;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getThWork() {
		return thWork;
	}

	public void setThWork(String thWork) {
		this.thWork = thWork;
	}
	public String getSkGrade() {
		return skGrade;
	}

	public void setSkGrade(String skGrade) {
		this.skGrade = skGrade;
	}

	public String getSkYear() {
		return skYear;
	}

	public void setSkYear(String skYear) {
		this.skYear = skYear;
	}
	public Short getAuditState() {
		return auditState;
	}

	public void setAuditState(Short auditState) {
		this.auditState = auditState;
	}

}