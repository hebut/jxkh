package org.iti.gh.entity;

import java.util.Date;

/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GH_PHASEREPORT {
	private long id;
	private String kuLid;
	private Long kuId;	
	private long proId;
	private String phRepoName;
	private String keyWord;	
	private String phRepoUser;
	private String phRepoDate;	
	private String phRepoRemark;
	private Integer phRepoState;
	private Integer SUBMIT=1,UN_SUBMIT=0;
	private String phRepoPath;
	
	/*
	 * default constructor
	 */
	public GH_PHASEREPORT(){
		
	}
	
	/*
	 * full constructor
	 */
	public GH_PHASEREPORT(long id,String kuLid,Long kuId,long proId,String phRepoName,String keyWord,String phRepoUser,String phRepoDate,
			String phRepoRemark,Integer phRepoState,String phRepoPath){
		this.id=id;
		this.kuLid=kuLid;
		this.kuId=kuId;
		this.proId=proId;
		this.phRepoName = phRepoName;
		this.keyWord = keyWord;
		this.phRepoUser=phRepoUser;
		this.phRepoDate=phRepoDate;
		this.phRepoRemark=phRepoRemark;
		this.phRepoState=phRepoState;
		this.phRepoPath=phRepoPath;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKuLid() {
		return kuLid;
	}
	public void setKuLid(String kuLid) {
		this.kuLid = kuLid;
	}
	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	public long getProId() {
		return proId;
	}
	public void setProId(long proId) {
		this.proId = proId;
	}
	public String getPhRepoName() {
		return phRepoName;
	}
	public void setPhRepoName(String phRepoName) {
		this.phRepoName = phRepoName;
	}
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getPhRepoUser() {
		return phRepoUser;
	}
	public void setPhRepoUser(String phRepoUser) {
		this.phRepoUser = phRepoUser;
	}
	public String getPhRepoDate() {
		return phRepoDate;
	}

	public void setPhRepoDate(String phRepoDate) {
		this.phRepoDate = phRepoDate;
	}
	public String getPhRepoRemark() {
		return phRepoRemark;
	}
	public void setPhRepoRemark(String phRepoRemark) {
		this.phRepoRemark = phRepoRemark;
	}
	public Integer getPhRepoState() {
		return phRepoState;
	}

	public void setPhRepoState(Integer phRepoState) {
		this.phRepoState = phRepoState;
	}
	public String getPhRepoPath() {
		return phRepoPath;
	}
	public void setPhRepoPath(String phRepoPath) {
		this.phRepoPath = phRepoPath;
	}
	
}
