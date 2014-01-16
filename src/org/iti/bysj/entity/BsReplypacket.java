package org.iti.bysj.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * BsReplypacket entity. @author MyEclipse Persistence Tools
 */

public class BsReplypacket implements java.io.Serializable {

	// Fields
	
	public static final Short BRP_RBATCH_ONE=1;
	public static final Short BRP_RBATCH_TWO=2;
	private Long brpId;
	private Long buId;
	private Long kuId;
	private Short brpRbatch;
	private Long brpBtime;
	private String brpAddress;
	private Long brpEtime;
	WkTUser user;
	BsGpunit gpunit;
	public BsGpunit getGpunit() {
		if(gpunit==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.gpunit=(BsGpunit)userService.get(BsGpunit.class, buId);
		}
		return gpunit;
	}

	/**
	 * 获得组长对象，但是组长有可能是空值！！！
	 * @return 组长对象
	 * @author DATIAN	
	 */
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	// Constructors

	/** default constructor */
	public BsReplypacket() {
	}

	/** full constructor */
	public BsReplypacket(Long brpId, Long buId, Long kuId, Short brpRbatch,
			Long brpBtime, String brpAddress, Long brpEtime) {
		this.brpId = brpId;
		this.buId = buId;
		this.kuId = kuId;
		this.brpRbatch = brpRbatch;
		this.brpBtime = brpBtime;
		this.brpAddress = brpAddress;
		this.brpEtime = brpEtime;
	}

	// Property accessors

	public Long getBrpId() {
		return this.brpId;
	}

	public void setBrpId(Long brpId) {
		this.brpId = brpId;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Short getBrpRbatch() {
		return this.brpRbatch;
	}

	public void setBrpRbatch(Short brpRbatch) {
		this.brpRbatch = brpRbatch;
	}

	public Long getBrpBtime() {
		return this.brpBtime;
	}

	public void setBrpBtime(Long brpBtime) {
		this.brpBtime = brpBtime;
	}

	public String getBrpAddress() {
		return this.brpAddress;
	}

	public void setBrpAddress(String brpAddress) {
		this.brpAddress = brpAddress;
	}

	public Long getBrpEtime() {
		return this.brpEtime;
	}

	public void setBrpEtime(Long brpEtime) {
		this.brpEtime = brpEtime;
	}

}