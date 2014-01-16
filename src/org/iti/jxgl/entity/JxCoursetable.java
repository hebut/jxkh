package org.iti.jxgl.entity;

import org.iti.jxgl.service.CourseService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * JxCoursetable entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class JxCoursetable implements java.io.Serializable {

	// Fields

	private Long jctId;
	private Long jtId;
	private String jctYear;
	private Short jctTerm;
	private Long kdId;
	private Long kdIdSrc;
	private Long jcId;
	private String jcName;
	private Double jcCredit;
	private String thId;
	private String thName;
	private String jctAddress;
	private String jctTime;
	private String jctWeek;
	private String jctWeeks;
	private String jctClass;
	private String jctSign;
	private Integer jctSumstate;
    private Integer jctClassNum;
    JxCourse jxcourse;
    
    
	public JxCourse getJxcourse() {
		if(jxcourse==null){
		CourseService courseService=(CourseService)BeanFactory.getBean("courseService");
		return (JxCourse)courseService.get(JxCourse.class, jcId);
		} 
		return jxcourse;
		 
	}

	public void setJxcourse(JxCourse jxcourse) {
		this.jxcourse = jxcourse;
	}

	public Integer getJctClassNum() {
		return jctClassNum;
	}

	public void setJctClassNum(Integer jctClassNum) {
		this.jctClassNum = jctClassNum;
	}

	/** default constructor */
	public JxCoursetable() {
	}

	/** minimal constructor */
	public JxCoursetable(Long jctId) {
		this.jctId = jctId;
	}

	/** full constructor */
	public JxCoursetable(Long jctId, Long jtId, String jctYear, Short jctTerm, Long kdId, Long kdIdSrc, Long jcId, String jcName, Double jcCredit, String thId, String thName, String jctAddress, String jctTime, String jctWeek, String jctWeeks, String jctClass, String jctSign, Integer jctSumstate,Integer jctClassNum) {
		this.jctId = jctId;
		this.jtId = jtId;
		this.jctYear = jctYear;
		this.jctTerm = jctTerm;
		this.kdId = kdId;
		this.kdIdSrc = kdIdSrc;
		this.jcId = jcId;
		this.jcName = jcName;
		this.jcCredit = jcCredit;
		this.thId = thId;
		this.thName = thName;
		this.jctAddress = jctAddress;
		this.jctTime = jctTime;
		this.jctWeek = jctWeek;
		this.jctWeeks = jctWeeks;
		this.jctClass = jctClass;
		this.jctSign = jctSign;
		this.jctSumstate = jctSumstate;
		this.jctClassNum=jctClassNum;
	}

	public Long getJctId() {
		return jctId;
	}

	public void setJctId(Long jctId) {
		this.jctId = jctId;
	}

	public Long getJtId() {
		return jtId;
	}

	public void setJtId(Long jtId) {
		this.jtId = jtId;
	}

	public String getJctYear() {
		return jctYear;
	}

	public void setJctYear(String jctYear) {
		this.jctYear = jctYear;
	}

	public Short getJctTerm() {
		return jctTerm;
	}

	public void setJctTerm(Short jctTerm) {
		this.jctTerm = jctTerm;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKdIdSrc() {
		return kdIdSrc;
	}

	public void setKdIdSrc(Long kdIdSrc) {
		this.kdIdSrc = kdIdSrc;
	}

	public Long getJcId() {
		return jcId;
	}

	public void setJcId(Long jcId) {
		this.jcId = jcId;
	}

	public String getJcName() {
		return jcName;
	}

	public void setJcName(String jcName) {
		this.jcName = jcName;
	}

	public Double getJcCredit() {
		return jcCredit;
	}

	public void setJcCredit(Double jcCredit) {
		this.jcCredit = jcCredit;
	}

	public String getThId() {
		return thId;
	}

	public void setThId(String thId) {
		this.thId = thId;
	}

	public String getThName() {
		return thName;
	}

	public void setThName(String thName) {
		this.thName = thName;
	}

	public String getJctAddress() {
		return jctAddress;
	}

	public void setJctAddress(String jctAddress) {
		this.jctAddress = jctAddress;
	}

	public String getJctTime() {
		return jctTime;
	}

	public void setJctTime(String jctTime) {
		this.jctTime = jctTime;
	}

	public String getJctWeek() {
		return jctWeek;
	}

	public void setJctWeek(String jctWeek) {
		this.jctWeek = jctWeek;
	}

	public String getJctWeeks() {
		return jctWeeks;
	}

	public void setJctWeeks(String jctWeeks) {
		this.jctWeeks = jctWeeks;
	}

	public String getJctClass() {
		return jctClass;
	}

	public void setJctClass(String jctClass) {
		this.jctClass = jctClass;
	}

	public String getJctSign() {
		return jctSign;
	}

	public void setJctSign(String jctSign) {
		this.jctSign = jctSign;
	}

	public Integer getJctSumstate() {
		return jctSumstate;
	}

	public void setJctSumstate(Integer jctSumstate) {
		this.jctSumstate = jctSumstate;
	}

}