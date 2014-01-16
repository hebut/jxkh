package org.iti.bysj.entity;


import org.iti.bysj.service.NsourceService;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.basehs.util.BeanFactory;

/**
 * BsProject entity. @author MyEclipse Persistence Tools
 */

public class BsProject implements java.io.Serializable {

	// Fields

	public static final Short STATE_YES=2;
	public static final Short STATE_INVALID=3;
	public static final Short STATE_NONE=0;
	public static final Short STATE_NO=1;
	
	public static final Short TYPE_STU=1;
	public static final Short TYPE_XW=2;
	public static final Short TYPE_TEA=3;
	public static final String[] WORKLOAD={"大","适中","小"};
	public static final String[] DIFF={"难","一般","易"};
	public static final Short EDIT_YES=1;
	public static final Short EDIT_No=0;
	public static final Short IFAGREE_NO=0,IFAGREE_YSE=1;
	
	private Long bprId;
	private String bprName;
	private String bprKeyword;
	private Long bprNature;
	private Long bprSource;
	private String bprContent;
	private String bprReq;
	private Long bprUid;
	private String bprAppreviews;
	private Short bprState;
	private Long buId;
	private String bprSkill;
	private Short bprType;
	private Short brpIfagree;
	private Long bbId;
	private Short bprIfedit;
	private Long bprEdittime;
	private Long bprTime;
	private Integer bprWorkload;
	private Integer bprDiffculty;
	private Long xwKuid;
	private Long xwKdid;
	private Long xwadminKuid;
	private Short xwState;
	
	
//	private String bprXwtnuit;
	private String bprXwpreason;
//	private String bprXwttel;
//	private String bprXwtxl;
//	private String bprXwtposition;

	private BsNsource nature;
	private BsNsource source;
	public BsNsource getNature() {
		NsourceService nsourceService=(NsourceService)BeanFactory.getBean("nsourceService");
		return (BsNsource)nsourceService.get(BsNsource.class, bprNature);
	}
	
	public BsNsource getSource() {
		NsourceService nsourceService=(NsourceService)BeanFactory.getBean("nsourceService");
		return (BsNsource)nsourceService.get(BsNsource.class, bprSource);
	}

	// Constructors

	/** default constructor */
	public BsProject() {
		this.bprState=STATE_NONE;
		this.brpIfagree=IFAGREE_NO;
		this.bprIfedit=EDIT_No;
	}

	/** minimal constructor */
	public BsProject(Long bprId, String bprName, String bprKeyword,
			Long bprNature, Long bprSource, String bprContent, String bprReq,
			Long bprUid, Long buId, String bprSkill, Short bprType,
			Short brpIfagree, Long bbId, Short bprIfedit, Long bprEdittime,
			Long bprTime, Integer bprDiffculty,Long xwKuid ,Long xwKdid ,Long xwadminKuid) {
		this.bprId = bprId;
		this.bprName = bprName;
		this.bprKeyword = bprKeyword;
		this.bprNature = bprNature;
		this.bprSource = bprSource;
		this.bprContent = bprContent;
		this.bprReq = bprReq;
		this.bprUid = bprUid;
		this.buId = buId;
		this.bprSkill = bprSkill;
		this.bprType = bprType;
		this.brpIfagree = brpIfagree;
		this.bbId = bbId;
		this.bprIfedit = bprIfedit;
		this.bprEdittime = bprEdittime;
		this.bprTime = bprTime;
		this.bprDiffculty = bprDiffculty;
		this.xwadminKuid=xwadminKuid;
		this.xwKdid=xwKdid;
		this.xwKuid=xwKuid;
	}

	/** full constructor */
	public BsProject(Long bprId, String bprName, String bprKeyword,
			Long bprNature, Long bprSource, String bprContent, String bprReq,
			Long bprUid, String bprAppreviews, Short bprState, Long buId,
			String bprSkill, Short bprType, Short brpIfagree, Long bbId,
			Short bprIfedit, Long bprEdittime, Long bprTime,
			Integer bprWorkload, Integer bprDiffculty,
			String bprXwpreason,Long xwKuid ,Long xwKdid ,Long xwadminKuid , Short xwState) {
		this.bprId = bprId;
		this.bprName = bprName;
		this.bprKeyword = bprKeyword;
		this.bprNature = bprNature;
		this.bprSource = bprSource;
		this.bprContent = bprContent;
		this.bprReq = bprReq;
		this.bprUid = bprUid;
		this.bprAppreviews = bprAppreviews;
		this.bprState = bprState;
		this.buId = buId;
		this.bprSkill = bprSkill;
		this.bprType = bprType;
		this.brpIfagree = brpIfagree;
		this.bbId = bbId;
		this.bprIfedit = bprIfedit;
		this.bprEdittime = bprEdittime;
		this.bprTime = bprTime;
		this.bprWorkload = bprWorkload;
		this.bprDiffculty = bprDiffculty;
		this.xwadminKuid=xwadminKuid;
		this.xwKdid=xwKdid;
		this.xwKuid=xwKuid;
		//this.bprXwtname = bprXwtname;
//		this.bprXwtnuit = bprXwtnuit;
		this.bprXwpreason = bprXwpreason;
		this.xwState=xwState;
		
//		this.bprXwttel = bprXwttel;
//		this.bprXwtxl = bprXwtxl;
//		this.bprXwtposition = bprXwtposition;
	}

