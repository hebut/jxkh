package com.uniwin.framework.entity;

import java.util.Date;

import javax.persistence.Transient;

import org.iti.xypt.entity.Title;
import org.iti.xypt.service.XyptTitleService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.common.util.DateUtil;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.common.util.PinyinUtil;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

/**
 * WkTUser entity. @author MyEclipse Persistence Tools
 */
public class WkTUser implements java.io.Serializable {
	/**
	 * 
	 */
	// 表名：WK_T_USER
	private static final long serialVersionUID = 1L;
	// Fields
	public static final String SEX_MAN = "1";
	public static final String SEX_WOMEN = "2";
	public static final String BAND_YES = "1";
	public static final String BAND_NO = "0";
	public static final String AUTOENTER_YES = "1";
	public static final String AUTOENTER_NO = "0";
	public static final String MARRY_WEI = "0";// 未婚
	public static final String MARRY_YI = "1";// 已婚
	public static final String MARRY_LI = "2";// 离异
	public static final String MARRY_ZAI = "3";// 再婚
	public static final String COMPUTER_ONE = "1";// 计算机一级
	public static final String COMPUTER_TWO = "2";// 二级
	public static final String COMPUTER_THREE = "3";// 三级
	public static final String COMPUTER_FOUR = "4";// 四级
	public static final String SPOKEN_ONEA = "1";// 普通话一级甲等
	public static final String SPOKEN_ONEB = "2";// 普通话一级乙等
	public static final String SPOKEN_TWOA = "3";// 普通话二级甲等
	public static final String SPOKEN_TWOB = "4";// 普通话二级乙等
	public static final String SPOKEN_THREEA = "5";// 普通话三级甲等
	public static final String SPOKEN_THREEB = "6";// 普通话三级乙等
	public static final short TYPE_IN = 0,TYPE_OUT = 1;
	public static final int JOB_IN=0,JOB_OUT=1;//0表示用户为在编，1表示用户为外聘
	
	private Long kuId;
	private String kuLid;
	private String staffId;
	private Integer kuType;//用户是外聘还是在编
	private String title;
	private String titleRank;
	private String language;
	private String kuName;
	private String kuPasswd;
	private String kuRegdate;
	private String kuStatus;
	private String kuSex;
	private String kuBirthday;
	private String kuEmail;
	private String kuPhone;
	private String kuCompany;
	private String kuIntro;
	private Long kdId;
	private Integer kuLevel;
	private String kuLtime;
	private String kuRtime;
	private String kuLastaddr;
	private String kuOnline;
	private Integer kuLtimes;
	private Integer kuLimit;
	private String kuStyle;
	private String kuAutoshow;
	private String kuBindtype;
	private String kuBindaddr;
	private String kuQuestion;
	private String kuAnswer;
	private String kuAutoenter;
	private String kuSfzh;
	private String xiDept;
	private String yuDept;
	private WkTDept dept;
	private String kuIdentity;
	private String kuXuewei;
	private String kuEducational;
	private String kuPolitical;
	private String kuSchool;
	private String kuUsedname;
	private String kuNation;
	private String kuHealth;
	private String kuMarstatus;
	private String kuNativeplace;
	private String kuPartytime;
	private String kuHomeaddress;
	private String kuHometel;
	private String kuWorktel;
	private String kuQq;
	private String kuMsn;
	private String kuFax;
	private String kuHomepage;
	private String kuOthercontact;
	private String kuComputer;
	private String kuEnglish;
	private String kuSpoken;
	private String kuUsb;
	private String kuPath;
	private String KuNamePinyin;
	
	private short type;
	
	Title titleEnty;

	public String getKuNamePinyin() {
		return KuNamePinyin;
	}

	public void setKuNamePinyin(String kuNamePinyin) {
		KuNamePinyin = kuNamePinyin;
	}

	// Constructors
	/** default constructor */
	public WkTUser() {
		Date d = new Date();
		this.setKuAutoenter("0");
		this.setKuAutoshow("0");
		this.setKuBindtype("0");
		this.setKuLevel(20);
		this.setKuLimit(0);
		this.setKuLtimes(0);
		this.setKuStatus("1");
		this.setKuStyle("default");
		this.setKuPasswd(EncodeUtil.encodeByMD5(""));
		this.setKuRegdate(DateUtil.getDateTimeString(d));
	}

	/** minimal constructor */
	public WkTUser(Long kuId, String kuLid, String kuStyle) {
		this.kuId = kuId;
		this.kuLid = kuLid;
		this.kuStyle = kuStyle;
	}

