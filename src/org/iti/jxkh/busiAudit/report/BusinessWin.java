package org.iti.jxkh.busiAudit.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;

public class BusinessWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 2554078411012915032L;
	private Textbox reportName;
	private Listbox reportListbox, auditState, rank, dept;
	private Groupbox cxtj;
	private JxkhReportService jxkhReportService;
	private JXKHMeetingService jxkhMeetingService;
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private Set<Jxkh_ReportFile> filesList;
	private Paging reportPaging;
	private String nameSearch, depName, rankName;
	private Short auditStateSearch;
	private boolean isQuery = false;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		reportListbox.setItemRenderer(new ReportRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		initWindow();

		String[] report_Types = { "", "调研报告", "分析报告" };
		List<String> reportTpyeList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			reportTpyeList.add(report_Types[i]);
		}
		rank.setModel(new ListModelList(reportTpyeList));
		rank.setSelectedIndex(0);

		dept.setItemRenderer(new deptRenderer());
		List<WkTDept> depList = new ArrayList<WkTDept>();
		depList.add(new WkTDept());
		if (jxkhMeetingService.findAllDep() != null)
			depList.addAll(jxkhMeetingService.findAllDep());
		dept.setModel(new ListModelList(depList));
		dept.setSelectedIndex(0);
	}

	/** 部门列表渲染器 */
	public class deptRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			WkTDept d = (WkTDept) data;
			item.setValue(d);
			Listcell c0 = new Listcell();
			if (d.getKdName() == null) {
				c0.setLabel("--请选择--");
			} else {
				c0.setLabel(d.getKdName());
			}
			item.appendChild(c0);

			if (item.getIndex() == 0)
				item.setSelected(true);
		}
	}

	public void initWindow() {
		reportPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reportList = jxkhReportService.findReportByState(
						reportPaging.getActivePage(),
						reportPaging.getPageSize());
				reportListbox.setModel(new ListModelList(reportList));
			}
		});
		int totalNum = jxkhReportService.findReportByState();
		reportPaging.setTotalSize(totalNum);
		reportList = jxkhReportService.findReportByState(
				reportPaging.getActivePage(), reportPaging.getPageSize());
		reportListbox.setModel(new ListModelList(reportList));

		String[] a = { "", "待审核", "部门审核中","部门通过",  "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
	}

	// 报告列表渲染器
	public class ReportRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Report report = (Jxkh_Report) data;
			List<Jxkh_ReportMember> memberList = jxkhReportService
					.findReportMemberByReportId(report);
			String member = "";
			item.setValue(report);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(report.getName().length() <= 12?
					report.getName():report.getName().substring(0, 12) + "...");
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Report report = (Jxkh_Report) item.getValue();
					AddReportWin w = (AddReportWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/report/addReport.zul",
									null, null);
					try {
						w.setReport(report);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (isQuery) {
						onClick$query();
					} else {
						initWindow();
					}
				}
			});
			Listcell c3 = new Listcell(report.getType());
			Listcell c4 = new Listcell();
			if (report.getjxYear() != null) {
				c4 = new Listcell(report.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			Listcell c5 = new Listcell();
			Listcell c8 = new Listcell();
			Toolbarbutton downlowd = new Toolbarbutton();
			Toolbarbutton addrecode = new Toolbarbutton();
			downlowd.setImage("/css/default/images/button/down.gif");
			addrecode.setImage("/css/default/images/button/actEdit.gif");
			
			downlowd.setParent(c5);
			downlowd.setHeight("20px");
			addrecode.setParent(c8);
			addrecode.setHeight("20px");
			downlowd.setTooltip("附件");
			addrecode.setTooltip("填写档案号");
			downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/award/download.zul",
									null, null);
					filesList = report.getReportFile();
					win.setFiles(filesList);
					win.setFlag("REPORT");
					win.initWindow();
					win.doModal();
				}
			});
			addrecode.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					if (report.getState() == 0 
							|| report.getState() == 1
							|| report.getState() == 2 
							|| report.getState() == 3
							|| report.getState() == 5
							|| report.getState() == 7) {
						try {
							Messagebox.show("业务办还未审核通过，不能进行归档！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					RecordCodeWin win = (RecordCodeWin) Executions
							.createComponents(
									"/admin/jxkh/busiAudit/report/recordcode.zul",
									null, null);
					try {
						win.setReport(report);
						win.initWindow();
						win.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (isQuery) {
						onClick$query();
					} else {
						initWindow();
					}
				}
			});
			Listcell c6 = new Listcell(report.getScore() == null ? "" : report
					.getScore().toString());
			Listcell c7 = new Listcell();
			c7.setTooltiptext("点击填写审核意见");
			if (report.getState() == null || report.getState() == 0) {
				c7.setLabel("待审核");
				c7.setStyle("color:red");
			} else {
				switch (report.getState()) {
				case 0:
					break;
				case 1:
					c7.setLabel("部门通过");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("部门审核中");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("部门不通过");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("业务办通过");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("业务办不通过");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("归档");
					c7.setStyle("color:red");
					break;
				case JXKH_MEETING.BUSINESS_TEMP_PASS:
					c7.setLabel("业务办暂缓通过");
					c7.setStyle("color:red");
					break;
				}
			}
			// 弹出审核意见事件
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					if (report.getState() == null || report.getState() == 0
							|| report.getState() == 2 || report.getState() == 3) {
						try {
							Messagebox.show("部门审核通过后，业务办才可以审核！", "提示",
									Messagebox.OK, Messagebox.INFORMATION);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/busiAudit/report/advice.zul", null,
							null);
					try {
						w.setMeeting(report);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (isQuery) {
						onClick$query();
					} else {
						initWindow();
					}
				}
			});
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c7);
			item.appendChild(c8);
		}
	}

	public void onClick$passAll() {
		if (reportListbox.getSelectedItems() == null
				|| reportListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("请选择要审核报告！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Iterator<Listitem> items = reportListbox.getSelectedItems().iterator();
		List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
		Jxkh_Report report = new Jxkh_Report();
		while (items.hasNext()) {
			report = (Jxkh_Report) items.next().getValue();
			if (report.getState() == 1 || report.getState() == 4
					|| report.getState() == 5) {
				reportList.add(report);
			}
		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/busiAudit/report/batchAudit.zul", null, null);
		try {
			win.setReportList(reportList);
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (isQuery) {
			onClick$query();
		} else {
			initWindow();
		}
	}

	/** 状态列表渲染器 */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--请选择--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query() {
		nameSearch = null;
		nameSearch = reportName.getValue();
		auditStateSearch = null;
		isQuery = true;
		if (auditState.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("待审核")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("部门通过")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("部门审核中")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("部门不通过")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("业务办通过")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("业务办不通过")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("归档")) {
			auditStateSearch = 6;
		}else if (auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
			auditStateSearch = JXKH_MEETING.BUSINESS_TEMP_PASS;
		}
		rankName = null;
		rankName = rank.getSelectedItem().getValue() + "";
		depName = null;
		if (dept.getSelectedIndex() != 0) {
			WkTDept d = (WkTDept) dept.getSelectedItem().getValue();
			depName = d.getKdName();
		}

		reportPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reportList = jxkhReportService.findReportByCondition(
						nameSearch, auditStateSearch, rankName, depName,
						reportPaging.getActivePage(),
						reportPaging.getPageSize());
				reportListbox.setModel(new ListModelList(reportList));
			}
		});
		int totalNum = jxkhReportService.findReportByCondition(nameSearch,
				auditStateSearch, rankName, depName);
		reportPaging.setTotalSize(totalNum);
		reportList = jxkhReportService.findReportByCondition(nameSearch,
				auditStateSearch, rankName, depName,
				reportPaging.getActivePage(), reportPaging.getPageSize());
		reportListbox.setModel(new ListModelList(reportList));
	}

	public void onClick$searchcbutton() {
		if (cxtj.isVisible()) {
			cxtj.setVisible(false);
		} else {
			cxtj.setVisible(true);
		}

	}

	public void onClick$reset() {
		reportName.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		rank.getItemAtIndex(0).setSelected(true);
		dept.getItemAtIndex(0).setSelected(true);
	}

	// 导出
	public void onClick$expert() throws WriteException, IOException {
		if (reportListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("提示请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Report> expertlist = new ArrayList<Jxkh_Report>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = reportListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Report p = (Jxkh_Report) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "baogaoxinxi" + ".xls";
			String Title = "报告情况";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("报告名称");
			titlelist.add("完成人");
			titlelist.add("院内部门");
			titlelist.add("院外部门");
			titlelist.add("报告种类");
			titlelist.add("批示领导");
			titlelist.add("完成时间");
			titlelist.add("科学领域");
			titlelist.add("信息填写人");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Report report = (Jxkh_Report) expertlist.get(j);
				List<Jxkh_ReportMember> mlist = report.getReportMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = report.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_ReportMember mem = (Jxkh_ReportMember) mlist
								.get(m);
						member += mem.getName() + "、";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ReportDept> dlist = report.getReprotDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ReportDept de = (Jxkh_ReportDept) dlist.get(m);
						dept += de.getName() + "、";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = report.getCoCompany();
				c[j][5] = report.getType();
				c[j][6] = report.getLeader().getKbName();
				c[j][7] = report.getDate();
				c[j][8] = report.getFiled();
				c[j][9] = report.getSubmitId();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
