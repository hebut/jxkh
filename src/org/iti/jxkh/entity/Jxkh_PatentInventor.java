package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Jxkh_PatentInventor implements java.io.Serializable {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -5432772423647086045L;

	private Long id;// 序号
	private String name;// 发明人姓名
	private String personId;// 发明人编号
	private Short type;// 发明人类型
	private String dept;// 所属部门
	private Integer rank;// 发明人排名
	private Jxkh_Patent patent;// 所属知识产权
	private Float score;// 本人得分
	private Float per;// 所占比例
	private String assignDep;// 本人绩分指定给的部门

	public Jxkh_PatentInventor() {
		super();
	}

	public Jxkh_PatentInventor(Long id, String name, String personId,
			Short type, String dept, Integer rank, Jxkh_Patent patent,
			Float score, Float per, String assignDep) {
		super();
		this.id = id;
		this.name = name;
		this.personId = personId;
		this.type = type;
		this.dept = dept;
		this.rank = rank;
		this.patent = patent;
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

	@Column(length = 50)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(length = 50)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATENT", nullable = true, insertable = true, updatable = true)
	public Jxkh_Patent getPatent() {
		return patent;
	}

	public void setPatent(Jxkh_Patent patent) {
		this.patent = patent;
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
		Jxkh_PatentInventor other = (Jxkh_PatentInventor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
