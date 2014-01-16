package org.iti.gh.entity;

import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.YjfxService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class GhJsxm implements java.io.Serializable{

	//1科研项目，2教研项目，3获奖科研，4获奖教研
	public static final Integer KYXM=1,JYXM=2,HjKY=3,HJJY=4;
	private Long jsxmId;
	private Long kyId;
	private Long kuId;
	private Integer jsxmType;
	private String kyCdrw;
	private Long yjId;
	private Integer kyGx;
	private String kyMemberName;
	private String kyTaskDesc;
	
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
	
	public Long getJsxmId() {
		return jsxmId;
	}
	public void setJsxmId(Long jsxmId) {
		this.jsxmId = jsxmId;
	}
	public Long getKyId() {
		return kyId;
	}
	public void setKyId(Long kyId) {
		this.kyId = kyId;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public Integer getJsxmType() {
		return jsxmType;
	}
	public void setJsxmType(Integer jsxmType) {
		this.jsxmType = jsxmType;
	}
	
	public String getKyCdrw() {
		return kyCdrw;
	}
	public void setKyCdrw(String kyCdrw) {
		this.kyCdrw = kyCdrw;
	}
	public Long getYjId() {
		return yjId;
	}
	public void setYjId(Long yjId) {
		this.yjId = yjId;
	}
	public Integer getKyGx() {
		return kyGx;
	}
	public void setKyGx(Integer kyGx) {
		this.kyGx = kyGx;
	}
	public GhJsxm() {
		super();
	}
	public GhJsxm(Long jsxmId, Long kyId, Long kuId, Integer jsxmType,
			String kyCdrw, Long yjId, Integer kyGx,String kyMemberName,String kyTaskDesc) {
		super();
		this.jsxmId = jsxmId;
		this.kyId = kyId;
		this.kuId = kuId;
		this.jsxmType = jsxmType;
		this.kyCdrw = kyCdrw;
		this.yjId = yjId;
		this.kyGx = kyGx;
		this.kyMemberName=kyMemberName;
		this.kyTaskDesc=kyTaskDesc;
	}
	public String getKyMemberName() {
		return kyMemberName;
	}
	public void setKyMemberName(String kyMemberName) {
		this.kyMemberName = kyMemberName;
	}
	public String getKyTaskDesc() {
		return kyTaskDesc;
	}
	public void setKyTaskDesc(String kyTaskDesc) {
		this.kyTaskDesc = kyTaskDesc;
	}
	
}
