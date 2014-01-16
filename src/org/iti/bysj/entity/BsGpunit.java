package org.iti.bysj.entity;


import java.util.List;

import org.iti.bysj.service.GprocesService;
import org.iti.bysj.service.PhaseService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.UserService;

/**
 * BsGpunit entity. @author MyEclipse Persistence Tools
 */

public class BsGpunit implements java.io.Serializable {

	// Fields
	public static final Short REVIEWWAY_NONE = 0, REVIEWWAY_TEA = 1, REVIEWWAY_XZR = 2;
	public static final Short PACKET_NONE = 0;
	public static final Short ISFRUIT_NO = 0, ISFRUIT_YES = 1;
	public static final Short IFBATCH_NONE = 0, IFBATCH_NO = 1, IFBATCH_YES = 2;
	public static final Short IFOPEN_NO = 0, IFOPEN_YES = 1;
	public static final Short XTLB_NONE = 0, XTLB_SX = 1, XTLB_ZD = 2;
	public static final Short SPACKET_NONE = 0;
	public static final Short DBZG_NONE = 0, DBZG_XZR = 1, DBZG_JXMS = 2;
	public static final Short TWODBZG_NONE = 0, TWODBZG_XZR = 1, TWODBZG_JXMS = 2;
	public static final Short OPENWEEK_NO = 0, OPENWEEK_YES = 1;
	public static final Short PACKETWAY_RANDOM = 0, PACKETWAY_NOTEACHER = 1, PACKETWAY_SAMENATURE = 2, PACKETWAY_MIX12 = 3, PACKETWAY_YESTEACHER = 4, PACKETWAY_DIY = 5;
	private Long buId;
	private Long kdId;
	private Integer buThnum;
	private Integer buXtnum;
	private Integer buStunum;
	private Integer buMax;
	private Short buReviewway;
	private Short buPacketway;
	private String buEvaluation;
	private String buReport;
	private Short buIsfruit;
	private Long bgId;
	private Short buIfbatch;
	private Short buIfopen;
	private Short buXtlb;
	private Long bcpId;
	private Short buSpacket;
	private Short buDbzg;
	private Short buTwodbzg;

	private Integer buBegweek;
	private Integer buEndweek;
	private Short buOpenweek;

	WkTDept dept;
	BsGproces gprocess;

	/**
	 * �жϸñ��赥λ�Ƿ���ĳ���׶Σ�ĳ����Ľ׶�Ŷ
	 * 
	 * @param order
	 * @return -1δ���ã�0������δ�ﵽ�ý׶�,1�����ڽ׶�,2�Ѿ������ý׶�
	 */
	public int isDuring(int order) {
		int during = -1;
		PhaseService bsphaseService = (PhaseService) BeanFactory.getBean("bsphaseService");
		List plist = bsphaseService.findByBuIdAndOrder(buId, order);
		if (plist.size() == 0) {
			return during;
		}
		BsPhase phase = (BsPhase) plist.get(0);
		return phase.isDuring();
	}

	// Constructors

	public BsGproces getGprocess() {
		if (gprocess == null) {
			GprocesService gprocesService = (GprocesService) BeanFactory.getBean("gprocesService");
			BsGproces gprocess = (BsGproces) gprocesService.get(BsGproces.class, bgId);
			this.gprocess = gprocess;
		}
		return gprocess;
	}

