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
@Table(name = "JXKH_HYLW")
public class JXKH_HYLW implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 2002982859854390431L;

	private Long lwId;// ����ID
	private String lwName;// ������Ŀ
	private String recordCode;// ������
	private String lwWriter;// ��Ϣ��д��
	private String writerId;// ��Ϣ��д�˱��
	private Short ifUnion;// �Ƿ����ⵥλ����(0-��1-��)
	private Short ifSign;// �Ƿ��ǵ�һ����
	private String lwCoDep;// ������λ
	private Jxkh_BusinessIndicator lwGrade;// ���鼶��Ĭ��Ϊ0��0��ѡ��1���ʻ��飬2���һ��飬3ʡ�����飩
	// private Jxkh_BusinessIndicator lwType;//
	// ��¼���Ĭ��Ϊ0��0��ѡ��1-SCI��¼��2-EI��¼��3-ISTP��¼��
	private String lwRank;// ���ļ��� (һ���� ������ Ȩ����CSSCI�� ������������)
	private String lwTime;// ����ʱ��
	private String bookName;// ����������
	private String lwJuan;// ��
	private String lwQC;// �������ڴ�
	private String lwPages;// ��ֹҳ
	private String lwHyName;// ��������
	private String lwAuthor;// ͨѶ����
	private String lwZDep;// ���쵥λ
	private String lwCDep;// �а쵥λ
	private String lwXDep;// Э�쵥λ
	private String lwDate;// �ٿ�ʱ��
	private Short lwState;// ���״̬
	private String tempState;// �����ʱ״̬λ��0δ��ˣ�1ͨ����2��ͨ����
	private String ywReason;// ҵ��췴�����
	private String dep1Reason;// �����ŷ������
	private String dep2Reason;
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;
	private String jxYear;
	private String submitTime;// �ύʱ��
	private Float score;
	private List<JXKH_HYLWDept> lwDep;// Ժ�ڲ���
	private Set<JXKH_HYLWFile> files;// ����ĵ�
	private List<JXKH_HULWMember> lwAll;// ȫ������
	private List<JXKH_HYlwSlMessage> slMessage;// ��¼��Ϣ
	private Long computeType;
	// /**
	// * ���״̬��0 ����ˣ�1 �������ͨ���� 2 ������˲�ͨ����3 ҵ������ͨ����4 ҵ�����˲�ͨ��
	// */
	// public static final short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2,
	// BUSINESS_PASS = 3, BUSINESS_NOT_PASS = 4, SAVE_RECORD = 5;
	/**
	 * ���״̬��0 ����ˣ�1 �������ͨ����2���������ͨ����3 ������˲�ͨ����4 ҵ������ͨ����5 ҵ�����˲�ͨ����6�鵵��7ҵ����ݻ�ͨ����
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/**
	 * �Ƿ����ⵥλ������0 ��1 ��
	 */
	public static final short NO = 0, YES = 1;

	public JXKH_HYLW() {
		super();
	}

	public JXKH_HYLW(Long lwId, String lwName, String recordCode,
			String lwWriter, String writerId, Short ifUnion, Short ifSign,
			String lwCoDep, Jxkh_BusinessIndicator lwGrade, String lwRank,
			String lwTime, String bookName, String lwJuan, String lwQC,
			String lwPages, String lwHyName, String lwAuthor, String lwZDep,
			String lwCDep, String lwXDep, String lwDate, Short lwState,
			String tempState, String ywReason, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime, Float score,
			List<JXKH_HYLWDept> lwDep, Set<JXKH_HYLWFile> files,
			List<JXKH_HULWMember> lwAll, List<JXKH_HYlwSlMessage> slMessage,
			Long computeType) {
		super();
		this.lwId = lwId;
		this.lwName = lwName;
		this.recordCode = recordCode;
		this.lwWriter = lwWriter;
		this.writerId = writerId;
		this.ifUnion = ifUnion;
		this.ifSign = ifSign;
		this.lwCoDep = lwCoDep;
		this.lwGrade = lwGrade;
		this.lwRank = lwRank;
		this.lwTime = lwTime;
		this.bookName = bookName;
		this.lwJuan = lwJuan;
		this.lwQC = lwQC;
		this.lwPages = lwPages;
		this.lwHyName = lwHyName;
		this.lwAuthor = lwAuthor;
		this.lwZDep = lwZDep;
		this.lwCDep = lwCDep;
		this.lwXDep = lwXDep;
		this.lwDate = lwDate;
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

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(length = 100)
	public String getLwWriter() {
		return lwWriter;
	}

	public void setLwWriter(String lwWriter) {
		this.lwWriter = lwWriter;
	}

	@Column(length = 40)
	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
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
	@JoinColumn(name = "lwGrade", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getLwGrade() {
		return lwGrade;
	}

	public void setLwGrade(Jxkh_BusinessIndicator lwGrade) {
		this.lwGrade = lwGrade;
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

	@Column(length = 50)
	public String getLwTime() {
		return lwTime;
	}

	public void setLwTime(String lwTime) {
		this.lwTime = lwTime;
	}

	@Column(length = 100)
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	@Column(length = 200)
	public String getLwHyName() {
		return lwHyName;
	}

	public void setLwHyName(String lwHyName) {
		this.lwHyName = lwHyName;
	}

	@Column(length = 100)
	public String getLwAuthor() {
		return lwAuthor;
	}

	public void setLwAuthor(String lwAuthor) {
		this.lwAuthor = lwAuthor;
	}

	@Column(length = 200)
	public String getLwZDep() {
		return lwZDep;
	}

	public void setLwZDep(String lwZDep) {
		this.lwZDep = lwZDep;
	}

	@Column(length = 200)
	public String getLwCDep() {
		return lwCDep;
	}

	public void setLwCDep(String lwCDep) {
		this.lwCDep = lwCDep;
	}

	@Column(length = 200)
	public String getLwXDep() {
		return lwXDep;
	}

	public void setLwXDep(String lwXDep) {
		this.lwXDep = lwXDep;
	}

	@Column(length = 100)
	public String getLwDate() {
		return lwDate;
	}

	public void setLwDate(String lwDate) {
		this.lwDate = lwDate;
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
	public List<JXKH_HYLWDept> getLwDep() {
		return lwDep;
	}

	public void setLwDep(List<JXKH_HYLWDept> lwDep) {
		this.lwDep = lwDep;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meeting")
	public Set<JXKH_HYLWFile> getFiles() {
		return files;
	}

	public void setFiles(Set<JXKH_HYLWFile> files) {
		this.files = files;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "HYLWName")
	public List<JXKH_HULWMember> getLwAll() {
		return lwAll;
	}

	public void setLwAll(List<JXKH_HULWMember> lwAll) {
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
	public List<JXKH_HYlwSlMessage> getSlMessage() {
		return slMessage;
	}

	public void setSlMessage(List<JXKH_HYlwSlMessage> slMessage) {
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