	// Property accessors

	public Long getBprId() {
		return this.bprId;
	}

	public void setBprId(Long bprId) {
		this.bprId = bprId;
	}

	public String getBprName() {
		return this.bprName;
	}

	public void setBprName(String bprName) {
		this.bprName = bprName;
	}

	public String getBprKeyword() {
		return this.bprKeyword;
	}

	public void setBprKeyword(String bprKeyword) {
		this.bprKeyword = bprKeyword;
	}

	public Long getBprNature() {
		return this.bprNature;
	}

	public void setBprNature(Long bprNature) {
		this.bprNature = bprNature;
	}

	public Long getBprSource() {
		return this.bprSource;
	}

	public void setBprSource(Long bprSource) {
		this.bprSource = bprSource;
	}

	public String getBprContent() {
		return this.bprContent;
	}

	public void setBprContent(String bprContent) {
		this.bprContent = bprContent;
	}

	public String getBprReq() {
		return this.bprReq;
	}

	public void setBprReq(String bprReq) {
		this.bprReq = bprReq;
	}

	public Long getBprUid() {
		return this.bprUid;
	}

	public void setBprUid(Long bprUid) {
		this.bprUid = bprUid;
	}

	public String getBprAppreviews() {
		return this.bprAppreviews;
	}

	public void setBprAppreviews(String bprAppreviews) {
		this.bprAppreviews = bprAppreviews;
	}

	public Short getBprState() {
		return this.bprState;
	}

	public void setBprState(Short bprState) {
		this.bprState = bprState;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public String getBprSkill() {
		return this.bprSkill;
	}

	public void setBprSkill(String bprSkill) {
		this.bprSkill = bprSkill;
	}

	public Short getBprType() {
		return this.bprType;
	}

	public void setBprType(Short bprType) {
		this.bprType = bprType;
	}

	public Short getBrpIfagree() {
		return this.brpIfagree;
	}

	public void setBrpIfagree(Short brpIfagree) {
		this.brpIfagree = brpIfagree;
	}

	public Long getBbId() {
		return this.bbId;
	}

	public void setBbId(Long bbId) {
		this.bbId = bbId;
	}

	public Short getBprIfedit() {
		return this.bprIfedit;
	}

	public void setBprIfedit(Short bprIfedit) {
		this.bprIfedit = bprIfedit;
	}

	public Long getBprEdittime() {
		return this.bprEdittime;
	}

	public void setBprEdittime(Long bprEdittime) {
		this.bprEdittime = bprEdittime;
	}

	public Long getBprTime() {
		return this.bprTime;
	}

	public void setBprTime(Long bprTime) {
		this.bprTime = bprTime;
	}

	public Integer getBprWorkload() {
		return this.bprWorkload;
	}

	public void setBprWorkload(Integer bprWorkload) {
		this.bprWorkload = bprWorkload;
	}

	public Integer getBprDiffculty() {
		return this.bprDiffculty;
	}

	public void setBprDiffculty(Integer bprDiffculty) {
		this.bprDiffculty = bprDiffculty;
	}

	public Long getXwKuid() {
		return xwKuid;
	}

	public void setXwKuid(Long xwKuid) {
		this.xwKuid = xwKuid;
	}

	public Long getXwKdid() {
		return xwKdid;
	}

	public void setXwKdid(Long xwKdid) {
		this.xwKdid = xwKdid;
	}

	public Long getXwadminKuid() {
		return xwadminKuid;
	}

	public void setXwadminKuid(Long xwadminKuid) {
		this.xwadminKuid = xwadminKuid;
	}

//	public String getBprXwtname() {
//		return this.bprXwtname;
//	}
//
//	public void setBprXwtname(String bprXwtname) {
//		this.bprXwtname = bprXwtname;
//	}
//
//	public String getBprXwtnuit() {
//		return this.bprXwtnuit;
//	}
//
//	public void setBprXwtnuit(String bprXwtnuit) {
//		this.bprXwtnuit = bprXwtnuit;
//	}
//
	public String getBprXwpreason() {
		return this.bprXwpreason;
	}

	public void setBprXwpreason(String bprXwpreason) {
		this.bprXwpreason = bprXwpreason;
	}
//
//	public String getBprXwttel() {
//		return this.bprXwttel;
//	}
//
//	public void setBprXwttel(String bprXwttel) {
//		this.bprXwttel = bprXwttel;
//	}
//
//	public String getBprXwtxl() {
//		return this.bprXwtxl;
//	}
//
//	public void setBprXwtxl(String bprXwtxl) {
//		this.bprXwtxl = bprXwtxl;
//	}
//
//	public String getBprXwtposition() {
//		return this.bprXwtposition;
//	}
//
//	public void setBprXwtposition(String bprXwtposition) {
//		this.bprXwtposition = bprXwtposition;
//	}

	public Short getXwState() {
		return xwState;
	}

	public void setXwState(Short xwState) {
		this.xwState = xwState;
	}

	

}