	public WkTDept getDept() {
		if (dept == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			WkTDept d = (WkTDept) userService.get(WkTDept.class, kdId);
			this.dept = d;
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	/** default constructor */
	public BsGpunit() {
		this.buThnum = 0;// ͬ������0������δ�趨
		this.buXtnum = 0;// ѡ����ĿΪ0������δ�趨���ѡ����Ŀ
		this.buStunum = 0;//��ѡѧ����ĿΪ0������δ�趨���ѧ����Ŀ
		this.buMax = 0;// �����������ֵ0,����δ�趨
		this.buReviewway = REVIEWWAY_NONE;// ָ�������˷�ʽ
		this.buPacketway = PACKET_NONE;// һ��ѧ�����鷽ʽ
		this.buIsfruit = ISFRUIT_NO;// �Ƿ������ɹ��ɼ�
		this.buIfbatch = IFBATCH_NONE;
		this.buIfopen = IFOPEN_NO;
		this.buXtlb = XTLB_NONE;
		this.buSpacket = SPACKET_NONE;
		this.buDbzg = DBZG_NONE;
		this.buTwodbzg = TWODBZG_NONE;
		this.bcpId = 0L;// δָ�������ĵ�
		this.buBegweek = 0;
		this.buEndweek = 0;
		this.buOpenweek = OPENWEEK_NO;
	}

	/** minimal constructor */
	public BsGpunit(Long buId, Long kdId, Integer buThnum, Integer buXtnum,Integer buStunum, Integer buMax, Short buReviewway, Short buPacketway, Short buIsfruit, Long bgId, Short buIfbatch, Short buIfopen, Short buXtlb, Long bcpId, Short buSpacket, Short buDbzg,
			Short buTwodbzg, Integer buBegweek, Integer buEndweek, Short buOpenweek) {
		this.buId = buId;
		this.kdId = kdId;
		this.buThnum = buThnum;
		this.buXtnum = buXtnum;
		this.buStunum = buStunum;
		this.buMax = buMax;
		this.buReviewway = buReviewway;
		this.buPacketway = buPacketway;
		this.buIsfruit = buIsfruit;
		this.bgId = bgId;
		this.buIfbatch = buIfbatch;
		this.buIfopen = buIfopen;
		this.buXtlb = buXtlb;
		this.bcpId = bcpId;
		this.buSpacket = buSpacket;
		this.buDbzg = buDbzg;
		this.buTwodbzg = buTwodbzg;
		this.buBegweek = buBegweek;
		this.buEndweek = buEndweek;
		this.buOpenweek = buOpenweek;
	}

	/** full constructor */
	public BsGpunit(Long buId, Long kdId, Integer buThnum, Integer buXtnum, Integer buStunum,Integer buMax, Short buReviewway, Short buPacketway, String buEvaluation, String buReport, Short buIfcalculat, Short buIsfruit, Long bgId, Short buIfbatch, Short buIfopen,
			Short buXtlb, Long bcpId, Short buSpacket, Short buDbzg, Short buTwodbzg) {
		this.buId = buId;
		this.kdId = kdId;
		this.buThnum = buThnum;
		this.buXtnum = buXtnum;
		this.buStunum = buStunum;
		this.buMax = buMax;
		this.buReviewway = buReviewway;
		this.buPacketway = buPacketway;
		this.buEvaluation = buEvaluation;
		this.buReport = buReport;
		this.buIsfruit = buIsfruit;
		this.bgId = bgId;
		this.buIfbatch = buIfbatch;
		this.buIfopen = buIfopen;
		this.buXtlb = buXtlb;
		this.bcpId = bcpId;
		this.buSpacket = buSpacket;
		this.buDbzg = buDbzg;
		this.buTwodbzg = buTwodbzg;
	}

	// Property accessors

	public Integer getBuStunum() {
		return buStunum;
	}

	public void setBuStunum(Integer buStunum) {
		this.buStunum = buStunum;
	}

	public Long getBuId() {
		return this.buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Integer getBuThnum() {
		return this.buThnum;
	}

	public void setBuThnum(Integer buThnum) {
		this.buThnum = buThnum;
	}

	public Integer getBuXtnum() {
		return this.buXtnum;
	}

	public void setBuXtnum(Integer buXtnum) {
		this.buXtnum = buXtnum;
	}

	public Integer getBuMax() {
		return this.buMax;
	}

	public void setBuMax(Integer buMax) {
		this.buMax = buMax;
	}

	public Short getBuReviewway() {
		return this.buReviewway;
	}

	public void setBuReviewway(Short buReviewway) {
		this.buReviewway = buReviewway;
	}

	public Short getBuPacketway() {
		return this.buPacketway;
	}

	public void setBuPacketway(Short buPacketway) {
		this.buPacketway = buPacketway;
	}

	public String getBuEvaluation() {
		return this.buEvaluation;
	}

	public void setBuEvaluation(String buEvaluation) {
		this.buEvaluation = buEvaluation;
	}

	public String getBuReport() {
		return this.buReport;
	}

	public void setBuReport(String buReport) {
		this.buReport = buReport;
	}

	public Short getBuIsfruit() {
		return this.buIsfruit;
	}

	public void setBuIsfruit(Short buIsfruit) {
		this.buIsfruit = buIsfruit;
	}

	public Long getBgId() {
		return this.bgId;
	}

	public void setBgId(Long bgId) {
		this.bgId = bgId;
	}

	public Short getBuIfbatch() {
		return this.buIfbatch;
	}

	public void setBuIfbatch(Short buIfbatch) {
		this.buIfbatch = buIfbatch;
	}

	public Short getBuIfopen() {
		return this.buIfopen;
	}

	public void setBuIfopen(Short buIfopen) {
		this.buIfopen = buIfopen;
	}

	public Short getBuXtlb() {
		return this.buXtlb;
	}

	public void setBuXtlb(Short buXtlb) {
		this.buXtlb = buXtlb;
	}

	public Long getBcpId() {
		return this.bcpId;
	}

	public void setBcpId(Long bcpId) {
		this.bcpId = bcpId;
	}

	public Short getBuSpacket() {
		return this.buSpacket;
	}

	public void setBuSpacket(Short buSpacket) {
		this.buSpacket = buSpacket;
	}

	public Short getBuDbzg() {
		return this.buDbzg;
	}

	public void setBuDbzg(Short buDbzg) {
		this.buDbzg = buDbzg;
	}

	public Integer getBuBegweek() {
		return buBegweek;
	}

	public void setBuBegweek(Integer buBegweek) {
		this.buBegweek = buBegweek;
	}

	public Integer getBuEndweek() {
		return buEndweek;
	}

	public void setBuEndweek(Integer buEndweek) {
		this.buEndweek = buEndweek;
	}

	public Short getBuOpenweek() {
		return buOpenweek;
	}

	public void setBuOpenweek(Short buOpenweek) {
		this.buOpenweek = buOpenweek;
	}

	public Short getBuTwodbzg() {
		return buTwodbzg;
	}

	public void setBuTwodbzg(Short buTwodbzg) {
		this.buTwodbzg = buTwodbzg;
	}

}