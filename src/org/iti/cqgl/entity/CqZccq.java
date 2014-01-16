package org.iti.cqgl.entity;

import java.util.Date;

import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

/**
 * CqZccq entity. @author MyEclipse Persistence Tools
 */

public class CqZccq implements java.io.Serializable {

	// Fields

	private Long czId;
	private Long kuId;
	private Date czTime;
	private Long czKuid;
	private Long xlId;
	private Long kdId;
	private Long krId;
	private Long ctZid;
	
	public Long getCtZid() {
		return ctZid;
	}

	public void setCtZid(Long ctZid) {
		this.ctZid = ctZid;
	}

	WkTUser user;
	WkTUser lruser;
	JxCalendar calendar;
	WkTDept dept;
	WkTRole role;
	
	// Constructors

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

	public WkTUser getLruser() {
		if(lruser==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.lruser=(WkTUser)userService.get(WkTUser.class, czKuid);
		}
		return lruser;
	}

	public void setLruser(WkTUser lruser) {
		this.lruser = lruser;
	}

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

	public WkTRole getRole() {
		if(role==null){
			RoleService roleService =(RoleService)BeanFactory.getBean("roleService");
			this.role=(WkTRole)roleService.get(WkTRole.class, krId);
		}
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	/** default constructor */
	public CqZccq() {
	}

	/** minimal constructor */
	public CqZccq(Long czId, Long kuId, Long xlId,Long kdId) {
		this.czId = czId;
		this.kuId = kuId;
		this.xlId = xlId;
		this.kdId=kdId;
		
	}

	/** full constructor */
	public CqZccq(Long czId, Long kuId, Date czTime, Integer czType,
			Long czKuid, Long xlId,Long kdId,Long krId ,Long ctZid) {
		this.czId = czId;
		this.kuId = kuId;
		this.czTime = czTime;
		this.czKuid = czKuid;
		this.xlId = xlId;
		this.kdId=kdId;
		this.krId=krId;
		this.ctZid=ctZid;
	}

	// Property accessors

	public Long getCzId() {
		return this.czId;
	}

	public void setCzId(Long czId) {
		this.czId = czId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Date getCzTime() {
		return this.czTime;
	}

	public void setCzTime(Date czTime) {
		this.czTime = czTime;
	}

	public Long getCzKuid() {
		return this.czKuid;
	}

	public void setCzKuid(Long czKuid) {
		this.czKuid = czKuid;
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

	public Long getKrId() {
		return krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

}