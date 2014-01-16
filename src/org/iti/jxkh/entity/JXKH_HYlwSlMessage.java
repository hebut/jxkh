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
@Table(name = "JXKH_HYlwSlMessage")
public class JXKH_HYlwSlMessage implements java.io.Serializable {

	/**
	 * CXX
	 */
	private static final long serialVersionUID = -1199249840131551532L;

	private Long id;// ���
	private String lwType;// Jxkh_BusinessIndicator��¼���Ĭ��Ϊ0��0��ѡ��1-SCI��¼��2-EI��¼��3-ISTP��¼��
	private String lwTime;// ��¼ʱ��
	private String jxYear;// �������
	private JXKH_HYLW meeting;// �����ڿ�

	public JXKH_HYlwSlMessage() {
		super();
	}

	public JXKH_HYlwSlMessage(Long id, String lwType, String lwTime,
			String jxYear, JXKH_HYLW meeting) {
		super();
		this.id = id;
		this.lwType = lwType;
		this.lwTime = lwTime;
		this.jxYear = jxYear;
		this.meeting = meeting;
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
	public String getLwType() {
		return lwType;
	}

	public void setLwType(String lwType) {
		this.lwType = lwType;
	}

	@Column(length = 50)
	public String getLwTime() {
		return lwTime;
	}

	public void setLwTime(String lwTime) {
		this.lwTime = lwTime;
	}

	@Column(length = 8)
	public String getJxYear() {
		return jxYear;
	}

	public void setJxYear(String jxYear) {
		this.jxYear = jxYear;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lwId", insertable = true, updatable = true)
	public JXKH_HYLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_HYLW meeting) {
		this.meeting = meeting;
	}

}
