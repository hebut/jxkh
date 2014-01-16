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
@Table(name = "JXKH_QKLW")
public class JXKH_QKLW implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 6025853178958634262L;

	private Long lwId;// 论文ID
	private String lwName;// 论文题目
	private String recordCode;// 档案号
	private String lwWriter;// 信息填写人
	private String writerId;// 信息填写人Id
	private Short ifUnion;// 是否与外单位合作(0-否，1-是)
	private Short ifSign;// 是否是第一署名
	private String lwCoDep;// 合作单位
	private Jxkh_BusinessIndicator qkGrade;// 期刊级别，默认为0（期刊级别，默认为0（0请选择，1-中文核心期刊，2-普通期刊））
	// private Jxkh_BusinessIndicator lwType;//
	// 收录类别，默认为0（0请选择，1-SCI收录，2-EI收录，3-ISTP收录）
	private String lwRank;// 论文级别 (一级， 二级， 权威，CSSCI， 其他公开刊物)
	// private String lwTime;// 收录时间
	private String lwDate;// 发表时间
	private String kwName;// 发表刊物名称
	private String lwJuan;// 卷
	private String lwQC;// 发表刊物期次
	private String lwPages;// 起止页
	private String lwAuthor;// 通讯作者
	private Short lwState;// 审核状态
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
	private List<JXKH_QKLWDept> lwDep;// 院内部门
	private Set<JXKH_QKLWFile> files;// 相关文档
	private List<JXKH_QKLWMember> lwAll;// 全部作者
	private List<JXKH_QklwSlMessage> slMessage;// 收录信息
	private Long computeType;
	/**
	 * 审核状态：0 待审核，1 部门审核通过，2主部门审核通过，3 部门审核不通过，4 业务办审核通过，5 业务办审核不通过，6归档，7业务办暂缓通过；
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/**
	 * 是否与外单位合作：0 否，1 是
	 */
	public static final short NO = 0, YES = 1;

	public JXKH_QKLW() {
		super();
	}

	public JXKH_QKLW(Long lwId, String lwName, String recordCode,
			String lwWriter, String writerId, Short ifUnion, Short ifSign,
			String lwCoDep, Jxkh_BusinessIndicator qkGrade, String lwRank,
			String lwDate, String kwName, String lwJuan, String lwQC,
			String lwPages, String lwAuthor, Short lwState, String tempState,
			String ywReason, String dep1Reason, String dep2Reason,
			String dep3Reason, String dep4Reason, String dep5Reason,
			String dep6Reason, String dep7Reason, String jxYear,
			String submitTime, Float score, List<JXKH_QKLWDept> lwDep,
			Set<JXKH_QKLWFile> files, List<JXKH_QKLWMember> lwAll,
			List<JXKH_QklwSlMessage> slMessage, Long computeType) {
		super();
		this.lwId = lwId;
		this.lwName = lwName;
		this.recordCode = recordCode;
		this.lwWriter = lwWriter;
		this.writerId = writerId;
		this.ifUnion = ifUnion;
		this.ifSign = ifSign;
		this.lwCoDep = lwCoDep;
		this.qkGrade = qkGrade;
		this.lwRank = lwRank;
		this.lwDate = lwDate;
		this.kwName = kwName;
		this.lwJuan = lwJuan;
		this.lwQC = lwQC;
		this.lwPages = lwPages;
		this.lwAuthor = lwAuthor;
		this.lwState = lwState;
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
		this.lwDep = lwDep;
		this.files = files;
		this.lwAll = lwAll;
		this.slMessage = slMessage;
		this.computeType = computeType;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getLwId() {
		return lwId;
	}

	public void setLwId(Long lwId) {
		this.lwId = lwId;
	}

	@Column(length = 400)
	public String getLwName() {
		return lwName;
	}

	public void setLwName(String lwName) {
		this.lwName = lwName;
	}

	@Column(length = 100)
	public String getLwWriter() {
		return lwWriter;
	}

	public void setLwWriter(String lwWriter) {
		this.lwWriter = lwWriter;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(length = 20)
	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
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

	@Column(length = 200)
	public String getLwCoDep() {
		return lwCoDep;
	}

	public void setLwCoDep(String lwCoDep) {
		this.lwCoDep = lwCoDep;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "qkGrade", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getQkGrade() {
		return qkGrade;
	}

	public void setQkGrade(Jxkh_BusinessIndicator qkGrade) {
		this.qkGrade = qkGrade;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @ManyToOne
	// @JoinColumn(name = "lwType", nullable = true, insertable = true,
	// updatable = true)
	// public Jxkh_BusinessIndicator getLwType() {
	// return lwType;
	// }
	//
	// public void setLwType(Jxkh_BusinessIndicator lwType) {
	// this.lwType = lwType;
	// }

	@Column(length = 30)
	public String getLwRank() {
		return lwRank;
	}

	public void setLwRank(String lwRank) {
		this.lwRank = lwRank;
	}

	// @Column(length = 50)
	// public String getLwTime() {
	// return lwTime;
	// }
	//
	// public void setLwTime(String lwTime) {
	// this.lwTime = lwTime;
	// }

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

	@Column(length = 50)
	public String getLwDate() {
		return lwDate;
	}

	public void setLwDate(String lwDate) {
		this.lwDate = lwDate;
	}

	@Column(length = 100)
	public String getKwName() {
		return kwName;
	}

	public void setKwName(String kwName) {
		this.kwName = kwName;
	}

	@Column(length = 80)
	public String getLwJuan() {
		return lwJuan;
	}

	public void setLwJuan(String lwJuan) {
		this.lwJuan = lwJuan;
	}

	@Column(length = 80)
	public String getLwQC() {
		return lwQC;
	}

	public void setLwQC(String lwQC) {
		this.lwQC = lwQC;
	}

	@Column(length = 80)
	public String getLwPages() {
		return lwPages;
	}

	public void setLwPages(String lwPages) {
		this.lwPages = lwPages;
	}

	@Column(length = 100)
	public String getLwAuthor() {
		return lwAuthor;
	}

	public void setLwAuthor(String lwAuthor) {
		this.lwAuthor = lwAuthor;
	}

	@Column(length = 10)
	public Short getLwState() {
		return lwState;
	}

	public void setLwState(Short lwState) {
		this.lwState = lwState;
	}

	@Column(length = 20)
	public String getTempState() {
		return tempState;
	}

	public void setTempState(String tempState) {
		this.tempState = tempState;
	}

	@Column(length = 200)
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
	public List<JXKH_QKLWDept> getLwDep() {
		return lwDep;
	}

	public void setLwDep(List<JXKH_QKLWDept> lwDep) {
		this.lwDep = lwDep;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meeting")
	public Set<JXKH_QKLWFile> getFiles() {
		return files;
	}

	public void setFiles(Set<JXKH_QKLWFile> files) {
		this.files = files;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "QKLWName")
	public List<JXKH_QKLWMember> getLwAll() {
		return lwAll;
	}

	public void setLwAll(List<JXKH_QKLWMember> lwAll) {
		this.lwAll = lwAll;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meeting")
	public List<JXKH_QklwSlMessage> getSlMessage() {
		return slMessage;
	}

	public void setSlMessage(List<JXKH_QklwSlMessage> slMessage) {
		this.slMessage = slMessage;
	}
	@Column
	public Long getComputeType() {
		return computeType;
	}

	public void setComputeType(Long computeType) {
		this.computeType = computeType;
	}

}
