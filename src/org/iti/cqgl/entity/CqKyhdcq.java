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
 * CqKyhdcq entity. @author MyEclipse Persistence Tools
 */

public class CqKyhdcq implements java.io.Serializable {

	// Fields

	private Long ckId;
	private Long kuId;
	private Date ckStart;
	private Date ckEnd;
	private Long ckUid;
	private Long xlId;
	private Long kdId;
	private Long krId;
	private Long ctKid;
	
	public Long getCtKid() {
		return ctKid;
	}

	public void setCtKid(Long ctKid) {
		this.ctKid = ctKid;
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
			this.lruser=(WkTUser)userService.get(WkTUser.class, ckUid);
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
	public CqKyhdcq() {
	}

	/** minimal constructor */
	public CqKyhdcq(Long ckId, Long kuId, Long xlId,Long kdId) {
		this.ckId = ckId;
		this.kuId = kuId;
		this.xlId = xlId;
		this.kdId=kdId;
	}

	/** full constructor */
	public CqKyhdcq(Long ckId, Long kuId, Long xlId,Long krId,Long kdId, Date ckStart, Date ckEnd,
			 Long ckUid,Long ctKid) {
		this.ckId = ckId;
		this.kuId = kuId;
		this.ckStart = ckStart;
		this.ckEnd = ckEnd;
		this.ckUid = ckUid;
		this.xlId = xlId;
		this.kdId=kdId;
		this.krId=krId;
		this.ctKid=ctKid;
	}

	// Property accessors

	public Long getCkId() {
		return this.ckId;
	}

	public void setCkId(Long ckId) {
		this.ckId = ckId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Date getCkStart() {
		return this.ckStart;
	}

	public void setCkStart(Date ckStart) {
		this.ckStart = ckStart;
	}

	public Date getCkEnd() {
		return this.ckEnd;
	}

	public void setCkEnd(Date ckEnd) {
		this.ckEnd = ckEnd;
	}



	public Long getCkUid() {
		return this.ckUid;
	}

	public void setCkUid(Long ckUid) {
		this.ckUid = ckUid;
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