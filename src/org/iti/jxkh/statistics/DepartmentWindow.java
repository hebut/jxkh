package org.iti.jxkh.statistics;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class DepartmentWindow extends BaseWindow {
	private static final long serialVersionUID = -2032464190190651247L;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;
	private YearListbox yearlist;
	private Grid grid;
	private Label money, level,xishu,total,num,everage,rate,workscore,base,wordbase;
	private WkTUser user;

	public void onClick$query() {
		initWindow();
	}

	public void initShow() {
		//yearlist.initAnyyearlist();
		yearlist.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public void initWindow() {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		JXKH_AuditResult ar = auditResultService
				.findDepartment(year, user.getDept().getKdId());
		if (ar != null) {
			level.setValue(ar.getArLevel() + "档");
			money.setValue(ar.getArMoney() + "");
			if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
				xishu.setValue("1.0");
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
				xishu.setValue("1.3");
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
				xishu.setValue("1.6");
			}
			total.setValue(new DecimalFormat("#.00").format(ar.getArScore()) + "");
			WkTDept dept = (WkTDept) auditConfigService.loadById(WkTDept.class,user.getDept().getKdId());
			int numb = auditConfigService.findDeptMember(dept.getKdId());
			num.setValue(numb+"");
			everage.setValue(new DecimalFormat("#.00").format(ar.getArScore() / numb)+"");
			BigDecimal bd = new BigDecimal(ar.getArRate() * 100);
			rate.setValue(bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
			workscore.setValue(new DecimalFormat("#.00").format(ar.getArIndex()) + "");
			base.setValue(new DecimalFormat("#.00").format(ar.getArBase()) + "");
			JXKH_AuditConfig config = auditConfigService.findByYear(year);
			wordbase.setValue(config.getAcBase() + "");
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
