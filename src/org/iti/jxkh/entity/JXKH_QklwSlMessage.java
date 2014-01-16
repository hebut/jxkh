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
@Table(name = "JXKH_QklwSlMessage")
public class JXKH_QklwSlMessage implements java.io.Serializable {

	/**
	 * CXX
	 */
	private static final long serialVersionUID = 3884631615091125883L;

	private Long id;// 序号
	private String lwType;// Jxkh_BusinessIndicator收录类别，默认为0（0请选择，1-SCI收录，2-EI收录，3-ISTP收录）
	private String lwTime;// 收录时间
	private String jxYear;// 绩分年度
	private JXKH_QKLW meeting;// 所属期刊

	public JXKH_QklwSlMessage() {
		super();
	}

	public JXKH_QklwSlMessage(Long id, String lwType, String lwTime,
			String jxYear, JXKH_QKLW meeting) {
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
	public JXKH_QKLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_QKLW meeting) {
		this.meeting = meeting;
	}
}
