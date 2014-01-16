package org.iti.gh.entity;

public class GhFile {
	private long fId;
	private long fxmId;
	private Integer xmType;
	private String fPath;
	private long kuid;
	private Integer fbackup;
	
	/*
	 * default constructor
	 */
	public GhFile(){
		
	}
	/*
	 * full constructor
	 */
	public GhFile(long fxmId,Integer xmType,String fPath,long kuid,Integer fbackup){
		this.fxmId=fxmId;
		this.xmType=xmType;
		this.fPath=fPath;
		this.kuid = kuid;
		this.fbackup=fbackup;
	}
	
	public long getKuid() {
		return kuid;
	}
	public void setKuid(long kuid) {
		this.kuid = kuid;
	}
	public Integer getFbackup() {
		return fbackup;
	}
	public void setFbackup(Integer fbackup) {
		this.fbackup = fbackup;
	}
	
	public GhFile(long fxmId,Integer xmType,String fPath){
		this.fxmId=fxmId;
		this.xmType=xmType;
		this.fPath=fPath;
	}
	
	public long getfId() {
		return this.fId;
	}
	public void setfId(long id) {
		this.fId = id;
	}
	public long getfxmId() {
		return this.fxmId;
	}
	public void setfxmId(long fxmId) {
		this.fxmId = fxmId;
	}
	public Integer getxmType() {
		return this.xmType;
	}
	public void setxmType(Integer xmType) {
		this.xmType = xmType;
	}
	public String getfPath() {
		return this.fPath;
	}
	public void setfPath(String path) {
		this.fPath = path;
	}
	
}
