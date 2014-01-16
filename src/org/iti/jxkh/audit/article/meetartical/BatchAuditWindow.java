package org.iti.jxkh.audit.article.meetartical;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.service.JxkhHylwService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class BatchAuditWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -8305609774509236656L;
	private Radio pass, back;
	private WkTUser user;
	private JxkhHylwService jxkhHylwService;
	private List<JXKH_HYLW> meetingList = new ArrayList<JXKH_HYLW>();
	private List<JXKH_HYLWDept> meetDeptList = new ArrayList<JXKH_HYLWDept>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		pass.setChecked(true);
	}

	public void onClick$submit() {
		JXKH_HYLW meeting = new JXKH_HYLW();
		for (int i = 0; i < meetingList.size(); i++) {
			meeting = meetingList.get(i);
			meetDeptList = jxkhHylwService.findMeetingDeptByMeetingId(meeting);
			int index = 0;
			//int tt = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				JXKH_HYLWDept dept = (JXKH_HYLWDept) meetDeptList.get(t - 1);
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
				meeting.setLwState(JXKH_MEETING.First_Dept_Pass);
				jxkhHylwService.saveOrUpdate(meeting);

				int k = 1;
				String str = meeting.getTempState();
				for (int j = 0; j < meetDeptList.size(); j++) {
					if (str.charAt(j) != '1')
						k = 0;
				}
				if (k == 1)
					meeting.setLwState(JXKH_MEETING.DEPT_PASS);
			} else if (back.isChecked()) {
				myStr[index - 1] = '2';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setLwState(JXKH_MEETING.DEPT_NOT_PASS);
			}
			jxkhHylwService.saveOrUpdate(meeting);
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

	public List<JXKH_HYLW> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<JXKH_HYLW> meetingList) {
		this.meetingList = meetingList;
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}

}
