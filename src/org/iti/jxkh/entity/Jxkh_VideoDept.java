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
@Table(name = "Jxkh_VideoDept")
public class Jxkh_VideoDept implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4216595331729113807L;

	private Long id;

	private String name;// 部门名称

	private Integer rank;// 部门排名

	private String deptId;// 部门编号

	private Jxkh_Video video;// 所属奖励
	private Float score;// 部门绩分

	/** default constructor */
	public Jxkh_VideoDept() {

	}

	public Jxkh_VideoDept(Long id, String name, Integer rank, String deptId,
			Jxkh_Video video, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.deptId = deptId;
		this.video = video;
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
	@JoinColumn(name = "VIDEO", nullable = true, insertable = true, updatable = true)
	public Jxkh_Video getVideo() {
		return video;
	}

	public void setVideo(Jxkh_Video video) {
		this.video = video;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
