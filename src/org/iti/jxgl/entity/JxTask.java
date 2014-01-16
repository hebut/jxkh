package org.iti.jxgl.entity;

/**
 * JxTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class JxTask implements java.io.Serializable {
	// Fields
	private Long jtId;
	private Long jeId;
	private Long jcId;
	private String jtYear;
	private Short jtTerm;
	private Long kdId;
	private Long jtXh;
	private Short jtTesttype;
	private String jtWeek;
	private String jtWeeks;
	private String jtClass;
	private Integer jtClassnum;
	private Integer jtSumstate;
	private String jtCampus;
	private String jtMark;
	private String thId;
	private String thName;
	private Long kuId;

	public String getThName() {
		return thName;
	}

	public void setThName(String thName) {
		this.thName = thName;
	}

	private Float jtKnweek;

	// Constructors
	public Float getJtKnweek() {
		return jtKnweek;
	}

	public void setJtKnweek(Float jtKnweek) {
		this.jtKnweek = jtKnweek;
	}

	/** default constructor */
	public JxTask() {
	}

	/** minimal constructor */
	public JxTask(Long jtId) {
		this.jtId = jtId;
	}

	/** full constructor */
	public JxTask(Long jtId, Long jeId, Long jcId, String jtYear, Short jtTerm, Long kdId, Long jtXh, Short jtTesttype, String jtWeek, String jtWeeks, String jtClass, Integer jtClassnum, Integer jtSumstate, String jtCampus, String jtMark, String thId, String thName, Float jtKnweek, Long kuId) {
		this.jtId = jtId;
		this.jeId = jeId;
		this.jcId = jcId;
		this.jtYear = jtYear;
		this.jtTerm = jtTerm;
		this.kdId = kdId;
		this.jtXh = jtXh;
		this.jtTesttype = jtTesttype;
		this.jtWeek = jtWeek;
		this.jtWeeks = jtWeeks;
		this.jtClass = jtClass;
		this.jtClassnum = jtClassnum;
		this.jtSumstate = jtSumstate;
		this.jtCampus = jtCampus;
		this.jtMark = jtMark;
		this.thId = thId;
		this.thName = thName;
		this.jtKnweek = jtKnweek;
		this.kuId = kuId;
	}

	public Long getJtId() {
		return jtId;
	}

	public void setJtId(Long jtId) {
		this.jtId = jtId;
	}

	public Long getJeId() {
		return jeId;
	}

	public void setJeId(Long jeId) {
		this.jeId = jeId;
	}

	public Long getJcId() {
		return jcId;
	}

	public void setJcId(Long jcId) {
		this.jcId = jcId;
	}

	public String getJtYear() {
		return jtYear;
	}

	public void setJtYear(String jtYear) {
		this.jtYear = jtYear;
	}

	public Short getJtTerm() {
		return jtTerm;
	}

	public void setJtTerm(Short jtTerm) {
		this.jtTerm = jtTerm;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getJtXh() {
		return jtXh;
	}

	public void setJtXh(Long jtXh) {
		this.jtXh = jtXh;
	}

	public Short getJtTesttype() {
		return jtTesttype;
	}

	public void setJtTesttype(Short jtTesttype) {
		this.jtTesttype = jtTesttype;
	}

	public String getJtWeek() {
		return jtWeek;
	}

	public void setJtWeek(String jtWeek) {
		this.jtWeek = jtWeek;
	}

	public String getJtWeeks() {
		return jtWeeks;
	}

	public void setJtWeeks(String jtWeeks) {
		this.jtWeeks = jtWeeks;
	}

	public String getJtClass() {
		return jtClass;
	}

	public void setJtClass(String jtClass) {
		this.jtClass = jtClass;
	}

	public Integer getJtClassnum() {
		return jtClassnum;
	}

	public void setJtClassnum(Integer jtClassnum) {
		this.jtClassnum = jtClassnum;
	}

	public Integer getJtSumstate() {
		return jtSumstate;
	}

	public void setJtSumstate(Integer jtSumstate) {
		this.jtSumstate = jtSumstate;
	}

	public String getJtCampus() {
		return jtCampus;
	}

	public void setJtCampus(String jtCampus) {
		this.jtCampus = jtCampus;
	}

	public String getJtMark() {
		return jtMark;
	}

	public void setJtMark(String jtMark) {
		this.jtMark = jtMark;
	}

	public String getThId() {
		return thId;
	}

	public void setThId(String thId) {
		this.thId = thId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
}