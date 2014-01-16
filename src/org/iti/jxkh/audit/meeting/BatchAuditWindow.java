package org.iti.jxkh.audit.meeting;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.service.JXKHMeetingService;
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
	private JXKHMeetingService jxkhMeetingService;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
	private List<JXKH_MeetingDept> meetDeptList = new ArrayList<JXKH_MeetingDept>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		pass.setChecked(true);
	}

	public void onClick$submit() {
		JXKH_MEETING meeting = new JXKH_MEETING();
		for (int i = 0; i < meetingList.size(); i++) {
			meeting = meetingList.get(i);
			meetDeptList = jxkhMeetingService
					.findMeetingDeptByMeetingId(meeting);
			int index = 0;
			//int tt = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				JXKH_MeetingDept dept = (JXKH_MeetingDept) meetDeptList
						.get(t - 1);
				if (user.getDept().getKdName().equals(dept.getName())) {
					index = dept.getRank();
					//tt = t;
				}
			}
			/*System.out.println("tt=" + tt);
			System.out.println(tt == index);
			System.out.println("下标是：" + index);*/
			String s = meeting.getTempState();
			char[] myStr = s.toCharArray();
			if (pass.isChecked()) {
				myStr[index - 1] = '1';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setMtState(JXKH_MEETING.First_Dept_Pass);
				jxkhMeetingService.saveOrUpdate(meeting);

				int k = 1;
				String str = meeting.getTempState();
				for (int j = 0; j < meetDeptList.size(); j++) {
					if (str.charAt(j) != '1')
						k = 0;
				}
				if (k == 1)
					meeting.setMtState(JXKH_MEETING.DEPT_PASS);
			} else if (back.isChecked()) {
				myStr[index - 1] = '2';
				String s0 = new String(myStr);
				meeting.setTempState(s0);
				meeting.setMtState(JXKH_MEETING.DEPT_NOT_PASS);
			}
			jxkhMeetingService.saveOrUpdate(meeting);
		}

		try {
			Messagebox.show("批量审核成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("批量审核失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public List<JXKH_MEETING> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<JXKH_MEETING> meetingList) {
		this.meetingList = meetingList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

}
