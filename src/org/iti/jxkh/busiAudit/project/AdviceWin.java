package org.iti.jxkh.busiAudit.project;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.service.JxkhProjectService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class AdviceWin extends Window implements AfterCompose {

	/**
	 * @author ZhangYuguang
	 */
	private static final long serialVersionUID = -8879597214061641214L;
	private Row r1, r2, r3, r4, r5, r6, r7;
	private Label dep1, dep2, dep3, dep4, dep5, dep6, dep7;
	private Radio pass1, back1, pass2, back2, pass3, back3, pass4, back4,
			pass5, back5, pass6, back6, pass7, back7, pass,temp, back;
	private Textbox advice1, advice2, advice3, advice4, advice5, advice6,
			advice7, bAdvice;
	private Jxkh_Project meeting;
	private JxkhProjectService jxkhProjectService;
	private List<Jxkh_ProjectDept> meetDeptList = new ArrayList<Jxkh_ProjectDept>();

	public Jxkh_Project getMeeting() {
		return meeting;
	}

	public void setMeeting(Jxkh_Project meeting) {
		this.meeting = meeting;
	}

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		if (meeting.getState() == 1 || meeting.getState() == 4
				|| meeting.getState() == 5 || meeting.getState() == 7) {
			pass.setDisabled(false);
			back.setDisabled(false);
			temp.setDisabled(false);
			bAdvice.setDisabled(false);
		}
		if (meeting.getState() == 4 || meeting.getState() == 6) {
			pass.setSelected(true);
		}else if (meeting.getState() == 5) {
			back.setSelected(true);
		}else if(meeting.getState() == 7){
			temp.setSelected(true);
		}
		// 控制显示多少行，和每行的内容
		meetDeptList = jxkhProjectService.findProjectDep(meeting);
		for (int i = 1; i <= meetDeptList.size(); i++) {
			Jxkh_ProjectDept dept = (Jxkh_ProjectDept) meetDeptList.get(i - 1);
			switch (i) {
			case 1:
				r1.setVisible(true);
				dep1.setValue(dept.getName());
				break;
			case 2:
				r2.setVisible(true);
				dep2.setValue(dept.getName());
				break;
			case 3:
				r3.setVisible(true);
				dep3.setValue(dept.getName());
				break;
			case 4:
				r4.setVisible(true);
				dep4.setValue(dept.getName());
				break;
			case 5:
				r5.setVisible(true);
				dep5.setValue(dept.getName());
				break;
			case 6:
				r6.setVisible(true);
				dep6.setValue(dept.getName());
				break;
			case 7:
				r7.setVisible(true);
				dep7.setValue(dept.getName());
				break;
			}
		}

		// "通过" "退回" 的初始化
		String s = meeting.getTempState();
		if (s.charAt(0) == '1') {
			pass1.setChecked(true);
		}
		if (s.charAt(0) == '2') {
			back1.setChecked(true);
		}
		if (s.charAt(1) == '1') {
			pass2.setChecked(true);
		}
		if (s.charAt(1) == '2') {
			back2.setChecked(true);
		}
		if (s.charAt(2) == '1') {
			pass3.setChecked(true);
		}
		if (s.charAt(2) == '2') {
			back3.setChecked(true);
		}
		if (s.charAt(3) == '1') {
			pass4.setChecked(true);
		}
		if (s.charAt(3) == '2') {
			back4.setChecked(true);
		}
		if (s.charAt(4) == '1') {
			pass5.setChecked(true);
		}
		if (s.charAt(4) == '2') {
			back5.setChecked(true);
		}
		if (s.charAt(5) == '1') {
			pass6.setChecked(true);
		}
		if (s.charAt(5) == '2') {
			back6.setChecked(true);
		}
		if (s.charAt(6) == '1') {
			pass7.setChecked(true);
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
		meeting.setbAdvice(bAdvice.getValue()!=null?bAdvice.getValue():null);
		if (pass.isChecked()) {
			meeting.setState(JXKH_MEETING.BUSINESS_PASS);
		} else if (temp.isChecked()) {
			meeting.setState(JXKH_MEETING.BUSINESS_TEMP_PASS);
		}else 
			meeting.setState(JXKH_MEETING.BUSINESS_NOT_PASS);

		try {
			jxkhProjectService.saveOrUpdate(meeting);
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
