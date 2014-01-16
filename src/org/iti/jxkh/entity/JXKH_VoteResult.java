package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

@Entity
public class JXKH_VoteResult implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long vrId;
	private String vrYear;
	private Long kuId;
	private Integer vrNumber;
	private String vrRecord;
	private JXKH_VoteConfig config;
	private WkTUser user;

	public JXKH_VoteResult() {
	}

	public JXKH_VoteResult(Long vrId) {
		this.vrId = vrId;
	}

	public JXKH_VoteResult(Long vrId, String vrYear, Long kuId, Integer vrNumber, String vrRecord, JXKH_VoteConfig config) {
		super();
		this.vrId = vrId;
		this.vrYear = vrYear;
		this.kuId = kuId;
		this.vrNumber = vrNumber;
		this.vrRecord = vrRecord;
		this.config = config;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getVrId() {
		return vrId;
	}

	public void setVrId(Long vrId) {
		this.vrId = vrId;
	}

	@Column
	public String getVrYear() {
		return vrYear;
	}

	public void setVrYear(String vrYear) {
		this.vrYear = vrYear;
	}

	@Column
	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	@Column
	public Integer getVrNumber() {
		return vrNumber;
	}

	public void setVrNumber(Integer vrNumber) {
		this.vrNumber = vrNumber;
	}

	@Column
	public String getVrRecord() {
		return vrRecord;
	}

	public void setVrRecord(String vrRecord) {
		this.vrRecord = vrRecord;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config", nullable = true, insertable = true, updatable = true)
	public JXKH_VoteConfig getConfig() {
		return config;
	}

	public void setConfig(JXKH_VoteConfig config) {
		this.config = config;
	}

	@Transient
	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			user = (WkTUser) userService.get(WkTUser.class, kuId);
		}
		return user;
	}
}