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
@Table(name = "Jxkh_Fruit")
public class Jxkh_Fruit implements java.io.Serializable {

	private static final long serialVersionUID = -503547062861853463L;
	
	private Long id;

	private String name;// 成果名称

	private Short coState;// 是否与外单位合作

	private Jxkh_BusinessIndicator appraiseRank;// 成果水平id

	private Jxkh_BusinessIndicator acceptRank;// 验收等级id

	private Jxkh_BusinessIndicator indentRank;// 鉴定级别id

	private String recordCode;// 档案号

	private String appraiseCode;// 鉴定号

	private String acceptCode;// 验收号

	private String appraiseType;// 鉴定形式

	private String appraiseDate;// 鉴定日期

	private String organAppraiseCompany;// 组织鉴定单位

	private String holdAppraiseCompany;// 主持鉴定单位

	private String acceptDetp;// 验收组织部门

	private String acceptDate;// 验收日期

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

	private Float score;
	private List<Jxkh_FruitMember> fruitMember;

	private List<Jxkh_FruitDept> fruitDept;

	private Set<Jxkh_FruitFile> fruitFile;
	
	private Long computeType;

	// /* 审核状态：0 待审核，1 部门审核通过， 2 部门审核不通过，3 业务办审核通过，4 业务办审核不通过 */
	// public static final Short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2, BUSINESS_PASS = 3,
	// BUSINESS_NOT_PASS = 4,SAVE_RECORD=5;
	/**
	 * 审核状态：0 待审核，1 部门审核通过，2主部门审核通过，3 部门审核不通过，4 业务办审核通过，5 业务办审核不通过，6归档，7业务办暂缓通过；
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/* 是否与外单位合作：0 否，1 是 */
	public static final Short NO = 0, YES = 1;
	/*成果水平 */
	public static final String GJLX="611",GJXJ="612",GNLX="613",GNXJ="614";
	/*鉴定级别 */
	public static final String GJJJD="631",SJJD="632",TJJD="633";
	/*验收等级*/
	public static final String GJJYS="621", SJYS="622", ZCJT="623";
	
	
	
	
	/** default constructor */
	public Jxkh_Fruit() {

	}

	public Jxkh_Fruit(Long id, String name, Short coState,
			Jxkh_BusinessIndicator appraiseRank,
			Jxkh_BusinessIndicator acceptRank,
			Jxkh_BusinessIndicator indentRank, String recordCode,
			String appraiseCode, String acceptCode, String appraiseType,
			String appraiseDate, String organAppraiseCompany,
			String holdAppraiseCompany, String acceptDetp, String acceptDate,
			String coCompany, String submitName, String submitId, Short state,
			String tempState, Short firstSign, String bAdvice,
			String dep1Reason, String dep2Reason, String dep3Reason,
			String dep4Reason, String dep5Reason, String dep6Reason,
			String dep7Reason, String jxYear, String submitTime, Float score,
			List<Jxkh_FruitMember> fruitMember, List<Jxkh_FruitDept> fruitDept,
			Set<Jxkh_FruitFile> fruitFile, Long computeType) {
		super();
		this.id = id;
		this.name = name;
		this.coState = coState;
		this.appraiseRank = appraiseRank;
		this.acceptRank = acceptRank;
		this.indentRank = indentRank;
		this.recordCode = recordCode;
		this.appraiseCode = appraiseCode;
		this.acceptCode = acceptCode;
		this.appraiseType = appraiseType;
		this.appraiseDate = appraiseDate;
		this.organAppraiseCompany = organAppraiseCompany;
		this.holdAppraiseCompany = holdAppraiseCompany;
		this.acceptDetp = acceptDetp;
		this.acceptDate = acceptDate;
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
		this.score = score;
		this.fruitMember = fruitMember;
		this.fruitDept = fruitDept;
		this.fruitFile = fruitFile;
		this.computeType = computeType;
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

	@ManyToOne
	@JoinColumn(name = "appraiseRank")
	public Jxkh_BusinessIndicator getAppraiseRank() {
		return appraiseRank;
	}

	public void setAppraiseRank(Jxkh_BusinessIndicator appraiseRank) {
		this.appraiseRank = appraiseRank;
	}

	@ManyToOne
	@JoinColumn(name = "acceptRank")
	public Jxkh_BusinessIndicator getAcceptRank() {
		return acceptRank;
	}

	public void setAcceptRank(Jxkh_BusinessIndicator acceptRank) {
		this.acceptRank = acceptRank;
	}

	@ManyToOne
	@JoinColumn(name = "indentRank")
	public Jxkh_BusinessIndicator getIndentRank() {
		return indentRank;
	}

	public void setIndentRank(Jxkh_BusinessIndicator indentRank) {
		this.indentRank = indentRank;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(length = 50)
	public String getAppraiseCode() {
		return appraiseCode;
	}

	public void setAppraiseCode(String appraiseCode) {
		this.appraiseCode = appraiseCode;
	}

	@Column(length = 50)
	public String getAcceptCode() {
		return acceptCode;
	}

	public void setAcceptCode(String acceptCode) {
		this.acceptCode = acceptCode;
	}

	@Column(length = 30)
	public String getAppraiseType() {
		return appraiseType;
	}

	public void setAppraiseType(String appraiseType) {
		this.appraiseType = appraiseType;
	}

	@Column(length = 20)
	public String getAppraiseDate() {
		return appraiseDate;
	}

	public void setAppraiseDate(String appraiseDate) {
		this.appraiseDate = appraiseDate;
	}

	@Column(length = 50)
	public String getOrganAppraiseCompany() {
		return organAppraiseCompany;
	}

	public void setOrganAppraiseCompany(String organAppraiseCompany) {
		this.organAppraiseCompany = organAppraiseCompany;
	}

	@Column(length = 50)
	public String getHoldAppraiseCompany() {
		return holdAppraiseCompany;
	}

	public void setHoldAppraiseCompany(String holdAppraiseCompany) {
		this.holdAppraiseCompany = holdAppraiseCompany;
	}

	@Column(length = 50)
	public String getAcceptDetp() {
		return acceptDetp;
	}

	public void setAcceptDetp(String acceptDetp) {
		this.acceptDetp = acceptDetp;
	}

	@Column(length = 20)
	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
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

	@Column(length = 200)
	public String getbAdvice() {
		return bAdvice;
	}

	public void setbAdvice(String bAdvice) {
		this.bAdvice = bAdvice;
	}

	@Column
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fruit")
	public List<Jxkh_FruitMember> getFruitMember() {
		return fruitMember;
	}

	public void setFruitMember(List<Jxkh_FruitMember> fruitMember) {
		this.fruitMember = fruitMember;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fruit")
	public List<Jxkh_FruitDept> getFruitDept() {
		return fruitDept;
	}

	public void setFruitDept(List<Jxkh_FruitDept> fruitDept) {
		this.fruitDept = fruitDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fruit")
	public Set<Jxkh_FruitFile> getFruitFile() {
		return fruitFile;
	}

	public void setFruitFile(Set<Jxkh_FruitFile> fruitFile) {
		this.fruitFile = fruitFile;
	}

	@Column
	public Short getFirstSign() {
		return firstSign;
	}

	public void setFirstSign(Short firstSign) {
		this.firstSign = firstSign;
	}
	@Column
	public Long getComputeType() {
		return computeType;
	}

	public void setComputeType(Long computeType) {
		this.computeType = computeType;
	}

}
