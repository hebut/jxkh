package org.iti.xypt.entity;


import org.iti.xypt.service.XyClassService;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;
/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields
	public static final String[] NORMALS={"正常","休学","复学","退学","留级","延长学习年限","免修","其他"};
	
	public static final Short STATUS_NORMAL=0;

	private Long kuId;
	private String stId;	
	private String stClass;
	private Integer stGrade;
	private Integer stBynf;
	private Integer stRxnf;
	private String stRoom;
	private String stAddress;
	private String stFname;
	private String stFrelation;
	private String stFwork;
	private String stFphone;
	private String stMname;
	private String stMrelation;
	private String stMwork;
	private String stMphon;
	private String stSnamefirst;
	private String stSrelationfirst;
	private String stSworkfirst;
	private String stSphonefirst;
	private String stSnamesecond;
	private String stSrelationsecond;
	private String stSworksecond;
	private String stSphonesecond;
	private String stSnamethird;
	private String stSrelationthird;
	private String stSworkthird;
	private String stSphonethird;
	private String stPostcode;
	private String  stPeople;
	private String  stTall;
	private String stOldhome;
	private Short stNormal;
	private Long clId;
	WkTUser user;
	XyClass xyClass;

	public XyClass getXyClass() {
		if(xyClass==null){
			XyClassService xyClassService=(XyClassService)BeanFactory.getBean("xyClassService");
			xyClass=(XyClass) xyClassService.get(XyClass.class, clId);
		}
		return xyClass;
	}

	public void setXyClass(XyClass xyClass) {
		this.xyClass = xyClass;
	}

	public WkTUser getUser() {
		if(user==null){
			UserService userService =(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public void setUser(WkTUser user) {
		
		this.user = user;
	}

	/** default constructor */
	public Student() {
		this.setStNormal(Student.STATUS_NORMAL); 
	}

	/** full constructor */
	public Student(Long kuId, String stId, String stClass, Integer stGrade,Integer stBynf,Integer stRxnf,Short stNormal,Long clId,String stRoom, String stAddress, String stFname,
			String stFrelation, String stFwork, String stFphone,
			String stMname, String stMrelation, String stMwork, String stMphon,
			String stSnamefirst, String stSrelationfirst, String stSworkfirst,
			String stSphonefirst, String stSnamesecond,
			String stSrelationsecond, String stSworksecond,
			String stSphonesecond, String stSnamethird,
			String stSrelationthird, String stSworkthird, String stSphonethird,String stPostcode,String stPeople,String stOldhome,String stTall) {
		this.kuId = kuId;
		this.stId = stId;
		this.stClass = stClass;
		this.stGrade = stGrade;
		this.stBynf = stBynf;
		this.stNormal=stNormal;
		this.clId=clId;
		this.stRxnf=stRxnf;
		this.stRoom = stRoom;
		this.stAddress = stAddress;
		this.stFname = stFname;
		this.stFrelation = stFrelation;
		this.stFwork = stFwork;
		this.stFphone = stFphone;
		this.stMname = stMname;
		this.stMrelation = stMrelation;
		this.stMwork = stMwork;
		this.stMphon = stMphon;
		this.stSnamefirst = stSnamefirst;
		this.stSrelationfirst = stSrelationfirst;
		this.stSworkfirst = stSworkfirst;
		this.stSphonefirst = stSphonefirst;
		this.stSnamesecond = stSnamesecond;
		this.stSrelationsecond = stSrelationsecond;
		this.stSworksecond = stSworksecond;
		this.stSphonesecond = stSphonesecond;
		this.stSnamethird = stSnamethird;
		this.stSrelationthird = stSrelationthird;
		this.stSworkthird = stSworkthird;
		this.stSphonethird = stSphonethird;
		this.stOldhome=stOldhome;
		this.stTall=stTall;
		this.stPostcode=stPostcode;
		this.stPeople=stPeople;
	}

	// Property accessors

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getStId() {
		return this.stId;
	}

	public void setStId(String stId) {
		this.stId = stId;
	}

	public String getStClass() {
		return this.stClass;
	}

	public void setStClass(String stClass) {
		this.stClass = stClass;
	}

	public Integer getStGrade() {
		return this.stGrade;
	}

	public void setStGrade(Integer stGrade) {
		this.stGrade = stGrade;
	}

	public Short getStNormal() {
		return stNormal;
	}

	public void setStNormal(Short stNormal) {
		this.stNormal = stNormal;
	}

	public Long getClId() {
		return clId;
	}

	public void setClId(Long id) {
		clId = id;
	}

	public String getStRoom() {
		return this.stRoom;
	}

	public void setStRoom(String stRoom) {
		this.stRoom = stRoom;
	}

	public String getStAddress() {
		return this.stAddress;
	}

	public void setStAddress(String stAddress) {
		this.stAddress = stAddress;
	}

	public String getStFname() {
		return this.stFname;
	}

	public void setStFname(String stFname) {
		this.stFname = stFname;
	}

	public String getStFrelation() {
		return this.stFrelation;
	}

	public void setStFrelation(String stFrelation) {
		this.stFrelation = stFrelation;
	}

	public String getStFwork() {
		return this.stFwork;
	}

	public void setStFwork(String stFwork) {
		this.stFwork = stFwork;
	}

	public String getStFphone() {
		return this.stFphone;
	}

	public void setStFphone(String stFphone) {
		this.stFphone = stFphone;
	}

	public String getStMname() {
		return this.stMname;
	}

	public void setStMname(String stMname) {
		this.stMname = stMname;
	}

	public String getStMrelation() {
		return this.stMrelation;
	}

	public void setStMrelation(String stMrelation) {
		this.stMrelation = stMrelation;
	}

	public String getStMwork() {
		return this.stMwork;
	}

	public void setStMwork(String stMwork) {
		this.stMwork = stMwork;
	}

	public String getStMphon() {
		return this.stMphon;
	}

	public void setStMphon(String stMphon) {
		this.stMphon = stMphon;
	}

	public String getStSnamefirst() {
		return this.stSnamefirst;
	}

	public void setStSnamefirst(String stSnamefirst) {
		this.stSnamefirst = stSnamefirst;
	}

	public String getStSrelationfirst() {
		return this.stSrelationfirst;
	}

	public void setStSrelationfirst(String stSrelationfirst) {
		this.stSrelationfirst = stSrelationfirst;
	}

	public String getStSworkfirst() {
		return this.stSworkfirst;
	}

	public void setStSworkfirst(String stSworkfirst) {
		this.stSworkfirst = stSworkfirst;
	}

	public String getStSphonefirst() {
		return this.stSphonefirst;
	}

	public void setStSphonefirst(String stSphonefirst) {
		this.stSphonefirst = stSphonefirst;
	}

	public String getStSnamesecond() {
		return this.stSnamesecond;
	}

	public void setStSnamesecond(String stSnamesecond) {
		this.stSnamesecond = stSnamesecond;
	}

	public String getStSrelationsecond() {
		return this.stSrelationsecond;
	}

	public void setStSrelationsecond(String stSrelationsecond) {
		this.stSrelationsecond = stSrelationsecond;
	}

	public String getStSworksecond() {
		return this.stSworksecond;
	}

	public void setStSworksecond(String stSworksecond) {
		this.stSworksecond = stSworksecond;
	}

	public String getStSphonesecond() {
		return this.stSphonesecond;
	}

	public void setStSphonesecond(String stSphonesecond) {
		this.stSphonesecond = stSphonesecond;
	}

	public String getStSnamethird() {
		return this.stSnamethird;
	}

	public void setStSnamethird(String stSnamethird) {
		this.stSnamethird = stSnamethird;
	}

	public String getStSrelationthird() {
		return this.stSrelationthird;
	}

	public void setStSrelationthird(String stSrelationthird) {
		this.stSrelationthird = stSrelationthird;
	}

	public String getStSworkthird() {
		return this.stSworkthird;
	}

	public void setStSworkthird(String stSworkthird) {
		this.stSworkthird = stSworkthird;
	}

	public String getStSphonethird() {
		return this.stSphonethird;
	}

	public void setStSphonethird(String stSphonethird) {
		this.stSphonethird = stSphonethird;
	}
	public String getStPostcode() {
		return stPostcode;
	}
	public void setStPostcode(String stPostcode) {
		this.stPostcode = stPostcode;
	}
	public String getStPeople() {
		return stPeople;
	}
	public void setStPeople(String stPeople) {
		this.stPeople = stPeople;
	}
	public String getStTall() {
		return stTall;
	}
	public void setStTall(String stTall) {
		this.stTall = stTall;
	}
	public String getStOldhome() {
		return stOldhome;
	}
	public void setStOldhome(String stOldhome) {
		this.stOldhome = stOldhome;
	}
	public Integer getStRxnf() {
		return stRxnf;
	}
	public void setStRxnf(Integer stRxnf) {
		this.stRxnf = stRxnf;
	}
	public Integer getStBynf() {
		return stBynf;
	}
	public void setStBynf(Integer stBynf) {
		this.stBynf = stBynf;
	}
}