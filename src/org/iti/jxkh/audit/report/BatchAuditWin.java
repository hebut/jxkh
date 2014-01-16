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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang C
	 */
	private static final long serialVersionUID = -1072196319348834971L;
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private List<Jxkh_ReportDept> meetDeptList = new ArrayList<Jxkh_ReportDept>();
	private Radio pass, back;
	private WkTUser user;
	private JxkhReportService jxkhReportService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		pass.setChecked(true);
	}

	public void onClick$submit() {
		Jxkh_Report meeting = new Jxkh_Report();
		for (int i = 0; i < reportList.size(); i++) {
			meeting = reportList.get(i);
			meetDeptList = jxkhReportService.findReportDeptByReportId(meeting);
			int index = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				Jxkh_ReportDept dept = (Jxkh_ReportDept) meetDeptList
						.get(t - 1);
				if (user.getDept().getKdName().equals(dept.getName())) {
					index = dept.getRank();
				}
			}
			String s = meeting.getTempState();
			char[] myStr = s.toCharArray();
			if (pass.isChecked()) {
				myStr[index - 1] = '1';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setState(JXKH_MEETING.First_Dept_Pass);
				jxkhReportService.saveOrUpdate(meeting);

				int k = 1;
				String str = meeting.getTempState();
				for (int j = 0; j < meetDeptList.size(); j++) {
					if (str.charAt(j) != '1')
						k = 0;
				}
				if (k == 1)
					meeting.setState(JXKH_MEETING.DEPT_PASS);
			} else if (back.isChecked()) {
				myStr[index - 1] = '2';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setState(JXKH_MEETING.DEPT_NOT_PASS);
			}
			jxkhReportService.saveOrUpdate(meeting);
		}

		try {
			Messagebox.show("������˳ɹ���", "��ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("�������ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public List<Jxkh_Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Jxkh_Report> reportList) {
		this.reportList = reportList;
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}
}
