package org.iti.gh.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhXm entity. @author MyEclipse Persistence Tools
 */

public class GhXm implements java.io.Serializable {

	// Fields

	public static final Short KYCG=1,KYDQ=2,JYCG=3,JYDQ=4;
	//科研成果/目前承担项目/已完成教研/正在进行教研情况
	public static final Short KYXM=1,JYXM=2;
	//国家级、省部级
	public static final String GJJ="2",SBJ="3",STJ="4";
	//横向、纵向
	public static final String HX="1",ZX="2";
	//项目进展  1申请中/2已完成3/在研
	public static final String PROGRESS_DOING="1",PROGRESS_DO="2",PROGRESS_DONE="3";
	//是否获奖 1否，2是
	public static final String PRIZE_NO = "1",PRIZE_YES="2";
    public static final Short HFILE_NO=0,HFILE_YES=1;	
	public static final Short AUDIT_NO=0,AUDIT_YES=1;	
	public static final String BASE_FILE="/upload/xkjs/xm/";//学科建设
	private Long kyId;
	private String kyMc;
	private String kyLy;
	private String kyLxsj;
	private String kyKssj;
	private String kyJssj;
	private Float kyJf;
	private Integer kyGx;
	private String kyCdrw;
	private Short kyLx;
	private Long kuId;
	WkTUser user;
	WkTUser auditUser;
	// Constructors
	//新加字段
	private String yjId;
	private String kyNumber;
	private String kySubjetype;
	private String kyProman;
	private String kyProstaffs;
	private String kyRegister;
	private String kyClass;
	private String kyScale;//级别
	private String kyProgress;
	private String kyTarget;
	private String kyIdenttime;
	private String kyLevel;
	private String kyPrize;
	private Short kyHfile;
	
	public String getCommitCom() {
		return commitCom;
	}

	public void setCommitCom(String commitCom) {
		this.commitCom = commitCom;
	}

	public String getCommitComPlace() {
		return commitComPlace;
	}

	public void setCommitComPlace(String commitComPlace) {
		this.commitComPlace = commitComPlace;
	}

	public String getAcceptCom() {
		return acceptCom;
	}

	public void setAcceptCom(String acceptCom) {
		this.acceptCom = acceptCom;
	}

	public String getAcceptComPlace() {
		return acceptComPlace;
	}

