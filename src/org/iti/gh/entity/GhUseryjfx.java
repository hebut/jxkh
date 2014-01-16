package org.iti.gh.entity;

import org.iti.gh.service.YjfxService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhUseryjfx entity. @author MyEclipse Persistence Tools
 */

public class GhUseryjfx implements java.io.Serializable {

	// Fields

	private GhUseryjfxId id;
	private GhYjfx yjfx;
	private WkTUser user;

	// Constructors
	
	public GhYjfx getYjfx() {
		if(yjfx==null){
			YjfxService yjfxService=(YjfxService) BeanFactory.getBean("yjfxService");
			this.yjfx=(GhYjfx)yjfxService.get(GhYjfx.class, id.getGyId());
		}
		return yjfx;
	}

	// Constructors
	
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService) BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, id.getKuId());
		}
		return user;
	}
	// Constructors

	/** default constructor */
	public GhUseryjfx() {
	}

	/** full constructor */
	public GhUseryjfx(GhUseryjfxId id) {
		this.id = id;
	}

	// Property accessors

	public GhUseryjfxId getId() {
		return this.id;
	}

	public void setId(GhUseryjfxId id) {
		this.id = id;
	}

}