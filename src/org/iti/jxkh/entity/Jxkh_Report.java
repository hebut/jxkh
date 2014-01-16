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

@Entity
@Table(name = "Jxkh_Report")
public class Jxkh_Report implements java.io.Serializable {

	private static final long serialVersionUID = -8852098533157214212L;

	private Long id;

	private String name;// 报告名称

	private Float score;// 业务分值

	private Short coState;// 是否与外单位合作

	private String recordCode;// 档案编号

	private String type;// 报告类型

	private Jxkh_BusinessIndicator leader;// 批示领导等级id

	private String date;// 完成时间

	private String filed;// 科学领域

	private String coCompany;// 合作单位

	private String submitName;// 提交人姓名

	private String submitId;// 提交人编号

	private Short state;// 审核状态
	private String tempState;// 审核临时状态位（0未审核，1通过，2不通过）

	private Short firstSign;// 是否第一署名

	private String bAdvice;// 业务办审核意见
	private String dep1Reason;// 主部门反馈意见
	private String dep2Reason;
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;

	private String jxYear;// 绩分年度
	private String submitTime;// 提交时间

	private List<Jxkh_ReportMember> reportMember;

	private List<Jxkh_ReportDept> reprotDept;

	private Set<Jxkh_ReportFile> reportFile;

	// /* 审核状态：0 待审核，1 部门审核通过， 2 部门审核不通过，3 业务办审核通过，4 业务办审核不通过 */
	// public static final Short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2, BUSINESS_PASS = 3, BUSINESS_NOT_PASS = 4;
	/**
	 * 审核状态：0 待审核，1 部门审核通过，2主部门审核通过，3 部门审核不通过，4 业务办审核通过，5 业务办审核不通过，6归档，7业务办暂缓通过；
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/* 是否与外单位合作：0 否，1 是 */
	public static final Short NO = 0, YES = 1;

	/** default constructor */
	public Jxkh_Report() {

	}

	public Jxkh_Report(Long id, String name, Float score, Short coState,
			String recordCode, String type, Jxkh_BusinessIndicator leader,
			String date, String filed, String coCompany, String submitName,
			String submitId, Short state, String tempState, Short firstSign,
			String bAdvice, String dep1Reason, String dep2Reason,
			String dep3Reason, String dep4Reason, String dep5Reason,
			String dep6Reason, String dep7Reason, String jxYear,
			String submitTime, List<Jxkh_ReportMember> reportMember,
			List<Jxkh_ReportDept> reprotDept, Set<Jxkh_ReportFile> reportFile) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.coState = coState;
		this.recordCode = recordCode;
		this.type = type;
		this.leader = leader;
		this.date = date;
		this.filed = filed;
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
		this.reportMember = reportMember;
		this.reprotDept = reprotDept;
		this.reportFile = reportFile;
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

	@Column(length = 40)
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column
	public Short getCoState() {
		return coState;
	}

	public void setCoState(Short coState) {
		this.coState = coState;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "leader", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getLeader() {
		return leader;
	}

	public void setLeader(Jxkh_BusinessIndicator leader) {
		this.leader = leader;
	}

	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(length = 20)
	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
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

	@Column
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Column(length = 20)
	public String getTempState() {
		return tempState;
	}

	public void setTempState(String tempState) {
		this.tempState = tempState;
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

	@Column(length = 200)
	public String getbAdvice() {
		return bAdvice;
	}

	public void setbAdvice(String bAdvice) {
		this.bAdvice = bAdvice;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "report")
	public List<Jxkh_ReportMember> getReportMember() {
		return reportMember;
	}

	public void setReportMember(List<Jxkh_ReportMember> reportMember) {
		this.reportMember = reportMember;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "report")
	public List<Jxkh_ReportDept> getReprotDept() {
		return reprotDept;
	}

	public void setReprotDept(List<Jxkh_ReportDept> reprotDept) {
		this.reprotDept = reprotDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "report")
	public Set<Jxkh_ReportFile> getReportFile() {
		return reportFile;
	}

	public void setReportFile(Set<Jxkh_ReportFile> reportFile) {
		this.reportFile = reportFile;
	}

	@Column
	public Short getFirstSign() {
		return firstSign;
	}

	public void setFirstSign(Short firstSign) {
		this.firstSign = firstSign;
	}

}
