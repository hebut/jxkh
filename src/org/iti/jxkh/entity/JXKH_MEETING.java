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
@Table(name = "JXKH_MEETING")
public class JXKH_MEETING implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 3147792742062726181L;

	private Long mtId;// 学术会议ID
	private String mtName;// 会议名称
	private String recordCode;// 档案号
	private Jxkh_BusinessIndicator mtDegree;// 会议级别，默认为0（0请选择，1国家级学会学术年会，2省级学会学术年会，3省内专业学术研讨会）
	private Jxkh_BusinessIndicator mtType;// 举办类型，默认为0（0请选择，1主办，2承办，3协办）
	private Short ifUnion;// 是否与外单位合作，(0-否，1-是)
	private Short ifSign;// 是否是第一署名
	private List<JXKH_MeetingDept> mtDep;// 院内部门
	private String mtCoDep;// 合作单位
	private String mtWriter;// 信息填写人
	private String writerId;// 信息填写人Id
	private String mtPerson;// 负责人
	private String mtZDep;// 主办单位
	private String mtCDep;// 承办单位
	private String mtXDep;// 协办单位
	private String mtTime;// 会议时间
	private String mtAddress;// 会议地点
	private String mtTheme;// 会议主题
	private String mtScope;// 会议规模
	private Short mtState;// 审核状态
	private String tempState;// 审核临时状态位（0未审核，1通过，2不通过）
	private String ywReason;// 业务办反馈意见
	private String dep1Reason;// 主部门反馈意见
	private String dep2Reason;
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;
	private String jxYear;// 绩分年度
	private String submitTime;// 提交时间
	private Float score;
	private Set<JXKH_MeetingFile> files;// 会议文档
	private List<JXKH_MeetingMember> meetingAuthor;// 会议组织人员

	// /**
	// * 审核状态：0 待审核，1 部门审核通过， 2 部门审核不通过，3 业务办审核通过，4 业务办审核不通过，5归档，6主部门审核通过
	// */
	// public static final short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2,
	// BUSINESS_PASS = 3, BUSINESS_NOT_PASS = 4, SAVE_RECORD = 5;

	/**
	 * 审核状态：0提交待审核，9部门审核中,1 部门审核通过，2主部门审核通过，3 部门审核不通过，4 业务办审核通过，5 业务办审核不通过，6归档，7业务办暂缓通过，8填写中；
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7,WRITING=8,DEPT_AUDITING=9;

	/**
	 * 是否与外单位合作：0 否，1 是
	 */
	public static final short NO = 0, YES = 1;

	public JXKH_MEETING() {
		super();
	}

	public JXKH_MEETING(Long mtId, String mtName, String recordCode,
			Jxkh_BusinessIndicator mtDegree, Jxkh_BusinessIndicator mtType,
			Short ifUnion, Short ifSign, List<JXKH_MeetingDept> mtDep,
			String mtCoDep, String mtWriter, String writerId, String mtPerson,
			String mtZDep, String mtCDep, String mtXDep, String mtTime,
			String mtAddress, String mtTheme, String mtScope, Short mtState,
			String tempState, String ywReason, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime, Float score,
			Set<JXKH_MeetingFile> files, List<JXKH_MeetingMember> meetingAuthor) {
		super();
		this.mtId = mtId;
		this.mtName = mtName;
		this.recordCode = recordCode;
		this.mtDegree = mtDegree;
		this.mtType = mtType;
		this.ifUnion = ifUnion;
		this.ifSign = ifSign;
		this.mtDep = mtDep;
		this.mtCoDep = mtCoDep;
		this.mtWriter = mtWriter;
		this.writerId = writerId;
		this.mtPerson = mtPerson;
		this.mtZDep = mtZDep;
		this.mtCDep = mtCDep;
		this.mtXDep = mtXDep;
		this.mtTime = mtTime;
		this.mtAddress = mtAddress;
		this.mtTheme = mtTheme;
		this.mtScope = mtScope;
		this.mtState = mtState;
		this.tempState = tempState;
		this.ywReason = ywReason;
		this.dep1Reason = dep1Reason;
		this.dep2Reason = dep2Reason;
		this.dep3Reason = dep3Reason;
		this.dep4Reason = dep4Reason;
		this.dep5Reason = dep5Reason;
		this.dep6Reason = dep6Reason;
		this.dep7Reason = dep7Reason;
		this.jxYear = jxYear;
		this.submitTime = submitTime;
		this.score = score;
		this.files = files;
		this.meetingAuthor = meetingAuthor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getMtId() {
		return mtId;
	}

	public void setMtId(Long mtId) {
		this.mtId = mtId;
	}

	@Column(length = 400)
	public String getMtName() {
		return mtName;
	}

	public void setMtName(String mtName) {
		this.mtName = mtName;
	}

	@Column(length = 20)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "mtDegree", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getMtDegree() {
		return mtDegree;
	}

	public void setMtDegree(Jxkh_BusinessIndicator mtDegree) {
		this.mtDegree = mtDegree;
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

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "mtType", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getMtType() {
		return mtType;
	}

	public void setMtType(Jxkh_BusinessIndicator mtType) {
		this.mtType = mtType;
	}

	@Column(length = 10)
	public Short getIfUnion() {
		return ifUnion;
	}

	public void setIfUnion(Short ifUnion) {
		this.ifUnion = ifUnion;
	}

	@Column(length = 10)
	public Short getIfSign() {
		return ifSign;
	}

	public void setIfSign(Short ifSign) {
		this.ifSign = ifSign;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meeting")
	public List<JXKH_MeetingDept> getMtDep() {
		return mtDep;
	}

	public void setMtDep(List<JXKH_MeetingDept> mtDep) {
		this.mtDep = mtDep;
	}

	@Column(length = 200)
	public String getMtCoDep() {
		return mtCoDep;
	}

	public void setMtCoDep(String mtCoDep) {
		this.mtCoDep = mtCoDep;
	}

	@Column(length = 100)
	public String getMtWriter() {
		return mtWriter;
	}

	public void setMtWriter(String mtWriter) {
		this.mtWriter = mtWriter;
	}

	@Column(length = 20)
	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	@Column(length = 50)
	public String getMtPerson() {
		return mtPerson;
	}

	public void setMtPerson(String mtPerson) {
		this.mtPerson = mtPerson;
	}

	@Column(length = 100)
	public String getMtZDep() {
		return mtZDep;
	}

	public void setMtZDep(String mtZDep) {
		this.mtZDep = mtZDep;
	}

	@Column(length = 100)
	public String getMtCDep() {
		return mtCDep;
	}

	public void setMtCDep(String mtCDep) {
		this.mtCDep = mtCDep;
	}

	@Column(length = 100)
	public String getMtXDep() {
		return mtXDep;
	}

	public void setMtXDep(String mtXDep) {
		this.mtXDep = mtXDep;
	}

	@Column(length = 30)
	public String getMtTime() {
		return mtTime;
	}

	public void setMtTime(String mtTime) {
		this.mtTime = mtTime;
	}

	@Column
	public String getMtAddress() {
		return mtAddress;
	}

	public void setMtAddress(String mtAddress) {
		this.mtAddress = mtAddress;
	}

	@Column
	public String getMtTheme() {
		return mtTheme;
	}

	public void setMtTheme(String mtTheme) {
		this.mtTheme = mtTheme;
	}

	@Column
	public String getMtScope() {
		return mtScope;
	}

	public void setMtScope(String mtScope) {
		this.mtScope = mtScope;
	}

	@Column(length = 10)
	public Short getMtState() {
		return mtState;
	}

	public void setMtState(Short mtState) {
		this.mtState = mtState;
	}

	@Column(length = 20)
	public String getTempState() {
		return tempState;
	}

	public void setTempState(String tempState) {
		this.tempState = tempState;
	}

	@Column(length = 500)
	public String getYwReason() {
		return ywReason;
	}

	public void setYwReason(String ywReason) {
		this.ywReason = ywReason;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meeting")
	public Set<JXKH_MeetingFile> getFiles() {
		return files;
	}

	public void setFiles(Set<JXKH_MeetingFile> files) {
		this.files = files;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meetingName")
	public List<JXKH_MeetingMember> getMeetingAuthor() {
		return meetingAuthor;
	}

	public void setMeetingAuthor(List<JXKH_MeetingMember> meetingAuthor) {
		this.meetingAuthor = meetingAuthor;
	}

}
