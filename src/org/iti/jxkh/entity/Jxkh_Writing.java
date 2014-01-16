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

@Entity
public class Jxkh_Writing implements java.io.Serializable {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -1216479008532181771L;

	private Long id;// ���
	private String name;// ��������
	private String recordCode;// �������
	private Jxkh_BusinessIndicator sort;// ��������
	private String publishDate;// ����ʱ��
	private String publishName;// ������
	private Short ifPublish;// �Ƿ���ʽ����
	private String infoWriter;// ��Ϣ��д��
	private Short cooState;// �Ƿ����ⵥλ����
	private String cooCompany;// ������λ
	private Short state;// ���״̬
	private String tempState;// �����ʱ״̬λ��0δ��ˣ�1ͨ����2��ͨ����
	private String bAdvice;// ҵ���������
	private String dep1Reason;// �����ŷ������
	private String dep2Reason;
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;
	private String jxYear;// �������
	private String submitTime;// �ύʱ��
	private String scoreWay;// ���ַ�ʽ
	private String memPercent;//�α���Ա����
	private String contentPercent;//�α����ݱ���
	private List<Jxkh_WritingDept> writingDept;// Ժ����ɲ���
	private List<Jxkh_Writer> writer;// ȫ������
	private Set<Jxkh_WritingFile> writingFile;// ����
	private Short firstSign;
	private Float score;

	// /* ���״̬��0 ����ˣ�1 �������ͨ���� 2 ������˲�ͨ����3 ҵ������ͨ����4 ҵ�����˲�ͨ�� */
	// public static final short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2, BUSINESS_PASS = 3, BUSINESS_NOT_PASS = 4,SAVE_RECORD = 5;
	/**
	 * ���״̬��0 ����ˣ�1 �������ͨ����2���������ͨ����3 ������˲�ͨ����4 ҵ������ͨ����5 ҵ�����˲�ͨ����6�鵵��7ҵ����ݻ�ͨ����
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/* �Ƿ�0 ��1 �� */
	public static final short NO = 0, YES = 1;

	public Jxkh_Writing() {
		super();
	}

	public Jxkh_Writing(Long id, String name, String recordCode,
			Jxkh_BusinessIndicator sort, String publishDate,
			String publishName, Short ifPublish, String infoWriter,
			Short cooState, String cooCompany, Short state, String tempState,
			String bAdvice, String dep1Reason, String dep2Reason,
			String dep3Reason, String dep4Reason, String dep5Reason,
			String dep6Reason, String dep7Reason, String jxYear,
			String submitTime, String scoreWay, String memPercent,
			String contentPercent, List<Jxkh_WritingDept> writingDept,
			List<Jxkh_Writer> writer, Set<Jxkh_WritingFile> writingFile,
			Short firstSign, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.recordCode = recordCode;
		this.sort = sort;
		this.publishDate = publishDate;
		this.publishName = publishName;
		this.ifPublish = ifPublish;
		this.infoWriter = infoWriter;
		this.cooState = cooState;
		this.cooCompany = cooCompany;
		this.state = state;
		this.tempState = tempState;
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
		this.scoreWay = scoreWay;
		this.memPercent = memPercent;
		this.contentPercent = contentPercent;
		this.writingDept = writingDept;
		this.writer = writer;
		this.writingFile = writingFile;
		this.firstSign = firstSign;
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

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@ManyToOne
	@JoinColumn(name = "kbId3")
	public Jxkh_BusinessIndicator getSort() {
		return sort;
	}

	public void setSort(Jxkh_BusinessIndicator sort) {
		this.sort = sort;
	}

	@Column(length = 50)
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	@Column(length = 50)
	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	@Column
	public Short getIfPublish() {
		return ifPublish;
	}

	public void setIfPublish(Short ifPublish) {
		this.ifPublish = ifPublish;
	}

	@Column(length = 50)
	public String getInfoWriter() {
		return infoWriter;
	}

	public void setInfoWriter(String infoWriter) {
		this.infoWriter = infoWriter;
	}

	@Column
	public Short getCooState() {
		return cooState;
	}

	public void setCooState(Short cooState) {
		this.cooState = cooState;
	}

	@Column(length = 50)
	public String getCooCompany() {
		return cooCompany;
	}

	public void setCooCompany(String cooCompany) {
		this.cooCompany = cooCompany;
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
	public String getScoreWay() {
		return scoreWay;
	}

	public void setScoreWay(String scoreWay) {
		this.scoreWay = scoreWay;
	}

	@Column(length = 50)
	public String getMemPercent() {
		return memPercent;
	}

	public void setMemPercent(String memPercent) {
		this.memPercent = memPercent;
	}

	@Column(length = 50)
	public String getContentPercent() {
		return contentPercent;
	}

	public void setContentPercent(String contentPercent) {
		this.contentPercent = contentPercent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "writing")
	public List<Jxkh_WritingDept> getWritingDept() {
		return writingDept;
	}

	public void setWritingDept(List<Jxkh_WritingDept> writingDept) {
		this.writingDept = writingDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "writing")
	public List<Jxkh_Writer> getWriter() {
		return writer;
	}

	public void setWriter(List<Jxkh_Writer> writer) {
		this.writer = writer;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "writing")
	public Set<Jxkh_WritingFile> getWritingFile() {
		return writingFile;
	}

	public void setWritingFile(Set<Jxkh_WritingFile> writingFile) {
		this.writingFile = writingFile;
	}

	@Column
	public Short getFirstSign() {
		return firstSign;
	}

	public void setFirstSign(Short firstSign) {
		this.firstSign = firstSign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_Writing other = (Jxkh_Writing) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Column(length = 50)
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
}
