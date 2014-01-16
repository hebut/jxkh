package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iti.common.util.EntityUtil;

@Entity
@Table(name = "Jxkh_AwardDept")
public class Jxkh_AwardDept implements java.io.Serializable {

	private static final long serialVersionUID = -2618733894962643952L;

	private Long id;

	private String name;// 部门名称

	private Integer rank;// 部门排名

	private String deptId;// 部门编号

	private Jxkh_Award award;// 所属奖励
	private Float score;// 部门绩分

	/** default constructor */
	public Jxkh_AwardDept() {

	}

	public Jxkh_AwardDept(Long id, String name, Integer rank, String deptId,
			Jxkh_Award award, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.deptId = deptId;
		this.award = award;
		this.score = score;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 50)
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(length = 20)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AWARD", nullable = true, insertable = true, updatable = true)
	public Jxkh_Award getAward() {
		return award;
	}

	public void setAward(Jxkh_Award award) {
		this.award = award;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_AwardDept other = (Jxkh_AwardDept) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_AwardDept.class, this.id);
	}

}
