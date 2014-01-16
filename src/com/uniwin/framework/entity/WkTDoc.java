package com.uniwin.framework.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WkTDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	public static int ULevel=0;//校级
	public static int CLevel=1;//院级
	public static int DLevel=2;//系级别
	public static final String BASE_FILE = "/upload/doc";
	private long docId;
	private long docKdid;
	private String docName;
	private String docInfo;
	private Long docSize;
	private Long docKuid;
	private String docKuname;
	private Long doctime;
	private int docLevel;
	private String docPath;
	
	public String getDocKuname() {
		return docKuname;
	}
	public void setDocKuname(String docKuname) {
		this.docKuname = docKuname;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public int getDocLevel() {
		return docLevel;
	}
	public void setDocLevel(int docLevel) {
		this.docLevel = docLevel;
	}
	
	public Long getDoctime() {
		return doctime;
	}
	public void setDoctime(Long doctime) {
		this.doctime = doctime;
	}
	public Long getDocKuid() {
		return docKuid;
	}
	public void setDocKuid(Long docKuid) {
		this.docKuid = docKuid;
	}
	private Set<WkTRole> wktroles=new HashSet<WkTRole>();
	private WkTDocType wktdoctype;
	public WkTDocType getWktdoctype() {
		return wktdoctype;
	}
	public void setWktdoctype(WkTDocType wktdoctype) {
		this.wktdoctype = wktdoctype;
	}
	public Set<WkTRole> getWktroles() {
		return wktroles;
	}
	public void setWktroles(Set<WkTRole> wktroles) {
		this.wktroles = wktroles;
	}
	public WkTDoc() {
	}

	public WkTDoc(long docId, long docKdid, String docName, String docInfo,
			Long docSize, Long docKuid, Long doctime, Set<WkTRole> wktroles,
			WkTDocType wktdoctype) {
		super();
		this.docId = docId;
		this.docKdid = docKdid;
		this.docName = docName;
		this.docInfo = docInfo;
		this.docSize = docSize;
		this.docKuid = docKuid;
		this.doctime = doctime;
		this.wktroles = wktroles;
		this.wktdoctype = wktdoctype;
	}
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public long getDocKdid() {
		return docKdid;
	}
	public void setDocKdid(long docKdid) {
		this.docKdid = docKdid;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocInfo() {
		return docInfo;
	}
	public void setDocInfo(String docInfo) {
		this.docInfo = docInfo;
	}
	public Long getDocSize() {
		return docSize;
	}
	public void setDocSize(Long docSize) {
		this.docSize = docSize;
	}
	
}
