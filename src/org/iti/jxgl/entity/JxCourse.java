package org.iti.jxgl.entity;

import org.iti.jxgl.service.CoursetypeService;

import com.uniwin.basehs.util.BeanFactory;


/**
 * JxCourse entity. @author MyEclipse Persistence Tools
 */

public class JxCourse implements java.io.Serializable {

	// Fields
	
	public static final Short SiNone=0;
	private Long jcId;
	private String jcBh;
	private String jcTid;
	private String jcNo;
	private String jcName;
	private Short jcType;
	private Float jcCredit;
	private Float jcWeeks;
	private Float jcTotalhours;
	private Float jcTeachhours;
	private Float jcExperhours;
	private Float jcComhours;
	private Float jcDeshours;
	
	public Float getJcDeshours() {
		return jcDeshours;
	}

	public void setJcDeshours(Float jcDeshours) {
		this.jcDeshours = jcDeshours;
	}

	private Short jcTesttype;
	private Long jcFromkdid;
	private Short jcSign;
	private Short jcLanguage;
	private Short jcWorktype;
	
	public Short getJcWorktype() {
		return jcWorktype;
	}

	public void setJcWorktype(Short jcWorktype) {
		this.jcWorktype = jcWorktype;
	}

	public Short getJcLanguage() {
		return jcLanguage;
	}

	public void setJcLanguage(Short jcLanguage) {
		this.jcLanguage = jcLanguage;
	}

	JxCoursetype jctype;


	public JxCoursetype getJctype() {
		if(jctype==null){
			CoursetypeService coursetypeService=(CoursetypeService)BeanFactory.getBean("coursetypeService");
			this.jctype=(JxCoursetype)coursetypeService.findByJctid(jcTid);
		}
		return jctype;
	}

	public void setJctype(JxCoursetype jctype) {
		this.jctype = jctype;
	}

	/** default constructor */
	public JxCourse() {
	}

	/** minimal constructor */
	public JxCourse(Long jcId) {
		this.jcId = jcId;
	}

	/** full constructor */
	public JxCourse(Long jcId,String jcBh, String jcTid, String jcNo, String jcName,
			Short jcType, Float jcCredit, Float jcWeeks,
			Float jcTotalhours, Float jcTeachhours, Float jcExperhours,
			Float jcComhours, Short jcTesttype, Long jcFromkdid,Short jcSign) {
		this.jcBh = jcBh;
		this.jcTid = jcTid;
		this.jcNo = jcNo;
		this.jcName = jcName;
		this.jcType = jcType;
		this.jcCredit = jcCredit;
		this.jcWeeks = jcWeeks;
		this.jcTotalhours = jcTotalhours;
		this.jcTeachhours = jcTeachhours;
		this.jcExperhours = jcExperhours;
		this.jcComhours = jcComhours;
		this.jcTesttype = jcTesttype;
		this.jcFromkdid = jcFromkdid;
		this.jcId=jcId;
		this.jcSign=SiNone;
	}

	// Property accessors
	public Long getJcId() {
		return jcId;
	}

	public void setJcId(Long jcId) {
		this.jcId = jcId;
	}
	public String getJcBh() {
		return this.jcBh;
	}

	public void setJcBh(String jcBh) {
		this.jcBh = jcBh;
	}

	public String getJcTid() {
		return this.jcTid;
	}

	public void setJcTid(String jcTid) {
		this.jcTid = jcTid;
	}

	public String getJcNo() {
		return this.jcNo;
	}

	public void setJcNo(String jcNo) {
		this.jcNo = jcNo;
	}

	public String getJcName() {
		return this.jcName;
	}

	public void setJcName(String jcName) {
		this.jcName = jcName;
	}

	public Short getJcType() {
		return this.jcType;
	}

	public void setJcType(Short jcType) {
		this.jcType = jcType;
	}

	public Float getJcCredit() {
		return this.jcCredit;
	}

	public void setJcCredit(Float jcCredit) {
		this.jcCredit = jcCredit;
	}

	public Float getJcWeeks() {
		return this.jcWeeks;
	}

	public void setJcWeeks(Float jcWeeks) {
		this.jcWeeks = jcWeeks;
	}

	public Float getJcTotalhours() {
		return this.jcTotalhours;
	}

	public void setJcTotalhours(Float jcTotalhours) {
		this.jcTotalhours = jcTotalhours;
	}

	public Float getJcTeachhours() {
		return this.jcTeachhours;
	}

	public void setJcTeachhours(Float jcTeachhours) {
		this.jcTeachhours = jcTeachhours;
	}

	public Float getJcExperhours() {
		return this.jcExperhours;
	}

	public void setJcExperhours(Float jcExperhours) {
		this.jcExperhours = jcExperhours;
	}

	public Float getJcComhours() {
		return this.jcComhours;
	}

	public void setJcComhours(Float jcComhours) {
		this.jcComhours = jcComhours;
	}

	public Short getJcTesttype() {
		return this.jcTesttype;
	}

	public void setJcTesttype(Short jcTesttype) {
		this.jcTesttype = jcTesttype;
	}

	public Long getJcFromkdid() {
		return this.jcFromkdid;
	}

	public void setJcFromkdid(Long jcFromkdid) {
		this.jcFromkdid = jcFromkdid;
	}
	public Short getJcSign() {
		return this.jcSign;
	}

	public void setJcSign(Short jcSign) {
		this.jcSign = jcSign;
	}

}