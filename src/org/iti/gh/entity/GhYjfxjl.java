package org.iti.gh.entity;



/**
 * GhYjfxjl entity. @author MyEclipse Persistence Tools
 */

public class GhYjfxjl  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] JB={"国家级","省部级"};
	public static final String[] DJ={"特等奖","一等奖","二等奖","三等奖","优秀奖","纪念奖","参与奖"};
	public static final String[] Type={"科研","教学"};
	 private Long gjId;
     private Long kuId;
     private Long gyId;
     private Short gjJb;
  
	private Short gjDj;
     private Integer gjNum;
     private Integer gjYear;
     private String gjContent;
     private Short gjType;


    // Constructors

    /** default constructor */
    public GhYjfxjl() {
    }

	/** minimal constructor */
    public GhYjfxjl(Long gjId) {
        this.gjId = gjId;
    }
    
    /** full constructor */
    public GhYjfxjl(Long gjId, Long kuId, Long gyId, Short gjJb,Short gjDj, Integer gjNum, Integer gjYear, String gjContent, Short gjType) {
        this.gjId = gjId;
        this.kuId = kuId;
        this.gyId = gyId;
        this.gjJb = gjJb;
        this.gjDj = gjDj;
        this.gjNum = gjNum;
        this.gjYear = gjYear;
        this.gjContent = gjContent;
        this.gjType = gjType;
    }

   
    // Property accessors
    public Short getGjJb() {
		return gjJb;
	}

	public void setGjJb(Short gjJb) {
		this.gjJb = gjJb;
	}

    public Long getGjId() {
        return this.gjId;
    }
    
    public void setGjId(Long gjId) {
        this.gjId = gjId;
    }

    public Long getKuId() {
        return this.kuId;
    }
    
    public void setKuId(Long kuId) {
        this.kuId = kuId;
    }

    public Long getGyId() {
        return this.gyId;
    }
    
    public void setGyId(Long gyId) {
        this.gyId = gyId;
    }

    public Short getGjDj() {
        return this.gjDj;
    }
    
    public void setGjDj(Short gjDj) {
        this.gjDj = gjDj;
    }

    public Integer getGjNum() {
        return this.gjNum;
    }
    
    public void setGjNum(Integer gjNum) {
        this.gjNum = gjNum;
    }

    public Integer getGjYear() {
        return this.gjYear;
    }
    
    public void setGjYear(Integer gjYear) {
        this.gjYear = gjYear;
    }

    public String getGjContent() {
        return this.gjContent;
    }
    
    public void setGjContent(String gjContent) {
        this.gjContent = gjContent;
    }

    public Short getGjType() {
        return this.gjType;
    }
    
    public void setGjType(Short gjType) {
        this.gjType = gjType;
    }
   








}