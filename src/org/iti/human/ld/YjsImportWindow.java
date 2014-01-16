package org.iti.human.ld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyNUrdId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.service.YjsService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

public class YjsImportWindow extends FrameworkWindow {

	public static final String BASE_PATH = "upload/human/";
	private WkTDept schDept;
	private WkTRole yjsRole;
	XyClassService xyClassService;
	YjsService yjsService;
	UserService userService;
	DepartmentService departmentService;
	XyUserRoleService xyUserRoleService;
	MessageService messageService;
	Hbox result;
	String resultPath = "";
	Label process;

	public void initShow() {
		result.setVisible(false);
	}

	private String getSavePath() {
		return BASE_PATH + schDept.getKdId() + "/yjsExcel/";
	}

	public void onClick$resultDown() throws InterruptedException {
		downloadFile(resultPath);
	}

	public void onClick$downExample() throws InterruptedException {
		downloadFile(BASE_PATH + "yjs_example.xls");
	}

	public void onClick$importStu() throws InterruptedException, IOException {
		Date d = new Date();
		String path = uploadFile(getSavePath(), d.getTime() + "", "xls", 50);
		if (path.length() == 0) {
			return;
		}
		String filePath = this.getDesktop().getWebApp().getRealPath(path);
		String resfilePath = this.getDesktop().getWebApp().getRealPath(getSavePath());
		// 将excel中数据导入
		POIFSFileSystem fs;
		HSSFWorkbook wb;
		fs = new POIFSFileSystem(new FileInputStream(filePath));
		wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell lnamecell = null;
		HSSFCell xmcell = null;
		HSSFCell sfzhcell = null;
		HSSFCell xbcell = null;
		HSSFCell csrqcell = null;
		HSSFCell zyhcell = null;
		HSSFCell ssnjcell = null;
		HSSFCell bynfcell = null;
		HSSFCell rxnfcell = null;
		HSSFCell rescell = null;
		HSSFCell politicsnamecell = null;
		HSSFCell politicscell = null;
		HSSFCell englishnamecell = null;
		HSSFCell englishcell = null;
		HSSFCell mathnamecell = null;
		HSSFCell mathcell = null;
		HSSFCell majornamecell = null;
		HSSFCell majorcell = null;
		HSSFCell totalcell = null;
		HSSFCell politicalcell = null;
		HSSFCell bydwcell = null;
		HSSFCell byzymccell = null;
		HSSFCell ksfscell = null;
		HSSFCell sourcecell = null;
		HSSFCell bynycell = null;
		HSSFCell addresscell = null;
		HSSFCell codecell = null;
		// ////////////////////////////////////////
		HSSFCell zjlxcell = null;
		HSSFCell lqdwdmcell = null;
		HSSFCell lqdwmccell = null;
		HSSFCell lqyxsmcell = null;
		HSSFCell lqzymccell = null;
		HSSFCell bydwmcell = null;
		HSSFCell byzydmcell = null;
		HSSFCell zzllmcell = null;
		HSSFCell wgymcell = null;
		HSSFCell ywk1mcell = null;
		HSSFCell ywk2mcell = null;
		HSSFCell bzcell = null;
		HSSFCell mzmcell = null;
		HSSFCell hfmcell = null;
		HSSFCell xyjrmcell = null;
		HSSFCell hkszdmcell = null;
		HSSFCell xlmcell = null;
		HSSFCell xlzsbhcell = null;
		HSSFCell xxxscell = null;
		HSSFCell xwmcell = null;
		HSSFCell xwzsbhcell = null;
		HSSFCell kslymcell = null;
		HSSFCell daszdwcell = null;
		HSSFCell lqlbmcell = null;
		HSSFCell fscjcell = null;
		HSSFCell js1mccell = null;
		HSSFCell js1cjcell = null;
		HSSFCell js2mccell = null;
		HSSFCell js2cjcell = null;
		HSSFCell dxwpdwcell = null;
		HSSFCell dxwpdwszdmcell = null;
		HSSFCell blzgnxcell = null;
		HSSFCell pgcell = null;
		HSSFCell zxjhcell = null;
		HSSFCell xszgzccell = null;
		HSSFCell zcxhcell = null;
		HSSFCell szssmcell = null;
		HSSFCell fscjqzcell = null;
		// ////////////////////////////////////////
		Short lnameIndex = 0; // 登录名=考生编号
		Short xmIndex = 3; // 姓名
		Short sfzhIndex = 2; // 身份证号
		Short xbIndex = 6; // 性别
		Short csrqIndex = 4; // 出生日期
		Short zyhIndex = 15; // 专业代码
		Short ssnjIndex = 61; // 年级
		Short bynfIndex = 62; // 毕业年份
		Short rxnfIndex = 63; // 入学年份
		Short resIndex = 64; // 错误标志位
		Short politicsnameIndex = 34; // 政治
		Short politicsIndex = 41; // 政治
		Short englishnameIndex = 36; // 英语
		Short englishIndex = 42; // 英语
		Short mathnameIndex = 38; // 数学
		Short mathIndex = 43; // 数学
		Short majornameIndex = 40; // 专业课
		Short majorIndex = 44; // 专业课
		Short totalIndex = 45; // 总分
		Short politicalIndex = 9; // 政治面貌
		Short bydwIndex = 18; // 毕业单位
		Short byzymcIndex = 20; // 本科专业
		Short ksfsIndex = 28; // 考试方式
		Short sourceIndex = 11; // 生源
		Short bynyIndex = 21; // 毕业年月
		Short addressIndex = 30; // 联系地址
		Short codeIndex = 31; // 邮编
		// ////////////////////////////////////////
		Short zjlxIndex = 1; // 证件类型
		Short lqdwdmIndex = 12; // 录取单位代码
		Short lqdwmcIndex = 13; // 录取单位名称
		Short lqyxsmIndex = 14; // 录取学院代码
		Short lqzymcIndex = 16; // 录取专业名称
		Short bydwmIndex = 17; // 毕业单位码
		Short byzydmIndex = 19; // 毕业专业代码
		Short zzllmIndex = 33; // 政治课码
		Short wgymIndex = 35; // 英语课码
		Short ywk1mIndex = 37; // 数学课码
		Short ywk2mIndex = 39; // 专业课码
		Short bzIndex = 59; // 志愿
		Short mzmIndex = 5;
		Short hfmIndex = 7;
		Short xyjrmIndex = 8;
		Short hkszdmIndex = 10;
		Short xlmIndex = 22;
		Short xlzsbhIndex = 23;
		Short xxxsIndex = 24;
		Short xwmIndex = 25;
		Short xwzsbhIndex = 26;
		Short kslymIndex = 27;
		Short daszdwIndex = 29;
		Short lqlbmIndex = 32;
		Short fscjIndex = 46;
		Short js1mcIndex = 47;
		Short js1cjIndex = 48;
		Short js2mcIndex = 49;
		Short js2cjIndex = 50;
		Short dxwpdwIndex = 51;
		Short dxwpdwszdmIndex = 52;
		Short blzgnxIndex = 53;
		Short pgIndex = 54;
		Short zxjhIndex = 55;
		Short xszgzcIndex = 56;
		Short zcxhIndex = 57;
		Short szssmIndex = 58;
		Short fscjqzIndex = 60;
		// ////////////////////////////////////////
		String className = null;
		WkTDept userDept = null;
		String deptid = null;
		XyClass clist = null;
		WkTUser newUser = new WkTUser();
		Yjs yjs = new Yjs();
		int sucessNum = 0;
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			xmcell = row.getCell(xmIndex);
			sfzhcell = row.getCell(sfzhIndex);
			xbcell = row.getCell(xbIndex);
			csrqcell = row.getCell(csrqIndex);
			zyhcell = row.getCell(zyhIndex);
			ssnjcell = row.getCell(ssnjIndex);
			bynfcell = row.getCell(bynfIndex);
			rxnfcell = row.getCell(rxnfIndex);
			rescell = row.getCell(resIndex);
			String lname = getCellString(lnamecell);
			String xm = getCellString(xmcell);
			String sfzh = getCellString(sfzhcell);
			String xb = getCellString(xbcell);
			String csrq = getCellString(csrqcell);
			String zyh = getCellString(zyhcell);
			String ssnj = getCellString(ssnjcell);
			String bynf = getCellString(bynfcell);
			String rxnf = getCellString(rxnfcell);
			politicsnamecell = row.getCell(politicsnameIndex);
			String politicsname = getCellString(politicsnamecell);
			politicscell = row.getCell(politicsIndex);
			Integer politics = Integer.parseInt(getCellString(politicscell));
			englishnamecell = row.getCell(englishnameIndex);
			String englishname = getCellString(englishnamecell);
			englishcell = row.getCell(englishIndex);
			Integer english = Integer.parseInt(getCellString(englishcell));
			mathnamecell = row.getCell(mathnameIndex);
			String mathname = getCellString(mathnamecell);
			mathcell = row.getCell(mathIndex);
			Integer math = Integer.parseInt(getCellString(mathcell));
			majornamecell = row.getCell(majornameIndex);
			String majorname = getCellString(majornamecell);
			majorcell = row.getCell(majorIndex);
			Integer major = Integer.parseInt(getCellString(majorcell));
			totalcell = row.getCell(totalIndex);
			Integer total = Integer.parseInt(getCellString(totalcell));
			politicalcell = row.getCell(politicalIndex);
			String political = getCellString(politicalcell).trim();
			bydwcell = row.getCell(bydwIndex);
			String bydw = getCellString(bydwcell).trim();
			byzymccell = row.getCell(byzymcIndex);
			String byzymc = getCellString(byzymccell).trim();
			ksfscell = row.getCell(ksfsIndex);
			String ksfs = getCellString(ksfscell).trim();
			sourcecell = row.getCell(sourceIndex);
			String source = getCellString(sourcecell).trim();
			bynycell = row.getCell(bynyIndex);
			String byny = getCellString(bynycell).trim();
			addresscell = row.getCell(addressIndex);
			String address = getCellString(addresscell).trim();
			codecell = row.getCell(codeIndex);
			String code = getCellString(codecell).trim();
			// ////////////////////////////////////////
			zjlxcell = row.getCell(zjlxIndex);
			String zjlx = getCellString(zjlxcell).trim();
			lqdwdmcell = row.getCell(lqdwdmIndex);
			String lqdwdm = getCellString(lqdwdmcell).trim();
			lqdwmccell = row.getCell(lqdwmcIndex);
			String lqdwmc = getCellString(lqdwmccell).trim();
			lqyxsmcell = row.getCell(lqyxsmIndex);
			String lqyxsm = getCellString(lqyxsmcell).trim();
			lqzymccell = row.getCell(lqzymcIndex);
			String lqzymc = getCellString(lqzymccell).trim();
			bydwmcell = row.getCell(bydwmIndex);
			String bydwm = getCellString(bydwmcell).trim();
			byzydmcell = row.getCell(byzydmIndex);
			String byzydm = getCellString(byzydmcell).trim();
			zzllmcell = row.getCell(zzllmIndex);
			String zzllm = getCellString(zzllmcell).trim();
			wgymcell = row.getCell(wgymIndex);
			String wgym = getCellString(wgymcell).trim();
			ywk1mcell = row.getCell(ywk1mIndex);
			String ywk1m = getCellString(ywk1mcell).trim();
			ywk2mcell = row.getCell(ywk2mIndex);
			String ywk2m = getCellString(ywk2mcell).trim();
			bzcell = row.getCell(bzIndex);
			String bz = getCellString(bzcell).trim();
			mzmcell = row.getCell(mzmIndex);
			String mzm = getCellString(mzmcell).trim();
			hfmcell = row.getCell(hfmIndex);
			String hfm = getCellString(hfmcell).trim();
			xyjrmcell = row.getCell(xyjrmIndex);
			String xyjrm = getCellString(xyjrmcell).trim();
			hkszdmcell = row.getCell(hkszdmIndex);
			String hkszdm = getCellString(hkszdmcell).trim();
			xlmcell = row.getCell(xlmIndex);
			String xlm = getCellString(xlmcell).trim();
			xlzsbhcell = row.getCell(xlzsbhIndex);
			String xlzsbh = getCellString(xlzsbhcell).trim();
			xxxscell = row.getCell(xxxsIndex);
			String xxxs = getCellString(xxxscell).trim();
			xwmcell = row.getCell(xwmIndex);
			String xwm = getCellString(xwmcell).trim();
			xwzsbhcell = row.getCell(xwzsbhIndex);
			String xwzsbh = getCellString(xwzsbhcell).trim();
			kslymcell = row.getCell(kslymIndex);
			String kslym = getCellString(kslymcell).trim();
			daszdwcell = row.getCell(daszdwIndex);
			String daszdw = getCellString(daszdwcell).trim();
			lqlbmcell = row.getCell(lqlbmIndex);
			String lqlbm = getCellString(lqlbmcell).trim();
			fscjcell = row.getCell(fscjIndex);
			String fscj = getCellString(fscjcell).trim();
			js1mccell = row.getCell(js1mcIndex);
			String js1mc = getCellString(js1mccell).trim();
			js1cjcell = row.getCell(js1cjIndex);
			String js1cj = getCellString(js1cjcell).trim();
			js2mccell = row.getCell(js2mcIndex);
			String js2mc = getCellString(js2mccell).trim();
			js2cjcell = row.getCell(js2cjIndex);
			String js2cj = getCellString(js2cjcell).trim();
			dxwpdwcell = row.getCell(dxwpdwIndex);
			String dxwpdw = getCellString(dxwpdwcell).trim();
			dxwpdwszdmcell = row.getCell(dxwpdwszdmIndex);
			String dxwpdwszdm = getCellString(dxwpdwszdmcell).trim();
			blzgnxcell = row.getCell(blzgnxIndex);
			String blzgnx = getCellString(blzgnxcell).trim();
			pgcell = row.getCell(pgIndex);
			String pg = getCellString(pgcell).trim();
			zxjhcell = row.getCell(zxjhIndex);
			String zxjh = getCellString(zxjhcell).trim();
			xszgzccell = row.getCell(xszgzcIndex);
			String xszgzc = getCellString(xszgzccell).trim();
			zcxhcell = row.getCell(zcxhIndex);
			String zcxh = getCellString(zcxhcell).trim();
			szssmcell = row.getCell(szssmIndex);
			String szssm = getCellString(szssmcell).trim();
			fscjqzcell = row.getCell(fscjqzIndex);
			String fscjqz = getCellString(fscjqzcell).trim();
			// ////////////////////////////////////////
			if (deptid == null || !deptid.equalsIgnoreCase(zyh)) {
				userDept = departmentService.findByKdnumberAndKdSchid(zyh.trim(), getSchDept().getKdId());
			}
			if (userDept == null) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("用户单位不存在");
				continue;
			}
			List ulist = userService.getUsersByLogin(lname);
			if (ulist.size() > 0) {
				if (rescell == null || rescell.getStringCellValue().length() == 0) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
					rescell.setCellValue("登陆名存在");
				}
			}
			if (rescell != null && rescell.getStringCellValue().length() > 0) {
				continue;
			}
			newUser.setKuLid(lname);
			newUser.setKuBirthday(csrq);
			newUser.setKuName(xm);
			newUser.setKdId(userDept.getKdId());
			newUser.setKuSfzh(sfzh);
			// sfzh = EncodeUtil.decodeByDESStr(sfzh);
			newUser.setKuPasswd(EncodeUtil.encodeByDES(sfzh));
			if (xb.trim().equalsIgnoreCase("2")) {
				newUser.setKuSex("2");
			} else {
				newUser.setKuSex("1");
			}
			if ("01".equals(political)) {
				newUser.setKuPolitical("中共党员");
			} else if ("02".equals(political)) {
				newUser.setKuPolitical("预备党员");
			} else if ("03".equals(political)) {
				newUser.setKuPolitical("共青团团员");
			}
			userService.save(newUser);
			yjs.setKuId(newUser.getKuId());
			yjs.setYjsGrade(Integer.valueOf(ssnj.trim()));
			yjs.setClId(0L);
			yjs.setYjsBynf(Integer.valueOf(bynf.trim()));
			yjs.setYjsRxnf(Integer.valueOf(rxnf.trim()));
			yjs.setYjsId(lname);
			yjs.setPoliticsName(politicsname);
			yjs.setEnglishName(englishname);
			yjs.setMathName(mathname);
			yjs.setMajorName(majorname);
			yjs.setPolitics(politics);
			yjs.setEnglish(english);
			yjs.setMath(math);
			yjs.setMajor(major);
			yjs.setTotal(total);
			yjs.setBydw(bydw);
			yjs.setByzymc(byzymc);
			yjs.setKsfs(ksfs);
			yjs.setMarry(0);
			yjs.setSoldier(0);
			yjs.setSource(source);
			yjs.setByny(byny);
			yjs.setAddress(address);
			yjs.setCode(code);
			yjs.setZjlx(zjlx);
			yjs.setLqdwdm(lqdwdm);
			yjs.setLqdwmc(lqdwmc);
			yjs.setLqyxsm(lqyxsm);
			yjs.setLqzymc(lqzymc);
			yjs.setBydwm(bydwm);
			yjs.setByzydm(byzydm);
			yjs.setZzllm(zzllm);
			yjs.setWgym(wgym);
			yjs.setYwk1m(ywk1m);
			yjs.setYwk2m(ywk2m);
			yjs.setBz(bz);
			yjs.setMzm(mzm);
			yjs.setHfm(hfm);
			yjs.setXyjrm(xyjrm);
			yjs.setHkszdm(hkszdm);
			yjs.setXlm(xlm);
			yjs.setXlzsbh(xlzsbh);
			yjs.setXxxs(xxxs);
			yjs.setXwm(xwm);
			yjs.setXwzsbh(xwzsbh);
			yjs.setKslym(kslym);
			yjs.setDaszdw(daszdw);
			yjs.setLqlbm(lqlbm);
			yjs.setFscj(fscj);
			yjs.setJs1mc(js1mc);
			yjs.setJs1cj(js1cj);
			yjs.setJs2mc(js2mc);
			yjs.setJs2cj(js2cj);
			yjs.setDxwpdw(dxwpdw);
			yjs.setDxwpdwszdm(dxwpdwszdm);
			yjs.setBlzgnx(blzgnx);
			yjs.setPg(pg);
			yjs.setZxjh(zxjh);
			yjs.setXszgzc(xszgzc);
			yjs.setZcxh(zcxh);
			yjs.setSzssm(szssm);
			yjs.setFscjqz(fscjqz);
			yjsService.save(yjs);
			XyUserroleId urid = new XyUserroleId(getYjsRole().getKrId(), newUser.getKuId());
			XyUserrole ur = new XyUserrole(urid, userDept.getKdId());
			xyUserRoleService.saveXyUserrole(ur);
			XyNUrdId xnurid = new XyNUrdId(urid.getKrId(), urid.getKuId(), yjs.getClId(), XyNUrd.TYPE_CLID);
			XyNUrd xnu = new XyNUrd(xnurid);
			messageService.save(xnu);
			sucessNum++;
		}
		int j = sheet.getLastRowNum();
		int num = sheet.getLastRowNum();
		for (int i = 1; i < num + 1; i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			xmcell = row.getCell(xmIndex);
			sfzhcell = row.getCell(sfzhIndex);
			xbcell = row.getCell(xbIndex);
			csrqcell = row.getCell(csrqIndex);
			zyhcell = row.getCell(zyhIndex);
			ssnjcell = row.getCell(ssnjIndex);
			bynfcell = row.getCell(bynfIndex);
			rxnfcell = row.getCell(rxnfIndex);
			rescell = row.getCell(resIndex);
			if (rescell == null || rescell.getStringCellValue() == null || rescell.getStringCellValue().length() == 0) {
				sheet.shiftRows(i + 1, i + num - 1, -1);
				num--;
				i--;
			}
		}
		File resFile = new File(resfilePath + "\\" + d.getTime() + "_导入结果.xls");
		FileOutputStream fileOut = new FileOutputStream(resFile);
		wb.write(fileOut);
		result.setVisible(true);
		process.setValue("共有记录：" + j + ",导入成功：" + sucessNum);
		resultPath = getSavePath() + d.getTime() + "_导入结果.xls";
		downloadFile(resultPath);
	}

	private String getCellString(HSSFCell fcell) {
		if (fcell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("##");
			return df.format(fcell.getNumericCellValue());
		}
		return fcell.getStringCellValue();
	}

	public void initWindow() {
		System.out.println(schDept.getKdName());
	}

	public WkTDept getSchDept() {
		return schDept;
	}

	public void setSchDept(WkTDept schDept) {
		this.schDept = schDept;
	}

	public WkTRole getYjsRole() {
		return yjsRole;
	}

	public void setYjsRole(WkTRole yjsRole) {
		this.yjsRole = yjsRole;
	}
}
