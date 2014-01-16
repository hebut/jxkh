package org.iti.bysj.entity;

import org.iti.bysj.service.DbchooseService;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.service.ResultsService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * BsPeerview entity. @author MyEclipse Persistence Tools
 */

public class BsPeerview implements java.io.Serializable {

	// Fields
	public static final Short BPV_STATE_Wait = 0, BPV_STATE_Yes = 2, BPV_STATE_No = 1;
	public static final Short BPV_IFEDIT_NO = 0, BPV_IFEDIT_TJ = 1,BPV_IFEDIT_ZC=2;
	private Long bpvId;
	private Long kuId;
	private Short bpvIfedit;
	private Short bpvState;
	private Long buId;
	private Long bdbId;
	private String bpvComment;
	private Double bscore;
	BsGpunit bsGpunt;

	// Constructors

	public BsGpunit getBsGpunt() {
		if(bsGpunt==null){
			GpunitService gpunitService=(GpunitService) BeanFactory.getBean("gpunitService");
			this.bsGpunt=(BsGpunit) gpunitService.get(BsGpunit.class, buId);
		}
		return bsGpunt;
	}

	public Double getBscore() {
		if (bscore == null) {
			ResultsService resultsService = (ResultsService) BeanFactory.getBean("resultsService");
			this.bscore = resultsService.findSumBypeer(kuId, bdbId);
		}
		return bscore;
	}

	WkTUser user;
	BsDbchoose db;

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public BsDbchoose getDb() {
		if (db == null) {
			DbchooseService dbchooseService = (DbchooseService) BeanFactory.getBean("dbchooseService");
			this.db = (BsDbchoose) dbchooseService.get(BsDbchoose.class, bdbId);
		}
		return db;
	}

	// Constructors

	/** default constructor */
	public BsPeerview() {
		this.bpvIfedit = BsPeerview.BPV_IFEDIT_NO;
		this.bpvState = BsPeerview.BPV_STATE_Wait;
	}

	/** full constructor */
	public BsPeerview(Long bpvId, Long kuId, Short bpvIfedit, Short bpvState, Long buId, Long bdbId) {
		this.bpvId = bpvId;
		this.kuId = kuId;
		this.bpvIfedit = bpvIfedit;
		this.bpvState = bpvState;
		this.buId = buId;
		this.bdbId = bdbId;
	}

	public BsPeerview(Long bpvId, Long kuId, Short bpvIfedit, Short bpvState, Long buId, Long bdbId, String bpvComment) {
		super();
		this.bpvId = bpvId;
		this.kuId = kuId;
		this.bpvIfedit = bpvIfedit;
		this.bpvState = bpvState;
		this.buId = buId;
		this.bdbId = bdbId;
		this.bpvComment = bpvComment;
	}

	// Property accessors

	public String getBpvComment() {
		return bpvComment;
	}

	public void setBpvComment(String bpvComment) {
		this.bpvComment = bpvComment;
	}

	public Long getBpvId() {
		return this.bpvId;
	}

	public void setBpvId(Long bpvId) {
		this.bpvId = bpvId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Short getBpvIfedit() {
		return this.bpvIfedit;
	}

	public void setBpvIfedit(Short bpvIfedit) {
		this.bpvIfedit = bpvIfedit;
	}

	public Short getBpvState() {
		return this.bpvState;
	}

	public void setBpvState(Short bpvState) {
		this.bpvState = bpvState;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getBdbId() {
		return bdbId;
	}

	public void setBdbId(Long bdbId) {
		this.bdbId = bdbId;
	}

}