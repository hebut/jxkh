package com.uniwin.asm.personal.entity;

import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.basehs.util.BeanFactory;

public class DocTree implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BASE_FILE = "/upload/doc";
	private Long dtId;
	private String dtName;
	private Long dtpId;
	private String dtBz;
	private int totleDocument;
	private Long dtKuid;
	private BaseService baseService;
	private Long totleSize;
	public Long getTotleSize() {
		return totleSize;
	}
	public void setTotleSize(Long totleSize) {
		this.totleSize = totleSize;
	}
	public DocTree(){
	}
	public List<DocList> getDocList(){
		if(baseService==null){
			synchronized (this) {
				if(baseService==null){
					baseService=(BaseService) BeanFactory.getBean("baseService");
				}
			}
		}
		String query="from DocList as model where model.dtId = ? ";
		return (List<DocList>) baseService.find(query, new Object[]{dtId});
	}
	public DocTree(Long dtId, String dtName, Long dtpId, String dtBz,
			int totleDocument) {
		super();
		this.dtId = dtId;
		this.dtName = dtName;
		this.dtpId = dtpId;
		this.dtBz = dtBz;
		this.totleDocument = totleDocument;
	}
	public Long getDtKuid() {
		return dtKuid;
	}

	public void setDtKuid(Long dtKuid) {
		this.dtKuid = dtKuid;
	}

	public Long getDtId() {
		return dtId;
	}
	public void setDtId(Long dtId) {
		this.dtId = dtId;
	}
	public String getDtName() {
		return dtName;
	}
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}
	public Long getDtpId() {
		return dtpId;
	}
	public void setDtpId(Long dtpId) {
		this.dtpId = dtpId;
	}
	public String getDtBz() {
		return dtBz;
	}
	public void setDtBz(String dtBz) {
		this.dtBz = dtBz;
	}
	public int getTotleDocument() {
		return totleDocument;
	}
	public void setTotleDocument(int totleDocument) {
		this.totleDocument = totleDocument;
	}
	
}
