package org.iti.jxgl.entity;

import java.util.Date;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

/**
 * JxCalendar entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class JxCalendar implements java.io.Serializable {

	// Fields

	private Long id;
	private Long kdId;
	private String CYear;
	private Short term;
	private String weeks;
	private Date starttime;
	private Date endtime;

	private WkTDept dept;
	
	public WkTDept getDept() {
		if (dept == null) {
			DepartmentService deptService = (DepartmentService) BeanFactory.getBean("departmentService");
			dept = (WkTDept) deptService.get(WkTDept.class, kdId);
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
	
	// Constructors

	
	public Date getEndtime() {
		return endtime;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/** default constructor */
	public JxCalendar() {
	}

	/** minimal constructor */
	public JxCalendar(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxCalendar(Long id, Long kdId,String CYear, Short term, String weeks,
			Date starttime) {
		this.id = id;
		this.kdId=kdId;
		this.CYear = CYear;
		this.term = term;
		this.weeks = weeks;
		this.starttime = starttime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCYear() {
		return this.CYear;
	}

	public void setCYear(String CYear) {
		this.CYear = CYear;
	}

	public Short getTerm() {
		return this.term;
	}

	public void setTerm(Short term) {
		this.term = term;
	}

	public String getWeeks() {
		return this.weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

}