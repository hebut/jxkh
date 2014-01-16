package org.iti.gh.entity;
/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GH_ARCHIVECOMMENT {
	private long id;
	private long arId;
	private long commUserId;
	private String commUserName;
	private String commUserMajor;
	private String commDate;
	private String commContext;
	
	/*
	 * default constructor
	 */
	public GH_ARCHIVECOMMENT(){
		
	}
	/*
	 * full constructor
	 */
	public GH_ARCHIVECOMMENT(long id,long arId,long commUserId,String commUserName,String commUserMajor,
			String commDate,String commContext){
		this.id = id;
		this.arId =arId;
		this.commUserId=commUserId;
		this.commUserName=commUserName;
		this.commUserMajor=commUserMajor;
		this.commDate=commDate;
		this.commContext=commContext;		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getArId() {
		return arId;
	}
	public void setArId(long arId) {
		this.arId = arId;
	}
	public long getCommUserId() {
		return commUserId;
	}
	public void setCommUserId(long commUserId) {
		this.commUserId = commUserId;
	}
	public String getCommUserName() {
		return commUserName;
	}
	public void setCommUserName(String commUserName) {
		this.commUserName = commUserName;
	}
	public String getCommUserMajor() {
		return commUserMajor;
	}
	public void setCommUserMajor(String commUserMajor) {
		this.commUserMajor = commUserMajor;
	}
	public String getCommDate() {
		return commDate;
	}
	public void setCommDate(String commDate) {
		this.commDate = commDate;
	}
	public String getCommContext() {
		return commContext;
	}
	public void setCommContext(String commContext) {
		this.commContext = commContext;
	}
	
}