	/** full constructor */
	public WkTUser(Long kuId, String kuLid, String staffId, Integer kuType,String title,
			String titleRank, String kuName, String kuPasswd, String kuRegdate,
			String kuStatus, String kuSex, String kuBirthday, String kuEmail,
			String kuPhone, String kuCompany, String kuIntro, Long kdId,
			Integer kuLevel, String kuLtime, String kuRtime, String kuLastaddr,
			String kuOnline, Integer kuLtimes, Integer kuLimit, String kuStyle,
			String kuAutoshow, String kuBindtype, String kuBindaddr,
			String kuQuestion, String kuAnswer, String kuAutoenter,
			String kuSfzh, String xiDept, String yuDept, WkTDept dept,
			String kuIdentity, String kuXuewei, String kuEducational,
			String kuPolitical, String kuSchool, String kuUsedname,
			String kuNation, String kuHealth, String kuMarstatus,
			String kuNativeplace, String kuPartytime, String kuHomeaddress,
			String kuHometel, String kuWorktel, String kuQq, String kuMsn,
			String kuFax, String kuHomepage, String kuOthercontact,
			String kuComputer, String kuEnglish, String kuSpoken, String kuUsb,
			String kuPath, String kuNamePinyin, short type) {
		super();
		this.kuId = kuId;
		this.kuLid = kuLid;
		this.staffId = staffId;
		this.kuType = kuType;
		this.title = title;
		this.titleRank = titleRank;
		this.kuName = kuName;
		this.kuPasswd = kuPasswd;
		this.kuRegdate = kuRegdate;
		this.kuStatus = kuStatus;
		this.kuSex = kuSex;
		this.kuBirthday = kuBirthday;
		this.kuEmail = kuEmail;
		this.kuPhone = kuPhone;
		this.kuCompany = kuCompany;
		this.kuIntro = kuIntro;
		this.kdId = kdId;
		this.kuLevel = kuLevel;
		this.kuLtime = kuLtime;
		this.kuRtime = kuRtime;
		this.kuLastaddr = kuLastaddr;
		this.kuOnline = kuOnline;
		this.kuLtimes = kuLtimes;
		this.kuLimit = kuLimit;
		this.kuStyle = kuStyle;
		this.kuAutoshow = kuAutoshow;
		this.kuBindtype = kuBindtype;
		this.kuBindaddr = kuBindaddr;
		this.kuQuestion = kuQuestion;
		this.kuAnswer = kuAnswer;
		this.kuAutoenter = kuAutoenter;
		this.kuSfzh = kuSfzh;
		this.xiDept = xiDept;
		this.yuDept = yuDept;
		this.dept = dept;
		this.kuIdentity = kuIdentity;
		this.kuXuewei = kuXuewei;
		this.kuEducational = kuEducational;
		this.kuPolitical = kuPolitical;
		this.kuSchool = kuSchool;
		this.kuUsedname = kuUsedname;
		this.kuNation = kuNation;
		this.kuHealth = kuHealth;
		this.kuMarstatus = kuMarstatus;
		this.kuNativeplace = kuNativeplace;
		this.kuPartytime = kuPartytime;
		this.kuHomeaddress = kuHomeaddress;
		this.kuHometel = kuHometel;
		this.kuWorktel = kuWorktel;
		this.kuQq = kuQq;
		this.kuMsn = kuMsn;
		this.kuFax = kuFax;
		this.kuHomepage = kuHomepage;
		this.kuOthercontact = kuOthercontact;
		this.kuComputer = kuComputer;
		this.kuEnglish = kuEnglish;
		this.kuSpoken = kuSpoken;
		this.kuUsb = kuUsb;
		this.kuPath = kuPath;
		KuNamePinyin = kuNamePinyin;
		this.type = type;
		try {
			setKuNamePinyin(PinyinUtil.getPinYin(kuName));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			setKuNamePinyin(kuName);
		}
	}

	// Property accessors
	public String getXiDept() {
		if (xiDept == null) {
			if (getDept().getKdLevel().intValue() == WkTDept.GRADE_XI.intValue()) {
				xiDept = getDept().getKdName();
			} else {
				xiDept = "";
			}
		}
		return xiDept;
	}
	

	public String getYuDept() {
		if (kdId!=0){          //2011-3-19 dongyf  加一个判断，当为管理员时，返回空符号
		  if (yuDept == null) {
			if (getDept().getKdLevel().intValue() == WkTDept.GRADE_XI.intValue()) {
				yuDept = getDept().getPdept().getKdName();					
			} else if (getDept().getKdLevel().intValue() == WkTDept.GRADE_YUAN.intValue()) {
				yuDept = getDept().getKdName();			
			}else{
				yuDept=" ";			
			}
		  }	else{
			yuDept=" ";	 
		  }
		}
		return yuDept;
	}

	public String getXxDept() {
		if(getDept()!=null){
			return getDept().getSchDept().getKdName();
		}else{
			return null;
		}
		
	}
	
	

