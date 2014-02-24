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
@Table(name = "Jxkh_Project")
public class Jxkh_Project implements java.io.Serializable {

	/**
	 * @author SongYu
	 */
	private static final long serialVersionUID = -2139011926491891840L;

	private Long id;// ���
	private String name;// ��Ŀ����
	private String projecCode;// ��Ŀ���
	private String recordCode;// �������
	private Jxkh_BusinessIndicator rank;// ��Ŀ����
	private String beginDate;// ��ͬʼ��
	private String endDate;// ��ͬ����
	private String takeCompany;// �е���λ
	private String header;// ��Ŀ������
	private Short ifHumanities;// �Ƿ���ҡ�ʡ������ƿ���
	private Short ifSoftScience;// �Ƿ���ҡ�ʡ���ѧ����
	private Float sumFund;// �����ܶ�
	private String progress;// ��Ŀ��չ
	private String standYear;// �������
	private String standDept;// �����
	private String origin;// ��Ŀ��Դ
	private Short ifEntruster;// �Ƿ�ί�з�
	private String entruster;// ί�з�
	private String trustee;// ���з�
	private String infoWriter;// ��Ϣ��д��
	private Short cooState;// �Ƿ����ⵥλ����
	private String cooCompany;// ������λ
	private Short state;// ���״̬
	private String tempState;// �����ʱ״̬λ��0δ��ˣ�1ͨ����2��ͨ����//��״̬λ�Ѿ�����Ҫ��
	private String bAdvice;// ҵ���������
	private String dep1Reason;// �����ŷ�����������ڶಿ�ź���ֻ��Ҫ���ܲ������ͨ������
	private String dep2Reason;//2-7��deptreason�Ѿ�����Ҫ��
	private String dep3Reason;
	private String dep4Reason;
	private String dep5Reason;
	private String dep6Reason;
	private String dep7Reason;
	private String jxYear;// �������
	private String submitTime;// �ύʱ��
	private Short sort;// ��Ŀ����
	private List<Jxkh_ProjectDept> projectDept;// Ժ����ɲ���
	private List<Jxkh_ProjectMember> projectMember;// ��Ŀ���Ա
	private Set<Jxkh_ProjectFile> projectFile;// ����
	private List<Jxkh_ProjectFund> projectFund;// ����
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

	/* ��Ŀ���ࣺ0������Ŀ��1 ������Ŀ��2������Ŀ */
	public static final short ZP = 0, HP = 1, CP = 2;

	public Jxkh_Project() {
		super();
	}

	public Jxkh_Project(Long id, String name, String projecCode,
			String recordCode, Jxkh_BusinessIndicator rank, String beginDate,
			String endDate, String takeCompany, String header,
			Short ifHumanities, Short ifSoftScience, Float sumFund,
			String progress, String standYear, String standDept, String origin,
			Short ifEntruster, String entruster, String trustee,
			String infoWriter, Short cooState, String cooCompany, Short state,
			String tempState, String bAdvice, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime, Short sort,
			List<Jxkh_ProjectDept> projectDept,
			List<Jxkh_ProjectMember> projectMember,
			Set<Jxkh_ProjectFile> projectFile,
			List<Jxkh_ProjectFund> projectFund, Short firstSign, Float score) {
		super();
		this.id = id;
		this.name = name;
		this.projecCode = projecCode;
		this.recordCode = recordCode;
		this.rank = rank;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.takeCompany = takeCompany;
		this.header = header;
		this.ifHumanities = ifHumanities;
		this.ifSoftScience = ifSoftScience;
		this.sumFund = sumFund;
		this.progress = progress;
		this.standYear = standYear;
		this.standDept = standDept;
		this.origin = origin;
		this.ifEntruster = ifEntruster;
		this.entruster = entruster;
		this.trustee = trustee;
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
		this.sort = sort;
		this.projectDept = projectDept;
		this.projectMember = projectMember;
		this.projectFile = projectFile;
		this.projectFund = projectFund;
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
	public String getProjecCode() {
		return projecCode;
	}

	public void setProjecCode(String projecCode) {
		this.projecCode = projecCode;
	}

	@Column(length = 50)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@ManyToOne
	@JoinColumn(name = "kbId1")
	public Jxkh_BusinessIndicator getRank() {
		return rank;
	}

	public void setRank(Jxkh_BusinessIndicator rank) {
		this.rank = rank;
	}

	@Column(length = 50)
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	@Column(length = 50)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(length = 50)
	public String getTakeCompany() {
		return takeCompany;
	}

	public void setTakeCompany(String takeCompany) {
		this.takeCompany = takeCompany;
	}

	@Column(length = 50)
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Column
	public Short getIfHumanities() {
		return ifHumanities;
	}

	public void setIfHumanities(Short ifHumanities) {
		this.ifHumanities = ifHumanities;
	}

	@Column
	public Short getIfSoftScience() {
		return ifSoftScience;
	}

	public void setIfSoftScience(Short ifSoftScience) {
		this.ifSoftScience = ifSoftScience;
	}

	@Column
	public Float getSumFund() {
		return sumFund;
	}

	public void setSumFund(Float sumFund) {
		this.sumFund = sumFund;
	}

	@Column(length = 50)
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Column(length = 50)
	public String getStandYear() {
		return standYear;
	}

	public void setStandYear(String standYear) {
		this.standYear = standYear;
	}

	@Column(length = 50)
	public String getStandDept() {
		return standDept;
	}

	public void setStandDept(String standDept) {
		this.standDept = standDept;
	}

	@Column(length = 50)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column
	public Short getIfEntruster() {
		return ifEntruster;
	}

	public void setIfEntruster(Short ifEntruster) {
		this.ifEntruster = ifEntruster;
	}

	@Column(length = 50)
	public String getEntruster() {
		return entruster;
	}

	public void setEntruster(String entruster) {
		this.entruster = entruster;
	}

	@Column(length = 50)
	public String getTrustee() {
		return trustee;
	}

	public void setTrustee(String trustee) {
		this.trustee = trustee;
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

	@Column
	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public List<Jxkh_ProjectDept> getProjectDept() {
		return projectDept;
	}

	public void setProjectDept(List<Jxkh_ProjectDept> projectDept) {
		this.projectDept = projectDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public List<Jxkh_ProjectMember> getProjectMember() {
		return projectMember;
	}

	public void setProjectMember(List<Jxkh_ProjectMember> projectMember) {
		this.projectMember = projectMember;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project")
	public Set<Jxkh_ProjectFile> getProjectFile() {
		return projectFile;
	}

	public void setProjectFile(Set<Jxkh_ProjectFile> projectFile) {
		this.projectFile = projectFile;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	public List<Jxkh_ProjectFund> getProjectFund() {
		return projectFund;
	}

	public void setProjectFund(List<Jxkh_ProjectFund> projectFund) {
		this.projectFund = projectFund;
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
		Jxkh_Project other = (Jxkh_Project) obj;
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
