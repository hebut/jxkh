package org.iti.jxkh.busiAudit.report;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 76920826025913101L;
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private Radio pass,temp;
	private JxkhReportService jxkhReportService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
	}

	public void onClick$submit() {
		Short state = null;		
		if (pass.isChecked()) {
			state = JXKH_QKLW.BUSINESS_PASS;
		}else if(temp.isChecked()) {
			state = JXKH_QKLW.BUSINESS_TEMP_PASS;
		}else {
			state = JXKH_QKLW.BUSINESS_NOT_PASS;
		}
		for (int i = 0; i < reportList.size(); i++) {
			Jxkh_Report report = reportList.get(i);
			report.setState(state);
			jxkhReportService.saveOrUpdate(report);
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

	public List<Jxkh_Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Jxkh_Report> reportList) {
		this.reportList = reportList;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}
}
