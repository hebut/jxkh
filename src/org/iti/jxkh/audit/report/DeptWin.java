package org.iti.jxkh.audit.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.deptbusiness.report.AddReportWin;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.JxkhReportService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
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
import org.zkoss.zul.Window;
import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class DeptWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = 6457783967925654629L;
	private Textbox reportName;
	private Listbox reportListbox, auditState, rank;
	private Groupbox cxtj;
	private YearListbox year;
	private JxkhReportService jxkhReportService;
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private WkTUser user;
	private Paging reportPaging;
	private String nameSearch, indicatorId, yearSearch;
	private Short auditStateSearch;
	private boolean isQuery = false;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		year.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		reportListbox.setItemRenderer(new ReportRenderer());
		auditState.setItemRenderer(new auditStateRenderer());
		initWindow();
		String[] report_Types = { "", "���б���", "��������" };
		List<String> reportTpyeList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			reportTpyeList.add(report_Types[i]);
		}
		rank.setModel(new ListModelList(reportTpyeList));
		rank.setSelectedIndex(0);
	}

	public void initWindow() {
		reportPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reportList = jxkhReportService.findReportByKbNumber2(user
						.getDept().getKdNumber(), reportPaging.getActivePage(),
						reportPaging.getPageSize());
				reportListbox.setModel(new ListModelList(reportList));
			}
		});
		int totalNum = jxkhReportService.findReportByKbNumber2(user.getDept()
				.getKdNumber());
		reportPaging.setTotalSize(totalNum);
		reportList = jxkhReportService.findReportByKbNumber2(user.getDept()
				.getKdNumber(), reportPaging.getActivePage(), reportPaging
				.getPageSize());
		reportListbox.setModel(new ListModelList(reportList));

		String[] a = { "", "�����", "���������", "����ͨ��", "������ͨ��", "���Ų�ͨ��" };
		List<String> auditStateList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			auditStateList.add(a[i]);
		}
		auditState.setModel(new ListModelList(auditStateList));
		auditState.setSelectedIndex(0);
	}

	// �����б���Ⱦ��
	public class ReportRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Report report = (Jxkh_Report) data;
			item.setValue(report);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(report.getName().length() <= 14?
					report.getName():report.getName().substring(0, 14) + "...");
			c2.setTooltiptext(report.getName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					Listitem item = (Listitem) event.getTarget().getParent();
					Jxkh_Report report = (Jxkh_Report) item.getValue();
					AddReportWin w = (AddReportWin) Executions
							.createComponents(
									"/admin/personal/deptbusinessdata/report/addreport.zul",
									null, null);
					try {
						w.setReport(report);
						w.setAudit("AUDIT");
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			//��������
			Listcell c3 = new Listcell(report.getType());
			//�������
			Listcell c4 = new Listcell();
			if (report.getjxYear() != null) {
				c4 = new Listcell(report.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			//����÷�
			Listcell c5 = new Listcell(report.getScore() == null ? "" : report
					.getScore().toString());
			//��д��
			Listcell c6 = new Listcell();
			c6.setLabel(report.getSubmitName());
			//������
			Listcell c7 = new Listcell();
			c7.setTooltiptext("�����д������");
			if (report.getState() == null || report.getState() == 0) {
				c7.setLabel("�����");
				c7.setStyle("color:red");
			} else {
				switch (report.getState()) {
				case 0:
					break;
				case 1:
					c7.setLabel("����ͨ��");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("������ͨ��");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("���Ų�ͨ��");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("ҵ���ͨ��");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("ҵ��첻ͨ��");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("�鵵");
					c7.setStyle("color:red");
					break;
				case 7:
					c7.setLabel("ҵ����ݻ�ͨ��");
					c7.setStyle("color:red");
					break;
				}
			}
			// �����������¼�
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/jxkh/audit/report/advice.zul", null, null);
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
		}
	}

	public void onClick$passAll() {
		if (reportListbox.getSelectedItems() == null
				|| reportListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫ��˱��棡", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		@SuppressWarnings("unchecked")
		Iterator<Listitem> items = reportListbox.getSelectedItems().iterator();
		List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
		Jxkh_Report report = new Jxkh_Report();
		while (items.hasNext()) {

			report = (Jxkh_Report) items.next().getValue();
			int rank = 0, index = 0;
			List<Jxkh_ReportDept> meetingDepList = report.getReprotDept();
			for (int t = 0; t < meetingDepList.size(); t++) {
				Jxkh_ReportDept dep = meetingDepList.get(t);
				if (dep.getDeptId().equals(user.getDept().getKdNumber())) {
					rank = dep.getRank();
					index = t;
				}
			}
			if ((rank == 1 || report.getState() == Jxkh_Report.First_Dept_Pass)
					&& report.getTempState().charAt(index) == '0') {
				reportList.add(report);
			}

		}
		BatchAuditWin win = (BatchAuditWin) Executions.createComponents(
				"/admin/jxkh/audit/report/batchAudit.zul", null, null);
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

	/** ״̬�б���Ⱦ�� */
	public class auditStateRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			String auditState = (String) data;
			item.setValue(auditState);
			Listcell c0 = new Listcell();
			if (auditState == null || auditState.equals(""))
				c0.setLabel("--��ѡ��--");
			else {
				c0.setLabel(auditState);
			}
			item.appendChild(c0);
		}
	}

	public void onClick$query() {
		nameSearch = reportName.getValue();
		auditStateSearch = null;
		isQuery = true;
		if (auditState.getSelectedItem().getValue().equals("")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("������ͨ��")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch = 6;
		}
		indicatorId = null;
		if (rank.getSelectedIndex() != 0) {
			indicatorId = rank.getSelectedItem().getValue() + "";
		}
		yearSearch = null;
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		reportPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reportList = jxkhReportService.findReportByCondition(
						nameSearch, auditStateSearch, indicatorId, yearSearch,
						user.getDept().getKdNumber(),
						reportPaging.getActivePage(),
						reportPaging.getPageSize());
				reportListbox.setModel(new ListModelList(reportList));
			}
		});
		int totalNum = jxkhReportService.findReportByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber());
		reportPaging.setTotalSize(totalNum);
		reportList = jxkhReportService.findReportByCondition(nameSearch,
				auditStateSearch, indicatorId, yearSearch, user.getDept()
						.getKdNumber(), reportPaging.getActivePage(),
				reportPaging.getPageSize());
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
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (reportListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
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
			String Title = "�������";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("��������");
			titlelist.add("��ʾ�쵼");
			titlelist.add("���ʱ��");
			titlelist.add("��ѧ����");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
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
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_ReportDept> dlist = report.getReprotDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_ReportDept de = (Jxkh_ReportDept) dlist.get(m);
						dept += de.getName() + "��";
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
