package org.iti.jxkh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JXKH_JFRESULT")


public class JXKH_JFRESULT  implements java.io.Serializable{

	private static final long serialVersionUID = -1806546760925211468L;
	private Long id;
	private Long KdId;//业务部门ID 
	private String name;//业务部门名称
	private double lastYearJf;//上一年积分
	private double yearJf;//本年积分
	private double rjJf;//本年度人均积分
	private double zzlJf;//本年度增长率
	private double peoNum;//人数
	private double totalJf;//总积分
	private String year;//年份
	public JXKH_JFRESULT() {
		super();
	}

	public JXKH_JFRESULT(Long id, Long kdId, String name, double lastYearJf,
			double yearJf, double rjJf, double zzlJf, double peoNum,
			double totalJf, String year) {
		super();
		this.id = id;
		KdId = kdId;
		this.name = name;
		this.lastYearJf = lastYearJf;
		this.yearJf = yearJf;
		this.rjJf = rjJf;
		this.zzlJf = zzlJf;
		this.peoNum = peoNum;
		this.totalJf = totalJf;
		this.year = year;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getKdId() {
		return KdId;
	}
	public void setKdId(Long kdId) {
		KdId = kdId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLastYearJf() {
		return lastYearJf;
	}
	public void setLastYearJf(double lastYearJf) {
		this.lastYearJf = lastYearJf;
	}
	public double getYearJf() {
		return yearJf;
	}
	public void setYearJf(double yearJf) {
		this.yearJf = yearJf;
	}
	public double getPeoNum() {
		return peoNum;
	}
	public void setPeoNum(double peoNum) {
		this.peoNum = peoNum;
	}
	public double getTotalJf() {
		return totalJf;
	}
	public void setTotalJf(double totalJf) {
		this.totalJf = totalJf;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public double getRjJf() {
		return rjJf;
	}

	public void setRjJf(double rjJf) {
		this.rjJf = rjJf;
	}

	public double getZzlJf() {
		return zzlJf;
	}

	public void setZzlJf(double zzlJf) {
		this.zzlJf = zzlJf;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
