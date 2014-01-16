package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JXKH_MultiDept implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long mdId;
	private Long prId;
	private String depts;
	private String rate;
	private Short type;
	//会议论文0，期刊论文1，著作2，奖励3，影视专题片4，科研项目5，专利6，成果7，会议8，报告9
	public static final short HYLW = 0, QKLW = 1, ZZ = 2, JL = 3, SP = 4, XM = 5, ZL = 6, CG = 7, HY = 8, BG = 9;

	public JXKH_MultiDept() {
	}

	public JXKH_MultiDept(Long mdId) {
		this.mdId = mdId;
	}

	public JXKH_MultiDept(Long mdId, Long prId, String depts, String rate, Short type) {
		super();
		this.mdId = mdId;
		this.prId = prId;
		this.depts = depts;
		this.rate = rate;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getMdId() {
		return mdId;
	}

	public void setMdId(Long mdId) {
		this.mdId = mdId;
	}

	@Column
	public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	@Column
	public String getDepts() {
		return depts;
	}

	public void setDepts(String depts) {
		this.depts = depts;
	}

	@Column
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
}