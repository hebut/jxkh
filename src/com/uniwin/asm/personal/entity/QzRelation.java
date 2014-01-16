package com.uniwin.asm.personal.entity;

import com.uniwin.basehs.util.BeanFactory;

/**
 * QzGroup entity. @author MyEclipse Persistence Tools
 */
public class QzRelation implements java.io.Serializable {
	// Fields
	private Long rlId;
	private Long sjId;
	private Long kuId;
	private Short rlState;
	private Short rlShow;

	// Constructors
	/** default constructor */
	public QzRelation() {
	}

	/** minimal constructor */
	public QzRelation(Long rlId) {
		this.rlId = rlId;
	}

	/** full constructor */
	public QzRelation(Long rlId, Long sjId, Long kuId, Short rlState, Short rlShow) {
		this.rlId = rlId;
		this.sjId = sjId;
		this.kuId = kuId;
		this.rlState = rlState;
		this.rlShow = rlShow;
	}

	// Property accessors
	public Long getRlId() {
		return rlId;
	}

	public void setRlId(Long rlId) {
		this.rlId = rlId;
	}

	public Long getSjId() {
		return sjId;
	}

	public void setSjId(Long sjId) {
		this.sjId = sjId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Short getRlState() {
		return rlState;
	}

	public void setRlState(Short rlState) {
		this.rlState = rlState;
	}

	public Short getRlShow() {
		return rlShow;
	}

	public void setRlShow(Short rlShow) {
		this.rlShow = rlShow;
	}
}