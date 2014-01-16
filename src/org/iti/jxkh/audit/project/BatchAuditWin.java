package org.iti.jxkh.audit.project;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.service.JxkhProjectService;
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
	private static final long serialVersionUID = 5151969361938519733L;
	private List<Jxkh_Project> awardList = new ArrayList<Jxkh_Project>();
	private List<Jxkh_ProjectDept> meetDeptList = new ArrayList<Jxkh_ProjectDept>();
	private Radio pass, out;
	private WkTUser user;
	private JxkhProjectService jxkhProjectService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
	}

	public void onClick$submit() {
		Jxkh_Project award = new Jxkh_Project();
		for (int i = 0; i < awardList.size(); i++) {
			award = awardList.get(i);
			meetDeptList = jxkhProjectService.findProjectDept(award);
			int index = 0;
			for (int t = 1; t <= meetDeptList.size(); t++) {
				Jxkh_ProjectDept dept = (Jxkh_ProjectDept) meetDeptList
						.get(t - 1);
				if (user.getDept().getKdName().equals(dept.getName())) {
					index = dept.getRank() - 1;
				}
			}
			String s = award.getTempState();
			char[] myStr = s.toCharArray();
			if (pass.isChecked()) {
				myStr[index] = '1';
				String s0 = new String(myStr);
				award.setTempState(s0);
				award.setState(JXKH_MEETING.First_Dept_Pass);
				jxkhProjectService.saveOrUpdate(award);
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
			jxkhProjectService.saveOrUpdate(award);
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

	public List<Jxkh_Project> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<Jxkh_Project> awardList) {
		this.awardList = awardList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
