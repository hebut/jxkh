package org.iti.bysj.entity;

import java.util.List;

import org.iti.bysj.service.PhaseService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * BsBatch entity. @author MyEclipse Persistence Tools
 */

public class BsBatch implements java.io.Serializable {

	// Fields
	public static final Short IFOPEN_NO=0,IFOPEN_YES=1;
	public static final Short XTLB_NONE=0,XTLB_SX=1,XTLB_ZD=2;

	private Long bbId;
	private String bbName;
	private Long buId;
	private Short bbXtlb;
	private Short bbIfopen;
	private Long bcpId;
	private Integer bbXtnum;
	private Integer bbStunum;

	/**
	 * 判断该毕设单位是否处于某个阶段，某个大的阶段哦
	 * @param order,4个大阶段
	 * @return -1未设置，0已设置未达到该阶段,1正处于阶段,2已经超过该阶段
	 */
	public int isDuring(int order){
		int during=-1;
		PhaseService bsphaseService=(PhaseService)BeanFactory.getBean("bsphaseService");
		List plist;
		if(order<=2){
			plist=bsphaseService.findByBbIdAndOrder(bbId, order);
		}else{
		    plist=bsphaseService.findByBuIdAndOrder(buId, order);
		}
		if(plist.size()==0){
			return during;
		}		 
		BsPhase phase=(BsPhase)plist.get(0);
		return phase.isDuring();
	}
	// Constructors

	/** default constructor */
	public BsBatch() {
		this.bbXtnum = 0;
		this.bbStunum = 0;
    	this.bbIfopen = IFOPEN_NO;
    	this.bbXtlb = XTLB_NONE;
	}

	/** full constructor */
	public BsBatch(Long bbId, String bbName, Long buId, Short bbXtlb,
			Short bbIfopen, Long bcpId, Integer bbXtnum,Integer bbStunum) {
		this.bbId = bbId;
		this.bbName = bbName;
		this.buId = buId;
		this.bbXtlb = bbXtlb;
		this.bbIfopen = bbIfopen;
		this.bcpId = bcpId;
		this.bbXtnum = bbXtnum;
		this.bbStunum = bbStunum;
	}

	// Property accessors

	public Integer getBbStunum() {
		return bbStunum;
	}

	public void setBbStunum(Integer bbStunum) {
		this.bbStunum = bbStunum;
	}

	public Long getBbId() {
		return this.bbId;
	}

	public void setBbId(Long bbId) {
		this.bbId = bbId;
	}

	public String getBbName() {
		return this.bbName;
	}

	public void setBbName(String bbName) {
		this.bbName = bbName;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Short getBbXtlb() {
		return this.bbXtlb;
	}

	public void setBbXtlb(Short bbXtlb) {
		this.bbXtlb = bbXtlb;
	}

	public Short getBbIfopen() {
		return this.bbIfopen;
	}

	public void setBbIfopen(Short bbIfopen) {
		this.bbIfopen = bbIfopen;
	}

	public Long getBcpId() {
		return this.bcpId;
	}

	public void setBcpId(Long bcpId) {
		this.bcpId = bcpId;
	}

	public Integer getBbXtnum() {
		return this.bbXtnum;
	}

	public void setBbXtnum(Integer bbXtnum) {
		this.bbXtnum = bbXtnum;
	}

}