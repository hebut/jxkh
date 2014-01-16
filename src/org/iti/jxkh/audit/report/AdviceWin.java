package org.iti.jxkh.audit.report;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class AdviceWin extends Window implements AfterCompose {

	/**
	 * @author Cx
	 */
	private static final long serialVersionUID = -8047109583980673861L;
	private Row r1, r2, r3, r4, r5, r6, r7;
	private Label dep1, dep2, dep3, dep4, dep5, dep6, dep7;
	private Textbox advice1, advice2, advice3, advice4, advice5, advice6,
			advice7, bAdvice;
	private Radio pass1, back1, pass2, back2, pass3, back3, pass4, back4,
			pass5, back5, pass6, back6, pass7, back7;
	private Toolbarbutton save;
	private WkTUser user;
	private Jxkh_Report meeting;
	private JxkhReportService jxkhReportService;
	private List<Jxkh_ReportDept> meetDeptList = new ArrayList<Jxkh_ReportDept>();

	public Jxkh_Report getMeeting() {
		return meeting;
	}

	public void setMeeting(Jxkh_Report meeting) {
		this.meeting = meeting;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		// 控制显示多少行，和每行的内容
		meetDeptList = jxkhReportService.findReportDepByReportId(meeting);
		for (int i = 1; i <= meetDeptList.size(); i++) {
			Jxkh_ReportDept dept = (Jxkh_ReportDept) meetDeptList.get(i - 1);
			switch (i) {
			case 1:
				r1.setVisible(true);
				dep1.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())) {
					// pass1.setChecked(true);
					pass1.setDisabled(false);
					back1.setDisabled(false);
					advice1.setDisabled(false);
				}
				break;
			case 2:
				r2.setVisible(true);
				dep2.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass2.setChecked(true);
					pass2.setDisabled(false);
					back2.setDisabled(false);
					advice2.setDisabled(false);
				}
				break;
			case 3:
				r3.setVisible(true);
				dep3.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass3.setChecked(true);
					pass3.setDisabled(false);
					back3.setDisabled(false);
					advice3.setDisabled(false);
				}
				break;
			case 4:
				r4.setVisible(true);
				dep4.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass4.setChecked(true);
					pass4.setDisabled(false);
					back4.setDisabled(false);
					advice4.setDisabled(false);
				}
				break;
			case 5:
				r5.setVisible(true);
				dep5.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass5.setChecked(true);
					pass5.setDisabled(false);
					back5.setDisabled(false);
					advice5.setDisabled(false);
				}
				break;
			case 6:
				r6.setVisible(true);
				dep6.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass6.setChecked(true);
					pass6.setDisabled(false);
					back6.setDisabled(false);
					advice6.setDisabled(false);
				}
				break;
			case 7:
				r7.setVisible(true);
				dep7.setValue(dept.getName());
				if (user.getDept().getKdName().equals(dept.getName())
						&& meeting.getState() == 2) {
					// pass7.setChecked(true);
					pass7.setDisabled(false);
					back7.setDisabled(false);
					advice7.setDisabled(false);
				}
				break;
			}
		}
		// 保存和退出按钮的可见性
		if (meeting.getState() == 1 || meeting.getState() == 4
				|| meeting.getState() == 6 || meeting.getState() == JXKH_MEETING.BUSINESS_TEMP_PASS) {
			save.setVisible(false);
		}else {
			save.setVisible(true);
		}
		// "通过" "退回" 的初始化
		String s = meeting.getTempState();
		if (s.charAt(0) == '1') {
			pass1.setChecked(true);
			pass1.setDisabled(true);
			back1.setDisabled(true);
			advice1.setDisabled(true);
		}
		if (s.charAt(0) == '2') {
			back1.setChecked(true);
			pass1.setDisabled(true);
			back1.setDisabled(true);
			advice1.setDisabled(true);
		}
		if (s.charAt(1) == '1') {
			pass2.setChecked(true);
			pass2.setDisabled(true);
			back2.setDisabled(true);
			advice2.setDisabled(true);
		}
		if (s.charAt(1) == '2') {
			back2.setChecked(true);
		}
		if (s.charAt(2) == '1') {
			pass3.setChecked(true);
			pass3.setDisabled(true);
			back3.setDisabled(true);
			advice3.setDisabled(true);
		}
		if (s.charAt(2) == '2') {
			back3.setChecked(true);
		}
		if (s.charAt(3) == '1') {
			pass4.setChecked(true);
			pass4.setDisabled(true);
			back4.setDisabled(true);
			advice4.setDisabled(true);
		}
		if (s.charAt(3) == '2') {
			back4.setChecked(true);
		}
		if (s.charAt(4) == '1') {
			pass5.setChecked(true);
			pass5.setDisabled(true);
			back5.setDisabled(true);
			advice5.setDisabled(true);
		}
		if (s.charAt(4) == '2') {
			back5.setChecked(true);
		}
		if (s.charAt(5) == '1') {
			pass6.setChecked(true);
			pass6.setDisabled(true);
			back6.setDisabled(true);
			advice6.setDisabled(true);
		}
		if (s.charAt(5) == '2') {
			back6.setChecked(true);
		}
		if (s.charAt(6) == '1') {
			pass7.setChecked(true);
			pass7.setDisabled(true);
			back7.setDisabled(true);
			advice7.setDisabled(true);
		}
		if (s.charAt(6) == '2') {
			back7.setChecked(true);
		}

		// 取出业务办审核意见
		if (meeting.getbAdvice() == null || meeting.getbAdvice() == "") {
			bAdvice.setValue("");
		} else {
			bAdvice.setValue(meeting.getbAdvice());
		}

		// 取出部门审核意见
		if (meeting.getDep1Reason() == null
				|| meeting.getDep1Reason().equals("")) {
			advice1.setValue("");
		} else {
			advice1.setValue(meeting.getDep1Reason());
		}
		if (meeting.getDep2Reason() == null
				|| meeting.getDep2Reason().equals("")) {
			advice2.setValue("");
		} else {
			advice2.setValue(meeting.getDep2Reason());
		}
		if (meeting.getDep3Reason() == null
				|| meeting.getDep3Reason().equals("")) {
			advice3.setValue("");
		} else {
			advice3.setValue(meeting.getDep3Reason());
		}
		if (meeting.getDep4Reason() == null
				|| meeting.getDep4Reason().equals("")) {
			advice4.setValue("");
		} else {
			advice4.setValue(meeting.getDep4Reason());
		}
		if (meeting.getDep5Reason() == null
				|| meeting.getDep5Reason().equals("")) {
			advice5.setValue("");
		} else {
			advice5.setValue(meeting.getDep5Reason());
		}
		if (meeting.getDep6Reason() == null
				|| meeting.getDep6Reason().equals("")) {
			advice6.setValue("");
		} else {
			advice6.setValue(meeting.getDep6Reason());
		}
		if (meeting.getDep7Reason() == null
				|| meeting.getDep7Reason().equals("")) {
			advice7.setValue("");
		} else {
			advice7.setValue(meeting.getDep7Reason());
		}
	}

	public void onClick$save() {
		String s = meeting.getTempState();
		// char[] myStr = new char[7];
		// for (int r = 0; r < 7; r++) {
		// myStr[r] = s.charAt(r);
		// }
		char[] myStr = s.toCharArray();
		if (pass1.isChecked()) {
			meeting.setDep1Reason(advice1.getValue());
			myStr[0] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.First_Dept_Pass);
		} else if (back1.isChecked()) {
			meeting.setDep1Reason(advice1.getValue());
			myStr[0] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass2.isChecked()) {
			meeting.setDep2Reason(advice2.getValue());
			myStr[1] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back2.isChecked()) {
			meeting.setDep2Reason(advice2.getValue());
			myStr[1] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass3.isChecked()) {
			meeting.setDep3Reason(advice3.getValue());
			myStr[2] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back3.isChecked()) {
			meeting.setDep3Reason(advice3.getValue());
			myStr[2] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass4.isChecked()) {
			meeting.setDep4Reason(advice4.getValue());
			myStr[3] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back4.isChecked()) {
			meeting.setDep4Reason(advice4.getValue());
			myStr[3] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass5.isChecked()) {
			meeting.setDep5Reason(advice5.getValue());
			myStr[4] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back5.isChecked()) {
			meeting.setDep5Reason(advice5.getValue());
			myStr[4] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass6.isChecked()) {
			meeting.setDep6Reason(advice6.getValue());
			myStr[5] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back6.isChecked()) {
			meeting.setDep6Reason(advice6.getValue());
			myStr[5] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		if (pass7.isChecked()) {
			meeting.setDep7Reason(advice7.getValue());
			myStr[6] = '1';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
		} else if (back7.isChecked()) {
			meeting.setDep7Reason(advice7.getValue());
			myStr[6] = '2';
			String s0 = new String(myStr);
			meeting.setTempState(s0);
			meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
		}
		int t = 1;
		String str = meeting.getTempState();
		for (int j = 0; j < meetDeptList.size(); j++) {
			if (str.charAt(j) != '1')
				t = 0;
		}
		if (t == 1)
			meeting.setState(JXKH_MEETING.DEPT_PASS);

		try {
			jxkhReportService.saveOrUpdate(meeting);
			Messagebox.show("审核意见保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("审核意见保存失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
