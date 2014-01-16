package org.iti.jxkh.score.work;

import java.math.BigDecimal;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_PointNumber;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTDept;

public class NumberWindow extends BaseWindow {

	private static final long serialVersionUID = 6664095338604954067L;
	private Listbox listbox;
	private YearListbox yearlist;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;
	private String year;
	private JXKH_AuditConfig config;

	public void initShow() {
		yearlist.initAnyyearlist();
		listbox.setItemRenderer(new ListitemRenderer() {

			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_AuditResult res = (JXKH_AuditResult) arg1;
				WkTDept dept = (WkTDept) auditConfigService.loadById(WkTDept.class, res.getKdId());
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(dept.getKdName());
				int num = auditConfigService.findDeptMember(dept.getKdId());
				Listcell c2 = new Listcell(num + "");
				Listcell c3 = new Listcell();

				final JXKH_PointNumber pn = auditResultService.findPointNumber(year, res.getKdId());
				final Intbox ib = new Intbox();
				ib.setWidth("20px");
				if (pn == null) {
					float a = config.getAcBFloat();
					int n = new BigDecimal(num * a).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					ib.setValue(n);
				} else {
					ib.setValue(pn.getPnNumber());
				}
				Button bt = new Button();
				bt.setLabel("确定");
				c3.appendChild(ib);
				c3.appendChild(bt);
				bt.addEventListener(Events.ON_CLICK, new EventListener() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						if (pn == null) {
							JXKH_PointNumber pn_new = new JXKH_PointNumber();
							pn_new.setKdId(res.getKdId());
							pn_new.setPnYear(year);
							pn_new.setPnNumber(ib.getValue());
							auditResultService.save(pn_new);
						} else {
							pn.setPnNumber(ib.getValue());
							auditResultService.update(pn);
						}
						Messagebox.show("修改成功!", "提示", Messagebox.OK, Messagebox.INFORMATION);
					}
				});
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
	}

	public void initWindow() {

	}

	public void onSelect$yearlist() {
		JxYear jy = (JxYear) yearlist.getSelectedItem().getValue();
		year = jy.getYears();

		config = auditConfigService.findByYear(year);
		listbox.setModel(new ListModelList(auditResultService.findDept(year)));
	}
}
