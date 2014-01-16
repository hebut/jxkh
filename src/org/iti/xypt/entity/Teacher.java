package org.iti.xypt.entity;

/*import org.iti.xypt.service.XyUserRoleService;
//import org.jfree.chart.title.Title;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;*/
import org.iti.xypt.service.XyUserRoleService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;
/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */

public class Teacher implements java.io.Serializable {

	public static int ADVISOR_NONE=0,ADVISOR_SD=1,ADVISOR_BD=2,ADVISOR_BOTH=3;
	public static Short TYPE_GL=0,TYPE_SL=1,TYPE_QT=2;//职工性质（0公立，1私立，2其他）
	public static Short HIRE_HT=0,HIRE_PY=1,HIRE_LS=2;//用工性质（0合同制，1聘用制，2临时工）
	public static Short SORT_GB=0,SORT_GR=1,SORT_QZ=2;//职工类别（0干部，1工人，2群众）
	public static Short STATE_ZZ=0,STATE_LZ=1,STATE_SX=2,STATE_TX=3;//职工状态（0在职，1离职，2实习，3退休）
	public static Short  QUALIFY_ZERO=0,QUALIFY_ONE=1, QUALIFY_TWO=2, QUALIFY_THREE=3, QUALIFY_FOUR=4, QUALIFY_FIVE=5;//教师资格证（0幼儿园，1小学，2初级中学，3高级中学，4中等职业，5高等学校）
	
	// Fields

	private Long kuId;
	private String thId;
	private String thTitle;
	private Long kdId;
	private String thSearch;
	private Integer thAdvisor;
	private Float thWork;
	private Short thRetire;
	//hlj 
	private String thType;
	private String thStart;
	private String thHire;
	private String thEnter;
	private Long csId;//从事专业  GH_CSZY表
	private String thSort;
	private String thState;
	private String thPosition;
	private String thQualify;
	private String thEmplqualify;
	private String thEmpllevel;
	private String thEmplposition;
	private String thEmplTime;
	private String thEmplstate;
	private String thEmplend;
	private String thDeempltime;
	private String thDeemplno;
	private String thDeemplre;
	private String thLeare;
	private String thLeatime;
	private String thThought;
	private Long sgId;//发生事故处分 GH_SGCF表
	private Long thFiredu;
	private Long thFirdeg;
	private String thFiryear;
	private String thFirschool;
	private String thFirgratime;
	private String thFirsub;//第一学历主修专业 专业表id
	private String thFirsesub;
	private String thFiredunum;
	private String thSupeyear;
	private String thSupschool;
	private String thSupgratime;
	private String thSupsub;
	private String thSupsesub;
	private String thSupedunum;
	private String thZctime;
	private String thZcnum;
	private String thZcpubdept;
	private String thZcpubtime;
	private String thZcquasym;
	private String thZcidentdept;
	
	//新加 
	private String thEducation;
	private String thSchool;
	private String thResearch;
	private String thOthers;

	WkTUser user;
	Title title;
	
	/**
	 * <li>功能描述：获得某个学校教师角色编号。
	 * @param kdid 学校编号
	 * @return
	 * Long 
	 * @author DaLei
	 */
	public static Long getRoleId(Long kdid) {		
			XyUserRoleService xyUserRoleService=(XyUserRoleService)BeanFactory.getBean("xyUserRoleService");
			return xyUserRoleService.getRoleId("教师", kdid);		
	}
	
