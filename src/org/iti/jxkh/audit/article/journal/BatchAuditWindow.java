package org.iti.jxkh.audit.article.journal;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.service.JxkhQklwService;
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
	private static final long serialVersionUID = 1833886447172969477L;
	private Radio pass, back;
	private WkTUser user;
	private JxkhQklwService jxkhQklwService;
	private List<JXKH_QKLW> meetingList = new ArrayList<JXKH_QKLW>();
	private List<JXKH_QKLWDept> meetDeptList = new ArrayList<JXKH_QKLWDept>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		pass.setChecked(true);
	}

	public void onClick$submit() {
		JXKH_QKLW meeting = new JXKH_QKLW();
		for (int i = 0; i < meetingList.size(); i++) {
			meeting = meetingList.get(i);
			meetDeptList = jxkhQklwService.findMeetingDeptByMeetingId(meeting);
			int index = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				JXKH_QKLWDept dept = (JXKH_QKLWDept) meetDeptList.get(t - 1);
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
				jxkhQklwService.saveOrUpdate(meeting);

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
			jxkhQklwService.saveOrUpdate(meeting);
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

	public List<JXKH_QKLW> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<JXKH_QKLW> meetingList) {
		this.meetingList = meetingList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

}
