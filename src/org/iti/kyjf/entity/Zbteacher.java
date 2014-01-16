package org.iti.kyjf.entity;

import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class Zbteacher implements java.io.Serializable{
	 

private Long ztId;
private	Long kuId;
private	Short ztZc;
private	Short ztIfgg;
private	Short ztIfcj;
private	Integer ztYear;
private	Float ztSum;
private Short ztDs;
public static Short BD=0,SD=1,QTDS=2;//博导，硕导，普通
public static Short ZGZ=0,FGZ=1,PT=2;//正高职，副高职，普通
public static Short  GG=0,FGG=1;//公共课，非公共课
public static Short CJYS=0,CJYX=1;//处级以上，处级以下
/**
 * 学院kdid
 */
private Long kdId;  
Teacher teacher;
WkTUser user;


public Short getZtDs() {
	return ztDs;
}
public void setZtDs(Short ztDs) {
	this.ztDs = ztDs;
}
public Short getZtZc() {
	return ztZc;
}
public void setZtZc(Short ztZc) {
	this.ztZc = ztZc;
}
public Long getZtId() {
	return ztId;
}
public void setZtId(Long ztId) {
	this.ztId = ztId;
}
public Long getKuId() {
	return kuId;
}
public void setKuId(Long kuId) {
	this.kuId = kuId;
}
public Short getZtIfgg() {
	return ztIfgg;
}
public void setZtIfgg(Short ztIfgg) {
	this.ztIfgg = ztIfgg;
}
public Short getZtIfcj() {
	return ztIfcj;
}
public void setZtIfcj(Short ztIfcj) {
	this.ztIfcj = ztIfcj;
}
public Integer getZtYear() {
	return ztYear;
}
public void setZtYear(Integer ztYear) {
	this.ztYear = ztYear;
}
public Float getZtSum() {
	return ztSum;
}
public void setZtSum(Float ztSum) {
	this.ztSum = ztSum;
}
public Zbteacher(Long ztId, Long kuId, Short ztIfgg, Short ztIfcj,
		Integer ztYear, Float ztSum,Long kdId,Short ztZc,Short ztDs) {
	super();
	this.ztId = ztId;
	this.kuId = kuId;
	this.ztIfgg = ztIfgg;
	this.ztIfcj = ztIfcj;
	this.ztYear = ztYear;
	this.ztSum = ztSum;
	this.kdId = kdId;
	this.ztZc = ztZc;
	this.ztDs = ztDs;
}

 
public Long getKdId() {
	return kdId;
}
public void setKdId(Long kdId) {
	this.kdId = kdId;
}
public Zbteacher() {
	super();
}
public Teacher getTeacher() {
	if(teacher==null){
		TeacherService teacherService =(TeacherService)BeanFactory.getBean("teacherService");
		teacher=teacherService.findBtKuid(kuId);
	}
	return teacher;
}
public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
}
public WkTUser getUser() {
	if(user==null){
		UserService userService=(UserService)BeanFactory.getBean("userService");
		user=(WkTUser)userService.get(WkTUser.class, kuId);
	}
	return user;
}
public void setUser(WkTUser user) {
	this.user = user;
}	
	
}
