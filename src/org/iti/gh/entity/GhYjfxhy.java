package org.iti.gh.entity;



/**
 * GhYjfxhy entity. @author MyEclipse Persistence Tools
 */

public class GhYjfxhy  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] TYPE={"国际会议","国内会议"};
	public static final String[] SF={"主办","承办","参加"};
	private Long ghId;
     private Long kuId;
     private Long gyId;
     private Short ghType;
     private Short ghSf;
     private Integer ghYear;
     private String ghContent;


    // Constructors

    /** default constructor */
    public GhYjfxhy() {
    }

	/** minimal constructor */
    public GhYjfxhy(Long ghId) {
        this.ghId = ghId;
    }
    
    /** full constructor */
    public GhYjfxhy(Long ghId, Long kuId, Long gyId, Short ghType, Short ghSf, Integer ghYear, String ghContent) {
        this.ghId = ghId;
        this.kuId = kuId;
        this.gyId = gyId;
        this.ghType = ghType;
        this.ghSf = ghSf;
        this.ghYear = ghYear;
        this.ghContent = ghContent;
    }

   
    // Property accessors

    public Long getGhId() {
        return this.ghId;
    }
    
    public void setGhId(Long ghId) {
        this.ghId = ghId;
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

    public Short getGhType() {
        return this.ghType;
    }
    
    public void setGhType(Short ghType) {
        this.ghType = ghType;
    }

    public Short getGhSf() {
        return this.ghSf;
    }
    
    public void setGhSf(Short ghSf) {
        this.ghSf = ghSf;
    }

    public Integer getGhYear() {
        return this.ghYear;
    }
    
    public void setGhYear(Integer ghYear) {
        this.ghYear = ghYear;
    }

    public String getGhContent() {
        return this.ghContent;
    }
    
    public void setGhContent(String ghContent) {
        this.ghContent = ghContent;
    }
   








}