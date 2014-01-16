package org.iti.gh.entity;

import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.YjfxService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class GhJslw implements java.io.Serializable{

	//1科研会议论文，2科研期刊论文，3教研会议论文，4教研期刊论文，5，发明专利，6 软件著作
	public static final Short KYHY=1,KYQK=2,JYHY=3,JYQK=4,FMZL=5,RJZZ=6;
	private Long jslwId;
	private Long lwzlId;
	private Long kuId;
	private Short jslwtype;
	private String lwWords;
	private Integer lwSelfs;
	private Long yjId;
	WkTUser user;
    GhYjfx yjfx;
	
	
	public GhYjfx getYjfx() {
		if(yjfx==null){
			YjfxService	yjfxService=(YjfxService)BeanFactory.getBean("yjfxService");
			this.yjfx=(GhYjfx)yjfxService.get(GhYjfx.class, yjId);
		}
		return yjfx;
	}
	public void setYjfx(GhYjfx yjfx) {
		this.yjfx = yjfx;
	}
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public void setUser(WkTUser user) {
		
		this.user = user;
	}
	
	public Long getYjId() {
		return yjId;
	}
	public void setYjId(Long yjId) {
		this.yjId = yjId;
	}
	public Long getJslwId() {
		return jslwId;
	}
	public void setJslwId(Long jslwId) {
		this.jslwId = jslwId;
	}
	public Long getLwzlId() {
		return lwzlId;
	}
	public void setLwzlId(Long lwzlId) {
		this.lwzlId = lwzlId;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public Short getJslwtype() {
		return jslwtype;
	}
	public void setJslwtype(Short jslwtype) {
		this.jslwtype = jslwtype;
	}
	public String getLwWords() {
		return lwWords;
	}
	public void setLwWords(String lwWords) {
		this.lwWords = lwWords;
	}
	public Integer getLwSelfs() {
		return lwSelfs;
	}
	public void setLwSelfs(Integer lwSelfs) {
		this.lwSelfs = lwSelfs;
	}
	public GhJslw() {
		super();
	}
	public GhJslw(Long jslwId, Long lwzlId, Long kuId, Short jslwtype,
			String lwWords, Integer lwSelfs,Long yjId) {
		super();
		this.jslwId = jslwId;
		this.lwzlId = lwzlId;
		this.kuId = kuId;
		this.jslwtype = jslwtype;
		this.lwWords = lwWords;
		this.lwSelfs = lwSelfs;
		this.yjId = yjId;
	}
	
	
}
