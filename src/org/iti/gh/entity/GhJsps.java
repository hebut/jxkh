package org.iti.gh.entity;


public class GhJsps implements java.io.Serializable{

	private Long jspsId;
	private Long kuId;
	private Integer jspsYear;
	private String jspsSubject;
	private String jspsPosition;
	private String jspsType;
	private String jspsAnnualAssessment;
	private String jspsComputer;
	private String jspsForeign;
	private String jspsStatus;
	private Long jspsAuditUserId;
	private String jspsRemark;
	
	public static final String regular = "正常",unRegular="破格";
	public static final String qualified = "合格",unQualified= "不合格";
	public static final String notApplied = "未提出申请",auditing = "审核中", pass = "审核通过",notPass = "审核未通过";
	
	public GhJsps() {
		super();
	}
	public GhJsps(Long jspsId, Long kuid,Integer jspsYear, String jspsSubject,String jspsPosition,String jspsType, 
			String jspsAnnualAssessment, String jspsComputer,String jspsForeign,String jspsStatus,Long jspsAuditUserId,
			String jspsRemark) {
		super();
		this.jspsId = jspsId;
		this.kuId = kuid;
		this.jspsYear = jspsYear;
		this.jspsSubject= jspsSubject;
		this.jspsPosition = jspsPosition;
		this.jspsType = jspsType;
		this.jspsAnnualAssessment = jspsAnnualAssessment;
		this.jspsComputer = jspsComputer;
		this.jspsForeign = jspsForeign;
		this.jspsStatus = jspsStatus;
		this.jspsAuditUserId = jspsAuditUserId;
		this.jspsRemark = jspsRemark;
	}
	public Long getJspsId() {
		return jspsId;
	}
	public void setJspsId(Long jspsId) {
		this.jspsId = jspsId;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	
	public Integer getJspsYear() {
		return jspsYear;
	}
	public void setJspsYear(Integer jspsYear) {
		this.jspsYear = jspsYear;
	}
	public String getJspsSubject() {
		return jspsSubject;
	}
	public void setJspsSubject(String jspsSubject) {
		this.jspsSubject = jspsSubject;
	}
	public String getJspsPosition() {
		return jspsPosition;
	}
	public void setJspsPosition(String jspsPosition) {
		this.jspsPosition = jspsPosition;
	}
	public String getJspsType() {
		return jspsType;
	}
	public void setJspsType(String jspsType) {
		this.jspsType = jspsType;
	}
	public String getJspsAnnualAssessment() {
		return jspsAnnualAssessment;
	}
	public void setJspsAnnualAssessment(String jspsAnnualAssessment) {
		this.jspsAnnualAssessment = jspsAnnualAssessment;
	}
	public String getJspsComputer() {
		return jspsComputer;
	}
	public void setJspsComputer(String jspsComputer) {
		this.jspsComputer = jspsComputer;
	}
	public String getJspsForeign() {
		return jspsForeign;
	}
	public void setJspsForeign(String jspsForeign) {
		this.jspsForeign = jspsForeign;
	}
	public String getJspsStatus() {
		return jspsStatus;
	}
	public void setJspsStatus(String jspsStatus) {
		this.jspsStatus = jspsStatus;
	}
	public Long getJspsAuditUserId() {
		return jspsAuditUserId;
	}
	public void setJspsAuditUserId(Long jspsAuditUserId) {
		this.jspsAuditUserId = jspsAuditUserId;
	}
	public String getJspsRemark() {
		return jspsRemark;
	}
	public void setJspsRemark(String jspsRemark) {
		this.jspsRemark = jspsRemark;
	}
	
	
}
