package org.iti.jxkh.busiAudit.meeting;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.service.JXKHMeetingService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = -8305609774509236656L;
	private Radio pass,temp;
	private JXKHMeetingService jxkhMeetingService;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
	}

	public void onClick$submit() {
		JXKH_MEETING meeting = new JXKH_MEETING();
		Short state = null;		
		if (pass.isChecked()) {
			state = JXKH_QKLW.BUSINESS_PASS;
		}else if(temp.isChecked()) {
			state = JXKH_QKLW.BUSINESS_TEMP_PASS;
		}else {
			state = JXKH_QKLW.BUSINESS_NOT_PASS;
		}
		for (int i = 0; i < meetingList.size(); i++) {
			meeting = meetingList.get(i);
			meeting.setMtState(state);
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
