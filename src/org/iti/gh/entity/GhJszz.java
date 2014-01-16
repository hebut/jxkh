package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhJszz entity. @author MyEclipse Persistence Tools
 */

public class GhJszz implements java.io.Serializable {

	// Fields

	private Long jszzId;
	private Long zzId;
	private Short jszzType;
	private Long kuId;
	private String zzWords;
	private String  zzSelfs;
	// Fields
	public static final Short JC=1,ZZ=2;
	public static final String chiefEditor = "主编",DeputyEditor = "副主编", editor = "参编者";
	WkTUser user;
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	// Constructors

	/** default constructor */
	public GhJszz() {
	}

	/** minimal constructor */
	public GhJszz(String zzWords) {
		this.zzWords = zzWords;
	}

	/** full constructor */
	public GhJszz(Long zzId, Short jszzType, Long kuId, String zzWords,String  zzSelfs) {
		this.zzId = zzId;
		this.jszzType = jszzType;
		this.kuId = kuId;
		this.zzWords = zzWords;
		this.zzSelfs = zzSelfs;
	}

	// Property accessors

	public Long getJszzId() {
		return this.jszzId;
	}

	public void setJszzId(Long jszzId) {
		this.jszzId = jszzId;
	}

	public Long getZzId() {
		return this.zzId;
	}

	public void setZzId(Long zzId) {
		this.zzId = zzId;
	}

	public Short getJszzType() {
		return this.jszzType;
	}

	public void setJszzType(Short jszzType) {
		this.jszzType = jszzType;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getZzWords() {
		return this.zzWords;
	}

	public void setZzWords(String zzWords) {
		this.zzWords = zzWords;
	}

	public String getZzSelfs() {
		return zzSelfs;
	}

	public void setZzSelfs(String zzSelfs) {
		this.zzSelfs = zzSelfs;
	}
	

}