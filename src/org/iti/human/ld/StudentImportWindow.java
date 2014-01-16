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
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyNUrdId;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.entity.XyUserroleId;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.XyClassService;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;

import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

public class StudentImportWindow extends FrameworkWindow {
	public static final String BASE_PATH = "upload/human/";
	private WkTDept schDept;
	private WkTRole studentRole;
	XyClassService xyClassService;
	StudentService studentService;
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
		return BASE_PATH + schDept.getKdId() + "/stuExcel/";
	}

	public void onClick$resultDown() throws InterruptedException {
		downloadFile(resultPath);
	}

	public void onClick$downExample() throws InterruptedException {
		downloadFile(BASE_PATH + "student_example.xls");
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
		HSSFCell xhcell = null;
		HSSFCell xmcell = null;
		HSSFCell sfzhcell = null;
		HSSFCell bmcell = null;
		HSSFCell xbcell = null;
		HSSFCell csrqcell = null;
		HSSFCell zyhcell = null;
		HSSFCell ssnjcell = null;
		HSSFCell bynfcell = null;
		HSSFCell rxnfcell = null;
		HSSFCell rescell = null;
		Short lnameIndex = 0, xhIndex = 1, xmIndex = 2, sfzhIndex = 3, bmIndex = 4, xbIndex = 5, csrqIndex = 6, zyhIndex = 7, ssnjIndex = 8, bynfIndex = 9, rxnfIndex = 10, resIndex = 11;
		String className = null;
		WkTDept userDept = null;
		String deptid = null;
		XyClass clist = null;
		WkTUser newUser = new WkTUser();
		Student student = new Student();
		int sucessNum = 0;
		System.out.println(sheet.getLastRowNum());
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			xhcell = row.getCell(xhIndex);
			xmcell = row.getCell(xmIndex);
			sfzhcell = row.getCell(sfzhIndex);
			bmcell = row.getCell(bmIndex);
			xbcell = row.getCell(xbIndex);
			csrqcell = row.getCell(csrqIndex);
			zyhcell = row.getCell(zyhIndex);
			ssnjcell = row.getCell(ssnjIndex);
			bynfcell = row.getCell(bynfIndex);
			rxnfcell = row.getCell(rxnfIndex);
			rescell = row.getCell(resIndex);
			String lname = getCellString(lnamecell);
			String xh = getCellString(xhcell);
			String xm = getCellString(xmcell);
			String sfzh = getCellString(sfzhcell);
			String xb = getCellString(xbcell);
			String bm = getCellString(bmcell);
			String csrq = getCellString(csrqcell);
			String zyh = getCellString(zyhcell);
			String ssnj = getCellString(ssnjcell);
			String bynf = getCellString(bynfcell);
			String rxnf = getCellString(rxnfcell);
			// System.out.println(xh + "|" + xm + "|" + sfzh + "|" + xb + "|" + bm + "|" + csrq);
			if (deptid == null || !deptid.equalsIgnoreCase(zyh)) {
				deptid = zyh;
				userDept = departmentService.findByKdnumberAndKdSchid(deptid, getSchDept().getKdId());
			}
			if (userDept == null) {
				if (rescell == null) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
				}
				rescell.setCellValue("用户单位不存在");
				continue;
			}
			if (className == null || (!className.equalsIgnoreCase(bm))) {
				className = bm;
				clist = xyClassService.findByKdidAndCName(userDept.getKdId(), bm);
			}
			if (clist == null) {
				XyClass xyclass = new XyClass();
				xyclass.setClGrade(Integer.valueOf(ssnj));
				xyclass.setClName(bm);
				xyclass.setClSname(bm);
				xyclass.setKdId(userDept.getKdId());
				xyClassService.save(xyclass);
				clist = xyclass;
			}
			List slist = studentService.findBySid(xh);
			List ulist = userService.getUsersByLogin(lname);
			for (int j = 0; j < slist.size(); j++) {
				Student st = (Student) slist.get(j);
				if (st.getUser().getDept().getKdSchid().longValue() == getSchDept().getKdId().longValue()) {
					if (rescell == null) {
						rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
						rescell.setCellValue("学号已经存在");
					}
				}
			}
			if (ulist.size() > 0) {
				if (rescell == null || rescell.getStringCellValue().length() == 0) {
					rescell = row.createCell(resIndex, HSSFCell.CELL_TYPE_STRING);
					rescell.setCellValue("登陆名存在");
				}
			}
			// System.out.println(rescell);
			if (rescell != null && rescell.getStringCellValue().length() > 0) {
				continue;
			}
			newUser.setKuLid(lname);
			newUser.setKuBirthday(csrq);
			newUser.setKuName(xm);
			newUser.setKdId(userDept.getKdId());
			newUser.setKuSfzh(sfzh);
			newUser.setKuPasswd(EncodeUtil.encodeByMD5("666666"));
			if (xb.equalsIgnoreCase("女")) {
				newUser.setKuSex("2");
			} else {
				newUser.setKuSex("1");
			}
			userService.save(newUser);
			student.setKuId(newUser.getKuId());
			student.setStClass(bm);
			student.setStGrade(Integer.valueOf(ssnj));
			student.setClId(clist.getClId());
			student.setStBynf(Integer.valueOf(bynf));
			student.setStRxnf(Integer.valueOf(rxnf));
			student.setStId(xh);
			studentService.save(student);
			XyUserroleId urid = new XyUserroleId(getStudentRole().getKrId(), newUser.getKuId());
			XyUserrole ur = new XyUserrole(urid, userDept.getKdId());
			xyUserRoleService.saveXyUserrole(ur);
			XyNUrdId xnurid = new XyNUrdId(urid.getKrId(), urid.getKuId(), student.getClId(), XyNUrd.TYPE_CLID);
			XyNUrd xnu = new XyNUrd(xnurid);
			messageService.save(xnu);
			sucessNum++;
		}
		int j = sheet.getLastRowNum();
		int num = sheet.getLastRowNum();
		for (int i = 1; i < num + 1; i++) {
			row = sheet.getRow(i);
			lnamecell = row.getCell(lnameIndex);
			xhcell = row.getCell(xhIndex);
			xmcell = row.getCell(xmIndex);
			sfzhcell = row.getCell(sfzhIndex);
			bmcell = row.getCell(bmIndex);
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

	public WkTRole getStudentRole() {
		return studentRole;
	}

	public void setStudentRole(WkTRole studentRole) {
		this.studentRole = studentRole;
	}
}
