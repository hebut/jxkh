package org.iti.gh.ghtj;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class QkdcItem {

	private Long kuId;
	private String value;
	String kuName;
	
	public String getKuName() {
		if(kuName==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			WkTUser user=(WkTUser) userService.get(WkTUser.class, kuId);
			if(user==null){
				kuName="δ֪";
			}else{
				kuName=user.getKuName();
			}
		}
		return kuName;
	}
	public void setKuName(String kuName) {
		this.kuName = kuName;
	}
	public QkdcItem(Long kuId, String value) { 
		this.kuId = kuId;
		this.value = value;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
