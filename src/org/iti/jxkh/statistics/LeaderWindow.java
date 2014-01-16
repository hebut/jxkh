package org.iti.jxkh.statistics;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTUser;

public class LeaderWindow extends BaseWindow {
	private static final long serialVersionUID = -2032464190190651247L;
	private AuditResultService auditResultService;
//	private Listbox yearlist;
	private YearListbox yearlist;
	private Grid grid;
	private Label money, level;
	private WkTUser user;

	public void onClick$query() {
		initWindow();
	}

	public void initShow() {
		yearlist.initAnyyearlist();
		
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public void initWindow() {
		JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();
		JXKH_AuditResult ar = auditResultService.findPersonal(year, user.getKuId());
		if (ar != null) {
			if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_GOOD) {
				level.setValue("优秀");
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_PASS) {
				level.setValue("合格");
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FAIL) {
				level.setValue("不合格");
			}
			money.setValue(ar.getArMoney() + "");
			grid.setVisible(true);
		}else {
			try {
				Messagebox.show("无当年考核结果！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
