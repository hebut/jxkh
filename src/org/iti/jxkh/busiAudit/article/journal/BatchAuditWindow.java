package org.iti.jxkh.busiAudit.article.journal;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.service.JxkhQklwService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWindow extends Window implements AfterCompose {

	/**
	 * @author CXX
	 */
	private static final long serialVersionUID = 1833886447172969477L;
	private Radio pass,temp;
	private JxkhQklwService jxkhQklwService;
	private List<JXKH_QKLW> meetingList = new ArrayList<JXKH_QKLW>();

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
	}

	public void onClick$submit() {
		JXKH_QKLW meeting = new JXKH_QKLW();
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
			meeting.setLwState(state);
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
