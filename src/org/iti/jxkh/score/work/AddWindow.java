package org.iti.jxkh.score.work;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class AddWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 7709604284024931540L;
	private Listbox deptSelect, listbox1, listbox2;
	private WkTUser user;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;
	private String year;
	private JXKH_AuditConfig config;
	private int total = 0;

	public void setYear(String year) {
		this.year = year;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		deptSelect.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				WkTDept dept = (WkTDept) arg1;
				arg0.setLabel(dept.getKdName());
				arg0.setValue(arg1);
			}
		});
		listbox1.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult res = (JXKH_AuditResult) arg1;
				arg0.setValue(arg1);
				WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, res.getKuId());
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuLid());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(deptSelect.getSelectedItem().getLabel());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
		listbox2.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult res = (JXKH_AuditResult) arg1;
				arg0.setValue(arg1);
				WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, res.getKuId());
				Listcell c0 = new Listcell();
				Listcell c1 = new Listcell(user.getKuLid());
				Listcell c2 = new Listcell(user.getKuName());
				Listcell c3 = new Listcell(deptSelect.getSelectedItem().getLabel());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
			}
		});
	}

	public void initWindow() {
		config = auditConfigService.findByYear(year);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		List<WkTDept> deptlist = new ArrayList<WkTDept>();
		deptlist.add(user.getDept());
		deptSelect.setModel(new ListModelList(deptlist));
		deptSelect.setSelectedIndex(0);
	}

	public void onClick$search() {
		WkTDept dept = (WkTDept) deptSelect.getSelectedItem().getValue();
		JXKH_AuditResult res = auditResultService.findWorkDeptByKdIdAndYear(dept.getKdId(), year);
		List<JXKH_AuditResult> ulist1 = auditResultService.findWorkByLevel(dept.getKdId(), year, res.getArLevel());
		List<JXKH_AuditResult> ulist2 = auditResultService
				.findWorkByLevel(dept.getKdId(), year, (short) (res.getArLevel() + 1));
		listbox1.setModel(new ListModelList(ulist1));
		listbox2.setModel(new ListModelList(ulist2));
		total = ulist1.size() + ulist2.size();
	}

	public void onClick$add() {
		Object[] items = listbox1.getSelectedItems().toArray();
		int num = new BigDecimal((float) total * config.getAcBFloat()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		for (int i = 0; i < items.length; i++) {
			if (listbox2.getItemCount() >= num) {
				return;
			}
			Listitem item = (Listitem) items[i];
			if (listbox2.getItemCount() == 0) {
				listbox2.appendChild(item);
			} else {
				Listitem topItem = (Listitem) listbox2.getItems().get(0);
				listbox2.insertBefore(item, topItem);
			}
		}
	}

	public void onClick$delete() {
		Object[] items = listbox2.getSelectedItems().toArray();
		for (int i = 0; i < items.length; i++) {
			Listitem item = (Listitem) items[i];
			if (listbox1.getItemCount() == 0) {
				listbox1.appendChild(item);
			} else {
				Listitem topItem = (Listitem) listbox1.getItems().get(0);
				listbox1.insertBefore(item, topItem);
			}
		}
	}

	public void onClick$submit() {
		WkTDept dept = (WkTDept) deptSelect.getSelectedItem().getValue();
		JXKH_AuditResult res = auditResultService.findWorkDeptByKdIdAndYear(dept.getKdId(), year);
		JXKH_AuditConfig config = auditConfigService.findByYear(year);
		for (JXKH_AuditResult r : auditResultService.findWorkByLevel(dept.getKdId(), year, (short) (res.getArLevel() + 1f))) {
			r.setArLevel(res.getArLevel());
			if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
				r.setArMoney(config.getAcBase());
			} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
				r.setArMoney(1.3f * config.getAcBase());
			} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
				r.setArMoney(1.6f * config.getAcBase());
			}
			auditResultService.update(r);
		}
		Object[] items = listbox2.getItems().toArray();
		for (int i = 0; i < items.length; i++) {
			Listitem item = (Listitem) items[i];
			JXKH_AuditResult result = (JXKH_AuditResult) item.getValue();
			if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
				result.setArMoney(1.3f * config.getAcBase());
				result.setArLevel(JXKH_AuditResult.LEVEL_TWO);
			} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
				result.setArMoney(1.6f * config.getAcBase());
				result.setArLevel(JXKH_AuditResult.LEVEL_THREE);
			} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
				result.setArMoney(2f * config.getAcBase());
				result.setArLevel(JXKH_AuditResult.LEVEL_FOUR);
			}
			auditResultService.update(result);
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}
}