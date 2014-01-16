package org.iti.cqgl.entity;

import java.util.Date;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

/**
 * CqCqcs entity. @author MyEclipse Persistence Tools
 */

public class CqCqcs implements java.io.Serializable {

	// Fields

	private Long ccId;
	private Long ccZczcs;
	private Long ccZczscs;
	private Long ccKyhdzcs;
	private Long ccKyhdzscs;
	private Long xlId;
	private Long kdId;
    private Date endDate;
    private Date zcendDate;
	JxCalendar calendar;
	WkTDept dept;
	
	
	
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

	public WkTDept getDept() {
		
		if(dept==null){
			DepartmentService departmentService =(DepartmentService)BeanFactory.getBean("departmentService");
		    this.dept=(WkTDept)departmentService.get(WkTDept.class, kdId);
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	/** default constructor */
	public CqCqcs() {
	}

	/** minimal constructor */
	public CqCqcs(Long ccId, Long ccZczcs, Long xlId,Long kdId) {
		this.ccId = ccId;
		this.ccZczcs = ccZczcs;
		this.xlId = xlId;
		this.kdId=kdId;
	}

	/** full constructor */
	public CqCqcs(Long ccId, Long ccZczcs, Long ccZczscs, Long ccKyhdzcs,
			Long ccKyhdzscs, Long xlId,Long kdId,Date endDate,Date zcendDate) {
		this.ccId = ccId;
		this.ccZczcs = ccZczcs;
		this.ccZczscs = ccZczscs;
		this.ccKyhdzcs = ccKyhdzcs;
		this.ccKyhdzscs = ccKyhdzscs;
		this.xlId = xlId;
		this.kdId=kdId;
		this.endDate = endDate;
		this.zcendDate = zcendDate;
	}

	// Property accessors

	public Long getCcId() {
		return this.ccId;
	}

	public void setCcId(Long ccId) {
		this.ccId = ccId;
	}

	public Long getCcZczcs() {
		return this.ccZczcs;
	}

	public void setCcZczcs(Long ccZczcs) {
		this.ccZczcs = ccZczcs;
	}

	public Long getCcZczscs() {
		return this.ccZczscs;
	}

	public void setCcZczscs(Long ccZczscs) {
		this.ccZczscs = ccZczscs;
	}

	public Long getCcKyhdzcs() {
		return this.ccKyhdzcs;
	}

	public void setCcKyhdzcs(Long ccKyhdzcs) {
		this.ccKyhdzcs = ccKyhdzcs;
	}

	public Long getCcKyhdzscs() {
		return this.ccKyhdzscs;
	}

	public void setCcKyhdzscs(Long ccKyhdzscs) {
		this.ccKyhdzscs = ccKyhdzscs;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getZcendDate() {
		return zcendDate;
	}

	public void setZcendDate(Date zcendDate) {
		this.zcendDate = zcendDate;
	}

}