package org.iti.cqgl.entity;

import java.io.Serializable;
import java.util.Date;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class CqDstudent implements Serializable {


	// Fields

	private Long cdsId;
	private Long kuId;
	private Integer cdsType;
	private Long xlId;
	private Long dsDate;
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
	public CqDstudent() {
	}

	/** full constructor */
	public CqDstudent(Long cdsId, Long kuId, Integer cdsType, Long xlId,Long kdId,Long dsDate) {
		this.cdsId = cdsId;
		this.kuId = kuId;
		this.cdsType = cdsType;
		this.xlId = xlId;
		this.kdId=kdId;
		this.dsDate=dsDate;
	}

	// Property accessors

	

	

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
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

	public Long getCdsId() {
		return cdsId;
	}

	public void setCdsId(Long cdsId) {
		this.cdsId = cdsId;
	}

	public Integer getCdsType() {
		return cdsType;
	}

	public void setCdsType(Integer cdsType) {
		this.cdsType = cdsType;
	}

	public Long getDsDate() {
		return dsDate;
	}

	public void setDsDate(Long dsDate) {
		this.dsDate = dsDate;
	}

	

}
