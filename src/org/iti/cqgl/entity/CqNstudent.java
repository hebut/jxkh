package org.iti.cqgl.entity;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * CqNstudent entity. @author MyEclipse Persistence Tools
 */

public class CqNstudent implements java.io.Serializable {

	// Fields

	private Long cnsId;
	private Long kuId;
	private Integer cnsType;
	private Long xlId;	
	WkTUser user;
	JxCalendar calendar;	
	private Long kdId;

	// Constructors

	public JxCalendar getCalendar() {
		if(calendar==null){
			CalendarService calendarService=(CalendarService)BeanFactory.getBean("calendarService");
			this.calendar=(JxCalendar)calendarService.get(JxCalendar.class, xlId);
		}
		return calendar;
	}

	public void setCalendar(JxCalendar calendar) {
		this.calendar = calendar;
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

	/** default constructor */
	public CqNstudent() {
	}

	/** full constructor */
	public CqNstudent(Long cnsId, Long kuId, Integer cnsType, Long xlId,Long kdId) {
		this.cnsId = cnsId;
		this.kuId = kuId;
		this.cnsType = cnsType;
		this.xlId = xlId;
		this.kdId=kdId;
	}

	// Property accessors

	public Long getCnsId() {
		return this.cnsId;
	}

	public void setCnsId(Long cnsId) {
		this.cnsId = cnsId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Integer getCnsType() {
		return this.cnsType;
	}

	public void setCnsType(Integer cnsType) {
		this.cnsType = cnsType;
	}

	public Long getXlId() {
		return this.xlId;
	}

	public void setXlId(Long xlId) {
		this.xlId = xlId;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

}