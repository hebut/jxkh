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
public class Jxkh_Patent implements java.io.Serializable {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = 1654435039073143080L;

	private Long id;// ���
	private String name;// ר��(���)����
	private String applyCode;// ������
	private String grantCode;// ��Ȩ���
	private String recordCode;// �������
	private Jxkh_BusinessIndicator sort;// ר��(���)����
	private String owner;// ֪ʶ��Ȩ��
	private String applyDate;// ����ʱ��
	private String grantDate;// ��Ȩʱ��
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
	private List<Jxkh_PatentDept> patentDept;// Ժ����ɲ���
	private List<Jxkh_PatentInventor> patentInventor;// ������
	private Set<Jxkh_PatentFile> patentFile;// ����
	private Short firstSign;//�Ƿ��һ����
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

	/* �Ƿ����ⵥλ������0 ��1 �� */
	public static final short NO = 0, YES = 1;

	public Jxkh_Patent() {
		super();
	}

	public Jxkh_Patent(Long id, String name, String applyCode,
			String grantCode, String recordCode, Jxkh_BusinessIndicator sort,
			String owner, String applyDate, String grantDate,
			String infoWriter, Short cooState, String cooCompany, Short state,
			String tempState, String bAdvice, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime, List<Jxkh_PatentDept> patentDept,
			List<Jxkh_PatentInventor> patentInventor,
			Set<Jxkh_PatentFile> patentFile, Short firstSign, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.applyCode = applyCode;
		this.grantCode = grantCode;
		this.recordCode = recordCode;
		this.sort = sort;
		this.owner = owner;
		this.applyDate = applyDate;
		this.grantDate = grantDate;
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
		this.patentDept = patentDept;
		this.patentInventor = patentInventor;
		this.patentFile = patentFile;
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
	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	@Column(length = 50)
	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@ManyToOne
	@JoinColumn(name = "kbId2")
	public Jxkh_BusinessIndicator getSort() {
		return sort;
	}

	public void setSort(Jxkh_BusinessIndicator sort) {
		this.sort = sort;
	}

	@Column(length = 50)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(length = 50)
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
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
	public String getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(String grantDate) {
		this.grantDate = grantDate;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patent")
	public List<Jxkh_PatentDept> getPatentDept() {
		return patentDept;
	}

	public void setPatentDept(List<Jxkh_PatentDept> patentDept) {
		this.patentDept = patentDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patent")
	public List<Jxkh_PatentInventor> getPatentInventor() {
		return patentInventor;
	}

	public void setPatentInventor(List<Jxkh_PatentInventor> patentInventor) {
		this.patentInventor = patentInventor;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "patent")
	public Set<Jxkh_PatentFile> getPatentFile() {
		return patentFile;
	}

	public void setPatentFile(Set<Jxkh_PatentFile> patentFile) {
		this.patentFile = patentFile;
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
		Jxkh_Patent other = (Jxkh_Patent) obj;
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
