package com.uniwin.framework.entity;

import org.iti.bysj.service.DbchooseService;

import com.uniwin.basehs.util.BeanFactory;

/**
 * WkTAuthId entity. @author MyEclipse Persistence Tools
 */
public class WkTAuth implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	public static final String TYPE_TITLE = "1";
	public static final String TYPE_Chanel = "2";
	public static final String TYPE_TASK="3";
	public static final Short KACODE1_OK = 1, KACODE1_NO = 0;
	/**
	 * 主键id
	 */
	private Long kaId;
	private Long kaRid;
	private Long krId;
	private Long kdId;
	// 角色名称
	private String krName;
	// 部门名称
	private String kdName;
	private Long kaIp11;
	private Long kaIp12;
	private Long kaIp21;
	private Long kaIp22;
	private Long kaIp31;
	private Long kaIp32;
	private Long kaIp41;
	private Long kaIp42;
	private String kaType;
	private String kaCode;
	private Short kaCode1;
	private Short kaCode2;
	private Short kaCode3;
	private Short kaCode4;
	private Short kaCode5;
	private Short kaCode6;
	private Short kaCode7;
	private Short kaCode8;
	private Short kaCode9;
	private Short kaCode10;
	private WkTTitle title;

	// Constructors
	/** default constructor */
	public WkTAuth() {
	}

	/** minimal constructor */
	public WkTAuth(Long kaRid, Long krId, Long kdId, String kaType) {
		this.kaRid = kaRid;
		this.krId = krId;
		this.kdId = kdId;
		this.kaType = kaType;
	}

	/** full constructor */
	public WkTAuth(Long kaId, Long kaRid, Long krId, Long kdId, Long kaIp11, Long kaIp12, Long kaIp21, Long kaIp22, Long kaIp31, Long kaIp32, Long kaIp41, Long kaIp42, String kaType, String kaCode, Short kaCode1, Short kaCode2, Short kaCode3, Short kaCode4, Short kaCode5, Short kaCode6, Short kaCode7, Short kaCode8, Short kaCode9, Short kaCode10) {
		this.kaId = kaId;
		this.kaRid = kaRid;
		this.krId = krId;
		this.kdId = kdId;
		this.kaIp11 = kaIp11;
		this.kaIp12 = kaIp12;
		this.kaIp21 = kaIp21;
		this.kaIp22 = kaIp22;
		this.kaIp31 = kaIp31;
		this.kaIp32 = kaIp32;
		this.kaIp41 = kaIp41;
		this.kaIp42 = kaIp42;
		this.kaType = kaType;
		this.kaCode = kaCode;
		this.kaCode1 = kaCode1;
		this.kaCode2 = kaCode2;
		this.kaCode3 = kaCode3;
		this.kaCode4 = kaCode4;
		this.kaCode5 = kaCode5;
		this.kaCode6 = kaCode6;
		this.kaCode7 = kaCode7;
		this.kaCode8 = kaCode8;
		this.kaCode9 = kaCode9;
		this.kaCode10 = kaCode10;
	}

	// Property accessors
	public WkTAuth(Long kaId, Long kaRid, Long krId, Long kdId, String krName, String kdName, Long kaIp11, Long kaIp12, Long kaIp21, Long kaIp22, Long kaIp31, Long kaIp32, Long kaIp41, Long kaIp42, String kaType, String kaCode, Short kaCode1, Short kaCode2, Short kaCode3, Short kaCode4, Short kaCode5, Short kaCode6, Short kaCode7, Short kaCode8, Short kaCode9, Short kaCode10) {
		super();
		this.kaId = kaId;
		this.kaRid = kaRid;
		this.krId = krId;
		this.kdId = kdId;
		this.krName = krName;
		this.kdName = kdName;
		this.kaIp11 = kaIp11;
		this.kaIp12 = kaIp12;
		this.kaIp21 = kaIp21;
		this.kaIp22 = kaIp22;
		this.kaIp31 = kaIp31;
		this.kaIp32 = kaIp32;
		this.kaIp41 = kaIp41;
		this.kaIp42 = kaIp42;
		this.kaType = kaType;
		this.kaCode = kaCode;
		this.kaCode1 = kaCode1;
		this.kaCode2 = kaCode2;
		this.kaCode3 = kaCode3;
		this.kaCode4 = kaCode4;
		this.kaCode5 = kaCode5;
		this.kaCode6 = kaCode6;
		this.kaCode7 = kaCode7;
		this.kaCode8 = kaCode8;
		this.kaCode9 = kaCode9;
		this.kaCode10 = kaCode10;
	}

	public Long getKaId() {
		return this.kaId;
	}

	public void setKaId(Long kaId) {
		this.kaId = kaId;
	}

	public Long getKaRid() {
		return this.kaRid;
	}

	public void setKaRid(Long kaRid) {
		this.kaRid = kaRid;
	}

	public Long getKrId() {
		return this.krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKaIp11() {
		return this.kaIp11;
	}

	public void setKaIp11(Long kaIp11) {
		this.kaIp11 = kaIp11;
	}

	public Long getKaIp12() {
		return this.kaIp12;
	}

	public void setKaIp12(Long kaIp12) {
		this.kaIp12 = kaIp12;
	}

	public Long getKaIp21() {
		return this.kaIp21;
	}

	public void setKaIp21(Long kaIp21) {
		this.kaIp21 = kaIp21;
	}

	public Long getKaIp22() {
		return this.kaIp22;
	}

	public void setKaIp22(Long kaIp22) {
		this.kaIp22 = kaIp22;
	}

	public Long getKaIp31() {
		return this.kaIp31;
	}

	public void setKaIp31(Long kaIp31) {
		this.kaIp31 = kaIp31;
	}

	public Long getKaIp32() {
		return this.kaIp32;
	}

	public void setKaIp32(Long kaIp32) {
		this.kaIp32 = kaIp32;
	}

	public Long getKaIp41() {
		return this.kaIp41;
	}

	public void setKaIp41(Long kaIp41) {
		this.kaIp41 = kaIp41;
	}

	public Long getKaIp42() {
		return this.kaIp42;
	}

	public void setKaIp42(Long kaIp42) {
		this.kaIp42 = kaIp42;
	}

	public String getKaType() {
		return this.kaType;
	}

	public void setKaType(String kaType) {
		this.kaType = kaType;
	}

	public String getKaCode() {
		return this.kaCode;
	}

	public void setKaCode(String kaCode) {
		this.kaCode = kaCode;
	}

	public Short getKaCode1() {
		return this.kaCode1;
	}

	public void setKaCode1(Short kaCode1) {
		this.kaCode1 = kaCode1;
	}

	public Short getKaCode2() {
		return this.kaCode2;
	}

	public void setKaCode2(Short kaCode2) {
		this.kaCode2 = kaCode2;
	}

	public Short getKaCode3() {
		return this.kaCode3;
	}

	public void setKaCode3(Short kaCode3) {
		this.kaCode3 = kaCode3;
	}

	public Short getKaCode4() {
		return this.kaCode4;
	}

	public void setKaCode4(Short kaCode4) {
		this.kaCode4 = kaCode4;
	}

	public Short getKaCode5() {
		return this.kaCode5;
	}

	public void setKaCode5(Short kaCode5) {
		this.kaCode5 = kaCode5;
	}

	public Short getKaCode6() {
		return this.kaCode6;
	}

	public void setKaCode6(Short kaCode6) {
		this.kaCode6 = kaCode6;
	}

	public Short getKaCode7() {
		return this.kaCode7;
	}

	public void setKaCode7(Short kaCode7) {
		this.kaCode7 = kaCode7;
	}

	public Short getKaCode8() {
		return this.kaCode8;
	}

	public void setKaCode8(Short kaCode8) {
		this.kaCode8 = kaCode8;
	}

	public Short getKaCode9() {
		return this.kaCode9;
	}

	public void setKaCode9(Short kaCode9) {
		this.kaCode9 = kaCode9;
	}

	public Short getKaCode10() {
		return this.kaCode10;
	}

	public void setKaCode10(Short kaCode10) {
		this.kaCode10 = kaCode10;
	}

	public String getKrName() {
		return krName;
	}

	public void setKrName(String krName) {
		this.krName = krName;
	}

	public String getKdName() {
		return kdName;
	}

	public void setKdName(String kdName) {
		this.kdName = kdName;
	}

	public String getIP() {
		if (kaIp11 == null)
			return "";
		StringBuffer sb = new StringBuffer();
		if (kaIp11.intValue() == 0) {
			sb.append("*");
		} else {
			if (kaIp11.intValue() == kaIp12.intValue()) {
				sb.append(kaIp11);
			} else {
				sb.append(kaIp11 + "-" + kaIp12);
			}
		}
		if (kaIp21.intValue() == 0) {
			sb.append(".*");
		} else {
			if (kaIp21.intValue() == kaIp22.intValue()) {
				sb.append("." + kaIp21);
			} else {
				sb.append("." + kaIp21 + "-" + kaIp22);
			}
		}
		if (kaIp31.intValue() == 0) {
			sb.append(".*");
		} else {
			if (kaIp31.intValue() == kaIp32.intValue()) {
				sb.append("." + kaIp31);
			} else {
				sb.append("." + kaIp31 + "-" + kaIp32);
			}
		}
		if (kaIp41.intValue() == 0) {
			sb.append(".*");
		} else {
			if (kaIp41.intValue() == kaIp42.intValue()) {
				sb.append("." + kaIp41);
			} else {
				sb.append("." + kaIp41 + "-" + kaIp42);
			}
		}
		return sb.toString();
	}

	public WkTTitle getTitle() {
		if (title == null) {
			DbchooseService dbchooseService = (DbchooseService) BeanFactory.getBean("dbchooseService");
			this.title = (WkTTitle) dbchooseService.get(WkTTitle.class, kaRid);
		}
		return title;
	}
}