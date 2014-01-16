package org.iti.gh.entity;



/**
 * GhYjfxlw entity. @author MyEclipse Persistence Tools
 */

public class GhYjfxlw  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] TYPE={"SCI","EI","ISTP","ºËÐÄ"};
	private Long glId;
     private Long kuId;
     private Short glType;
     private Integer glNum;
     private Integer glYear;
     private String glContent;
     private Long gyId;


    // Constructors

    /** default constructor */
    public GhYjfxlw() {
    }

	/** minimal constructor */
    public GhYjfxlw(Long glId) {
        this.glId = glId;
    }
    
    /** full constructor */
    public GhYjfxlw(Long glId, Long kuId, Short glType, Integer glNum, Integer glYear, String glContent, Long gyId) {
        this.glId = glId;
        this.kuId = kuId;
        this.glType = glType;
        this.glNum = glNum;
        this.glYear = glYear;
        this.glContent = glContent;
        this.gyId = gyId;
    }

   
    // Property accessors

    public Long getGlId() {
        return this.glId;
    }
    
    public void setGlId(Long glId) {
        this.glId = glId;
    }

    public Long getKuId() {
        return this.kuId;
    }
    
    public void setKuId(Long kuId) {
        this.kuId = kuId;
    }

    public Short getGlType() {
        return this.glType;
    }
    
    public void setGlType(Short glType) {
        this.glType = glType;
    }

    public Integer getGlNum() {
        return this.glNum;
    }
    
    public void setGlNum(Integer glNum) {
        this.glNum = glNum;
    }

    public Integer getGlYear() {
        return this.glYear;
    }
    
    public void setGlYear(Integer glYear) {
        this.glYear = glYear;
    }

    public String getGlContent() {
        return this.glContent;
    }
    
    public void setGlContent(String glContent) {
        this.glContent = glContent;
    }

    public Long getGyId() {
        return this.gyId;
    }
    
    public void setGyId(Long gyId) {
        this.gyId = gyId;
    }
   








}