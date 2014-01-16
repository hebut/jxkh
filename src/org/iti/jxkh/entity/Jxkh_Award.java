package org.iti.jxkh.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.iti.common.util.EntityUtil;

@Entity
@Table(name = "Jxkh_Award")
public class Jxkh_Award implements java.io.Serializable {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 4075072707661279469L;

	private Long id;

	private String name;// 奖励名称

	private Short coState;// 是否与外单位合作

	private String date;// 获奖日期

	private Jxkh_BusinessIndicator rank;// 奖励等级id

	private String authorizeCompany;// 授奖单位

	private String registerCode;// 奖励证书编号

	private String recordCode;// 档案号

	private String coCompany;// 合作单位

	private String submitName;// 提交人姓名

	private String submitId;// 提交人编号

	private Short state;// 审核状态
	private String tempState;// 审核临时状态位（0未审核，1通过，2不通过）

	private Short firstSign;

	private String bAdvice;// 业务办审核意见
	private String dep1Reason;// 主部门反馈意见
	private String dep2Reason;
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;

	private String jxYear;//绩分年度
	private String submitTime;// 提交时间
	
	private List<Jxkh_AwardMember> awardMember;// 奖励成员

	private List<Jxkh_AwardDept> awardDept;// 奖励所属部门

	private Set<Jxkh_AwardFile> awardFile;// 附件

	private Float score;

	// /* 审核状态：0 待审核，1 部门审核通过， 2 部门审核不通过，3 业务办审核通过，4 业务办审核不通过 */
	// public static final Short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2,
	//	BUSINESS_PASS = 3, BUSINESS_NOT_PASS = 4, SAVE_RECORD = 5;
	/**
	 * 审核状态：0 待审核，1 部门审核通过，2主部门审核通过，3 部门审核不通过，4 业务办审核通过，5 业务办审核不通过，6归档，7业务办暂缓通过；
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;
	
	/* 是否与外单位合作：0 否，1 是 */
	public static final Short NO = 0, YES = 1;

	/** default constructor */
	public Jxkh_Award() {

	}

	public Jxkh_Award(Long id) {
		this.id = id;
	}

	public Jxkh_Award(Long id, String name, Short coState, String date,
			Jxkh_BusinessIndicator rank, String authorizeCompany,
			String registerCode, String recordCode, String coCompany,
			String submitName, String submitId, Short state, String tempState,
			Short firstSign, String bAdvice, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime,
			List<Jxkh_AwardMember> awardMember, List<Jxkh_AwardDept> awardDept,
			Set<Jxkh_AwardFile> awardFile, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.coState = coState;
		this.date = date;
		this.rank = rank;
		this.authorizeCompany = authorizeCompany;
		this.registerCode = registerCode;
		this.recordCode = recordCode;
		this.coCompany = coCompany;
		this.submitName = submitName;
		this.submitId = submitId;
		this.state = state;
		this.tempState = tempState;
		this.firstSign = firstSign;
		this.bAdvice = bAdvice;
		this.dep1Reason = dep1Reason;
		this.dep2Reason = dep2Reason;
		this.dep3Reason = dep3Reason;
		this.dep4Reason = dep4Reason;
		this.dep5Reason = dep5Reason;
		this.dep6Reason = dep6Reason;
		this.dep7Reason = dep7Reason;
		this.jxYear = jxYear;
		this.submitTime = submitTime;
		this.awardMember = awardMember;
		this.awardDept = awardDept;
		this.awardFile = awardFile;
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

	@Column(length = 400)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public Short getCoState() {
		return coState;
	}

	public void setCoState(Short coState) {
		this.coState = coState;
	}

	@Column(length = 20)
	public String getTempState() {
		return tempState;
	}

	public void setTempState(String tempState) {
		this.tempState = tempState;
	}

	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "rank_id")
	public Jxkh_BusinessIndicator getRank() {
		return rank;
	}

	public void setRank(Jxkh_BusinessIndicator rank) {
		this.rank = rank;
	}

	@Column(length = 50)
	public String getAuthorizeCompany() {
		return authorizeCompany;
	}

	public void setAuthorizeCompany(String authorizeCompany) {
		this.authorizeCompany = authorizeCompany;
	}

	@Column(length = 50)
	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(length = 50)
	public String getCoCompany() {
		return coCompany;
	}

	public void setCoCompany(String coCompany) {
		this.coCompany = coCompany;
	}

	@Column(length = 20)
	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}

	@Column(length = 20)
	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	@Column
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(length = 200)
	public String getbAdvice() {
		return bAdvice;
	}

	public void setbAdvice(String bAdvice) {
		this.bAdvice = bAdvice;
	}

	@Column(length = 500)
	public String getDep1Reason() {
		return dep1Reason;
	}

	public void setDep1Reason(String dep1Reason) {
		this.dep1Reason = dep1Reason;
	}

	@Column(length = 500)
	public String getDep2Reason() {
		return dep2Reason;
	}

	public void setDep2Reason(String dep2Reason) {
		this.dep2Reason = dep2Reason;
	}

	@Column(length = 500)
	public String getDep3Reason() {
		return dep3Reason;
	}

	public void setDep3Reason(String dep3Reason) {
		this.dep3Reason = dep3Reason;
	}

	@Column(length = 500)
	public String getDep4Reason() {
		return dep4Reason;
	}

	public void setDep4Reason(String dep4Reason) {
		this.dep4Reason = dep4Reason;
	}

	@Column(length = 500)
	public String getDep5Reason() {
		return dep5Reason;
	}

	public void setDep5Reason(String dep5Reason) {
		this.dep5Reason = dep5Reason;
	}

	@Column(length = 500)
	public String getDep6Reason() {
		return dep6Reason;
	}

	public void setDep6Reason(String dep6Reason) {
		this.dep6Reason = dep6Reason;
	}

	@Column(length = 500)
	public String getDep7Reason() {
		return dep7Reason;
	}

	public void setDep7Reason(String dep7Reason) {
		this.dep7Reason = dep7Reason;
	}

	@Column(length = 8)
	public String getjxYear() {
		return jxYear;
	}

	public void setjxYear(String jxYear) {
		this.jxYear = jxYear;
	}
	
	@Column(length = 50)
	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "award")
	public List<Jxkh_AwardMember> getAwardMember() {
		return awardMember;
	}

	public void setAwardMember(List<Jxkh_AwardMember> awardMember) {
		this.awardMember = awardMember;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "award")
	public List<Jxkh_AwardDept> getAwardDept() {
		return awardDept;
	}

	public void setAwardDept(List<Jxkh_AwardDept> awardDept) {
		this.awardDept = awardDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "award")
	public Set<Jxkh_AwardFile> getAwardFile() {
		return awardFile;
	}

	public void setAwardFile(Set<Jxkh_AwardFile> awardFile) {
		this.awardFile = awardFile;
	}

	@Column(length = 50)
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_Award other = (Jxkh_Award) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_Award.class, this.id);
	}

	@Column
	public Short getFirstSign() {
		return firstSign;
	}

	public void setFirstSign(Short firstSign) {
		this.firstSign = firstSign;
	}

}
