package org.iti.bysj.entity;

import java.util.Date;

import org.iti.bysj.service.CheckService;
import org.iti.bysj.service.PhaseService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * BsCphase entity. @author MyEclipse Persistence Tools
 */

public class BsCphase implements java.io.Serializable {

	// Fields
	public static final Short UPUSER_JS=1;
	public static final Short UPUSER_XS=0;
	public static final Short IFREVIEW_NO=0,IFREVIEW_YES=1;
	public static final Integer FILE_MAXSIZE=100;
	private Long bcpId;
	private Long buId;
	private Long bbId;
	private String bcpName;
	private Long bcpStart;
	private Long bcpEnd;
	private Long bcpDeferdate;
	//private String bcpDocname;
	private String bcpDoctype;
	private Short bcpUpuser;
	private Short bcpIfreview;

	private BsBatch batch;
	// Constructors

	/** default constructor */
	public BsCphase() {
		this.bcpDeferdate=0L;
	}

	/** full constructor */
	public BsCphase(Long bcpId, Long buId, Long bbId, String bcpName,
			Long bcpStart, Long bcpEnd, Long bcpDeferdate, 
			String bcpDoctype, Short bcpUpuser, Short bcpIfreview) {
		this.bcpId = bcpId;
		this.buId = buId;
		this.bbId = bbId;
		this.bcpName = bcpName;
		this.bcpStart = bcpStart;
		this.bcpEnd = bcpEnd;
		this.bcpDeferdate = bcpDeferdate;
		//this.bcpDocname = bcpDocname;
		this.bcpDoctype = bcpDoctype;
		this.bcpUpuser = bcpUpuser;
		this.bcpIfreview = bcpIfreview;
	}

	public BsBatch getBatch() {
		if(batch==null){
			CheckService checkService = (CheckService) BeanFactory.getBean("checkService");
			batch = (BsBatch) checkService.get(BsBatch.class, bbId);
		}
		return batch;
	}

	// Property accessors
	public int isDuring(){
		Date d=new Date();
		if(d.getTime()<bcpStart){
			return 0;
		}else if(d.getTime()<(bcpEnd+1000*60*60*24)){
			return 1;
		}else{
			return 2;
		}
	}

	public Long getBcpId() {
		return this.bcpId;
	}

	public void setBcpId(Long bcpId) {
		this.bcpId = bcpId;
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

	public String getBcpName() {
		return this.bcpName;
	}

	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}

	public Long getBcpStart() {
		return this.bcpStart;
	}

	public void setBcpStart(Long bcpStart) {
		this.bcpStart = bcpStart;
	}

	public Long getBcpEnd() {
		return this.bcpEnd;
	}

	public void setBcpEnd(Long bcpEnd) {
		this.bcpEnd = bcpEnd;
	}

	public Long getBcpDeferdate() {
		return this.bcpDeferdate;
	}

	public void setBcpDeferdate(Long bcpDeferdate) {
		this.bcpDeferdate = bcpDeferdate;
	}

//	public String getBcpDocname() {
//		return this.bcpDocname;
//	}
//
//	public void setBcpDocname(String bcpDocname) {
//		this.bcpDocname = bcpDocname;
//	}

	public String getBcpDoctype() {
		return this.bcpDoctype;
	}

	public void setBcpDoctype(String bcpDoctype) {
		this.bcpDoctype = bcpDoctype;
	}

	public Short getBcpUpuser() {
		return this.bcpUpuser;
	}

	public void setBcpUpuser(Short bcpUpuser) {
		this.bcpUpuser = bcpUpuser;
	}

	public Short getBcpIfreview() {
		return this.bcpIfreview;
	}

	public void setBcpIfreview(Short bcpIfreview) {
		this.bcpIfreview = bcpIfreview;
	}

}