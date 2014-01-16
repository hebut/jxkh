package org.iti.jxkh.score.leader;

import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

public class LeaderWindow extends BaseWindow {
	private static final long serialVersionUID = -2803419586964832849L;
	private YearListbox yearlist;
	private Listbox leaderlist;
	private Grid grid;
	private Checkbox good, pass, fail;
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;

	public void initShow() {
		yearlist.initYearListbox("");
		
		leaderlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_AppraisalMember user = (JXKH_AppraisalMember) arg1;
				arg0.setLabel(user.getName());
				arg0.setValue(user);
				arg0.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						String year = yearlist.getSelectYear();
						List<JXKH_AuditResult> ldlist = auditResultService.findLeaderByYear(user.getKuId(), year);
						if(yearlist.getSelectedIndex() == 0){
							Messagebox.show("请选择年份！");
							return;
						}
						if (ldlist.size() == 0) {
							grid.setVisible(false);
							grid.setVisible(true);
						} else {
							grid.setVisible(true);
							grid.setVisible(false);
							Messagebox.show("已经评分！", "提示", Messagebox.OK, Messagebox.INFORMATION);
						}
					}
				});
			}
		});
		List<JXKH_AppraisalMember>dlist=auditConfigService.findleaderByDept("河北省科学技术情报研究院");//查询指定参加考核的院领导
		leaderlist.setModel(new ListModelList(dlist));
	}

	public void onClick$submit() {
		JXKH_AppraisalMember user = (JXKH_AppraisalMember) leaderlist.getSelectedItem().getValue();
		JXKH_AuditResult res = new JXKH_AuditResult();
		res.setKuId(user.getKuId());
		res.setKdId(user.getDeptId());
		res.setArType(JXKH_AuditResult.AR_LEADER);
		String year = yearlist.getSelectYear();
		res.setArYear(year);
		if (good.isChecked()) {
			res.setArLevel(JXKH_AuditResult.LEVEL_GOOD);
		} else if (pass.isChecked()) {
			res.setArLevel(JXKH_AuditResult.LEVEL_PASS);
		} else if (fail.isChecked()) {
			res.setArLevel(JXKH_AuditResult.LEVEL_FAIL);
		}
		auditConfigService.save(res);
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		grid.setVisible(true);
		grid.setVisible(false);
	}

	public void initWindow() {
	}

	public void onCheck$good() {
		if (good.isChecked()) {
			pass.setChecked(false);
			fail.setChecked(false);
		}
	}

	public void onCheck$pass() {
		if (pass.isChecked()) {
			good.setChecked(false);
			fail.setChecked(false);
		}
	}

	public void onCheck$fail() {
		if (fail.isChecked()) {
			good.setChecked(false);
			pass.setChecked(false);
		}
	}
}
