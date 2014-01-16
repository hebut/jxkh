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

@Entity
@Table(name = "JXKH_QKLWMember")
public class JXKH_QKLWMember implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = -4219609042435900367L;

	private Long id;// 序号
	private String name;// 人员姓名
	private String type;// 部门类型（0内部，1外部）
	private String dept;// 所属部门
	private String personId;// 人员ID
	private JXKH_QKLW QKLWName;// 所属期刊论文
	private String rank;// 人员排名
	private Float score;// 本人得分
	private Float per;// 所占比例
	private String assignDep;// 本人绩分指定给的部门

	public JXKH_QKLWMember() {
		super();
	}

	public JXKH_QKLWMember(Long id, String name, String type, String dept,
			String personId, JXKH_QKLW qKLWName, String rank, Float score,
			Float per, String assignDep) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.dept = dept;
		this.personId = personId;
		QKLWName = qKLWName;
		this.rank = rank;
		this.score = score;
		this.per = per;
		this.assignDep = assignDep;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(length = 100)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(length = 100)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lwId", insertable = true, updatable = true)
	public JXKH_QKLW getQKLWName() {
		return QKLWName;
	}

	public void setQKLWName(JXKH_QKLW qKLWName) {
		QKLWName = qKLWName;
	}

	@Column(length = 50)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(length = 10)
	public Float getPer() {
		return per;
	}

	public void setPer(Float per) {
		this.per = per;
	}

	@Column(length = 100)
	public String getAssignDep() {
		return assignDep;
	}

	public void setAssignDep(String assignDep) {
		this.assignDep = assignDep;
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
		JXKH_QKLWMember other = (JXKH_QKLWMember) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JXKH_QKLWMember [id=" + id + ", name=" + name + ", type="
				+ type + ", dept=" + dept + ", personId=" + personId
				+ ", QKLWName=" + QKLWName + ", rank=" + rank + "]";
	}

}
