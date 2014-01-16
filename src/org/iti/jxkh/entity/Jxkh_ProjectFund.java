package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Jxkh_ProjectFund implements java.io.Serializable {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -2971013792187051230L;

	private Long id;// 序号
	private Float money;// 金额
	private Short type;// 类型
	private String date;// 日期
	private String transactor;// 经办人
	private String remark;// 备注
	private Short sort;// 经费类型
	private String jxYear;// 绩分年度
	private Jxkh_Project project;// 所属项目
	@Column
	private Long deptNum;

	/* 类型：0 否，1 是 */
//	public static final short IN = 0, WG = 1, WJ = 2, OUT = 3;
	public static final short IN = 0, OUT = 3;
	/* 经费类型：0专项经费，1 自筹经费 */
	public static final short ZXF = 0, ZCF = 1;

	public Jxkh_ProjectFund() {
		super();
	}	
	public Jxkh_ProjectFund(Long id, Float money, Short type, String date, String transactor, String remark, Short sort, String jxYear, Jxkh_Project project, Long deptNum) {
		super();
		this.id = id;
		this.money = money;
		this.type = type;
		this.date = date;
		this.transactor = transactor;
		this.remark = remark;
		this.sort = sort;
		this.jxYear = jxYear;
		this.project = project;
		this.deptNum = deptNum;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	@Column
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(length = 50)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(length = 50)
	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	@Column(length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	@ManyToOne
	@JoinColumn(name = "PROJECT", nullable = true, insertable = true, updatable = true)
	public Jxkh_Project getProject() {
		return project;
	}

	@Column(length = 8)
	public String getjxYear() {
		return jxYear;
	}

	public void setjxYear(String jxYear) {
		this.jxYear = jxYear;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
	}
	
	public Long getDeptNum() {
		return deptNum;
	}
	
	public void setDeptNum(Long deptNum) {
		this.deptNum = deptNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_ProjectFund other = (Jxkh_ProjectFund) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
