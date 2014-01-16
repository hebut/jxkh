package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JXKH_AuditConfig implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long acId;
	private String acYear;//年度
	private Float acBFloat;//上浮一档比例
	private String acMWeight;//院长-副院长-部门负责人-员工所占权重
	private Float acWage;
	private Float acBase; // 业务部门基数
	private Float acBase2; // 管理部门基数
	private Float acMoney; // 业务部门钱数
	private Float acMoney2; // 管理部门钱数
	private Integer third;//三档的部门数
	private Integer second;//二档的部门数
	private Integer first;//一档的部门数

	public JXKH_AuditConfig() {
	}

	public JXKH_AuditConfig(Long acId) {
		this.acId = acId;
	}

	public JXKH_AuditConfig(Long acId, String acYear, Float acBFloat,
			String acMWeight, Float acWage, Float acBase, Float acBase2,
			Float acMoney, Float acMoney2, Integer third, Integer second,
			Integer first) {
		super();
		this.acId = acId;
		this.acYear = acYear;
		this.acBFloat = acBFloat;
		this.acMWeight = acMWeight;
		this.acWage = acWage;
		this.acBase = acBase;
		this.acBase2 = acBase2;
		this.acMoney = acMoney;
		this.acMoney2 = acMoney2;
		this.third = third;
		this.second = second;
		this.first = first;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getAcId() {
		return acId;
	}

	public void setAcId(Long acId) {
		this.acId = acId;
	}

	@Column
	public String getAcYear() {
		return acYear;
	}

	public void setAcYear(String acYear) {
		this.acYear = acYear;
	}

	@Column
	public Float getAcBFloat() {
		return acBFloat;
	}

	public void setAcBFloat(Float acBFloat) {
		this.acBFloat = acBFloat;
	}

	@Column
	public String getAcMWeight() {
		return acMWeight;
	}

	public void setAcMWeight(String acMWeight) {
		this.acMWeight = acMWeight;
	}

	@Column
	public Float getAcWage() {
		return acWage;
	}

	public void setAcWage(Float acWage) {
		this.acWage = acWage;
	}

	@Column
	public Float getAcBase() {
		return acBase;
	}

	public void setAcBase(Float acBase) {
		this.acBase = acBase;
	}

	@Column
	public Float getAcBase2() {
		return acBase2;
	}

	public void setAcBase2(Float acBase2) {
		this.acBase2 = acBase2;
	}

	@Column
	public Float getAcMoney() {
		return acMoney;
	}

	public void setAcMoney(Float acMoney) {
		this.acMoney = acMoney;
	}

	@Column
	public Float getAcMoney2() {
		return acMoney2;
	}

	public void setAcMoney2(Float acMoney2) {
		this.acMoney2 = acMoney2;
	}
	@Column
	public Integer getThird() {
		return third;
	}

	public void setThird(Integer third) {
		this.third = third;
	}
	@Column
	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}
	@Column
	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}	
}