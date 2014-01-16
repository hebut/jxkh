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
@Table(name = "Jxkh_BusinessIndicator")
public class Jxkh_BusinessIndicator implements EntityCommonInterface {	

	/**
	 * ָ��
	 */
	
	public static final short USE_YES=0,USE_NO=1;//����Ϊ0��ͣ��Ϊ1
	public static final short LOCK_YES=0,LOCK_NO=1;//����Ϊ0������Ϊ1
	public static final String INDEX_TYPE="11",JOUR_RANK="12",MEETARTI_RANK="13";//��¼���͡��ڿ����𡢻������ĵĻ��鼶��
	public static final String PUB_TYPE="";//�������
	public static final String AWARD_RANK="31";//��������
	public static final String PROJECT_RANK="41";//��Ŀ����
	public static final String PATENT_TYPE="51";//ר�������������
	public static final String FRUIT_LEVEL="61",CHECK_RANK="62";//�ɹ�ˮƽ�����յȼ�
	public static final String MEET_RANK="71";//ѧ�������еĻ��鼶��
	public static final String REP_LEADER="81";//�������ʾ�쵼����
	public static final String VEDIO_LEADER="91";//Ӱ��ר��Ƭ�е���ʾ�쵼����
	public static final String VEDIO_PROVENCE="922";//Ӱ��ר��Ƭ�е�ʡ��
	public static final String VEDIO_NATION="921";//Ӱ��ר��Ƭ�е�ʡ��
	private static final long serialVersionUID = 1L;
	// Fields
	private Long kbId;//ָ����
	private Long kbPid;//������ָ����
	private Long kbOrdno;//ָ�����
	private short kbStatus;//ָ��Ӧ��״̬�����á�ͣ��
	private short kbLock;//ָ������״̬������������
	private String kbName;//ָ������
	private String kbDesc;//ָ������
	private String kbTime;//ָ�����ʱ��
	private Integer kbScore;//ָ���ֵ
	private Float kbIndex;//ָ��Ȩ��
	private String kbValue;//ָ������ֵ
	private String kbMeasure;//������λ������ƪ	

	// Constructors

	/** default constructor */
	public Jxkh_BusinessIndicator() {
	}

	/** minimal constructor */
	public Jxkh_BusinessIndicator(Long kbId, Long kbPid) {
		this.kbId = kbId;
		this.kbPid = kbPid;
	}
	
	
	/** full constructor */
	public Jxkh_BusinessIndicator(Long kbId, Long kbPid, Long kbOrdno,
			short kbStatus, short kbLock, String kbName, String kbDesc,
			String kbTime, Integer kbScore, Float kbIndex, String kbValue,
			String kbMeasure) {
		super();
		this.kbId = kbId;
		this.kbPid = kbPid;
		this.kbOrdno = kbOrdno;
		this.kbStatus = kbStatus;
		this.kbLock = kbLock;
		this.kbName = kbName;
		this.kbDesc = kbDesc;
		this.kbTime = kbTime;
		this.kbScore = kbScore;
		this.kbIndex = kbIndex;
		this.kbValue = kbValue;
		this.kbMeasure = kbMeasure;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Column
	public Long getKbOrdno() {
		return kbOrdno;
	}

	public void setKbOrdno(Long kbOrdno) {
		this.kbOrdno = kbOrdno;
	}
	@Column(length = 10)
	public short getKbStatus() {
		return kbStatus;
	}

	public void setKbStatus(short kbStatus) {
		this.kbStatus = kbStatus;
	}
	@Column(length = 10)
	public short getKbLock() {
		return kbLock;
	}

	public void setKbLock(short kbLock) {
		this.kbLock = kbLock;
	}
	@Column(length = 30)
	public String getKbName() {
		return kbName;
	}

	public void setKbName(String kbName) {
		this.kbName = kbName;
	}
	@Column(length = 50)
	public String getKbDesc() {
		return kbDesc;
	}

	public void setKbDesc(String kbDesc) {
		this.kbDesc = kbDesc;
	}
	@Column(length = 20)
	public String getKbTime() {
		return kbTime;
	}

	public void setKbTime(String kbTime) {
		this.kbTime = kbTime;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kbId == null) ? 0 : kbId.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Jxkh_BusinessIndicator [kbId=" + kbId + ", kbPid=" + kbPid
				+ ", kbOrdno=" + kbOrdno + ", kbStatus=" + kbStatus
				+ ", kbLock=" + kbLock + ", kbName=" + kbName + ", kbDesc="
				+ kbDesc + ", kbTime=" + kbTime + ", kbScore=" + kbScore
				+ ", kbIndex=" + kbIndex + ", kbValue=" + kbValue
				+ ", kbMeasure=" + kbMeasure + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_BusinessIndicator other = (Jxkh_BusinessIndicator) obj;
		if (kbId == null) {
			if (other.kbId != null)
				return false;
		} else if (!kbId.equals(other.kbId))
			return false;
		return true;
	}

	@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_BusinessIndicator.class, this.kbId);
	}	
}