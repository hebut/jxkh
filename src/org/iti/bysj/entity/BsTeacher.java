package org.iti.bysj.entity;

import org.iti.bysj.service.GpunitService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * BsTeacher entity. @author MyEclipse Persistence Tools
 */

public class BsTeacher implements java.io.Serializable {

	// Fields
	//public static Short GROUP_NO = 0, GROUP_YES = 1;

	private Long btId;
	private Long kuId;
	private Long buId;
	//private Short btIsgroup;
	Teacher teacher;
	BsGpunit bsGpunt;

	// Constructors

	public BsGpunit getBsGpunt() {
		if(bsGpunt==null){
			GpunitService gpunitService=(GpunitService) BeanFactory.getBean("gpunitService");
			this.bsGpunt=(BsGpunit) gpunitService.get(BsGpunit.class, buId);
		}
		return bsGpunt;
	}

	public void setBsGpunt(BsGpunit bsGpunt) {
		this.bsGpunt = bsGpunt;
	}

	public Teacher getTeacher() {
		if (teacher == null) {
			TeacherService teacherService = (TeacherService) BeanFactory.getBean("teacherService");
			this.teacher = (Teacher) teacherService.get(Teacher.class, kuId);
		}
		return teacher;
	}

	public void setTeacher(Teacher tea) {
		this.teacher = tea;
	}

	// private Integer TId;

	// Constructors

	/** default constructor */
	public BsTeacher() {
	}

	/** full constructor */
	public BsTeacher(Long btId, Long kuId, Long buId) {
		this.btId = btId;
		this.kuId = kuId;
		this.buId = buId;
		//this.btIsgroup = btIsgroup;
		// this.TId = TId;
	}

	// Property accessors

	public Long getBtId() {
		return this.btId;
	}

	public void setBtId(Long btId) {
		this.btId = btId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

//	public Short getBtIsgroup() {
//		return this.btIsgroup;
//	}
//
//	public void setBtIsgroup(Short btIsgroup) {
//		this.btIsgroup = btIsgroup;
//	}

}