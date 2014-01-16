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
@Table(name = "Jxkh_Video")
public class Jxkh_Video implements java.io.Serializable {

	private static final long serialVersionUID = 3186569327019370896L;

	private Long id;

	private String name;// Ӱ������

	private Float score;

	private Short coState;// �Ƿ����ⵥλ����

	private String recordCode;// �������

	private Jxkh_BusinessIndicator leader;// ��ʾ�쵼�ȼ�id

	private Jxkh_BusinessIndicator rank;// ������λ�ȼ�

	private String type;// Ӱ������

	private Float longTime;// ����ʱ��

	private String shootTime;// ����ʱ��

	private String playTime;// ����ʱ��

	private String media;// ����ý��

	private String coCompany;// ������λ

	private String submitName;// �ύ������

	private String submitId;// �ύ�˱��

	private Short firstSign;// �Ƿ��һ����

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

	private List<Jxkh_VideoMember> videoMember;

	private List<Jxkh_VideoDept> videoDept;

	private Set<Jxkh_VideoFile> videoFile;
	private Long computeType;
	// /* ���״̬��0 ����ˣ�1 �������ͨ���� 2 ������˲�ͨ����3 ҵ������ͨ����4 ҵ�����˲�ͨ�� */
	// public static final Short NOT_AUDIT = 0, DEPT_PASS = 1, DEPT_NOT_PASS =
	// 2, BUSINESS_PASS = 3,
	// BUSINESS_NOT_PASS = 4,SAVE_RECORD=5;
	/**
	 * ���״̬��0 ����ˣ�1 �������ͨ����2���������ͨ����3 ������˲�ͨ����4 ҵ������ͨ����5 ҵ�����˲�ͨ����6�鵵��7ҵ����ݻ�ͨ����
	 */
	public static final short NOT_AUDIT = 0, DEPT_PASS = 1,
			First_Dept_Pass = 2, DEPT_NOT_PASS = 3, BUSINESS_PASS = 4,
			BUSINESS_NOT_PASS = 5, SAVE_RECORD = 6,BUSINESS_TEMP_PASS=7;

	/* �Ƿ����ⵥλ������0 ��1 �� */
	public static final Short NO = 0, YES = 1;
	/* Ӱ������ */
	public static final String TYPE1 = "��Ƶ��Ƭ", TYPE2 = "�Ƽ�����ר��Ƭ",
			TYPE3 = "���ս���ר��Ƭ", TYPE4 = "���Ųɷ�";

	/** default constructor */
	public Jxkh_Video() {

	}

	public Jxkh_Video(Long id, String name, Float score, Short coState,
			String recordCode, Jxkh_BusinessIndicator leader,
			Jxkh_BusinessIndicator rank, String type, Float longTime,
			String shootTime, String playTime, String media, String coCompany,
			String submitName, String submitId, Short firstSign, Short state,
			String tempState, String bAdvice, String dep1Reason,
			String dep2Reason, String dep3Reason, String dep4Reason,
			String dep5Reason, String dep6Reason, String dep7Reason,
			String jxYear, String submitTime,
			List<Jxkh_VideoMember> videoMember, List<Jxkh_VideoDept> videoDept,
			Set<Jxkh_VideoFile> videoFile, Long computeType) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.coState = coState;
		this.recordCode = recordCode;
		this.leader = leader;
		this.rank = rank;
		this.type = type;
		this.longTime = longTime;
		this.shootTime = shootTime;
		this.playTime = playTime;
		this.media = media;
		this.coCompany = coCompany;
		this.submitName = submitName;
		this.submitId = submitId;
		this.firstSign = firstSign;
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
		this.videoMember = videoMember;
		this.videoDept = videoDept;
		this.videoFile = videoFile;
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

	@ManyToOne
	@JoinColumn(name = "leader", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getLeader() {
		return leader;
	}

	public void setLeader(Jxkh_BusinessIndicator leader) {
		this.leader = leader;
	}

	@ManyToOne
	@JoinColumn(name = "rank", nullable = true, insertable = true, updatable = true)
	public Jxkh_BusinessIndicator getRank() {
		return rank;
	}

	public void setRank(Jxkh_BusinessIndicator rank) {
		this.rank = rank;
	}

	@Column(length = 50)
	public Float getLongTime() {
		return longTime;
	}

	public void setLongTime(Float longTime) {
		this.longTime = longTime;
	}

	@Column(length = 20)
	public String getShootTime() {
		return shootTime;
	}

	public void setShootTime(String shootTime) {
		this.shootTime = shootTime;
	}

	@Column(length = 20)
	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	@Column(length = 50)
	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
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

	@Column(length = 20)
	public String getTempState() {
		return tempState;
	}

	@Column(length = 50)
	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public void setTempState(String tempState) {
		this.tempState = tempState;
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

	@Column(length = 200)
	public String getbAdvice() {
		return bAdvice;
	}

	public void setbAdvice(String bAdvice) {
		this.bAdvice = bAdvice;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "video")
	public List<Jxkh_VideoMember> getVideoMember() {
		return videoMember;
	}

	public void setVideoMember(List<Jxkh_VideoMember> videoMember) {
		this.videoMember = videoMember;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "video")
	public List<Jxkh_VideoDept> getVideoDept() {
		return videoDept;
	}

	public void setVideoDept(List<Jxkh_VideoDept> videoDept) {
		this.videoDept = videoDept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "video")
	public Set<Jxkh_VideoFile> getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(Set<Jxkh_VideoFile> videoFile) {
		this.videoFile = videoFile;
	}

	@Column
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
