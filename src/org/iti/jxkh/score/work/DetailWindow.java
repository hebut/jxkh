package org.iti.jxkh.score.work;

import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;

public class DetailWindow extends BaseWindow {//业务人员明细
	private static final long serialVersionUID = -2803419586964832849L;
	private Listbox deptlist;
	private Label level, money;
	private Grid grid;
	private AuditResultService auditResultService;
	private WkTUser user;
	private YearListbox yearlist;
	
	public void initShow() {
		yearlist.initYearListbox("");
		deptlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_AuditResult arg = (JXKH_AuditResult) arg1;
				WkTUser u = (WkTUser) auditResultService.loadById(WkTUser.class, arg.getKuId());
				Listcell c0 = new Listcell(u.getKuName());
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						grid.setVisible(false);
						grid.setVisible(true);
						level.setValue(arg.getArLevel() + "档");
						money.setValue(arg.getArMoney() + "");
					}
				});
			}
		});
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//initDeptList();
	}
	public void initDeptList(){
		List<JXKH_AuditResult> dlist = auditResultService.findWorkByKdId(user.getKdId(), yearlist.getSelectYear());
		deptlist.setModel(new ListModelList(dlist));
	}
	public void initWindow() {
	}
	public void onSelect$yearlist() {
		initDeptList();
	}
}
