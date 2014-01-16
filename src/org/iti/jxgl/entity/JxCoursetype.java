package org.iti.jxgl.entity;


/**
 * JxCoursetype entity. @author MyEclipse Persistence Tools
 */

public class JxCoursetype implements java.io.Serializable {

	// Fields

	private String jctId;//���
	private String jctName;//�������
	public Integer getJctOrder() {
		return jctOrder;
	}

	public void setJctOrder(Integer jctOrder) {
		this.jctOrder = jctOrder;
	}

	private String jctFid;//�������
	private Integer jctOrder;//�����

	// Constructors

	/** default constructor */
	public JxCoursetype() {
	}

	/** minimal constructor */
	public JxCoursetype(String jctId) {
		this.jctId = jctId;
	}

	/** full constructor */
	public JxCoursetype(String jctId, String jctName, String jctFid,Integer jctOrder) {
		this.jctId = jctId;
		this.jctName = jctName;
		this.jctFid = jctFid;
		this.jctOrder = jctOrder;
	}

	// Property accessors

	public String getJctId() {
		return this.jctId;
	}

	public void setJctId(String jctId) {
		this.jctId = jctId;
	}

	public String getJctName() {
		return this.jctName;
	}

	public void setJctName(String jctName) {
		this.jctName = jctName;
	}

	public String getJctFid() {
		return this.jctFid;
	}

	public void setJctFid(String jctFid) {
		this.jctFid = jctFid;
	}

}