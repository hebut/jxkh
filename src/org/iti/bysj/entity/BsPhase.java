package org.iti.bysj.entity;

import java.util.Date;

/**
 * BsPhase entity. @author MyEclipse Persistence Tools
 */

public class BsPhase implements java.io.Serializable {

	// Fields
	
	public static int PHASE_XUANTI=1,PHASE_PROCESS=2,PHASE_SCORE=3,PHASE_PY=4;

	private Long bpId;
	private String bpName;
	private Long bpStart;
	private Long bpEnd;
	private Integer bpOrder;
	private Long buId;
	private Long bbId;

	// Constructors
	
	public int isDuring(){
		Date d=new Date();
		if(d.getTime()<bpStart){
			return 0;
		}else if(d.getTime()<(bpEnd+1000*60*60*24)){
			return 1;
		}else{
			return 2;
		}
	}

	/** default constructor */
	public BsPhase() {
	}

	/** full constructor */
	public BsPhase(Long bpId, String bpName, Long bpStart, Long bpEnd,
			Integer bpOrder, Long buId, Long bbId) {
		this.bpId = bpId;
		this.bpName = bpName;
		this.bpStart = bpStart;
		this.bpEnd = bpEnd;
		this.bpOrder = bpOrder;
		this.buId = buId;
		this.bbId = bbId;
	}

	// Property accessors

	public Long getBpId() {
		return this.bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public String getBpName() {
		return this.bpName;
	}

	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	public Long getBpStart() {
		return this.bpStart;
	}

	public void setBpStart(Long bpStart) {
		this.bpStart = bpStart;
	}

	public Long getBpEnd() {
		return this.bpEnd;
	}

	public void setBpEnd(Long bpEnd) {
		this.bpEnd = bpEnd;
	}

	public Integer getBpOrder() {
		return this.bpOrder;
	}

	public void setBpOrder(Integer bpOrder) {
		this.bpOrder = bpOrder;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getBbId() {
		return this.bbId;
	}

	public void setBbId(Long bbId) {
		this.bbId = bbId;
	}

}