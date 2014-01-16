package org.iti.xypt.entity;

import org.iti.xypt.service.XyClassService;
import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */
public class Yjs implements java.io.Serializable {

	// Fields
	public static final String[] NORMALS = { "正常", "休学", "复学", "退学", "留级", "延长学习年限", "免修", "其他" };
	public static final Short STATUS_NORMAL = 0;
	private Long ysId;
	private Long kuId;
	private String yjsId;
	private Integer yjsGrade;
	private Integer yjsBynf;
	private Integer yjsRxnf;
	private Short yjsNormal;
	private Long clId;
	WkTUser user;
	XyClass xyClass;
	private String stFname;
	private String stFrelation;
	private String stFwork;
	private String stFphone;
	private String stMname;
	private String stMrelation;
	private String stMwork;
	private String stMphon;
	private String politicsName;
	private Integer politics;
	private String englishName;
	private Integer english;
	private String mathName;
	private Integer math;
	private String majorName;
	private Integer major;
	private Integer total;
	private String path;
	private String bydw;
	private String byzymc;
	private String ksfs;
	private String bmh;
	private Integer marry;
	private Integer soldier;
	private String source;
	private String byny;
	private String address;
	private String code;
	private String work;
	private String pride;
	private String qq;
	private String zjlx;
	private String lqdwdm;
	private String lqdwmc;
	private String lqyxsm;
	private String lqzymc;
	private String bydwm;
	private String byzydm;
	private String zzllm;
	private String wgym;
	private String ywk1m;
	private String ywk2m;
	private String bz;
	private String mzm;
	private String hfm;
	private String xyjrm;
	private String hkszdm;
	private String xlm;
	private String xlzsbh;
	private String xxxs;
	private String xwm;
	private String xwzsbh;
	private String kslym;
	private String daszdw;
	private String lqlbm;
	private String fscj;
	private String js1mc;
	private String js1cj;
	private String js2mc;
	private String js2cj;
	private String dxwpdw;
	private String dxwpdwszdm;
	private String blzgnx;
	private String pg;
	private String zxjh;
	private String xszgzc;
	private String zcxh;
	private String szssm;
	private String fscjqz;

	public Yjs(Long ysId) {
		this.ysId = ysId;
	}

	public Integer getYjsRxnf() {
		return yjsRxnf;
	}

	public void setYjsRxnf(Integer yjsRxnf) {
		this.yjsRxnf = yjsRxnf;
	}

	public Integer getYjsBynf() {
		return yjsBynf;
	}

	public void setYjsBynf(Integer yjsBynf) {
		this.yjsBynf = yjsBynf;
	}

	public XyClass getXyClass() {
		if (xyClass == null) {
			XyClassService xyClassService = (XyClassService) BeanFactory.getBean("xyClassService");
			xyClass = (XyClass) xyClassService.get(XyClass.class, clId);
		}
		return xyClass;
	}

