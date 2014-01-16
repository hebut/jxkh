package org.iti.jxkh.score.work;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_PointNumber;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.framework.entity.WkTUser;

public class PointWindow extends BaseWindow {
	private static final long serialVersionUID = -2803419586964832849L;
	private YearListbox yearlist;
	private Listbox listbox;//
	private Label number;
	private WkTUser user;
	private Toolbarbutton add;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;

	public void initShow() {
		yearlist.initYearListbox("");
		//yearlist.initAnyyearlist();
		//没有选择年份的时候添加按钮不可用 20120714
		if (yearlist.getSelectedIndex() == 0
				|| yearlist.getSelectedItem() == null)
			add.setDisabled(true);
		else
			add.setDisabled(false);
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult res = (JXKH_AuditResult) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, res.getKuId());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell();
				if (user.getKuSex().equals("1"))
					c2.setLabel("男");
				else
					c2.setLabel("女");
				Listcell c3 = new Listcell(user.getDept().getKdName());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initWindow();
	}

	public void initWindow() {
		/*
		 * JxYear jy = (JxYear)yearlist.getSelectedItem().getValue(); String
		 * year = jy.getYears(); JXKH_AuditResult res =
		 * auditResultService.findWorkDeptByKdIdAndYear
		 * (user.getDept().getKdId(), yearlist .getSelectedItem().getLabel());
		 * List<JXKH_AuditResult> ulist =
		 * auditResultService.findWorkByLevel(user.getDept().getKdId(),
		 * yearlist.getSelectedItem() .getLabel(), (short) (res.getArLevel() +
		 * 1)); listbox.setModel(new ListModelList(ulist));
		 * List<JXKH_AuditResult> tlist =
		 * auditResultService.findWorkByKdId(user.getDept().getKdId(),
		 * yearlist.getSelectedItem() .getLabel()); JXKH_AuditResult res =
		 * auditResultService
		 * .findWorkDeptByKdIdAndYear(user.getDept().getKdId(), year); if(res ==
		 * null ) { try { Messagebox.show("当年部门还未进行考核，请先进行业绩考核！"); } catch
		 * (InterruptedException e) { e.printStackTrace(); } return; }
		 * List<JXKH_AuditResult> ulist =
		 * auditResultService.findWorkByLevel(user.getDept().getKdId(), year,
		 * (short) (res.getArLevel() + 1)); listbox.setModel(new
		 * ListModelList(ulist)); List<JXKH_AuditResult> tlist =
		 * auditResultService.findWorkByKdId(user.getDept().getKdId(), year);
		 * int total = tlist.size(); int num = new BigDecimal((float) total
		 * auditConfigService.findByYear(year).getAcBFloat()).setScale(0,
		 * BigDecimal.ROUND_HALF_UP).intValue(); number.setValue(num + "");
		 */
	}

	public void onSelect$yearlist() {
		/*JxYear jy = (JxYear) yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		/*
		 * JXKH_AuditResult res =
		 * auditResultService.findWorkDeptByKdIdAndYear(user
		 * .getDept().getKdId(), yearlist .getSelectedItem().getLabel());
		 * List<JXKH_AuditResult> ulist =
		 * auditResultService.findWorkByLevel(user.getDept().getKdId(),
		 * yearlist.getSelectedItem() .getLabel(), (short) (res.getArLevel() +
		 * 1)); listbox.setModel(new ListModelList(ulist));
		 * List<JXKH_AuditResult> tlist =
		 * auditResultService.findWorkByKdId(user.getDept().getKdId(),
		 * yearlist.getSelectedItem() .getLabel());
		 */
		JXKH_AuditResult res = auditResultService.findWorkDeptByKdIdAndYear(user.getDept().getKdId(), year);
		if (res == null) {
			try {
				Messagebox.show("当年部门还未进行考核，请先进行业绩考核！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			number.setValue("");
			List<JXKH_AuditResult> list = new ArrayList<JXKH_AuditResult>();
			listbox.setModel(new ListModelList(list));
			add.setDisabled(true);
		} else {
			add.setDisabled(false);
			List<JXKH_AuditResult> ulist = auditResultService.findWorkByLevel(user.getDept().getKdId(), year, (short) (res.getArLevel() + 1));
			listbox.setModel(new ListModelList(ulist));
			JXKH_PointNumber pn = auditResultService.findPointNumber(year, user.getDept().getKdId());
			int num = 0;
			if (pn == null) {
				List<JXKH_AuditResult> tlist = auditResultService.findWorkByKdId(user.getDept().getKdId(), year);
				int total = tlist.size();
				num = new BigDecimal((float) total * auditConfigService.findByYear(year).getAcBFloat()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			} else {
				num = pn.getPnNumber();
			}
			number.setValue(num + "");
		}
	}

	public void onClick$add() {
		/*JxYear jy = (JxYear) yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		final AddWindow win = (AddWindow) Executions.createComponents("/admin/jxkh/score/work/point/add.zul", null, null);
		win.setYear(year);
		win.initWindow();
		win.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				win.detach();
				initWindow();
			}
		});
		win.doHighlighted();
	}
}
