package org.iti.bysj.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * BsResults entity. @author MyEclipse Persistence Tools
 */

public class BsResults implements java.io.Serializable {

	// Fields
	public static final Short BATCH_ONE=1,BATCH_TWO=2,BATCH_NO=0;
	private Long brId;
	private Long bscId;
	private Double brScore;
	private Long buId;
	private Long kuId;
	private Long bsId;
	private Short brBatch;
	private WkTUser user;
	public WkTUser getUser() {
		if(user==null){
			UserService userService =(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	// Constructors

	/** default constructor */
	public BsResults() {
	}

	/** minimal constructor */
	public BsResults(Long brId, Long bscId, Double brScore, Long buId,
			Long kuId, Long bsId) {
		this.brId = brId;
		this.bscId = bscId;
		this.brScore = brScore;
		this.buId = buId;
		this.kuId = kuId;
		this.bsId = bsId;
	}

	/** full constructor */
	public BsResults(Long brId, Long bscId, Double brScore, Long buId,
			Long kuId, Long bsId, Short brBatch) {
		this.brId = brId;
		this.bscId = bscId;
		this.brScore = brScore;
		this.buId = buId;
		this.kuId = kuId;
		this.bsId = bsId;
		this.brBatch = brBatch;
	}

	// Property accessors

	public Long getBrId() {
		return this.brId;
	}

	public void setBrId(Long brId) {
		this.brId = brId;
	}

	public Long getBscId() {
		return this.bscId;
	}

	public void setBscId(Long bscId) {
		this.bscId = bscId;
	}

	public Double getBrScore() {
		return this.brScore;
	}

	public void setBrScore(Double brScore) {
		this.brScore = brScore;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getBsId() {
		return this.bsId;
	}

	public void setBsId(Long bsId) {
		this.bsId = bsId;
	}

	public Short getBrBatch() {
		return this.brBatch;
	}

	public void setBrBatch(Short brBatch) {
		this.brBatch = brBatch;
	}

}