	public void setXyClass(XyClass xyClass) {
		this.xyClass = xyClass;
	}

	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.user = (WkTUser) userService.get(WkTUser.class, kuId);
		}
		return user;
	}

	public void setUser(WkTUser user) {
		this.user = user;
	}

	/** default constructor */
	public Yjs() {
		this.setYjsNormal(Yjs.STATUS_NORMAL);
	}

	/** full constructor */
	public Yjs(Long ysId, Long kuId, String yjsId, Integer yjsGrade, Integer yjsBynf, Integer yjsRxnf, Short yjsNormal, Long clId, String stFname, String stFrelation, String stFwork, String stFphone, String stMname, String stMrelation, String stMwork, String stMphon, String politicsName, Integer politics, String englishName, Integer english, String mathName, Integer math, String majorName, Integer major, Integer total, String path, String bydw, String byzymc, String ksfs, String bmh, Integer marry,
			Integer soldier, String source, String byny, String address, String code, String work, String pride, String qq, String zjlx, String lqdwdm, String lqdwmc, String lqyxsm, String lqzymc, String bydwm, String byzydm, String zzllm, String wgym, String ywk1m, String ywk2m, String bz, String mzm, String hfm, String xyjrm, String hkszdm, String xlm, String xlzsbh, String xxxs, String xwm, String xwzsbh, String kslym, String daszdw, String lqlbm, String fscj, String js1mc, String js1cj, String js2mc,
			String js2cj, String dxwpdw, String dxwpdwszdm, String blzgnx, String pg, String zxjh, String xszgzc, String zcxh, String szssm, String fscjqz) {
		this.ysId = ysId;
		this.kuId = kuId;
		this.yjsId = yjsId;
		this.yjsGrade = yjsGrade;
		this.yjsBynf = yjsBynf;
		this.yjsNormal = yjsNormal;
		this.clId = clId;
		this.yjsRxnf = yjsRxnf;
		this.stFname = stFname;
		this.stFrelation = stFrelation;
		this.stFwork = stFwork;
		this.stFphone = stFphone;
		this.stMname = stMname;
		this.stMrelation = stMrelation;
		this.stMwork = stMwork;
		this.stMphon = stMphon;
		this.politicsName = politicsName;
		this.politics = politics;
		this.englishName = englishName;
		this.english = english;
		this.mathName = mathName;
		this.math = math;
		this.majorName = majorName;
		this.major = major;
		this.total = total;
		this.path = path;
		this.bydw = bydw;
		this.byzymc = byzymc;
		this.ksfs = ksfs;
		this.bmh = bmh;
		this.marry = marry;
		this.soldier = soldier;
		this.source = source;
		this.byny = byny;
		this.address = address;
		this.code = code;
		this.work = work;
		this.pride = pride;
		this.qq = qq;
		this.zjlx = zjlx;
		this.lqdwdm = lqdwdm;
		this.lqdwmc = lqdwmc;
		this.lqyxsm = lqyxsm;
		this.lqzymc = lqzymc;
		this.bydwm = bydwm;
		this.byzydm = byzydm;
		this.zzllm = zzllm;
		this.wgym = wgym;
		this.ywk1m = ywk1m;
		this.ywk2m = ywk2m;
		this.bz = bz;
		this.mzm = mzm;
		this.hfm = hfm;
		this.xyjrm = xyjrm;
		this.hkszdm = hkszdm;
		this.xlm = xlm;
		this.xlzsbh = xlzsbh;
		this.xxxs = xxxs;
		this.xwm = xwm;
		this.xwzsbh = xwzsbh;
		this.kslym = kslym;
		this.daszdw = daszdw;
		this.lqlbm = lqlbm;
		this.fscj = fscj;
		this.js1mc = js1mc;
		this.js1cj = js1cj;
		this.js2mc = js2mc;
		this.js2cj = js2cj;
		this.dxwpdw = dxwpdw;
		this.dxwpdwszdm = dxwpdwszdm;
		this.blzgnx = blzgnx;
		this.pg = pg;
		this.zxjh = zxjh;
		this.xszgzc = xszgzc;
		this.zcxh = zcxh;
		this.szssm = szssm;
		this.fscjqz = fscjqz;
	}

	// Property accessors
	public Long getYsId() {
		return ysId;
	}

	public void setYsId(Long ysId) {
		this.ysId = ysId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getYjsId() {
		return this.yjsId;
	}

	public void setYjsId(String yjsId) {
		this.yjsId = yjsId;
	}

	public Integer getYjsGrade() {
		return this.yjsGrade;
	}

	public void setYjsGrade(Integer yjsGrade) {
		this.yjsGrade = yjsGrade;
	}

	public Short getYjsNormal() {
		return yjsNormal;
	}

	public void setYjsNormal(Short yjsNormal) {
		this.yjsNormal = yjsNormal;
	}

	public Long getClId() {
		return clId;
	}

	public void setClId(Long id) {
		clId = id;
	}

	public String getStFname() {
		return stFname;
	}

	public void setStFname(String stFname) {
		this.stFname = stFname;
	}

	public String getStFrelation() {
		return stFrelation;
	}

	public void setStFrelation(String stFrelation) {
		this.stFrelation = stFrelation;
	}

	public String getStFwork() {
		return stFwork;
	}

	public void setStFwork(String stFwork) {
		this.stFwork = stFwork;
	}

	public String getStFphone() {
		return stFphone;
	}

	public void setStFphone(String stFphone) {
		this.stFphone = stFphone;
	}

	public String getStMname() {
		return stMname;
	}

	public void setStMname(String stMname) {
		this.stMname = stMname;
	}

	public String getStMrelation() {
		return stMrelation;
	}

	public void setStMrelation(String stMrelation) {
		this.stMrelation = stMrelation;
	}

	public String getStMwork() {
		return stMwork;
	}

	public void setStMwork(String stMwork) {
		this.stMwork = stMwork;
	}

	public String getStMphon() {
		return stMphon;
	}

	public void setStMphon(String stMphon) {
		this.stMphon = stMphon;
	}

	public Integer getPolitics() {
		return politics;
	}

	public void setPolitics(Integer politics) {
		this.politics = politics;
	}

	public Integer getEnglish() {
		return english;
	}

	public void setEnglish(Integer english) {
		this.english = english;
	}

	public Integer getMath() {
		return math;
	}

	public void setMath(Integer math) {
		this.math = math;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBydw() {
		return bydw;
	}

	public void setBydw(String bydw) {
		this.bydw = bydw;
	}

	public String getByzymc() {
		return byzymc;
	}

	public void setByzymc(String byzymc) {
		this.byzymc = byzymc;
	}

	public String getKsfs() {
		return ksfs;
	}

	public void setKsfs(String ksfs) {
		this.ksfs = ksfs;
	}

	public String getBmh() {
		return bmh;
	}

	public void setBmh(String bmh) {
		this.bmh = bmh;
	}

	public Integer getMarry() {
		return marry;
	}

	public void setMarry(Integer marry) {
		this.marry = marry;
	}

	public Integer getSoldier() {
		return soldier;
	}

	public void setSoldier(Integer soldier) {
		this.soldier = soldier;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getByny() {
		return byny;
	}

	public void setByny(String byny) {
		this.byny = byny;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPride() {
		return pride;
	}

	public void setPride(String pride) {
		this.pride = pride;
	}

	public String getPoliticsName() {
		return politicsName;
	}

	public void setPoliticsName(String politicsName) {
		this.politicsName = politicsName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getMathName() {
		return mathName;
	}

	public void setMathName(String mathName) {
		this.mathName = mathName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getLqdwdm() {
		return lqdwdm;
	}

	public void setLqdwdm(String lqdwdm) {
		this.lqdwdm = lqdwdm;
	}

	public String getLqdwmc() {
		return lqdwmc;
	}

	public void setLqdwmc(String lqdwmc) {
		this.lqdwmc = lqdwmc;
	}

	public String getLqyxsm() {
		return lqyxsm;
	}

	public void setLqyxsm(String lqyxsm) {
		this.lqyxsm = lqyxsm;
	}

	public String getLqzymc() {
		return lqzymc;
	}

	public void setLqzymc(String lqzymc) {
		this.lqzymc = lqzymc;
	}

	public String getBydwm() {
		return bydwm;
	}

	public void setBydwm(String bydwm) {
		this.bydwm = bydwm;
	}

	public String getByzydm() {
		return byzydm;
	}

	public void setByzydm(String byzydm) {
		this.byzydm = byzydm;
	}

	public String getZzllm() {
		return zzllm;
	}

	public void setZzllm(String zzllm) {
		this.zzllm = zzllm;
	}

	public String getWgym() {
		return wgym;
	}

	public void setWgym(String wgym) {
		this.wgym = wgym;
	}

	public String getYwk1m() {
		return ywk1m;
	}

	public void setYwk1m(String ywk1m) {
		this.ywk1m = ywk1m;
	}

	public String getYwk2m() {
		return ywk2m;
	}

	public void setYwk2m(String ywk2m) {
		this.ywk2m = ywk2m;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getMzm() {
		return mzm;
	}

	public void setMzm(String mzm) {
		this.mzm = mzm;
	}

	public String getHfm() {
		return hfm;
	}

	public void setHfm(String hfm) {
		this.hfm = hfm;
	}

	public String getXyjrm() {
		return xyjrm;
	}

	public void setXyjrm(String xyjrm) {
		this.xyjrm = xyjrm;
	}

	public String getHkszdm() {
		return hkszdm;
	}

	public void setHkszdm(String hkszdm) {
		this.hkszdm = hkszdm;
	}

	public String getXlm() {
		return xlm;
	}

	public void setXlm(String xlm) {
		this.xlm = xlm;
	}

	public String getXlzsbh() {
		return xlzsbh;
	}

	public void setXlzsbh(String xlzsbh) {
		this.xlzsbh = xlzsbh;
	}

	public String getXxxs() {
		return xxxs;
	}

	public void setXxxs(String xxxs) {
		this.xxxs = xxxs;
	}

	public String getXwm() {
		return xwm;
	}

	public void setXwm(String xwm) {
		this.xwm = xwm;
	}

	public String getXwzsbh() {
		return xwzsbh;
	}

	public void setXwzsbh(String xwzsbh) {
		this.xwzsbh = xwzsbh;
	}

	public String getKslym() {
		return kslym;
	}

	public void setKslym(String kslym) {
		this.kslym = kslym;
	}

	public String getDaszdw() {
		return daszdw;
	}

	public void setDaszdw(String daszdw) {
		this.daszdw = daszdw;
	}

	public String getLqlbm() {
		return lqlbm;
	}

	public void setLqlbm(String lqlbm) {
		this.lqlbm = lqlbm;
	}

	public String getFscj() {
		return fscj;
	}

	public void setFscj(String fscj) {
		this.fscj = fscj;
	}

	public String getJs1mc() {
		return js1mc;
	}

	public void setJs1mc(String js1mc) {
		this.js1mc = js1mc;
	}

	public String getJs1cj() {
		return js1cj;
	}

	public void setJs1cj(String js1cj) {
		this.js1cj = js1cj;
	}

	public String getJs2mc() {
		return js2mc;
	}

	public void setJs2mc(String js2mc) {
		this.js2mc = js2mc;
	}

	public String getJs2cj() {
		return js2cj;
	}

	public void setJs2cj(String js2cj) {
		this.js2cj = js2cj;
	}

	public String getDxwpdw() {
		return dxwpdw;
	}

	public void setDxwpdw(String dxwpdw) {
		this.dxwpdw = dxwpdw;
	}

	public String getDxwpdwszdm() {
		return dxwpdwszdm;
	}

	public void setDxwpdwszdm(String dxwpdwszdm) {
		this.dxwpdwszdm = dxwpdwszdm;
	}

	public String getBlzgnx() {
		return blzgnx;
	}

	public void setBlzgnx(String blzgnx) {
		this.blzgnx = blzgnx;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getZxjh() {
		return zxjh;
	}

	public void setZxjh(String zxjh) {
		this.zxjh = zxjh;
	}

	public String getXszgzc() {
		return xszgzc;
	}

	public void setXszgzc(String xszgzc) {
		this.xszgzc = xszgzc;
	}

	public String getZcxh() {
		return zcxh;
	}

	public void setZcxh(String zcxh) {
		this.zcxh = zcxh;
	}

	public String getSzssm() {
		return szssm;
	}

	public void setSzssm(String szssm) {
		this.szssm = szssm;
	}

	public String getFscjqz() {
		return fscjqz;
	}

	public void setFscjqz(String fscjqz) {
		this.fscjqz = fscjqz;
	}
}