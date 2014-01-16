package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhKyjh entity. @author MyEclipse Persistence Tools
 */

public class GhKyjh implements java.io.Serializable {

	// Fields

	private Long jhId;
	private String jhNr;
	private String jhGjwt;
	private String jhYjcg;
	private String jhKysx;
	private Long kuId;
	private String yjId;
	WkTUser user;
	// Constructors

	/** default constructor */
	public GhKyjh() {
	}

	/** minimal constructor */
	public GhKyjh(Long jhId, Long kuId) {
		this.jhId = jhId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhKyjh(Long jhId, String jhNr, String jhGjwt, String jhYjcg,
			String jhKysx, Long kuId, String yjId) {
		this.jhId = jhId;
		this.jhNr = jhNr;
		this.jhGjwt = jhGjwt;
		this.jhYjcg = jhYjcg;
		this.jhKysx = jhKysx;
		this.kuId = kuId;
		this.yjId = yjId;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public Long getJhId() {
		return this.jhId;
	}

	public void setJhId(Long jhId) {
		this.jhId = jhId;
	}

	public String getJhNr() {
		return this.jhNr;
	}

	public void setJhNr(String jhNr) {
		this.jhNr = jhNr;
	}

	public String getJhGjwt() {
		return this.jhGjwt;
	}

	public void setJhGjwt(String jhGjwt) {
		this.jhGjwt = jhGjwt;
	}

	public String getJhYjcg() {
		return this.jhYjcg;
	}

	public void setJhYjcg(String jhYjcg) {
		this.jhYjcg = jhYjcg;
	}

	public String getJhKysx() {
		return this.jhKysx;
	}

	public void setJhKysx(String jhKysx) {
		this.jhKysx = jhKysx;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getYjId() {
		return yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}
}