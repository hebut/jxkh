package org.iti.cqgl.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class CqUsb implements java.io.Serializable{
	private String cuId;
	private Long kuId;
	private WkTUser user;
	private String uipId;
	private Integer cuType;
	private String ceId;

	public String getCeId() {
		return ceId;
	}
	public void setCeId(String ceId) {
		this.ceId = ceId;
	}
	public String getUipId() {
		return uipId;
	}
	public void setUipId(String uipId) {
		this.uipId = uipId;
	}
	public Integer getCuType() {
		return cuType;
	}
	public void setCuType(Integer cuType) {
		this.cuType = cuType;
	}
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	public String getCuId() {
		return cuId;
	}
	public void setCuId(String cuId) {
		this.cuId = cuId;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public CqUsb(){
		
		
	}
	public CqUsb(String cuId,Long kuId,String uipId,Integer cuType,String ceId){
		this.cuId=cuId;
		this.kuId=kuId;
		this.uipId=uipId;
		this.cuType=cuType;
		this.ceId=ceId;
	}
	

}
