package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhQt entity. @author MyEclipse Persistence Tools
 */

public class GhQt implements java.io.Serializable {

	// Fields

	private Long qtId;
	private String qtMc;
	private String qtSj;
	private String qtBz;
	private Long kuId;
	//hlj
	private Long kyId;
	private String yjId;
	
	WkTUser user;
	// Constructors
	
	/** default constructor */
	public GhQt() {
	}

	/** minimal constructor */
	public GhQt(Long qtId, Long kuId) {
		this.qtId = qtId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhQt(Long qtId, String qtMc, String qtSj, String qtBz, Long kuId,
			Long kyId,String yjId) {
		this.qtId = qtId;
		this.qtMc = qtMc;
		this.qtSj = qtSj;
		this.qtBz = qtBz;
		this.kuId = kuId;
		this.kyId = kyId;
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
	public Long getQtId() {
		return this.qtId;
	}

	public void setQtId(Long qtId) {
		this.qtId = qtId;
	}

	public String getQtMc() {
		return this.qtMc;
	}

	public void setQtMc(String qtMc) {
		this.qtMc = qtMc;
	}

	public String getQtSj() {
		return this.qtSj;
	}

	public void setQtSj(String qtSj) {
		this.qtSj = qtSj;
	}

	public String getQtBz() {
		return this.qtBz;
	}

	public void setQtBz(String qtBz) {
		this.qtBz = qtBz;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public Long getKyId() {
		return kyId;
	}

	public void setKyId(Long kyId) {
		this.kyId = kyId;
	}

	public String getYjId() {
		return yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}

}