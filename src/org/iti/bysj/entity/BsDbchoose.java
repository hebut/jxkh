package org.iti.bysj.entity;

import org.iti.bysj.service.DbchooseService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * BsDbchoose entity. @author MyEclipse Persistence Tools
 */

public class BsDbchoose implements java.io.Serializable {

	// Fields

	public static final Short IFACCEPT_YES=1,IFACCEPT_NO=0;
	private Long bdbId;
	private Long buId;
	private Long btId;
	private Long bsId;
	private Long bprId;
	private Short bdbIfaccept;
	private String bdbTmessage;
	private String bdbSmessage;
	private Long bbId;

	private BsProject project;
	private BsTeacher teacher;
	private BsStudent student;
	public BsProject getProject() {
		if(project==null){
			DbchooseService dbchooseService=(DbchooseService)BeanFactory.getBean("dbchooseService");
			this.project=(BsProject)dbchooseService.get(BsProject.class, bprId);
		}
		return project;
	}

	public BsTeacher getTeacher() {
		if(teacher==null){
			DbchooseService dbchooseService=(DbchooseService)BeanFactory.getBean("dbchooseService");
			this.teacher=(BsTeacher)dbchooseService.get(BsTeacher.class, btId);
		}
		return teacher;
	}

	public BsStudent getStudent() {
		if(student==null){
			DbchooseService dbchooseService=(DbchooseService)BeanFactory.getBean("dbchooseService");
			student= (BsStudent)dbchooseService.get(BsStudent.class, bsId);
		}
		return student;
	}
	// Constructors

	/** default constructor */
	public BsDbchoose() {
		this.bbId=0L;
		this.bprId=0L;
		this.bdbIfaccept=IFACCEPT_NO;		 
	}

	/** minimal constructor */
	public BsDbchoose(Long bdbId, Long buId, Long btId, Long bsId, Long bprId,
			Long bbId) {
		this.bdbId = bdbId;
		this.buId = buId;
		this.btId = btId;
		this.bsId = bsId;
		this.bprId = bprId;
		this.bbId = bbId;
	}

	/** full constructor */
	public BsDbchoose(Long bdbId, Long buId, Long btId, Long bsId, Long bprId,
			Short bdbIfaccept, String bdbTmessage, String bdbSmessage,
			Long bbId) {
		this.bdbId = bdbId;
		this.buId = buId;
		this.btId = btId;
		this.bsId = bsId;
		this.bprId = bprId;
		this.bdbIfaccept = bdbIfaccept;
		this.bdbTmessage = bdbTmessage;
		this.bdbSmessage = bdbSmessage;		
		this.bbId = bbId;
	}

	// Property accessors

	public Long getBdbId() {
		return this.bdbId;
	}

	public void setBdbId(Long bdbId) {
		this.bdbId = bdbId;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getBtId() {
		return this.btId;
	}

	public void setBtId(Long btId) {
		this.btId = btId;
	}

	public Long getBsId() {
		return this.bsId;
	}

	public void setBsId(Long bsId) {
		this.bsId = bsId;
	}

	public Long getBprId() {
		return this.bprId;
	}

	public void setBprId(Long bprId) {
		this.bprId = bprId;
	}

	public Short getBdbIfaccept() {
		return this.bdbIfaccept;
	}

	public void setBdbIfaccept(Short bdbIfaccept) {
		this.bdbIfaccept = bdbIfaccept;
	}

	public String getBdbTmessage() {
		return this.bdbTmessage;
	}

	public void setBdbTmessage(String bdbTmessage) {
		this.bdbTmessage = bdbTmessage;
	}

	public String getBdbSmessage() {
		return this.bdbSmessage;
	}

	public void setBdbSmessage(String bdbSmessage) {
		this.bdbSmessage = bdbSmessage;
	}

	public Long getBbId() {
		return this.bbId;
	}

	public void setBbId(Long bbId) {
		this.bbId = bbId;
	}

}