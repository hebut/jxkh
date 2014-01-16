package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class JXKH_AuditResult implements java.io.Serializable {
	private static final long serialVersionUID = -8136134284556758392L;
	private Long arId;
	private Long kuId;//�û�id
	private Long kdId;//����id
	private Short arType; // 0-ҵ����Ա��1-������Ա��2-Ժ�쵼��3-����
	private String arYear;//�������
	private String arKeys;
	private String arValues;
	private Short arLevel; // ����
	private Float arScore; // �ܻ���
	private Float newArScore;//�µĲ��ŵ÷֣�ֻ��Բ��Ŷ���
	private Float arBase; // ����
	private Float arMoney; // �ʽ�
	private Float arRate; // �ʽ�(?)
	private Float arIndex; // ���
	private String arExpand;
	public static final Short AR_WORK = 0, AR_MANAGE = 1, AR_LEADER = 2, AR_DEPT = 3;//���ͣ�ҵ����0��������1���쵼2������3
	public static final Short LEVEL_ONE = 1, LEVEL_TWO = 2, LEVEL_THREE = 3, LEVEL_FOUR = 4;
	public static final Short LEVEL_GOOD = 5, LEVEL_PASS = 6, LEVEL_FAIL = 7;

	public JXKH_AuditResult() {
	}

	public JXKH_AuditResult(Long arId) {
		this.arId = arId;
	}
	
	public JXKH_AuditResult(Long arId, Long kuId, Long kdId, Short arType, String arYear, String arKeys, String arValues, Short arLevel, Float arScore, Float newArScore, Float arBase, Float arMoney, Float arRate, Float arIndex, String arExpand) {
		super();
		this.arId = arId;
		this.kuId = kuId;
		this.kdId = kdId;
		this.arType = arType;
		this.arYear = arYear;
		this.arKeys = arKeys;
		this.arValues = arValues;
		this.arLevel = arLevel;
		this.arScore = arScore;
		this.newArScore = newArScore;
		this.arBase = arBase;
		this.arMoney = arMoney;
		this.arRate = arRate;
		this.arIndex = arIndex;
		this.arExpand = arExpand;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getArId() {
		return arId;
	}

	public void setArId(Long arId) {
		this.arId = arId;
	}

	@Column
	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	@Column
	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	@Column
	public Short getArType() {
		return arType;
	}

	public void setArType(Short arType) {
		this.arType = arType;
	}

	@Column
	public String getArYear() {
		return arYear;
	}

	public void setArYear(String arYear) {
		this.arYear = arYear;
	}

	@Column(length = 5000)
	public String getArKeys() {
		return arKeys;
	}

	public void setArKeys(String arKeys) {
		this.arKeys = arKeys;
	}

	@Column(length = 5000)
	public String getArValues() {
		return arValues;
	}

	public void setArValues(String arValues) {
		this.arValues = arValues;
	}

	@Column
	public Short getArLevel() {
		return arLevel;
	}

	public void setArLevel(Short arLevel) {
		this.arLevel = arLevel;
	}

	@Column
	public Float getArScore() {
		return arScore;
	}

	public void setArScore(Float arScore) {
		this.arScore = arScore;
	}
	@Column
	public Float getNewArScore() {
		return newArScore;
	}

	public void setNewArScore(Float newArScore) {
		this.newArScore = newArScore;
	}

	@Column
	public Float getArMoney() {
		return arMoney;
	}

	public void setArMoney(Float arMoney) {
		this.arMoney = arMoney;
	}

	@Column
	public Float getArRate() {
		return arRate;
	}

	public void setArRate(Float arRate) {
		this.arRate = arRate;
	}

	@Column
	public Float getArIndex() {
		return arIndex;
	}

	public void setArIndex(Float arIndex) {
		this.arIndex = arIndex;
	}

	@Column
	public String getArExpand() {
		return arExpand;
	}

	public void setArExpand(String arExpand) {
		this.arExpand = arExpand;
	}

	@Column
	public Float getArBase() {
		return arBase;
	}

	public void setArBase(Float arBase) {
		this.arBase = arBase;
	}
}