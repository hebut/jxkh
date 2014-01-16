package org.iti.human.ld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.service.XyptTitleService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

public class TeacherImportWindow1 extends FrameworkWindow {

	public static final String BASE_PATH = "upload/human/";
	private WkTDept schDept;
	private WkTRole teacherRole;
	XyClassService xyClassService;
	TeacherService teacherService;
	UserService userService;
	DepartmentService departmentService;
	XyUserRoleService xyUserRoleService;
	XyptTitleService xypttitleService;
	Hbox result;
	String resultPath = "";
	Label process;

	public void initShow() {
		result.setVisible(false);
	}

	private String getSavePath() {
		return BASE_PATH + schDept.getKdId() + "/teaExcel/";
	}

	public void onClick$resultDown() throws InterruptedException {
		downloadFile(resultPath);
	}

	public void onClick$downExample() throws InterruptedException {
		downloadFile(BASE_PATH + "teacher_example.xls");
	}

	public void onClick$importTea() throws InterruptedException, IOException {
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
		HSSFCell lnamecell = null;// 登录名
		HSSFCell jshcell = null;// 教师号
		HSSFCell xmcell = null;// 姓名
		HSSFCell xbcell = null;// 性别
		HSSFCell dwcell = null;// 单位
		HSSFCell zccell = null;// 职称
		HSSFCell rescell = null;// 保存结果
		// HSSFCell sfzhcell=null;//身份证
		// HSSFCell csrqcell=null;//出生日期
		Short lnameIndex = 0, jshIndex = 1, xmIndex = 2, xbIndex = 3, dwIndex = 4, zcIndex = 5, resIndex = 6;
		WkTDept userDept = null;
		String deptid = null;
		String titleName = null;
		List titlelist = new ArrayList();
		WkTUser newUser = new WkTUser();
		Teacher teacher = new Teacher();
		int sucessNum = 0;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			jshcell = row.getCell(jshIndex);
			xmcell = row.getCell(xmIndex);
			// sfzhcell=row.getCell(sfzhIndex);
			xbcell = row.getCell(xbIndex);
			dwcell = row.getCell(dwIndex);
			zccell = row.getCell(zcIndex);
			// csrqcell=row.getCell(csrqIndex);
			rescell = row.getCell(resIndex);
			String lname = getCellString(lnamecell);
			String jsh = getCellString(jshcell);
			String xm = getCellString(xmcell);
			// String sfzh=getCellString(sfzhcell);
			String xb = getCellString(xbcell);
			String dw = getCellString(dwcell);
			// String csrq=getCellString(csrqcell);
			String zc = getCellString(zccell);
			// System.out.println(xh+"|"+xm+"|"+sfzh+"|"+xb+"|"+bm+"|"+csrq);
			if (deptid == null || !deptid.equalsIgnoreCase(dw)) {
				deptid = dw;
				userDept = departmentService.findByKdnumberAndKdSchid(deptid, getSchDept().getKdId());
			}
			if (userDept == null) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("用户单位不存在");
				continue;
			}
			List tlist = teacherService.findByThidAndKdid(jsh, getSchDept().getKdId());
			List ulist = userService.getUsersByLogin(lname);
			if (tlist.size() > 0) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("教师号已经存在");
				continue;
			}
			if (ulist.size() > 0) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("登陆名已经存在");
				continue;
			}
			if (titleName == null || (!titleName.equalsIgnoreCase(zc))) {
				titlelist = xypttitleService.findByTname(zc);
			}
			if (titlelist.size() == 0) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("职称不存在");
				continue;
			}
			Title title = (Title) titlelist.get(0);
			newUser.setKuLid(lname);
			newUser.setKuName(xm);
			newUser.setKdId(userDept.getKdId());
			newUser.setKuPasswd(EncodeUtil.encodeByDES("888888"));
			// newUser.setKuSfzh(sfzh);
			if (xb.equalsIgnoreCase("男")) {
				newUser.setKuSex("1");
			} else {
				newUser.setKuSex("2");
			}
			userService.save(newUser);
			teacher.setKuId(newUser.getKuId());
			teacher.setKdId(getSchDept().getKdId());
			teacher.setThId(jsh);
			teacher.setThTitle(title.getTiId());
			teacherService.save(teacher);
			XyUserroleId urid = new XyUserroleId(getTeacherRole().getKrId(), newUser.getKuId());
			XyUserrole ur = new XyUserrole(urid, userDept.getKdId());
			xyUserRoleService.saveXyUserrole(ur);
			sucessNum++;
		}
		int j = sheet.getLastRowNum();
		int num = sheet.getLastRowNum();
		for (int i = 1; i < num + 1; i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			jshcell = row.getCell(jshIndex);
			xmcell = row.getCell(xmIndex);
			// sfzhcell=row.getCell(sfzhIndex);
			xbcell = row.getCell(xbIndex);
			dwcell = row.getCell(dwIndex);
			zccell = row.getCell(zcIndex);
			// csrqcell=row.getCell(csrqIndex);
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
		return fcell.getStringCellValue() == null ? "" : fcell.getStringCellValue().trim();
	}

	@Override
	public void initWindow() {
		// System.out.println(schDept.getKdName());
	}

	public WkTDept getSchDept() {
		return schDept;
	}

	public void setSchDept(WkTDept schDept) {
		this.schDept = schDept;
	}

	public WkTRole getTeacherRole() {
		return teacherRole;
	}

	public void setTeacherRole(WkTRole teacherRole) {
		this.teacherRole = teacherRole;
	}
}
