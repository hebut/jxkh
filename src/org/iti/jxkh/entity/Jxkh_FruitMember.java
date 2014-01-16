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
@Table(name = "Jxkh_FruitMember")
public class Jxkh_FruitMember implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 367794468023475700L;

	private Long id;

	private String name;// 成员姓名

	private Short type;// 成员类型

	private Integer rank;// 成员排名

	private String dept;// 成员所属部门

	private String personId;// 成员编号

	private Jxkh_Fruit fruit;// 所属成果
	private Float score;// 本人得分
	private Float per;// 所占比例
	private String assignDep;// 本人绩分指定给的部门名称

	/* 成员类型：0 校内，1 校外 */
	public static final Short IN = 0, OUT = 1;

	/** default constructor */
	public Jxkh_FruitMember() {

	}

	public Jxkh_FruitMember(Long id, String name, Short type, Integer rank,
			String dept, String personId, Jxkh_Fruit fruit, Float score,
			Float per, String assignDep) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.rank = rank;
		this.dept = dept;
		this.personId = personId;
		this.fruit = fruit;
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

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(length = 50)
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(length = 100)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(length = 20)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FRUIT", nullable = true, insertable = true, updatable = true)
	public Jxkh_Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Jxkh_Fruit fruit) {
		this.fruit = fruit;
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
	public String toString() {
		return "Jxkh_FruitMember [id=" + id + ", name=" + name + ", type="
				+ type + ", rank=" + rank + ", dept=" + dept + ", personId="
				+ personId + ", fruit=" + fruit + ", score=" + score + ", per="
				+ per + ", assignDep=" + assignDep + "]";
	}
	
}
