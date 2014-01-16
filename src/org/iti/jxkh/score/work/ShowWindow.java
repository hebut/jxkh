package org.iti.jxkh.score.work;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;

import com.iti.common.util.PropertiesLoader;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class ShowWindow extends BaseWindow {

	private static final long serialVersionUID = -2803419586964832849L;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;
	private BusinessIndicatorService businessIndicatorService;
	private YearListbox yearlist;
	private Listbox deptlist;// , yearlist;
	private Tabbox tabbox;
	private Grid grid;
	private Tabs tabs;
	private Label total, average, rate, level, people;
	private List<String> wlist = new ArrayList<String>();
	private List<EventListener> elist = new ArrayList<EventListener>();

	public void initShow() {
		yearlist.initYearListbox("");
		yearlist.addEventListener(Events.ON_SELECT, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				/*JxYear jy = (JxYear) yearlist.getSelectedItem().getValue();
				String year = jy.getYears();*/
				String year = yearlist.getSelectYear();
				List<JXKH_AuditResult> dlist = auditResultService
						.findDeptByYear(year);
				if (dlist.size() == 0) {
					Messagebox.show("当前无结果，请先进行考核！");
					return;
				}
				deptlist.setModel(new ListModelList(dlist));
			}
		});
		deptlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult arg = (JXKH_AuditResult) arg1;
				WkTDept dept = (WkTDept) auditResultService.loadById(
						WkTDept.class, arg.getKdId());
				Listcell c0 = new Listcell(dept.getKdName());
				Listcell c1 = new Listcell();
				if(arg.getArLevel() != null){
					c1.setLabel(arg.getArLevel() + "");
				}
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.setValue(arg);
				arg0.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						tabbox.setVisible(false);
						tabbox.setVisible(true);
						grid.setVisible(false);
						grid.setVisible(true);
						initGrid();
					}
				});
			}
		});
	}

	public void initGrid() {
		/*JxYear jy = (JxYear) yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		JXKH_AuditResult res = (JXKH_AuditResult) deptlist.getSelectedItem()
				.getValue();
		String key = res.getArKeys();
		String value = res.getArValues();
		String scores = res.getArExpand();
		key.split("-");
		final String[] keyArray = key.split("-");
		String[] valueArray = value.split("-");
		String[] scoreArray = scores.split("-");
		Properties property = PropertiesLoader.loader("title",
				"title.properties");
		for (int i = 0; i < keyArray.length; i++) {
			Jxkh_BusinessIndicator bi = (Jxkh_BusinessIndicator) businessIndicatorService
					.getEntityByName(property.getProperty(keyArray[i]));
			Label weight = (Label) this.getFellow("flag" + (i + 1))
					.getNextSibling();
			if (wlist.size() != keyArray.length) {
				wlist.add(weight.getValue());
			}
			Toolbarbutton num = (Toolbarbutton) this
					.getFellow("flag" + (i + 1)).getNextSibling()
					.getNextSibling();
			Label score = (Label) this.getFellow("flag" + (i + 1))
					.getNextSibling().getNextSibling().getNextSibling();
			final int index = i;
			if (bi != null) {
				EventListener event = new EventListener() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						JXKH_AuditResult res = (JXKH_AuditResult) deptlist
								.getSelectedItem().getValue();
						WkTDept dept = (WkTDept) auditResultService.loadById(
								WkTDept.class, res.getKdId());
						// List<?> rlist =
						// auditResultService.findListForShow(yearlist.getSelectedItem().getLabel(),
						// dept.getKdNumber(), keyArray[index],
						// tabs.getTabbox().getSelectedIndex());
						ShowDetailWindow win = (ShowDetailWindow) Executions
								.createComponents(
										"/admin/jxkh/score/work/show/show.zul",
										null, null);
						// String path = "C://save.perperties";
						// Properties property = new Properties();
						// FileInputStream inputFile = new
						// FileInputStream(path);
						// property.load(inputFile);
						// inputFile.close();
						String key = yearlist.getSelectedItem().getLabel()
								+ "-" + dept.getKdNumber() + "-"
								+ keyArray[index];
						win.initWindow(key, tabs.getTabbox().getSelectedIndex());
						win.doHighlighted();
					}
				};
				elist.add(event);
				for (EventListener e : elist) {
					num.removeEventListener(Events.ON_CLICK, e);
				}
				 num.addEventListener(Events.ON_CLICK, event);
				weight.setValue(Math.round(bi.getKbScore() * bi.getKbIndex())
						+ wlist.get(i));
				num.setLabel(valueArray[i]);
				score.setValue(Float.parseFloat(scoreArray[i]) + "");
			} else {
				weight.setValue("0" + wlist.get(i));
				num.setLabel("0");
				score.setValue("0");
			}
		}
		total.setValue(res.getArScore() + "");
		//int num = auditConfigService.findDeptMember(res.getKdId());//计算在职非外聘人数
		int num = auditConfigService.findCheckPeoByDeptId(res.getKdId());
		average.setValue((res.getArScore() / num) + "");
		BigDecimal bd = new BigDecimal(res.getArRate() * 100);
		rate.setValue(bd.setScale(2,
				BigDecimal.ROUND_HALF_UP).toString()
				+ "%");
		if(res.getArLevel() != null){
			level.setValue(res.getArLevel() + "档");
		}
		List<JXKH_AuditResult> ulist = auditResultService.findWorkByLevel(res.getKdId(), year, res.getArLevel() != null?(short) (res.getArLevel() + 1):null);
		String peoples = "";
		for(int i=0;i<ulist.size();i++) {
			JXKH_AuditResult re = (JXKH_AuditResult) ulist.get(i);
			WkTUser user = (WkTUser) auditResultService.loadById(WkTUser.class, re.getKuId());
			peoples += user.getKuName()+",";
		}
		if(!peoples.equals("")){
			peoples = peoples.substring(0, peoples.length()-1);
		}
		people.setValue(peoples);
	}

	public void initWindow() {
	}
}
