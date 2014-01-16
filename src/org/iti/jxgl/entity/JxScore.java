package org.iti.jxgl.entity;

/**
 * JxScores entity. @author MyEclipse Persistence Tools
 */
public class JxScore implements java.io.Serializable {
	// Fields
	private Long jsId;
	private String jsCid;
	private String jsCname;
	private String jsThid;
	private String jsCno;
	private Float jsCredit;
	private Float jsTime;
	private Short jsType;
	private String jsSid;
	private String jsCollege;
	private String jsMajor;
	private Integer jsYear;
	private Short jsTerm;
	private Float jsScore;
	private String jsMark;
	private Long jsExamdate;
	private Long jsKeyindate;
	private Short jsState;
	private Integer jsOrder;
    public static final Integer JX_KK=-1,JX_HK=-2,JX_MX=-3;//¿õ¿¼-1£¬»º¿¼-2£¬ÃâÐÞ-3
	// Constructors
	/** default constructor */
	public JxScore() {
	}

	/** minimal constructor */
	public JxScore(Long jsId) {
		this.jsId = jsId;
	}

	/** full constructor */
	public JxScore(Long jsId, String jsCid, String jsCname, String jsThid, String jsCno, Float jsCredit, Float jsTime, Short jsType, String jsSid, String jsCollege, String jsMajor, Integer jsYear, Short jsTerm, Float jsScore, String jsMark, Long jsExamdate, Long jsKeyindate, Short jsState, Integer jsOrder) {
		this.jsId = jsId;
		this.jsCid = jsCid;
		this.jsCname = jsCname;
		this.jsThid = jsThid;
		this.jsCno = jsCno;
		this.jsCredit = jsCredit;
		this.jsTime = jsTime;
		this.jsType = jsType;
		this.jsSid = jsSid;
		this.jsCollege = jsCollege;
		this.jsMajor = jsMajor;
		this.jsYear = jsYear;
		this.jsTerm = jsTerm;
		this.jsScore = jsScore;
		this.jsMark = jsMark;
		this.jsExamdate = jsExamdate;
		this.jsKeyindate = jsKeyindate;
		this.jsState = jsState;
		this.jsOrder = jsOrder;
	}

	// Property accessors
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	public String getJsCid() {
		return this.jsCid;
	}

	public void setJsCid(String jsCid) {
		this.jsCid = jsCid;
	}

	public String getJsCname() {
		return this.jsCname;
	}

	public void setJsCname(String jsCname) {
		this.jsCname = jsCname;
	}

	public String getJsThid() {
		return this.jsThid;
	}

	public void setJsThid(String jsThid) {
		this.jsThid = jsThid;
	}

	public String getJsCno() {
		return this.jsCno;
	}

	public void setJsCno(String jsCno) {
		this.jsCno = jsCno;
	}

	public Float getJsCredit() {
		return this.jsCredit;
	}

	public void setJsCredit(Float jsCredit) {
		this.jsCredit = jsCredit;
	}

	public Float getJsTime() {
		return this.jsTime;
	}

	public void setJsTime(Float jsTime) {
		this.jsTime = jsTime;
	}

	public Short getJsType() {
		return this.jsType;
	}

	public void setJsType(Short jsType) {
		this.jsType = jsType;
	}

	public String getJsSid() {
		return this.jsSid;
	}

	public void setJsSid(String jsSid) {
		this.jsSid = jsSid;
	}

	public String getJsCollege() {
		return this.jsCollege;
	}

	public void setJsCollege(String jsCollege) {
		this.jsCollege = jsCollege;
	}

	public String getJsMajor() {
		return this.jsMajor;
	}

	public void setJsMajor(String jsMajor) {
		this.jsMajor = jsMajor;
	}

	public Integer getJsYear() {
		return this.jsYear;
	}

	public void setJsYear(Integer jsYear) {
		this.jsYear = jsYear;
	}

	public Short getJsTerm() {
		return this.jsTerm;
	}

	public void setJsTerm(Short jsTerm) {
		this.jsTerm = jsTerm;
	}

	public Float getJsScore() {
		return this.jsScore;
	}

	public void setJsScore(Float jsScore) {
		this.jsScore = jsScore;
	}

	public String getJsMark() {
		return this.jsMark;
	}

	public void setJsMark(String jsMark) {
		this.jsMark = jsMark;
	}

	public Long getJsExamdate() {
		return this.jsExamdate;
	}

	public void setJsExamdate(Long jsExamdate) {
		this.jsExamdate = jsExamdate;
	}

	public Long getJsKeyindate() {
		return this.jsKeyindate;
	}

	public void setJsKeyindate(Long jsKeyindate) {
		this.jsKeyindate = jsKeyindate;
	}

	public Short getJsState() {
		return this.jsState;
	}

	public void setJsState(Short jsState) {
		this.jsState = jsState;
	}

	public Integer getJsOrder() {
		return jsOrder;
	}

	public void setJsOrder(Integer jsOrder) {
		this.jsOrder = jsOrder;
	}
}