	public void setAcceptComPlace(String acceptComPlace) {
		this.acceptComPlace = acceptComPlace;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	private Short auditState;
	private Long auditUid;
	private String reason;
	
	private String internalNum;
	private String kyContraNum;
	private String kyFinishUnit;
	private String kySetFinishTime;
	private String kyRealFinishTime;
	private Float  kyGrants;
	private String kyContentFinishConditon;
	private String kyFruit;
	
	private String commitCom;
	private String commitComPlace;
	private String acceptCom;
	private String acceptComPlace;
	private String contractType;
	
	public String getReleativeFilePath(){
		return kuId+"\\"+kyId+"\\";
	}

	/** default constructor */
	public GhXm() {
	}
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	/** minimal constructor */
	public GhXm(Long kyId, Short kyLx, Long kuId) {
		this.kyId = kyId;
		this.kyLx = kyLx;
		this.kuId = kuId;
	}

	/** full constructor */
	/** full constructor */
	public GhXm(Long kyId, String kyMc, String kyLy,String kyLxsj, String kyKssj,
			String kyJssj, Float kyJf, Integer kyGx, String kyCdrw,
			Short kyLx, Long kuId, String yjId, String kyNumber,
			String kySubjetype, String kyProman, String kyProstaffs,
			String kyRegister, String kyClass, String kyScale,
			String kyProgress, String kyTarget, String kyIdenttime,
			String kyLevel, String kyPrize,Short kyHfile,Short auditState,Long auditUid,String reason,String internalNum,
			String kyContraNum,String kyFinishUnit,String kySetFinishTime,String kyRealFinishTime,Float  kyGrants,
			String kyContentFinishConditon,String kyFruit, String commitCom,String commitComPlace,String acceptCom,
			String acceptComPlace,String contractType) {
		this.kyId = kyId;
		this.kyMc = kyMc;
		this.kyLxsj=kyLxsj;
		this.kyLy = kyLy;
		this.kyKssj = kyKssj;
		this.kyJssj = kyJssj;
		this.kyJf = kyJf;
		this.kyGx = kyGx;
		this.kyCdrw = kyCdrw;
		this.kyLx = kyLx;
		this.kuId = kuId;
		this.yjId = yjId;
		this.kyNumber = kyNumber;
		this.kySubjetype = kySubjetype;
		this.kyProman = kyProman;
		this.kyProstaffs = kyProstaffs;
		this.kyRegister = kyRegister;
		this.kyClass = kyClass;
		this.kyScale = kyScale;
		this.kyProgress = kyProgress;
		this.kyTarget = kyTarget;
		this.kyIdenttime = kyIdenttime;
		this.kyLevel = kyLevel;
		this.kyPrize = kyPrize;		
		this.kyHfile = kyHfile;
		this.auditUid = auditUid;
		this.reason = reason;
		this.auditState=auditState;
		
		this.internalNum = internalNum;
		this.kyContraNum=kyContraNum;
		this.kyFinishUnit=kyFinishUnit;
		this.kySetFinishTime=kySetFinishTime;
		this.kyRealFinishTime=kyRealFinishTime;
		this.kyGrants=kyGrants;
		this.kyContentFinishConditon=kyContentFinishConditon;
		this.kyFruit=kyFruit;
		
		this.commitCom=commitCom;
		this.commitComPlace=commitComPlace;
		this.acceptCom=acceptCom;
		this.acceptComPlace=acceptComPlace;
		this.contractType=contractType;
	}
	
	public String getKyContraNum() {
		return kyContraNum;
	}

	public void setKyContraNum(String kyContraNum) {
		this.kyContraNum = kyContraNum;
	}

	public String getKyFinishUnit() {
		return kyFinishUnit;
	}

	public void setKyFinishUnit(String kyFinishUnit) {
		this.kyFinishUnit = kyFinishUnit;
	}

	public String getKySetFinishTime() {
		return kySetFinishTime;
	}

	public void setKySetFinishTime(String kySetFinishTime) {
		this.kySetFinishTime = kySetFinishTime;
	}

	public String getKyRealFinishTime() {
		return kyRealFinishTime;
	}

	public void setKyRealFinishTime(String kyRealFinishTime) {
		this.kyRealFinishTime = kyRealFinishTime;
	}

	public Float getKyGrants() {
		return kyGrants;
	}

	public void setKyGrants(Float kyGrants) {
		this.kyGrants = kyGrants;
	}

	public String getKyContentFinishConditon() {
		return kyContentFinishConditon;
	}

	public void setKyContentFinishConditon(String kyContentFinishConditon) {
		this.kyContentFinishConditon = kyContentFinishConditon;
	}

	public String getKyFruit() {
		return kyFruit;
	}

	public void setKyFruit(String kyFruit) {
		this.kyFruit = kyFruit;
	}

	public GhXm(Long kyId, String kyMc, String kyLy,String kyLxsj, String kyKssj,
			String kyJssj, Float kyJf, Integer kyGx, String kyCdrw,
			Short kyLx, Long kuId, String yjId, String kyNumber,
			String kySubjetype, String kyProman, String kyProstaffs,
			String kyRegister, String kyClass, String kyScale,
			String kyProgress, String kyTarget, String kyIdenttime,
			String kyLevel, String kyPrize,Short kyHfile,Short auditState,Long auditUid,String reason) {
		this.kyId = kyId;
		this.kyMc = kyMc;
		this.kyLxsj=kyLxsj;
		this.kyLy = kyLy;
		this.kyKssj = kyKssj;
		this.kyJssj = kyJssj;
		this.kyJf = kyJf;
		this.kyGx = kyGx;
		this.kyCdrw = kyCdrw;
		this.kyLx = kyLx;
		this.kuId = kuId;
		this.yjId = yjId;
		this.kyNumber = kyNumber;
		this.kySubjetype = kySubjetype;
		this.kyProman = kyProman;
		this.kyProstaffs = kyProstaffs;
		this.kyRegister = kyRegister;
		this.kyClass = kyClass;
		this.kyScale = kyScale;
		this.kyProgress = kyProgress;
		this.kyTarget = kyTarget;
		this.kyIdenttime = kyIdenttime;
		this.kyLevel = kyLevel;
		this.kyPrize = kyPrize;
		
		this.kyHfile = kyHfile;
		this.auditUid = auditUid;
		this.reason = reason;
		this.auditState=auditState;
	}

	// Property accessors

	public Long getKyId() {
		return this.kyId;
	}

	public void setKyId(Long kyId) {
		this.kyId = kyId;
	}

	public String getKyMc() {
		return this.kyMc;
	}

	public void setKyMc(String kyMc) {
		this.kyMc = kyMc;
	}

	public String getKyLy() {
		return this.kyLy;
	}

	public void setKyLy(String kyLy) {
		this.kyLy = kyLy;
	}

	public String getKyLxsj() {
		return kyLxsj;
	}

	public void setKyLxsj(String kyLxsj) {
		this.kyLxsj = kyLxsj;
	}

	public String getKyKssj() {
		return this.kyKssj;
	}

	public void setKyKssj(String kyKssj) {
		this.kyKssj = kyKssj;
	}

	public String getKyJssj() {
		return this.kyJssj;
	}

	public void setKyJssj(String kyJssj) {
		this.kyJssj = kyJssj;
	}

	public Float getKyJf() {
		return this.kyJf;
	}

	public void setKyJf(Float kyJf) {
		this.kyJf = kyJf;
	}

	public Integer getKyGx() {
		return this.kyGx;
	}

	public void setKyGx(Integer kyGx) {
		this.kyGx = kyGx;
	}

	public String getKyCdrw() {
		return this.kyCdrw;
	}

	public void setKyCdrw(String kyCdrw) {
		this.kyCdrw = kyCdrw;
	}

	public Short getKyLx() {
		return this.kyLx;
	}

	public void setKyLx(Short kyLx) {
		this.kyLx = kyLx;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getYjId() {
		return this.yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}

	public String getKyNumber() {
		return this.kyNumber;
	}

	public void setKyNumber(String kyNumber) {
		this.kyNumber = kyNumber;
	}

	public String getInternalNum() {
		return internalNum;
	}

	public void setInternalNum(String internalNum) {
		this.internalNum = internalNum;
	}

	public String getKySubjetype() {
		return this.kySubjetype;
	}

	public void setKySubjetype(String kySubjetype) {
		this.kySubjetype = kySubjetype;
	}

	public String getKyProman() {
		return this.kyProman;
	}

	public void setKyProman(String kyProman) {
		this.kyProman = kyProman;
	}

	public String getKyProstaffs() {
		return this.kyProstaffs;
	}

	public void setKyProstaffs(String kyProstaffs) {
		this.kyProstaffs = kyProstaffs;
	}

	public String getKyRegister() {
		return this.kyRegister;
	}

	public void setKyRegister(String kyRegister) {
		this.kyRegister = kyRegister;
	}

	public String getKyClass() {
		return this.kyClass;
	}

	public void setKyClass(String kyClass) {
		this.kyClass = kyClass;
	}

	public String getKyScale() {
		return this.kyScale;
	}

	public void setKyScale(String kyScale) {
		this.kyScale = kyScale;
	}

	public String getKyProgress() {
		return this.kyProgress;
	}

	public void setKyProgress(String kyProgress) {
		this.kyProgress = kyProgress;
	}

	public String getKyTarget() {
		return this.kyTarget;
	}

	public void setKyTarget(String kyTarget) {
		this.kyTarget = kyTarget;
	}

	public String getKyIdenttime() {
		return this.kyIdenttime;
	}

	public void setKyIdenttime(String kyIdenttime) {
		this.kyIdenttime = kyIdenttime;
	}

	public String getKyLevel() {
		return this.kyLevel;
	}

	public void setKyLevel(String kyLevel) {
		this.kyLevel = kyLevel;
	}

	public String getKyPrize() {
		return this.kyPrize;
	}

	public void setKyPrize(String kyPrize) {
		this.kyPrize = kyPrize;
	}
	public Short getKyHfile() {
		return kyHfile;
	}
	public void setKyHfile(Short kyHfile) {
		this.kyHfile = kyHfile;
	}

	public Short getAuditState() {
		return auditState;
	}

	public void setAuditState(Short auditState) {
		this.auditState = auditState;
	}

	public Long getAuditUid() {
		return auditUid;
	}

	public void setAuditUid(Long auditUid) {
		this.auditUid = auditUid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public WkTUser getAuditUser() {
		if(auditUser==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.auditUser=(WkTUser)userService.get(WkTUser.class, auditUid);
		}
		return auditUser;
	}

	public void setAuditUser(WkTUser auditUser) {
		this.auditUser = auditUser;
	}

}