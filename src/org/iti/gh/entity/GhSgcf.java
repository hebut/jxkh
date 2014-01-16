package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhSgcf entity. @author MyEclipse Persistence Tools
 */

public class GhSgcf implements java.io.Serializable {

	// Fields

	private Long sgId;
	private Long kuId;
	private String sgYear;
	private String sgName;
	private String sgDep;
	private String sgReason;
	WkTUser user;
	// Constructors

	/** default constructor */
	public GhSgcf() {
	}

	/** minimal constructor */
	public GhSgcf(Long sgId, Long kuId) {
		this.sgId = sgId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhSgcf(Long sgId, Long kuId, String sgYear, String sgName,
			String sgDep, String sgReason) {
		this.sgId = sgId;
		this.kuId = kuId;
		this.sgYear = sgYear;
		this.sgName = sgName;
		this.sgDep = sgDep;
		this.sgReason = sgReason;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public Long getSgId() {
		return this.sgId;
	}

	public void setSgId(Long sgId) {
		this.sgId = sgId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getSgYear() {
		return this.sgYear;
	}

	public void setSgYear(String sgYear) {
		this.sgYear = sgYear;
	}

	public String getSgName() {
		return this.sgName;
	}

	public void setSgName(String sgName) {
		this.sgName = sgName;
	}

	public String getSgDep() {
		return this.sgDep;
	}

	public void setSgDep(String sgDep) {
		this.sgDep = sgDep;
	}

	public String getSgReason() {
		return this.sgReason;
	}

	public void setSgReason(String sgReason) {
		this.sgReason = sgReason;
	}

}