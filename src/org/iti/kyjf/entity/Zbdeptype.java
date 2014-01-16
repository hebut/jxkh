package org.iti.kyjf.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class Zbdeptype implements java.io.Serializable {
	private Long zdId;
	private Integer Zxyear;
	private Long  kdId;
	private	Short zdTsxk;
	private	Short zdZdjs;
	private	Short zdBsd;
	private	Short zdZdxk;
	private	Short zdZdsys;
	private WkTDept dept;
	public static Short TSXK_YES=1,TSXK_NO=0;
	public static Short ZDJS_YES=1,ZDJS_NO=0;
	public static Short BSD_YES=1,BSD_NO=0;
	public static Short ZDXK_YES=1,ZDXK_NO=0;
	public static Short ZDSYS_YES=1,ZDSYS_NO=0;
	
	public WkTDept getDept() {
		if(dept==null){
			DepartmentService departmentService=(DepartmentService)BeanFactory.getBean("departmentService");
			WkTDept de=(WkTDept)departmentService.get(WkTDept.class, kdId);
			dept=de;
		}
		return dept;
	}
	public void setDept(WkTDept dept) {
		this.dept = dept;
	}
	public Long getZdId() {
		return zdId;
	}
	public void setZdId(Long zdId) {
		this.zdId = zdId;
	}
	public Integer getZxyear() {
		return Zxyear;
	}
	public void setZxyear(Integer zxyear) {
		Zxyear = zxyear;
	}
	public Long getKdId() {
		return kdId;
	}
	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}
	public Short getZdTsxk() {
		return zdTsxk;
	}
	public void setZdTsxk(Short zdTsxk) {
		this.zdTsxk = zdTsxk;
	}
	public Short getZdZdjs() {
		return zdZdjs;
	}
	public void setZdZdjs(Short zdZdjs) {
		this.zdZdjs = zdZdjs;
	}
	public Short getZdBsd() {
		return zdBsd;
	}
	public void setZdBsd(Short zdBsd) {
		this.zdBsd = zdBsd;
	}
	public Short getZdZdxk() {
		return zdZdxk;
	}
	public void setZdZdxk(Short zdZdxk) {
		this.zdZdxk = zdZdxk;
	}
	public Short getZdZdsys() {
		return zdZdsys;
	}
	public void setZdZdsys(Short zdZdsys) {
		this.zdZdsys = zdZdsys;
	}
	public Zbdeptype(Long zdId, Integer zxyear, Long kdId, Short zdTsxk,
			Short zdZdjs, Short zdBsd, Short zdZdxk, Short zdZdsys) {
		super();
		this.zdId = zdId;
		Zxyear = zxyear;
		this.kdId = kdId;
		this.zdTsxk = zdTsxk;
		this.zdZdjs = zdZdjs;
		this.zdBsd = zdBsd;
		this.zdZdxk = zdZdxk;
		this.zdZdsys = zdZdsys;
	}
	public Zbdeptype() {
		super();
	}
	
}
