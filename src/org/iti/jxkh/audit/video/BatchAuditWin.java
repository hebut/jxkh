package org.iti.jxkh.audit.video;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.service.JxkhVideoService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -8029818273276053823L;
	private List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
	private List<Jxkh_VideoDept> meetDeptList = new ArrayList<Jxkh_VideoDept>();
	private Radio pass, out;
	private WkTUser user;
	private JxkhVideoService jxkhVideoService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
	}

	public void onClick$submit() {
		Jxkh_Video award = new Jxkh_Video();
		for (int i = 0; i < videoList.size(); i++) {
			award = videoList.get(i);
			meetDeptList = jxkhVideoService.findVideoDeptrByVideoId(award);
			int index = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				Jxkh_VideoDept dept = (Jxkh_VideoDept) meetDeptList.get(t - 1);
				if (user.getDept().getKdName().equals(dept.getName())) {
					index = dept.getRank();
				}
			}
			String s = award.getTempState();
			char[] myStr = s.toCharArray();
			if (pass.isChecked()) {
				myStr[index - 1] = '1';
				String s0 = new String(myStr);
				award.setTempState(s0);
				award.setState(JXKH_MEETING.First_Dept_Pass);
				jxkhVideoService.saveOrUpdate(award);
				int k = 1;
				String str = award.getTempState();
				for (int j = 0; j < meetDeptList.size(); j++) {
					if (str.charAt(j) != '1')
						k = 0;
				}
				if (k == 1)
					award.setState(JXKH_MEETING.DEPT_PASS);
			} else if (out.isChecked()) {
				myStr[index - 1] = '2';
				String s0 = new String(myStr);
				award.setTempState(s0);
				award.setState(JXKH_MEETING.DEPT_NOT_PASS);
			}
			jxkhVideoService.saveOrUpdate(award);
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

	public List<Jxkh_Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<Jxkh_Video> videoList) {
		this.videoList = videoList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
