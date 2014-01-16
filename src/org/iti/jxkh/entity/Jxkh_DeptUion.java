package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Jxkh_DeptUion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long jdId;
	private int sum;
	private String holdDept;
	private String AttenDept;
	private String rate;
	
	public Jxkh_DeptUion() {
		
	}
	
	
	public Jxkh_DeptUion(Long jdId, String holdDept, String attenDept,
			String rate) {
		super();
		this.jdId = jdId;
		this.holdDept = holdDept;
		AttenDept = attenDept;
		this.rate = rate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getJdId() {
		return jdId;
	}


	public void setJdId(Long jdId) {
		this.jdId = jdId;
	}
	
	@Column(length=20)
	public int getSum() {
		return sum;
	}


	public void setSum(int sum) {
		this.sum = sum;
	}


	@Column(length=30)
	public String getHoldDept() {
		return holdDept;
	}


	public void setHoldDept(String holdDept) {
		this.holdDept = holdDept;
	}

	@Column(length=30)
	public String getAttenDept() {
		return AttenDept;
	}


	public void setAttenDept(String attenDept) {
		AttenDept = attenDept;
	}

	@Column(length=80)
	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
	
	

}
