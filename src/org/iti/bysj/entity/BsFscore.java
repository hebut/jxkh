package org.iti.bysj.entity;

/**
 * BsFscore entity. @author MyEclipse Persistence Tools
 */

public class BsFscore implements java.io.Serializable {

	// Field
	public static final Short LEVEL_NONE=0, LEVEL_ONE=1, LEVEL_TWO=2, LEVEL_THREE=3, LEVEL_FOUR=4, LEVEL_FIVE=5;
	public static final String[] CGCJ={"暂无","优秀","良好","中等","及格","不及格"};
	
	public static final Short DBEDIT_NONE=0,DBEDIT_TJ=1,DBEDIT_ZC=2;
	public static final Short TWODBEDIT_NONE=0,TWODBEDIT_TJ=1,TWODBEDIT_ZC=2;
	public static final Short TEDIT_NONE=0,TEDIT_TJ=1,TEDIT_ZC=2;
	public static final Double CjNone=0D;
	private Long bsId;
	private Long buId;
	private Double bfsTscore;
	private Double bfsReview;
	private Double bfsReply;
	private Double bfsFruit;
	private String bfsRecord;
	private String bfsAdvice;
	private String bfsWriter;
	private Short bfsFruitlevel;
	private String bfsTadvise;
	private Short bfsTedit;
	private Short bfsDbedit;
	
	private Short bfsTwodbedit;
	private String bfsTworecord;
	private String bfsTwoadvice;
	private Double bfsTworeply;
	private String bfsTwowriter;
	// Constructors

	public String getBfsTwowriter() {
		return bfsTwowriter;
	}

	public void setBfsTwowriter(String bfsTwowriter) {
		this.bfsTwowriter = bfsTwowriter;
	}

	/** default constructor */
	public BsFscore() {
		this.bfsTscore=CjNone;//无指导教师评阅成绩
		this.bfsReview=CjNone;//无同行评阅成绩
		this.bfsFruit=CjNone;//无成果成绩
		this.bfsReply=CjNone;//无答辩成绩
		this.bfsTworeply=CjNone;//无二辩答辩成绩
		this.bfsFruitlevel=LEVEL_NONE;//未计算成果，无成绩等级
		this.bfsDbedit=DBEDIT_NONE;//答辩成绩修改权限，不可以修改
		this.bfsTwodbedit=TWODBEDIT_NONE;//二辩成绩修改权限，不可以修改
		this.bfsTedit=TEDIT_NONE;//导师成绩修改权限
	}

	/** minimal constructor */
	public BsFscore(Long bsId, Long buId, Double bfsTscore,
			Double bfsReview, Double bfsReply, Double bfsFruit,
			Short bfsFruitlevel, Short bfsTedit, 
			Short bfsDbedit,Double bfsTworeply,Short bfsTwodbedit) {		
		this.bsId = bsId;
		this.buId = buId;
		this.bfsTscore = bfsTscore;
		this.bfsReview = bfsReview;
		this.bfsReply = bfsReply;
		this.bfsFruit = bfsFruit;
		this.bfsFruitlevel = bfsFruitlevel;
		this.bfsTedit = bfsTedit;
		this.bfsDbedit = bfsDbedit;
		this.bfsTworeply=bfsTworeply;
		this.bfsTwodbedit=bfsTwodbedit;
	}

	

	// Property accessors

	public BsFscore(Long bsId, Long buId, Double bfsTscore,
			Double bfsReview, Double bfsReply, Double bfsFruit,
			String bfsRecord, String bfsAdvice, String bfsWriter,
			Short bfsFruitlevel, String bfsTadvise, Short bfsTedit,
			Short bfsDbedit, String bfsTwoecord,
			String bfsTwoadvice, Double bfsTworeply, String bfsTwowriter,Short bfsTwodbedit) {
		super();		
		this.bsId = bsId;
		this.buId = buId;
		this.bfsTscore = bfsTscore;
		this.bfsReview = bfsReview;
		this.bfsReply = bfsReply;
		this.bfsFruit = bfsFruit;
		this.bfsRecord = bfsRecord;
		this.bfsAdvice = bfsAdvice;
		this.bfsWriter = bfsWriter;
		this.bfsFruitlevel = bfsFruitlevel;
		this.bfsTadvise = bfsTadvise;
		this.bfsTedit = bfsTedit;
		this.bfsDbedit = bfsDbedit;
		this.bfsTworecord = bfsTwoecord;
		this.bfsTwoadvice = bfsTwoadvice;
		this.bfsTworeply = bfsTworeply;
		this.bfsTwowriter = bfsTwowriter;
		this.bfsTwodbedit=bfsTwodbedit;
	}

	public Short getBfsTwodbedit() {
		return bfsTwodbedit;
	}

	public void setBfsTwodbedit(Short bfsTwodbedit) {
		this.bfsTwodbedit = bfsTwodbedit;
	}

	public Long getBsId() {
		return this.bsId;
	}

	public void setBsId(Long bsId) {
		this.bsId = bsId;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Double getBfsTscore() {
		return this.bfsTscore;
	}

	public void setBfsTscore(Double bfsTscore) {
		this.bfsTscore = bfsTscore;
	}

	public Double getBfsReview() {
		return this.bfsReview;
	}

	public void setBfsReview(Double bfsReview) {
		this.bfsReview = bfsReview;
	}

 

	public Double getBfsFruit() {
		return this.bfsFruit;
	}

	public void setBfsFruit(Double bfsFruit) {
		this.bfsFruit = bfsFruit;
	}

	public String getBfsRecord() {
		return this.bfsRecord;
	}

	public void setBfsRecord(String bfsRecord) {
		this.bfsRecord = bfsRecord;
	}

	public String getBfsAdvice() {
		return this.bfsAdvice;
	}

	public void setBfsAdvice(String bfsAdvice) {
		this.bfsAdvice = bfsAdvice;
	}

	public String getBfsWriter() {
		return this.bfsWriter;
	}

	public void setBfsWriter(String bfsWriter) {
		this.bfsWriter = bfsWriter;
	}

	public Short getBfsFruitlevel() {
		return this.bfsFruitlevel;
	}

	public void setBfsFruitlevel(Short bfsFruitlevel) {
		this.bfsFruitlevel = bfsFruitlevel;
	}

	public String getBfsTadvise() {
		return this.bfsTadvise;
	}

	public void setBfsTadvise(String bfsTadvise) {
		this.bfsTadvise = bfsTadvise;
	}

	public Short getBfsTedit() {
		return this.bfsTedit;
	}

	public void setBfsTedit(Short bfsTedit) {
		this.bfsTedit = bfsTedit;
	}

	public Short getBfsDbedit() {
		return this.bfsDbedit;
	}

	public void setBfsDbedit(Short bfsDbedit) {
		this.bfsDbedit = bfsDbedit;
	}

	public Double getBfsReply() {
		return bfsReply;
	}

	public void setBfsReply(Double bfsReply) {
		this.bfsReply = bfsReply;
	}

	public String getBfsTworecord() {
		return bfsTworecord;
	}

	public void setBfsTworecord(String bfsTworecord) {
		this.bfsTworecord = bfsTworecord;
	}

	public String getBfsTwoadvice() {
		return bfsTwoadvice;
	}

	public void setBfsTwoadvice(String bfsTwoadvice) {
		this.bfsTwoadvice = bfsTwoadvice;
	}

	public Double getBfsTworeply() {
		return bfsTworeply;
	}

	public void setBfsTworeply(Double bfsTwoReply) {
		this.bfsTworeply = bfsTwoReply;
	}
 

}