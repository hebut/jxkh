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
@Table(name = "JXKH_QKLWDept")
public class JXKH_QKLWDept implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 6463555180235471050L;

	private Long id;// 序号
	private String name;// 部门名称
	private String depId;// 部门编号
	private Integer rank;// 部门排名
	private JXKH_QKLW meeting;// 所属期刊论文
	private Float score;// 部门绩分

	public JXKH_QKLWDept() {
		super();
	}

	public JXKH_QKLWDept(Long id, String name, String depId, Integer rank,
			JXKH_QKLW meeting, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.depId = depId;
		this.rank = rank;
		this.meeting = meeting;
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

	@Column(length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 80)
	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	@Column(length = 10)
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lwId", insertable = true, updatable = true)
	public JXKH_QKLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_QKLW meeting) {
		this.meeting = meeting;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
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
		JXKH_QKLWDept other = (JXKH_QKLWDept) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
