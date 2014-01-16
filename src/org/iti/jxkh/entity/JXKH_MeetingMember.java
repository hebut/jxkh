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
@Table(name = "JXKH_MeetingMember")
public class JXKH_MeetingMember implements java.io.Serializable {

	/**
	 * 2012��2��29��
	 */
	private static final long serialVersionUID = 4899121691428467879L;
	private Long id;// ���
	private String name;// ��Ա����
	private String type;// �������ͣ�0�ڲ���1�ⲿ��
	private String dept;// ��������
	private String personId;// ��ԱID
	private JXKH_MEETING meetingName;// ��������
	private String rank;// ��Ա����
	private Float score;// ���˵÷�
	private Float per;// ��ռ����
	private String assignDep;// ���˼���ָ�����Ĳ���

	public JXKH_MeetingMember() {
		super();
	}

	public JXKH_MeetingMember(Long id, String name, String type, String dept,
			String personId, JXKH_MEETING meetingName, String rank,
			Float score, Float per, String assignDep) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.dept = dept;
		this.personId = personId;
		this.meetingName = meetingName;
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
	public JXKH_MEETING getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(JXKH_MEETING meetingName) {
		this.meetingName = meetingName;
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
}
