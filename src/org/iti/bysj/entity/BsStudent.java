package org.iti.bysj.entity;

import org.iti.xypt.entity.Student;
import org.iti.xypt.service.StudentService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * BsStudent entity. @author MyEclipse Persistence Tools
 */

public class BsStudent implements java.io.Serializable {

	public static final Short PASSONE_NONE=0,PASSONE_XZR=1,PASSONE_JXMS=2;
	public static final Short PASSTWO_NONE=0,PASSTWO_XZR=1,PASSTWO_JXMS=2;
	
	public static final String[] NORMALS={"正常","休学","复学","退学","留级","延长学习年限","免修","其他"};
	
	public static final Short STATUS_NORMAL=0;

	// Fields[]0正常1休学2复学3退学4留级5延长学习年限6免修7其他	
	private Long bsId;
	private Long kuId;
	private Long buId;
	private Long bbId;
	private Short stNormal;
	public Short getStNormal() {
		return stNormal;
	}

	public void setStNormal(Short stNormal) {
		this.stNormal = stNormal;
	}

	private Short bstIspassone;
	private Short bstIspasstwo;
	
	
	private Student student;

	// Constructors

	public Student getStudent() {
		if(student==null){
			StudentService stService=(StudentService)BeanFactory.getBean("studentService");
			this.student=(Student)stService.get(Student.class, kuId);
		}
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/** default constructor */
	public BsStudent() {
		this.bbId=0L;
		this.bstIspassone=BsStudent.PASSONE_NONE;
		this.bstIspasstwo=BsStudent.PASSTWO_NONE;
		this.stNormal=0;
	}

	/** full constructor */
	public BsStudent(Long bsId, Long kuId, Long buId, Long bbId,Short stNormal,
			Short bstIspassone, Short bstIspasstwo) {
		this.bsId = bsId;
		this.kuId = kuId;
		this.buId = buId;
		this.bbId = bbId;
		this.stNormal=stNormal;
		this.bstIspassone = bstIspassone;
		this.bstIspasstwo = bstIspasstwo;
	}

	// Property accessors

	public Long getBsId() {
		return this.bsId;
	}

	public void setBsId(Long bsId) {
		this.bsId = bsId;
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

	public Long getBbId() {
		return this.bbId;
	}

	public void setBbId(Long bbId) {
		this.bbId = bbId;
	}

	public Short getBstIspassone() {
		return this.bstIspassone;
	}

	public void setBstIspassone(Short bstIspassone) {
		this.bstIspassone = bstIspassone;
	}

	public Short getBstIspasstwo() {
		return this.bstIspasstwo;
	}

	public void setBstIspasstwo(Short bstIspasstwo) {
		this.bstIspasstwo = bstIspasstwo;
	}

}