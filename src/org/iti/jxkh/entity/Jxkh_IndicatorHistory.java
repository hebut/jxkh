package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iti.common.interfaces.EntityCommonInterface;
import com.iti.common.util.EntityUtil;

@Entity
@Table(name = "Jxkh_IndicatorHistory")
public class Jxkh_IndicatorHistory implements EntityCommonInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long jihId;
	private Long kbId;//指标编号
	private Long kbPid;//所属父指标编号
	private String kbName;//指标名称
	private Integer kbScore;//指标分值
	private Float kbIndex;//指标权重
	private String kbValue;//指标属性值
	private String kbMeasure;//计量单位，例如篇
	private String kbTime;//指标添加时间
	private String jihTime;//更改时间
	
	// Constructors

	/** default constructor */
	public Jxkh_IndicatorHistory() {
		
	}
	/** minimal constructor */
	public Jxkh_IndicatorHistory(Long jihId, Long kbId, Long kbPid) {
		super();
		this.jihId = jihId;
		this.kbId = kbId;
		this.kbPid = kbPid;
	}
	/** full constructor */
	public Jxkh_IndicatorHistory(Long jihId, Long kbId, Long kbPid,
			String kbName, Integer kbScore, Float kbIndex, String kbValue,
			String kbMeasure, String kbTime, String jihTime) {
		super();
		this.jihId = jihId;
		this.kbId = kbId;
		this.kbPid = kbPid;
		this.kbName = kbName;
		this.kbScore = kbScore;
		this.kbIndex = kbIndex;
		this.kbValue = kbValue;
		this.kbMeasure = kbMeasure;
		this.kbTime = kbTime;
		this.jihTime = jihTime;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public Long getJihId() {
		return jihId;
	}
	public void setJihId(Long jihId) {
		this.jihId = jihId;
	}
	@Column
	public Long getKbId() {
		return kbId;
	}
	public void setKbId(Long kbId) {
		this.kbId = kbId;
	}
	@Column
	public Long getKbPid() {
		return kbPid;
	}
	public void setKbPid(Long kbPid) {
		this.kbPid = kbPid;
	}
	@Column(length = 30)
	public String getKbName() {
		return kbName;
	}
	public void setKbName(String kbName) {
		this.kbName = kbName;
	}
	@Column(length = 20)
	public Integer getKbScore() {
		return kbScore;
	}
	public void setKbScore(Integer kbScore) {
		this.kbScore = kbScore;
	}
	@Column(length = 20)
	public Float getKbIndex() {
		return kbIndex;
	}
	public void setKbIndex(Float kbIndex) {
		this.kbIndex = kbIndex;
	}
	@Column(length = 30)
	public String getKbValue() {
		return kbValue;
	}
	public void setKbValue(String kbValue) {
		this.kbValue = kbValue;
	}
	@Column(length = 10)
	public String getKbMeasure() {
		return kbMeasure;
	}
	public void setKbMeasure(String kbMeasure) {
		this.kbMeasure = kbMeasure;
	}
	@Column(length = 20)
	public String getKbTime() {
		return kbTime;
	}
	public void setKbTime(String kbTime) {
		this.kbTime = kbTime;
	}
	@Column(length = 20)
	public String getJihTime() {
		return jihTime;
	}
	public void setJihTime(String jihTime) {
		this.jihTime = jihTime;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jihId == null) ? 0 : jihId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_IndicatorHistory other = (Jxkh_IndicatorHistory) obj;
		if (jihId == null) {
			if (other.jihId != null)
				return false;
		} else if (!jihId.equals(other.jihId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Jxkh_IndicatorHistory [jihId=" + jihId + ", kbId=" + kbId
				+ ", kbPid=" + kbPid + ", kbName=" + kbName + ", kbScore="
				+ kbScore + ", kbIndex=" + kbIndex + ", kbValue=" + kbValue
				+ ", kbMeasure=" + kbMeasure + ", kbTime=" + kbTime
				+ ", jihTime=" + jihTime + "]";
	}
	
	@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_IndicatorHistory.class, this.jihId);
	}

	

}
