package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JXKH_PointNumber implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long pnId;
	private Long kdId;
	private String pnYear;
	private Integer pnNumber;

	public JXKH_PointNumber() {
	}

	public JXKH_PointNumber(Long pnId) {
		this.pnId = pnId;
	}

	public JXKH_PointNumber(Long pnId, Long kdId, String pnYear, Integer pnNumber) {
		super();
		this.pnId = pnId;
		this.kdId = kdId;
		this.pnYear = pnYear;
		this.pnNumber = pnNumber;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getPnId() {
		return pnId;
	}

	public void setPnId(Long pnId) {
		this.pnId = pnId;
	}

	@Column
	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	@Column
	public String getPnYear() {
		return pnYear;
	}

	public void setPnYear(String pnYear) {
		this.pnYear = pnYear;
	}

	@Column
	public Integer getPnNumber() {
		return pnNumber;
	}

	public void setPnNumber(Integer pnNumber) {
		this.pnNumber = pnNumber;
	}

}