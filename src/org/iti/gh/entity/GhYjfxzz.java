package org.iti.gh.entity;



/**
 * GhYjfxzz entity. @author MyEclipse Persistence Tools
 */

public class GhYjfxzz  implements java.io.Serializable {


    // Fields    

     private Long gzId;
     private Long kuId;
     private Long gyId;
     private String gzContent;
     private Short gzType;
     private Integer gzYear;


    // Constructors

    /** default constructor */
    public GhYjfxzz() {
    }

	/** minimal constructor */
    public GhYjfxzz(Long gzId) {
        this.gzId = gzId;
    }
    
    /** full constructor */
    public GhYjfxzz(Long gzId, Long kuId, Long gyId, String gzContent, Short gzType, Integer gzYear) {
        this.gzId = gzId;
        this.kuId = kuId;
        this.gyId = gyId;
        this.gzContent = gzContent;
        this.gzType = gzType;
        this.gzYear = gzYear;
    }

   
    // Property accessors

    public Long getGzId() {
        return this.gzId;
    }
    
    public void setGzId(Long gzId) {
        this.gzId = gzId;
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

    public String getGzContent() {
        return this.gzContent;
    }
    
    public void setGzContent(String gzContent) {
        this.gzContent = gzContent;
    }

    public Short getGzType() {
        return this.gzType;
    }
    
    public void setGzType(Short gzType) {
        this.gzType = gzType;
    }

    public Integer getGzYear() {
        return this.gzYear;
    }
    
    public void setGzYear(Integer gzYear) {
        this.gzYear = gzYear;
    }
   








}