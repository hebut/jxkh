package org.iti.jxkh.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iti.common.interfaces.EntityCommonInterface;
import com.iti.common.util.EntityUtil;
@Entity
@Table(name = "Jxkh_UserDetail")
public class Jxkh_UserDetail implements EntityCommonInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long udId;
	private Long kuId;
	private String language;
	
	private String fDegree;
	private String fQulification;
	private String fSchoolYear;
	private String fSchool;
	private String fMajor;
	private String fQuliNumb;
	private String fInTime;
	private String fGraduTime;
	
	private String hDegree;
	private String hQulification;
	private String hSchoolYear;
	private String hSchool;
	private String hMajor;
	private String hQuliNumb;
	private String hInTime;
	private String hGraduTime;
	
	
	private String startTime;
	private String enterTime;
	private String jobQuality;
	private String post;
	private String postRank;
	private String nowPost;
	private String nowPtRank;
	private String workQulity;
	private String workType;
	private String staffQuality;
	private String staffState;
	private String leaveTime;
	private String leaveReason;
	private String entireTime;
	private String entireReason;
	
	private String empQualy;
	private String getTime;
	private String empRank;
	private String empJob;
	private String empState;
	private String empTime;
	private String empEndTime;
	private String dismisTime;
	private String dismisNum;
	private String dismissReasn;	
	
	
	
	
	public Jxkh_UserDetail() {
		
	}

	public Jxkh_UserDetail(Long udId, Long kuId, String language, String fDegree, String fQulification, String fSchoolYear, String fSchool, String fMajor, String fQuliNumb, String fInTime, String fGraduTime, String hDegree, String hQulification,
			String hSchoolYear, String hSchool, String hMajor, String hQuliNumb, String hInTime, String hGraduTime, String startTime, String enterTime, String jobQuality, String post, String postRank, String nowPost, String nowPtRank, String workQulity,
			String workType, String staffQuality, String staffState, String leaveTime, String leaveReason, String entireTime, String entireReason, String empQualy, String getTime, String empRank, String empJob, String empState, String empTime,
			String empEndTime, String dismisTime, String dismisNum, String dismissReasn) {
		super();
		this.udId = udId;
		this.kuId = kuId;
		this.language = language;
		this.fDegree = fDegree;
		this.fQulification = fQulification;
		this.fSchoolYear = fSchoolYear;
		this.fSchool = fSchool;
		this.fMajor = fMajor;
		this.fQuliNumb = fQuliNumb;
		this.fInTime = fInTime;
		this.fGraduTime = fGraduTime;
		this.hDegree = hDegree;
		this.hQulification = hQulification;
		this.hSchoolYear = hSchoolYear;
		this.hSchool = hSchool;
		this.hMajor = hMajor;
		this.hQuliNumb = hQuliNumb;
		this.hInTime = hInTime;
		this.hGraduTime = hGraduTime;
		this.startTime = startTime;
		this.enterTime = enterTime;
		this.jobQuality = jobQuality;
		this.post = post;
		this.postRank = postRank;
		this.nowPost = nowPost;
		this.nowPtRank = nowPtRank;
		this.workQulity = workQulity;
		this.workType = workType;
		this.staffQuality = staffQuality;
		this.staffState = staffState;
		this.leaveTime = leaveTime;
		this.leaveReason = leaveReason;
		this.entireTime = entireTime;
		this.entireReason = entireReason;
		this.empQualy = empQualy;
		this.getTime = getTime;
		this.empRank = empRank;
		this.empJob = empJob;
		this.empState = empState;
		this.empTime = empTime;
		this.empEndTime = empEndTime;
		this.dismisTime = dismisTime;
		this.dismisNum = dismisNum;
		this.dismissReasn = dismissReasn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getUdId() {
		return udId;
	}


	public void setUdId(Long udId) {
		this.udId = udId;
	}

	@Column
	public Long getKuId() {
		return kuId;
	}


	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	@Column(length = 10)
	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(length = 10)
	public String getfDegree() {
		return fDegree;
	}


	public void setfDegree(String fDegree) {
		this.fDegree = fDegree;
	}

	@Column(length = 10)
	public String getfQulification() {
		return fQulification;
	}


	public void setfQulification(String fQulification) {
		this.fQulification = fQulification;
	}

	@Column(length = 50)
	public String getfSchoolYear() {
		return fSchoolYear;
	}


	public void setfSchoolYear(String fSchoolYear) {
		this.fSchoolYear = fSchoolYear;
	}

	@Column(length = 5000)
	public String getfSchool() {
		return fSchool;
	}


	public void setfSchool(String fSchool) {
		this.fSchool = fSchool;
	}

	@Column(length = 5000)
	public String getfMajor() {
		return fMajor;
	}


	public void setfMajor(String fMajor) {
		this.fMajor = fMajor;
	}

	@Column(length = 5000)
	public String getfQuliNumb() {
		return fQuliNumb;
	}


	public void setfQuliNumb(String fQuliNumb) {
		this.fQuliNumb = fQuliNumb;
	}

	@Column(length = 20)
	public String getfGraduTime() {
		return fGraduTime;
	}


	public void setfGraduTime(String fGraduTime) {
		this.fGraduTime = fGraduTime;
	}
	@Column(length = 20)
	public String getfInTime() {
		return fInTime;
	}

	public void setfInTime(String fInTime) {
		this.fInTime = fInTime;
	}

	@Column(length = 10)
	public String gethDegree() {
		return hDegree;
	}


	public void sethDegree(String hDegree) {
		this.hDegree = hDegree;
	}

	@Column(length = 10)
	public String gethQulification() {
		return hQulification;
	}


	public void sethQulification(String hQulification) {
		this.hQulification = hQulification;
	}

	@Column(length = 20)
	public String gethSchoolYear() {
		return hSchoolYear;
	}


	public void sethSchoolYear(String hSchoolYear) {
		this.hSchoolYear = hSchoolYear;
	}

	@Column(length = 5000)
	public String gethSchool() {
		return hSchool;
	}


	public void sethSchool(String hSchool) {
		this.hSchool = hSchool;
	}

	@Column(length = 5000)
	public String gethMajor() {
		return hMajor;
	}


	public void sethMajor(String hMajor) {
		this.hMajor = hMajor;
	}

	@Column(length = 5000)
	public String gethQuliNumb() {
		return hQuliNumb;
	}


	public void sethQuliNumb(String hQuliNumb) {
		this.hQuliNumb = hQuliNumb;
	}

	@Column(length = 20)
	public String gethGraduTime() {
		return hGraduTime;
	}


	public void sethGraduTime(String hGraduTime) {
		this.hGraduTime = hGraduTime;
	}	
	@Column(length = 20)
	public String gethInTime() {
		return hInTime;
	}

	public void sethInTime(String hInTime) {
		this.hInTime = hInTime;
	}

	@Column(length = 20)
	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(length = 20)
	public String getEnterTime() {
		return enterTime;
	}


	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	@Column(length = 500)
	public String getJobQuality() {
		return jobQuality;
	}


	public void setJobQuality(String jobQuality) {
		this.jobQuality = jobQuality;
	}

	@Column(length = 5000)
	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}

	@Column(length = 5000)
	public String getPostRank() {
		return postRank;
	}


	public void setPostRank(String postRank) {
		this.postRank = postRank;
	}

	@Column(length = 5000)
	public String getNowPost() {
		return nowPost;
	}


	public void setNowPost(String nowPost) {
		this.nowPost = nowPost;
	}

	@Column(length = 5000)
	public String getNowPtRank() {
		return nowPtRank;
	}


	public void setNowPtRank(String nowPtRank) {
		this.nowPtRank = nowPtRank;
	}

	@Column(length = 50)
	public String getWorkQulity() {
		return workQulity;
	}


	public void setWorkQulity(String workQulity) {
		this.workQulity = workQulity;
	}

	@Column(length = 50)
	public String getWorkType() {
		return workType;
	}


	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@Column(length = 50)
	public String getStaffQuality() {
		return staffQuality;
	}


	public void setStaffQuality(String staffQuality) {
		this.staffQuality = staffQuality;
	}

	@Column(length = 10)
	public String getStaffState() {
		return staffState;
	}


	public void setStaffState(String staffState) {
		this.staffState = staffState;
	}

	@Column(length = 20)
	public String getLeaveTime() {
		return leaveTime;
	}


	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(length = 8000)
	public String getLeaveReason() {
		return leaveReason;
	}


	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	@Column(length = 20)
	public String getEntireTime() {
		return entireTime;
	}


	public void setEntireTime(String entireTime) {
		this.entireTime = entireTime;
	}

	@Column(length = 8000)
	public String getEntireReason() {
		return entireReason;
	}


	public void setEntireReason(String entireReason) {
		this.entireReason = entireReason;
	}

	@Column(length = 5000)
	public String getEmpQualy() {
		return empQualy;
	}


	public void setEmpQualy(String empQualy) {
		this.empQualy = empQualy;
	}	
	
	@Column(length = 30)
	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	@Column(length = 5000)
	public String getEmpRank() {
		return empRank;
	}


	public void setEmpRank(String empRank) {
		this.empRank = empRank;
	}

	@Column(length = 5000)
	public String getEmpJob() {
		return empJob;
	}


	public void setEmpJob(String empJob) {
		this.empJob = empJob;
	}

	@Column(length = 5000)
	public String getEmpState() {
		return empState;
	}


	public void setEmpState(String empState) {
		this.empState = empState;
	}

	@Column(length = 20)
	public String getEmpTime() {
		return empTime;
	}


	public void setEmpTime(String empTime) {
		this.empTime = empTime;
	}

	@Column(length = 20)
	public String getEmpEndTime() {
		return empEndTime;
	}


	public void setEmpEndTime(String empEndTime) {
		this.empEndTime = empEndTime;
	}

	@Column(length = 20)
	public String getDismisTime() {
		return dismisTime;
	}


	public void setDismisTime(String dismisTime) {
		this.dismisTime = dismisTime;
	}

	@Column(length = 5000)
	public String getDismisNum() {
		return dismisNum;
	}


	public void setDismisNum(String dismisNum) {
		this.dismisNum = dismisNum;
	}

	@Column(length = 8000)
	public String getDismissReasn() {
		return dismissReasn;
	}


	public void setDismissReasn(String dismissReasn) {
		this.dismissReasn = dismissReasn;
	}
	

	@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_UserDetail.class, this.udId);
	}

}
