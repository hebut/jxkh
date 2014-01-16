package org.iti.gh.entity;
/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GH_PROJECTSOURCE {
	private long psId;
	private String psName;
	
	/*
	 * default constructor
	 */
	public GH_PROJECTSOURCE(){
		
	}
	/*
	 * full constructor
	 */
	public GH_PROJECTSOURCE(long psId,String psName){
		this.psId = psId;
		this.psName = psName;
	}
	public long getPsId() {
		return psId;
	}
	public void setPsId(long psId) {
		this.psId = psId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
}