	public Title getTitleEnty() {
		if(titleEnty==null){
			XyptTitleService titleService=(XyptTitleService)BeanFactory.getBean("xypttitleService");
			this.titleEnty=(Title)titleService.findBytid(title);
    	}
    	return titleEnty;
	}


	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKuLid() {
		return this.kuLid;
	}

	public void setKuLid(String kuLid) {
		this.kuLid = kuLid;
	}	

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}	
	public Integer getKuType() {
		return kuType;
	}

	public void setKuType(Integer kuType) {
		this.kuType = kuType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleRank() {
		return titleRank;
	}

	public void setTitleRank(String titleRank) {
		this.titleRank = titleRank;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getKuName() {
		return this.kuName;
	}

	public void setKuName(String kuName){
		this.kuName = kuName;
		try {
			setKuNamePinyin(PinyinUtil.getPinYin(kuName));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			setKuNamePinyin(kuName);
		}
	}

	public String getKuPasswd() {
		return this.kuPasswd;
	}

	public void setKuPasswd(String kuPasswd) {
		this.kuPasswd = kuPasswd;
	}

	public String getKuRegdate() {
		return this.kuRegdate;
	}

	public void setKuRegdate(String kuRegdate) {
		this.kuRegdate = kuRegdate;
	}

	public String getKuStatus() {
		return this.kuStatus;
	}

	public void setKuStatus(String kuStatus) {
		this.kuStatus = kuStatus;
	}

	public String getKuSex() {
		return this.kuSex;
	}

	public void setKuSex(String kuSex) {
		this.kuSex = kuSex;
	}

	public String getKuBirthday() {
		return this.kuBirthday;
	}

	public void setKuBirthday(String kuBirthday) {
		this.kuBirthday = kuBirthday;
	}

	public String getKuEmail() {
		return this.kuEmail;
	}

	public void setKuEmail(String kuEmail) {
		this.kuEmail = kuEmail;
	}

	public String getKuPhone() {
		return this.kuPhone;
	}

	public void setKuPhone(String kuPhone) {
		this.kuPhone = kuPhone;
	}

	public String getKuCompany() {
		return this.kuCompany;
	}

	public void setKuCompany(String kuCompany) {
		this.kuCompany = kuCompany;
	}

	public String getKuIntro() {
		return kuIntro;
	}

	public void setKuIntro(String kuIntro) {
		this.kuIntro = kuIntro;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Integer getKuLevel() {
		return this.kuLevel;
	}

	public void setKuLevel(Integer kuLevel) {
		this.kuLevel = kuLevel;
	}

	public String getKuLtime() {
		return this.kuLtime;
	}

	public void setKuLtime(String kuLtime) {
		this.kuLtime = kuLtime;
	}

	public String getKuRtime() {
		return this.kuRtime;
	}

	public void setKuRtime(String kuRtime) {
		this.kuRtime = kuRtime;
	}

	public String getKuLastaddr() {
		return this.kuLastaddr;
	}

	public void setKuLastaddr(String kuLastaddr) {
		this.kuLastaddr = kuLastaddr;
	}

	public String getKuOnline() {
		return this.kuOnline;
	}

	public void setKuOnline(String kuOnline) {
		this.kuOnline = kuOnline;
	}

	public Integer getKuLtimes() {
		return this.kuLtimes;
	}

	public void setKuLtimes(Integer kuLtimes) {
		this.kuLtimes = kuLtimes;
	}

	public Integer getKuLimit() {
		return this.kuLimit;
	}

	public void setKuLimit(Integer kuLimit) {
		this.kuLimit = kuLimit;
	}

	public String getKuStyle() {
		return this.kuStyle;
	}

	public void setKuStyle(String kuStyle) {
		this.kuStyle = kuStyle;
	}

	public String getKuAutoshow() {
		return this.kuAutoshow;
	}

	public void setKuAutoshow(String kuAutoshow) {
		this.kuAutoshow = kuAutoshow;
	}

	public String getKuBindtype() {
		return this.kuBindtype;
	}

	public void setKuBindtype(String kuBindtype) {
		this.kuBindtype = kuBindtype;
	}

	public String getKuBindaddr() {
		return this.kuBindaddr;
	}

	public void setKuBindaddr(String kuBindaddr) {
		this.kuBindaddr = kuBindaddr;
	}

	public String getKuQuestion() {
		return this.kuQuestion;
	}

	public void setKuQuestion(String kuQuestion) {
		this.kuQuestion = kuQuestion;
	}

	public String getKuAnswer() {
		return this.kuAnswer;
	}

	public void setKuAnswer(String kuAnswer) {
		this.kuAnswer = kuAnswer;
	}

	public String getKuAutoenter() {
		return this.kuAutoenter;
	}

	public void setKuAutoenter(String kuAutoenter) {
		this.kuAutoenter = kuAutoenter;
	}

	public WkTDept getDept() {
		if (dept == null) {
			DepartmentService deptService = (DepartmentService) BeanFactory.getBean("departmentService");
			dept = (WkTDept) deptService.get(WkTDept.class, kdId);
		}
		return dept;
	}
	public WkTDept getDept1() {
		return getDept();
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	public String getKuSfzh() {
		return kuSfzh;
	}

	public void setKuSfzh(String kuSfzh) {
		this.kuSfzh = kuSfzh;
	}

	public String getKuIdentity() {
		return kuIdentity;
	}

	public void setKuIdentity(String kuIdentity) {
		this.kuIdentity = kuIdentity;
	}

	public String getKuEducational() {
		return kuEducational;
	}

	public void setKuEducational(String kuEducational) {
		this.kuEducational = kuEducational;
	}

	public String getKuPolitical() {
		return kuPolitical;
	}

	public void setKuPolitical(String kuPolitical) {
		this.kuPolitical = kuPolitical;
	}

	public String getKuSchool() {
		return kuSchool;
	}

	public void setKuSchool(String kuSchool) {
		this.kuSchool = kuSchool;
	}

	public String getKuXuewei() {
		return kuXuewei;
	}

	public void setKuXuewei(String kuXuewei) {
		this.kuXuewei = kuXuewei;
	}

	public String getKuUsedname() {
		return kuUsedname;
	}

	public void setKuUsedname(String kuUsedname) {
		this.kuUsedname = kuUsedname;
	}

	public String getKuNation() {
		return kuNation;
	}

	public void setKuNation(String kuNation) {
		this.kuNation = kuNation;
	}

	public String getKuHealth() {
		return kuHealth;
	}

	public void setKuHealth(String kuHealth) {
		this.kuHealth = kuHealth;
	}

	public String getKuMarstatus() {
		return kuMarstatus;
	}

	public void setKuMarstatus(String kuMarstatus) {
		this.kuMarstatus = kuMarstatus;
	}

	public String getKuNativeplace() {
		return kuNativeplace;
	}

	public void setKuNativeplace(String kuNativeplace) {
		this.kuNativeplace = kuNativeplace;
	}

	public String getKuPartytime() {
		return kuPartytime;
	}

	public void setKuPartytime(String kuPartytime) {
		this.kuPartytime = kuPartytime;
	}

	public String getKuHomeaddress() {
		return kuHomeaddress;
	}

	public void setKuHomeaddress(String kuHomeaddress) {
		this.kuHomeaddress = kuHomeaddress;
	}

	public String getKuHometel() {
		return kuHometel;
	}

	public void setKuHometel(String kuHometel) {
		this.kuHometel = kuHometel;
	}

	public String getKuWorktel() {
		return kuWorktel;
	}

	public void setKuWorktel(String kuWorktel) {
		this.kuWorktel = kuWorktel;
	}

	public String getKuQq() {
		return kuQq;
	}

	public void setKuQq(String kuQq) {
		this.kuQq = kuQq;
	}

	public String getKuMsn() {
		return kuMsn;
	}

	public void setKuMsn(String kuMsn) {
		this.kuMsn = kuMsn;
	}

	public String getKuHomepage() {
		return kuHomepage;
	}

	public void setKuHomepage(String kuHomepage) {
		this.kuHomepage = kuHomepage;
	}

	public String getKuOthercontact() {
		return kuOthercontact;
	}

	public void setKuOthercontact(String kuOthercontact) {
		this.kuOthercontact = kuOthercontact;
	}

	public String getKuComputer() {
		return kuComputer;
	}

	public void setKuComputer(String kuComputer) {
		this.kuComputer = kuComputer;
	}

	public String getKuEnglish() {
		return kuEnglish;
	}

	public void setKuEnglish(String kuEnglish) {
		this.kuEnglish = kuEnglish;
	}

	public String getKuSpoken() {
		return kuSpoken;
	}

	public void setKuSpoken(String kuSpoken) {
		this.kuSpoken = kuSpoken;
	}

	public void setXiDept(String xiDept) {
		this.xiDept = xiDept;
	}

	public void setYuDept(String yuDept) {
		this.yuDept = yuDept;
	}

	public String getKuFax() {
		return kuFax;
	}

	public void setKuFax(String kuFax) {
		this.kuFax = kuFax;
	}

	public String getKuUsb() {
		return kuUsb;
	}

	public void setKuUsb(String kuUsb) {
		this.kuUsb = kuUsb;
	}

	public String getKuPath() {
		return kuPath;
	}

	public void setKuPath(String kuPath) {
		this.kuPath = kuPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kuId == null) ? 0 : kuId.hashCode());
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
		WkTUser other = (WkTUser) obj;
		if (kuId == null) {
			if (other.kuId != null)
				return false;
		} else if (!kuId.equals(other.kuId))
			return false;
		return true;
	}
}