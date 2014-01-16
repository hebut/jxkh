package org.iti.xypt.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class XyClass implements java.io.Serializable{
 
	private static final long serialVersionUID = -4521406234177057239L;
	private Long clId;
	private String clName;
	private String clSname;
	private Long kdId;
	private Integer clGrade;
	/**
	 * À˘ Ùµ•Œª
	 */
	private WkTDept dept;
	
	public String getXiDept(){
		if(getDept().getKdLevel().intValue()==WkTDept.GRADE_XI.intValue()){
			return getDept().getKdName();
		}
		return "";
	}
	
	public String getYuanDept(){
		if(getDept().getKdLevel().intValue()==WkTDept.GRADE_XI.intValue()){
			return getDept().getPdept().getKdName();
		}else if(getDept().getKdLevel().intValue()==WkTDept.GRADE_YUAN.intValue()){
			return getDept().getKdName();
		}
		return "";
	}
	
	public WkTDept getDept() {
		if(dept==null){
			DepartmentService departmentService=(DepartmentService)BeanFactory.getBean("departmentService");
			this.dept=(WkTDept) departmentService.get(WkTDept.class, kdId);
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	public XyClass() {
		 
	}
	
	public XyClass(Long clId, String clName, String clSname, Long kdId,
			Integer clGrade) {
		super();
		this.clId = clId;
		this.clName = clName;
		this.clSname = clSname;
		this.kdId = kdId;
		this.clGrade = clGrade;
	}

	public Long getClId() {
		return clId;
	}
	public void setClId(Long clId) {
		this.clId = clId;
	}
	public String getClName() {
		return clName;
	}
	public void setClName(String clName) {
		this.clName = clName;
	}
	public String getClSname() {
		return clSname;
	}
	public void setClSname(String clSname) {
		this.clSname = clSname;
	}
	public Long getKdId() {
		return kdId;
	}
	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}
	public Integer getClGrade() {
		return clGrade;
	}
	public void setClGrade(Integer clGrade) {
		this.clGrade = clGrade;
	}
	
}
