package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;



/**
 * GhYjfxxm entity. @author MyEclipse Persistence Tools
 */

public class GhYjfxxm  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    
	public static final String[] JB={"国家级","省部级"};
	public static final String[] XMLB={"自然基金","支撑计划","863项目","973项目","其他"};
	public static final String[] LB={"横向","纵向"};
	

     private Long gxId;
     private String gxName;
     private Long gyId;
     private Long kuId;
     private Short gxJb;
     private Short gxXmlb;
     private Short gxLb;
     private Integer gxYear;
     private String gxContent;
     private Long gxTime;
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
    public GhYjfxxm() {
    }

	/** minimal constructor */
    public GhYjfxxm(Long gxId) {
        this.gxId = gxId;
    }
    
    /** full constructor */
    public GhYjfxxm(Long gxId, String gxName, Long gyId, Long kuId, Short gxJb, Short gxXmlb, Short gxLb, Integer gxYear, String gxContent, Long gxTime) {
        this.gxId = gxId;
        this.gxName = gxName;
        this.gyId = gyId;
        this.kuId = kuId;
        this.gxJb = gxJb;
        this.gxXmlb = gxXmlb;
        this.gxLb = gxLb;
        this.gxYear = gxYear;
        this.gxContent = gxContent;
        this.gxTime = gxTime;
    }

   
    // Property accessors

    public Long getGxId() {
        return this.gxId;
    }
    
    public void setGxId(Long gxId) {
        this.gxId = gxId;
    }

    public String getGxName() {
        return this.gxName;
    }
    
    public void setGxName(String gxName) {
        this.gxName = gxName;
    }

    public Long getGyId() {
        return this.gyId;
    }
    
    public void setGyId(Long gyId) {
        this.gyId = gyId;
    }

    public Long getKuId() {
        return this.kuId;
    }
    
    public void setKuId(Long kuId) {
        this.kuId = kuId;
    }

    public Short getGxJb() {
        return this.gxJb;
    }
    
    public void setGxJb(Short gxJb) {
        this.gxJb = gxJb;
    }

    public Short getGxXmlb() {
        return this.gxXmlb;
    }
    
    public void setGxXmlb(Short gxXmlb) {
        this.gxXmlb = gxXmlb;
    }

    public Short getGxLb() {
        return this.gxLb;
    }
    
    public void setGxLb(Short gxLb) {
        this.gxLb = gxLb;
    }

    public Integer getGxYear() {
        return this.gxYear;
    }
    
    public void setGxYear(Integer gxYear) {
        this.gxYear = gxYear;
    }

    public String getGxContent() {
        return this.gxContent;
    }
    
    public void setGxContent(String gxContent) {
        this.gxContent = gxContent;
    }

    public Long getGxTime() {
        return this.gxTime;
    }
    
    public void setGxTime(Long gxTime) {
        this.gxTime = gxTime;
    }
   








}