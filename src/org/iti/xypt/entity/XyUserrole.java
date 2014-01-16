package org.iti.xypt.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

/**
 * XyUserrole entity. @author MyEclipse Persistence Tools
 */

public class XyUserrole implements java.io.Serializable {

	// Fields

	private XyUserroleId id;
	private Long kdId;

	WkTUser user;

	WkTDept dept;

	public WkTDept getDept() {
		if (dept == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.dept = (WkTDept) userService.get(WkTDept.class, kdId);
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, id.getKuId());
		}
		return user;
	}

	WkTRole role;

	// Constructors

	public WkTRole getRole() {
		if (role == null) {
			RoleService roleService = (RoleService) BeanFactory.getBean("roleService");
			this.role = roleService.findByRid(id.getKrId());
		}
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	/** default constructor */
	public XyUserrole() {
	}

	/** full constructor */
	public XyUserrole(XyUserroleId id, Long kdId) {
		this.id = id;
		this.kdId = kdId;
	}

	// Property accessors

	public XyUserroleId getId() {
		return this.id;
	}

	public void setId(XyUserroleId id) {
		this.id = id;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

}