	/**
	 * 查询教师在某个学校所属单位
	 * @param kdid
	 * @return
	 */
	public WkTDept getDept(Long kdid){
		Long rid=Teacher.getRoleId(kdid);
		XyUserRoleService xyUserRoleService=(XyUserRoleService)BeanFactory.getBean("xyUserRoleService");
		XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, new XyUserroleId(rid,kuId));
		return (WkTDept) xyUserRoleService.get(WkTDept.class,xyr.getKdId());
	}
	
	/**
	 * <li>功能描述：获得教师所在系。
	 * @param kdid 学校编号
	 * @return
	 * String 
	 * @author DaLei
	 */
	public String getXiDept(Long kdid){
		 Long rid=Teacher.getRoleId(kdid);
		 XyUserRoleService xyUserRoleService=(XyUserRoleService)BeanFactory.getBean("xyUserRoleService");
		 XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, new XyUserroleId(rid,kuId));
		 WkTDept d=(WkTDept) xyUserRoleService.get(WkTDept.class,xyr.getKdId());
		 if(d.getKdLevel().intValue()==WkTDept.GRADE_XI.intValue()){
				return d.getKdName();
		 }else{
				return "";
		 } 
	}
	
	/**
	 * <li>功能描述：获得用户教师角色所在学院。
	 * @param kdid 学校编号
	 * @return
	 * String 
	 * @author DaLei
	 */
	public String getYuDept(Long kdid){
		 Long rid=Teacher.getRoleId(kdid);
		 XyUserRoleService xyUserRoleService=(XyUserRoleService)BeanFactory.getBean("xyUserRoleService");
		 XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, new XyUserroleId(rid,kuId));
		 WkTDept d=(WkTDept) xyUserRoleService.get(WkTDept.class, xyr.getKdId());
		 if(d.getKdLevel().intValue()==WkTDept.GRADE_XI.intValue()){
				return d.getPdept().getKdName();
		 }else if(d.getKdLevel().intValue()==WkTDept.GRADE_YUAN.intValue()){
				return d.getKdName();
		 }	
		 return "";
	}
	
	/**
	 * <li>功能描述：获得用户教师角色所在学校。
	 * @param kdid 学校编号
	 * @return
	 * String 
	 * @author DaLei
	 */
	public String getSchDept(Long kdid){
		 Long rid=Teacher.getRoleId(kdid);
		 XyUserRoleService xyUserRoleService=(XyUserRoleService)BeanFactory.getBean("xyUserRoleService");
		 XyUserrole xyr=(XyUserrole) xyUserRoleService.get(XyUserrole.class, new XyUserroleId(rid,kuId));
		 WkTDept d=(WkTDept) xyUserRoleService.get(WkTDept.class, xyr.getKdId());
		 if(d.getKdLevel().intValue()==WkTDept.GRADE_XI.intValue()){
				return d.getPdept().getPdept().getKdName();
		 }else if(d.getKdLevel().intValue()==WkTDept.GRADE_YUAN.intValue()){
				return d.getPdept().getKdName();
		 }	
		 return d.getPdept().getKdName();
	}

	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
    public Title getTitle(){
    	if(title==null){
    		UserService userService=(UserService)BeanFactory.getBean("userService");
			this.title=(Title)userService.get(Title.class, thTitle);
    	}
    	return title;
    }
	public void setUser(WkTUser user) {		
		this.user = user;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	/** default constructor */
	public Teacher() {
	}

	/** full constructor */
	public Teacher(Long kuId, String thId, String thTitle,Long kdId,String thSearch,Integer thAdvisor,
			String thType,String thStart,String thHire,String thEnter,Long csId,String thSort,String thState,
			String thPosition,String thQualify,String thEmplqualify,String thEmpllevel,String thEmplposition,
			String thEmplTime,String thEmplstate,String thEmplend, String thDeempltime,String thDeemplno,
			String thDeemplre,String thLeare,String thLeatime,String thThought, Long sgId,Long thFiredu,
			Long thFirdeg,String thFiryear,String thFirschool,String thFirgratime,String thFirsub,String thFirsesub,String thFiredunum,
			String thSupeyear,String thSupschool,String thSupgratime,String thSupsub,String thSupsesub,
			String thSupedunum,String thZctime,String thZcnum,String thZcpubdept,String thZcpubtime,String thZcquasym,
			String thZcidentdept,String thEducation,String thSchool,String thResearch,String thOthers) {
		this.kuId = kuId;
		this.thId = thId;
		this.thTitle = thTitle;
		this.kdId = kdId;
		this.thSearch = thSearch;
		this.thAdvisor=thAdvisor;
		this.thType = thType;
		this.thStart = thStart;
		this.thHire = thHire;
		this.thEnter = thEnter;
		this.csId = csId;
		this.thSort = thSort;
		this.thState = thState;
		this.thPosition = thPosition;
		this.thQualify = thQualify;
		this.thEmplqualify = thEmplqualify;
		this.thEmpllevel = thEmpllevel;
		this.thEmplposition = thEmplposition;
		this.thEmplTime = thEmplTime;
		this.thEmplstate = thEmplstate;
		this.thEmplend = thEmplend;
		this.thDeempltime = thDeempltime;
		this.thDeemplno = thDeemplno;
		this.thDeemplre = thDeemplre;
		this.thLeare = thLeare;
		this.thLeatime = thLeatime;
		this.thThought = thThought;
		this.sgId = sgId;
		this.thFiredu = thFiredu;
		this.thFirdeg = thFirdeg;
		this.thFiryear = thFiryear;
		this.thFirschool = thFirschool;
		this.thFirgratime = thFirgratime;
		this.thFirsub =thFirsub;
		this.thFirsesub=thFirsesub;
		this.thFiredunum = thFiredunum;
		this.thSupeyear = thSupeyear;
		this.thSupschool = thSupschool;
		this.thSupgratime = thSupgratime;
		this.thSupsub = thSupsub;
		this.thSupsesub =thSupsesub;
		this.thSupedunum = thSupedunum;
		this.thZctime = thZctime;
		this.thZcnum = thZcnum;
		this.thZcpubdept = thZcpubdept;
		this.thZcpubtime = thZcpubtime;
		this.thZcquasym = thZcquasym;
		this.thZcidentdept = thZcidentdept;
		this.thEducation=thEducation;
		this.thSchool=thSchool;
		this.thResearch=thResearch;
		this.thOthers=thOthers;
	}

	// Property accessors


	public String getThSearch() {
		return thSearch;
	}

	public void setThSearch(String thSearch) {
		this.thSearch = thSearch;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getThId() {
		return this.thId;
	}

	public void setThId(String thId) {
		this.thId = thId;
	}

	public String getThTitle() {
		return this.thTitle;
	}

	public void setThTitle(String thTitle) {
		this.thTitle = thTitle;
	}

	public Integer getThAdvisor() {
		return thAdvisor;
	}

	public void setThAdvisor(Integer thAdvisor) {
		this.thAdvisor = thAdvisor;
	}
	public String getThType() {
		return thType;
	}

	public void setThType(String thType) {
		this.thType = thType;
	}

	public String getThStart() {
		return thStart;
	}

	public void setThStart(String thStart) {
		this.thStart = thStart;
	}

	public String getThHire() {
		return thHire;
	}

	public void setThHire(String thHire) {
		this.thHire = thHire;
	}

	public String getThEnter() {
		return thEnter;
	}

	public void setThEnter(String thEnter) {
		this.thEnter = thEnter;
	}

	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public String getThSort() {
		return thSort;
	}

	public void setThSort(String thSort) {
		this.thSort = thSort;
	}
	public String getThSupsesub() {
		return thSupsesub;
	}

	public void setThSupsesub(String thSupsesub) {
		this.thSupsesub = thSupsesub;
	}


	public String getThState() {
		return thState;
	}

	public void setThState(String thState) {
		this.thState = thState;
	}

	public String getThPosition() {
		return thPosition;
	}

	public void setThPosition(String thPosition) {
		this.thPosition = thPosition;
	}

	public String getThQualify() {
		return thQualify;
	}

	public void setThQualify(String thQualify) {
		this.thQualify = thQualify;
	}

	public String getThEmplqualify() {
		return thEmplqualify;
	}

	public void setThEmplqualify(String thEmplqualify) {
		this.thEmplqualify = thEmplqualify;
	}

	public String getThEmpllevel() {
		return thEmpllevel;
	}

	public void setThEmpllevel(String thEmpllevel) {
		this.thEmpllevel = thEmpllevel;
	}

	public String getThEmplposition() {
		return thEmplposition;
	}

	public void setThEmplposition(String thEmplposition) {
		this.thEmplposition = thEmplposition;
	}

	public String getThEmplTime() {
		return thEmplTime;
	}

	public void setThEmplTime(String thEmplTime) {
		this.thEmplTime = thEmplTime;
	}

	public String getThEmplstate() {
		return thEmplstate;
	}

	public void setThEmplstate(String thEmplstate) {
		this.thEmplstate = thEmplstate;
	}

	public String getThEmplend() {
		return thEmplend;
	}

	public void setThEmplend(String thEmplend) {
		this.thEmplend = thEmplend;
	}

	public String getThDeempltime() {
		return thDeempltime;
	}

	public void setThDeempltime(String thDeempltime) {
		this.thDeempltime = thDeempltime;
	}

	public String getThDeemplno() {
		return thDeemplno;
	}

	public void setThDeemplno(String thDeemplno) {
		this.thDeemplno = thDeemplno;
	}

	public String getThDeemplre() {
		return thDeemplre;
	}

	public void setThDeemplre(String thDeemplre) {
		this.thDeemplre = thDeemplre;
	}

	public String getThLeare() {
		return thLeare;
	}

	public void setThLeare(String thLeare) {
		this.thLeare = thLeare;
	}

	public String getThLeatime() {
		return thLeatime;
	}

	public void setThLeatime(String thLeatime) {
		this.thLeatime = thLeatime;
	}

	public String getThThought() {
		return thThought;
	}

	public void setThThought(String thThought) {
		this.thThought = thThought;
	}

	public Long getSgId() {
		return sgId;
	}

	public void setSgId(Long sgId) {
		this.sgId = sgId;
	}

	public Long getThFiredu() {
		return thFiredu;
	}

	public void setThFiredu(Long thFiredu) {
		this.thFiredu = thFiredu;
	}

	public Long getThFirdeg() {
		return thFirdeg;
	}

	public void setThFirdeg(Long thFirdeg) {
		this.thFirdeg = thFirdeg;
	}

	public String getThFiryear() {
		return thFiryear;
	}

	public void setThFiryear(String thFiryear) {
		this.thFiryear = thFiryear;
	}

	public String getThFirschool() {
		return thFirschool;
	}

	public void setThFirschool(String thFirschool) {
		this.thFirschool = thFirschool;
	}

	public String getThFirgratime() {
		return thFirgratime;
	}

	public void setThFirgratime(String thFirgratime) {
		this.thFirgratime = thFirgratime;
	}

	public String getThFirsub() {
		return thFirsub;
	}

	public void setThFirsub(String thFirsub) {
		this.thFirsub = thFirsub;
	}

	public String getThFiredunum() {
		return thFiredunum;
	}

	public void setThFiredunum(String thFiredunum) {
		this.thFiredunum = thFiredunum;
	}

	public String getThSupeyear() {
		return thSupeyear;
	}

	public void setThSupeyear(String thSupeyear) {
		this.thSupeyear = thSupeyear;
	}

	public String getThSupschool() {
		return thSupschool;
	}

	public void setThSupschool(String thSupschool) {
		this.thSupschool = thSupschool;
	}

	public String getThSupgratime() {
		return thSupgratime;
	}
	public String getThFirsesub() {
		return thFirsesub;
	}

	public void setThFirsesub(String thFirsesub) {
		this.thFirsesub = thFirsesub;
	}

	
	public void setThSupgratime(String thSupgratime) {
		this.thSupgratime = thSupgratime;
	}

	public String getThSupsub() {
		return thSupsub;
	}

	public void setThSupsub(String thSupsub) {
		this.thSupsub = thSupsub;
	}

	public String getThSupedunum() {
		return thSupedunum;
	}

	public void setThSupedunum(String thSupedunum) {
		this.thSupedunum = thSupedunum;
	}

	public String getThZctime() {
		return thZctime;
	}

	public void setThZctime(String thZctime) {
		this.thZctime = thZctime;
	}

	public String getThZcnum() {
		return thZcnum;
	}

	public void setThZcnum(String thZcnum) {
		this.thZcnum = thZcnum;
	}

	public String getThZcpubdept() {
		return thZcpubdept;
	}

	public void setThZcpubdept(String thZcpubdept) {
		this.thZcpubdept = thZcpubdept;
	}

	public String getThZcpubtime() {
		return thZcpubtime;
	}

	public void setThZcpubtime(String thZcpubtime) {
		this.thZcpubtime = thZcpubtime;
	}

	public String getThZcquasym() {
		return thZcquasym;
	}

	public void setThZcquasym(String thZcquasym) {
		this.thZcquasym = thZcquasym;
	}

	public String getThZcidentdept() {
		return thZcidentdept;
	}

	public void setThZcidentdept(String thZcidentdept) {
		this.thZcidentdept = thZcidentdept;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	
	
	public Short getThRetire() {
		return thRetire;
	}

	public void setThRetire(Short thRetire) {
		this.thRetire = thRetire;
	}

	public Float getThWork() {
		return thWork;
	}

	public void setThWork(Float thWork) {
		this.thWork = thWork;
	}

	public String getThEducation() {
		return thEducation;
	}

	public void setThEducation(String thEducation) {
		this.thEducation = thEducation;
	}

	public String getThSchool() {
		return thSchool;
	}

	public void setThSchool(String thSchool) {
		this.thSchool = thSchool;
	}

	public String getThResearch() {
		return thResearch;
	}

	public void setThResearch(String thResearch) {
		this.thResearch = thResearch;
	}

	public String getThOthers() {
		return thOthers;
	}

	public void setThOthers(String thOthers) {
		this.thOthers = thOthers;
	}

}