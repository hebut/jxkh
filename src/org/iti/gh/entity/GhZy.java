package org.iti.gh.entity;
/**
 * GhZy entity. @author MyEclipse Persistence Tools
 */

public class GhZy  implements java.io.Serializable {


    // Fields    

     private String zyId;
     private String zyPid;
     private String zySubname;


    // Constructors

    /** default constructor */
    public GhZy() {
    }

	/** minimal constructor */
    public GhZy(String zyId, String zyPid) {
        this.zyId = zyId;
        this.zyPid = zyPid;
    }
    
    /** full constructor */
    public GhZy(String zyId, String zyPid, String zySubname) {
        this.zyId = zyId;
        this.zyPid = zyPid;
        this.zySubname = zySubname;
    }

   
    // Property accessors

    public String getZyId() {
        return this.zyId;
    }
    
    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public String getZyPid() {
        return this.zyPid;
    }
    
    public void setZyPid(String zyPid) {
        this.zyPid = zyPid;
    }

    public String getZySubname() {
        return this.zySubname;
    }
    
    public void setZySubname(String zySubname) {
        this.zySubname = zySubname;
    }
   








}