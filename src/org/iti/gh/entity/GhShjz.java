package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhShjz entity. @author MyEclipse Persistence Tools
 */

public class GhShjz implements java.io.Serializable {

	// Fields

	private Long jzId;
	private Long kuId;
	private String jzPosition;
	private String jzTime;
	private String jzName;
	private String jzCharacter;
	WkTUser user;
	// Constructors

	/** default constructor */
	public GhShjz() {
	}

	/** minimal constructor */
	public GhShjz(Long jzId, Long kuId) {
		this.jzId = jzId;
		this.kuId = kuId;
	}

	/** full constructor */
	public GhShjz(Long jzId, Long kuId, String jzPosition, String jzTime,
			String jzName, String jzCharacter) {
		this.jzId = jzId;
		this.kuId = kuId;
		this.jzPosition = jzPosition;
		this.jzTime = jzTime;
		this.jzName = jzName;
		this.jzCharacter = jzCharacter;
	}

	// Property accessors
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public Long getJzId() {
		return this.jzId;
	}

	public void setJzId(Long jzId) {
		this.jzId = jzId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getJzPosition() {
		return this.jzPosition;
	}

	public void setJzPosition(String jzPosition) {
		this.jzPosition = jzPosition;
	}

	public String getJzTime() {
		return this.jzTime;
	}

	public void setJzTime(String jzTime) {
		this.jzTime = jzTime;
	}

	public String getJzName() {
		return this.jzName;
	}

	public void setJzName(String jzName) {
		this.jzName = jzName;
	}

	public String getJzCharacter() {
		return this.jzCharacter;
	}

	public void setJzCharacter(String jzCharacter) {
		this.jzCharacter = jzCharacter;
	}

}