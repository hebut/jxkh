package org.iti.gh.entity;

/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GH_PROMEMBER {
	private long id;
	private String kuId;
	private long proId;
	private String memberName;
	private Integer contribBank;
	private String taskDesc;
	
	/*
	 * default constructor
	 */
	public GH_PROMEMBER(){
		
	}
	/*
	 * full constructor
	 */
	public GH_PROMEMBER(long id,String kuId,long proId,String memberName,Integer contribBank,String taskDesc){
		this.id=id;
		this.kuId=kuId;
		this.proId=proId;
		this.memberName = memberName;
		this.contribBank=contribBank;
		this.taskDesc=taskDesc;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKuId() {
		return kuId;
	}
	public void setKuId(String kuId) {
		this.kuId = kuId;
	}
	public long getProId() {
		return proId;
	}
	public void setProId(long proId) {
		this.proId = proId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getContribBank() {
		return contribBank;
	}
	public void setContribBank(Integer contribBank) {
		this.contribBank = contribBank;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	
}
