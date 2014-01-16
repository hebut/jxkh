package org.iti.jxkh.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class JXKH_VoteConfig implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long vcId;
	private String vcYear;
	private String vcStart;
	private String vcEnd;
	private String vcName;
	private String vcVoter;
	private String vcWeight;
	private List<JXKH_VoteResult> vcObject;

	public JXKH_VoteConfig() {
	}

	public JXKH_VoteConfig(Long vcId) {
		this.vcId = vcId;
	}

	public JXKH_VoteConfig(Long vcId, String vcYear, String vcStart, String vcEnd, String vcName, String vcVoter,
			String vcWeight, List<JXKH_VoteResult> vcObject) {
		super();
		this.vcId = vcId;
		this.vcYear = vcYear;
		this.vcStart = vcStart;
		this.vcEnd = vcEnd;
		this.vcName = vcName;
		this.vcVoter = vcVoter; // 投票者
		this.vcWeight = vcWeight;
		this.vcObject = vcObject; // 投票对象
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getVcId() {
		return vcId;
	}

	public void setVcId(Long vcId) {
		this.vcId = vcId;
	}

	@Column
	public String getVcYear() {
		return vcYear;
	}

	public void setVcYear(String vcYear) {
		this.vcYear = vcYear;
	}

	@Column
	public String getVcStart() {
		return vcStart;
	}

	public void setVcStart(String vcStart) {
		this.vcStart = vcStart;
	}

	@Column
	public String getVcEnd() {
		return vcEnd;
	}

	public void setVcEnd(String vcEnd) {
		this.vcEnd = vcEnd;
	}

	@Column
	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "config")
	public List<JXKH_VoteResult> getVcObject() {
		return vcObject;
	}

	public void setVcObject(List<JXKH_VoteResult> vcObject) {
		this.vcObject = vcObject;
	}

	@Column
	public String getVcVoter() {
		return vcVoter;
	}

	public void setVcVoter(String vcVoter) {
		this.vcVoter = vcVoter;
	}

	@Column
	public String getVcWeight() {
		return vcWeight;
	}

	public void setVcWeight(String vcWeight) {
		this.vcWeight = vcWeight;
	}
}